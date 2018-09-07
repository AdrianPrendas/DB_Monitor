
package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JavaRunCommand {
    //private static final String CMD = "create_dblink.bat dbladr adrian root 127.0.0.1 1521 XE";
    private static final String CMD = "transactions.bat t2 3";
    public static void main(String args[]) {

        try {
            // Run "netsh" Windows command
            Process process = Runtime.getRuntime().exec(CMD);

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
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}