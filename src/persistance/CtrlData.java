package persistance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// import java.io.FileNotFoundException;
// import org.json.simple.JSONObject;

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
	
	public String readEnvironment(String filename) throws FileNotFoundException {
		return readData("environments/"+filename);
	}
	
	public boolean writeEnvironment(String filename, String content) throws FileNotFoundException {
        return writeData("environments/"+filename, content);
	}
	
	public String readSchedule(String filename) throws FileNotFoundException {
		return readData("schedules/"+filename);
	}
	
	public boolean writeSchedule(String filename, String content) throws FileNotFoundException {
		return writeData("schedules/"+filename, content);
	}
	
	public String readData(String filename) throws FileNotFoundException {
			FileReader fr = new FileReader("data/"+filename);
			Scanner scan = new Scanner(fr);
			scan.useDelimiter("\\Z");
			String out = scan.next();
			scan.close();
			return out;
	}
	
	private boolean writeData(String filename, String content) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter("data/"+filename); 
        pw.write(content); 
        pw.flush(); 
        pw.close(); 
        return true;
	}
	
	public List<String> getEnvironmentFilesList(){
		return getFilesList("data/environments");
	}
	
	public List<String> getScheduleFilesList(){
		return getFilesList("data/schedules");
	}
	
	private List<String> getFilesList(String path){
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<String> results = new ArrayList<String>();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		return results;
	}
}