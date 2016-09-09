package edu.nust.test;

import java.util.List;

import edu.nust.ioc.ioc.IOCContianer;

public class RegisterService {
	private UserDao dao;
	
	public void register(String username,String password){
		dao.addUser(new User(username,password));
	}
	
	public List<User> showAll(){
		return dao.getAll();
	}
}
