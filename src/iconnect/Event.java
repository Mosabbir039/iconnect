package iconnect;

public class Event {
	 public static final int MOUSE_MOVED = 0;
	 public static final int MOUSE_CLICKED = 1;
	 
	double x;
	double y;
	double eventType;
	
	public Event(double x, double y, double eventType){
		this.x = x;
		this.y = y;
		this.eventType = eventType;
	
	}
	
	public String toString() {
		return x+" " +y + " " + eventType;
	}

	public static Event parse(String event) {
		String [] e= event.split(" ");
		System.out.println("parsed: " + e[0]+" \n"+e[1]+"\n"+e[2]);
		return new Event(Double.parseDouble(e[0]), Double.parseDouble(e[1]), Double.parseDouble(e[2]));
	}
	
	
}
