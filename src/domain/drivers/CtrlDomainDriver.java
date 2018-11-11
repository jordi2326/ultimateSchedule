package domain.drivers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import domain.classes.Group;
import domain.classes.Room;
import domain.controllers.CtrlDomain;

public class CtrlDomainDriver {
	private static CtrlDomain cD;
	
	private static void menu() {
		System.out.print(
	            "Subject Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 0| Salir\n"
	            + " 1| getInstance\n"
	            + " 2| exportEnvironment\n"
	            + " 3| exportSchedule\n"
	            + " 4| getEnvironmentFileList\n"
	            + " 5| getGroup\n"
	            + " 6| getGroups\n"
	            + " 7| getGroupsFromSubject\n"
	            + " 8| getRoomNamesList\n"
	            + " 9| getRooms\n"
	            + "10| getSubject\n"
	            + "11| getSubjectNamesList\n"
	            + "12| importEnvironment\n"
	            + "13| importSchedule\n"
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
	        		testGetInstance();
	        		break;
	        	case 2:
	        		testExportEnvironment();
	        		break;
	        	case 3:
	        		testExportSchedule();
	        		break;
	        	case 4:
	        		testGetEnvironmentFileList();
	        		break;
	        	case 5:
	        		testGetGroup(sc);
	        		break;
	        	case 6:
	        		testGetGroups();
	        		break;
	        	case 7:
	        		testGetGroupsFromSubject();
	        		break;
	        	case 8:
	        		testGetRoomNamesList();
	        		break;
	        	case 9:
	        		testGetRooms();
	        		break;
	        	case 12:
	        		testImportEnvironment();
	        		break;
	        }
	        //menu();
	        n = sc.nextInt();
	    }
	}

	public static void testGetInstance() {
		cD = CtrlDomain.getInstance();
		System.out.println("Instanced");
	}
	
	private static void testExportEnvironment() {
		// TODO Auto-generated method stub
		
	}
	
	private static void testExportSchedule() {
		// TODO Auto-generated method stub
		
	}
	
	private static void testGetEnvironmentFileList() {
		// TODO Auto-generated method stub
		
	}
	
	private static void testGetGroup(Scanner sc) {
		System.out.println(">Introduzca nombre del grupo");
		try {
			String s = sc.next();
            Group x = cD.getGroup(s);
            System.out.println("Group: " + x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private static void testGetGroups() {
		try {
            ArrayList<Group> x = cD.getGroups();
            System.out.println("Groups: " + x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private static void testGetGroupsFromSubject() {
		// TODO Auto-generated method stub
		
	}
	
	private static void testGetRoomNamesList() {
		try {
            ArrayList<String> x = cD.getRoomNamesList();
            System.out.println("Room Names: " + x);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private static void testGetRooms() {
		try {
            ArrayList<Room> x = cD.getRooms();
            System.out.println("Rooms: " + x);
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

}