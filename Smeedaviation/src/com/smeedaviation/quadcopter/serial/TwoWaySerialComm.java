package com.smeedaviation.quadcopter.serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import com.smeedaviation.quadcopter.model.AbstractModel;
import com.smeedaviation.quadcopter.model.AccelerationModel;
import com.smeedaviation.quadcopter.model.GyrationModel;
import com.smeedaviation.quadcopter.model.MotorModel;

public class TwoWaySerialComm {

	private static final String PORT_NAMES[] = {
		"/dev/tty.usbserial-A9007LL6", // mac
		"COM7" // windows
	};

	@SuppressWarnings("unchecked")
	private CommPortIdentifier getCommPortIdentifier() {
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					return currPortId;
				}
			}
		}
		return null;
	}

	public void connect(AccelerationModel accelModel, GyrationModel gyroModel, MotorModel moterModel) throws Exception {
		CommPortIdentifier portIdentifier = getCommPortIdentifier();
		if (portIdentifier == null) {
			throw new NoSuchPortException();
		}
		if (portIdentifier.isCurrentlyOwned()) {
			throw new Exception("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();
				
				ModelHelper modelHelper = new ModelHelper(accelModel, gyroModel);

				serialPort.addEventListener((SerialPortEventListener) new SerialReader(in, modelHelper));
				serialPort.notifyOnDataAvailable(true);
				
				new SerialWriter(out, moterModel);

			} else {
				throw new Exception("Error: Only serial ports are handled by this example.");
			}
		}
	}
	
	/**
	 * Handles the input coming from the serial port. A new line character is
	 * treated as the end of a block in this example.
	 */
	public static class SerialReader implements SerialPortEventListener {
		private InputStream in;
		private byte[] buffer = new byte[1024];
		private ModelHelper modelHelper;

		
		public SerialReader(InputStream in, ModelHelper modelHelper) {
			this.in = in;
			this.modelHelper = modelHelper;
		}
		
		@Override
		public void serialEvent(SerialPortEvent arg0) {
			int data;

			try {
				int len = 0;
				while ((data = in.read()) > -1) {
					if (data == '\n') {
						break;
					}
					buffer[len++] = (byte) data;
				}
				
				String message = new String(buffer, 0, len);
				
				modelHelper.setSensorData(message);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	/** */
	public static class SerialWriter implements Observer  {
		private OutputStream out;
		
		public SerialWriter(OutputStream out, MotorModel motorModel) {
			this.out = out;
			motorModel.addObserver(this);
		}

		@Override
		public void update(Observable o, Object arg) {
			AbstractModel model = (MotorModel)o;
			int[] data = model.getData();
			
			byte[] outData = String.format("%d,%d,%d,%d; %n", data[0], data[1], data[2], data[3]).getBytes();
			
			try {
				this.out.write(outData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}