package Base;

import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static Pages.BasePage.*;

/**
 * Created by out-kosova-aa on 05.10.2019.
 */
public class BaseTest {
    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            makeScreenshot();
            closeBrowser();
        }

        @Override
        protected void succeeded(Description description) {
            closeBrowser();
        }
    };


    @After
    public void closeBrowser() {
        if (getWebDriver() != null) {
            setWebDriverNull();
        }
    }
}