package Base;

import Pages.BasePage;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by out-kosova-aa on 05.10.2019.
 */
public class Bean {
//    public Bean() {
////        initIEDriver();
//        initChromeDriver();
////        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(iedriver)), this);
//        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(chromeDriver)), this);
//
//    }

//    private static InternetExplorerDriver iedriver;
//    private static ChromeDriver chromeDriver;
//
//    public static ChromeDriver getChromeDriver() {
//        return chromeDriver;
//    }
//    public static InternetExplorerDriver getIEDriver() {
//        return iedriver;
//    }

//    @Rule
//    public TestWatcher watcher = new TestWatcher() {
//        @Override
//        protected void failed(Throwable e, Description description) {
//            BasePage.makeScreenshot();
//            new BasePage().closeBrowser();
//        }
//
//        @Override
//        protected void succeeded(Description description) {
//            new BasePage().closeBrowser();
//        }
//    };

//
//    public void initIEDriver() {
//        if (iedriver == null) {
//            File IEDriver = new File(Props.get("webdriver.path"));
//            System.setProperty("webdriver.ie.driver", IEDriver.getAbsolutePath());
//            iedriver = new InternetExplorerDriver();
//            iedriver.manage().window().maximize();
//        }
//    }
//
//    public void initChromeDriver() {
//        if (chromeDriver == null) {
//            File chromeDriverFile = new File(Props.get("chromedriver.path"));
//            System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
//            ChromeOptions options = new ChromeOptions();
//
//            options.addArguments("--intl.accept_languages=ru", "--lang=ru");
//
//            chromeDriver = new ChromeDriver(options);
//            chromeDriver.manage().window().maximize();
//        }
//    }

//    @Step("Переходим на страницу по ссылке {0}")
//    public void goTo(String url) {
//        if (chromeDriver != null) {
//            chromeDriver.navigate().to(url);
//        } else {
//            iedriver.navigate().to(url);
//        }
//    }
//
//
//    @Step("Прикрепленный скриншот")
//    @Attachment(type = "image/png", value = "Screenshot")
//    public static byte[] makeScreenshot() {
//
//        return getChromeDriver().getScreenshotAs(OutputType.BYTES);
//    }



//    @After
//    public void closeBrowser() {
//        if (iedriver != null) {
//            iedriver.quit();
//            iedriver = null;
//        }
//        if (chromeDriver != null) {
//            chromeDriver.quit();
//            chromeDriver = null;
//        }
//    }


}
