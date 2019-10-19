package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBManager;
import db.entity.Employee;
import web.exception.AppException;

@WebServlet("/CreateEmployee")
public class CreateEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int d_id = Integer.parseInt(request.getParameter("d_id"));
		request.setAttribute("d_id", d_id);
		
		getServletContext().getRequestDispatcher("/createEmployee.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int d_id = Integer.parseInt(request.getParameter("d_id"));

		Employee employee = Util.getNewEmployee(request, response);
		
		try {
			
			Util.newEmployeeIsValid(employee);

			DBManager db = DBManager.getInstance();
			db.createEmployee(employee);

			response.sendRedirect(request.getContextPath() + "/employees?d_id=" + d_id);
			
		} catch (AppException ex) {
			
			String exceptionMessage = ex.getMessage();
			request.setAttribute("employee", employee);
			request.setAttribute("d_id", d_id);
			request.setAttribute("error", exceptionMessage);
			getServletContext().getRequestDispatcher("/createEmployee.jsp").forward(request, response);
		
		} catch (SQLException ex) {

			getServletContext().getRequestDispatcher("/Index").forward(request, response);
		}
	}
	

}