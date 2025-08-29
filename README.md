# Selenium Testing Bootcamp

Beginner‑friendly Selenium automation project built with Java 11+, Maven, and TestNG.

## Why this repo
- Simple, readable tests to learn the basics fast
- Gradual layering (start minimal, then add Page Objects, waits, data, CI)
- Portfolio‑ready structure and commit history

## Tech Stack
- Java 11+
- Selenium WebDriver 4.x
- TestNG
- Maven
- Page Object Model (POM) as you advance
- WebDriverManager for driver binaries

## Prerequisites
- JDK 11 or newer (java -version)
- Maven (mvn -v)
- Google Chrome or Firefox installed

## Quick Start
1) Clone and open the project
2) Run the minimal smoke test:

```
mvn clean test
```


Tip: If you want to watch the browser longer, add short sleeps in the test (already shown in the sample).

## Project Structure
```
Selenium-Testing-Bootcamp/
├── README.md
├── pom.xml
├── src/
│   ├── main/java/
│   │   └── pages/                # Page Objects (e.g., AmazonSearchBar)
│   └── test/java/
│       └── tests/                # TestNG tests (e.g., OpenGoogleTest)
└── (optional as you grow)
    ├── drivers/                  # Local driver binaries (if ever needed)
    ├── reports/                  # Test reports
    └── screenshots/              # Captured screenshots
```

## Running Tests
- All tests:
```
mvn clean test
```

- A specific test class:
```
mvn -Dtest=OpenGoogleTest test
```

## Troubleshooting
- Browser doesn’t open: ensure Chrome/Firefox is installed and up to date.
- Driver issues: WebDriverManager manages binaries automatically; a clean build often resolves issues.
- Elements not found: the web may change; verify locators in the browser dev tools.

## License
MIT — feel free to use and adapt for learning and interviews.
