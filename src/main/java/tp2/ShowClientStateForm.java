package tp2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DAOException;
import simplejdbc.DataSourceFactory;

@WebServlet(name = "ShowClientStateForm", urlPatterns = {"/ShowClientStateForm"})
public class ShowClientStateForm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String state = request.getParameter("stateCode");
        DAO dao = new DAO(DataSourceFactory.getDataSource());
        //Création du DAO avec source de données
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StateForm</title>");
            out.println("</head>");
            out.println("<body>");
            try {

                List<String> states = dao.listStates();
                if (states.isEmpty()) {
                    throw new Exception("Erreur sql");
                }
                out.println("<form action='ShowClientInState'>");
                out.println("<select name= 'StateCode'>");
                for (String s : states) {
                    out.println("<option value=\"" + s + "\">" + s + "</option>");
                }
                out.println("</select>");
                out.println("<input type='submit'>");
                out.println("</form>");

            } catch (Exception e) {
                out.printf("Erreur : %s", e.getMessage());
            }

            if (!state.isEmpty()) {
                state = state.toUpperCase();
                out.println("<p>Les clients aux : " + state + "</p>");
            }
            try {
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th>\n<th>Name</th>\n<th>Address</th></tr>");
                for (CustomerEntity e : dao.customersInState(state)) {
                    out.println("<tr><td>" + e.getCustomerId() + "</td>");
                    out.println("<td>" + e.getName() + "</td>");
                    out.println("<td>" + e.getAddressLine1() + "</td></tr>");
                }
                out.println("</table>");

                out.printf("<hr><a href='%s'>Retour au menu</a>", request.getContextPath());
                out.println("</body>");
                out.println("</html>");

            } catch (Exception ex) {
                Logger.getLogger("StateForm").log(Level.SEVERE, "Erreur de traitement", ex);
            }
        }
    }
            /**
             * Handles the HTTP <code>GET</code> method.
             *
             * @param request servlet request
             * @param response servlet response
             * @throws ServletException if a servlet-specific error occurs
             * @throws IOException if an I/O error occurs
             */
            @Override
            protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
                processRequest(request, response);
            }

            /**
             * Handles the HTTP <code>POST</code> method.
             *
             * @param request servlet request
             * @param response servlet response
             * @throws ServletException if a servlet-specific error occurs
             * @throws IOException if an I/O error occurs
             */
            @Override
            protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
                processRequest(request, response);
            }

            /**
             * Returns a short description of the servlet.
             *
             * @return a String containing servlet description
             */
            @Override
            public String getServletInfo
        
            
            
                () {
		return "Short description";
            }
        }

