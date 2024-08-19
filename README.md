# bank-account

Overview
This application is designed to track the balance of a bank account by processing credit and debit transactions in real-time. It supports the creation of transactions, tracks the resulting account balance, and meets an audit requirement by sending the last 1,000 transactions in batches to a downstream audit system. The system is optimized to minimize the number of audit batches sent, reducing associated costs.

Key Components
Producer:

Responsible for generating transactions at a rate of 50 per second, split evenly between credits and debits.
Each transaction has a randomly generated ID and an amount between £20,000 and £500,000.
The producer operates on two separate threads: one for credits and one for debits.
Balance Tracker:

Processes transactions to update the account balance.
Tracks the overall balance by aggregating all processed transactions.
Publishes batches of transactions to an audit system, ensuring the batch size and value constraints are respected.
Exposes the current account balance via a REST API.
Audit System:

Receives transaction batches where each batch is limited to a total value of £1,000,000 and a maximum of 1,000 transactions.
The system prints batch details to the console, including batch number, total value, and the count of transactions.
Optimized to minimize the number of batches sent to reduce costs.
Requirements
The application is built using Java and Maven.
Sufficient functional test coverage ensures the accuracy of balance calculations.
The application is designed for scalability, allowing the batch size to be increased as needed for performance testing.
All code and documentation are fictitious and should not reference real companies or individuals.
Technical Considerations
Concurrency: The producer uses multiple threads to simulate high-throughput transaction generation.
Scalability: The system is designed to handle large volumes of transactions and batch processing, with considerations for scaling the batch size.
Performance: Efficient batch processing and minimal batch submissions to the audit system help optimize performance and cost.
This application is a robust solution for real-time bank account tracking and auditing, ensuring accuracy, performance, and scalability.
