package cr.ac.una.bases2.dbstoragemonitor.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cr.ac.una.bases2.dbstoragemonitor.dao.TablespaceDAO;
import cr.ac.una.bases2.dbstoragemonitor.dao.TablespaceDAOA;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author _Adrián_Prendas_
 */
@WebServlet(name = "DBAService", urlPatterns = {"/DBAService"})
public class DBAService extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");

            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            String json = "";
            

            String accion = request.getParameter("action");
            
System.out.println("accion: " + accion);
            switch (accion) {
                case "connect":
                    out.print("mensaje del servlet");
                    break;
                case "getTablespaces":
                    String str = TablespaceDAO.getInstance().getTablespaces().toString();
                    System.out.println(str);
                    out.print(str);
                    break;
                    
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
