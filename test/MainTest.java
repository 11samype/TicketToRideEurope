import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class MainTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLogGameData() {
		ByteArrayOutputStream log_baos = new ByteArrayOutputStream();
		ByteArrayOutputStream empty_baos = new ByteArrayOutputStream();
		PrintStream logStream = new PrintStream(log_baos);
		PrintStream blankStream = new PrintStream(empty_baos);
		PrintStream backUp = System.out;
		
		System.setOut(logStream);
		String printed = log_baos.toString();
		String str = empty_baos.toString();
		assertTrue(printed.isEmpty());
		assertTrue(str.isEmpty());

		try {
			Class<Main> cls = Main.class;
			Object obj = cls.newInstance();

			Method log = cls.getDeclaredMethod("prepareGameData", boolean.class);
			log.setAccessible(true);
			
			log.invoke(obj, new Boolean(true));
			logStream.flush();
			
			printed = log_baos.toString();
			assertFalse(printed.isEmpty());
			
			// run again without logging
			System.setOut(blankStream);
			log.invoke(obj, false);
			blankStream.flush();
			printed = empty_baos.toString();
			assertTrue(printed.isEmpty());
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			System.setOut(backUp);
			logStream.close();
		}
	}

}
