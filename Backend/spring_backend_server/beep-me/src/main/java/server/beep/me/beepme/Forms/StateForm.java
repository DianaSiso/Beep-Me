package server.beep.me.beepme.Forms;

public class StateForm {

    Integer order_id;
    String state;

    public StateForm() {
    }

    public StateForm(Integer order_id, String state) {
        this.order_id = order_id;
        this.state = state;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    

    
    
}
