package cr.ac.una.bases2.dbmonitor.domain;

/**
 *
 * @author a6r1a
 */
public class User implements Jsonable{
    String username;
    String password;
    int access_level;
    
    public User(){}
    
        
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.access_level = 2;
    }

    public User(String username, String password, int access_level){
        this.username = username;
        this.password = password;
        this.access_level = access_level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccess_level() {
        return access_level;
    }

    public void setAccess_level(int access_level) {
        this.access_level = access_level;
    }
    
    
    public String toString(){
        return "{username:"+username
                +", passwrod:"+password
                +", access_level:"+access_level+"}";
    }
    
}
