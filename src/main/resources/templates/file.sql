-- Добавляем пользователей
INSERT INTO user (username, password, role_id, full_name, email) VALUES
                                                                     ('admin', '$2a$10$ExampleHashedPassword1', 1, 'Admin User', 'admin@example.com'), -- Пароль должен быть захеширован
                                                                     ('user1', '$2a$10$ExampleHashedPassword2', 2, 'User One', 'user1@example.com'),
                                                                     ('user2', '$2a$10$ExampleHashedPassword3', 2, 'User Two', 'user2@example.com');

-- Добавляем адреса
INSERT INTO address (address_name, url, description) VALUES
                                                         ('Google', 'https://google.com', 'Search engine'),
                                                         ('GitHub', 'https://github.com', 'Code hosting platform'),
                                                         ('Stack Overflow', 'https://stackoverflow.com', 'Developer community');

-- Добавляем данные паролей
INSERT INTO password_data (user_id, address_id, user_login, user_password) VALUES
                                                                               (2, 1, 'user1@gmail.com', 'password1'), -- user1 -> Google
                                                                               (2, 2, 'user1_github', 'password2'),    -- user1 -> GitHub
                                                                               (3, 1, 'user2@gmail.com', 'password3'), -- user2 -> Google
                                                                               (3, 3, 'user2_stackoverflow', 'password4'); -- user2 -> Stack Overflow