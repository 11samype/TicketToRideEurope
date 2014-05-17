package utils.exceptions;

import utils.MessageHelper;

public class RouteOwnedException extends ClaimRouteException {

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.unable.message"));
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.alreadyOwn.message"));
		return sb.toString();
	};
}
