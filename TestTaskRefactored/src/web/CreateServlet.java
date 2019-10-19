package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBManager;
import db.entity.Department;
import web.exception.AppException;

@WebServlet("/Create")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		Department department = new Department();
		department.setName(name);

		try {

			DBManager db = DBManager.getInstance();
			Util.departmentExists(name);
			db.createDepartment(department);
			response.sendRedirect(request.getContextPath() + "/Index");

		} catch (AppException ex) {

			String exceptionMessage = ex.getMessage();
			request.setAttribute("department", department);
			request.setAttribute("error", exceptionMessage);
			getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);

		} catch (SQLException e) {
			
			getServletContext().getRequestDispatcher("/Index").forward(request, response);
		}

	}
}