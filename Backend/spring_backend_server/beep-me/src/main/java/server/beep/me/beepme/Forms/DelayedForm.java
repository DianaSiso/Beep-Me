package server.beep.me.beepme.Forms;

public class DelayedForm {

    Integer order_id;
    Integer minutes;
    
    public DelayedForm() {
    }

    public DelayedForm(Integer order_id, Integer minutes) {
        this.order_id = order_id;
        this.minutes = minutes;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    
    
}
