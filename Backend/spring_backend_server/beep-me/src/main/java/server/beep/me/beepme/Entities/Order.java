package server.beep.me.beepme.Entities;

import java.time.LocalDateTime;

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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@NamedQuery(name = "Order.findByCode", query = "SELECT o FROM Order o WHERE o.code = ?1")
@Table(name = "orders")
public class Order {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column
    private LocalDateTime orderedTime;

    @Column
    private LocalDateTime possibleDeliveryTime;

    @Column
    private String code;

    @Column(columnDefinition = "ENUM('READY', 'ORDERED', 'LATE', 'DELIVERED', 'NON_DELIVERED')")
    @Enumerated(EnumType.STRING)
    private State state;

    @Column
    private boolean late;

    public Order() {
    }

    public Order(Restaurant restaurant, LocalDateTime orderedTime, LocalDateTime possibleDeliveryTime, String code,
            State state, boolean late) {
        this.restaurant = restaurant;
        this.orderedTime = orderedTime;
        this.possibleDeliveryTime = possibleDeliveryTime;
        this.code = code;
        this.state = state;
        this.late = late;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalDateTime getPossibleDeliveryTime() {
        return possibleDeliveryTime;
    }

    public void setPossibleDeliveryTime(LocalDateTime possibleDeliveryTime) {
        this.possibleDeliveryTime = possibleDeliveryTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean getLate() {
        return late;
    }

    public void setLate(boolean late) {
        this.late = late;
    }

    @Override
    public String toString() {
        return "Order [code=" + code + ", id=" + id + ", orderedTime=" + orderedTime + ", possibleDeliveryTime="
                + possibleDeliveryTime + ", restaurant=" + restaurant + ", state=" + state + ", late=" + late + "]";
    }

    
}
