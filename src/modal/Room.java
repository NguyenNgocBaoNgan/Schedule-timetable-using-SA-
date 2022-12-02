package modal;

public class Room {
	private int nameRoom;
	private int numberComputer;

	public Room(int name, int computer) {
		super();
		this.nameRoom = name;
		this.numberComputer = computer;
	}

	public int getNameRoom() {
		return nameRoom;
	}

	public void setNameRoom(int name) {
		this.nameRoom = name;
	}

	public int getNumberComputer() {
		return numberComputer;
	}

	public void setNumberComputer(int count) {
		this.numberComputer = count;
	}

	@Override
	public String toString() {
		return "[tenPhong=" + nameRoom + ", soluongMay=" + numberComputer + "]";
	}

}
