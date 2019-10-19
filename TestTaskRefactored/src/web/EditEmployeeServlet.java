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
 
@WebServlet("/EditEmployee")
public class EditEmployeeServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
 
        try {
        	int id = Integer.parseInt(request.getParameter("id"));
        	
        	DBManager db = DBManager.getInstance();
        	
        	Employee employee = db.findEmployee(id);
        	
            if(employee!=null) {
                request.setAttribute("employee", employee);
                getServletContext().getRequestDispatcher("/editEmployee.jsp").forward(request, response);
            }
            else {
                getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
            }
        }
        catch(Exception ex) {
        	ex.printStackTrace();
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
     
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
 
    	
    	Employee employee = Util.getNewEmployee(request, response);
    	
        try {
        	
        	DBManager db = DBManager.getInstance();        	
        	int d_id = Integer.parseInt(request.getParameter("d_id"));
        	
        	Util.updateEmployeeIsValid(employee);

			db.updateEmployee(employee);
			System.out.println(employee);
			
			response.sendRedirect(request.getContextPath() + "/employees?d_id=" + d_id);
        }
        catch(AppException ex) {
        	
        	String exceptionMessage = ex.getMessage();
        	request.setAttribute("error", exceptionMessage);
        	request.setAttribute("employee", employee);
            getServletContext().getRequestDispatcher("/editEmployee.jsp").forward(request, response);   
        
        } catch (SQLException e) {
        	getServletContext().getRequestDispatcher("/Index").forward(request, response);
		}
    }

}