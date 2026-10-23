# 🍳 Cookmate — Full-Stack Recipe Finder

**Discover recipes. Find meals by ingredients. Save your favourites.**

Cookmate is a full-stack web application that helps users explore recipes, search by recipe name or available ingredients, filter results, and bookmark their favourite dishes. It combines a React frontend with a Spring Boot REST API, MySQL database, and JWT-based authentication.

## 🌐 Live Demo : https://recipe-app-jpif.vercel.app/
- **GitHub Repository:** https://github.com/ishita191/Recipe-App

> Note: The backend is an API, so opening its base URL directly in a browser may not display a webpage. The frontend communicates with the backend through REST endpoints.

## ✨ Features

- 🔍 **Ingredient-Based Search** — Find recipes using multiple ingredients.
- 📝 **Keyword Search** — Search recipes by name or description.
- 🏷️ **Cuisine & Category Filters** — Narrow results using available cuisine and category options.
- 🔐 **User Authentication** — Register and log in using JWT-based authentication.
- 📌 **Recipe Bookmarks** — Save and manage favourite recipes after logging in.
- 📖 **Recipe Details** — View recipe information, ingredients, and cooking steps.
- 🔗 **REST API Integration** — Connect the React frontend with the Spring Boot backend.
- 📱 **Responsive Interface** — Browse recipes through a user-friendly web interface.

## 🛠️ Tech Stack

| Layer | Technologies |
|---|---|
| Frontend | React 18, JavaScript, HTML, CSS |
| Backend | Java, Spring Boot 3.x, Spring Web |
| Database | MySQL 8 |
| ORM | Spring Data JPA, Hibernate |
| Authentication | Spring Security, JWT |
| API Communication | REST APIs, Axios |
| Build Tools | Maven, npm |
| Version Control | Git, GitHub |
| Deployment | Vercel (frontend), Render (backend) |

## 🏗️ Application Architecture

```text
             User / Browser
                    |
                    v
          React Frontend (Vercel)
                    |
              Axios / REST API
                    |
                    v
        Spring Boot Backend (Render)
                    |
          Spring Data JPA / Hibernate
                    |
                    v
               MySQL Database
```

The frontend handles the user interface and sends API requests to the Spring Boot backend. The backend processes requests, applies business logic, interacts with MySQL, and returns responses to the frontend.

## 📁 Project Structure

```text
Recipe-App/
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/recipeapp/
│       │   ├── RecipeApplication.java
│       │   ├── config/
│       │   │   ├── DataSeeder.java
│       │   │   └── SecurityConfig.java
│       │   ├── controller/
│       │   │   ├── AuthController.java
│       │   │   ├── RecipeController.java
│       │   │   └── IngredientController.java
│       │   ├── dto/
│       │   ├── entity/
│       │   ├── repository/
│       │   ├── security/
│       │   └── service/
│       └── resources/
│
└── frontend/
    ├── package.json
    └── src/
        ├── components/
        ├── context/
        ├── pages/
        ├── services/
        ├── utils/
        ├── App.js
        └── index.css
```

## ⚙️ Prerequisites

Install the following before running the application locally:

- Java 17 or a compatible version required by the backend configuration
- Node.js and npm
- MySQL 8
- Maven 3.8+ (or the Maven Wrapper, if included in the project)
- Git

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/ishita191/Recipe-App.git
cd Recipe-App
```

### 2. Configure MySQL

Create the database in MySQL:

```sql
CREATE DATABASE IF NOT EXISTS recipedb;
```

Configure the database URL, username, and password in the backend configuration.

For local development, `backend/src/main/resources/application.properties` may use settings like:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recipedb
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Make sure the property names match your actual backend configuration. Set the corresponding environment variables locally; do not commit database passwords or JWT secrets to GitHub.

If your application uses Hibernate schema updates, confirm the configured `spring.jpa.hibernate.ddl-auto` setting before starting the backend.

### 3. Start the Backend

Open a terminal in the repository root:

```bash
cd backend
mvn spring-boot:run
```

The backend normally runs at:

```text
http://localhost:8080
```

The actual port depends on your application configuration and environment variables.

### 4. Start the Frontend

Open a second terminal:

```bash
cd frontend
npm install
npm start
```

The React development server normally opens at:

```text
http://localhost:3000
```

Ensure the frontend API base URL points to the local backend when testing locally, or to the deployed backend when using the production website.

## 🔌 REST API Endpoints

The following endpoints reflect the application's recipe and
Ensure `app.cors.allowed-origins=http://localhost:3000` matches your frontend URL.
