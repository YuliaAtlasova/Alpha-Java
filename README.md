# Release Scheduler
## Overview
The Release Scheduler is a Java application designed to help user to maximize the number of releases they can validate within a given sprint duration. The program reads a list of releases, each with a delivery day and duration, and calculates the maximum number of releases that can be completed in a 10-day sprint.

### Features
Reads input from a file containing release delivery days and validation durations.

Calculates the maximum number of releases that can be validated within the sprint duration.

Validates the calculated results against expected output.

### Project Structure
```
alpha-java/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── ReleaseScheduler.java
│   │
│   └── resources/
│       ├── input/
│       │   └── incomeTasks01.txt
│       └── output/
│           └── outputTasks01.txt
│           └── resultReleases.txt
│
├── pom.xml
└── README.md
```
### Requirements
Java Version: 21 or later

Maven: For managing dependencies and building the project

### Installation
Clone the repository:
```
git clone <repository-url>
```
Navigate to the project directory:
```
cd alpha-java
```
Ensure you have Maven installed. If not, install it by following the Maven Installation Guide.

Build the project using Maven:
```
mvn clean install
```
### Usage
Prepare your input file in src/resources/input/incomeTasks01.txt with the following format:
```
<delivery_day1> <duration1>
<delivery_day2> <duration2>
...
```
example:
```
1 1
2 1
3 1
9 1
10 4
10 2
9 5
10 3
4 5
```
Prepare your expected output file in src/resources/output/outputTasks01.txt with the following format:

First line: The total number of releases user can validate within the 10-day sprint.

Subsequent lines: Each line contains two integers representing the start day and end day of a validated release.
```
5
1 1
2 2
3 3
4 8
9 9
```
Run the ReleaseScheduler:
```
mvn exec:java -Dexec.mainClass="ReleaseScheduler"
```
The results will be validated against the expected output defined in src/resources/output/outputTasks01.txt.
### Testing
This project uses AssertJ for assertions during validation. Make sure to include the necessary dependencies in your pom.xml.
### Test Scenarios
The project includes 10 input task files located in the src/resources/input/ directory, each covering a different base test scenario. Corresponding 10 expected result files are stored in src/resources/output/. These files serve to validate the correctness of the algorithm under different conditions.
These files cover various scenarios such as overlapping releases, single-day releases, edge cases, releases delivered towards the end of the sprint etc.
