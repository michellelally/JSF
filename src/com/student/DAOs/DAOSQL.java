package com.student.DAOs;
import java.sql.*;
import java.util.ArrayList;
import javax.sql.DataSource;
import javax.naming.*;
import com.student.Models.*;

public class DAOSQL {
	private DataSource mysqlDS;
	private Connection conn; 
	
	public DAOSQL() throws Exception {
		Context context = new InitialContext();
		String jndiName = "java:comp/env/jdbc/studentdb";
		mysqlDS = (DataSource) context.lookup(jndiName);
	}
	
	public ArrayList<Course> loadCourses() throws Exception {
		 ArrayList<Course> courses = new ArrayList<Course>();
		 conn = mysqlDS.getConnection();
		 
		 Statement stmt = conn.createStatement();		
		 ResultSet rs = stmt.executeQuery("select * from course");
		 
		 while (rs.next()) {
			 Course course = new Course();
			 course.setCid(rs.getString("cID"));
			 course.setName(rs.getString("cName"));
			 course.setDuration(rs.getInt("duration"));
			 courses.add(course);
		 }
		 return courses;
	}
	
	public void addCourse(Course c) throws Exception{
		System.out.print("addCourse()" + c.getCid() + "', '" + c.getName() + "', '" + c.getDuration());
		
		conn = mysqlDS.getConnection();
		Statement stmt = conn.createStatement();
		
		String query = "insert into course (cID, cNAme, duration) values ('" + c.getCid() + "', '" + c.getName() + "', '" + c.getDuration() + "');";
		stmt.executeUpdate(query);
		
		ArrayList<Course> courses = new ArrayList<Course>();
		System.out.println("Size: " + courses.size());
	}
	
	public void deleteCourse(Course c) throws SQLException {
		System.out.print("deleteCourse()" + c.getCid() + "', '" + c.getName() + "', '" + c.getDuration());
		
		conn = mysqlDS.getConnection();
		
		Statement stmt = conn.createStatement();
		String query = "delete from course where cid in('" + c.getCid() +"');";
		
		stmt.executeUpdate(query);
		
		ArrayList<Course> courses = new ArrayList<Course>();
		System.out.println("Size: " + courses.size());

	}
	
	public ArrayList<CourseStudent> loadCourseStudentDetails(Course c) throws Exception {
		System.out.print("loadCourseStudentDetails()" + c.getCid() + "', '" + c.getName() + "', '" + c.getDuration());

		 ArrayList<CourseStudent> courseStudent = new ArrayList<CourseStudent>();
		 conn = mysqlDS.getConnection();
		 
		 Statement stmt = conn.createStatement();		
		 ResultSet rs = stmt.executeQuery("select c.cid, c.cname, c.duration, s.name, s.address from course c join student s on c.cid = s.cid and c.cid='"+ c.getCid() + "';");
		 
		 while (rs.next()) {
			 CourseStudent courseStudents = new CourseStudent();
			 courseStudents.setCid(rs.getString("cID"));
			 courseStudents.setcName(rs.getString("cName"));
			 courseStudents.setDuration(rs.getInt("duration"));
			 courseStudents.setsName(rs.getString("name"));
			 courseStudents.setAddress(rs.getString("address"));
			 courseStudent.add(courseStudents);
		 }
		 return courseStudent;
	}
	
}
