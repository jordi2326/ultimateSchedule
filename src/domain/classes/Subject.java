package domain.classes;

import java.util.ArrayList;

public class Subject {
	
	private String code; // e.g. FM
	private String name; // e.g. Fonaments Matematics
	private String level;
	private ArrayList<String> groups; //Contains the group.toString() of every group in the subject
	private ArrayList<String> coreqs; //Contains the subject.toString() of every group in the subject

	public Subject(String code, String name, String level, ArrayList<String> groups, ArrayList<String> coreqs) {
		this.code = code;
		this.name = name;
		this.level = level;
		this.coreqs = coreqs;
		this.groups = groups;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public String getLevel() {
		return level;
	}
	
	public ArrayList<String> getCoreqs() {
		return coreqs;
	}
	
	public ArrayList<String> getGroups() {
		return groups;
	}
	
	@Override
	public String toString() {
		return code;
	}
}