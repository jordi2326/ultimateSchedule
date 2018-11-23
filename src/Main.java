import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.classes.Group;
import domain.classes.Group.DayPeriod;
import domain.classes.Group.Type;
import domain.controllers.CtrlDomain;

/** Representa un grup.
 * @author Carlos Bergillos Varela
*/

public class Main {
	private static Scanner sc;
	private static CtrlDomain ctDomain;
	private static boolean silent = false;
	
	private static void printMain() {
		if (!silent) System.out.print(
	            "Welcome to UltimateSchedule\n"
	            + "---------------------\n"
	    	    + "Opciones\n"
	    	    + " 1| Test Automatico\n"
	    	    + " 2| Probar Manualmente\n"
	    	    + " 0| Salir\n"
	    	    + "---------------------\n"
	    	    );  
	                    
    }
	
	private static void printMainMenu() {
		if (!silent) System.out.print(
	            "Welcome to UltimateSchedule\n"
	            + "--------------------------\n"
	            + " 1| Load Environment File\n"
	            + " 0| Exit\n"
	            + "--------------------------\n"
	            );         
    }
	
	public static void main(String[] args) throws Exception {
		sc = new Scanner(System.in);
		ctDomain = CtrlDomain.getInstance();
		int n;
	    printMain();
	    n = sc.nextInt();
	    switch (n) {
	    	case 1:
	    		loadTestMenu();
	            break;
	        case 2:
	        	silent = false;
	        	Manual();
	            break;
	    }
	}
	
	public static void Manual(){
		sc = new Scanner(System.in);
		ctDomain = CtrlDomain.getInstance();
		printMainMenu();
		int n = sc.nextInt();
		while (n != 0) {
			switch (n) {
				case 1:
					loadEnvironmentMenu();
					break;
			}
			printMainMenu();
			n = sc.nextInt();
		}
	}
	
	public static void loadTestMenu(){
		String path = "data/";
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
	    	int r = sc.nextInt();
			while (r != 0) {
				switch (r) {
					case 1:
						loadEnvironmentMenu();
						break;
				}
				printMainMenu();
				r = sc.nextInt();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printLoadFileMenu(String title, List<String> filenames) {
		if (!silent) System.out.println(title);
		if (!silent) System.out.print("--------------------------\n");
		for (int i = 0; i < filenames.size(); i++) {
			if (!silent) System.out.println(i + "| "+ filenames.get(i));
		}
		
		if (!silent) System.out.print("--------------------------\n");
    }
	
	public static void loadEnvironmentMenu(){
		List<String> filenames = ctDomain.getEnvironmentFilesList();
		printLoadFileMenu("Load Environment", filenames);
		ctDomain.erase();
		sc.nextLine();
	    String s = sc.nextLine();
	    try {
			ctDomain.importEnvironment(s);
			environmentMenu(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printEnvironmentMenu(String envName) {
		if (!silent) System.out.println(envName);
		if (!silent) System.out.print(
	            "--------------------------\n"
	            + " 1| Generate New Schedule\n"
	          	+ " 2| View Saved Schedule\n"
	          	+ " 3| View Subjects\n"
	          	+ " 4| View Rooms\n"
	          	+ " 5| View Restrictions\n"
	          	+ " 6| View Groups\n"
	          	+ " 0| Back\n"
	            + "--------------------------\n"
	            );         
    }
	
	public static void environmentMenu(String envName){
		printEnvironmentMenu(envName);
	    int n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	            case 1:
	            	if(ctDomain.generateSchedule()){
	            		ctDomain.printSchedule();
	            		scheduleMenu();
	            	}else{
	            		System.out.println("Error. No Valid Schedule Found");
	            	}
	                break;
	            case 2:
	            	loadSchedulesMenu();
	                break;
	            case 3:
	            	genericListMenu("Subjects", ctDomain.getSubjectNamesList());
	                break;
	            case 4:
	            	genericListMenu("Rooms", ctDomain.getRoomNamesList());
	                break;
	            case 5:
	            	genericListMenuMap("Un-ary restrictions", ctDomain.getUnaryRestrictions());
	            	genericListMenu("N-ary restrictions", ctDomain.getNaryRestrictions());
	            	break;
	            case 6:
	            	genericListMenu("Groups", ctDomain.getGroupNamesList());
	            	break;
	        }
	        printEnvironmentMenu(envName);
	        n = sc.nextInt();
	    }
	}
	
	public static void loadSchedulesMenu(){
		List<String> filenames = ctDomain.getScheduleFilesList();
		printLoadFileMenu("Load Schedule", filenames);
	    int n = sc.nextInt();
	    try {
	    	String filename = filenames.get(n);
			ctDomain.importSchedule(filename);
			ctDomain.printSchedule();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printGenericListMenu(String title, List<String> items) {
		System.out.println(title);
		System.out.print("--------------------------\n");
		for (String s : items) {
			System.out.println("  | " + s);
		}
		System.out.print("--------------------------\n");   
		System.out.println(" 0| Back");
		System.out.print("--------------------------\n");       
    }
	
	public static void scheduleMenu(){
		printScheduleMenu();
		int n = sc.nextInt();
	    switch (n) {
	        case 1:
	            saveSchedule();
	        break;
	    }
	}
	
	private static void printScheduleMenu() {
		if (!silent) System.out.print(
	            "--------------------------\n"
	            + " 1| Save Schedule\n"
	          	+ " 0| Back\n"
	            + "--------------------------\n"
	            );         
    }
	
	public static void saveSchedule(){
		if (!silent) System.out.print("Enter name: ");
		sc.nextLine(); //clear for next line
		String filename = sc.nextLine();
		try {
			ctDomain.exportSchedule(filename+".json");
			System.out.println("Saved '"+filename+".json'");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void genericListMenu(String title, List<String> items){
		printGenericListMenu(title, items);
	    int n = sc.nextInt();
	    while (n != 0) n = sc.nextInt();
	}
	
	public static void genericListMenuMap(String title, Map<String, String> map){
		System.out.println(title);
		System.out.print("--------------------------\n");
		for (String group : map.keySet()) {
			System.out.println("  | " + group + ": " + map.get(group));
		}
		System.out.println("");
	}
}