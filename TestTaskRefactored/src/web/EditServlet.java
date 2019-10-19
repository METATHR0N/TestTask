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

@WebServlet("/Edit")
public class EditServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			int id = Integer.parseInt(request.getParameter("id"));

			DBManager db = DBManager.getInstance();
			Department department = db.findDepartment(id);
			request.setAttribute("department", department);
			
			getServletContext().getRequestDispatcher("/edit.jsp").forward(request, response);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");

		DBManager db = DBManager.getInstance();
		Department department = new Department();
		department.setId(id);
		department.setName(name);

		try {

			Util.departmentExists(name);
			db.updateDepartment(department);
			System.out.println(department);

			response.sendRedirect(request.getContextPath() + "/Index");
			
		} catch (AppException ex) {

			String exceptionMessage = ex.getMessage();
			request.setAttribute("error", exceptionMessage);
			request.setAttribute("department", department);
			getServletContext().getRequestDispatcher("/edit.jsp").forward(request, response);
			
		} catch (SQLException e) {
			
			getServletContext().getRequestDispatcher("/Index").forward(request, response);
		}
	}
}