# LinkedIn CV Downloader Automation

This project automates the process of downloading applicant CVs from LinkedIn job postings using **Java + Playwright**, significantly reducing manual effort for HR teams.

The automation mimics real human behavior to minimize detection risk and significantly reduces manual effort for HR teams.

---

## 🚀 Features

- Human-like browser automation (non-headless)
- Sequential applicant processing
- Automatic CV download (when available)
- Pagination support
- Session persistence (login once)
- Configurable rate limiting & safety limits
- Structured logging (SLF4J + Logback)
- Resilient selectors based on LinkedIn HTML structure

---

## 🧠 How It Works

1. Opens a real Chromium browser
2. Uses an existing LinkedIn session (or waits for manual login)
3. Navigates to the applicants page
4. Clicks applicants one by one
5. Downloads CVs when available
6. Moves through pages until completion or safety limit is reached

---

## 📦 Tech Stack

- Java 21
- Playwright for Java
- Maven
- SLF4J + Logback
- IntelliJ IDEA

---

## 📁 Project Structure

src/main/java
└── org/test/automation
├── browser
│ ├── BrowserManager.java
│ └── InteractionPacing.java
├── config
│ └── LinkedInAutomationConfig.java
├── linkedin
│ ├── ApplicantProcessor.java
│ └── PaginationController.java
└── Main.java

---

## ⚙️ Configuration

Edit the following file:
Key settings:

- `APPLICANTS_URL` – LinkedIn applicants page URL
- `MAX_CV_PER_RUN` – Safety limit per execution
- `MIN_WAIT` / `MAX_WAIT` – Random wait times
- CSS selectors for UI elements

---

## ▶️ How to Run

### 1️⃣ Install Playwright browsers
```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```
### 2️⃣ Run the application
From IntelliJ:

Open Main.java

Right-click → Run

Or via terminal:
mvn clean package
java -jar target/linkedin-cv-downloader.jar

## 🔐 First-Time Login

On first run, the browser will open LinkedIn login page

Log in manually (2FA supported)

Session will be saved locally

Subsequent runs will reuse the session automatically

To force re-login, delete:

linkedin-session.json

## 📂 Download Location

CV files are downloaded to the default browser download directory.

## ⚠️ Safety Notes

Do NOT run in headless mode

Avoid downloading large numbers of CVs in a short time

Recommended maximum: 200–300 CVs per day

Random delays are intentionally applied to reduce detection risk

## ⚠️ Disclaimer

This project is created solely as an automation project for internal and experimental purposes.  
It is not intended for production use.

The project is provided as-is, without any warranties or guarantees.  
The authors assume no responsibility for any issues, damages, or consequences that may arise from using this project.
