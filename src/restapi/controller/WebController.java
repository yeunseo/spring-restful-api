package restapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {
	
	// 첫 번째 - POST
	@RequestMapping(value="/user", method=RequestMethod.POST,
			headers= {"Content-type=application/json"})
	@ResponseBody
	public Map insertUser(@RequestBody Map<String, String> list) {
	
	Map result = new HashMap();
	result.put("success", Boolean.TRUE);
	
	return result;
	}

	// 두 번째 - POST
	@RequestMapping(value="/login", method=RequestMethod.POST,
					headers= {"Content-type=application/json"})
	@ResponseBody
	public Map login(@RequestBody Map<String, String> list) {
		
		String token = "123456";

		Map result = new HashMap();
		result.put("token", token);
		result.put("success", true);

		return result;
	}
	
	// 세 번째 - GET
	@RequestMapping(value="/user", method= RequestMethod.GET)
	@ResponseBody
	public Map getUser(@RequestParam("token") int token) {
		
		String id = "";
		String nickname = "";
		
		if(token == 123456) {
			id = "hello";
			nickname = "restapi";
		}
		
		Map result = new HashMap();
		result.put("loginId", id);
		result.put("nickname", nickname);
		
		return result;
	}
}
