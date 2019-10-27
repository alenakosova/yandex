package Pages;

import Base.Props;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {
    private static InternetExplorerDriver iedriver;
    private static ChromeDriver chromeDriver;

    public static ChromeDriver getChromeDriver() {
        return chromeDriver;
    }

    public static InternetExplorerDriver getIEDriver() {
        return iedriver;
    }

    @Rule
    public ErrorCollector collector = new ErrorCollector();


    public static RemoteWebDriver getWebDriver() {
        if (chromeDriver != null) {
            return chromeDriver;
        } else {
            return iedriver;
        }
    }


    BasePage() {
        if (Boolean.valueOf(Props.get("iedriverIsOn"))) {
            initIEDriver();
            PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(iedriver)), this);
        } else {
            initChromeDriver();
            PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(chromeDriver)), this);

        }

    }

    void pageIsLoad() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void setWebDriverNull() {
        chromeDriver = null;
        iedriver = null;
    }

    private void initIEDriver() {
        if (iedriver == null) {
            File IEDriver = new File(Props.get("iedriver.path"));
            System.setProperty("webdriver.ie.driver", IEDriver.getAbsolutePath());
            iedriver = new InternetExplorerDriver();
            iedriver.manage().window().maximize();
        }
    }

    public void initChromeDriver() {
        if (chromeDriver == null) {
            File chromeDriverFile = new File(Props.get("chromedriver.path"));
            System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
            ChromeOptions options = new ChromeOptions();
            if (Boolean.valueOf(Props.get("headless"))) {
                options.addArguments("--headless", "--intl.accept_languages=ru", "--lang=ru");
                chromeDriver.manage().window().setSize(new Dimension(1920, 1080));

            } else {
                options.addArguments("--intl.accept_languages=ru", "--lang=ru");
            }
            chromeDriver = new ChromeDriver(options);
            chromeDriver.manage().timeouts().pageLoadTimeout(Integer.valueOf(Props.get("webdriver.timeout")), TimeUnit.MILLISECONDS);
            chromeDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
            chromeDriver.manage().window().maximize();
        }
    }

    @Step("Переходим на страницу по ссылке {0}")
    public void goTo(String url) {
        getWebDriver().navigate().to(url);

    }

    public List<WebElement> getWebElements(By by) {
        List<WebElement> elements = getWebDriver().findElements(by);
        if (elements.isEmpty()) {
            return null;
        }
        return elements;
    }

    @Step("Прикрепленный скриншот")
    @Attachment(type = "image/png", value = "Screenshot")
    public static byte[] makeScreenshot() {
        byte[] screenshot = new byte[0];
        if (getWebDriver() != null) {
            screenshot = getWebDriver().getScreenshotAs(OutputType.BYTES);
        }
        return screenshot;
    }


}
