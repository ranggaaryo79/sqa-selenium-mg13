package com.selenium.ujian.mg13;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestShop {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\juaracoding\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }


    @Test(priority = 1)
    public void testUrl() {
        delay(1);
        String url = "https://shop.demoqa.com/";
        driver.get(url);
        System.out.println("get URL " + url);
        driver.manage().window().maximize();
        System.out.println("Maximize Browser");
        String title = driver.getTitle(); //scrapping
        System.out.println("Title Page = " + title);
        driver.findElement(By.className("woocommerce-store-notice__dismiss-link")).click();


        Assert.assertEquals(title, "ToolsQA Demo Site – ToolsQA – Demo E-Commerce Site");
    }

    @Test(priority = 2)
    public void Login() {
        delay(2);
        driver.findElement(By.xpath("//a[normalize-space()='My Account']")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        delay(1);
        driver.findElement(By.id("username")).sendKeys("ranggaaryo");
        driver.findElement(By.id("password")).sendKeys("rangga123");

        driver.findElement(By.xpath("//*[@id=\"customer_login\"]/div[1]/form/p[3]/button")).click();
        js.executeScript("window.scrollBy(0,500)");

        //verify
        String verifyLogin = driver.findElement(By.xpath("//*[@id=\"post-8\"]/div/div/nav/ul/li[6]/a")).getText();
        Assert.assertEquals(verifyLogin, "Logout");
    }

    @Test(priority = 3)
    public void testAddCart() {
        delay(2);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.navigate().to("https://shop.demoqa.com/");
        delay(1);
        js.executeScript("window.scrollBy(0,500)");
        driver.findElement(By.xpath("//*[@id=\"noo-site\"]/div[2]/div[3]/div/div[2]/div/div/div/div[2]/div[2]/div[2]/div/h3/a")).click();
        String titleProduct = driver.findElement(By.xpath("//*[@id=\"product-1491\"]/div[1]/div[2]/h1")).getText();
        String priceProduct = driver.findElement(By.xpath("//*[@id=\"product-1491\"]/div[1]/div[2]/p/span")).getText();
        System.out.println("Product Name : " + titleProduct);
        System.out.println("Product Price : " + priceProduct);

        js.executeScript("window.scrollBy(0,700)");
        delay(1);
        WebElement selectColor = driver.findElement(By.name("attribute_pa_color"));
        Select color = new Select(selectColor);
        color.selectByIndex(1);
        WebElement selectSize = driver.findElement(By.name("attribute_pa_size"));
        Select size = new Select(selectSize);
        size.selectByIndex(2);
        System.out.println("Size : " + size.getFirstSelectedOption().getText());
        System.out.println("Color : " + color.getFirstSelectedOption().getText());

        Assert.assertEquals(titleProduct, "PLAYBOY X MISSGUIDED PLUS SIZE GREY LIPS PRINT FRONT CROPPED T SHIRT");
        Assert.assertEquals(priceProduct, "₹22.00");
        Assert.assertEquals(color.getFirstSelectedOption().getText(), "Grey");
        Assert.assertEquals(size.getFirstSelectedOption().getText(), "42");

        driver.findElement(By.xpath("//*[@id=\"product-1491\"]/div[1]/div[2]/form/div/div[2]/button")).click();
        System.out.println("ADD TO CART");

        String notifyAddCart = driver.findElement(By.xpath("//*[@id=\"noo-site\"]/div[2]/div/div/div[1]/div")).getText();
        Assert.assertTrue(notifyAddCart.contains("“playboy x missguided plus size grey lips print front cropped t shirt” has been added to your cart"));

        driver.findElement(By.xpath("//a[@class='button wc-forward wp-element-button']")).click();
        js.executeScript("window.scrollBy(0,700)");
        delay(2);
        System.out.println("Cart Page");

    }


    @AfterClass
    public void quitBrowser() {
        delay(3);
        driver.quit();
        System.out.println("Quit Browser");
    }


    static void delay(long detik) {
        System.out.println("Delay");
        try {
            Thread.sleep(detik * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
