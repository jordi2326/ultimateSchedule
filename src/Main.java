import java.util.List;
import java.util.Scanner;
import domain.controllers.CtrlDomain;

public class Main {
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
		ctDomain = CtrlDomain.getInstance();
		Scanner sc = new Scanner(System.in);
		printMainMenu();
	    int n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	            case 1:
	            	loadEnvironmentMenu(sc);
	                break;
	        }
	        printMainMenu();
	        n = sc.nextInt();
	    }
	}
	
	private static void printLoadEnvironmentMenu(List<String> filenames) {
		System.out.print(
	            "Load Environment\n"
	            + "--------------------------\n");
		for (int i = 0; i < filenames.size(); i++) {
			System.out.println(i + "| "+ filenames.get(i));
		}
		
		System.out.print("--------------------------\n");
    }
	
	public static void loadEnvironmentMenu(Scanner sc){
		List<String> filenames = ctDomain.getEnvironmentFilesList();
		printLoadEnvironmentMenu(filenames);
	    int n = sc.nextInt();
	    try {
	    	String filename = filenames.get(n);
			ctDomain.importEnvironment(filename);
			environmentMenu(sc, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printEnvironmentMenu(String envName) {
		System.out.println(envName);
		System.out.print(
	            "--------------------------\n"
	            + " 1| Generate New Schedule\n"
	          	+ " 2| Load Schedule From File\n"
	          	+ " 3| View Subjects\n"
	          	+ " 4| View Rooms\n"
	          	+ " 0| Back\n"
	            + "--------------------------\n"
	            );         
    }
	
	public static void environmentMenu(Scanner sc, String envName){
		printEnvironmentMenu(envName);
	    int n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	            case 1:
	            	ctDomain.generateSchedule();
	                break;
	            case 2:
	            	//testGetCapacity(sc);
	                break;
	            case 3:
	            	genericListMenu(sc, "Subjects", ctDomain.getSubjectNamesList());
	                break;
	            case 4:
	            	genericListMenu(sc, "Rooms", ctDomain.getRoomNamesList());
	                break;
	        }
	        printEnvironmentMenu(envName);
	        n = sc.nextInt();
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
	
	public static void genericListMenu(Scanner sc, String title, List<String> items){
		printGenericListMenu(title, items);
	    int n = sc.nextInt();
	    while (n != 0) n = sc.nextInt();
	}
}