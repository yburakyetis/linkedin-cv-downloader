package org.test.automation.linkedin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.automation.browser.InteractionPacing;

import static org.test.automation.config.LinkedInAutomationConfig.APPLICATION_LIST;
import static org.test.automation.config.LinkedInAutomationConfig.GO_NEXT_BUTTON;

public class PaginationController {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(PaginationController.class);

  private final Page page;

  public PaginationController(Page page) {
    this.page = page;
  }

  public boolean goNextPage() {

    Locator nextBtn = page.locator(GO_NEXT_BUTTON);

    if (nextBtn.count() > 0 && nextBtn.first().isEnabled()) {
      LOGGER.info("Moving to the next applicants page");
      nextBtn.first().click();

      page.locator(APPLICATION_LIST)
          .first()
          .waitFor(new Locator.WaitForOptions().setTimeout(15000));
      InteractionPacing.randomWait(10, 30);
      return true;
    }
    LOGGER.info("Reached the last page of applicants");
    return false;
  }
}
