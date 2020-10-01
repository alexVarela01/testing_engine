package testing_engine_unitTests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testing_engine.Action;
import testing_engine.Class;
import testing_engine.ExcelManager;
import testing_engine.KeyWordsManager;
import testing_engine.SendEmail;
import testing_engine.Settings;
import testing_engine.TSDAutomation;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

public class TestLogic {

	private static KeyWordsManager keywords;
	private static WebDriver driver;
	private static ExtentHtmlReporter htmlReporter;
	private static ExtentTest extentTest;
	private static ExtentReports extent;
	private static int StepN = 1;
	private static String strPath;

	// path
	private static String baseUrl = "file:///C:/Windows/System32/config/systemprofile/AppData/Local/Jenkins/.jenkins/workspace/UmNomeQualquer/testes.html";

	public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new java.lang.Class[] {TestLogic.class});
        testng.addListener(tla);
        testng.run();
    } 
	
	/**
	 * Initializes the browser before the tests start
	 */
	@BeforeClass
	public static void InicializeBrowser() {

		ZonedDateTime dateTime = ZonedDateTime.now();
		String executionDateTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

		strPath = "TDReport_TestNG";

		// Create File object from path
		File dir = new File(strPath);

		dir.mkdir();

		executionDateTime = dateTime.format(formatter);
		strPath = strPath + "\\" + executionDateTime + "\\";
		String extentReportFile = strPath + "extentReportFile" + executionDateTime + ".html";
		System.out.println(extentReportFile);
		
		// specify location of the report
		htmlReporter = new ExtentHtmlReporter(extentReportFile);

		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Functional Testing"); // Name of the report
		htmlReporter.config().setTheme(Theme.STANDARD);

		// Create object of extent report and specify the report file path.
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extentTest = extent.createTest("Testes Unitarios TestNG", "Testes Unitarios TestNG");

		HashMap<String, String> data = ExcelManager.getTestData(Settings.excelDataFilePath);
		data.replace("BaseUrl", baseUrl);
		driver = Class.launchBrowser(data, extentTest);
		driver.manage().window().maximize(); // Maximize Window
	}

	/**
	 * Initializes the browser before the tests start
	 */
	@BeforeMethod
	public void InicializeVariables() {
		keywords = new KeyWordsManager(); // Initialize of the keywords object
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void excelManagerSuccess() {
		ArrayList<ArrayList<Action>> arrayList = ExcelManager.getTestCasesData("data.xlsx"); // Click method
		// Assert.assertEquals(!= null, arrayList); //Verify if the method succeeded
		Assert.assertNotNull(arrayList);
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void excelManagerFailure() {
		ArrayList<ArrayList<Action>> arrayList = ExcelManager.getTestCasesData("asdadasd.xlsx"); // Click method
		// Assert.assertEquals(!= null, arrayList); //Verify if the method succeeded
		Assert.assertNull(arrayList);
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void excelManagerTestDataSuccess() {
		HashMap<String, String> hashmap = ExcelManager.getTestData("data.xlsx"); // Click method
		// Assert.assertEquals(!= null, arrayList); //Verify if the method succeeded
		Assert.assertNotNull(hashmap);
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void excelManagerTestDataFailure() {
		HashMap<String, String> hashmap = ExcelManager.getTestData("adasdsa.xlsx"); // Click method
		// Assert.assertEquals(!= null, arrayList); //Verify if the method succeeded
		Assert.assertNull(hashmap);
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void testClickNull() {
		keywords.Click(driver, null); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void testClickSuccess() {
		keywords.Click(driver, "//*[@id=\"button\"]"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful click method
	 */
	@Test
	public void testClickFailure() {
		keywords.Click(driver, "//*[@id=\"button\"]xxx"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void testWaitForElementNull() {
		keywords.WaitForElement(driver, null,10); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void testWaitForElementSuccess() {
		keywords.WaitForElement(driver, "//*[@id=\"button\"]", 10); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful click method
	 */
	@Test
	public void testWaitForElementFailure() {
		keywords.WaitForElement(driver, "//*[@id=\"button\"]xxx", 10); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a successful navigate method
	 */
	@Test
	public void testNavigateSuccess() {
		keywords.Navigate("https://google.com/", driver); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testNavigateFailure() {
		keywords.Navigate("https://adssadasdsad", driver); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void testSelectNullxPath() {
		keywords.Select(driver, null, "option3"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testSelectSuccess() {
		keywords.Select(driver, "//*[@id=\"select\"]", "option3"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testSelectInvalidXpath() {
		keywords.Select(driver, "//*[@id=\"select\"]zzz", "option3"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testSelectInvalidValue() {
		keywords.Select(driver, "//*[@id=\"select\"]", "option3zzz"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void testCheckNullxPath() {
		keywords.Check(driver, null, "disponivel", "Sim"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckInvalidXpath() {
		keywords.Check(driver, "//*[@id=\"select\"]zzz", "disponivel", "Sim"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testWaitSuccess() {
		keywords.Wait("1"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testWaitLettersInValue() {
		keywords.Wait("This is not the test case you're looking for"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testWaitNegativeValue() {
		keywords.Wait("-4"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckInvalidAttributeName() {
		keywords.Check(driver, "//*[@id=\"button\"]", "TODO: Insert lame joke here", "Sim"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckDisponivelInvalidValue() {
		keywords.Check(driver, "//*[@id=\"button\"]", "Disponivel", "NOT"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckDisponivelSimSuccess() {
		keywords.Check(driver, "//*[@id=\"buttonEnabled\"]", "Disponivel", "Sim"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckDisponivelNaoSuccess() {
		keywords.Check(driver, "//*[@id=\"buttonDisabled\"]", "Disponivel", "Nao"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckDisponivelSimFail() {
		keywords.Check(driver, "//*[@id=\\\"buttonDisabled\\\"]", "Disponivel", "Sim"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckDisponivelNaoFail() {
		keywords.Check(driver, "//*[@id=\\\"buttonEnabled\\\"]", "Disponivel", "Nao"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckVisivelInvalidValue() {
		keywords.Check(driver, "//*[@id=\"button\"]", "Visivel", "NOT"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckVisivelSimSuccess() {
		keywords.Check(driver, "//*[@id=\"buttonVisible\"]", "Visivel", "Sim"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckVisivelNaoSuccess() {
		keywords.Check(driver, "//*[@id=\"buttonInvisible\"]", "Visivel", "Nao"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckVisivelSimFail() {
		keywords.Check(driver, "//*[@id=\\\"buttonInvisible\\\"]", "Visivel", "Sim"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckVisivelNaoFail() {
		keywords.Check(driver, "//*[@id=\\\"buttonVisible\\\"]", "Visivel", "Nao"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckTextoSuccess() {
		keywords.Check(driver, "//*[@id=\"buttonText\"]", "Texto", "Button Text"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckTextoFail() {
		keywords.Check(driver, "//*[@id=\"buttonText\"]", "Texto", "Nothing to see here, move along"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckContemSuccess() {
		keywords.Check(driver, "/html/body/h1", "Contem", "holder"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckContemFail() {
		keywords.Check(driver, "/html/body/h1", "Contem", "Nothing to see here, move along"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckCorSuccess() {
		keywords.Check(driver, "/html/body/h1", "cor", "#000000"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckCorFail() {
		keywords.Check(driver, "/html/body/h1", "cor", "#313a13"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckClassSuccess() {
		keywords.Check(driver, "/html/body/h1", "class", "classOK"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckClassFail() {
		keywords.Check(driver, "/html/body/h1", "class", "#313a13"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckUrlSuccess() {
		keywords.Check(driver, "/html/body/h1", "url", baseUrl); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testCheckUrlFail() {
		keywords.Check(driver, "/html/body/h1", "url", "sadsaddsadsa"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testSelectWindowSuccess() {
		keywords.SelectWindow(driver); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void testPasteValueNullxPath() {
		keywords.PasteValue(driver, null, "disponivel"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testPasteValueSuccess() {
		keywords.PasteValue(driver, "//*[@id=\"inputText\"]", "No Joke here"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testPasteValueInvalidxPath() {
		keywords.PasteValue(driver, "//*[@id=\"inputText\"]xxx", "No Joke here"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testPasteValueEmptyValue() {
		keywords.PasteValue(driver, "//*[@id=\"inputText\"]", ""); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testPasteValueNullValue() {
		keywords.PasteValue(driver, "//*[@id=\"inputText\"]", null); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void testMoveMouseObjectNullxPath() {
		keywords.MoveMouseObject(driver, null); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testMoveMouseObjectSuccess() {
		keywords.MoveMouseObject(driver, "//*[@id=\"button\"]"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testMoveMouseObjectInvalidxPath() {
		keywords.MoveMouseObject(driver, "//*[@id=\"button\"]xxx"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testMoveMouseXYSuccess() {
		keywords.MoveMouseXY(driver, 200, 200); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testMoveMouseXYInvalidX() {
		keywords.MoveMouseXY(driver, -200, 200); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testScrollXYSuccess() {
		keywords.ScrollXY(driver, 200, 200); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testScrollXYInvalidX() {
		keywords.ScrollXY(driver, -200, 200); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testMoveMouseXYInvalidY() {
		keywords.MoveMouseXY(driver, 200, -200); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testMoveMouseXYInvalidXY() {
		keywords.MoveMouseXY(driver, -200, -200); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testClickXYSuccess() {
		keywords.ClickXY(driver, 200, 200); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testClickXYInvalidX() {
		keywords.ClickXY(driver, -200, 200); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testClickXYInvalidY() {
		keywords.ClickXY(driver, 200, -200); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 */
	@Test
	public void testClickXYInvalidXY() {
		keywords.ClickXY(driver, -200, -200); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a successful click method
	 */
	@Test
	public void testTypeNullxPath() {
		keywords.Type(driver, null, "A word"); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testTypeSuccessWith1Word() {
		keywords.Type(driver, "//*[@id=\"inputText\"]", "A word");
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testTypeSuccessWith2Words() {
		keywords.Type(driver, "//*[@id=\"inputText\"]", "A,word");
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testTypeSuccessWithCtrl() {
		keywords.Type(driver, "//*[@id=\"inputText\"]", "ctrl");
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testTypeSuccessWithCtrlAndWord() {
		keywords.Type(driver, "//*[@id=\"inputText\"]", "ctrl,Word");
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testTypeSuccessWithShift() {
		keywords.Type(driver, "//*[@id=\"inputText\"]", "shift");
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testTypeSuccessWithShiftAndWord() {
		keywords.Type(driver, "//*[@id=\"inputText\"]", "shift,Word");
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testTypeSuccessWithAlt() {
		keywords.Type(driver, "//*[@id=\"inputText\"]", "alt");
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testTypeSuccessWithAltAndWord() {
		keywords.Type(driver, "//*[@id=\"inputText\"]", "alt,Word");
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	@Test
	public void testTypeSuccessWithNull() {
		keywords.Type(driver, "//*[@id=\"inputText\"]", null);
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSetSuccess() {
		keywords.Set("//*[@id=\"inputText\"]", "costa come bosta", driver); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSetInvalid() {
		keywords.Set("//*[@id=\\\"inputText\\\"]asdadasd", "costa come bosta", driver); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSetNull() {
		keywords.Set("//*[@id=\\\"inputText\\\"]", null, driver); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSwitchSuccess() {
		keywords.SwitchTo(0, driver); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSwitchInvalid() {
		keywords.SwitchTo(99999, driver); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testWriteDynamicValueSuccess() {
		keywords.WriteDynamicValue("okoko"); // Click method
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testWriteDynamicValueInvalid() {
		keywords.WriteDynamicValue(null); // Click method
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testWriteDynamicLineSuccess() {
		keywords.WriteDynamicLine(new Action(new HashMap<String, String>() {
			{
				put(Settings.xPathColumn, "//*[@id=\"inputText\"]");
			}
		}));
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testWriteDynamicLineInvalid() {
		keywords.WriteDynamicLine(null);
		Assert.assertEquals(false, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testReadDynamicValueSuccess() {
		keywords.ReadDynamicValue();
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Test of a unsuccessful navigate method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testReadDynamicLineSuccess() {
		keywords.ReadDynamicLine();
		Assert.assertEquals(true, keywords.passou); // Verify if the method succeeded
	}

	/**
	 * Finalizes the browser
	 */
	@AfterMethod
	public static void goBack(ITestResult result) {

		try {
			String extentReportImage = TSDAutomation.takeScreenshot(driver, strPath);

			// Verifies if the passou flag is true or false
			// If true sends a positive feedback message
			// If false sends a negative feedback message
			if (result.getStatus() == ITestResult.SUCCESS) {
				if (keywords.result == "") {
					extentTest.log(Status.PASS,
							"Test N " + StepN + ": " + " Test name: " + result.getMethod().getMethodName()
									+ " Result: Test passed",
							MediaEntityBuilder.createScreenCaptureFromPath(extentReportImage).build());
				} else {
					extentTest.log(Status.PASS,
							"Test N " + StepN + ": " + " Test name: " + result.getMethod().getMethodName() + " Result: "
									+ keywords.result,
							MediaEntityBuilder.createScreenCaptureFromPath(extentReportImage).build());
				}

			} else {
				extentTest.log(Status.FAIL,
						"Test N " + StepN + ": " + " Test name: " + result.getMethod().getMethodName() + " Result: "
								+ result.getThrowable(),
						MediaEntityBuilder.createScreenCaptureFromPath(extentReportImage).build());
			}

			StepN++;

			String currentURL = driver.getCurrentUrl().trim().toLowerCase();

			if (!currentURL.equals(baseUrl.trim().toLowerCase())) {
				driver.get(baseUrl); // Starts website
			} else {
				driver.switchTo().defaultContent();
			}

		} catch (Exception e) {
			System.out.println("Failed with error" + e.getMessage());
		}

	}

	/**
	 * Finalizes the browser
	 */
	@AfterClass
	public static void FinalizeBrowser() {
		driver.close(); // Closes browser
		extent.flush();
		String strPathEmail = strPath.substring(0, strPath.length() - 1);
		SendEmail.send("alex.hilas05@gmail.com","varela.varela.varela01@gmail.com","Password.password123",strPathEmail,"Subject","Message");
	}
}