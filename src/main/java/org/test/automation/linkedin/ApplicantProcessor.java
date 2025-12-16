package org.test.automation.linkedin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.automation.browser.InteractionPacing;
import org.test.automation.config.LinkedInAutomationConfig;

import static org.test.automation.config.LinkedInAutomationConfig.APPLICATION_LIST;
import static org.test.automation.config.LinkedInAutomationConfig.DOWNLOAD_BUTTON;

public class ApplicantProcessor {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ApplicantProcessor.class);

  private final Page page;

  private final AtomicInteger downloadCount = new AtomicInteger(0);

  public ApplicantProcessor(Page page) {
    this.page = page;
  }

  public void processCurrentPage() {

    Locator applicants = page.locator(APPLICATION_LIST);
    int count = applicants.count();

    LOGGER.info("Number of applicants on current page: {}", count);

    for (int i = 0; i < count; i++) {

      if (downloadCount.get() >= LinkedInAutomationConfig.MAX_CV_PER_RUN) {
        LOGGER.warn("Maximum CV download limit reached: {}",
                    LinkedInAutomationConfig.MAX_CV_PER_RUN);
        return;
      }

      Locator applicant = applicants.nth(i);
      applicant.click();

      page.locator(LinkedInAutomationConfig.DETAILS_PANEL)
          .waitFor(new Locator.WaitForOptions().setTimeout(15000));
      InteractionPacing.microPause();

      scrollDetailPanel();
      tryDownloadCv();

      if (downloadCount.get() % 10 == 0 && downloadCount.get() > 0) {
        InteractionPacing.randomWait(30, 60);
      } else {
        InteractionPacing.randomWait(LinkedInAutomationConfig.MIN_WAIT, LinkedInAutomationConfig.MAX_WAIT);
      }
    }
  }

  private void scrollDetailPanel() {
    InteractionPacing.randomScrollDown(
        () -> page.mouse().wheel(0, 200 + (int) (Math.random() * 400)));
  }

  private void tryDownloadCv() {

    Locator detailPanel = page.locator(LinkedInAutomationConfig.DETAILS_PANEL);

    Locator downloadBtn = detailPanel.locator(DOWNLOAD_BUTTON);

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
