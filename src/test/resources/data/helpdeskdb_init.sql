create sequence users_id_seq        increment by 50;
create sequence tickets_id_seq      increment by 50;
create sequence feedbacks_id_seq    increment by 50;
create sequence attachments_id_seq  increment by 50;
create sequence histories_id_seq    increment by 50;
create sequence comments_id_seq     increment by 50;

create table users
(
    id         integer      not null,
    first_name varchar(20)  not null,
    last_name  varchar(25)  not null,
    role       varchar(40)  not null,
    email      varchar(100) not null,
    password   varchar(255) not null,
    status     varchar(40)  not null,
    primary key (id),
    unique (email),
    constraint status_enum
        check ((status)::text = ANY ((ARRAY ['ACTIVE'::character varying, 'BANNED'::character varying])::text[])),
    constraint role_enum
        check ((role)::text = ANY
               ((ARRAY ['ROLE_EMPLOYEE'::character varying, 'ROLE_MANAGER'::character varying, 'ROLE_ENGINEER'::character varying])::text[]))
);

create table tickets
(
    id                      integer      not null,
    name                    varchar(100) not null,
    description             varchar(500),
    created_on              timestamp    not null,
    desired_resolution_date date         not null,
    assignee_id             integer,
    owner_id                integer      not null,
    state                   integer      not null,
    category                integer      not null,
    urgency                 integer      not null,
    approver_id             integer,
    primary key (id),
    constraint fk_ticket_assignee_id
        foreign key (assignee_id) references users
            on delete restrict,
    constraint fk_ticket_owner_id
        foreign key (owner_id) references users
            on delete restrict,
    constraint fk_ticket_approver_id
        foreign key (approver_id) references users
            on delete restrict
);

create table feedbacks
(
    id        integer   not null,
    user_id   integer   not null,
    rate      smallint  not null,
    created   timestamp not null,
    text      varchar(1000),
    ticket_id integer   not null,
    primary key (id),
    constraint fk_feedback_user_id
        foreign key (user_id) references users
            on delete restrict,
    constraint fk_feedback_ticket_id
        foreign key (ticket_id) references tickets
            on delete cascade
);