import java.util.Scanner;
import domain.Group;
import domain.Group.DayPeriod;
import domain.Group.Type;

public class GroupDriver {
	private static Group g;
	
	private static void menu() {
		System.out.print(
	            "Group Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 0| Salir\n"
	            + " 1| new Group\n"
	            + " 2| getCode\n"
	            + " 3| getDayPeriod\n"
	            + " 4| getLectureDuration\n"
	            + " 5| getNumberOfLectures\n"
	            + " 6| getNumPeople\n"
	            + " 7| getParentGroupCode\n"
	            + " 8| getSubject\n"
	            + " 9| getType\n"
	            + "10| toString\n"
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
	            	testGetCode(sc);
	                break;
	            case 3:
	            	testGetDayPeriod(sc);
	                break;
	            case 4:
	            	testGetLectureDuration(sc);
	                break;
	            case 5:
	            	testGetNumberOfLectures(sc);
	                break;
	            case 6:
	            	testGetNumPeople(sc);
	                break;
	            case 7:
	            	testGetParentGroupCode(sc);
	                break;
	            case 8:
	            	testGetSubject(sc);
	                break;
	            case 9:
	            	testGetType(sc);
	                break;
	            case 10:
	            	testToString(sc);
	                break;
	        }
	        //menu();
	        n = sc.nextInt();
	    }
	}
	
		public static void testConstructor(Scanner sc){
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
		    } catch (Exception e) {
		        System.out.println(e);
		    }
		}
		
		public static void testGetCode(Scanner sc){
			try {
	            String x = g.getCode();
	            System.out.println("Codigo: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetDayPeriod(Scanner sc){
			try {
	            DayPeriod x = g.getDayPeriod();
	            System.out.println("Periodo del dia: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetLectureDuration(Scanner sc){
			System.out.println(">Introduzca numero de Lecture");
			try {
				while (!sc.hasNextInt()) {
		        	System.out.println("Error: Se ncecesita un entero");
		            sc.nextLine();
		        }
				Integer i = sc.nextInt();
	            Integer x = g.getLectureDuration(i);
	            System.out.println("Duracion Lecture " + i + ": " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetNumberOfLectures(Scanner sc){
			try {
	            Integer x = g.getNumberOfLectures();
	            System.out.println("Num Lectures: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetNumPeople(Scanner sc){
			try {
	            Integer x = g.getNumPeople();
	            System.out.println("Num. Personas: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetParentGroupCode(Scanner sc){
			try {
	            String x = g.getParentGroupCode();
	            System.out.println("Grupo Padre: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetSubject(Scanner sc){
			try {
	            String x = g.getSubject();
	            System.out.println("Asignatura: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetType(Scanner sc){
			try {
	            Type x = g.getType();
	            System.out.println("Tipo: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testToString(Scanner sc){
			try {
	            String x = g.toString();
	            System.out.println("String: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
	
	}
