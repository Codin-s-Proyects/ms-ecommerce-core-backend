INSERT INTO pricing.price_lists (tenant_id, code, name, currency_code)
VALUES
    ('bf89c25a-3ed4-4a00-96bd-59cde2ced8c0', 'minorista', 'Lista Minorista', 'PEN'),
    ('bf89c25a-3ed4-4a00-96bd-59cde2ced8c0', 'mayorista', 'Lista Mayorista', 'PEN')
ON CONFLICT DO NOTHING;
