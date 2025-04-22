-- Table: categories
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    user_id BIGINT
);

-- Table: budgets
CREATE TABLE IF NOT EXISTS budgets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    month INT,
    year INT,
    total_limit DECIMAL(10,2),
    description TEXT,
    user_id BIGINT
);

-- Table: budget_items
CREATE TABLE IF NOT EXISTS budget_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    amount DECIMAL(10,2),
    spent_amount DECIMAL(10,2),
    budget_id BIGINT,
    category_id BIGINT,
    FOREIGN KEY (budget_id) REFERENCES budgets(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Table: expenses
CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255),
    amount DECIMAL(10,2),
    date DATE,
    user_id BIGINT,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Table: bills
CREATE TABLE IF NOT EXISTS bills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    due_date DATE NOT NULL,
    is_paid BOOLEAN DEFAULT FALSE,
    payment_date DATE,
    is_recurring BOOLEAN DEFAULT FALSE,
    recurrence_type VARCHAR(20) DEFAULT 'NONE',
    user_id BIGINT,
    category_id BIGINT
);

-- Table: goals
CREATE TABLE IF NOT EXISTS goals (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    description TEXT,
    target_amount DECIMAL(10,2),
    saved_amount DECIMAL(10,2),
    start_date DATE,
    target_date DATE,
    user_id BIGINT
);

-- Table: reports
CREATE TABLE IF NOT EXISTS reports (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(50),
    content TEXT,
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);