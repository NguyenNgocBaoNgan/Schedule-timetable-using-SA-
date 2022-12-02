package modal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Schedule {
	private List<Subject> listSubjects;
	private List<Room> listRooms = new ArrayList<>();

	public Schedule(List<Subject> listSubjects) {
		ArrayList<Subject> resSubjects = new ArrayList<>();
		for (int i = 0; i < listSubjects.size(); i++) {
			Subject sub = new Subject(listSubjects.get(i).getIdSubject(), listSubjects.get(i).getNameSubject(),
					listSubjects.get(i).getTeacher(), listSubjects.get(i).getQuantityStudent(),
					listSubjects.get(i).getNameClass());
			Coure coure = new Coure(listSubjects.get(i).getCoure().getRoom());
			coure.setBeginTime(listSubjects.get(i).getCoure().getBeginTime());
			coure.setDay(listSubjects.get(i).getCoure().getDay());
			sub.setCoure(coure);
			resSubjects.add(sub);
		}
		this.listSubjects = resSubjects;

	}

	public Schedule(List<Subject> listSub, List<Room> rooms) {
		ArrayList<Subject> resSubjects = new ArrayList<>();
		for (int i = 0; i < listSub.size(); i++) {
			Subject sub = new Subject(listSub.get(i).getIdSubject(), listSub.get(i).getNameSubject(),
					listSub.get(i).getTeacher(), listSub.get(i).getQuantityStudent(), listSub.get(i).getNameClass());
			Coure coure = new Coure(listSub.get(i).getCoure().getRoom());
			coure.setBeginTime(listSub.get(i).getCoure().getBeginTime());
			coure.setDay(listSub.get(i).getCoure().getDay());
			sub.setCoure(coure);
			resSubjects.add(sub);
		}
		this.listSubjects = resSubjects;
		this.listRooms = rooms;

	}

	public Schedule(Schedule schedule) {
		ArrayList<Subject> resSubjects = new ArrayList<>();
		for (int i = 0; i < schedule.listSubjects.size(); i++) {
			Subject sub = new Subject(schedule.listSubjects.get(i).getIdSubject(),
					schedule.listSubjects.get(i).getNameSubject(), schedule.listSubjects.get(i).getTeacher(),
					schedule.listSubjects.get(i).getQuantityStudent(), schedule.listSubjects.get(i).getNameClass());
			Coure coure = new Coure(schedule.listSubjects.get(i).getCoure().getRoom());
			coure.setBeginTime(schedule.listSubjects.get(i).getCoure().getBeginTime());
			coure.setDay(schedule.listSubjects.get(i).getCoure().getDay());
			sub.setCoure(coure);
			resSubjects.add(sub);
		}
		this.listSubjects = resSubjects;
		this.listRooms = schedule.getListRooms();
	}

	public int getH1() {
		int heuristic = 0;
		for (int i = 0; i < listSubjects.size(); i++) {
			if (listSubjects.get(i).isBiggerQuantity()) {
				heuristic++;
			}
			if (moreThanFourADay(listSubjects.get(i).getTeacher(), listSubjects.get(i).getCoure().getDay())) {
				heuristic++;
			}
			if (moreThanFourAWeek(listSubjects.get(i).getTeacher())) {
				heuristic++;
			}
			if (isSunday(listSubjects.get(i))) {
				heuristic++;
			}
			if (tooFar(listSubjects.get(i))) {
				heuristic++;
			}
			for (int j = i + 1; j < listSubjects.size(); j++) {
				if (listSubjects.get(i).isConflitTeacher(listSubjects.get(j))) {
					heuristic++;
				}
			}
		}
		return heuristic;

	}


	public List<Schedule> generateAllCandidates() {
		List<Schedule> result = new ArrayList<Schedule>();
		for (int i = 0; i < listSubjects.size(); i++) {
			Schedule child = new Schedule(listSubjects, listRooms);
			child.getSubjects().get(i).move(this.listRooms);
			result.add(child);
		}

		return result;
	}

// create a random schedule
	public Schedule selectNextRandomSchedule() {
		List<Schedule> result = generateAllCandidates();
		Random rd = new Random();
		return result.get(rd.nextInt(result.size()));

	}

	public Schedule selectScheduleBest() {
		List<Schedule> result = generateAllCandidates();
		Schedule lichHoc = result.get(0);

		for (int i = 1; i < result.size(); i++) {
			if (lichHoc.getH1() > result.get(i).getH1()) {
				lichHoc = result.get(i);
			} 

		}
		return lichHoc;
	}

//// more than 4 coure in a day
	public boolean moreThanFourADay(String teacherName, int day) {
		int count = 0;
		for (Subject sb : listSubjects) {
			if (sb.getTeacher().equals(teacherName) && sb.getCoure().getDay() == day) {
				count++;
			}
		}
		if (count > 5)
			return true;

		return false;
	}
// have coure in a day
	public boolean haveCoure(String teacherName, int day) {
		int count = 0;
		for (Subject sb : listSubjects) {
			if (sb.getTeacher().equals(teacherName) && sb.getCoure().getDay() == day) {
				count++;
			}
		}
		if (count > 0)
			return true;

		return false;
	}

//// more than 4 day in a week
	public boolean moreThanFourAWeek(String teacherName) {
		int count = 0;
		for (int i = 2; i < 9; i++) {
			if (haveCoure(teacherName, i)) {
				count++;
			}
		}
		if (count > 5) 
			return true;

		return false;
	}

//// too far example 1 - 4
	public boolean tooFar(Subject orther) {
		for (Subject sb : listSubjects) {
			if (sb.getTeacher().equals(orther.getTeacher()) && sb.getCoure().getDay() == orther.getCoure().getDay()) {
				if (Math.abs(sb.getCoure().getBeginTime() - orther.getCoure().getBeginTime()) >= 2) {
					return true;
				}
			}

		}

		return false;
	}

	//// NOT SUNDAY
	public boolean isSunday(Subject sb) {
		if (sb.getCoure().getDay() == 8)
			return true;
		return false;
	}

	public List<Subject> getSubjects() {
		return listSubjects;
	}

	public void setListSubjects(List<Subject> subjects) {
		this.listSubjects = subjects;
	}

	public List<Room> getListRooms() {
		return listRooms;
	}

	public void setListRooms(List<Room> rooms) {
		this.listRooms = rooms;
	}

	public static final Comparator<Subject> DESCOMPARATOR = new Comparator<Subject>() {

		@Override
		public int compare(Subject o1, Subject o2) {
			if (o1.getTeacher().compareTo(o2.getTeacher()) != 0) {
				return o1.getTeacher().compareTo(o2.getTeacher());
			}
			if (o1.getCoure().getDay() - o2.getCoure().getDay() != 0) {
				return o1.getCoure().getDay() - o2.getCoure().getDay();
			}
			if (o1.getCoure().getBeginTime() - o2.getCoure().getBeginTime() != 0) {
				return o1.getCoure().getBeginTime() - o2.getCoure().getBeginTime();
			}
			return o1.getCoure().getRoom().getNameRoom() - o2.getCoure().getRoom().getNameRoom();

		}

	};

	public void display() {
		Collections.sort(listSubjects, DESCOMPARATOR);
		for (int i = 2; i < 9; i++) {
			System.out.println("================================================================ DAY " + i
					+ " =========================================================");
			System.out.printf("%10s %35s %15s %15s %10s %10s %10s", "ID Sub", "nameSub", "teacher", "class",
					"begin", "room", "maximum");
			System.out.println(" ");
			for (Subject subject : listSubjects) {

				if (subject.getCoure().getDay() == i) {
					System.out.printf("%10s %35s %15s %15s %10s %10s %10s", subject.getIdSubject(),
							subject.getNameSubject(), subject.getTeacher(), subject.getNameClass()
							, subject.getCoure().getBeginTime(),
							"lab " + subject.getCoure().getRoom().getNameRoom(),
							subject.getCoure().getRoom().getNumberComputer());
					System.out.println(" ");
				}

			}
		}
	}

}
