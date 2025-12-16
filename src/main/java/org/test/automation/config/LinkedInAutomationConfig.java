package org.test.automation.config;

public final class LinkedInAutomationConfig {

  private LinkedInAutomationConfig() {
  }

  // for first login save session
  public static final String STORAGE_STATE_PATH = "linkedin-session.json";

  // LinkedIn Applicants page
  public static final String APPLICANTS_URL = "https://www.linkedin.com/talent/job-posting/XXXXXXXX/applicants/";

  public static final int MAX_CV_PER_RUN = 250;

  public static final int MIN_WAIT = 5;

  public static final int MAX_WAIT = 30;

  public static final String GO_NEXT_BUTTON = "button[aria-label='Next']";

  public static final String DOWNLOAD_BUTTON = "button[aria-label='Download resume']";

  public static final String DETAILS_PANEL = "div.hiring-applicant-details";

  public static final String APPLICATION_LIST = "li.hiring-applicants__list-item";
}

