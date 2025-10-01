INSERT INTO customers (id, name, email, cpf) VALUES 
(1, 'John Doe', 'john@example.com', '12345678901');

INSERT INTO addresses (id, street, number, complement, neighborhood, city, state, zip_code, customer_id) VALUES 
(1, 'Main Street', '123', 'Apt 4B', 'Downtown', 'New York', 'NY', '10001', 1);