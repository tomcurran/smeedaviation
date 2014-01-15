package smeedaviation.smeedmote;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
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

public class SmeedMote{
	
	private Wiimote[] wiimotes;
	
	public SmeedMote(){
		wiimotes = WiiUseApiManager.getWiimotes(1, true);
		wiimotes[0].activateMotionSensing();
		wiimotes[0].addWiiMoteEventListeners(new SmeedMoteListener());
	}

	private class SmeedMoteListener implements WiimoteListener {

		@Override
		public void onButtonsEvent(WiimoteButtonsEvent e) {
			System.out.println("Buttons");
			
		}
	
		@Override
		public void onIrEvent(IREvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onMotionSensingEvent(MotionSensingEvent e) {
			System.err.println("Accel0rate");
			System.err.println("X: " + e.getRawAcceleration().getX());
			System.err.println("Y: " + e.getRawAcceleration().getY());
			System.err.println("Z: " + e.getRawAcceleration().getZ());
			
		}
	
		@Override
		public void onExpansionEvent(ExpansionEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onStatusEvent(StatusEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onDisconnectionEvent(DisconnectionEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onNunchukInsertedEvent(NunchukInsertedEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onNunchukRemovedEvent(NunchukRemovedEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onClassicControllerInsertedEvent(
				ClassicControllerInsertedEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
}