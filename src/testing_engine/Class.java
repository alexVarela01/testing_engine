package testing_engine;
import java.awt.Desktop;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.openqa.selenium.opera.OperaDriver;

public class Class {
	
	public static WebDriver webdriver;
	public static int timeoutInSeconds;
	public static WebElement element;
	public static String elementXpath;
	public static String elementXpathCostumers;
	public static String logoSrc;
	public static String extentReportImage;
	public static ExtentHtmlReporter htmlReporter;
	
	
	public static void main(String[] args) throws Exception {
		WebDriver driver = null;
		
		ZonedDateTime dateTime = ZonedDateTime.now();
		String executionDateTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        String strPath = "TDReport";
        
        //Create File object from path
        File dir = new File(strPath);
        dir.mkdir();
        executionDateTime = dateTime.format(formatter);
        strPath = strPath + "\\" + executionDateTime + "\\";
        
        //path
		String extentReportFile = strPath + "extentReportFile" + executionDateTime + ".html";
		System.out.println(extentReportFile);
		
		// specify location of the report and defines ihs properties
		htmlReporter = new ExtentHtmlReporter(extentReportFile);
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Functional Testing"); // Name of the report
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		// Create object of extent report and specify the report file path.
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// gets data from Excel, launches browser and execute steps list
		ArrayList<ArrayList<Action>> testCases = ExcelManager.getTestCasesData(Settings.excelDataFilePath);
		
		// runs each testCase
		for (ArrayList<Action> arrayList : testCases) {
			
			//checks if is to run the current testCase
			if(arrayList.get(0).getTestCaseRun())
			{
				ExtentTest extentTest = extent.createTest(arrayList.get(0).getTestCaseCode() + " - " + arrayList.get(0).getTestCasePriority(), arrayList.get(0).getTestCaseName() );
				
				driver = launchBrowser(ExcelManager.getTestData(Settings.excelDataFilePath), extentTest);
				System.out.println("-------" +" TestCase:" + arrayList.get(0).getTestCaseID() +  " -------");
				Action.ExecuteList(arrayList, extentTest, strPath, driver);
				
		        //Close driver
				driver.quit();
			}
		}
		
		//finishes the report
		extent.flush();
		System.out.println("Tests Finished");
		
		String strPathEmail = strPath.substring(0, strPath.length() - 1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime now = LocalDateTime.now();  
		SendEmail.send(ExcelManager.getEmailData(Settings.excelDataFilePath),"","",strPathEmail,"Relatório Testes " + dtf.format(now),"Relatório de testes realizados no dia " + dtf.format(now));
		
		// Opens Final Report
		//File fileRlatorio = new File(extentReportFile);
		//Desktop.getDesktop().open(fileRlatorio);
    }
	
	/**
	 * Launches Browser
	 * @param data
	 * @return
	 */
	public static WebDriver launchBrowser(HashMap<String, String> data, ExtentTest extentTest) {
		WebDriver driverTemp = null;
		
		try {
			System.setProperty(data.get("WebDriverPackage"),data.get("WebDriverPath"));
		
			//Checks which driver was choosen
			switch(data.get("WebDriverType")) {
				case "ChromeDriver":
					driverTemp = new ChromeDriver();
					break;
				case "FirefoxDriver":
					driverTemp = new FirefoxDriver();
					break;
				case "EdgeDriver":
					driverTemp = new EdgeDriver();
					break;
				case "InternetExplorerDriver":
					driverTemp = new InternetExplorerDriver();
					break;
				case "SafariDriver":
					driverTemp = new SafariDriver();
					break;
				case "OperaDriver":
					driverTemp = new OperaDriver();
					break;
				default:
					driverTemp = null;
					System.out.println("Unknown WebDriver: " + data.get("WebDriverType"));
					System.exit(0);
					break;
			}
			
			//So executa se nao for null
			if(extentTest != null) {
				//Prints some info
				extentTest.log(Status.INFO, "Browser Launched");
				extentTest.log(Status.INFO, "Navigated to "+ data.get("BaseUrl"));
			}

	        //Initializes Website
	        driverTemp.get(data.get("BaseUrl"));	//Starts website
	        driverTemp.manage().window().maximize();	//Maximize Window
		}catch(IllegalStateException e) {
			driverTemp = null;
			System.out.println("Unknown Path or Package - Path:" + data.get("WebDriverPath") + " | Package:" + data.get("WebDriverPackage") +  "    --- " + e.getMessage());
			System.exit(0);
		}catch(Exception e) {
			driverTemp = null;
			System.out.println("Generic Exception: " + e.getMessage());
			System.exit(0);
		}

		return driverTemp;
	}
}

