import domaincontrollers.CtrlDomain;

import domain.Schedule;

public class Main {
	public static void main(String[] args) throws Exception {
		CtrlDomain CDo = CtrlDomain.getInstance();
		CDo.importData("input.json");
		
	
		Schedule schedule = CDo.importSchedule("inpSchedule.json");
		
		/*
		ArrayList<Calendar> calendars = CDo.generatePossibleCalendars(Calendar.MONDAY, Calendar.FRIDAY, 8, 20);
		Map<Calendar, Map<String,String>> map = new HashMap<Calendar, Map<String,String>>();
		for (Calendar calendar : calendars) {	
			ArrayList<Room> rooms = CDo.getRooms();
			ArrayList<Group> groups = CDo.getGroups();
			Iterator itr = groups.iterator(); 
			Map<String, String> roomsGroups = new HashMap<String, String>(); 
			for (Room room : rooms) {
				if (itr.hasNext()) {
					roomsGroups.put(room.toString(), itr.next().toString());
				}
			}
			map.put(calendar, roomsGroups);
		}
		
		Schedule schedule = new Schedule(map);
		*/
		CDo.exportSchedule("outSchedule.json", schedule);
		
		
		CDo.exportData("output.json");
		
		/**ArrayList<Timeframe> tfs = CDo.generatePossibleTimeframes(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, 8, 20);
		for(Timeframe tf : tfs){
		    System.out.println(tf.toString());
		}**/
		
		System.out.println("Ended without a problem :)");
	}
}
