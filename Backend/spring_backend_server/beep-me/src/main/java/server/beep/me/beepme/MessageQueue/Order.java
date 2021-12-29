package server.beep.me.beepme.MessageQueue;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @ToString
public class Order {
    
    private String code;
    private String restaurant;
    private String ordered;
    private String previsted;

    public Order(String code, String restaurant, String ordered, String previsted) {
        this.code = code;
        this.restaurant = restaurant;
        this.ordered = ordered;
        this.previsted = previsted;
    }

    public Order() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getOrdered() {
        return ordered;
    }

    public void setOrdered(String ordered) {
        this.ordered = ordered;
    }

    public String getPrevisted() {
        return previsted;
    }

    public void setPrevisted(String previsted) {
        this.previsted = previsted;
    }

    @Override
    public String toString() {
        return "Order [code=" + code + ", ordered=" + ordered + ", previsted=" + previsted + ", restaurant="
                + restaurant + "]";
    }

    

    
}
