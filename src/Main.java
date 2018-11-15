import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import domain.controllers.CtrlDomain;

public class Main {
	private static Scanner sc;
	private static CtrlDomain ctDomain;
	
	private static void printMainMenu() {
		System.out.print(
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
	
	private static void printLoadFileMenu(String title, List<String> filenames) {
		System.out.println(title);
		System.out.print("--------------------------\n");
		for (int i = 0; i < filenames.size(); i++) {
			System.out.println(i + "| "+ filenames.get(i));
		}
		
		System.out.print("--------------------------\n");
    }
	
	public static void loadEnvironmentMenu(){
		List<String> filenames = ctDomain.getEnvironmentFilesList();
		printLoadFileMenu("Load Environment", filenames);
	    int n = sc.nextInt();
	    try {
	    	String filename = filenames.get(n);
			ctDomain.importEnvironment(filename);
			environmentMenu(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printEnvironmentMenu(String envName) {
		System.out.println(envName);
		System.out.print(
	            "--------------------------\n"
	            + " 1| Generate New Schedule\n"
	          	+ " 2| View Saved Schedule\n"
	          	+ " 3| View Subjects\n"
	          	+ " 4| View Rooms\n"
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
		System.out.print(
	            "--------------------------\n"
	            + " 1| Save Schedule\n"
	          	+ " 0| Back\n"
	            + "--------------------------\n"
	            );         
    }
	
	public static void saveSchedule(){
		System.out.print("Enter name: ");
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
}