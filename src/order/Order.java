package order;

public class Order {

	private State state;
	private String webCode;

	public Order(State state, String webCode) {
		this.state = state;
		this.webCode = webCode;
	}

	public String getWebCode() {
		return webCode;
	}

	@Override
	public String toString() {
		return "Order [state=" + state + "]";
	}

	public void setWebCode(String webCode) {
		this.webCode = webCode;
	}
}
