import java.util.Scanner;
import domaincontrollers.CtrlDomain;

public class CtrlDomainDriver {
	private static CtrlDomain cD;
	
	private static void menu() {
		System.out.print(
	            "Subject Driver\n"
	            + "---------------------\n"
	            + "Opciones\n"
	            + " 0| Salir\n"
	            + " 1| exportData\n"
	            + " 2| generatePossibleCalendars\n"
	            + " 3| getGroup\n"
	            + " 4| getGroupsFromSubject??\n"
	            + " 5| getSubject\n"
	            + " 6| importData\n"
	            + "---------------------\n"
	            );
	                    
    }
	
	public static void main (String [] args) {
		cD = CtrlDomain.getInstance();
		
		Scanner sc = new Scanner(System.in);
	
		int n;
	    menu();
	    n = sc.nextInt();
	    while (n != 0) {
	        switch (n) {
	            
	        }
	        //menu();
	        n = sc.nextInt();
	    }
	}
}