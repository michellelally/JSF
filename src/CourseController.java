import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.*;

@ManagedBean
@ApplicationScoped
public class CourseController {
	private ArrayList<Course> courses;
	private CourseDAO dao;
	
	public CourseController() throws Exception {
		super();
		dao = new CourseDAO();
		courses = new ArrayList<>();
	}
	
	public void loadCourses() throws SQLException {
		courses = dao.loadCourses();
		System.out.println("Course size = " + courses.size());
	}
	
}
