export PGPASSWORD='BITS-DDA-BC-GRP'; psql -h 18.212.201.45 -p 5432 -d projectDB -U admin

Author login - 1st
publisher - 2nd
order and shipping - 3rd
----------

Database - Noel, Lohar Thushar, Soban Venkat - Postgress
Frontend - Amol Gupta, Amber Mithal - Angular
Backend - Rohit Bandi, Noel - Java
Deployment - Soban - AWS

31st Oct 9:30 pm
Final - 3rd Nov
-----------
book - a_id, b_number, p_id, b_title, b_description, b_language
publisher - p_id, p_royalties, p_terms, p_signdate, p_advance, p_contract
edition - b_number, ed_id, ed_copies, ed_pages, ed_cover, ed_publishdate, ed_price
print_book - ed_id, pr_id, pr_phone, pr_email, pr_cost, pr_address
shipper - s_id, s_name, s_phone, s_email, s_address
order - ed_id, s_id, u_id, o_id, o_status, o_issuedby
user - u_id, u_name, u_password, u_type, u_phone, u_email, u_dob, u_address
-----------


