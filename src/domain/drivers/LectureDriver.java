package domain.drivers;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.classes.Lecture;

public class LectureDriver {
	private static Scanner sc;
	private static Lecture l;
	
	private static void printMain() {
		System.out.print(
	            "Lecture Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| Cargar Lecture de un archivo\n"
	            + " 2| Definir Lecture manualmente\n"
	            + " 0| Salir\n"
	            + "---------------------\n"
	            );
	                    
    }
	
	public static void main (String [] args) throws Exception {
		sc = new Scanner(System.in);
		int n;
	    printMain();
	    n = sc.nextInt();
	    switch (n) {
	    	case 1:
	    		loadLectureMenu();
	            break;
	        case 2:
	        	newRoomMenu();
	            break;
	    }
	}
	
	private static void printLoadFileMenu(List<String> filenames) {
		System.out.print(
	            "Cargar Archivo\n"
	            + "--------------------------\n");
		for (int i = 0; i < filenames.size(); i++) {
			System.out.println(i + "| "+ filenames.get(i));
		}
		
		System.out.print("--------------------------\n");
    }
	
	public static void loadLectureMenu(){
		String path = "data/driverTests/lectures/";
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
	    	Scanner in = new Scanner(new FileReader(new File(path+filename)));
	    	l = new Lecture(in.nextInt(), in.next(), in.nextInt());
	    	in.close();
	    	subMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void newRoomMenu() {
		try {
	    	System.out.println("Definir nueva Lecture");
	    	System.out.println(">Introduzca Id de la Lecture");
	        while (!sc.hasNextInt()) {
	        	System.out.println("Error: Se ncecesita un entero");
	            sc.nextLine();
	        }
	        Integer id = sc.nextInt();
	        
	        System.out.println(">Introduzca Codigo del Grupo");
	        String group = sc.next();
	        
	        System.out.println(">Introduzca Duracion");
	        while (!sc.hasNextInt()) {
	        	System.out.println("Error: Se ncecesita un entero");
	            sc.nextLine();
	        }
	        Integer duration = sc.nextInt();
	        
	        l = new Lecture(id, group, duration);
	        System.out.println("Nueva Aula definida");
	        subMenu();
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}
	
	private static void printSubMenu() {
		System.out.print(
	            "Room Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| getDuration\n"
	            + " 2| getGroup\n"
	            + " 0| Salir\n"
	            + "---------------------\n"
	            );
	                    
    }
	
	public static void subMenu() {
		int n;
		printSubMenu();
	    n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	            case 1:
	            	testGetDuration();
	                break;
	            case 2:
	            	testGetGroup();
	                break;
	        }
	        n = sc.nextInt();
	    }
	}
		public static void testGetDuration(){
			try {
	            Integer x = l.getDuration();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetGroup(){
			try {
	            String x = l.getGroup();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
	
	}