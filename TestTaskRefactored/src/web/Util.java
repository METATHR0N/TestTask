package web;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBManager;
import db.entity.Employee;
import web.exception.AppException;
import web.exception.Messages;

public class Util {

	public static Employee getNewEmployee(HttpServletRequest request, HttpServletResponse response) {

		String input_id = request.getParameter("e_id");
		int e_id = (input_id == null) ? 0 : Integer.parseInt(input_id);
		int d_id = Integer.parseInt(request.getParameter("d_id"));
		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		String phone = request.getParameter("phone");
		String birthday = request.getParameter("birthday");

		Employee e = new Employee();
		e.setId(e_id);
		e.setD_id(d_id);
		e.setBirthday(birthday);
		e.setMail(mail);
		e.setName(name);
		e.setPhone(phone);

		return e;
	}
	
	public static void newEmployeeIsValid(Employee e) throws AppException {
		mailIsValid(e.getMail());
		mailExists(e.getMail());
		phoneIsValid(e.getPhone());
		nameIsValid(e.getName());
	}
	
	public static void updateEmployeeIsValid(Employee e) throws AppException {
		mailIsValid(e.getMail());
		phoneIsValid(e.getPhone());
		nameIsValid(e.getName());
	}

	public static void departmentExists(String name) throws AppException {
		
		DBManager db = DBManager.getInstance();
		try {
			if(db.DepartmentExists(name)) {
				throw new AppException(Messages.DEPARTMENT_EXISTS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void mailIsValid(String email) throws AppException {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (!email.matches(regex)) {
			throw new AppException(Messages.INCORRECT_EMAIL);
		}
	}

	private static void mailExists(String email) throws AppException {
		
		DBManager db = DBManager.getInstance();
		try {
			if(db.EmployeeExists(email)) {
				throw new AppException(Messages.MAIL_EXISTS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void phoneIsValid(String phone) throws AppException {
		String regex = "\\+?\\d*";
		if (!phone.matches(regex)) {
			throw new AppException(Messages.INCORRECT_PHONE_NUMBER);
		}
	}
	
	private static void nameIsValid(String name) throws AppException {
		String regex = "\\p{L}{2,}";
		if (!name.matches(regex)) {
			throw new AppException(Messages.INCORRECT_NAME);
		}
	}
	
}
