package edu.nust.mvc.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nust.mvc.page.ZPage;

public class PageUtils {
	
	public static void forward(ZPage page,HttpServletRequest req,HttpServletResponse resp){
		String path = page.getPrefix()+page.getViewName()+page.getSuffix();
		try {
			req.getRequestDispatcher(path).forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
