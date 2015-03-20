package tv;

public class TVOn implements TVState {

	@Override
	public void pressOnOffButton() {
		System.out.println("Desligando a TV");
	}
}
