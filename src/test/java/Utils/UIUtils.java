package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UIUtils {

	public WebDriver driver;
	public WebDriverWait wait;
	Logger logs;
	private Properties properties;

	public UIUtils() {
		setProperties(new Properties());
		try {
			//
			// getProperties().load(UIUtils.class.getClassLoader().getResourceAsStream(System.getProperty("user.dir")
			// + "\\sources\\files\\OR.properties"));
			properties = new Properties();
			FileInputStream fIn = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\java\\properties\\OR.properties");
			properties.load(fIn);
			Logger logs = Logger.getLogger("devpinoyLogger");
			System.out.println("Properties loaded");
		} catch (IOException e) {
			System.out.println("Properties not loaded");
			e.printStackTrace();
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void openApp() throws IOException {
		// UIUtils utl = new UIUtils();
		String bname = getProperties().getProperty("Browser").trim();
		System.out.println("BrName::: " + bname);
		if (bname.equalsIgnoreCase("firefox") || bname.equalsIgnoreCase("mozilla")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (bname.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}

		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
		driver.manage().window().maximize();
		driver.get(getProperties().getProperty("URL"));
	}

	public void validateURL(String expectedURL) {
		if (driver.getCurrentUrl().equalsIgnoreCase(expectedURL)) {
			System.out.println("Correct URL opened");

		} else {
			System.out.println("Incorrect URL/Page is opened");
		}

	}

	public void validateText(String locatortype, String locatorValue, String expectedText) {
		WebElement element = driver.findElement(getWebElement(locatortype, locatorValue));
		if (element.isDisplayed()) {
			if (element.getText().equalsIgnoreCase(expectedText)) {
				System.out.println("Text Matched");
			}
		} else {
			System.out.println("Text mismatch");
		}

	}

	public By getWebElement(String locatortype, String locatorValue) {

		try {
			switch (locatortype.toUpperCase()) {
			case "XPATH":
				return By.xpath(locatorValue);
			case "CSS":
				return By.cssSelector(locatorValue);
			case "TAGNAME":
				return By.tagName(locatorValue);
			case "PARTIALTEXT":
				return By.partialLinkText(locatorValue);
			case "LINKTEXT":
				return By.linkText(locatorValue);
			case "ID":
				return By.id(locatorValue);
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public WebElement objElement(By locatorTypes) {
		return driver.findElement(locatorTypes);

	}

	public String getValue(String key) throws IOException {
		/*
		 * properties = new Properties(); FileInputStream fIn = new
		 * FileInputStream(System.getProperty("user.dir") +
		 * "\\sources\\files\\OR.properties"); properties.load(fIn);
		 */
		return (getProperties().getProperty(key));

	}

	public void enterText(String locatortype, String locatorValue, String text) {
		WebElement element = driver.findElement(getWebElement(locatortype, locatorValue));
		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(text);
			System.out.println(text + "entered for" + locatortype);
		} else {
			System.out.println("Webement" + locatortype + "is not displayed");
		}
	}

	public void clickOn(String locatortype, String locatorValue) {
		WebElement element = driver.findElement(getWebElement(locatortype, locatorValue));
		if (element.isDisplayed()) {
			element.click();
			System.out.println("Clicked on : " + locatortype);
		} else {
			System.out.println("Webement" + locatortype + "is not displayed");
		}
	}

	public void selectDropDownByText(String locatortype, String locatorValue, String textToSelect) {
		WebElement element = driver.findElement(getWebElement(locatortype, locatorValue));
		Select select = new Select(element);
		select.selectByVisibleText(textToSelect);
		System.out.println("Selected :" + locatortype + " from dropdown");
	}

	public void staticWait(String testData) {
		try {
			long time = Long.parseLong(testData);
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeBrowser() {
		wait = new WebDriverWait(driver, 2);
		driver.close();
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
