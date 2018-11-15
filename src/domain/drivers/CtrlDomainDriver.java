package domain.drivers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.parser.ParseException;
import domain.controllers.CtrlDomain;

public class CtrlDomainDriver {
	private static Scanner sc;
	private static CtrlDomain cD;
	
	private static void menu() {
		System.out.print(
	            "Subject Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 1| exportSchedule\n"
	            + " 2| generateSchedule\n"
	            + " 3| getEnvironmentFileList\n"
	            + " 4| getRoomNamesList\n"
	            + " 5| getScheduleFileList\n"
	            + " 6| getSubjectNamesList\n"
	            + " 7| importEnvironment\n"
	            + " 8| importSchedule\n"
	            + " 9| scheduleToJsonString\n"
	            + " 0| Salir\n"
	            + "---------------------\n"
	            );
	                    
    }
	
	public static void main (String [] args) {
		sc = new Scanner(System.in);
		cD = CtrlDomain.getInstance();
		
		int n;
	    menu();
	    n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	        	case 1:
	        		testExportSchedule();
	        		break;
	        	case 2:
	        		testGenerateSchedule();
	        		break;
	        	case 3:
	        		testGetEnvironmentFileList();
	        		break;
	        	case 4:
	        		testGetRoomNamesList();
	        		break;
	        	case 5:
	        		testGetScheduleFileList();
	        		break;
	        	case 6:
	        		testGetSubjectNamesList();
	        		break;
	        	case 7:
	        		testImportEnvironment();
	        		break;
	        	case 8:
	        		testImportSchedule();
	        		break;
	        	case 9:
	        		testScheduleToJsonString();
	        		break;
	        }
	        n = sc.nextInt();
	    }
	}
	
	private static void testExportSchedule() {
		try {
			String filename = "driverOutputSch.json";
            cD.exportSchedule(filename);
            System.out.println("Exported schedule to: "+filename);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private static void testGenerateSchedule() {
		try {
            cD.generateSchedule();
            System.out.println("Schedule generated");
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private static void testGetEnvironmentFileList() {
		try {
            ArrayList<String> x = (ArrayList<String>) cD.getEnvironmentFilesList();
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private static void testGetRoomNamesList() {
		try {
            ArrayList<String> x = cD.getRoomNamesList();
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private static void testGetScheduleFileList() {
		try {
            ArrayList<String> x = (ArrayList<String>) cD.getScheduleFilesList();
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private static void testGetSubjectNamesList() {
		try {
            ArrayList<String> x = cD.getSubjectNamesList();
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	
	private static void testImportEnvironment() {
		try {
			String filename = "basic.json";
			cD.importEnvironment(filename);
			System.out.println("Imported "+filename);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void testImportSchedule() {
		try {
			String filename = "scheduleA.json";
			cD.importSchedule(filename);
			System.out.println("Imported "+filename);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void testScheduleToJsonString() {
		try {
			String x = cD.scheduleToJsonString();
			System.out.println(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}