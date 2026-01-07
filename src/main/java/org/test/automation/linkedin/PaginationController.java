package org.test.automation.linkedin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.automation.browser.InteractionPacing;

import static org.test.automation.config.LinkedInAutomationConfig.ACTIVE_PAGE_BUTTON;
import static org.test.automation.config.LinkedInAutomationConfig.PAGINATION_PAGE_ITEMS;

public class PaginationController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaginationController.class);

  private final Page page;

  public PaginationController(Page page) {
    this.page = page;
  }

  public boolean isNextPageAvailable() {

    Locator pages = page.locator(PAGINATION_PAGE_ITEMS);
    Locator activeBtn = page.locator(ACTIVE_PAGE_BUTTON);

    if (activeBtn.count() == 0) {
      return false;
    }

    int activeIndex = -1;

    for (int i = 0; i < pages.count(); i++) {
      Locator btn = pages.nth(i).locator("button");
      if (btn.getAttribute("aria-current") != null) {
        activeIndex = i;
        break;
      }
    }

    return activeIndex != -1 && activeIndex + 1 < pages.count();
  }

  public void switchToNextPage() {

    Locator pages = page.locator(PAGINATION_PAGE_ITEMS);

    for (int i = 0; i < pages.count(); i++) {
      Locator btn = pages.nth(i).locator("button");

      if (btn.getAttribute("aria-current") != null) {
        Locator nextBtn = pages.nth(i + 1).locator("button");

        LOGGER.info("Moving to the next applicants page");
        nextBtn.scrollIntoViewIfNeeded();
        InteractionPacing.microPause();
        nextBtn.click();

        InteractionPacing.randomWait(8, 15);
        return;
      }
    }

    LOGGER.info("Reached the last page");
  }
}
