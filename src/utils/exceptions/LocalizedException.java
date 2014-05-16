package utils.exceptions;

import java.util.ResourceBundle;

import utils.MessageHelper;

public abstract class LocalizedException extends Exception {

	protected String title;

	public abstract String getTitle();

	@Override
	public abstract String getMessage();

	protected ResourceBundle getMessageBundle() {
		return MessageHelper.getMessages();
	}
}
