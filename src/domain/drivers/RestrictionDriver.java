package domain.drivers;
import java.util.Scanner;

import domain.classes.Restriction;
import domain.classes.Room;
import domain.drivers.stubs.SubclassRestrictionStub;

public class RestrictionDriver {
	private static Restriction r;
	
	private static void menu() {
		System.out.print(
	            "Room Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| new Restriction\n"
	            + " 2| disable\n"
	            + " 3| enable\n"
	            + " 4| isEnabled\n"
	            + " 5| isNegotiable\n"
	            + " 0| Salir\n"
	            + "---------------------\n"
	            );
	                    
    }
	
	public static void main (String [] args) throws Exception {
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
	            	testDisable(sc);
	                break;
	            case 3:
	            	testEnable(sc);
	                break;
	            case 4:
	            	testIsEnabled(sc);
	                break;
	            case 5:
	            	testIsNegotiable(sc);
	                break;
	        }
	        //menu();
	        n = sc.nextInt();
	    }
	}
	
		public static void testConstructor(Scanner sc){
		    try {
		    	System.out.println("Definir nueva Restriccion");
		        System.out.println(">Introduzca si es negociable [S/N]");
		        
		        Boolean negotiable = false;
		        String b = sc.next().trim().toLowerCase();
		        while(!b.equals("s") && !b.equals("n")) {
		        	System.out.println("Error: Introduzca 'S' o 'N'");
		        	b = sc.next().trim().toLowerCase();
		        }
		        if(b.equals("s")) negotiable = true;
		        
		        r = (Restriction) new SubclassRestrictionStub(negotiable);
		        System.out.println("Nueva Restriccion definida");
		    } catch (Exception e) {
		        System.out.println(e);
		    }
		}
		
		public static void testDisable(Scanner sc){
			try {
	            r.disable();
	            System.out.println("Disabled");
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testEnable(Scanner sc){
			try {
	            r.enable();
	            System.out.println("Enabled");
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		
		public static void testIsEnabled(Scanner sc){
			try {
	            Boolean x = r.isEnabled();
	            System.out.println("is Enabled: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testIsNegotiable(Scanner sc){
			try {
	            Boolean x = r.isNegotiable();
	            System.out.println("is Negotiable: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
	
	}