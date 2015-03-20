package order;

import java.util.Arrays;
import java.util.List;

public enum State {
	OPEN(FromOpenToClosed.class), CLOSED();

	private final List<Class<? extends Action>> actions;

	private State(Class<? extends Action>... actions) {
		this.actions = Arrays.asList(actions);
	}

	public boolean canBeChangedBy(Class<? extends Action> action){
		return actions.contains(action);
	}
}
