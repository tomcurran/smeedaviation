package smeedmote;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

public class SmeedWiiMain {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		QuadControlState qcs = new QuadControlState();
		SmeedWii smeedWii = new SmeedWii(qcs);
		AviationControl aviationControl = new AviationControl(qcs);
		qcs.addObserver(aviationControl);
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		
		wiimotes[0].addWiiMoteEventListeners(smeedWii);
		
//		aviationControl.controlLoop();
	}

}
