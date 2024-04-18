# Online Book Store Project

This is a backend-focused Spring Boot project for an online book store. The project implements core functionalities such as user authentication, book management, shopping cart, checkout, order management, search/filter, error handling, security, logging, and documentation.

## Technologies Used

- Java 8
- Spring Boot
- Spring Data JPA
- Spring Security
- Hibernate
- Maven
- RESTful APIs
- MySQL (or your preferred database)
- Swagger (or OpenAPI) for API documentation
- Postman
- JWT   

## Features Implemented

1. **User Authentication and Authorization:**
   - Registration, login, logout
   - Password hashing
   - User roles (admin, regular user)

2. **Book Management:**
   - CRUD operations for books
   - Pagination, sorting, filtering
   - Search by title, author, category

3. **Shopping Cart and Checkout:**
   - Add/remove items from cart
   - Update quantities, calculate totals
   - Checkout process with order summary

4. **Order Management:**
   - Place orders, view order history
   - Update order status, generate invoices

5. **Security and Data Privacy:**
   - Input validation, error handling
   - SQL injection prevention, data encryption

6. **Documentation:**
   - Swagger/OpenAPI for API documentation
   - Readme file with project overview, setup instructions


## DB_SCHEMA
  ![DB_SCHEMA](https://raw.githubusercontent.com/SaiD-MH/Online-BookStore-RestAPIs/main/src/main/resources/static/DB_SCHEMA.png)

## Setup Instructions

1. Clone the repository:
   git clone https://github.com/SaiD-MH/Online-BookStore-RestAPIs.git

2. Navigate to the project directory:
   cd Online-BookStore-RestAPIs

3. Build the project using Maven:
   mvn clean install

4. Run the application locally:
   mvn spring-boot:run


5. Access the API documentation:
- Open your browser and go to: `http://localhost:8080/swagger-ui.html`
  
6.access the application in your browser:
- Open your web browser and go to: `http://localhost:8080`## Deployment

### Database Configuration

1. By default, the application uses an in-memory H2 database for local testing.
2. For production or persistent storage, configure a MySQL or PostgreSQL database by updating the `application.properties` file with the database URL, username, password, and other settings.

## Contributing

Contributions to this project are welcome. Please fork the repository, make your changes, and submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).




