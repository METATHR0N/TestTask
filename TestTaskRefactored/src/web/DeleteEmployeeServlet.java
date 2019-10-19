package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBManager;
 
@WebServlet("/DeleteEmployee")
public class DeleteEmployeeServlet extends HttpServlet {
     
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
         
        try {
        	
            int e_id = Integer.parseInt(request.getParameter("e_id"));
            int d_id = Integer.parseInt(request.getParameter("d_id"));
            DBManager db = DBManager.getInstance();
            db.deleteEmployee(e_id);
            response.sendRedirect(request.getContextPath() + "/employees?d_id=" + d_id);
        }
        catch(Exception ex) {
        	ex.printStackTrace();
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}
