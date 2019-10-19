package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBManager;
 
@WebServlet("/Delete")
public class DeleteServlet extends HttpServlet {
     
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
         
        try {
            int id = Integer.parseInt(request.getParameter("id"));          
            DBManager db = DBManager.getInstance();
            db.deleteDepartment(id);
            response.sendRedirect(request.getContextPath() + "/Index");
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
    }
}
