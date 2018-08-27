package cr.ac.una.bases2.dbstoragemonitor.dao;

import org.json.JSONArray;

/**
 *
 * @author _Adrián_Prendas_
 */
public class TablespaceDAO {

    static String[] headers = {"Tables", "Max MB", "Free MB", "Used MB", "%SP", "SatSP", "SatMx"};
    static Object[][] data = {
        {"A1", 300, 250, 50, 80, "2d:2h", "4d:10h"},
        {"A2", 300, 250, 50, 80, "2d:2h", "4d:10h"},
        {"A3", 300, 250, 50, 80, "2d:2h", "4d:10h"},
        {"A4", 300, 250, 50, 80, "2d:2h", "4d:10h"}
    };
    static JSONArray arr = new JSONArray(data);

    public static JSONArray getTableSpaces() {

        for (Object e : arr) {
            System.out.println(e);
        }

        return arr;
    }

    public static JSONArray getRecord(String tablespace_name) {

        for (int i = 0; i < arr.length(); i++) {
            JSONArray arr2 = arr.getJSONArray(i);
            if (arr2.get(0).equals(tablespace_name)) {
                return arr2;
            }
        }
        return null;
    }

    public static JSONArray getTableSpaceNames() {

        JSONArray arr3 = new JSONArray();
        for (int i = 0; i < arr.length(); i++) {
            JSONArray arr2 = arr.getJSONArray(i);
            arr3.put(arr2.get(0));
        }
        return arr3;
    }

    public static void main(String[] args) {

        //System.out.println(getRecord("A3"));
        System.out.println(getTableSpaceNames());

    }
}
