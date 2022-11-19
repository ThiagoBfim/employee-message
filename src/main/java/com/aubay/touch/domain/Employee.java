package com.aubay.touch.domain;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(name = "TB_EMPLOYEE")
@NamedEntityGraph(name = "Employee.detail",
        attributeNodes = {@NamedAttributeNode("groups"), @NamedAttributeNode("messagesDelivered")})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TX_NAME", nullable = false)
    private String name;
    @Column(name = "DT_CREATE", nullable = false)
    private LocalDateTime dtCreate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "RL_EMPLOYEE_GROUP",
            joinColumns = {@JoinColumn(name = "EMPLOYEE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")}
    )
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<DeliveryMessage> messagesDelivered = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeChannel> employeeChannels = new HashSet<>();

    public Employee(String name, String groups, Set<EmployeeChannel> employeeChannels) {
        this.name = name;
        Arrays.stream(groups.split(",")).forEach(groupName -> this.groups.add(new Group(groupName)));
        this.employeeChannels = employeeChannels;
        this.employeeChannels.forEach(e -> e.setEmployee(this));

    }

    @PrePersist
    public void insertDate() {
        this.dtCreate = LocalDateTime.now();
    }

    public Employee() {
        /*Default*/
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

    public String getEmployeeChannelsSeparatedByComma() {
        return getEmployeeChannels().stream().map(e -> e.getChannel().getName()).collect(Collectors.joining(","));
    }

    public String getEmployeeGroupsSeparatedByComma() {
        return getGroups().stream().map(Group::getName).collect(Collectors.joining(","));
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(employeeChannels, employee.employeeChannels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, employeeChannels);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groups=" + groups +
                ", messagesDelivered=" + messagesDelivered +
                ", employeeChannels=" + employeeChannels +
                '}';
    }

    public void addMessage(DeliveryMessage message) {
        message.setEmployee(this);
        messagesDelivered.add(message);
    }
}
