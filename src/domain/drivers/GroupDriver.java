package domain.drivers;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.classes.Group;
import domain.classes.Group.DayPeriod;
import domain.classes.Group.Type;

public class GroupDriver {
	private static Scanner sc;
	private static Group g;
	
	private static void printMain() {
		System.out.print(
	            "Group Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| Cargar Group de un archivo\n"
	            + " 2| Definir Group manualmente\n"
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
	    		loadGroupMenu();
	            break;
	        case 2:
	        	newGroupMenu();
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
	
	public static void loadGroupMenu(){
		String path = "data/driverTests/groups/";
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
	    	g = new Group(in.next(), in.nextInt(), in.next(), in.next(), Type.valueOf(in.next()), DayPeriod.valueOf(in.next()), readStringList(in));
	    	in.close();
	    	subMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<String> readStringList(Scanner in){
		ArrayList<String> l = new ArrayList<String>();
		l.add(in.next());
		return l;
	}

	private static void newGroupMenu() {
		try {
	    	System.out.println("Definir nuevo Grupo");
	    	System.out.println(">Introduzca Codigo del Grup");
	        String code = sc.next();
	        
	        System.out.println(">Introduzca Num. de personas");
	        while (!sc.hasNextInt()) {
	        	System.out.println("Error: Se ncecesita un entero");
	            sc.nextLine();
	        }
	        Integer numPeople = sc.nextInt();
	        
	        System.out.println(">Introduzca Codigo de la Asignatura");
	        String subject = sc.next();
	        
	        System.out.println(">Introduzca Tipo de classe");
	        Type[] types = Type.values();
	        Integer int_type = 0;
	        do {
	        	System.out.println("Valores disponibles:");
	        	for (Type t : types) System.out.println(t.ordinal()+"| "+t.toString());
	        	while (!sc.hasNextInt()) {
		            sc.nextLine();
		            System.out.println("Error: Se ncecesita un entero");
		        }
	        	int_type = sc.nextInt();
	        	
	        }while (int_type < 0 || int_type>=types.length);
	        Type type = Type.values()[int_type];
	        
	        System.out.println(">Introduzca Periodo del dia");
	        DayPeriod[] dayPeriods = DayPeriod.values();
	        Integer int_dayPeriod = 0;
	        do {
	        	System.out.println("Valores disponibles:");
	        	for (DayPeriod t : dayPeriods) System.out.println(t.ordinal()+"| "+t.toString());
	        	while (!sc.hasNextInt()) {
		            sc.nextLine();
		            System.out.println("Error: Se ncecesita un entero");
		        }
	        	int_type = sc.nextInt();
	        	
	        }while (int_type < 0 || int_type>=types.length);
	        DayPeriod dayPeriod = DayPeriod.values()[int_dayPeriod];
	        
	        g = new Group(code, numPeople, "", subject, type, dayPeriod, null);
	        System.out.println("Nuevo Grupo definido");
	        subMenu();
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}
	
	private static void printSubMenu() {
		System.out.print(
	            "Group Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| getCode\n"
	            + " 2| getDayPeriod\n"
	            + " 3| getLectures\n"
	            + " 4| getNumOfPeople\n"
	            + " 5| getParentGroupCode\n"
	            + " 6| getSubject\n"
	            + " 7| getType\n"
	            + " 8| toString\n"
	            + " 0| Salir\n"
	            + "---------------------\n"
	            );
	                    
    }
	
	public static void subMenu () {
		int n;
        printSubMenu();
	    n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	            case 1:
	            	testGetCode();
	                break;
	            case 2:
	            	testGetDayPeriod();
	                break;
	            case 3:
	            	testGetLectures();
	                break;
	            case 4:
	            	testGetNumOfPeople();
	                break;
	            case 5:
	            	testGetParentGroupCode();
	                break;
	            case 6:
	            	testGetSubject();
	                break;
	            case 7:
	            	testGetType();
	                break;
	            case 8:
	            	testToString();
	                break;
	        }
	        n = sc.nextInt();
	    }
	}
		
		public static void testGetCode(){
			try {
	            String x = g.getCode();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetDayPeriod(){
			try {
	            DayPeriod x = g.getDayPeriod();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetLectures(){
			try {
	            ArrayList<String> x = g.getLectures();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetNumOfPeople(){
			try {
	            Integer x = g.getNumOfPeople();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetParentGroupCode(){
			try {
	            String x = g.getParentGroupCode();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetSubject(){
			try {
	            String x = g.getSubject();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetType(){
			try {
	            Type x = g.getType();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}

		public static void testToString(){
			try {
	            String x = g.toString();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
	
	}
