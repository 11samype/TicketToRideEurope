package objects.interfaces;

import objects.Destination;

public interface IRoute {
	public Destination getStart();
	public Destination getEnd();
	public int getLength();
}
