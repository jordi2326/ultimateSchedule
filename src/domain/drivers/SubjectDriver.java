package domain.drivers;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import domain.classes.Room;
import domain.classes.Subject;

public class SubjectDriver {
	private static Scanner sc;
	private static Subject s;
	
	private static void printMain() {
		System.out.print(
	            "Subject Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| Cargar Subject de un archivo\n"
	            + " 2| Definir Subject manualmente\n"
	            + " 0| Salir\n"
	            + "---------------------\n"
	            );
	                    
    }
	
	public static void main (String [] args) {
		sc = new Scanner(System.in);
		int n;
	    printMain();
	    n = sc.nextInt();
	    switch (n) {
	    	case 1:
	    		loadSubjectMenu();
	            break;
	        case 2:
	        	newSubjectMenu();
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
	
	public static void loadSubjectMenu(){
		String path = "data/driverTests/subjects/";
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
	    	s = new Subject(in.nextLine(), in.nextLine(), in.nextLine(), readStringList(in), readStringList(in));
	    	in.close();
	    	subMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<String> readStringList(Scanner in){
		ArrayList<String> l = new ArrayList<String>();
		String s = in.next();
		while(!s.equals("-1")) {
			l.add(s);
			s = in.next();
		}
		return l;
	}
	
	public static void newSubjectMenu(){
	    try {
	    	System.out.println("Definir nueva Asignatura");
	    	System.out.println(">Introduzca Codigo");
	        String code = sc.next();
	        
	        System.out.println(">Introduzca Nombre");
	        String name = sc.next();
	        
	        System.out.println(">Introduzca Nivel");
	        String level = sc.next();
	        
	        ArrayList<String> groups = new ArrayList<String>(Arrays.asList(code+"10THEORY", code+"11LABORATORY", code+"20THEORY", code+"21LABORATORY")); //stub
	        ArrayList<String> coreqs = new ArrayList<String>(Arrays.asList("M1", "PROP", "IA")); //stub
	        s = new Subject(code, name, level, groups, coreqs);
	        System.out.println("Nueva Asignatura definida");
	        subMenu();
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}
	
	private static void printSubMenu() {
		System.out.print(
	            "Subject Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| getCode\n"
	            + " 2| getCoreqs\n"
	            + " 3| getGroups\n"
	            + " 4| getLevel\n"
	            + " 5| getName\n"
	            + " 6| toString\n"
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
	            	testGetCode();
	                break;
	            case 2:
	            	testGetCoreqs();
	                break;
	            case 3:
	            	testGetGroups();
	                break;
	            case 4:
	            	testGetLevel();
	                break;
	            case 5:
	            	testGetName();
	                break;
	            case 6:
	            	testToString();
	                break;
	        }
	        n = sc.nextInt();
	    }
	}
	
		public static void testGetCoreqs(){
			try {
	            ArrayList<String> x = s.getCoreqs();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetGroups(){
			try {
	            ArrayList<String> x = s.getGroups();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetCode(){
			try {
	            String x = s.getCode();
	            System.out.println( x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetLevel(){
			try {
	            String x = s.getLevel();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetName(){
			try {
	            String x = s.getName();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testToString(){
			try {
	            String x = s.toString();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
	
	}