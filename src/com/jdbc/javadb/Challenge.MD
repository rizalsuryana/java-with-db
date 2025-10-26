Create database
CREATE DATABASE cashflow_db;


connect database
\c cashflow_db;


create table
CREATE TABLE cashflow (
id INT PRIMARY KEY,
type VARCHAR(10) NOT NULL,
description VARCHAR(255),
amount NUMERIC(12,2) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CRUD