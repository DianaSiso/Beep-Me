package server.beep.me.beepme.Forms;

public class OrderMobile {

    private String code;
    private Integer orderID;
    private Integer restaurant_id;
    private String restaurantName;
    private String possibleDeliveryTime;
    private String state;
    
    public OrderMobile() {
    }

    public OrderMobile(String code, Integer orderID, Integer restaurant_id, String restaurantName, String possibleDeliveryTime,
            String state) {
        this.code = code;
        this.orderID = orderID;
        this.restaurant_id = restaurant_id;
        this.restaurantName = restaurantName;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    
    
}
