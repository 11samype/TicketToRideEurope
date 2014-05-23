package utils.exceptions;

import utils.MessageHelper;

public class RouteAfterTrainException extends ClaimRouteException {
	
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.unable.message"));
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"claim.error.routeAfterTrain.message"));
		return sb.toString();
	}

}
