package org.test.automation;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.automation.browser.BrowserManager;
import org.test.automation.config.LinkedInAutomationConfig;
import org.test.automation.linkedin.service.LinkedInCvAutomationService;

import static org.test.automation.config.LinkedInAutomationConfig.DOWNLOAD_DIR;
import static org.test.automation.config.LinkedInAutomationConfig.LOGIN_WAIT_SECONDS;

public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

    LOGGER.info("Starting LinkedIn CV automation");

    try (Playwright playwright = Playwright.create()) {

      Files.createDirectories(DOWNLOAD_DIR);

      BrowserContext context = BrowserManager.createContext(playwright);
      Page page = context.newPage();

      LOGGER.info("Login required, waiting for manual authentication");
      page.navigate("https://www.linkedin.com/login");
      page.waitForTimeout(LOGIN_WAIT_SECONDS);

      BrowserManager.persistSession(context);
      LOGGER.info("Session state saved successfully");

      page.navigate(LinkedInAutomationConfig.APPLICANTS_URL);
      page.waitForLoadState();

      new LinkedInCvAutomationService(page).run();

      LOGGER.info("All processing completed successfully");
    } catch (Exception e) {
      LOGGER.error("An unexpected error occurred", e);
    }
  }
}
