# Customer Account Service

This project provides a solution for managing customer accounts, including creating new current accounts, handling transactions, and updating account balances. It implements core features such as checking for existing customer accounts, adding initial credit to accounts, and saving transaction data.

---

## Table of Contents

1. [Overview](#overview)
2. [Command Line Runner](#commandlinerunner)
3. [Prerequisites](#prerequisites)
4. [Installation](#installation)
5. [Setup](#setup)
7. [Testing](#testing)
8. [API Endpoints](#api-endpoints)
---

## Overview

The **Customer Account Service** is built using **Spring Boot** and provides an API for creating and managing customer accounts. The service handles the creation of current accounts, adding initial credit to accounts, and managing transactions. It includes appropriate error handling for different scenarios such as non-existent customers or accounts.

---

## CommandLineRunner

### Data Initialization

The application includes a **CommandLineRunner** implementation that runs automatically when the application starts. This runner is designed to initialize the database with sample data for customers, accounts, and transactions. It's particularly useful for development and testing as it populates the database with predefined entries.

#### Purpose of CommandLineRunner:

- **Populate Sample Data:** Automatically adds a set of sample customers, accounts, and transactions to the database when the application starts.
- **Predefined Setup:** Useful for developers who need sample data for testing the application without having to manually create records.
- **Automatic Execution:** The `CommandLineRunner` is executed automatically when the Spring Boot application is launched, so no manual intervention is required to load the sample data.

#### How it Works:

1. **Sample Customers:** The `CommandLineRunner` creates several customers with unique `customerId`, `name`, and `surname`.
2. **Accounts:** For each customer, a new savings account is created with a generated account number. Each account is linked to the customer.
3. **Transactions:** For each created account, three sample transactions are generated. Each transaction includes an amount, transaction type (CREDIT), and a description (e.g., "Deposit", "POS transfer", "Salary payment").
4. **Saving Data:** All created customers, accounts, and transactions are saved into the database automatically.

Here’s the `DataInitializer` class that implements the `CommandLineRunner`:

---

### Unit Testing with Mockito

The testing framework includes **Mockito** for mocking dependencies, allowing us to focus on specific business logic in isolation.

---

## Features

- **Account Creation:** Allows creation of a new current account for a customer.
- **Retrieving Customer Info:** Allows for getting saved customer info .
- **Transaction Management:** Tracks transactions for customer accounts and updates balances accordingly.
- **Error Handling:** Includes handling for invalid operations like creating multiple current accounts for a customer.

---

## Prerequisites

To run the project, you will need the following:

- **Java 17+** (required version)
- **Spring Boot** (backend framework)
- **Maven** (build tool)
- **Postman** (optional, for API testing)

---

## SWAGGER URL
http://localhost:8080/swagger-ui/index.html#/

## Installation

### 1. Clone the Repository

First, clone the repository to your local machine:

```bash
git clone https://github.com/Edceebee/3LineAssessment.git


