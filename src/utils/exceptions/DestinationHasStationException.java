package utils.exceptions;

import utils.MessageHelper;

public class DestinationHasStationException extends BuildStationException {

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"build.error.unable.message"));
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"build.error.alreadyTaken.message"));
		return sb.toString();
	};
}
