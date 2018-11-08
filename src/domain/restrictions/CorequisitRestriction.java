package domain.restrictions;

import java.util.ArrayList;
import java.util.Map;

import domain.Group;
import domain.Restriction;
import domain.Room;
import domain.Schedule;
import domain.Subject;
import domain.Timeframe;
import domain.controllers.CtrlDomain;

public class CorequisitRestriction extends Restriction{
	
	public CorequisitRestriction() {
		super(true); //negotiable
	}
	
	@Override
	public boolean validate(Group group, Room room, Timeframe timeFrame, Schedule schedule) {
		CtrlDomain CD = CtrlDomain.getInstance();
		Map<String, String> lectures = schedule.getLecturesAtTime(timeFrame);
		Subject subject = CD.getSubject(group.getSubject());
		ArrayList<String> coreqs = subject.getAllCorequisits();
		
		if(!coreqs.isEmpty()) {
			for (Map.Entry<String, String> l: lectures.entrySet()) {
				String ngroup_name = l.getValue();
				Group ngroup = CD.getGroup(ngroup_name);
				
				if(coreqs.contains(ngroup.getSubject())) return false;
				
			}
		}
		
		return true;
	}
}