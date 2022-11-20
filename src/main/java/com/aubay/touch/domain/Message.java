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
                       CAST(M.ST_STATUS AS VARCHAR) as status,
                       M.TX_MESSAGE as message,
                       M.DT_DELIVERY as deliveryDate,
                       (SELECT string_agg(TG.TX_NAME, ',') FROM RL_MESSAGE_GROUP G INNER JOIN TB_GROUP TG on G.GROUP_ID = TG.TX_NAME WHERE G.MESSAGE_ID = M.ID) as groups,
                       (SELECT string_agg(TC.TX_NAME, ',') FROM RL_DELIVERY_CHANNEL C INNER JOIN TB_CHANNEL TC on C.CHANNEL_ID = TC.TX_NAME WHERE C.MESSAGE_ID = M.ID) as channels,
                       (SELECT COUNT(TDM.ID) FROM TB_DELIVERY_MESSAGE TDM WHERE TDM.MESSAGE_ID = M.ID AND TDM.ST_SUCCESS = true) as deliverySuccess,
                       (SELECT COUNT(TDM.ID) FROM TB_DELIVERY_MESSAGE TDM WHERE TDM.MESSAGE_ID = M.ID AND TDM.ST_SUCCESS = false) as deliveryFailed
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
                        @ColumnResult(name = "status"),
                        @ColumnResult(name = "message"),
                        @ColumnResult(name = "deliveryDate"),
                        @ColumnResult(name = "groups"),
                        @ColumnResult(name = "channels"),
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

    @Column(name = "TX_TITLE")
    private String title;

    @Column(name = "TX_MESSAGE", nullable = false)
    private String message;

    @Column(name = "DT_DELIVERY", nullable = false)
    private LocalDateTime deliveryTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ST_STATUS", nullable = false)
    private MessageStatus status;

    @Column(name = "DT_CREATE", nullable = false)
    private LocalDateTime dtCreate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "RL_MESSAGE_GROUP",
            joinColumns = {@JoinColumn(name = "MESSAGE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")}
    )
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
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
        Arrays.stream(groups.split(",")).forEach(name -> this.groups.add(new Group(name)));
        Arrays.stream(channels.split(",")).forEach(name -> this.deliveryChannel.add(new Channel(name)));
    }

    @PrePersist
    public void insertDate() {
        LocalDateTime now = LocalDateTime.now();
        if (deliveryTime == null) {
            deliveryTime = now;
        }
        this.dtCreate = now;
        this.status = MessageStatus.PENDING;
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

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
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
