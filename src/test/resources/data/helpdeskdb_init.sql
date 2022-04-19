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

INSERT INTO public.users (id, first_name, last_name, role, email, password, status) VALUES (1, 'user1', 'user1', 'ROLE_EMPLOYEE', 'user1_mogilev@yopmail.com', '$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2', 'ACTIVE');
INSERT INTO public.users (id, first_name, last_name, role, email, password, status) VALUES (2, 'user2', 'user2', 'ROLE_EMPLOYEE', 'user2_mogilev@yopmail.com', '$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2', 'ACTIVE');
INSERT INTO public.users (id, first_name, last_name, role, email, password, status) VALUES (3, 'manager1', 'manager1', 'ROLE_MANAGER', 'manager1_mogilev@yopmail.com', '$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2', 'ACTIVE');
INSERT INTO public.users (id, first_name, last_name, role, email, password, status) VALUES (4, 'manager2', 'manager2', 'ROLE_MANAGER', 'manager2_mogilev@yopmail.com', '$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2', 'ACTIVE');
INSERT INTO public.users (id, first_name, last_name, role, email, password, status) VALUES (5, 'engineer1', 'engineer1', 'ROLE_ENGINEER', 'engineer1_mogilev@yopmail.com', '$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2', 'ACTIVE');
INSERT INTO public.users (id, first_name, last_name, role, email, password, status) VALUES (6, 'engineer2', 'engineer2', 'ROLE_ENGINEER', 'engineer2_mogilev@yopmail.com', '$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2', 'ACTIVE');

INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (1, 'Ticket 1', 'First ticket', '2022-03-16 00:00:00.000000', '2022-03-18', 5, 1, 1, 0, 0, 3);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (2, 'Ticket 2', 'Second ticket', '2022-03-17 10:00:00.000000', '2022-03-20', 5, 1, 2, 1, 1, 3);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (3, 'Ticket 3', 'Third ticket', '2022-03-17 10:00:00.000000', '2022-03-20', 6, 2, 3, 2, 2, 4);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (4, 'Ticket 4', 'Fourth ticket', '2022-03-17 10:00:00.000000', '2022-03-20', 6, 2, 4, 3, 3, 4);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (5, 'Ticket 5', 'Fourth ticket', '2022-03-17 10:00:00.000000', '2022-03-20', 5, 1, 5, 4, 3, 4);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (6, 'Ticket 6', 'Six ticket', '2022-03-17 10:00:00.000000', '2022-03-20', 5, 1, 6, 5, 2, 4);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (7, 'Ticket 7', 'Seven ticket', '2022-03-17 10:00:00.000000', '2022-03-20', 6, 1, 1, 0, 1, 3);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (8, 'Ticket 8', 'Eight ticket', '2022-03-13 10:00:00.000000', '2022-03-20', 6, 1, 0, 1, 2, 3);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (9, 'Ticket 9', 'Nine ticket', '2022-03-15 10:00:00.000000', '2022-04-02', 5, 1, 2, 2, 0, 3);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (10, 'Ticket 10', 'Ten ticket', '2022-03-14 10:00:00.000000', '2022-03-25', 6, 1, 3, 3, 3, 3);

INSERT INTO public.feedbacks (id, user_id, rate, created, text, ticket_id) VALUES (1, 2, 5, '2022-04-19 09:51:54.989423', 'Good job!', 3);