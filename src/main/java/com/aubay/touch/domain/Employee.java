package com.aubay.touch.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TX_NAME", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "TB_EMPLOYEE_GROUP",
        joinColumns = {@JoinColumn(name = "EMPLOYEE_ID")},
        inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")}
    )
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<DeliveryMessage> messagesDelivered = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<EmployeeChannel> employeeChannels = new HashSet<>();

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

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<DeliveryMessage> getMessagesDelivered() {
        return messagesDelivered;
    }

    public void setMessagesDelivered(Set<DeliveryMessage> messages) {
        this.messagesDelivered = messages;
    }

    public Set<EmployeeChannel> getEmployeeChannels() {
        return employeeChannels;
    }

    public void setEmployeeChannels(Set<EmployeeChannel> channels) {
        this.employeeChannels = channels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addMessage(DeliveryMessage message) {
        messagesDelivered.add(message);
    }
}
