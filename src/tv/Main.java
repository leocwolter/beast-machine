package tv;

public class Main {

	public static void main(String[] args) {

		TVContext context = new TVContext();
		TVOn on = new TVOn();
		TVOff off = new TVOff();

		context.setState(off);
		context.pressOnOffButton();

		context.setState(on);
		context.pressOnOffButton();
	}
}
