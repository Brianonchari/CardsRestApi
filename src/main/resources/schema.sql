CREATE TABLE `user` (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(255) NOT NULL,
                      email VARCHAR(255) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(20) NOT NULL
);

CREATE TABLE `card` (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(255) NOT NULL,
                      description VARCHAR(1000),
                      color VARCHAR(20),
                      status VARCHAR(20) NOT NULL,
                      user_id BIGINT NOT NULL,
                      FOREIGN KEY (user_id) REFERENCES user (id)
);


