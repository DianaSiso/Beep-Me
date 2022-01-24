package generation;

import java.time.LocalDateTime;

class Order {

    private String code;
    private String restName;
    private LocalDateTime orderDateTime;
    private LocalDateTime previstDateTime;

    public Order(String code, String restName, LocalDateTime orderDateTime, LocalDateTime previstDateTime) {
        this.code = code;
        this.restName = restName;
        this.orderDateTime = orderDateTime;
        this.previstDateTime = previstDateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public LocalDateTime getPrevistDateTime() {
        return previstDateTime;
    }

    public void setPrevistDateTime(LocalDateTime previstDateTime) {
        this.previstDateTime = previstDateTime;
    }

    @Override
    public String toString() {
        return "Order [code=" + code + ", orderDateTime=" + orderDateTime + ", previstDateTime=" + previstDateTime
                + ", restName=" + restName + "]";
    }

    
    
    
}