-- CREATE TABLE users (
--                        id SERIAL PRIMARY KEY,
--                        username VARCHAR(100),
--                        email VARCHAR(100) UNIQUE NOT NULL,
--                        password VARCHAR(8),
--                        phone_number VARCHAR(11),
--
-- );
--
--
--
-- insert into users(id, username, email, password, phone_number) values
--         (101,"dark_royal","praiseoyewole560@gmail.com","praise12","09028979349")  ,
--         (102,"real","real@gmail.com","real1234","09164998141"),
--         (103,"freddie","freddie@gmail.ocm","freddie1","09054023394"),
--         (104,"ziri","ziri@gmail.com","ziri2345","09038942436")
--
--

CREATE TABLE agents (
                        id SERIAL PRIMARY KEY,
                        username VARCHAR(100),
                        email VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(8),
                        phone_number VARCHAR(11),

);

CREATE TABLE address(

    id SERIAL PRIMARY KEY ,
    street_name VARCHAR(100),
    city VARCHAR(100),
    zip_code(8),
    postalCode(8),


);


insert into agents(id, username, email, password, phone_number) values
                    (101,"dark_royal","praiseoyewole560@gmail.com","praise12","09028979349")  ,
                    (102,"real","real@gmail.com","real1234","09164998141"),
                    (103,"freddie","freddie@gmail.ocm","freddie1","09054023394"),
                    (104,"ziri","ziri@gmail.com","ziri2345","09038942436"));

insert into address(id, street_name, city, zip_code, postalCode) values
                    (201,"sabo yaba","lagos state","12345","0902897")  ,
                    (202,"adagun","ogun state","56789","0916499"),
                    (203,"ajenifuja","abia state","54321","0905402"),
                    (204,"davol","ota","56789","0903894"));