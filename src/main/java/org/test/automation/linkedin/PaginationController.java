package org.test.automation.linkedin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.automation.browser.InteractionPacing;

import static org.test.automation.config.LinkedInAutomationConfig.NEXT_PAGE_BUTTON;
import static org.test.automation.config.LinkedInAutomationConfig.PAGE_SWITCH_WAIT_SECONDS;

public class PaginationController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaginationController.class);

  private final Page page;

  public PaginationController(Page page) {
    this.page = page;
  }

  public boolean isNextPageAvailable() {
    Locator nextBtn = page.locator(NEXT_PAGE_BUTTON);
    return nextBtn.count() > 0 && nextBtn.first().isEnabled();
  }

  public void switchToNextPage() {
    LOGGER.info("Moving to the next applicants page");
    page.locator(NEXT_PAGE_BUTTON).first().click();
    InteractionPacing.randomWait(PAGE_SWITCH_WAIT_SECONDS, PAGE_SWITCH_WAIT_SECONDS + 5);
  }
}
