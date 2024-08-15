package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrappers;
    WebDriverWait wait;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */


    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        wrappers = new Wrappers(driver);

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }

    @Test
    public void testCase01() {
        try {

            wrappers.openFlipkart();
            wrappers.searchItems("Washing Machine");
            WebElement populatiry = driver.findElement(By.xpath("//div[text()='Popularity']"));
            populatiry.click();
            List<WebElement> itemList = driver.findElements(By.cssSelector(".cPHDOP.col-12-12"));
            int count = 0;

            for (WebElement e : itemList) {
                try {
                    WebElement rating = e.findElement(By.className("XQDdHH"));
                    float num = 4.0f;
                    float f = Float.parseFloat(rating.getText());
                    if (f <= num) {
                        count++;
                    }
                } catch (Exception ex) {}
            }
            System.out.println(count);
            assert count > 0;
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }


    }

    @Test
    public void testCase02() {
        wrappers.openFlipkart();
        wrappers.searchItems("iPhone");
        List<WebElement> itemList = driver.findElements(By.cssSelector(".cPHDOP.col-12-12"));
        System.out.println(itemList.size());

        int count = 0;

        for (WebElement e : itemList) {
            try {
                WebElement discount = e.findElement(By.className("UkUFwK"));
                String discountText = discount.getText();
                discountText = discountText.replace("% off", "");
                float disc = Float.parseFloat(discountText);
                if (disc > 17.0f) {
                    WebElement itemName = e.findElement(By.className("KzDlHZ"));
                    System.out.println(itemName.getText() + " :-Discount% = " + discountText + "%");
                    count++;
                }


            } catch (Exception ignored) {

            }
        }


    }

    @Test
    public void testCase03() {

        wrappers.openFlipkart();
        wrappers.searchItems("Coffee Mug");
        WebElement fourStarCheckBox = driver.findElement(By.xpath("(//div[@class='XqNaEv'])[1]"));
        fourStarCheckBox.click();

        wrappers.sleep(5000);

        List<WebElement> itemList = driver.findElements(By.className("slAVV4"));
        System.out.println(itemList.size());
        TreeMap<Integer, WebElement> reviewMap = new TreeMap<>(Collections.reverseOrder());

        for (WebElement e : itemList){
            try {
                String review = e.findElement(By.className("Wphh3N")).getText();
                int reviewInt = wrappers.getReview(review);

                if (!reviewMap.containsKey(reviewInt)){
                    reviewMap.put(reviewInt,e);
                }

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }


        int count = 0;
        for (Map.Entry<Integer, WebElement> entry : reviewMap.entrySet()) {
            if (count >= 5) break;
            WebElement item = entry.getValue();
            String title = item.findElement(By.className("wjcEIp")).getText();
            String imageUrl = item.findElement(By.className("DByuf4")).getAttribute("src");
            System.out.println("Title: " + title);
            System.out.println("Image URL: " + imageUrl);
            count++;
        }






    }
}



