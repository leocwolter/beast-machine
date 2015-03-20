package order;

public interface Action {

	State getPreviousState();

	State getNextState();

	boolean canExecute(Order order);

}
