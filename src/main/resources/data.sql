DROP TABLE IF EXISTS series;

CREATE TABLE series (
    id int unsigned primary key auto_increment,
    title varchar (100) not null,
    platform varchar (20) not null
);

INSERT INTO series (title, platform) VALUES
  ('Ozark', 'Netflix');

INSERT INTO series (title, platform) VALUES
  ('The Witcher', 'Netflix');

INSERT INTO series (title, platform) VALUES
  ('Game of Thrones', 'HBO');