CREATE TABLE tb_products (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    url VARCHAR(255) NOT NULL,
    value NUMERIC(14,2) NOT NULL,
    discount_value NUMERIC(14,2),
    status VARCHAR(10) NOT NULL CHECK (status IN ('ATIVO', 'INATIVO')),
    team UUID,
    category UUID,
    sport UUID,
    highlight BOOLEAN DEFAULT false NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);