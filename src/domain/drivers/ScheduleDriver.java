package domain.drivers;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.classes.Lecture;
import domain.classes.Schedule;

public class ScheduleDriver {
	private static Scanner sc;
	private static Schedule sch;

	private static void printMain() {
		System.out.print(
				"Schedule Driver\n" 
						+ "---------------------\n" 
						+ "Opciones\n" 
						+ " 1| Cargar Schedule de un archivo\n"
						+ " 2| Empezar con Schedule vacio\n"
						+ " 0| Salir\n" + "---------------------\n");

	}

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		int n;
		printMain();
		n = sc.nextInt();
		switch (n) {
		case 1:
			loadScheduleMenu();
			break;
		case 2:
			sch = new Schedule();
			subMenu();
			break;
		}
	}

	private static void printLoadFileMenu(List<String> filenames) {
		System.out.print("Cargar Archivo\n" + "--------------------------\n");
		for (int i = 0; i < filenames.size(); i++) {
			System.out.println(i + "| " + filenames.get(i));
		}

		System.out.print("--------------------------\n");
	}

	public static void loadScheduleMenu() {
		
		String path = "data/schedules/";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<String> filenames = new ArrayList<String>();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				filenames.add(file.getName());
			}
		}
		printLoadFileMenu(filenames);
		int n = sc.nextInt();
		try {
			String filename = filenames.get(n);
			Scanner in = new Scanner(new FileReader(new File(path + filename)));
			in.useDelimiter("\\Z");
			sch = new Schedule(in.next());
			in.close();
			subMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}

	private static void printSubMenu() {
		System.out.print("Schedule Driver\n" 
				+ "---------------------\n" 
				+ "Opciones\n" 
				+ " 1| putLecture\n"
				+ " 2| removeLecture\n"
				+ " 3| toJsonString\n" 
				+ " 0| Salir\n" + "---------------------\n");

	}

	public static void subMenu() {
		int n;
		printSubMenu();
		n = sc.nextInt();
		while (n != 0) {
			switch (n) {
			case 1:
				testPutLecture();
				break;
			case 2:
				testRemoveLecture();
				break;
			case 3:
				testToJsonString();
				break;
			}
			n = sc.nextInt();
		}
	}

	public static void testPutLecture() {
		try {
			System.out.println(">Introduzca codigo de Room");
			String room = sc.next();
			
			System.out.println(">Introduzca numero de dia [0-5]");
			while (!sc.hasNextInt()) {
				System.out.println("Error: Se ncecesita un entero");
				sc.nextLine();
			}
			Integer day = sc.nextInt();
			
			System.out.println(">Introduzca numero de hora [0-12]");
			while (!sc.hasNextInt()) {
				System.out.println("Error: Se ncecesita un entero");
				sc.nextLine();
			}
			Integer hour = sc.nextInt();
			
			System.out.println(">Introduzca codigo del Group a añadir");
			String group = sc.next();
			
			sch.putLecture(room, day, hour, group);
			System.out.println("Grupo añadido al horario");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void testRemoveLecture() {
		try {
			System.out.println(">Introduzca codigo de Room");
			String room = sc.next();
			
			System.out.println(">Introduzca numero de dia [0-5]");
			while (!sc.hasNextInt()) {
				System.out.println("Error: Se ncecesita un entero");
				sc.nextLine();
			}
			Integer day = sc.nextInt();
			
			System.out.println(">Introduzca numero de hora [0-12]");
			while (!sc.hasNextInt()) {
				System.out.println("Error: Se ncecesita un entero");
				sc.nextLine();
			}
			Integer hour = sc.nextInt();
			
			sch.removeLecture(room, day, hour);
			System.out.println("Grupo eliminado del horario");
		} catch (Exception e) {
			System.out.println(e);
			
		}
	}
	
	public static void testToJsonString(){
		try {
            String x = sch.toJsonString();
            System.out.println(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}