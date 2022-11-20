package com.aubay.touch.domain;

import com.aubay.touch.repository.ChannelRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "TB_DELIVERY_MESSAGE")
public class DeliveryMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DT_DELIVERY", nullable = true)
    private LocalDateTime deliveryTime;

    @Column(name = "ST_SUCCESS", nullable = false)
    private Boolean success;

    @Column(name = "TX_ERROR", nullable = true, length = 5000)
    private String error;

    @Column(name = "DT_CREATE", nullable = false)
    private LocalDateTime dtCreate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
    private Channel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID")
    private Message message;

    public DeliveryMessage() {
        /*Default*/
    }

    public DeliveryMessage(Message message) {
        this.message = message;
    }

    @PrePersist
    public void insertDate() {
        this.dtCreate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        setSuccess(Boolean.FALSE);
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryMessage that = (DeliveryMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(deliveryTime, that.deliveryTime) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliveryTime, message);
    }
}
