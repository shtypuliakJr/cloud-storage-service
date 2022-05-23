-- users
INSERT INTO users(email, user_name, user_password, created_at, updated_at, last_login_at)
VALUES ('shtepa.jr@gmail.com', 'shtepa.jr',  'password1', now(), now(), now());

INSERT INTO users(email, user_name, user_password, created_at, updated_at, last_login_at)
VALUES ('shtypuliak.jr@gmail.com', 'shtypuliak.jr', 'password2', now(), now(), now());

INSERT INTO users(email, user_name, user_password, created_at, updated_at, last_login_at)
VALUES ('user123.jr@gmail.com', 'user3', 'password3', now(), now(), now());

-- objects
INSERT INTO public.objects
(object_name, is_folder, parent_object_id, user_id, created_at, updated_at)
VALUES('/', true, 1, 2, now(), now());

INSERT INTO public.objects(object_name, is_folder, parent_object_id, user_id, created_at, updated_at)
VALUES
('folder-1', true, 1, 2, now(), now()),
('folder-2', true, 1, 2, now(), now()),
('file1.txt', false, 1, 2, now(), now());

INSERT INTO public.objects
(object_name, is_folder, parent_object_id, user_id, created_at, updated_at)
VALUES
('folder-1/file1-1.txt', false, 2, 2, now(), now()),
('folder-1/file1-2.txt', false, 2, 2, now(), now()),
('folder-1/file1-3.txt', false, 2, 2, now(), now()),
('folder-1/task-5.sql', false, 2, 2, now(), now());

INSERT INTO public.objects
(object_name, is_folder, parent_object_id, user_id, created_at, updated_at)
VALUES
('folder-2/file2-1.txt', false, 3, 2, now(), now()),
('folder-2/file2-2.txt', false, 3, 2, now(), now()),
('folder-2/file2-3.txt', false, 3, 2, now(), now()),
('folder-2/folder-2-1', true, 3, 2, now(), now());


