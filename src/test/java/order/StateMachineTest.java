package order;

import static order.State.CLOSED;
import static order.State.OPEN;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class StateMachineTest {

	@Test
	public void shouldCloseOrder()	{
		Order order = new Order(OPEN, "ABC123");
		String orderWebCode = StateMachine.move(order)
				.using(FromOpenToClosed.class)
				.execute(order);

		String actualWebCode = "alterado";
		assertEquals(order.getWebCode(), actualWebCode);
		assertEquals(order.getState(), CLOSED);
		assertEquals(orderWebCode, actualWebCode);

	}

	@Test(expected=IllegalStateException.class)
	public void shouldNotCloseOrderThatIsAlreadyClosed()	{
		Order order = new Order(State.CLOSED, "ABC123");
		StateMachine.move(order)
				.using(FromOpenToClosed.class)
				.execute(order);
	}

}
