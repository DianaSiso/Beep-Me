package server.beep.me.beepme.Forms;

public class LoginResponseForm {
    
    private String status;

    private Integer rest_id;

    private Integer manager;

    public LoginResponseForm() {
    }

    public LoginResponseForm(String status, Integer rest_id, Integer manager) {
        this.status = status;
        this.rest_id = rest_id;
        this.manager = manager;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRest_id() {
        return rest_id;
    }

    public void setRest_id(Integer rest_id) {
        this.rest_id = rest_id;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }
    
}
