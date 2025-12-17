package org.test.automation.linkedin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.automation.browser.InteractionPacing;

import static org.test.automation.config.LinkedInAutomationConfig.APPLICATION_LIST;
import static org.test.automation.config.LinkedInAutomationConfig.DETAILS_PANEL;
import static org.test.automation.config.LinkedInAutomationConfig.DETAIL_PANEL_TIMEOUT_MS;
import static org.test.automation.config.LinkedInAutomationConfig.DOWNLOAD_BUTTON;
import static org.test.automation.config.LinkedInAutomationConfig.MAX_CV_PER_RUN;
import static org.test.automation.config.LinkedInAutomationConfig.MAX_WAIT_SECONDS;
import static org.test.automation.config.LinkedInAutomationConfig.MIN_WAIT_SECONDS;

public class ApplicantProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicantProcessor.class);

  private final Page page;

  private final AtomicInteger downloadCount = new AtomicInteger();

  public ApplicantProcessor(Page page) {
    this.page = page;
  }

  public void processCurrentPage() {

    var applicants = page.querySelectorAll(APPLICATION_LIST);
    LOGGER.info("Number of applicants on current page: {}", applicants.size());

    for (var applicant : applicants) {

      if (downloadCount.get() >= MAX_CV_PER_RUN) {
        LOGGER.warn("Maximum CV download limit reached: {}", MAX_CV_PER_RUN);
        return;
      }

      applicant.click();

      waitForDetailsPanel();
      tryDownloadCv();

      InteractionPacing.randomWait(MIN_WAIT_SECONDS, MAX_WAIT_SECONDS);
    }
  }

  private void waitForDetailsPanel() {
    page.locator(DETAILS_PANEL).waitFor(new Locator.WaitForOptions().setTimeout(DETAIL_PANEL_TIMEOUT_MS));
  }

  private void tryDownloadCv() {
    var downloadBtn = page.locator(DOWNLOAD_BUTTON);

    if (downloadBtn.count() > 0 && downloadBtn.first().isVisible()) {
      downloadBtn.first().click();
      int total = downloadCount.incrementAndGet();
      LOGGER.info("CV downloaded successfully | Total downloaded: {}", total);
      InteractionPacing.randomWait(3, 8);
    } else {
      LOGGER.info("No downloadable CV available for this applicant");
    }
  }
}
