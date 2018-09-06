
package cr.ac.una.bases2.dbstoragemonitor.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
import org.json.JSONArray;

/**
 *
 * @author _Adrián_Prendas_
 */
public class SGADAO extends ABaseDAO{
 public static SGADAO instance;
    
    private static final String BUFFER = "{?= call bufferInfo()}";
    
    private SGADAO(){
        super();
    }
    
    public static SGADAO getInstance(){
        if(instance==null){
            instance = new SGADAO();
        }
        return instance;
    }
    
    
    public JSONArray getBufferInfo() {
        ResultSet rs = null;
        JSONArray tablespaces = new JSONArray();
        
        CallableStatement pstmt=null;  
        try {
            conectar();
        }catch (ClassNotFoundException e) {
            System.out.println("No se ha localizado el driver");
        } catch (SQLException e) {
            System.out.println("La base de datos no se encuentra disponible");
        }
        try{
            pstmt = conexion.prepareCall(BUFFER);                
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);            
            pstmt.execute();
            rs = (ResultSet)pstmt.getObject(1); 
            while (rs.next()) {
                JSONArray tuple = new JSONArray();
                tuple.put(rs.getString("name"));
                tuple.put(rs.getInt("bytes"));
                tablespaces.put(tuple);
            }
        }
        catch (SQLException e) {
            System.out.println("Sentencia no valida");
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                System.out.println("Estatutos invalidos o nulos");
               e.printStackTrace();
            }
        }
        return tablespaces;
    }
}
   
