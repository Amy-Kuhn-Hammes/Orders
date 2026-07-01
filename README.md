# Orders API

## Requirements

Before running the project, make sure you have installed:

* Java 21
* Maven
* Docker
* Docker Compose

## 1. Clone the repository

```bash
git clone https://github.com/Amy-Kuhn-Hammes/Orders.git
cd Orders
```

## 2. Start the application

Run the following command:

```bash
docker compose up --build
```

This command will:

* Build the Spring Boot application
* Start a PostgreSQL database
* Start the API
* Connect the application to the database automatically

## 3. Test the API

The application will be available at:

```
http://localhost:8080
```

Create an order using:

```
POST http://localhost:8080/orders
```

Example request:

```json
{
  "customerName": "Amy",
  "address": "Pelotas, RS",
  "creditCardNumber": "4111111111111111",
  "products": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

## 4. Stop the application

```bash
docker compose down
```

To also remove the database volume:

```bash
docker compose down -v
```
