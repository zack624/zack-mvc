package edu.nust.mvc.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nust.mvc.annotation.ZAController;
import edu.nust.mvc.annotation.ZARequestMapping;



@ZAController
public class HelloController{
	@ZARequestMapping("hello")
	public String helloexecute(HttpServletRequest req,HttpServletResponse resp){
		return "index";
	}
	@ZARequestMapping("test")
	public String testexecute(HttpServletRequest req,HttpServletResponse resp){
		try {
			resp.sendRedirect("http://www.baidu.com");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
