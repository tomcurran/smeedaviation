package smeedmote;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class QuadControlState extends Observable {
	
	protected Map<String, Boolean> controlsPressed;
	
	public QuadControlState(){
		controlsPressed = new HashMap<>();
		controlsPressed.put("thrust", false);
		controlsPressed.put("forward", false);
		controlsPressed.put("bankLeft", false);
		controlsPressed.put("bankRight", false);
		controlsPressed.put("backward", false);
		
	}
	
	public void setThrust(boolean pressed){
		controlsPressed.put("thrust", pressed);
		setChanged();
		notifyObservers();
	}
	
	public void setForward(boolean pressed){
		controlsPressed.put("forward", pressed);
		setChanged();
		notifyObservers("forward");
	}
	
	public void setBankLeft(boolean pressed){
		controlsPressed.put("bankLeft", pressed);
		setChanged();
		notifyObservers();
	}
	
	public void setBankRight(boolean pressed){
		controlsPressed.put("bankRight", pressed);
		setChanged();
		notifyObservers();
	}
	
	public void setBackward(boolean pressed){
		controlsPressed.put("backward", pressed);
		setChanged();
		notifyObservers();
	}
	
	public boolean getControlValue(String control){
		return controlsPressed.get(control);
	}
	
	public boolean getThrust(){
		return controlsPressed.get("thrust");
	}
	
	public boolean getForward(){
		return controlsPressed.get("forward");
	}
	
	public boolean getBankLeft(){
		return controlsPressed.get("bankLeft");
	}
	
	public boolean getBankRight(){
		return controlsPressed.get("bankRight");
	}
	
	public boolean getBackward(){
		return controlsPressed.get("backward");
	}
	
	public void setAllOff(){
		controlsPressed.put("thrust", false);
		controlsPressed.put("forward", false);
		controlsPressed.put("bankLeft", false);
		controlsPressed.put("bankRight", false);
		controlsPressed.put("backward", false);
		setChanged();
		notifyObservers();
	}

}
