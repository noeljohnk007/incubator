delete from dbms.order;
delete from dbms.shipper;
delete from dbms.print_book;
delete from dbms.edition;
delete from dbms.book;
delete from dbms.publisher;
delete from dbms.user;

INSERT INTO dbms.user (u_id, u_name, u_password, u_type, u_phone, u_email, u_dob, u_address) VALUES (0001, 'Noel', 'noel', 'Reader', '1234567890', 'noel@example.com', '2022-01-01', 'Noel House');
INSERT INTO dbms.user (u_id, u_name, u_password, u_type, u_phone, u_email, u_dob, u_address) VALUES (1000, 'William Shakespeare', 'william', 'Author', '111111111', 'william@example.com', '1564-04-26', 'Stratford-upon-Avon');

INSERT INTO dbms.publisher (p_id, p_royalties, p_terms, p_signdate, p_advance, p_contract) VALUES (1000, 50, 'This is the terms & conditions provided by publisher 1', '2022-11-02', 2000, 'This is the contract offered by publisher 1');

INSERT INTO dbms.book (a_id, b_number, p_id, b_title, b_description, b_language) VALUES (1000, 2000, 1000, 'Hamlet', 'The Tragedy of Hamlet, Prince of Denmark, often shortened to Hamlet', 'English');

INSERT INTO dbms.edition (b_number, ed_id, ed_copies, ed_pages, ed_cover, ed_publishdate, ed_price) VALUES (2000, 3000, 20, 100, 'First Edition of Hamlet', '1600-01-18', 100);
INSERT INTO dbms.edition (b_number, ed_id, ed_copies, ed_pages, ed_cover, ed_publishdate, ed_price) VALUES (2000, 3001, 50, 120, 'Second Edition of Hamlet', '1632-05-23', 100);

INSERT INTO dbms.print_book (ed_id, pr_id, pr_phone, pr_email, pr_cost, pr_address) VALUES (3000, 4000, '222222222', 'xerox@example.com', 40, 'Norwalk, Connecticut, United States');
INSERT INTO dbms.print_book (ed_id, pr_id, pr_phone, pr_email, pr_cost, pr_address) VALUES (3001, 4001, '333333333', 'cenveo@example.com', 60, 'Stamford, US');

INSERT INTO dbms.shipper (s_id, s_name, s_phone, s_email, s_address) VALUES (5000, 'Blue Dart Express Ltd.', '4444444444', 'bluedart@example.com', 'Mumbai, India');

INSERT INTO dbms.order (ed_id, s_id, u_id, o_id, o_status, o_issuedby) VALUES (3000, 5000, 0001, 6000, 'Booked','2022-11-20' );
