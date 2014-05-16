package utils.exceptions;

import utils.MessageHelper;

public class DestinationAfterTrainException extends DrawCardException {


	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"draw.error.unable.message"));
		sb.append(MessageHelper.getStringFromBundle(getMessageBundle(),
				"draw.error.destAfterTrain.message"));
		return sb.toString();
	}

}
