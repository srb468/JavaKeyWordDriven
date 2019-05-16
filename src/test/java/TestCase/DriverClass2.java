package TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Utils.KeywordMapping;
import Utils.UIUtils;

public class DriverClass2 extends UIUtils {
	public static final int TCcount =2;
	WebDriver driver = null;
	Logger logs = Logger.getLogger("devpinoyLogger");
	public List<List> allRows = null;
	KeywordMapping runner = new KeywordMapping();

	@BeforeSuite
	public void readExcelData() throws IOException {

		String filePath = System.getProperty("user.dir") + getProperties().getProperty("ExcelPath");
		System.out.println("FilePath : : : : " + filePath);
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);

		allRows = new ArrayList<List>();
		Workbook wb = new XSSFWorkbook(inputStream);
		Sheet sheet = wb.getSheet("AutomationData");
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			List<String> eachRow = new ArrayList<String>();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				if (!row.getCell(1).getStringCellValue().isEmpty()) {
					if (row.getCell(7).getStringCellValue().equalsIgnoreCase("Y")) {
						eachRow.add(row.getCell(j).getStringCellValue());
					}
				}
			}
			allRows.add(eachRow);
		}
		allRows.removeIf(p -> p.isEmpty());
		System.out.println(allRows);
		// getting total number of test cases

		String caseCount = allRows.get(allRows.size() - 1).get(6).toString().substring(2);
		//TCcount = Integer.parseInt(caseCount);
		//System.out.println("Test Case count : " + TCcount);
	}

	@Test(invocationCount = TCcount)
	public void execute() {
		System.out.println("----------Test Case count : ----------------" + TCcount);

		System.out.println("------------" + allRows);
		for (int y = 1; y <= TCcount; y++) {
			for (int x = 0; x < allRows.size(); x++) {
				String tcNum = "TC" + y;

				if (allRows.get(x).toString().contains(tcNum)) {

					// String TestCaseName = allRows.get(x).get(0).toString();
					String keyWord = allRows.get(x).get(2).toString();
					String locatorType = allRows.get(x).get(3).toString();
					String locatorvalue = allRows.get(x).get(4).toString();
					String testData = allRows.get(x).get(5).toString();
					String tcNo = allRows.get(x).get(6).toString();
					System.out.println(
							keyWord + "==" + locatorType + " == " + locatorvalue + "==" + testData + "==" + tcNo);
					 //runner.operation(keyWord, locatorType, locatorvalue, testData);
				}
			}
		}
	}
	@AfterSuite
	public void closure() {
		System.out.println("Closing suite");
	}

}