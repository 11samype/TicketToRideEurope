package utils.exceptions;

import utils.MessageHelper;

public abstract class DrawCardException extends LocalizedException {

	@Override
	public String getTitle() {
		return MessageHelper.getStringFromBundle(getMessageBundle(),
				"draw.error.title");
	}


}
