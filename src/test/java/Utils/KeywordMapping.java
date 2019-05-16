package Utils;

import java.io.IOException;

import org.openqa.selenium.By;

public class KeywordMapping  {

	UIUtils utils;
	public KeywordMapping()
	{
		utils = new UIUtils();
	}
	
	public void operation(String keyword, String locatorType, String locatorValue, String testData)
			throws IOException {

		switch (keyword.toUpperCase()) {
		case "CLICK":
			utils.clickOn(locatorType, locatorValue);
			break;
		case "SETTEXT":
			utils.enterText(locatorType, locatorValue, testData);
			break;
		case "SELECTBYTEXT":
			utils.selectDropDownByText(locatorType, locatorValue, testData);
			break;
		case "OPENAPP":
			utils.openApp();
			break;
		case "WAIT":
			utils.staticWait(testData);
			break;
		case "VALIDATEURL":
			utils.validateURL(testData);
			break;
		case "VALIDATETEXT":
			utils.validateText(locatorType, locatorValue, testData);
			break;	
		}

	}

}
