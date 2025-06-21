CREATE OR REPLACE FUNCTION update_order_total()
RETURNS TRIGGER AS $$
BEGIN

    UPDATE order_tb
    SET total = total + NEW.total_price
    WHERE id = NEW.order_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tg_update_order_total_after_insert
AFTER INSERT ON order_item_tb
FOR EACH ROW
EXECUTE PROCEDURE update_order_total();
