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
	private static final short BUTTON_A 	  = 4;		// 000000000100
	private static final short BUTTON_B		  = 8;		// 000000001000
	
	private static final short BUTTON_LEFT    = 256;	// 000100000000
	private static final short BUTTON_RIGHT   = 512;	// 001000000000
	private static final short BUTTON_BACK 	  = 1024;	// 010000000000
	private static final short BUTTON_FORWARD = 2048;	// 100000000000

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent e) {
		short pressed = e.getButtonsJustPressed();
		short released = e.getButtonsJustReleased();
		short held = e.getButtonsHeld();
		
		
//	    qcs.setAllOff();
//		qcs.setAltitudeUp((BUTTON_A & pressed) == BUTTON_A);
//		qcs.setAltitudeDown((BUTTON_B & pressed) == BUTTON_B);
//		
//		qcs.setBankLeft((BUTTON_LEFT & pressed) == BUTTON_LEFT);
//		qcs.setBankRight((BUTTON_RIGHT & pressed) == BUTTON_RIGHT);
//		qcs.setBackward((BUTTON_BACK & pressed) == BUTTON_BACK);
//		qcs.setForward((BUTTON_FORWARD & pressed) == BUTTON_FORWARD);
		
		boolean buttonA       = ((BUTTON_A & pressed) == BUTTON_A);
		boolean buttonB       = ((BUTTON_B & pressed) == BUTTON_B);
		boolean buttonLeft    = ((BUTTON_LEFT & pressed) == BUTTON_LEFT);
		boolean buttonRight   = ((BUTTON_RIGHT & pressed)	== BUTTON_RIGHT);
		boolean buttonBack    = ((BUTTON_BACK & pressed) == BUTTON_BACK);
		boolean buttonForward = ((BUTTON_FORWARD & pressed) == BUTTON_FORWARD);
		
		qcs.setAll(buttonA, buttonB, buttonLeft, buttonRight, buttonBack, buttonForward);
		
		
	
		
		
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
