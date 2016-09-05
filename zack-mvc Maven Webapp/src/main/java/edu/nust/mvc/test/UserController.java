package edu.nust.mvc.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nust.mvc.annotation.ZAController;
import edu.nust.mvc.annotation.ZARequestMapping;

@ZAController
public class UserController {
	@ZARequestMapping("user/hello")
	public String helloexecute(HttpServletRequest req,HttpServletResponse resp){
		resp.setContentType("text/html;charset=utf-8");
		try {
			resp.getWriter().write("<h1>中国hello --from usercontroller</h1>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@ZARequestMapping("user/tomyjsp")
	public String tomyjsp(HttpServletRequest req,HttpServletResponse resp){
		return "pages/MyJsp";
	}
}
