import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import domain.Subject;

public class SubjectDriver {
	private static Subject s;
	
	private static void menu() {
		System.out.print(
	            "Subject Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 0| Salir\n"
	            + " 1| new Subject\n"
	            + " 2| getAllCorequisits\n"
	            + " 3| getAllGroups\n"
	            + " 4| getCode\n"
	            + " 5| getLevel\n"
	            + " 6| getName\n"
	            + " 7| toString\n"
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
	            	testGetAllCorequisits(sc);
	                break;
	            case 3:
	            	testGetAllGroups(sc);
	                break;
	            case 4:
	            	testGetCode(sc);
	                break;
	            case 5:
	            	testGetLevel(sc);
	                break;
	            case 6:
	            	testGetName(sc);
	                break;
	            case 7:
	            	testToString(sc);
	                break;
	        }
	        //menu();
	        n = sc.nextInt();
	    }
	}
	
		public static void testConstructor(Scanner sc){
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
		    } catch (Exception e) {
		        System.out.println(e);
		    }
		}
		
		public static void testGetAllCorequisits(Scanner sc){
			try {
	            ArrayList<String> x = s.getAllCorequisits();
	            System.out.println("Corequisits: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetAllGroups(Scanner sc){
			try {
	            ArrayList<String> x = s.getAllGroups();
	            System.out.println("Groups: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetCode(Scanner sc){
			try {
	            String x = s.getCode();
	            System.out.println("Codigo: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetLevel(Scanner sc){
			try {
	            String x = s.getLevel();
	            System.out.println("Nivel: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testGetName(Scanner sc){
			try {
	            String x = s.getName();
	            System.out.println("Nombre: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testToString(Scanner sc){
			try {
	            String x = s.toString();
	            System.out.println("String: " + x);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
	
	}