package server.beep.me.beepme.Forms;

public class LoginResponseForm {
    
    private String status;

    private Integer rest_id;

    public LoginResponseForm() {
    }

    public LoginResponseForm(String status, Integer rest_id) {
        this.status = status;
        this.rest_id = rest_id;
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

    
}
