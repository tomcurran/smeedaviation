package com.smeedaviation.quadcopter.smeedmote;

import com.smeedaviation.quadcopter.model.ControlModel;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

public class SmeedWiiMain {
	
	public SmeedWiiMain(ControlModel controlModel) {
		
		QuadControlState qcs = new QuadControlState();
		SmeedWii smeedWii = new SmeedWii(qcs);
		AviationControl aviationControl = new AviationControl(qcs, controlModel);
		qcs.addObserver(aviationControl);
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		
		wiimotes[0].addWiiMoteEventListeners(smeedWii);
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		QuadControlState qcs = new QuadControlState();
		SmeedWii smeedWii = new SmeedWii(qcs);
		AviationControl aviationControl = new AviationControl(qcs, new ControlModel());
		qcs.addObserver(aviationControl);
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		
		wiimotes[0].addWiiMoteEventListeners(smeedWii);
		
//		aviationControl.controlLoop();
	}
	
    

}
