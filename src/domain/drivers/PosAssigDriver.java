package domain.drivers;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import domain.classes.PosAssig;

/**
 * @author Carlos Bergillos Varela
*/

public class PosAssigDriver {
	private static Scanner sc;
	private static PosAssig p;
	private static boolean silent = false;
	
	private static void printMain() {
		System.out.print(
	            "PosAssig Driver\n"
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
		String path = "data/driverTests/posAssig/";
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
	    	Map<Integer, Map< Integer, Set<String>>> m1 = new HashMap<Integer, Map< Integer, Set<String>>>();
	    	String s;
	    	s = sc.next(); //>
	    	while(!s.equals("<")) {
    			Map<Integer, Set<String>> m2 = new HashMap<Integer, Set<String>>();
	    		Integer m1_k = sc.nextInt();
	    		s = sc.next(); //:
	    		while(!s.equals(">") && !s.equals("<")) {
	    			Set<String> set = new HashSet<String>();
		    		Integer m2_k = sc.nextInt();
		    		s = sc.next(); //:
		    		while(!s.equals(":") && !s.equals(">") && !s.equals("<")) {
		    			set.add(s);
		    			s = sc.next(); //:
		    		}
	    			m2.put(m2_k, set);
	    		}
    			m1.put(m1_k, m2);
	    	}
	    	p = new PosAssig(m1);
	    	silent = true;
	    	subMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printSubMenu() {
		System.out.print(
	            "PosAssig Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| dayIsEmpty\n"
	            + " 2| getMap\n"
	            + " 3| hasDay\n"
	            + " 4| hasHourFromDay\n"
	            + " 5| hasNoDays\n"
	            + " 6| hasRoomFromDayAndHour\n"
	            + " 7| removeDay\n"
	            + " 8| removeHourFromDay\n"
	            + " 9| removeRoomFromHourAndDay\n"
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
	            	testDayIsEmpty();
	                break;
	            case 2:
	            	testGetMap();
	                break;
	            case 3:
	            	testHasDay();
	                break;
	            case 4:
	            	testHasHourFromDay();
	                break;
	            case 5:
	            	testHasNoDays();
	                break;
	            case 6:
	            	testHasRoomFromDayAndHour();
	                break;
	            case 7:
	            	testRemoveDay();
	                break;
	            case 8:
	            	testRemoveHourFromDay();
	                break;
	            case 9:
	            	testRemoveRoomFromHourAndDay();
	                break;
	        }
	        n = sc.nextInt();
	    }
	}
	
	public static void testDayIsEmpty(){
		if(!silent) System.out.println(">Introduzca Num de Dia");
		try {
            Boolean x = p.dayIsEmpty(sc.nextInt());
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testGetMap(){
		try {
            Map<Integer, Map<Integer, Set<String>>> x = p.getMap();
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testHasDay(){
		if(!silent) System.out.println(">Introduzca Num de Dia");
		try {
            Boolean x = p.hasDay(sc.nextInt());
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testHasHourFromDay(){
		if(!silent) System.out.println(">Introduzca Num de Dia");
		Integer d = sc.nextInt();
		if(!silent) System.out.println(">Introduzca Num de Hora");
		Integer h = sc.nextInt();
		try {
            Boolean x = p.hasHourFromDay(d, h);
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testHasNoDays(){
		try {
			Boolean x = p.hasNoDays();
	        System.out.println(x);
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}
	
	public static void testHasRoomFromDayAndHour(){
		if(!silent) System.out.println(">Introduzca Num de Dia");
		Integer d = sc.nextInt();
		if(!silent) System.out.println(">Introduzca Num de Hora");
		Integer h = sc.nextInt();
		if(!silent) System.out.println(">Introduzca Codigo del aula");
		String r = sc.next();
		try {
            Boolean x = p.hasRoomFromDayAndHour(d, h, r);
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testRemoveDay(){
		if(!silent) System.out.println(">Introduzca Num de Dia");
		try {
            p.removeDay(sc.nextInt());
            System.out.println("Dia eliminado");
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testRemoveHourFromDay(){
		if(!silent) System.out.println(">Introduzca Num de Dia");
		Integer d = sc.nextInt();
		if(!silent) System.out.println(">Introduzca Num de Hora");
		Integer h = sc.nextInt();
		try {
            p.removeHourFromDay(d,h);
            System.out.println("Hora eliminada");
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testRemoveRoomFromHourAndDay(){
		if(!silent) System.out.println(">Introduzca Num de Dia");
		Integer d = sc.nextInt();
		if(!silent) System.out.println(">Introduzca Num de Hora");
		Integer h = sc.nextInt();
		if(!silent) System.out.println(">Introduzca Codigo del aula");
		String r = sc.next();
		try {
            p.removeRoomFromHourAndDay(d,h,r);
            System.out.println("Aula eliminada");
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
}