ALTER TABLE core.tenants
    ADD COLUMN IF NOT EXISTS complaint_book_url TEXT;