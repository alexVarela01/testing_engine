package testing_engine;

import org.openqa.selenium.WebElement;

public class Settings {
	
	//Excel Columns
	public static String actionColumn = "action";
	public static String xPathColumn = "xPath";
	public static String attributeNameColumn = "attributeName";
	public static String attributeValueColumn = "attributeValue";
	public static String xCoordinateColumn = "xCoordinate";
	public static String yCoordinateColumn = "yCoordinate";
	public static String frameIndexColumn = "frameIndex";
	public static String valueReadColumn = "valueRead";
	public static String orderColumn = "order";
	public static String testCaseIDColumn= "testcaseID";
	public static String testCaseNameColumn= "testCaseName";
	public static String testCaseRun= "testCaseRun";
	public static String testCaseCode= "testCaseCode";
	public static String testCasePriority =  "testCasePriority";


	// Excel Path
	public static String excelDataFilePath = "data.xlsx";
	
	public static boolean isAttribtuePresent(WebElement element, String attribute) {
	    Boolean result = false;
	    try {
	        String value = element.getAttribute(attribute);
	        if (value != null){
	            result = true;
	        }
	    } catch (Exception e) {}

	    return result;
	}

}
