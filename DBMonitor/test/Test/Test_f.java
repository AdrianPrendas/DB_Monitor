package Test;

import cr.ac.una.bases2.dbstoragemonitor.dao.SGADAO;
import cr.ac.una.bases2.dbstoragemonitor.dao.TablespaceDAO;
import org.json.JSONArray;
/**
 *
 * @author _Adrián_Prendas_
 */
public class Test_f {
    
    public static void main(String[] args) throws Exception {
       TablespaceDAO td = new TablespaceDAO();
        JSONArray tablespaces  = SGADAO.getInstance().getBufferInfo();
               
        for(int i=0;i<tablespaces.length();i++){
            System.out.println(tablespaces.get(i));
        }
        
        JSONArray tnames = td.getTabNames();
        System.out.println(tnames.length());
        for(int i=0;i<tnames.length();i++){
            System.out.println(tnames.get(i));
        }
        
        
        
        JSONArray saturacion = td.getSaturacion("A4");
        System.out.println(saturacion.length());
        for(int i=0;i<saturacion.length();i++){
            System.out.println(saturacion.get(i));
        }
    }
}
