package persistance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.controllers.CtrlDomain;

/** Controlador principal de dades de l'aplicació.
 * @author Xavier Martín Ballesteros
*/

public class CtrlData {
	
	/** Instancia d'aquesta classe.
	*/
	private static CtrlData instance;
	
	/**
	 * Retorna la instancia d'aquesta classe.
	 * @return {@link CtrlDomain#instance}
	 */
	public static CtrlData getInstance() {
		if (instance == null)
			instance = new CtrlData() {
		};
		return instance;
	}
	
	/** Constructora estàndard.
	*/
	private CtrlData() {
	}
	
	/**
	 * Importa un entorn (aules, assignatures, aules) desde un arxiu.
	 * @param filename Nom de l'arxiu d'entorn a importar.
	 * @return Contingut de l'arxiu llegit.
	 */
	public String readEnvironment(String filename, boolean isFullpath) throws FileNotFoundException {
		if(isFullpath) return readData(filename);
		else return readData("data/environments/"+filename);
	}
	
	/**public boolean writeEnvironment(String filename, String content) throws FileNotFoundException {
        return writeData("environments/"+filename, content);
	}**/
	
	/**
	 * Guarda el contingut donat (que representa un horari) a un arxiu dins la carpeta d'horaris.
	 * @param filename Nom amb el que guardar l'horari.
	 * @param content Contingut de l'horari a guardar.
	 * @return true si s'ha guardat correctament.
	 */
	public boolean writeEnvironment(String filename, String content, boolean isFullpath) throws FileNotFoundException {
		if(isFullpath) return writeData(filename, content);
		else return writeData("data/environments/"+filename, content);
	}
	
	/**
	 * Importa un horari desde un arxiu.
	 * @param filename Nom de l'arxiu d'horari a importar.
	 * @return Contingut de l'arxiu llegit.
	 */
	public String readSchedule(String filename, boolean isFullpath) throws FileNotFoundException {
		if(isFullpath) return readData(filename);
		else return readData("data/schedules/"+filename);
	}
	
	/**
	 * Guarda el contingut donat (que representa un horari) a un arxiu dins la carpeta d'horaris.
	 * @param filename Nom amb el que guardar l'horari.
	 * @param content Contingut de l'horari a guardar.
	 * @return true si s'ha guardat correctament.
	 */
	public boolean writeSchedule(String filename, String content, boolean isFullpath) throws FileNotFoundException {
		if(isFullpath) return writeData(filename, content);
		else return writeData("data/schedules/"+filename, content);
	}
	
	/**
	 * Llegeix els continguts d'un arxiu.
	 * @param filename Adreça de l'arxiu a llegir.
	 * @return El contingut de l'arxiu.
	 */
	private String readData(String filename) throws FileNotFoundException {
		FileReader fr = new FileReader(filename);
		Scanner scan = new Scanner(fr);
		scan.useDelimiter("\\Z");
		String out = scan.next();
		scan.close();
		return out;
	}
	
	/**
	 * Escriu un String a un arxiu local.
	 * @param filename Adreça de l'arxiu a on escriure (si no existeix es crearà).
	 * @return true si s'ha escrit correctament.
	 */
	private boolean writeData(String filename, String content) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(filename); 
        pw.write(content); 
        pw.flush(); 
        pw.close(); 
        return true;
	}
	
	/**
	 * Retorna una llista de noms d'arxius d'entorn disponibles.
	 * @return Una llista de noms d'arxius d'entorn disponibles.
	 */
	public List<String> getEnvironmentFilesList() {
		return getFilesList("data/environments");
	}
	
	/**
	 * Retorna una llista de noms d'arxius d'horari disponibles.
	 * @return Una llista de noms d'arxius d'horari disponibles.
	 */
	public List<String> getScheduleFilesList() {
		return getFilesList("data/schedules");
	}
	
	/**
	 * Retorna una llista de noms d'arxius que es troben dins d'una carpeta.
	 * @param path Adreça de la carpeta.
	 * @return Una llista de noms d'arxius que es troben dins de la carpeta donada.
	 */
	private List<String> getFilesList(String path) {
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