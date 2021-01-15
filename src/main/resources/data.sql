DROP TABLE IF EXISTS series;

CREATE TABLE series (
    id int unsigned primary key auto_increment,
    title varchar (100) not null,
    platform varchar (20) not null
);