package server.beep.me.beepme.Forms;

public class RestForm {

    private String name;
    private Integer user_id;

    public RestForm() {
    }

    public RestForm(String name, Integer user_id) {
        this.name = name;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserID() {
        return user_id;
    }

    public void setUserID(Integer user_id) {
        this.user_id = user_id;
    }

}
