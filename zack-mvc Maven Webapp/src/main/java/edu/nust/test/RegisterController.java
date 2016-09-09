package edu.nust.test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.nust.mvc.annotation.ZAController;
import edu.nust.mvc.annotation.ZARequestMapping;

@ZAController
public class RegisterController {
	RegisterService service;

	@ZARequestMapping("user/toRegister")
	public String toRegister(HttpServletRequest req,HttpServletResponse resp){
		return "pages/Register";
	}
	
	@ZARequestMapping("user/register")
	public String register(HttpServletRequest req,HttpServletResponse resp){
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		service.register(username, password);		
		return null;
	}
	@ZARequestMapping("user/allUsers")
	public String allUser(HttpServletRequest req,HttpServletResponse resp){
		List<User> users = service.showAll();
		req.setAttribute("users",users);
		return "pages/show";
	}
}
