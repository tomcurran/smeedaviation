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

import com.smeedaviation.quadcopter.model.SynchronizedModel;

public class TwoWaySerialComm {

	private static final String PORT_NAMES[] = {
		"/dev/tty.usbserial-A9007LL6", // mac
		"COM7" // windows
	};

	SynchronizedModel model;
    InputStream in;

	public TwoWaySerialComm(SynchronizedModel model) {
		this.model = model;
	}

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

	public void connect() throws Exception {
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
				

				serialPort.addEventListener((SerialPortEventListener) new SerialReader(in, this.model));
				serialPort.notifyOnDataAvailable(true);
				
				(new Thread(new SerialWriter(out, this.model))).start();

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
		SynchronizedModel model;
		
		public SerialReader(InputStream in, SynchronizedModel model) {
			this.in = in;
			this.model = model;
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
				model.setSensorData(message);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

	}

	/** */
	public static class SerialWriter implements Runnable {
		private OutputStream out;
		private SynchronizedModel model;
		
//		int count = 0;
		public SerialWriter(OutputStream out, SynchronizedModel model) {
			this.out = out;
			this.model = model;
		}		

		public void run() {
			try {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (true) {
					int c = 0;
					while ((c = this.model.in.read()) > -1) {
						this.out.write(c);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}