package org.test.automation.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.test.automation.config.LinkedInAutomationConfig;

public class BrowserManager {

  public static BrowserContext createContext(Playwright playwright) {
    Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                                                       .setHeadless(false)
                                                       .setSlowMo(80)
                                                       .setArgs(List.of("--start-maximized")));

    Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
        .setViewportSize(null)
        .setAcceptDownloads(true);

    Path sessionPath = Paths.get(LinkedInAutomationConfig.STORAGE_STATE_PATH);
    if (sessionPath.toFile().exists()) {
      contextOptions.setStorageStatePath(sessionPath);
    }

    return browser.newContext(contextOptions);
  }

  public static void persistSession(BrowserContext context) {
    context.storageState(
        new BrowserContext.StorageStateOptions()
            .setPath(Paths.get(LinkedInAutomationConfig.STORAGE_STATE_PATH)));
  }
}
