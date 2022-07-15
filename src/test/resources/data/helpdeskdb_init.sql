create sequence tickets_id_seq      increment by 50;
create sequence feedbacks_id_seq    increment by 50;
create sequence attachments_id_seq  increment by 50;
create sequence comments_id_seq     increment by 50;

create table tickets
(
    id                      integer      not null
        primary key,
    name                    varchar(100) not null,
    description             varchar(500),
    created_on              timestamp    not null,
    desired_resolution_date date         not null,
    assignee_username       varchar(50),
    owner_username          varchar(50)  not null,
    owner_role              integer      not null,
    state                   integer      not null,
    category                integer      not null,
    urgency                 integer      not null,
    approver_username       varchar(50)
);

create table feedbacks
(
    id        integer     not null
        primary key,
    username  varchar(50) not null,
    rate      smallint    not null,
    created   timestamp   not null,
    text      varchar(1000),
    ticket_id integer     not null
        constraint fk_feedback_ticket_id
            references tickets
            on delete cascade
);

create table attachments
(
    id        integer      not null
        primary key,
    link      varchar(150) not null,
    ticket_id integer      not null
        constraint fk_attachment_ticket_id
            references tickets
            on delete cascade,
    name      varchar(100) not null
);