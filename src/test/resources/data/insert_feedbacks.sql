INSERT INTO public.users (id, first_name, last_name, role, email, password, status) VALUES (2, 'user2', 'user2', 'ROLE_EMPLOYEE', 'user2_mogilev@yopmail.com', '$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2', 'ACTIVE');
INSERT INTO public.users (id, first_name, last_name, role, email, password, status) VALUES (4, 'manager2', 'manager2', 'ROLE_MANAGER', 'manager2_mogilev@yopmail.com', '$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2', 'ACTIVE');
INSERT INTO public.users (id, first_name, last_name, role, email, password, status) VALUES (6, 'engineer2', 'engineer2', 'ROLE_ENGINEER', 'engineer2_mogilev@yopmail.com', '$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2', 'ACTIVE');

INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (3, 'Ticket 3', 'Third ticket', '2022-03-17 10:00:00.000000', '2022-03-20', 6, 2, 5, 2, 2, 4);
INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (4, 'Ticket 4', 'Next ticket', '2022-03-17 10:00:00.000000', '2022-03-20', 6, 2, 2, 2, 2, 4);

INSERT INTO public.tickets (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category, urgency, approver_id) VALUES (5, 'Ticket 5', 'Five ticket', '2022-04-20 10:00:00.000000', '2022-04-20', 6, 2, 5, 2, 2, 4);

INSERT INTO public.feedbacks (id, user_id, rate, created, text, ticket_id) VALUES (1, 2, 5, '2022-04-19 09:00:00.000000', 'Good job!', 3);