package server.beep.me.beepme.Forms;

public class UserForm {

    private String username;
    private String password;
    private String manager;
    
    public UserForm() {
    }

    public UserForm(String username, String password, String manager) {
        this.username = username;
        this.password = password;
        this.manager = manager;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "UserForm [manager=" + manager + ", password=" + password + ", username=" + username + "]";
    }

    
    
}
