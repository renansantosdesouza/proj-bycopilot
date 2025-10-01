# Postman Collections for Customer API

This directory contains Postman collections and environments for testing the Customer API.

## Files

- `Customer-API.postman_collection.json`: Collection of API requests for testing all endpoints
- `Customer-API-Local.postman_environment.json`: Environment variables for local testing

## How to Use

1. Import both files into Postman
2. Select the "Customer API - Local" environment
3. Start the API application locally
4. Execute the requests in the collection

## Available Requests

- Create Customer (POST)
- Get All Customers (GET)
- Get Customer by ID (GET)
- Update Customer (PUT)
- Delete Customer (DELETE)
- Invalid Customer Creation (POST) - For testing validation