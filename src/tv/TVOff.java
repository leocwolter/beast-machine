package tv;

public class TVOff implements TVState {

	@Override
	public void pressOnOffButton() {
		System.out.println("Ligando a TV");
	}
}
