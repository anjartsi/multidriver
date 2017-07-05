package multidriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by aourfalian on 7/3/2017.
 */
public class DriverFactory {
    private ArrayList<WebDriver> allDrivers;
    private File inputFile;
    private String url;
    public DriverFactory() {
        if(allDrivers == null) {allDrivers = new ArrayList<WebDriver>();}

        //TODO implement save/load functionality
//        String directory = System.getProperty("user.dir") + "/input.txt";
//        inputFile = new File(directory);
    }

    private void readInputFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        scanner.next();
        url = scanner.next();
        System.out.println(url);

        String username, password;
        scanner.next();
        scanner.next();
        while(scanner.hasNextLine()) {
            username = scanner.next();
            password = scanner.next();
            System.out.println("username = " + username);
            System.out.println("password = " + password);

            login(username, password);
        }
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public WebDriver login(String username, String password) {
        WebDriver driver = createNewDriver();
        driver.get(url);

        WebElement usrName = driver.findElement(By.id("username"));
        WebElement psswd = driver.findElement(By.id("password"));

        usrName.clear();
        usrName.sendKeys(username);

        psswd.clear();
        psswd.sendKeys(password);
        psswd.submit();

        return driver;
    }

    private WebDriver createNewDriver() {
        WebDriver driver;
        // Point to where the chrome driver is
        System.setProperty("webdriver.chrome.driver",  System.getProperty("user.dir") + "/external/chromedriver.exe");

        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs",chromePrefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        options.addArguments("--test-type");

        driver = new ChromeDriver(cap);

        // Maximize the window
        driver.manage().window().maximize();

        allDrivers.add(driver);
        return driver;
    }

    private boolean close(WebDriver driver) {
        driver.quit();
        return true;
    }

    public boolean closeAllDrivers() {
        boolean success = true;
        for(WebDriver d: allDrivers) {
            try {
                d.quit();
            }catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }
}
