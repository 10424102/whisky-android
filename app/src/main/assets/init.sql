drop table if exists t_user;

create table t_user (
    phone varchar(20) primary key,
    token character(32),
    used_count  int,
    username varchar(30),
    nickname varchar(30),
    signature varchar(140),
    email varchar(50),
    highschool varchar(30),
    hometown varchar(30),
    gender varchar(10),
    birthday character(10), /* ISO-8601 format YYYY-MM-DD */
    college varchar(30),
    academy varchar(30),
    grade varchar(30),
    visiable boolean,
    avatar_hash character(32),
    background_hash character(32),
    foreign key(avatar_hash) references t_image(hash),
    foreign key(background_hash) references t_image(hash)
);

create table t_image (
    hash character(32) primary key,
    data blob,
    hits int
);