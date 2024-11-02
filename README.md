# Portfolio Management System

This repository contains a microservices-based application for managing a user's stock portfolio. The application is built with three microservices, each with a unique purpose:

## Microservices Overview

1. ### **Service Registry (`service-register`)**
   - **Purpose**: Acts as the Eureka Server for service discovery.
   - **Description**: The `service-register` is responsible for keeping track of the active microservices in the system. It allows other services to register themselves and discover each other, enabling dynamic scaling and resilience.

2. ### **Portfolio Manager (`portfolio-manager`)**
   - **Purpose**: Manages user portfolios by adding (buying), deleting (selling), and displaying stocks.
   - **Description**: The `portfolio-manager` service provides core functionality for managing a user’s stock portfolio. It allows:
     - **Adding (Buying) Stocks**: Users can add stocks to their portfolio.
     - **Deleting (Selling) Stocks**: Users can remove stocks from their portfolio.
     - **Viewing Portfolio**: Users can see all the stocks in their portfolio.
     - **User Management**: Allows new users to create accounts and includes functionality for authenticating existing users.

3. ### **Stock Price Service**
   - **Purpose**: Fetches real-time stock prices based on stock symbols.
   - **Description**: This service provides real-time stock price data, which can be used to view current valuations in the portfolio. Users provide a stock symbol, and the service returns up-to-date pricing information for that stock.
   - Alpha Vantage allows only **25 requests per day** for accessing free stock API data.

## Setup Instructions

### Database Setup
Before running the application, set up the database:
1. Locate the `stockmanager.sql` file inside the `portfolio-manager` service directory.
2. Run this script in your MySQL database to create the required `stockmanager` schema and tables.

   ```sql
   SOURCE portfolio-manager/src/main
/resources/stockmanager.sql;

Pre-requisite: MySql, STS, VSCode 

