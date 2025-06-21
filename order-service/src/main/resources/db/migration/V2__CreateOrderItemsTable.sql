CREATE TABLE order_item_tb (
    id 			    UUID 			PRIMARY KEY,
    order_id 		UUID 			NOT NULL REFERENCES order_tb(id) ON DELETE CASCADE,
    product_id 		UUID 			NOT NULL,
    size            VARCHAR(5),
    quantity 		INTEGER 		NOT NULL CHECK (quantity > 0),
    unit_price 		DECIMAL(10, 2) 	NOT NULL CHECK (unit_price >= 0),
    total_price 	DECIMAL(10, 2)  NOT NULL CHECK (total_price > 0)
);
