
package cr.ac.una.bases2.dbmonitor.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cr.ac.una.bases2.dbmonitor.dao.UserDAO;
import cr.ac.una.bases2.dbmonitor.domain.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author _Adrian_Prendas_
 */
@WebServlet(name = "UserService", urlPatterns = {"/UserService"})
public class UserService extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
           

            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            String json = "";
            JSONObject obj = new JSONObject();
            

            String accion = request.getParameter("action");
            
System.out.println("accion: " + accion);
            switch (accion) {
                case "register":
                    User u = UserDAO.getInstance().create(
                            new User(
                                    request.getParameter("username"),
                                    request.getParameter("password")
                            ));
                    if(u==null){
                         obj = new JSONObject();
                        obj.put("status", "ER");
                        obj.put("response", "Error al crear el usuario");

System.out.println(obj.toString());
                        out.write(obj.toString());
                    }else{

                        obj = new JSONObject();
                        obj.put("status", "OK");
                        obj.put("response", "se creo el usuario");
                        obj.put("username", u.getUsername());
                        obj.put("password", u.getPassword());
                        
System.out.println(obj.toString());
                        out.write(obj.toString());
                    }
                    break;
                case "login":break;
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
