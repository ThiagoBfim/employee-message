package com.aubay.touch.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "TB_EMPLOYEE_GROUP")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TX_NAME", nullable = false)
    private String name;

    @Column(name = "DT_DELIVERY", nullable = false)
    private LocalDateTime deliveryTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "TB_MESSAGE_GROUP",
        joinColumns = {@JoinColumn(name = "MESSAGE_ID")},
        inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")}
    )
    private Set<EmployeeGroup> groups = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "TB_DELIVERY_CHANNEL",
        joinColumns = {@JoinColumn(name = "MESSAGE_ID")},
        inverseJoinColumns = {@JoinColumn(name = "CHANNEL_ID")}
    )
    private Set<Channel> deliveryChannel = new HashSet<>();

    @PrePersist
    public void insertDate() {
        if (deliveryTime == null) {
            deliveryTime = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Set<EmployeeGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<EmployeeGroup> groups) {
        this.groups = groups;
    }

    public Set<Channel> getDeliveryChannel() {
        return deliveryChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
