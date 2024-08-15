package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    ChromeDriver driver;


    public Wrappers(ChromeDriver driver){
        this.driver = driver;
    }


    public void searchItems(String searchKey){
        WebElement searchInp=driver.findElement(By.className("Pke_EE"));
        searchInp.sendKeys(searchKey);
        searchInp.sendKeys(Keys.ENTER);
    }

    public void openFlipkart(){
        driver.get("https://www.flipkart.com/");
    }
    public void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }


    public int getReview(String review) throws Exception {
        StringBuilder rev = new StringBuilder();
        for (int i = 0; i < review.length(); i++){
            if (Character.isDigit(review.charAt(i)))
                rev.append(review.charAt(i));
        }

        if (rev.length() > 0){
            return Integer.parseInt(rev.toString());
        }else
            throw new Exception("Invalid Review");
    }





}
