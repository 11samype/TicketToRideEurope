

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import utils.MessageHelper;
import utils.exceptions.BuildStationException;
import utils.exceptions.DestinationHasStationException;

public class LocalizedExceptionTest {

	@Test
	public void testBuildStationTitle() {
		BuildStationException e = new BuildStationException() {
			
			@Override
			public String getMessage() {
				return null; // don't need it
			}
		};
		assertEquals(getString("build.error.title"), e.getTitle());
	}

	@Test
	public void testHasStationMessage() {
		DestinationHasStationException e = new DestinationHasStationException();
		StringBuilder sb = new StringBuilder();
		sb.append(getString("build.error.unable.message"));
		sb.append(getString("build.error.alreadyTaken.message"));
		
		assertEquals(sb.toString(), e.getMessage());
		
		
	}
	
	private String getString(String key) {
		return MessageHelper.getStringFromBundle(MessageHelper.getMessages(), key);
	}

}
