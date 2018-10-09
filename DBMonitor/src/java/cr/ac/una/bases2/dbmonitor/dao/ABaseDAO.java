package cr.ac.una.bases2.dbmonitor.dao;

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
<<<<<<< HEAD:DBMonitor/src/java/cr/ac/una/bases2/dbstoragemonitor/dao/ABaseDAO.java
        conexion = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/DBLG2","sys as sysdba","k1n9r4d2");
=======
        conexion = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/DBLG2","sys as sysdba","root");
>>>>>>> eb67ec53513fa3a6a1f80fc0f5fe2deb1cf535a4:DBMonitor/src/java/cr/ac/una/bases2/dbmonitor/dao/ABaseDAO.java

    }
    
    protected void desconectar() throws SQLException{
        if(!conexion.isClosed())
            conexion.close();       
    }
}