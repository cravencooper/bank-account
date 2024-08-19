# bank-account

## Overview
The overall purpose of this application is to track the balance of a bank account by processing credit and debit transactions in real-time. It supports the creation of transactions, tracks the resulting account balance, and meets an audit requirement by sending the last 1,000 transactions in batches to a downstream audit system. The system is optimised to minimise the number of audit batches sent, reducing associated costs.

Key Components of the application

There are 3 main components of this application. These are - 

1. Producer
2. Balance Tracker
3. Auditing system

### Producer

- Responsible for generating transactions at a rate of 50 per second, split evenly between credits and debits.
- Each transaction has a randomly generated ID and an amount between £20,000 and £500,000.
- The producer operates on two separate threads: one for credits and one for debits.


### Balance Tracker

- Processes transactions to update the account balance.
- Tracks the overall balance by aggregating all processed transactions.
- Publishes batches of transactions to an audit system, ensuring the batch size and value constraints are respected.
- Exposes the current account balance via a REST API.

### Audit System:

- Receives transaction batches where each batch is limited to a total value of £1,000,000 and a maximum of 1,000 transactions.
- The system prints batch details to the console, including batch number, total value, and the count of transactions.
- Optimized to minimize the number of batches sent to reduce costs.

## Requirements

- This applcation has been built using Java, Maven and Spring-boot.
