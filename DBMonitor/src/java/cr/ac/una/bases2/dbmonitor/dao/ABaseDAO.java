package cr.ac.una.bases2.dbmonitor.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author _Adrián_Prendas_
 */
abstract class ABaseDAO {
    
    protected Connection conexion= null; 
    
    protected ABaseDAO() {
        
    }
    
    protected void conectar() throws SQLException,ClassNotFoundException 
    {   
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conexion = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/DBLG2","sys as sysdba","root");

    }
    
    protected void desconectar() throws SQLException{
        if(!conexion.isClosed())
            conexion.close();       
    }
}
