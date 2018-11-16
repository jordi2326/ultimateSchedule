package domain.junits;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import domain.classes.Schedule;

public class TestToJSONString {
	@Test //toJsonString
	public void testToJSONString1() throws ParseException {
		Schedule schedule = new Schedule();
		schedule.putLecture("A6001", 1, 10, "FM-10-LABORATORY");
		schedule.putLecture("A5S102", 4, 2, "FM-10-THEORY");
		
		String resultS = "{" + 
				"\"TUESDAY\":{" + 
				"	\"18\":{" + 
				"		\"A6001\":\"FM-10-LABORATORY\"" + 
				"		}" + 
				"	}," + 
				"\"FRIDAY\":{" + 
				"	\"10\":{" + 
				"		\"A5S102\":\"FM-10-THEORY\"" + 
				"		}" + 
				"	}" + 
				"}";
		
		JSONObject expected = (JSONObject) new JSONParser().parse(resultS);
		JSONObject actual = (JSONObject) new JSONParser().parse(schedule.toJsonString());

		assertEquals(expected, actual);
	}
}
