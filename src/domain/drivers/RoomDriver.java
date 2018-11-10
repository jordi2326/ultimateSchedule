package domain.drivers;
import java.util.Scanner;
import domain.Room;

public class RoomDriver {
	private static Room r;
	
	private static void menu() {
		System.out.print(
	            "Room Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 0| Salir\n"
	            + " 1| new Room\n"
	            + " 2| getCapacity\n"
	            + " 3| getCode\n"
	            + " 4| hasComputers\n"
	            + " 5| toString\n"
	            + "---------------------\n"
	            );
	                    
    }
	
	public static void main (String [] args) {
		Scanner sc = new Scanner(System.in);
	
		int n;
	    menu();
	    n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	            case 1:
	            	testConstructor(sc);
	                break;
	            case 2:
	            	testGetCapacity(sc);
	                break;
	            case 3:
	            	testGetCode(sc);
	                break;
	            case 4:
	            	testHasComputers(sc);
	                break;
	            case 5:
	            	testToString(sc);
	                break;
	        }
	        //menu();
	        n = sc.nextInt();
	    }
	}
	
		public static void testConstructor(Scanner sc){
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
		    } catch (Exception e) {
		        System.out.println(e);
		    }
		}
		
		public static void testGetCapacity(Scanner sc){
			try {
	            Integer x = r.getCapacity();
	            System.out.println("Capacidad: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetCode(Scanner sc){
			try {
	            String x = r.getCode();
	            System.out.println("Codigo: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testHasComputers(Scanner sc){
			try {
	            Boolean x = r.hasComputers();
	            System.out.println("Tiene ordenadores: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testToString(Scanner sc){
			try {
	            String x = r.toString();
	            System.out.println("String: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
	
	}
