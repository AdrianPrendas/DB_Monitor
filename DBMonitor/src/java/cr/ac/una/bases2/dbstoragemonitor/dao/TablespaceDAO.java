package cr.ac.una.bases2.dbstoragemonitor.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
/**
 *
 * @author _Adrián_Prendas_
 */
public class TablespaceDAO extends ABaseDAO{
     JSONArray tablespaces = new JSONArray();
     JSONArray tnames = new JSONArray();
     List<String> tnamesS = new ArrayList();
     JSONArray sat = new JSONArray();
    public static TablespaceDAO instance;
    
    private static final String TABLESPACES = "{?= call storageInfo()}";
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
                tnamesS.add(rs.getString("tablespace"));
                tuple.put(rs.getInt("tam"));
                tuple.put(rs.getFloat("free"));
                tuple.put(rs.getFloat("used"));
                
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
    
    /*public ResultSet executeQuery(String statement) throws ClassNotFoundException{
      try {
          Statement stm = conexion.createStatement();
          return stm.executeQuery(statement);
      } catch (SQLException ex) {
      }
      return null;
    }*/
     public ResultSet executeQuery(String statement) throws ClassNotFoundException{
      try {
          conectar();
          Statement stm = conexion.createStatement();
          return stm.executeQuery(statement);
      } catch (SQLException ex) {
      }
      return null;
    }
    public JSONArray getTabNames() throws Exception{
         String sql="select tablespace_name " +
        "from DBA_TABLESPACES t " +
        "where t.tablespace_name != 'SYSTEM' " +
        "and t.tablespace_name != 'SYSAUX' " +
        "and t.tablespace_name != 'USERS' " +
        "and t.tablespace_name != 'TEMP' " +
        "and t.tablespace_name != 'UNDOTBS1'";
       
        sql = String.format(sql);
        ResultSet rs = executeQuery(sql);
         while(rs.next()){
             JSONArray tuple = new JSONArray();
             tuple.put(rs.getString("tablespace_name"));
             tnames.put(tuple);
        }
        return tnames; 
    }
     public JSONArray getSaturacion(String tbname) throws Exception{
         String sql="SELECT calcula_sat_sp_dias('"+tbname+"') \"saturacion\" " +
        "FROM dual union all " +
        "SELECT calcula_sat_sp_horas('"+tbname+"')  " +
        "FROM dual union all " +
        "SELECT calcula_sat_total_dias ('"+tbname+"') " +
        "FROM dual union all " +
        "SELECT calcula_sat_total_horas ('"+tbname+"') " +
        "FROM dual";
        sql = String.format(sql);
        ResultSet rs = executeQuery(sql);
         while(rs.next()){
             JSONArray tuple = new JSONArray();
             tuple.put(rs.getString("saturacion"));
             sat.put(tuple);
        }
        return sat; 
    }
    
      public JSONArray getALLSaturacion() throws Exception{
           getTablespaces();
           ResultSet rs = null;
          for(int i=0; i<tnames.length();i++){
         String sql="SELECT calcula_sat_sp_dias('"+tnames.get(i).toString()+"') \"saturacion\" " +
        "FROM dual union all " +
        "SELECT calcula_sat_sp_horas('"+tnames.get(i)+"')  " +
        "FROM dual union all " +
        "SELECT calcula_sat_total_dias ('"+tnames.get(i)+"') " +
        "FROM dual union all " +
        "SELECT calcula_sat_total_horas ('"+tnames.get(i)+"') " +
        "FROM dual";
        sql = String.format(sql);
        rs = executeQuery(sql);
        
         while(rs.next()){
             JSONArray tuple = new JSONArray();
             tuple.put(rs.getString("saturacion"));
             sat.put(tuple);
        }
       }
        return sat; 
    }
    
    
}