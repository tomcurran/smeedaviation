package com.smeedaviation.quadcopter.model;

public class GyrationModel extends AbstractModel {

	private int[] gyrationModel = {0,0,0};

	@Override
	protected boolean inputValid(int[] data) {
		return data.length == 3;
	}

	@Override
	protected void updateData(int[] data) {
		gyrationModel = data.clone() ;
	}

	@Override
	protected int[] returnData() {
		return gyrationModel.clone();
	}
}
