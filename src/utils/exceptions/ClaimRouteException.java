package utils.exceptions;

import utils.MessageHelper;

public abstract class ClaimRouteException extends LocalizedException {

	@Override
	public String getTitle() {
		return MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.title");
	}

}
