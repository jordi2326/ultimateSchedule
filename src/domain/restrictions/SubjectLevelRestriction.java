package domain.restrictions;

import java.util.Map;
import domain.Group;
import domain.Room;
import domain.Schedule;
import domain.Subject;
import domain.Timeframe;
import domain.controllers.CtrlDomain;

public class SubjectLevelRestriction extends UnaryRestriction{
	
	public SubjectLevelRestriction() {
		super(true); //negotiable
	}
	
	@Override
	public boolean validate(Group group, Room room, Schedule schedule) {
		CtrlDomain CD = CtrlDomain.getInstance();
		Map<String, String> lectures = schedule.getLecturesAtTime(timeFrame);
		Subject subject = CD.getSubject(group.getSubject());
		
		for (Map.Entry<String, String> l: lectures.entrySet()) {
			String ngroup_name = l.getValue();
			Group ngroup = CD.getGroup(ngroup_name);
			if(group.getCode().equals(ngroup.getCode())) {
				Subject nsubject = CD.getSubject(ngroup.getSubject());
				if(subject.getLevel().equals(nsubject.getLevel())) return false;
			}
			
		}
		return true;
	}
}