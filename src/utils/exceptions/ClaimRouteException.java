package utils.exceptions;

import utils.MessageHelper;

public abstract class ClaimRouteException extends LocalizedException {

	public String getTitle() {
		return MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.title");
	}

}
