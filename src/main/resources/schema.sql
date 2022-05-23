DROP TABLE IF EXISTS public.objects;
DROP TABLE IF EXISTS public.users;

CREATE TABLE public.users (
	user_id INT GENERATED ALWAYS AS IDENTITY,
	email varchar(255) NOT NULL,
	user_name varchar(255) NOT NULL,
	user_password varchar(255) NOT NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NULL,
	last_login_at timestamp NULL,
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

CREATE TABLE public.objects (
    object_id INT GENERATED ALWAYS AS IDENTITY,
    object_name varchar(255) NOT NULL,
    file_type varchar,
	is_folder bool NOT NULL,
	parent_object_id INT,
	user_id INT,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	CONSTRAINT objects_pkey PRIMARY KEY (object_id),
	CONSTRAINT fk_users FOREIGN KEY(user_id) REFERENCES users(user_id)
);
