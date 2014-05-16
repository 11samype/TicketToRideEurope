package utils.exceptions;

public class DestinationAfterTrainException extends LocalizedException {

	@Override
	public String getTitle() {
		return "Unable to take destination card";
	}

	@Override
	public String getMessage() {
		return "Can't draw Destination Card after Train Card";
	}

}
