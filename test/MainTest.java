import static org.junit.Assert.*;
import gui.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import utils.GameState;

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
			GameState.initializeGameData(true);
			logStream.flush();
			
			printed = log_baos.toString();
			assertFalse(printed.isEmpty());
			
			// run again without logging
			System.setOut(blankStream);
			GameState.initializeGameData(false);
			blankStream.flush();
			printed = empty_baos.toString();
			assertTrue(printed.isEmpty());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.setOut(backUp);
			logStream.close();
		}
	}

}
