package edu.nust.test;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class UserDao {
	public void addUser(User user){
		Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/zack", "root","1234");
		Connection con = sql2o.open();
		con.createQuery("insert into mvc_user values(:username,:password)")
			.addParameter("username",user.getName())
			.addParameter("password",user.getPassword())
			.executeUpdate();
		con.close();
	}
	
	public List<User> getAll(){
		Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/zack", "root","1234");
		Connection con = sql2o.open();
		return con.createQuery("select * from mvc_user")
				.executeAndFetch(User.class);
	}
	
}
