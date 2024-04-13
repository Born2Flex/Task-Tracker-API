INSERT INTO users (name, surname, email, role)
VALUES ('John', 'Doe', 'test@gmail.com', 'USER'),
       ('John', 'Smith', 'jsmith321@example.com', 'USER'),
       ('Michael', 'Brown', 'mbrown789@example.com', 'USER'),
       ('Sarah', 'Davis', 'sdavis234@example.com', 'USER'),
       ('David', 'Wilson', 'dwilson567@example.com', 'USER'),
       ('Jennifer', 'Martinez', 'jmartinez890@example.com', 'USER'),
       ('Christopher', 'Taylor', 'ctaylor123@example.com', 'USER'),
       ('Jessica', 'Anderson', 'janderson456@example.com', 'USER'),
       ('Daniel', 'Thomas', 'dthomas789@example.com', 'USER'),
       ('Amanda', 'Clark', 'aclark012@example.com', 'USER');

INSERT INTO tasks (title, description, date, status, user_id)
VALUES ('Add controller', 'Description', '2024-04-13', 'PLANNED', 1),
       ('Fix service', 'Description', '2024-04-13', 'IN_PROGRESS', 2),
       ('Do refactoring', 'Description', '2024-04-13', 'DONE', 2),
       ('Add sample data', 'Description', '2024-04-13', 'PLANNED', 4),
       ('Change button color', 'Description', '2024-04-13', 'IN_PROGRESS', 5),
       ('Add new page', 'Description', '2024-04-13', 'CANCELLED', 5),
       ('Fix bug', 'Description', '2024-04-13', 'PLANNED', 7),
       ('Add new functionality', 'Description', '2024-04-13', 'IN_PROGRESS', 8);