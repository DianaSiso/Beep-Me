package server.beep.me.beepme.Entities;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer orderID;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column
    private LocalTime orderedTime;

    @Column
    private LocalTime possibleDeliveryTime;

    @Column(columnDefinition = "ENUM('READY', 'ORDERED', 'IN_PREPARATION', 'LATE')")
    @Enumerated(EnumType.STRING)
    private State state;

    

    public Order(Integer orderID, Restaurant restaurant, LocalTime orderedTime, LocalTime possibleDeliveryTime,
            State state) {
        this.orderID = orderID;
        this.restaurant = restaurant;
        this.orderedTime = orderedTime;
        this.possibleDeliveryTime = possibleDeliveryTime;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalTime getPossibleDeliveryTime() {
        return possibleDeliveryTime;
    }

    public void setPossibleDeliveryTime(LocalTime possibleDeliveryTime) {
        this.possibleDeliveryTime = possibleDeliveryTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    


    

    

    
    
}
