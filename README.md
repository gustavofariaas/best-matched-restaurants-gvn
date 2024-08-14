# Best Matched Restaurants Application

This is a Spring Boot application that provides an API for retrieving the best matched restaurants.

## Prerequisites

- Java 11
- Maven

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Installing

1. Clone the repository
```bash
git clone https://github.com/gustavofariaas/best-matched-restaurants.git
```
2. Open the project in your IDE

3. Run the commands to build the project and run.
```bash
mvn clean install
mvn spring-boot:run
```
4. The application will be running on http://localhost:8080
5. To run the tests, you can run the command below
```bash
mvn test
```

## How to run the API

1. To get the list of matched restaurants, you can use the following endpoint curl:
```bash
curl --location 'http://localhost:8080/getListOfMatchedRestaurants?restaurantName=&price=20&customerRating=5&cuisine=chinese&distance=1' 
```
2. If you have Postman, you can also use it to test the cURL command. Just import the collection file in the root of the project and run the request.

3. You can provide 5 parameters to the endpoint, parameters are not mandatory:

- restaurantName: The name of the restaurant
- price: The price of the restaurant 
- customerRating: The customer rating of the restaurant 
- cuisine: The cuisine of the restaurant
- distance: The distance of the restaurant 

![alt text](postman.png)

Built With:

Spring Boot - The web framework used
Maven - Dependency Management
H2 Database - In-memory database