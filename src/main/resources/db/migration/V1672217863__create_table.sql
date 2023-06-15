CREATE TABLE `user` (
  user_no bigint NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  updated_at timestamp NOT NULL,
  created_at timestamp NOT NULL,
  PRIMARY KEY (user_no),
  UNIQUE KEY uix_user_email (email)
);

CREATE TABLE `role` (
    role_no bigint NOT NULL AUTO_INCREMENT,
    name varchar(32) NOT NULL,
    updated_at timestamp NOT NULL,
    created_at timestamp NOT NULL,
    PRIMARY KEY (role_no),
    UNIQUE KEY uix_role_name (name)
);

INSERT INTO role (name, updated_at, created_at) VALUES ('ROLE_USER', now(), now());
INSERT INTO role (name, updated_at, created_at) VALUES ('ROLE_ADMIN', now(), now());

CREATE TABLE `authority` (
    role_no bigint NOT NULL,
    user_no bigint NOT NULL,

    CONSTRAINT fk_authority_role_no FOREIGN KEY (role_no) REFERENCES role(role_no) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_authority_user_no FOREIGN KEY (user_no) REFERENCES user(user_no) ON DELETE CASCADE ON UPDATE CASCADE
);