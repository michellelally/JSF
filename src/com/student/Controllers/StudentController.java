package com.student.Controllers;

import com.student.DAOs.DAOSQL;
import com.student.Models.*;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.SQLException;


@ManagedBean
@ApplicationScoped
public class StudentController {
	private ArrayList<Student> students;
	private ArrayList<CourseStudent> fullStudent;
	private DAOSQL dao;

	public StudentController() throws Exception {
		super();
		dao = new DAOSQL();
		students = new ArrayList<>();
	}
	
	public void loadStudents() {
		try {
			students = dao.loadStudents();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
		System.out.println("Students size = " + students.size());
	}
	
	public ArrayList<Student> getStudents() {
		return students;
	}
	
	public String addStudent(Student s) {
		try {
			dao.addStudent(s);
			return "index.xhtml";
		} catch (CommunicationsException e) {
			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Database Offline");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Student already exists");
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
	
	public String deleteStudent(Student s){	
		try {
			dao.deleteStudent(s);
		} catch (SQLException e) {
			FacesMessage message = 	new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return "list-students.xhtml";
	}
	
	public ArrayList<CourseStudent> getFullStudent() {
		return fullStudent;
	}
	
	public String loadFullStudentDetails(Student s) {
		try {
			fullStudent = dao.loadFullStudentDetails(s);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
		return "full-student-details.xhtml";
	}
	

	

}
