# 💰Budget and Expense Tracker 

## 📋 Project Overview
A modern, full-featured **Budget & Expense Tracker** application built using:
- Java + Spring Boot (MVC Architecture)
- Thymeleaf (HTML/CSS/JS frontend)
- MySQL Database
- Object-Oriented Analysis & Design (OOAD)
- 4 Design Patterns: Strategy, Observer, Singleton, DAO
- 4 Major + 4 Minor Features across 4 modules

---

## 🧠 OOAD Features
| Design Pattern | File(s) Implemented |
|----------------|---------------------|
| **Strategy**   | `BudgetStrategy.java`, `StrictBudgetStrategy.java` |
| **Observer**   | `BudgetNotifier.java`, `BudgetAlertService.java` |
| **Singleton**  | `DatabaseManager.java` |
| **DAO**        | Spring Data JPA Repositories |

| Design Principle | Application |
|------------------|-------------|
| SRP              | One class per responsibility |
| OCP              | `BudgetStrategy` open for extension |
| DIP              | Interfaces used for services/observers |
| LSP              | Strategy implementations interchangeable |

---

## ✅ Step-by-Step Setup Instructions

### 1. Start MySQL server
Make sure MySQL is running and the `budget_db` database exists:

```sql
CREATE DATABASE budget_db;
```

---

### 2. Open the project in your IDE
Use IntelliJ IDEA or VS Code.

---

### 3. Configure `application.properties`
Located at:
```
src/main/resources/application.properties
```

Example content:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/budget_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.thymeleaf.cache=false
server.port=8080
```

---

### 4. Run the Application

#### In IntelliJ IDEA:
- Open `BudgetTrackerApp.java`
- Right-click → **Run 'BudgetTrackerApp.main()'**

#### Or using Terminal:
```bash
./mvnw spring-boot:run
```

---

### 🌐 Access the Application
Open your browser and visit:

👉 [http://localhost:8080](http://localhost:8080)

You’ll see your Dashboard with buttons to navigate to:

- `/expenses`
- `/budgets`
- `/goals`
- `/reports`

---

### 🔁 Tip: Rebuild if code was changed
If you modified any Java files:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

---

## 🧪 Testing
Run unit tests with:
```bash
./mvnw test
```
Test files:
- `ExpenseServiceTest.java`
- `BudgetServiceTest.java`

---

## 👥 Team Responsibilities
| Member        | Major Feature      | Minor Feature  | Design Pattern |
|---------------|--------------------|----------------|----------------|
| Disha Prakasha| Daily Expense      | Alerts         | Observer       |
| Dishanth K    | Budget Planner     | Charts         | Strategy       |
| Chaitanya N   | Goals Tracker      | Goal Progress  | Singleton      |
| Chetan        | Report System      | CSV Export     | DAO            |

---

## 📂 Folder Structure
```
src/
├── controller/       # Spring MVC Controllers
├── model/            # JPA Entity Models
├── repository/       # DAO interfaces using Spring Data JPA
├── service/          # Business Logic + Patterns
├── observer/         # Observer Pattern
├── strategy/         # Strategy Pattern
├── util/             # Utility/Singletons
└── resources/
    ├── templates/    # Thymeleaf HTML Views
    ├── static/css/   # Global Styles
    ├── static/js/    # Chart Scripts
    └── application.properties
```

---

## 📜 License
MIT

---

Happy Budgeting! 🎯
