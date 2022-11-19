package com.aubay.touch.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CHANNEL")
public class Channel {

    @Id
    @Column(name = "TX_NAME", unique = true, nullable = false)
    private String name;

    public Channel(String name) {
        this.name = name;
    }

    public Channel() {
        /*default*/
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(name, channel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                '}';
    }
}
