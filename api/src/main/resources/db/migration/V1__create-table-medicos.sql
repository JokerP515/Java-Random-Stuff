CREATE TABLE medicos (
    id BIGINT not NULL auto_increment,
    nombre varchar(100) not NULL,
    email varchar(100) not NULL unique,
    documento varchar(6) not NULL unique,
    especialidad varchar(100) not NULL,
    calle varchar(100) not NULL,
    distrito varchar(100) not NULL,
    complemento varchar(100),
    numero integer,
    ciudad varchar(100) not NULL,

    primary key (id)
);