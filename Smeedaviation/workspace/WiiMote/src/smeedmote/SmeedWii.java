package smeedmote;

import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;


public class SmeedWii implements WiimoteListener {
	
	private QuadControlState qcs;
	
	public SmeedWii(QuadControlState qcs){
		this.qcs = qcs;
	}

	/*
	 * 
	 *  1.  Thrust (B) = 4		Thrust A
	 *  2.  Thrust (A) = 8		Thrust B
	 * 
	 *  3.     Forward = 2048 	Forward
	 *  4.        Left = 256    Left
	 *  5.       Right = 512	Right
	 *  6.        Back = 1024	Back
	 * 
	 *  7.     (1 + 3) = 2052   Thrust A + Forward
	 *  8.     (1 + 4) = 260    Thrust A + Left
	 *  9.     (1 + 5) = 516    Thrust A + Right
	 * 10.     (1 + 6) = 1028   Thrust A + Back
	 * 
	 * 11. (1 + 3) + 4 = 2308   Thrust A + Forward + Left
	 * 12. (1 + 3) + 5 = 2564   Thrust A + Forward + Right
	 * 
	 * 13. (1 + 6) + 4 = 1284   Thrust A + Back + Left
	 * 14. (1 + 6) + 5 = 1540   Thrust A + back + Right
	 * 
	 * 15.     (1 + 2) = 12     Thrust A + Thrust B
	 * 
	 */
	

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent e) {
		short pressed = e.getButtonsJustPressed();
		short released = e.getButtonsJustReleased();
		short held = e.getButtonsHeld();
		qcs.setAllOff();
		if(e.isButtonBHeld()){
			qcs.setThrust(true);
		}else{
			qcs.setThrust(false);
		}
		
		switch (pressed) {
		case 0:
			qcs.setAllOff();
			break;
		case 4:
			qcs.setThrust(true);
			break;
		case 2048: 
			qcs.setForward(true);
			break;
		case 256:
			qcs.setBankLeft(true);
			break;
		case 512:
			qcs.setBankRight(true);
			break;
		case 1024:
			qcs.setBackward(true);
			break;
		case 2052:
			qcs.setForward(true);
			break;
		case 260:
			qcs.setBankLeft(true);
			break;
		case 516:
			qcs.setBankRight(true);
			break;
		case 1028:
			qcs.setBackward(true);
			break;
		case 2308:
			qcs.setForward(true);
			qcs.setBankLeft(true);
			break;
		case 2564:
			qcs.setForward(true);
			qcs.setBankRight(true);
			break;
		case 1284:
			qcs.setBackward(true);
			qcs.setBankLeft(true);
			break;
		case 1540:
			qcs.setBackward(true);
			qcs.setBankRight(true);
		default:
			break;
		}
		
	}

	@Override
	public void onIrEvent(IREvent e) {
	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent e) {
	}

	@Override
	public void onExpansionEvent(ExpansionEvent e) {
	}

	@Override
	public void onStatusEvent(StatusEvent e) {
	}

	@Override
	public void onDisconnectionEvent(DisconnectionEvent e) {
	}

	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent e) {
	}

	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent e) {
	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent e) {
	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent e) {
	}

	@Override
	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent e) {
	}

	@Override
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent e) {
	}
}
