create table TB_CHANNEL
(
    ID      BIGINT auto_increment
        primary key,
    TX_NAME CHARACTER VARYING(255) not null
        constraint UK_52H4YYGTWM3CA3LRJIEMSWHLD
            unique
);

create table TB_EMPLOYEE
(
    ID      BIGINT auto_increment
        primary key,
    TX_NAME CHARACTER VARYING(255) not null
);

create table TB_EMPLOYEE_CHANNEL
(
    ID            BIGINT auto_increment
        primary key,
    TX_IDENTIFIER CHARACTER VARYING(255) not null,
    CHANNEL_ID    BIGINT,
    EMPLOYEE_ID   BIGINT,
    constraint FK1F9PH7GTIB83YWBAD7KOER2H9
        foreign key (EMPLOYEE_ID) references TB_EMPLOYEE,
    constraint FKPYAIKW4QCE8QB3BWX190NGH23
        foreign key (CHANNEL_ID) references TB_CHANNEL
);

create table TB_GROUP
(
    ID      BIGINT auto_increment
        primary key,
    TX_NAME CHARACTER VARYING(255) not null
        constraint UK_CNQ204PVGVXJ4LRT0M0X43UIT
            unique
);

create table RL_EMPLOYEE_GROUP
(
    EMPLOYEE_ID BIGINT not null,
    GROUP_ID    BIGINT not null,
    primary key (EMPLOYEE_ID, GROUP_ID),
    constraint FKCCMNAM10V34SI0P4DMWPSF15H
        foreign key (GROUP_ID) references TB_GROUP,
    constraint FKS2O71OJ0IM9FHNIPA8HPOSIH0
        foreign key (EMPLOYEE_ID) references TB_EMPLOYEE
);

create table TB_MESSAGE
(
    ID          BIGINT auto_increment
        primary key,
    DT_DELIVERY TIMESTAMP              not null,
    TX_MESSAGE  CHARACTER VARYING(255) not null,
    TX_TITLE    CHARACTER VARYING(255) not null
);

create table RL_DELIVERY_CHANNEL
(
    MESSAGE_ID BIGINT not null,
    CHANNEL_ID BIGINT not null,
    primary key (MESSAGE_ID, CHANNEL_ID),
    constraint FK8NUBUIWJ3I21OHR8P7D31DG6A
        foreign key (CHANNEL_ID) references TB_CHANNEL,
    constraint FKNAMWVYW0WSGM5EJ70539X8T5K
        foreign key (MESSAGE_ID) references TB_MESSAGE
);

create table RL_MESSAGE_GROUP
(
    MESSAGE_ID BIGINT not null,
    GROUP_ID   BIGINT not null,
    primary key (MESSAGE_ID, GROUP_ID),
    constraint FKKN41J2CP57J8DNVHXEH6ESKHE
        foreign key (GROUP_ID) references TB_GROUP,
    constraint FKQC5YB6U459HYTECMMPONWIX8L
        foreign key (MESSAGE_ID) references TB_MESSAGE
);

create table TB_DELIVERY_MESSAGE
(
    ID          BIGINT auto_increment
        primary key,
    DT_DELIVERY TIMESTAMP               not null,
    TX_ERROR    CHARACTER VARYING(5000) not null,
    ST_SUCCESS  BOOLEAN                 not null,
    EMPLOYEE_ID BIGINT,
    MESSAGE_ID  BIGINT,
    constraint FKFMQC90RGOTYO3FRTY31FULWI0
        foreign key (MESSAGE_ID) references TB_MESSAGE,
    constraint FKHOOE54J1AC1MOMWYSMVGLT67X
        foreign key (EMPLOYEE_ID) references TB_EMPLOYEE
);

