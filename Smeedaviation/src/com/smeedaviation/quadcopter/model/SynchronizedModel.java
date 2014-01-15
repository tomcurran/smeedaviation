package com.smeedaviation.quadcopter.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;

public class SynchronizedModel extends Observable{

	private int[] sensorData = {0,0,0,0,0,0};
	private int[] motorData = {0,0,0,0};
    private boolean newData = false;
	private byte[] outData;
	private int count = 0;

	public InputStream in;
    

	
	public SynchronizedModel() {
		this.in = new InputStream() {
			
			@Override
			public int read() throws IOException {
				if (outData != null && count == outData.length) {
					newData = false;
					count = 0;
					return -1;
				}
				
				if (newData) {
					return outData[count++];
				}
				
				return -1;
			}
		};
	}

	public synchronized int[] getSensorData() {
		return sensorData.clone();
	}

	public synchronized void setSensorData(int[] newSensorData) {
		if (newSensorData.length == 6) {
			this.sensorData = newSensorData.clone();
			
			setChanged();
			notifyObservers();
			
//			System.out.printf("ax:%d ay:%d az:%d gx:%d gy:%d gz:%d %n", 
//			this.sensorData[0] , this.sensorData[1], this.sensorData[2] , 
//			this.sensorData[3], this.sensorData[4], this.sensorData[5]);
			
		}
		
	}
	
	public synchronized void setSensorData(String newSensorData) {		
		
		newSensorData = newSensorData.replaceAll("(\\r|\\n)", "");
		String[] arguments = newSensorData.split(";");
		if (arguments.length != 6) {
			// invalid arguments
			return;
		}
		
		int[] newValues = new int[6];
		for (int i = 0; i < arguments.length; i++) {
			newValues[i] = Integer.parseInt(arguments[i].split(":")[1]);
		}
		
		this.setSensorData(newValues);
	}
	
	public synchronized int[] getMotorData() {
		return motorData.clone();
	}
	
	public synchronized void setMotorData(int[] newMotorData) {
		if (newMotorData.length == 4) {
			this.motorData = newMotorData.clone();
			
			setChanged();
			notifyObservers();
			
			this.outData = String.format("%d,%d,%d,%d; %n", this.motorData[0], this.motorData[1], this.motorData[2], this.motorData[3]).getBytes();
			this.newData = true;
		}
	}
}
