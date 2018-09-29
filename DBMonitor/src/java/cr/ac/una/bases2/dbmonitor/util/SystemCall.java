
 package cr.ac.una.bases2.dbmonitor.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author _Adrián_Prendas_
 */
public class SystemCall {
    public static boolean createDBLink(
            String dblink,
            String user,
            String password,
            String hostname,
            String port,
            String service){
        try {
            String instructions = String.format("create_dblink.bat %s %s %s %s %s %s",dblink,user,password,hostname,port,service);
            Process process = Runtime.getRuntime().exec(instructions);

            // Get input streams
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Read command standard output
            String s;
            System.out.println("Standard output: ");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read command errors
            System.out.println("Standard error: ");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return true;
    }
}