package modal;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Coure {
	private Room room;
	private int day;
	private int beginTime;
	private Random rd = new Random();

	public Coure(Room room) {
		super();
		int day = rd.nextInt(2, 9);
		int begin = rd.nextInt(1, 5);
		this.room = room;
		this.day = day;
		this.beginTime = begin;
	}



	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}

	public boolean isConflitCoureAndRoom(Coure orthercoure) {
		if(this.day == orthercoure.getDay()) {
			if(this.beginTime == orthercoure.getBeginTime()) {
				if(this.room.getNameRoom() == orthercoure.getRoom().getNameRoom()) {
					return true;
				}
			}
			
		}
	
		return false;
	}
	public boolean isConflitCoureNoRoom(Coure orthercoure) {
		if(this.day == orthercoure.getDay()) {
			if(this.beginTime == orthercoure.getBeginTime()) {
					return true;
					
			}
			
		}
		
		return false;
	}
	
	
	@Override
	public String toString() {
		return "[phongHoc=" + room + ", thu=" + day + ", ca=" + beginTime + "]";
	}

	
	

}
