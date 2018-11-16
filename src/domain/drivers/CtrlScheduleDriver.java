package domain.drivers;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import domain.controllers.CtrlDomain;

public class CtrlScheduleDriver {
	private static Scanner sc;
	private static CtrlDomain ctDomain;
	
	private static void printMainMenu() {
		System.out.print(
	            "Ctrl Schedule Driver\n"
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
			System.out.println("| "+ filenames.get(i));
		}
		
		System.out.print("--------------------------\n");
    }
	
	public static void loadEnvironmentMenu(){
		List<String> filenames = ctDomain.getEnvironmentFilesList();
		printLoadFileMenu("Load Environment", filenames);
	    try {
	    	String filename = sc.next();
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
	            	}else{
	            		System.out.println("Error. No Valid Schedule Found");
	            	}
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
	
	
	public static void genericListMenu(String title, List<String> items){
		printGenericListMenu(title, items);
	    int n = sc.nextInt();
	    while (n != 0) n = sc.nextInt();
	}
}
