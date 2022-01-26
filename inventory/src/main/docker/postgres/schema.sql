create sequence INVENTORY_ID_SEQUENCE start 1 increment 50;

create table app_permission
(
    id          int8         not null,
    version     int8         not null,
    code        varchar(4)   not null,
    description varchar(100) not null,
    primary key (id)
);

create table app_role
(
    id      int8        not null,
    version int8        not null,
    code    varchar(3)  not null,
    name    varchar(30) not null,
    primary key (id)
);

create table app_role_permission
(
    roleId         int8       not null,
    permissionCode varchar(4) not null,
    primary key (roleId, permissionCode)
);

create table app_user
(
    id       int8         not null,
    version  int8         not null,
    enabled  boolean      not null,
    password varchar(400) not null,
    username varchar(20)  not null,
    primary key (id)
);

create table app_user_role
(
    userId int8 not null,
    roleId int8 not null,
    primary key (userId, roleId)
);

create table Stock
(
    id           int8           not null,
    version      int8           not null,
    currentPrice numeric(19, 2) not null,
    lastUpdate   timestamp      not null,
    name         varchar(255)   not null,
    primary key (id)
);

alter table app_permission
    add constraint UK_3elog7l6e0y599004obtk1p2 unique (code);

alter table app_role
    add constraint UK_6c7277x8rt1ir7tgi431hfuqx unique (code);

alter table app_user
    add constraint UK_3k4cplvh82srueuttfkwnylq0 unique (username);

alter table Stock
    add constraint UK_dxlv5g5q4aabw6vm1tfjn83jg unique (name);

alter table app_role_permission
    add constraint FK36fhajdupe89tt0so94v3sy0r
        foreign key (roleId)
            references app_role;

alter table app_user_role
    add constraint FKdt4yi580kstfxw91f1mc5p4yb
        foreign key (roleId)
            references app_role;

alter table app_user_role
    add constraint FKiwp45iyocmg83gp5y8n96yf1k
        foreign key (userId)
            references app_user;