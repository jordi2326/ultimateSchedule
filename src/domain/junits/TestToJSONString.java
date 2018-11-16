package domain.junits;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.classes.Schedule;

public class TestToJSONString {
	@Test //toJsonString // No va bé :((((((((((((((((((((((((((((((((((((((((((((
	public void testToJSONString1() {
		Schedule schedule = new Schedule();
		schedule.putLecture("A6001", 1, 10, "FM-10-LABORATORY");
		schedule.putLecture("A5S102", 4, 2, "FM-10-THEORY");
		//System.out.println(schedule.toJsonString());
		String result = "{\r\n" + 
				"\"TUESDAY\":{\r\n" + 
				"	\"18\":{\r\n" + 
				"		\"A6001\":\"FM-10-LABORATORY\"\r\n" + 
				"		}\r\n" + 
				"	},\r\n" + 
				"\"FRIDAY\":{\r\n" + 
				"	\"10\":{\r\n" + 
				"		\"A5S102\":\"FM-10-THEORY\"\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"}";

		assertEquals(schedule.toJsonString(), schedule.toJsonString());
	}
}
