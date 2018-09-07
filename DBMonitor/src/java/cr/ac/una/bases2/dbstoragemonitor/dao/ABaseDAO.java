package cr.ac.una.bases2.dbstoragemonitor.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
        conexion = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/DBLG2","sys as sysdba","k1n9r4d2");

    }
    
    protected void desconectar() throws SQLException{
        if(!conexion.isClosed())
            conexion.close();       
    }
    
    
    
    
     public Connection getConnection(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return  DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/DBLG2","sys as sysdba","k1n9r4d2");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        } 
        return null;
    }
    
    
}
