DROP TABLE IF EXISTS T_TOKEN;

CREATE TABLE T_TOKEN (
    phone varchar(20) primary key,
    token varchar(100)
);