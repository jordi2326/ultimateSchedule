package domain.drivers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import domain.controllers.CtrlDomain;

/**
 * @author Xavier Lacasa Curto
*/

public class EnvironmentDriver {
	private static Scanner sc;
	private static CtrlDomain cD;
	private static boolean silent = false;
	
	private static void printMain() {
		System.out.print(
	            "Environment Driver\n"
	    	    );  
	                    
    }
	
	public static void main (String [] args) throws Exception {
		cD = CtrlDomain.getInstance();
		sc = new Scanner(System.in);
		loadTestMenu();
		
	}
	
	private static void printLoadFileMenu(String title, List<String> filenames) {
		System.out.println(title);
		System.out.print("--------------------------\n");
		for (int i = 0; i < filenames.size(); i++) {
			System.out.println(i + "| "+ filenames.get(i));
		}
		
		System.out.print("--------------------------\n");
    }
	
	private static void loadTestMenu(){
		String path = "data/driverTests/environment/";
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
	    	silent = true;
	    	subMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printSubMenu() {
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
	            + " 9| printSchedule\n"
	            + "10| scheduleToJsonString\n"
	            + " 0| Salir\n"
	            + "---------------------\n"
	            );
	                    
    }
	
	private static void subMenu () {	
		int n;
		if(!silent) printSubMenu();
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
	        		testPrintSchedule();
	        		break;
	        	case 10:
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
		if(!silent) System.out.println(">Introduzca Nombre de Archivo");
		String filename = sc.next();
		try {
			cD.importEnvironment(filename);
			System.out.println("Imported "+filename);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void testImportSchedule() {
		if(!silent) System.out.println(">Introduzca Nombre de Archivo");
		String filename = sc.next();
		try {
			cD.importSchedule(filename);
			System.out.println("Imported "+filename);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void testPrintSchedule() {
		try {
			cD.printSchedule();
		} catch (Exception e) {
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