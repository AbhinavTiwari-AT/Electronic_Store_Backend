# **Electronic Store - E-commerce Application**

## **Overview**
The Electronic Store is a full-stack web application built using **Spring Boot** for the backend. It provides a platform where users can browse, purchase, and manage electronic products efficiently. The application supports **user authentication, product management, order processing, and secure transactions**.

## **Features**
- **User Management**: Registration, login, profile management, and role-based authentication (**JWT Security**).
- **Role-based Access Control**: 
  - **Admin** can manage users, products, and orders.
  - **Customers** can browse and purchase products.
- **Product Management**: Add, update, delete, and search products with image upload support.
- **Category Management**: Products are categorized for easy navigation.
- **Shopping Cart & Orders**: Users can add products to a cart and place orders with order history tracking.
- **Image Upload**: Users and admins can upload images for products and profile pictures.
- **API Documentation**: Implemented using **Swagger UI** for easy testing and interaction.

## **Tech Stack**
- **Backend**: Java, Spring Boot, Spring Security, Hibernate, JWT
- **Frontend**: React.js *(Planned for future implementation)*
- **Database**: MySQL
- **Build Tool**: Maven
- **Testing Tools**: JUnit, Postman
- **Logging & Monitoring**: SLF4J, Logback
- **API Documentation**: Swagger

## **Installation Guide**

### **Prerequisites**
- **Java 17+**
- **MySQL**
- **Maven**
- **Postman** (for testing APIs)

### **Steps to Run**
1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/electronic-store.git
   ```
2. **Navigate to the project directory:**
   ```sh
   cd electronic-store
   ```
3. **Configure the database in `application.properties`:**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/electronic_store
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
4. **Install dependencies and build the project:**
   ```sh
   mvn clean install
   ```
5. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```
6. **Access API documentation at:**
   - [Swagger UI](http://localhost:8080/swagger-ui/)

## **API Endpoints**

### **Authentication**
- `POST /auth/register` - Register a new user
- `POST /auth/login` - Authenticate and get JWT token

### **Users**
- `GET /users/{id}` - Get user details
- `PUT /users/{id}` - Update user details

### **Products**
- `GET /products` - Get all products
- `POST /products` - Add a new product (**Admin only**)
- `PUT /products/{id}` - Update a product (**Admin only**)
- `DELETE /products/{id}` - Delete a product (**Admin only**)

### **Categories**
- `GET /categories` - List all categories
- `POST /categories` - Create a category (**Admin only**)

### **Orders**
- `POST /orders` - Create an order
- `GET /orders/user/{userId}` - Get orders for a user
- `DELETE /orders/{id}` - Cancel an order

### **API Endpoints Table**
| **Module**        | **Method** | **Endpoint**                  | **Description**                     |
|------------------|-----------|--------------------------------|-------------------------------------|
| **Authentication** | POST      | `/auth/register`              | Register a new user                |
|                  | POST      | `/auth/login`                 | Authenticate and get JWT token     |
| **Users**        | GET       | `/users/{id}`                 | Get user details                   |
|                  | PUT       | `/users/{id}`                 | Update user details                |
| **Products**     | GET       | `/products`                   | Get all products                   |
|                  | POST      | `/products`                   | Add a new product (**Admin only**)  |
|                  | PUT       | `/products/{id}`              | Update a product (**Admin only**)  |
|                  | DELETE    | `/products/{id}`              | Delete a product (**Admin only**)  |
| **Categories**   | GET       | `/categories`                 | List all categories                |
|                  | POST      | `/categories`                 | Create a category (**Admin only**) |
| **Orders**       | POST      | `/orders`                     | Create an order                    |
|                  | GET       | `/orders/user/{userId}`       | Get orders for a user              |
|                  | DELETE    | `/orders/{id}`                | Cancel an order                    |

## **Future Enhancements**
- **Payment Gateway Integration** (Razorpay/Stripe)
- **Wishlist Feature**
- **Review & Rating System**
- **Dockerization & Deployment to Cloud** (AWS/GCP)
- **Full-stack Integration with React.js**
- **Email Notifications for Orders & Password Reset**

## **Contributing**
Feel free to **fork** the repository and **create pull requests** for improvements.

## **Contact**
For queries, reach out to: **abhinavtiwari3056@gmail.com**
