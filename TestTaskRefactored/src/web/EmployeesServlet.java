package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBManager;
import db.entity.Employee;

@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {

	private static final long serialVersionUID = 6716085298779182103L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		redirect(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		redirect(request, response);
	}
	
	private void redirect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		DBManager dbmanager = DBManager.getInstance();
		List<Employee> employees;
		
		int d_id = Integer.parseInt(req.getParameter("d_id"));
		try {
			employees = dbmanager.findEmployeesFromDepartment(d_id);
			req.setAttribute("employees", employees);
			req.setAttribute("d_id", d_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/employees.jsp").forward(req, resp);
	
	}
}
