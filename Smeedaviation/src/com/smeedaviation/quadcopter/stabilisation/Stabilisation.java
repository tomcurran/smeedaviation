package com.smeedaviation.quadcopter.stabilisation;

import com.smeedaviation.quadcopter.model.SynchronizedModel;

public class Stabilisation implements Runnable {
	
	private SynchronizedModel model;

	public Stabilisation(SynchronizedModel model) {
		this.model = model;
	}

	@Override
	public void run() {
		
		
	}

}
