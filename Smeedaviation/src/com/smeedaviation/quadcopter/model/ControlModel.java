package com.smeedaviation.quadcopter.model;

public class ControlModel extends AbstractModel{
	
	private int[] controlModel = new int[6];

	@Override
	protected boolean inputValid(int[] data) {
		return data.length == 6;
	}

	@Override
	protected void updateData(int[] data) {
		controlModel = data.clone();
	}

	@Override
	protected int[] returnData() {
		return controlModel.clone();
	}

}
