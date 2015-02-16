/*
 * MySQL script.
 * Load the database with reference data and unit test data.
 */
-- password is 'password'
INSERT INTO Account (username, password, enabled, credentialsexpired, expired, locked) VALUES ('user', '$2a$10$9/44Rne7kQqPXa0cY6NfG.3XzScMrCxFYjapoLq/wFmHz7EC9praK', true, false, false, false);
-- password is 'operations'
INSERT INTO Account (username, password, enabled, credentialsexpired, expired, locked) VALUES ('operations', '$2a$10$CoMVfutnv1qZ.fNlHY1Na.rteiJhsDF0jB1o.76qXcfdWN6As27Zm', true, false, false, false);

INSERT INTO Role (id, code, label) VALUES (1, 'ROLE_USER', 'User');
INSERT INTO Role (id, code, label) VALUES (2, 'ROLE_ADMIN', 'Admin');
INSERT INTO Role (id, code, label) VALUES (3, 'ROLE_SYSADMIN', 'System Admin');

INSERT INTO AccountRole (accountId, roleId) SELECT a.id, r.id FROM Account a, Role r WHERE a.username = 'user' and r.id = 1;
INSERT INTO AccountRole (accountId, roleId) SELECT a.id, r.id FROM Account a, Role r WHERE a.username = 'operations' and r.id = 3;

INSERT INTO Greeting (text) VALUES ('Hello World!');
INSERT INTO Greeting (text) VALUES ('Hola Mundo!');