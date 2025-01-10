CREATE TABLE usuarios(
    id BIGINT not NULL auto_increment,
    login varchar(100) not NULL,
    clave varchar(100) not NULL,
    PRIMARY KEY (id)
);