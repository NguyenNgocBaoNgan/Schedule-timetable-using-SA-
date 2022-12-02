package Ex;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import SA.SimulatedAnnealing;
import modal.Schedule;
import modal.Subject;
import modal.Coure;
import modal.Room;

public class readData {

	public static void main(String[] args) throws IOException {
		List<Subject> listSubjects = readData.read();
		Random random = new Random();
		Room room1 = new Room(1, 60);
		Room room2 = new Room(2, 40);
		Room room3 = new Room(3, 50);
		Room room4 = new Room(4, 40);
		Room room5 = new Room(5, 60);
		List<Room> listRoom = new ArrayList<>();
		listRoom.add(room1);
		listRoom.add(room2);
		listRoom.add(room3);
		listRoom.add(room4);
		listRoom.add(room5);
		for (Subject item : listSubjects) {
			int idRoom = random.nextInt(listRoom.size());
			Coure coure = new Coure(listRoom.get(idRoom));
			item.setCoure(coure);
		}
		Schedule lichHoc = new Schedule(listSubjects);
		lichHoc.setListRooms(listRoom);
		
		Schedule resultSchedule = SimulatedAnnealing.execute(lichHoc);
		System.out.println("result Schedule H1: "+resultSchedule.getH1() );
		resultSchedule.display();
		

	}

	public static List<Subject> read() throws IOException {

		FileInputStream file = new FileInputStream("KHGD_KCNTT_HK1-2022.2023.xlsx");
		List<Subject> list = new ArrayList<>();
		XSSFWorkbook wb = new XSSFWorkbook(file);

		for (int i = 0; i < 3; i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			int count = sheet.getLastRowNum();
			for (int j = 6; j < count - 5; j++) {
				XSSFRow row = sheet.getRow(j);
				String nameClass = row.getCell(1).getStringCellValue();
				String nameSubject = row.getCell(3).getStringCellValue();
				if (nameSubject.trim().equals("")) {
					break;
				}
				String nameTeacher = row.getCell(13).getRichStringCellValue().getString();

				if (nameTeacher.trim() == "") {
					continue;
				}
				int numberStudent = (int) row.getCell(10).getNumericCellValue();
				String[] listTeacher = nameTeacher.split(",");
				for (int k = 0; k < listTeacher.length; k++) {
					int idSubject = (int) row.getCell(2).getNumericCellValue();
					String teacher = listTeacher[k].trim();
					int quantityStudent = numberStudent / 2;

					if (quantityStudent % 2 != 0 && i == 1) {
						quantityStudent += 1;
					}
					if (quantityStudent == 0) {
						quantityStudent = 40;
					}

					Subject monHoc = new Subject(idSubject, nameSubject, teacher, quantityStudent, nameClass);
					list.add(monHoc);

				}
			}

		}

		file.close();
		wb.close();

		return list;
	}
	
}
