create table statistic
(
    id    varchar(255) not null constraint statistic_pkey primary key,
    count integer,
    int1  integer,
    int2  integer,
    lim   integer constraint statistic_lim_check check (lim >= 1),
    str1  varchar(255),
    str2  varchar(255)
);
