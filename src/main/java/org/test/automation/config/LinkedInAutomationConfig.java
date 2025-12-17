package org.test.automation.config;

public final class LinkedInAutomationConfig {

  private LinkedInAutomationConfig() {
  }

  // Session
  public static final String STORAGE_STATE_PATH = "linkedin-session.json";

  // URLs
  public static final String APPLICANTS_URL = "https://www.linkedin.com/talent/job-posting/XXXXXXXX/applicants/";

  // Limits
  public static final int MAX_CV_PER_RUN = 250;

  // Waits (seconds)
  public static final int MIN_WAIT_SECONDS = 5;

  public static final int MAX_WAIT_SECONDS = 30;

  public static final int DETAIL_PANEL_TIMEOUT_MS = 5_000;

  public static final int PAGE_SWITCH_WAIT_SECONDS = 10;

  public static final int LOGIN_WAIT_SECONDS = 15_000;

  // Selectors
  public static final String NEXT_PAGE_BUTTON = "button[aria-label='Next']";

  public static final String DOWNLOAD_BUTTON = "button[aria-label='Download resume']";

  public static final String DETAILS_PANEL = "div.hiring-applicant-details";

  public static final String APPLICATION_LIST = "li.hiring-applicants__list-item";
}

