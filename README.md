# EShopping Project

## Introduction

This GitHub repository contains the "EShopping" project implemented in Java with four different versions:

1. **Fijalkowskim_EShopping_Console**
2. **Fijalkowskim_Eshopping_JavaFX**
3. **Fijalkowskim_Eshopping_Web**
4. **Fijalkowskim_Eshopping_Web + MySQL**

Each version serves as a different interface for the EShopping application, providing console, JavaFX, and web-based implementations. The fourth version incorporates MySQL for database interaction.

## Versions

### 1. Fijalkowskim_EShopping_Console

This version provides a console-based interface for the EShopping application. Users can interact with the system through a text-based menu, viewing and purchasing items.
![image](https://github.com/Fijalkowskim/Fijalkowskim_EShopping_repo/assets/91847461/208eba41-0ec1-444f-854a-6781d652908b)


#### Example Code:

```java
// Example code from InitServlet
@WebServlet("/init")
public class InitServlet extends HttpServlet {
    // ... (Code for creating tables, loading session data, and more)
}
```

### 2. Fijalkowskim_Eshopping_JavaFX

This version utilizes JavaFX to create a graphical user interface for the EShopping application. Users can browse items, view details, and make purchases through an interactive graphical interface.
![image](https://github.com/Fijalkowskim/Fijalkowskim_EShopping_repo/assets/91847461/8532298a-2a36-4d2d-9ee7-f9942508a615)

#### Example Code:

```java
// Example code from InitServlet in JavaFX version
@WebServlet("/init")
public class InitServlet extends HttpServlet {
    // ... (Code for processing requests, handling cookies, and more)
}
```

### 3. Fijalkowskim_Eshopping_Web

This version provides a web-based interface for the EShopping application. Users can access the system through a web browser, browse items, and perform transactions.
![image](https://github.com/Fijalkowskim/Fijalkowskim_EShopping_repo/assets/91847461/5a8d5cac-afb1-4219-a77e-3f95c71df316)

#### Example Code:

```java
// Example code from InitServlet in Web version
@WebServlet("/init")
public class InitServlet extends HttpServlet {
    // ... (Code for processing requests, handling cookies, and more)
}
```

### 4. Fijalkowskim_Eshopping_Web + MySQL

This version extends the web-based version by incorporating MySQL for data storage. It includes database creation scripts and interactions for managing items, user sessions, and purchases.
![image](https://github.com/Fijalkowskim/Fijalkowskim_EShopping_repo/assets/91847461/bc93be35-07bc-4ede-a20f-9bf0b91bda2f)
![image](https://github.com/Fijalkowskim/Fijalkowskim_EShopping_repo/assets/91847461/a4d5f818-8ff3-400b-b399-39ca1eb95ec8)


#### Example Code:

```java
// Example code for interacting with MySQL database
public class InitServlet extends HttpServlet {
    // ... (Code for creating tables, loading session data, and more)
}
```
