DROP TABLE IF EXISTS series;

CREATE TABLE series (
    id int unsigned primary key auto_increment,
    title varchar (100) not null,
    rate double (2) not null,
    platform varchar (20) not null
);

INSERT INTO series (title, rate, platform) VALUES
  ('Ozark', 7.5, 'Netflix');