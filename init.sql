CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table statistic
(
    id    varchar(255) NOT NULL DEFAULT uuid_generate_v1(),
    count integer,
    int1  integer,
    int2  integer,
    limit   integer constraint statistic_lim_check check (limit >= 1),
    str1  varchar(255),
    str2  varchar(255),
    CONSTRAINT id_tbl PRIMARY KEY ( id )
);
