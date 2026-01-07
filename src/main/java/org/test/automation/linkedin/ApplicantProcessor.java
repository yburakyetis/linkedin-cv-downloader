package org.test.automation.linkedin;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.automation.browser.InteractionPacing;

import static org.test.automation.config.LinkedInAutomationConfig.APPLICATION_LIST;
import static org.test.automation.config.LinkedInAutomationConfig.DETAILS_PANEL;
import static org.test.automation.config.LinkedInAutomationConfig.DETAIL_PANEL_TIMEOUT_MS;
import static org.test.automation.config.LinkedInAutomationConfig.DOWNLOAD_BUTTON;
import static org.test.automation.config.LinkedInAutomationConfig.DOWNLOAD_DIR;
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
    Locator applicants = page.locator(APPLICATION_LIST);
    int count = applicants.count();

    LOGGER.info("Number of applicants on current page: {}", count);

    for (int i = 0; i < count; i++) {

      if (downloadCount.get() >= MAX_CV_PER_RUN) {
        LOGGER.warn("Maximum CV download limit reached: {}", MAX_CV_PER_RUN);
        return;
      }

      Locator applicant = applicants.nth(i);

      applicant.scrollIntoViewIfNeeded();
      InteractionPacing.microPause();

      applicant.click();

      waitForDetailsPanel();
      scrollToDownloadSection();
      tryDownloadCv();

      InteractionPacing.randomWait(MIN_WAIT_SECONDS, MAX_WAIT_SECONDS);
    }
  }

  private void waitForDetailsPanel() {
    page.locator(DETAILS_PANEL).waitFor(new Locator.WaitForOptions().setTimeout(DETAIL_PANEL_TIMEOUT_MS));
  }

  private void tryDownloadCv() {
    var downloadBtn = page.locator(DOWNLOAD_BUTTON);

    if (downloadBtn.count() == 0 || !downloadBtn.first().isVisible()) {
      LOGGER.info("No downloadable CV available for this applicant");
      return;
    }

    try {
      Download download = page.waitForDownload(() -> {downloadBtn.first().click();});

      Path downloadPath =
          DOWNLOAD_DIR.resolve(download.suggestedFilename());

      download.saveAs(downloadPath);

      LOGGER.info("Saved to: {}", downloadPath.toAbsolutePath());
      LOGGER.info("File exists after saveAs: {}", Files.exists(downloadPath));

      int total = downloadCount.incrementAndGet();
      LOGGER.info("CV downloaded and saved: {} | Total: {}", downloadPath.getFileName(), total);

      InteractionPacing.randomWait(3, 8);

    } catch (Exception e) {
      LOGGER.error("Failed to download CV", e);
    }
  }

  private void scrollToDownloadSection() {

    Locator downloadBtn = page
        .locator(DETAILS_PANEL)
        .locator(DOWNLOAD_BUTTON);

    downloadBtn.first().waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.ATTACHED)
            .setTimeout(DETAIL_PANEL_TIMEOUT_MS));

    if (downloadBtn.count() > 0) {
      downloadBtn.first().scrollIntoViewIfNeeded();
      InteractionPacing.microPause();
    }
  }
}
