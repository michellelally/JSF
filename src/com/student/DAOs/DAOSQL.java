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
	
	public ArrayList<Student> loadStudents() throws Exception {
		 ArrayList<Student> students = new ArrayList<Student>();
		 conn = mysqlDS.getConnection();
		 
		 Statement stmt = conn.createStatement();		
		 ResultSet rs = stmt.executeQuery("select * from student");
		 
		 while (rs.next()) {
			 Student student = new Student();
			 student.setSid(rs.getString("sid"));
			 student.setCid(rs.getString("cID"));
			 student.setName(rs.getString("name"));
			 student.setAddress(rs.getString("address"));
			 students.add(student);
		 }
		 return students;
	}
	
	public void addStudent(Student s) throws Exception{
		System.out.print("addStudent()" + s.getSid() + ", " + s.getCid() + ", " + s.getName() + ", " + s.getAddress() );
		
		conn = mysqlDS.getConnection();
		Statement stmt = conn.createStatement();
		
		String query = "insert into student (sid ,cID, name, address) values ('" + s.getSid() + "', '" + s.getCid() + "', '" + s.getName() + "', '" + s.getAddress() + "');";
		stmt.executeUpdate(query);
		
		ArrayList<Student> students = new ArrayList<Student>();
		System.out.println("Size: " + students.size());
	}
	
	public void deleteStudent(Student s) throws SQLException {
		System.out.print("deleteStudent()" + s.getSid() + ", " + s.getCid() + ", " + s.getName() + ", " + s.getAddress() );
		
		conn = mysqlDS.getConnection();
		
		Statement stmt = conn.createStatement();
		String query = "delete from student where sid in('" + s.getSid() +"');";
		
		stmt.executeUpdate(query);
		
		ArrayList<Student> students = new ArrayList<Student>();
		System.out.println("Size: " + students.size());

	}
	
	public ArrayList<CourseStudent> loadFullStudentDetails(Student s) throws Exception {
		System.out.print("addStudent()" + s.getSid() + ", " + s.getCid() + ", " + s.getName() + ", " + s.getAddress() );

		 ArrayList<CourseStudent> fullStudents = new ArrayList<CourseStudent>();
		 conn = mysqlDS.getConnection();
		 
		 Statement stmt = conn.createStatement();		
		 ResultSet rs = stmt.executeQuery("select s.sid, s.name, s.cid, c.cname, c.duration from student s join course c on s.cid = c.cid and s.sid='"+ s.getSid()+ "';");
		 
		 while (rs.next()) {
			 CourseStudent fullStudent = new CourseStudent();
			 fullStudent.setSid(rs.getString("sid"));
			 fullStudent.setsName(rs.getString("name"));
			 fullStudent.setCid(rs.getString("cID"));
			 fullStudent.setcName(rs.getString("cName"));
			 fullStudent.setDuration(rs.getInt("duration"));
			 fullStudents.add(fullStudent);
		 }
		 return fullStudents;
	}
	
}
