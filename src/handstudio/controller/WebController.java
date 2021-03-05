package handstudio.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import handstudio.user.vo.UserVO;

@Controller
public class WebController {
	
	@RequestMapping(value="/user", method=RequestMethod.POST,
					headers= {"Content-type=application/json"})
	@ResponseBody
	public Map insertUser(@RequestBody UserVO user) {
		
		UserVO u = user;

		Map result = new HashMap();
//		result.put("id", u.getLoginId());
//		result.put("pw", u.getPassword());
//		result.put("nickName", u.getNickname());
		result.put("success", Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST,
					headers= {"Content-type=application/json"})
	@ResponseBody
	public Map<String, String> login(@RequestBody Map<String, String> list) {
		
		String id = list.get("loginId");
		String pw = list.get("password");
		String token = "123456";

		
		Map result = new HashMap();
		result.put("token", token);
		result.put("success", Boolean.TRUE.toString());

		return result;
	}
	
	
}
