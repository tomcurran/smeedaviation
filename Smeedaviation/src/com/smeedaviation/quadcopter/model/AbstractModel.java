package com.smeedaviation.quadcopter.model;

import java.util.Observable;

public abstract class AbstractModel extends Observable{
	

	public synchronized int[] getData() {
		return returnData();
	}

	public synchronized void setData(int[] data) {
		if (!this.inputValid(data)) { 
			return; 
		}
		
		updateData(data);
		
		setChanged();
		notifyObservers();			
	}
	
	protected abstract boolean inputValid(int[] data);
	protected abstract void updateData(int[] data);
	protected abstract int[] returnData();
	
}
