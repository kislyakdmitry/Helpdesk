INSERT INTO public.tickets (id, "name", description, created_on, desired_resolution_date, assignee_username,
                            owner_username, state, category, urgency, approver_username, owner_role)
VALUES (3, 'Ticket 3', 'Third ticket', '2022-03-17 10:00:00', '2022-03-20', 'engineer2_mogilev', 'user2_mogilev', 3, 2,
        2, 'manager2_mogilev', 0),
       (5, 'Ticket 5', 'Fourth ticket', '2022-03-17 10:00:00', '2022-03-20', 'engineer1_mogilev', 'user1_mogilev', 5, 4,
        3, 'manager2_mogilev', 0),
       (4, 'Ticket 4', 'Fourth ticket', '2022-03-17 10:00:00', '2022-03-20', 'engineer2_mogilev', 'manager1_mogilev', 4,
        3, 3, 'manager1_mogilev', 1);

INSERT INTO public.feedbacks (id, username, rate, created, "text", ticket_id)
VALUES (1, 'user1_mogilev', 5, '2022-04-19 09:51:54.989423', 'Good job!', 3);