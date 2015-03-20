package order;

import static order.State.CLOSED;
import static order.State.OPEN;

public class FromOpenToClosed implements Action {

	@ChangeState
	public String execute(Order order) {
		order.setWebCode("alterado");
		System.out.println("Estou fazendo alteracoes na order para fechar");
		return order.getWebCode();
	}

	@Override
	public State getPreviousState() {
		return OPEN;
	}

	@Override
	public State getNextState() {
		return CLOSED;
	}

	@Override
	public boolean canExecute(Order order) {
		return order.getWebCode() != null;
	}
}
