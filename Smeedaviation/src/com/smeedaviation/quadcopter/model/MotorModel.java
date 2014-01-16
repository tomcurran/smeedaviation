package com.smeedaviation.quadcopter.model;

public class MotorModel extends AbstractModel {

	private int[] motorModel = {0,0,0,0};

	@Override
	protected boolean inputValid(int[] data) {
		return data.length == 4;
	}

	@Override
	protected void updateData(int[] data) {
		motorModel = data.clone() ;
	}

	@Override
	protected int[] returnData() {
		return motorModel.clone();
	}
}
