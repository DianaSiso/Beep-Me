package server.beep.me.beepme.Forms;

public class OrderForm {

    private String code;

    private Integer restaurant_id;

    private String orderedTime;

    private String possibleDeliveryTime;

    private String state;

    public OrderForm() {
    }

    public OrderForm(String code, Integer restaurant_id, String orderedTime, String possibleDeliveryTime,
            String state) {
        this.code = code;
        this.restaurant_id = restaurant_id;
        this.orderedTime = orderedTime;
        this.possibleDeliveryTime = possibleDeliveryTime;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(String orderedTime) {
        this.orderedTime = orderedTime;
    }

    public String getPossibleDeliveryTime() {
        return possibleDeliveryTime;
    }

    public void setPossibleDeliveryTime(String possibleDeliveryTime) {
        this.possibleDeliveryTime = possibleDeliveryTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

   
}
