
package cr.ac.una.bases2.dbmonitor.dao;

import cr.ac.una.bases2.dbmonitor.domain.User;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author a6r1a
 */
public class UserDAO extends ABaseDAO implements IBaseCRUD<String, User> {
    private static UserDAO uniqueInstance;
    private static final String REGISTER = "{call createUser(?,?)}";
    
    private static final String READ_PRODUCTO_BY_TYPE = "{?=call buscarProductoTipo(?)}";
    private static final String READ_PRODUCTO_BY_NAME = "{?=call buscarProductoNombre(?)}";
    private static final String READ_PRODUCTO_BY_PK = "{?=call buscarProductoCodigo(?)}";
    private static final String READ_ALL_PRODUCTS = "{?=call listaProductos()}";
    private static final String UPDATE_PRODUCT = "{call modificarProducto(?,?,?,?,?)}";
    private static final String DELETE_PRODUCT = "{call eliminarProductos(?)}";
    
    private static final String LIST_PRODUCTS = "{?=call listaProductos()}";
    
    private UserDAO(){
        super();
    }
    
    public static UserDAO getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new UserDAO();
        }
        return uniqueInstance;
    }
    

    @Override
    public User create(User user) {
        CallableStatement pstmt = null;
        boolean resp = true;
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha localizado el driver");
            e.printStackTrace();
            resp=false;
        } catch (SQLException e) {
            System.out.println("La base de datos no se encuentra disponible");
            e.printStackTrace();
            resp=false;
        }
        try{
            pstmt = conexion.prepareCall(REGISTER);       
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());
            pstmt.execute();//retorna true o false
        } catch (SQLException e) {
            System.out.println("Llave duplicada");
            e.printStackTrace();
            resp=false;
        }
        finally{
            try {
                if (pstmt!=null)
                    pstmt.close();                                    
                desconectar();
            } catch (SQLException e) {
                System.out.println("Estatutos invalidos o nulos");
                e.printStackTrace();
                resp=false;
            }
        }
        if(resp){
            System.out.println("se creo con exito: "+user.toString());
            return user;
        }else{
                return null;
        }
    }

    @Override
    public User read(String k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
