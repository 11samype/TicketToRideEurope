package utils.exceptions;

import utils.MessageHelper;

public abstract class BuildStationException extends LocalizedException {

	@Override
	public String getTitle() {
		return MessageHelper.getStringFromBundle(getMessageBundle(),
				"build.error.title");
	}

}
