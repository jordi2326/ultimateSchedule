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
import domain.classes.Room;

public class PosAssigDriver {
	private static Scanner sc;
	private static PosAssig p;
	
	private static void printMain() {
		System.out.print(
	            "PosAssig Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| Cargar PosAssig de un archivo\n"
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
	    		loadRoomMenu();
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
	
	public static void loadRoomMenu(){
		String path = "data/driverTests/posAssigs/";
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
	    	Map<Integer, Map< Integer, Set<String>>> m1 = new HashMap<Integer, Map< Integer, Set<String>>>();
	    	String s;
	    	s = in.next(); //>
	    	while(!s.equals("<")) {
    			Map<Integer, Set<String>> m2 = new HashMap<Integer, Set<String>>();
	    		Integer m1_k = in.nextInt();
	    		s = in.next(); //:
	    		while(!s.equals(">") && !s.equals("<")) {
	    			Set<String> set = new HashSet<String>();
		    		Integer m2_k = in.nextInt();
		    		s = in.next(); //:
		    		while(!s.equals(":") && !s.equals(">") && !s.equals("<")) {
		    			set.add(s);
		    			s = in.next(); //:
		    		}
	    			m2.put(m2_k, set);
	    		}
    			m1.put(m1_k, m2);
	    	}
	    	p = new PosAssig(m1);
	    	in.close();
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
		printSubMenu();
	    n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	            case 1:
	            	testDayIsEmpty();
	                break;
	            case 2:
	            	//testGetMap();
	                break;
	            case 3:
	            	testHasDay();
	                break;
	            case 4:
	            	testHasHourFromDay();
	                break;
	            case 5:
	            	//testHasRoomFromDayAndHour();
	                break;
	            case 6:
	            	testHasNoDays();
	                break;
	            case 7:
	            	testRemoveDay();
	                break;
	            case 8:
	            	testRemoveHourFromDay();
	                break;
	            case 9:
	            	//testRemoveRoomFromHourAndDay();
	                break;
	        }
	        n = sc.nextInt();
	    }
	}
	
	public static void testHasDay(){
		System.out.println(">Introduzca Num de Dia");
		try {
            Boolean x = p.hasDay(sc.nextInt());
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testHasHourFromDay(){
		System.out.println(">Introduzca Num de Dia");
		Integer d = sc.nextInt();
		System.out.println(">Introduzca Num de Hora");
		Integer h = sc.nextInt();
		try {
            Boolean x = p.hasHourFromDay(d, h);
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testDayIsEmpty(){
		System.out.println(">Introduzca Num de Dia");
		try {
            Boolean x = p.dayIsEmpty(sc.nextInt());
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
	
	public static void testRemoveDay(){
		System.out.println(">Introduzca Num de Dia");
		try {
            p.removeDay(sc.nextInt());
            System.out.println("Dia eliminado");
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void testRemoveHourFromDay(){
		System.out.println(">Introduzca Num de Dia");
		Integer d = sc.nextInt();
		System.out.println(">Introduzca Num de Hora");
		Integer h = sc.nextInt();
		try {
            p.removeHourFromDay(d,h);
            System.out.println("Hora eliminada");
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	}