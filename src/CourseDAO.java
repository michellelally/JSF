import java.sql.*;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.naming.*;

public class CourseDAO {
	private DataSource mysqlDS;
	
	public CourseDAO() throws Exception {
		Context context = new InitialContext();
		String jndiName = "java:comp/env/jdbc/studentdb";
		mysqlDS = (DataSource) context.lookup(jndiName);
	}
	
	public ArrayList<Course> loadCourses() throws SQLException {
		 ArrayList<Course> courses = new ArrayList<Course>();
		 Connection conn = mysqlDS.getConnection();
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
}
