package smeedmote;

public class ControlBoolean {
	private char button;
	private boolean pressed;
	
	public ControlBoolean(char button, boolean pressed){
		this.button = button;
		this.pressed = pressed;
	}
	
	public char getButton(){
		return button;
	}
	
	public boolean getPressed(){
		return pressed;
	}
	
	public void setPressed(boolean pressed){
		this.pressed = pressed;
	}

}
