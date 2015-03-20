package tv;

public class TVContext implements TVState {

	private TVState state;

	public TVContext() {
	}

	public TVContext(TVState state) {
		this.state = state;
	}

	@Override
	public void pressOnOffButton() {
		this.state.pressOnOffButton();
	}

	public void setState(TVState state) {
		this.state = state;
	}
}
