package org.test.automation.linkedin.service;

import com.microsoft.playwright.Page;
import org.test.automation.linkedin.ApplicantProcessor;
import org.test.automation.linkedin.PaginationController;

public class LinkedInCvAutomationService {

  private final ApplicantProcessor processor;

  private final PaginationController pagination;

  public LinkedInCvAutomationService(Page page) {
    this.processor = new ApplicantProcessor(page);
    this.pagination = new PaginationController(page);
  }

  public void run() {
    do {
      processor.processCurrentPage();
    } while (pagination.isNextPageAvailable() && switchPageSafely());
  }

  private boolean switchPageSafely() {
    pagination.switchToNextPage();
    return true;
  }
}
