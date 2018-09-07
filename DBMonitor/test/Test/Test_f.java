package Test;

import com.google.gson.Gson;
import cr.ac.una.bases2.dbstoragemonitor.dao.SGADAO;
import cr.ac.una.bases2.dbstoragemonitor.dao.TablespaceDAO;
import cr.ac.una.bases2.dbstoragemonitor.util.SystemCall;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author _Adrián_Prendas_
 */
public class Test_f {
    
    
    public static void main(String[] args) {
        
        JSONArray tablespaces  = SGADAO.getInstance().getBufferInfo();
                //TablespaceDAO.getInstance().getTablespaces();
        for(int i=0;i<tablespaces.length();i++){
            System.out.println(tablespaces.get(i));
        }
        
        //SystemCall.createDBLink("adr3", "adr3", "adr3", "localhost", "3456", "xe");
    }
}
