CREATE DATABASE transactions;
\c transactions;

CREATE USER dev_user WITH PASSWORD 'dev_password';
ALTER DATABASE transactions OWNER TO dev_user;
GRANT ALL PRIVILEGES ON DATABASE transactions TO dev_user;

\c transactions dev_user;

CREATE SCHEMA IF NOT EXISTS transactions;
GRANT USAGE ON SCHEMA transactions TO dev_user;
GRANT CREATE ON SCHEMA transactions TO dev_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA transactions TO dev_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA transactions TO dev_user;
--
--
-- CREATE SCHEMA IF NOT EXISTS users;
-- GRANT USAGE ON SCHEMA users TO dev_user;
-- GRANT CREATE ON SCHEMA users TO dev_user;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA users TO dev_user;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA users TO dev_user;