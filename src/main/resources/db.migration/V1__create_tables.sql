create table if not exists public.sellers (
    id serial not null unique,
    name varchar not null,
    contact_info varchar not null,
    registration_date timestamp not null,
    primary key (id)
);

create table if not exists public.transactions (
    id serial not null unique,
    seller_id serial references sellers(id),
    amount integer not null,
    payment_type integer not null,
    transaction_date timestamp not null,
    primary key (id)
)