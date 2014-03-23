package objects;

public enum RouteType {
	TRAIN("train"), FERRY("ferry"), TUNNEL("tunnel");

	private String name;

	private RouteType(String name) {
		this.name = name;
	}

}
