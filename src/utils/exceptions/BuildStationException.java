package utils.exceptions;

import utils.MessageHelper;

public abstract class BuildStationException extends LocalizedException {

	public String getTitle() {
		return MessageHelper.getStringFromBundle(getMessageBundle(),
				"build.error.title");
	}

}
