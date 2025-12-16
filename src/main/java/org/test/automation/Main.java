package org.test.automation;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.automation.browser.BrowserManager;
import org.test.automation.config.LinkedInAutomationConfig;
import org.test.automation.linkedin.ApplicantProcessor;
import org.test.automation.linkedin.PaginationController;

public class Main {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

    LOGGER.info("Starting LinkedIn CV automation");

    try (Playwright playwright = Playwright.create()) {

      BrowserContext context =
          BrowserManager.createContext(playwright);

      Page page = context.newPage();

      page.navigate(LinkedInAutomationConfig.APPLICANTS_URL);
      page.waitForTimeout(20000);

      if (page.url().contains("/login")) {
        LOGGER.info("Login required, waiting for manual authentication");
        page.waitForTimeout(20000);
        BrowserManager.persistSession(context);
        LOGGER.info("Session state saved successfully");
      }

      ApplicantProcessor processor =
          new ApplicantProcessor(page);

      PaginationController pagination =
          new PaginationController(page);

      do {
        processor.processCurrentPage();
      } while (pagination.goNextPage());
      LOGGER.info("All processing completed successfully");
    }
    catch (Exception e) {
      LOGGER.error("An unexpected error occurred", e);
    }
  }
}
