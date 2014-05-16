package utils.exceptions;

import utils.MessageHelper;

public class RouteTakenException extends ClaimRouteException {

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.unable.message"));
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.alreadyTaken.message"));
		return sb.toString();
	};
}
