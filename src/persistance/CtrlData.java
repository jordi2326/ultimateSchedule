package persistance;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

// import java.io.FileNotFoundException;
//import org.json.simple.JSONObject;

public class CtrlData {
	
	private static CtrlData instance;
	
	public static CtrlData getInstance() {
		if (instance == null)
			instance = new CtrlData() {
		};
		return instance;
	}
	
	private CtrlData() {
	}
	
	
	public String readData(String filename) throws FileNotFoundException {
			FileReader fr = new FileReader("data/"+filename);
			Scanner scan = new Scanner(fr);
			scan.useDelimiter("\\Z");
			return scan.next();
	}
	
	public boolean writeData(String filename, String content) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter("data/"+filename); 
        pw.write(content); 
        pw.flush(); 
        pw.close(); 
        return true;
}
}
