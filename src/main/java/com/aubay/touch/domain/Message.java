package com.aubay.touch.domain;

import com.aubay.touch.controller.response.MessageResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@NamedNativeQuery(
        name = "MessageResponse",
        query = """
                    select M.ID as id, 
                    M.TX_TITLE as title,
                    M.TX_MESSAGE as message,
                    M.DT_DELIVERY as deliveryDate,
                    (SELECT COUNT(TDM.ID) FROM TB_DELIVERY_MESSAGE TDM WHERE TDM.ID = M.ID AND TDM.ST_SUCCESS = false) as deliverySuccess,
                    (SELECT COUNT(TDM.ID) FROM TB_DELIVERY_MESSAGE TDM WHERE TDM.ID = M.ID) as deliveryFailed
                    from TB_MESSAGE M
                """,
        resultSetMapping = "MessageResponse"
)
@SqlResultSetMapping(
        name = "MessageResponse",
        classes = @ConstructorResult(
                targetClass = MessageResponse.class,
                columns = {
                        @ColumnResult(name = "id"),
                        @ColumnResult(name = "title"),
                        @ColumnResult(name = "message"),
                        @ColumnResult(name = "deliveryDate"),
                        @ColumnResult(name = "deliverySuccess"),
                        @ColumnResult(name = "deliveryFailed")
                }
        )
)
@Entity
@Table(name = "TB_MESSAGE")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TX_TITLE", nullable = false)
    private String title;

    @Column(name = "TX_MESSAGE", nullable = false)
    private String message;

    @Column(name = "DT_DELIVERY", nullable = false)
    private LocalDateTime deliveryTime;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "RL_MESSAGE_GROUP",
            joinColumns = {@JoinColumn(name = "MESSAGE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")}
    )
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "RL_DELIVERY_CHANNEL",
            joinColumns = {@JoinColumn(name = "MESSAGE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CHANNEL_ID")}
    )
    private Set<Channel> deliveryChannel = new HashSet<>();

    public Message() {
        /*Default*/
    }

    public Message(String title, String message, String groups, String channels) {
        this.title = title;
        this.message = message;
        Arrays.stream(groups.split("/")).forEach(name -> this.groups.add(new Group(name)));
        Arrays.stream(channels.split("/")).forEach(name -> this.deliveryChannel.add(new Channel(name)));
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Channel> getDeliveryChannel() {
        return deliveryChannel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDeliveryChannel(Set<Channel> deliveryChannel) {
        this.deliveryChannel = deliveryChannel;
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


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", groups=" + groups +
                ", deliveryChannel=" + deliveryChannel +
                '}';
    }
}
