package order;

import static order.State.OPEN;

public class Main {

	public static void main(String[] args) {
		{
			Order order = new Order(OPEN, "ABC123");
			String orderWebCode = StateMachine.move(order)
					.using(FromOpenToClosed.class)
					.execute(order);

			System.out.println(order);
			System.out.println("Fechei a order " + orderWebCode);
		}

		{
			Order order = new Order(State.CLOSED, "ABC123");
			String orderWebCode = StateMachine.move(order)
					.using(FromOpenToClosed.class)
					.execute(order);

			System.out.println(order);
			System.out.println("Fechei a order " + orderWebCode);
		}
	}
}
