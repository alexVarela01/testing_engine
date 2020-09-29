package testing_engine;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TSDAutomation {
	public static String extentReportImage;

	public static String takeScreenshot(WebDriver driver,String strPath){
	        
    	Date data = new Date();
       
        try {
        	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        	
        	extentReportImage = strPath +"print_" +  data.getTime() + ".jpg";
            FileUtils.copyFile(scrFile, new File(extentReportImage),true);
            
            return extentReportImage;
        	
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
