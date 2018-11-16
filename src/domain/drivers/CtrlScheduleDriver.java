package domain.drivers;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.controllers.CtrlDomain;

public class CtrlScheduleDriver {
	private static Scanner sc = new Scanner(System.in);
	private static CtrlDomain ctDomain;
	private static boolean silent = false;
	
	private static void printMainMenu() {
		System.out.print(
	            "Ctrl Schedule Driver\n"
	            + "--------------------------\n"
	            + " 1| Load Environment File\n"
	            + " 0| Exit\n"
	            + "--------------------------\n"
	            );         
    }
	
	private static void printMain() {
		System.out.print(
	            "Lecture Driver\n"
	            + "---------------------\n"
	    	    + "Opciones\n"
	    	    + " 1| Test Automatico\n"
	    	    + " 2| Probar Manualmente\n"
	    	    + " 0| Salir\n"
	    	    + "---------------------\n"
	    	    );                     
    }
	
	public static void main (String [] args) throws Exception {
		sc = new Scanner(System.in);
		int n;
	    if (!silent) printMain();
	    n = sc.nextInt();
	    switch (n) {
	    	case 1:
	    		automaticTestMenu();
	            break;
	        case 2:
	        	manualMenu();
	            break;
	    }
	}
	
	public static void automaticTestMenu(){
		String path = "data/driverTests/ctrlSchedule/";
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
	    	manualMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void manualMenu() throws Exception {
		ctDomain = CtrlDomain.getInstance();
		if (!silent) printMainMenu();
	    int n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	            case 1:
	            	loadEnvironmentMenu();
	                break;
	        }
	        if (!silent) printMainMenu();
	        if (sc.hasNext()) n = sc.nextInt();
	        else return;
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
		if (!silent) printLoadFileMenu("Load Environment", filenames);
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
		if (!silent) printEnvironmentMenu(envName);
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
	        if (!silent) printEnvironmentMenu(envName);
	        if (sc.hasNext()) n = sc.nextInt();
	        else return;
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
		if (!silent) printGenericListMenu(title, items);
	    int n = sc.nextInt();
	    while (n != 0) n = sc.nextInt();
	}
}
