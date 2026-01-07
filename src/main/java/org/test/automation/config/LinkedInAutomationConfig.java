package org.test.automation.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class LinkedInAutomationConfig {

  private LinkedInAutomationConfig() {
  }

  // Session
  public static final String STORAGE_STATE_PATH = "linkedin-session.json";

  public static final String APPLICANTS_URL =
      "https://www.linkedin.com/hiring/jobs/XXXXXXXX/applicants/XXXXXXXX";

  public static final Path DOWNLOAD_DIR =
      Paths.get(System.getProperty("user.home"), "Downloads");

  // Limits
  public static final int MAX_CV_PER_RUN = 250;

  // Waits (seconds)
  public static final int MIN_WAIT_SECONDS = 5;

  public static final int MAX_WAIT_SECONDS = 30;

  public static final int DETAIL_PANEL_TIMEOUT_MS = 25_000;

  public static final int LOGIN_WAIT_SECONDS = 15_000;

  // Selectors
  public static final String DOWNLOAD_BUTTON =
      "a.inline-flex.align-items-center.link-without-visited-state[href]";

  public static final String DETAILS_PANEL = "#hiring-detail-root";

  public static final String APPLICATION_LIST = "li.hiring-applicants__list-item";

  public static final String PAGINATION_PAGE_ITEMS = "li[data-test-pagination-page-btn]";

  public static final String ACTIVE_PAGE_BUTTON = "button[aria-current='true']";
}

