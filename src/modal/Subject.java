package modal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Subject {
	private int idSubject;
	private String nameSubject;
	private String teacher;
	private Coure coure;
	private int quantityStudent;
	private String nameClass;

	public Subject(int idSubject, String nameSubject, String teacher, Coure coure, int quantityStudent,
			String nameClass) {
		super();
		this.idSubject = idSubject;
		this.nameSubject = nameSubject;
		this.teacher = teacher;
		this.coure = coure;
		this.quantityStudent = quantityStudent;
		this.nameClass = nameClass;
	}

	public Subject(int idSubject, String nameSubject, String teacher, int quantityStudent, String nameClass) {
		super();
		this.idSubject = idSubject;
		this.nameSubject = nameSubject;
		this.teacher = teacher;
		this.quantityStudent = quantityStudent;
		this.nameClass = nameClass;
	}

	public int getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(int idSubject) {
		this.idSubject = idSubject;
	}

	public String getNameSubject() {
		return nameSubject;
	}

	public void setNameSubject(String nameSubject) {
		this.nameSubject = nameSubject;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public Coure getCoure() {
		return coure;
	}

	public void setCoure(Coure coure) {
		this.coure = coure;
	}

	public int getQuantityStudent() {
		return quantityStudent;
	}

	public void setQuantityStudent(int quantityStudent) {
		this.quantityStudent = quantityStudent;
	}

	public String getNameClass() {
		return nameClass;
	}

	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}

	public void move(List<Room> rooms) {
		Random rd = new Random();
		int day = rd.nextInt(2, 7);
		int begin = rd.nextInt(1, 5);

		int room = rd.nextInt(0, rooms.size());

		this.coure.setRoom(rooms.get(room));
		this.coure.setDay(day);
		this.coure.setBeginTime(begin);

	}


	public boolean isConflitTeacher(Subject orther) {
		if (this.teacher.equals(orther.getTeacher())) {
			if (this.coure.isConflitCoureNoRoom(orther.getCoure())) {
				return true;
			}
		}
		if (!this.teacher.equals(orther.getTeacher())) {
			if (this.coure.isConflitCoureAndRoom(orther.getCoure())) {
				return true;
			}
			
		}

		return false;
	}
	public boolean isConflitClass(Subject orther) {
		if (this.nameClass.equals(orther.getNameClass())) {
			if (this.coure.isConflitCoureNoRoom(orther.getCoure())) {
				return true;
			}
		}
		if (!this.nameClass.equals(orther.getNameClass())) {
			if (this.coure.isConflitCoureAndRoom(orther.getCoure())) {
				return true;
			}
			
		}
		return false;
	}

	public boolean isBiggerQuantity() {
		if (this.getQuantityStudent() > this.coure.getRoom().getNumberComputer())
			return true;
		return false;
	}
	
	

	@Override
	public String toString() {
		return "[idSubject=" + idSubject + ", nameSubject=" + nameSubject + ", teacher=" + teacher + ", coure=" + coure
				+ ", quantityStudent=" + quantityStudent + ", nameClass=" + nameClass + "]";
	}

}
