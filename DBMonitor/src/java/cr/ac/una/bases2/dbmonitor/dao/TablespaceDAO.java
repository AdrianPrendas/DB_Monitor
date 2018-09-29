package cr.ac.una.bases2.dbmonitor.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OracleTypes;
import org.json.JSONArray;

/**
 *
 * @author _Adrián_Prendas_
 */
public class TablespaceDAO extends ABaseDAO{
    
    public static TablespaceDAO instance;
    
    private static final String TABLESPACES = "{?= call storageInfo()}";
    private static final String SATURACION = "{?= call saturaciones(?)}";
    
    private TablespaceDAO(){
        super();
    }
    
    public static TablespaceDAO getInstance(){
        if(instance==null){
            instance = new TablespaceDAO();
        }
        return instance;
    }
    
    
    public JSONArray getTablespaces() {
        JSONArray tablespaces = new JSONArray();
        ResultSet rs = null;
        CallableStatement pstmt=null;  
        try {
            conectar();
        }catch (ClassNotFoundException e) {
            System.out.println("No se ha localizado el driver");
        } catch (SQLException e) {
            System.out.println("La base de datos no se encuentra disponible");
        }
        try{
            pstmt = conexion.prepareCall(TABLESPACES);                
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);            
            pstmt.execute();
            rs = (ResultSet)pstmt.getObject(1); 
            while (rs.next()) {
                JSONArray tuple = new JSONArray();
                tuple.put(rs.getString("tablespace"));
                tuple.put(rs.getInt("tam"));
                tuple.put(rs.getFloat("free"));
                tuple.put(rs.getFloat("used"));
                
                tablespaces.put(tuple);
            }
            tablespaces = completeTable(tablespaces);
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
    
    public JSONArray completeTable(JSONArray t){
        //System.out.println(t);
        for(int i=0;i<t.length();i++){
            String key = t.getJSONArray(i).getString(0);
            //System.out.println(key);
            try{
                JSONArray c = getSaturacion(key);  
                //System.out.println(c);
                for(int j=0;j<c.length();j++){
                    t.getJSONArray(i).put(c.get(j));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        return t;
    }
    
    
    public JSONArray getSaturacion(String tbname) throws Exception{
        JSONArray sat = new JSONArray();
        ResultSet rs = null;
        CallableStatement pstmt=null;  
        
        try {
            conectar();
        }catch (ClassNotFoundException e) {
            System.out.println("No se ha localizado el driver");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("La base de datos no se encuentra disponible");
        }
        try{
            pstmt = conexion.prepareCall(SATURACION);                
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);       
            pstmt.setString(2,tbname);
            pstmt.execute();
            rs = (ResultSet)pstmt.getObject(1); 
            while (rs.next()) {
                sat.put(rs.getString("sat"));
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
        return sat;
    }
   
}
