# Cheffy API - Recipe Management and Cooking Collaboration

Cheffy is a Clojure-based application designed to help users manage recipes, ingredients, and cooking steps. (This project was developed following the [Reitit Pro Course](https://www.jacekschae.com/view/courses/learn-reitit-pro) on the Clojure Stream platform)

---

## Tech Stack

Cheffy leverages several modern technologies to provide a smooth and scalable experience:

- **Clojure**: A functional programming language that powers the backend logic of Cheffy, providing a robust and expressive environment for developing the application.
  
- **Docker**: Used to containerize the application for easy deployment, scalability, and isolation across various environments.

- **Ring**: A Clojure web library used to handle HTTP requests and responses. It provides a flexible interface for building web applications and APIs.

- **Reitit**: A Clojure routing library that integrates well with Ring, enabling flexible and efficient routing for our web application.

- **Integrant**: A simple configuration management library for Clojure, used to handle component management and initialization for the application, ensuring a clean and manageable architecture.

- **JDBC**: The Java Database Connectivity (JDBC) API is used to connect Cheffy with a PostgreSQL database, providing robust support for relational data storage and querying.

- **PostgreSQL**: A powerful, open-source relational database used to store recipes, ingredients, steps, and chat messages, ensuring data consistency and reliability.

- **Auth0**: A secure authentication service that handles user login, registration, and identity management, ensuring a safe and reliable user experience for the Cheffy community.

---

## Installation

### Requirements

- Java 
- Leiningen (for project management)

### Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/leoguilen/clojure-recipe-api-with-reitit-ring-jdbc-integrant.git cheffy

2. Navigate into the project directory:

    ```bash
    cd cheffy
    ```

3. Install project dependencies using Leiningen:

    ```bash
    lein deps
    ```

3. Run the application:

    ```bash
    lein run
    ```