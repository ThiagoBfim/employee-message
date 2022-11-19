package com.aubay.touch.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_EMPLOYEE_CHANNEL")
public class EmployeeChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TX_IDENTIFIER", nullable = false)
    private String identifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
    private Channel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    public EmployeeChannel(Channel channel, String identifier) {
        this.channel = channel;
        this.identifier = identifier;
    }

    public EmployeeChannel() {
        /*Default*/
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeChannel that = (EmployeeChannel) o;
        return Objects.equals(identifier, that.identifier) && Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, channel);
    }

    @Override
    public String toString() {
        return "EmployeeChannel{" +
                "id=" + id +
                ", identifier='" + identifier + '\'' +
                ", channel=" + channel +
                '}';
    }

    public String getChannelName() {
        return channel.getName();
    }


}
