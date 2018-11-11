package domain.drivers;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.classes.Room;

public class RoomDriver {
	private static Scanner sc;
	private static Room r;
	
	private static void printMain() {
		System.out.print(
	            "Room Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| Cargar Room de un archivo\n"
	            + " 2| Definir Room manualmente\n"
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
	    		loadRoomMenu();
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
	
	public static void loadRoomMenu(){
		String path = "data/driverTests/roomDriver/";
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
	    	r = new Room(in.next(), in.nextInt(), in.nextBoolean());
	    	in.close();
	    	subMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void newRoomMenu() {
		try {
	    	System.out.println("Definir nueva Aula");
	    	System.out.println(">Introduzca Codigo del Aula");
	        String code = sc.next();
	        
	        System.out.println(">Introduzca Capacidad");
	        while (!sc.hasNextInt()) {
	        	System.out.println("Error: Se ncecesita un entero");
	            sc.nextLine();
	        }
	        Integer capacity = sc.nextInt();
	        
	        System.out.println(">Introduzca si tiene ordenadores [S/N]");
	        
	        Boolean hasComputers = false;
	        String b = sc.next().trim().toLowerCase();
	        while(!b.equals("s") && !b.equals("n")) {
	        	System.out.println("Error: Introduzca 'S' o 'N'");
	        	b = sc.next().trim().toLowerCase();
	        }
	        if(b.equals("s")) hasComputers = true;
	        
	        r = new Room(code, capacity, hasComputers);
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
	            + " 0| Salir\n"
	            + " 1| getCapacity\n"
	            + " 2| getCode\n"
	            + " 3| hasComputers\n"
	            + " 4| toString\n"
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
	            	testGetCapacity();
	                break;
	            case 2:
	            	testGetCode();
	                break;
	            case 3:
	            	testHasComputers();
	                break;
	            case 4:
	            	testToString();
	                break;
	        }
	        n = sc.nextInt();
	    }
	}
		public static void testGetCapacity(){
			try {
	            Integer x = r.getCapacity();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetCode(){
			try {
	            String x = r.getCode();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testHasComputers(){
			try {
	            Boolean x = r.hasComputers();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testToString(){
			try {
	            String x = r.toString();
	            System.out.println(x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
	
	}
