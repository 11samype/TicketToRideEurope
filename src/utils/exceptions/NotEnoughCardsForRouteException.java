package utils.exceptions;

import utils.MessageHelper;

public class NotEnoughCardsForRouteException extends ClaimRouteException {

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.unable.message"));
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.notEnoughCards.message"));
		return sb.toString();
	};
}
