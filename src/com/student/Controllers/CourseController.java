package com.student.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.student.DAOs.DAOSQL;
import com.student.Models.*;

@ManagedBean
@ApplicationScoped
public class CourseController {
	private ArrayList<Course> courses;
	private ArrayList<CourseStudent> courseStudent;
	private DAOSQL dao;

	public CourseController() throws Exception {
		super();
		dao = new DAOSQL();
		courses = new ArrayList<>();
	}

	public void loadCourses() {
		try {
			courses = dao.loadCourses();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
		System.out.println("Course size = " + courses.size());
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public String addCourse(Course c) {
		try {
			dao.addCourse(c);
			return "index.xhtml";
		} catch (CommunicationsException e) {
			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Database Offline");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			FacesMessage message = new FacesMessage("Error: Course already exists");
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Course already exists");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: Unknown Exception");
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Unknown Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String deleteCourse(Course c){	
		try {
			dao.deleteCourse(c);
		} catch (SQLException e) {
			FacesMessage message = 	new FacesMessage("Error: "+ e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return "list-courses.xhtml";
	}
	
	public ArrayList<CourseStudent> getCourseStudent() {
		return courseStudent;
	}
	
	public void loadCourseStudentDetails(Course c) {
		try {
			courseStudent = dao.loadCourseStudentDetails(c);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
	}
	
	
}
