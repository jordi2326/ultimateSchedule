package domain.drivers;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import domain.classes.Subject;

/**
 * @author Carlos Bergillos Varela
*/

public class SubjectDriver {
	private static Scanner sc;
	private static Subject s;
	private static boolean silent = false;
	
	private static void printMain() {
		System.out.print(
	            "Subject Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| Test Automatico\n"
	            + " 2| Probar Manualmente\n"
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
	    		loadTestMenu();
	            break;
	        case 2:
	        	newSubjectMenu();
	            break;
	    }
	}
	
	private static void printLoadFileMenu(String title, List<String> filenames) {
		System.out.println(title);
		System.out.print("--------------------------\n");
		for (int i = 0; i < filenames.size(); i++) {
			System.out.println(i + "| "+ filenames.get(i));
		}
		
		System.out.print("--------------------------\n");
    }
	
	public static void loadTestMenu(){
		String path = "data/driverTests/subject/";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<String> filenames = new ArrayList<String>();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	filenames.add(file.getName());
		    }
		}
		printLoadFileMenu("Cargar Test", filenames);
	    int n = sc.nextInt();
	    try {
	    	String filename = filenames.get(n);
	    	sc = new Scanner(new FileReader(new File(path+filename)));
	    	s = new Subject(sc.nextLine(), sc.nextLine(), sc.nextLine(), readStringList(), readStringList());
	    	silent = true;
	    	subMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<String> readStringList(){
		ArrayList<String> l = new ArrayList<String>();
		String s = sc.next();
		while(!s.equals("-1")) {
			l.add(s);
			s = sc.next();
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
		if(!silent) printSubMenu();
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