package Pages;

import Base.Props;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.io.File;
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


    public static WebDriver getWebDriver() {
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

    public void pageIsLoad() {
        try {
            Thread.sleep(3000);
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

            } else {
                options.addArguments("--intl.accept_languages=ru", "--lang=ru");
            }
            chromeDriver = new ChromeDriver(options);
            chromeDriver.manage().timeouts().pageLoadTimeout(Integer.valueOf(Props.get("webdriver.timeout")), TimeUnit.MILLISECONDS);
            chromeDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
            chromeDriver.manage().window().setSize(new Dimension(1920,1080));
            chromeDriver.manage().window().fullscreen();
        }
    }

    @Step("Переходим на страницу по ссылке {0}")
    public void goTo(String url) {
        getWebDriver().navigate().to(url);

    }


    @Step("Прикрепленный скриншот")
    @Attachment(type = "image/png", value = "Screenshot")
    public static byte[] makeScreenshot() {
        if (iedriver != null) {
            return iedriver.getScreenshotAs(OutputType.BYTES);
        }
        if (chromeDriver != null) {
            return chromeDriver.getScreenshotAs(OutputType.BYTES);
        }
        else{
            Assert.fail("Не инициализаирован web-драйвер");
        return new byte[0];
        }

    }


}
