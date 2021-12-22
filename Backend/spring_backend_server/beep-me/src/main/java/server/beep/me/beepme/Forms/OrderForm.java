package server.beep.me.beepme.Forms;

public class OrderForm {

    private Integer orderID;

    private Integer restaurant_id;

    private String orderedTime;

    private String possibleDeliveryTime;

    private String state;

    

    public OrderForm() {
    }

    public OrderForm(Integer orderID, Integer restaurant_id, String orderedTime, String possibleDeliveryTime,
            String state) {
        this.orderID = orderID;
        this.restaurant_id = restaurant_id;
        this.orderedTime = orderedTime;
        this.possibleDeliveryTime = possibleDeliveryTime;
        this.state = state;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
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
