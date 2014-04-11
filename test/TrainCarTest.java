import static org.junit.Assert.*;
import objects.TrainCar;
import objects.TrainColor;

import org.junit.Test;


/**
 * TODO Put here a description of what this class does.
 *
 * @author samynpd.
 *         Created Apr 11, 2014.
 */
public class TrainCarTest {

	@Test
	public void testMakeTrainCar() {
		TrainCar car = new TrainCar(TrainColor.BLACK);
		assertNotNull(car);
	}
	
	@Test
	public void testGetTrainCarColor() {
		TrainColor color = TrainColor.BLACK;
		TrainCar car = new TrainCar(color);
		assertEquals(color, car.getColor());
	}
	
	@Test
	public void testGetTrainCarAWTColor() {
		TrainColor color = TrainColor.BLACK;
		TrainCar car = new TrainCar(color);
		assertEquals(color.getAwtColor(), car.getAwtColor());
	}

}
