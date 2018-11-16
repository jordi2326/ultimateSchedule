package domain.drivers;
import java.util.Scanner;

import domain.classes.Group;
import domain.classes.Restriction;
import domain.classes.restrictions.CorequisitRestriction;
import domain.classes.restrictions.DayPeriodRestriction;
import domain.classes.restrictions.LectureFromSameGroupOverlapRestriction;
import domain.classes.restrictions.OccupiedRoomRestriction;
import domain.classes.restrictions.ParentGroupOverlapRestriction;
import domain.classes.restrictions.SpecificDayOrHourRestriction;
import domain.classes.restrictions.SubjectLevelRestriction;


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
	            + " 6| validate\n"
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
	            case 6:
	            	testValidate(sc);
	            	break;
	        }
	        n = sc.nextInt();
	    }
	}
	
	public static void testValidate(Scanner sc) {
		
	}
	
		public static void listRestrictions() {
			System.out.println(
					"---------------------\n"
		            + "Opciones\n"
		            + " 1| CorequisitRestriction\n"
		            + " 2| DayPeriodRestriction\n"
		            + " 3| LectureFromSameGroupOverlap\n"
		            + " 4| OccupiedRoomRestriction\n"
		            + " 5| ParentGroupOverlapRestriction\n"
		            + " 6| SpecificDayOrHourRestriction"
		            + " 7| SubjectLevelRestriction"
		            + " 0| Salir\n"
		            + "---------------------\n"
		            );
		}
	
		public static void testConstructor(Scanner sc){
		    try {
		    	System.out.println("Escoger restriccion a crear. Solo se guarda la ultima restriccion creada.\n");
		    	listRestrictions();
		    	int n;
			    n = sc.nextInt();
			    while (n != 0) {
			        switch (n) {
			            case 1:
			            	r = new CorequisitRestriction();
					        System.out.println("Nueva Restriccion ( " + r.toString() + " ) definida\n");
			                break;
			            case 2:
			            	System.out.println("Introduzca la hora que quiere usar para el mediodia:\n");
			            	Integer midDay = sc.nextInt();
			            	Group.DayPeriod dp = null;
			            	System.out.println("Escoja el periodo de clases del grupo:\n"
			            			+"---------------------\n"
			    		            + "Opciones\n"
			    		            + " 1| MORNING\n"
			    		            + " 2| AFTERNOON\n"
			    		            + " 3| INDIFERENT\n"
			    		            + "---------------------\n");
			            	int o;
			            	o = sc.nextInt();
			            	switch (o) {
			            		case 1:
			            			dp = Group.DayPeriod.MORNING;
			            			break;
			            		case 2:
			            			dp = Group.DayPeriod.AFTERNOON;
			            			break;
			            		case 3:
			            			dp = Group.DayPeriod.INDIFERENT;
			            			break;
			            	}
			            	r = new DayPeriodRestriction(midDay, dp);
			                break;
			            case 3:
			            	r = new LectureFromSameGroupOverlapRestriction();
			            	System.out.println("Nueva Restriccion ( " + r.toString() + " ) definida\n");
			                break;
			            case 4:
			            	r = new OccupiedRoomRestriction();
			            	System.out.println("Nueva Restriccion ( " + r.toString() + " ) definida\n");
			                break;
			            case 5:
			            	r = new ParentGroupOverlapRestriction();
			            	System.out.println("Nueva Restriccion ( " + r.toString() + " ) definida\n");
			                break;
			            case 6:
			            	System.out.println("Introduzca dia y/o hora en este orden.\n"
            						+"en caso de solo dia o hora, introduzca -1 en el campo que no requiera.\n");
			            	Integer day, hour;
			            	day = sc.nextInt();
			            	if (day.equals(-1)) day = null;
			            	hour = sc.nextInt();
			            	if (hour.equals(-1)) hour = null;
			            	r = new SpecificDayOrHourRestriction(day, hour);
					        System.out.println("Nueva Restriccion ( " + r.toString() + " ) definida\n");	
					        break;
			            case 7:
			            	r = new SubjectLevelRestriction();
			            	System.out.println("Nueva Restriccion ( " + r.toString() + " ) definida\n");
			            	break;
			        }
			    }
		    } catch (Exception e) {
		        System.out.println(e);
		    }
		}
		
		public static void testDisable(Scanner sc){
			try {
	            if (r.disable()) System.out.println("La restriccion ( " + r.toString() + " ) ha sido desactivada.\n");
	            else System.out.println("La restriccion ( " + r.toString() + " ) no se puede desactivar porque no es negociable.\n");
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testEnable(Scanner sc){
			try {
	            if (r.enable()) System.out.println("La restriccion ( " + r.toString() + " ) ha sido activada.\n");
	            else System.out.println("La restriccion ( " + r.toString() + " ) esta siempre activada porque no es negociable.\n");
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		
		public static void testIsEnabled(Scanner sc){
			try {
	            Boolean x = r.isEnabled();
	            if (x) System.out.println("La restriccion ( " + r.toString() + " ) esta ACTIVADA.\n");
	            else System.out.println("La restriccion ( " + r.toString() + " ) esta DESACTIVADA.\n");
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
		public static void testIsNegotiable(Scanner sc){
			try {
	            Boolean x = r.isNegotiable();
	            if (x) System.out.println("La restriccion ( \" + r.toString() + \" ) es negociable.\n");
	            else  System.out.println("La restriccion ( \" + r.toString() + \" ) NO es negociable.\n");
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		}
		
	
	}
