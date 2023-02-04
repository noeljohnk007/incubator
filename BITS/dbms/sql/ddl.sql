DROP TABLE IF EXISTS dbms.order;
DROP TABLE IF EXISTS dbms.shipper;
DROP TABLE IF EXISTS dbms.print_book;
DROP TABLE IF EXISTS dbms.edition;
DROP TABLE IF EXISTS dbms.book;
DROP TABLE IF EXISTS dbms.publisher;
DROP TABLE IF EXISTS dbms.user;
DROP SCHEMA IF EXISTS dbms;

CREATE SCHEMA IF NOT EXISTS dbms;
COMMENT ON SCHEMA dbms IS 'schema created to store the tables used for dbms assignment.';

--user
CREATE TABLE IF NOT EXISTS dbms.user
(
    u_id integer NOT NULL,
    u_name character(50),
    u_password character(10),
    u_type character(15),
    u_phone character(10),
    u_email character(50),
    u_dob date,
    u_address text,
    CONSTRAINT user_pkey PRIMARY KEY (u_id)
);
COMMENT ON COLUMN dbms.user.u_id IS 'The ID of the user. This is the primary key.';
COMMENT ON COLUMN dbms.user.u_name IS 'The name of the user.';
COMMENT ON COLUMN dbms.user.u_password IS 'The password of the user.';
COMMENT ON COLUMN dbms.user.u_type IS 'The type of the user. Examples are author, readers, etc.';
COMMENT ON COLUMN dbms.user.u_phone IS 'The phone number of user.';
COMMENT ON COLUMN dbms.user.u_email IS 'The email address of the user.';
COMMENT ON COLUMN dbms.user.u_dob IS 'The date of birth of the user.';
COMMENT ON COLUMN dbms.user.u_address IS 'The address of the user.';

--publisher
CREATE TABLE IF NOT EXISTS dbms.publisher
(
    p_id integer NOT NULL,
    p_royalties real,
    p_terms text,
    p_signdate date,
    p_advance real,
    p_contract text,
    CONSTRAINT publisher_pkey PRIMARY KEY (p_id)
);
COMMENT ON COLUMN dbms.publisher.p_id IS 'The ID of the publisher. This will be the primary key.';
COMMENT ON COLUMN dbms.publisher.p_royalties IS 'The amount to be paid to the author as royalty for each copy of the book sold.';
COMMENT ON COLUMN dbms.publisher.p_terms IS 'The terms and conditions agreed by the author and publisher.';
COMMENT ON COLUMN dbms.publisher.p_signdate IS 'The date in which the author and publisher signed the terms and conditions.';
COMMENT ON COLUMN dbms.publisher.p_advance IS 'The advance paid to the author.';
COMMENT ON COLUMN dbms.publisher.p_contract IS 'The contract signed between the author and publisher.';

--book
CREATE TABLE IF NOT EXISTS dbms.book
(
    a_id integer NOT NULL,
    b_number integer NOT NULL,
    p_id integer NOT NULL,
    b_title character(50),
    b_description text,
    b_language character(50),
    CONSTRAINT book_pkey PRIMARY KEY (b_number),
    CONSTRAINT user_fk FOREIGN KEY(a_id) REFERENCES dbms.user(u_id),
    CONSTRAINT publisher_fk FOREIGN KEY(p_id) REFERENCES dbms.publisher(p_id)
);
COMMENT ON COLUMN dbms.book.a_id IS 'The ID of the author who wrote the book. This is the foreign key.';
COMMENT ON COLUMN dbms.book.b_number IS 'The unique number associated with the book. This will be the primary key.';
COMMENT ON COLUMN dbms.book.p_id IS 'The ID of the publisher. This will be the foreign key.';
COMMENT ON COLUMN dbms.book.b_title IS 'The title of the book.';
COMMENT ON COLUMN dbms.book.b_description IS 'The description about the book.';
COMMENT ON COLUMN dbms.book.b_language IS 'The language of the book.';

--edition
CREATE TABLE IF NOT EXISTS dbms.edition
(
    b_number integer NOT NULL,
    ed_id integer NOT NULL,
    ed_copies integer,
    ed_pages integer,
    ed_cover text,
    ed_publishdate date,
    ed_price real,
    CONSTRAINT edition_pkey PRIMARY KEY (ed_id),
    CONSTRAINT book_fk FOREIGN KEY(b_number) REFERENCES dbms.book(b_number)
);
COMMENT ON COLUMN dbms.edition.b_number IS 'The ID of the book in which the publisher has the rights to publish. This is the foreign key.';
COMMENT ON COLUMN dbms.edition.ed_id IS 'The ID of Edition. This will be the primary key.';
COMMENT ON COLUMN dbms.edition.ed_copies IS 'The no. of copies of the edition has printed.';
COMMENT ON COLUMN dbms.edition.ed_pages IS 'The no. of pages of the edition.';
COMMENT ON COLUMN dbms.edition.ed_cover IS 'The content of the cover page of the particular edition.';
COMMENT ON COLUMN dbms.edition.ed_publishdate IS 'The date in which the edition has published.';
COMMENT ON COLUMN dbms.edition.ed_price IS 'The price of the edition.';

--print_book
CREATE TABLE IF NOT EXISTS dbms.print_book
(
    ed_id integer NOT NULL,
    pr_id integer NOT NULL,
    pr_phone character(10),
    pr_email character(50),
    pr_cost real,
    pr_address text,
    CONSTRAINT print_book_pkey PRIMARY KEY (pr_id),
    CONSTRAINT edition_fk FOREIGN KEY(ed_id) REFERENCES dbms.edition(ed_id)
);
COMMENT ON COLUMN dbms.print_book.ed_id IS 'The ID of edition. This is the foreign key.';
COMMENT ON COLUMN dbms.print_book.pr_id IS 'The ID of the printing company. This will be the primary key.';
COMMENT ON COLUMN dbms.print_book.pr_phone IS 'The phone number of the printing company.';
COMMENT ON COLUMN dbms.print_book.pr_email IS 'The email address of the printing company.';
COMMENT ON COLUMN dbms.print_book.pr_cost IS 'The cost of printing the book.';
COMMENT ON COLUMN dbms.print_book.pr_address IS 'The address of the printing company.';

--shipper
CREATE TABLE IF NOT EXISTS dbms.shipper
(
    s_id integer NOT NULL,
    s_name character(50),
    s_phone character(10),
    s_email character(50),
    s_address text,
    CONSTRAINT shipper_pkey PRIMARY KEY (s_id)
);
COMMENT ON COLUMN dbms.shipper.s_id IS 'The ID of the shipper. This is the primary key.';
COMMENT ON COLUMN dbms.shipper.s_name IS 'The name of the shipper.';
COMMENT ON COLUMN dbms.shipper.s_phone IS 'The phone number of shipper.';
COMMENT ON COLUMN dbms.shipper.s_email IS 'The email address of the shipper.';
COMMENT ON COLUMN dbms.shipper.s_address IS 'The address of the shipper.';

--order
CREATE TABLE IF NOT EXISTS dbms.order
(
    ed_id integer NOT NULL,
    s_id integer NOT NULL,
    u_id integer NOT NULL,
    o_id integer NOT NULL,
    o_status character(20),
    o_issuedby date,
    CONSTRAINT order_pkey PRIMARY KEY (o_id),
    CONSTRAINT edition_fk FOREIGN KEY(ed_id) REFERENCES dbms.edition(ed_id),
    CONSTRAINT shipper_fk FOREIGN KEY(s_id) REFERENCES dbms.shipper(s_id),
    CONSTRAINT user_fk FOREIGN KEY(u_id) REFERENCES dbms.user(u_id)
);
COMMENT ON COLUMN dbms.order.ed_id IS 'The ID of edition. This is the foreign key.';
COMMENT ON COLUMN dbms.order.s_id IS 'The ID of the shipper. This is the foreign key.';
COMMENT ON COLUMN dbms.order.u_id IS 'The ID of the user. This is the foreign key.';
COMMENT ON COLUMN dbms.order.o_id IS 'The ID of the order. This is the primary key.';
COMMENT ON COLUMN dbms.order.o_status IS 'The status of the order placed.';
COMMENT ON COLUMN dbms.order.o_issuedby IS 'The date of issue of the order.';
