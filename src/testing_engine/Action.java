package testing_engine;

import java.util.*;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

@SuppressWarnings("serial")
public class Action implements java.io.Serializable{
	
	///////
	//Attributes of the class Action
	///////
	private String action;
	private String xPath;
	private String attributeValue;
	private String attributeName;
	private String testCaseName;
	private String testCaseCode;
	private String testCasePriority;
	private int frameIndex;
	private String valueRead;
	private int xCoordinate;
	private int yCoordinate;
	private int order;
	private int testCaseID;
	private Boolean testCaseRun;
	
	/**
	 * Constructor of the class Action
	 * 		Receives a Hashmap and sorts the values with the keys
	 * @param list
	 */
	public Action(HashMap<String, String> list) {
		this.action = list.get(Settings.actionColumn);
		this.xPath = list.get(Settings.xPathColumn);
		this.attributeValue = list.get(Settings.attributeValueColumn);
		this.attributeName = list.get(Settings.attributeNameColumn);
		this.valueRead = list.get(Settings.valueReadColumn);
		this.testCaseName = list.get(Settings.testCaseNameColumn);
		this.testCaseCode = list.get(Settings.testCaseCode);
		this.testCasePriority= list.get(Settings.testCasePriority);

		if(list.get(Settings.testCaseRun) != null && list.get(Settings.testCaseRun).equals("Sim")){
			testCaseRun = true;
		}else {
			testCaseRun = false;
		}
		
		//Tries to get the integer of the Hashmap if its null puts -1 instead
		//If another error occurs it warns the console
		try {
			this.order = Integer.valueOf(list.get(Settings.orderColumn));
		}catch(NullPointerException e) {
			this.order = -1;
		}catch(NumberFormatException e) {
			this.order = -1;
		}catch(Exception e) {
			System.out.println("Error in the constructor" + e.getMessage());
		}
		
		//Tries to get the integer of the Hashmap if its null puts -1 instead
		//If another error occurs it warns the console
		try {
			this.testCaseID = Integer.valueOf(list.get(Settings.testCaseIDColumn));
		}catch(NullPointerException e) {
			this.testCaseID = -1;
		}catch(NumberFormatException e) {
			this.testCaseID = -1;
		}catch(Exception e) {
			System.out.println("Error in the constructor" + e.getMessage());
		}
		
		//Tries to get the integer of the Hashmap if its null puts -1 instead
		//If another error occurs it warns the console
		try {
			this.xCoordinate = Integer.valueOf(list.get(Settings.xCoordinateColumn));
		}catch(NumberFormatException e) {
			this.xCoordinate = -1;
		}catch(NullPointerException e) {
			this.xCoordinate = -1;
		}catch(Exception e) {
			System.out.println("Error in the constructor" + e.getMessage());
		}
		
		//Tries to get the integer of the Hashmap if its null puts -1 instead
		//If another error occurs it warns the console
		try {
			this.yCoordinate = Integer.valueOf(list.get(Settings.yCoordinateColumn));
		}catch(NumberFormatException e) {
			this.yCoordinate = -1;
		}catch(NullPointerException e) {
			this.yCoordinate = -1;
		}catch(Exception e) {
			System.out.println("Error in the constructor" + e.getMessage());
		}
		
		//Tries to get the integer of the Hashmap if its null puts -1 instead
		//If another error occurs it warns the console
		try {
			this.frameIndex = Integer.valueOf(list.get(Settings.frameIndexColumn));
		}catch(NullPointerException e) {
			this.frameIndex = -1;
		}catch(NumberFormatException e) {
			this.frameIndex = -1;
		}catch(Exception e) {
			System.out.println("Error in the constructor" + e.getMessage());
		}
		
	}
	
	/**
	 * Constructor of the class Action
	 * 		Receives a Hashmap and sorts the values with the keys
	 * @param list
	 */
	public Action(String Action) {
		action = Action;
	}

	
	/**
	 * Executes the actions determinate by the received ArrayList 
	 * @param actionList
	 * @throws Exception 
	 */
	public static void ExecuteList (ArrayList<Action> actionList, ExtentTest extentTest, String strPath, WebDriver driver) throws Exception {
		
		try{
		
			int stepN = 0;	//The Step the operation is on to communicate on the report
			
			//Goes to all the Hashmaps on the ArrayList and detects the action then executes it
			for(Action actionObject : actionList) {
				
				stepN++;	//Increments the step
				
				KeyWordsManager keywords = new KeyWordsManager();	//A new keywords manager to execute the commands and receive the results
				
				//Selects the action to execute
				switch(actionObject.getAction()) {
				
					//KeyWord Navigate
					case Constants.Navigate:
						keywords.Navigate(actionObject.getAttributeValue(), driver);
						break;
						
					//KeyWord Click
		            case Constants.Click:
		            	keywords.Click(driver, actionObject.getxPath());
		            	break;
		            	
	            	//KeyWord Set
		            case Constants.Set:
		            	keywords.Set(actionObject.getxPath(), actionObject.getAttributeValue(), driver);
		            	break;
		            	
	            	//KeyWord Select
		            case Constants.Select:
		            	keywords.Select(driver, actionObject.getxPath(), actionObject.getAttributeValue());
		            	break;
		            	
	            	//KeyWord Check
		            case Constants.Check:
		            	keywords.Check(driver, actionObject.getxPath(), actionObject.getAttributeName(), actionObject.getAttributeValue());
		            	break;
		            	
	            	//KeyWord SwitchTO
		            case Constants.SwitchTo:
		            	keywords.SwitchTo(actionObject.getFrameIndex(), driver);
		            	break;
		            	
	            	//KeyWord SelectWindow
		            case Constants.SelectWindow:
		            	keywords.SelectWindow(driver);
	        			break;
	        			
	    			//KeyWord Type
		            case Constants.Type:
		            	keywords.Type(driver,actionObject.getxPath(), actionObject.getAttributeValue());
		            	break;
		            	
	            	//KeyWord SaveDynamicValue
		            case Constants.SaveDynamicValue:
		            	keywords.WriteDynamicValue(actionObject.getAttributeValue());
		            	break;
		            	
	            	//KeyWord ReadDynamicValue
		            case Constants.ReadDynamicValue:
		            	keywords.ReadDynamicValue();
		            	break;
		            	
	            	//KeyWord PasteValue
		            case Constants.PasteValue:
		            	keywords.PasteValue(driver, actionObject.getxPath(), actionObject.getAttributeValue());
		            	break;
		            	
	            	//KeyWord ClickXY
		            case Constants.ClickXY:
	            		// Converts to int and performs a click
	            		keywords.ClickXY(driver,  actionObject.getxCoordinate(), actionObject.getyCoordinate());
		            	break;
		            	
	            	//KeyWord MoveMouseObject
		            case Constants.MoveMouseObject:
		            	keywords.MoveMouseObject(driver, actionObject.getxPath());
		            	break;
		            	
	            	//KeyWord MoveMouseXY
		            case Constants.MoveMouseXY:
	            		// Converts to int and performs a click
	            		keywords.MoveMouseXY(driver, actionObject.getxCoordinate(), actionObject.getyCoordinate());
		            	break;
		            	
	            	//KeyWord Wait
		            case Constants.Wait:
		            	keywords.Wait(actionObject.attributeValue);
		            	break;
		            	
	            	//KeyWord WaitForElement
		            case Constants.WaitForElement:
		            	keywords.WaitForElement(driver, actionObject.getxPath());
		            	break;
		            	
		            	//KeyWord ChangeTab
		            case Constants.ChangeTab:
		            	keywords.ChangeTab(driver, actionObject.getAttributeValue());
		            	break;
		            	
		            	//KeyWord CloseTab
		            case Constants.CloseTab:
		            	keywords.CloseTab(driver, actionObject.getAttributeValue());
		            	break;
		            	
		            	//KeyWord Scrollxy
		            case Constants.Scrollxy:
		            	keywords.ScrollXY(driver, actionObject.xCoordinate, actionObject.yCoordinate);
		            	break;
		            	
		            	 //KeyWord CountChilds
                    case Constants.CountChilds:
                        keywords.CountChilds(driver, actionObject.getxPath(), Integer.valueOf(actionObject.getAttributeValue()));
                        break;
                       
                        //KeyWord WriteLog
                    case Constants.WriteLog:
                        keywords.WriteLog(extentTest, actionObject.getAttributeName(), actionObject.getAttributeValue());
                        break;
                        
                        //KeyWord InteractDynamicValue
                    case Constants.InteractDynamicValue:
                        keywords.InteractDynamicValue(driver, actionObject.getAttributeName(), actionObject.getAttributeValue());
                        break;
                       
                    default:
                        keywords.result = "Invalid Action";        //Unsuccessful message
                        keywords.passou = false;                        //Changes the flag
                        break;
                }
               
				//checks is the action is to write a log
                if(!actionObject.getAction().equals(Constants.WriteLog)) {
                    //Verifies if the passou flag is true or false
                    //If true sends a positive feedback message
                    //If false sends a negative feedback message
                    if(keywords.passou) {
                        extentTest.log(Status.PASS,"Step "+ stepN + ": " + keywords.result);
                    }else {
                        extentTest.log(Status.FAIL,"Step "+ stepN + ": " +keywords.result);
                    }
                }
				
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//GETTERS AND SETTERS
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	public String getxPath() {
		return xPath;
	}



	public void setxPath(String xPath) {
		this.xPath = xPath;
	}



	public String getAttributeValue() {
		return attributeValue;
	}



	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}



	public String getAttributeName() {
		return attributeName;
	}



	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}



	public int getFrameIndex() {
		return frameIndex;
	}



	public void setFrameIndex(int frameIndex) {
		this.frameIndex = frameIndex;
	}



	public String getValueRead() {
		return valueRead;
	}



	public void setValueRead(String valueRead) {
		this.valueRead = valueRead;
	}



	public int getxCoordinate() {
		return xCoordinate;
	}



	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}



	public int getyCoordinate() {
		return yCoordinate;
	}



	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	public int getOrder() {
		return order;
	}



	public void setOrder(int order) {
		this.order = order;
	}



	public int getTestCaseID() {
		return testCaseID;
	}



	public void setTestCaseID(int testCaseID) {
		this.testCaseID = testCaseID;
	}
	
	
	public String getTestCaseName() {
		return testCaseName;
	}



	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	
	public String getTestCaseCode() {
		return testCaseCode;
	}



	public void setTestCaseCode(String testCaseCode) {
		this.testCaseCode = testCaseCode;
	}
	
	public String getTestCasePriority() {
		return testCasePriority;
	}



	public void setTestCaseCodePriority(String testCasePriority) {
		this.testCasePriority = testCasePriority;
	}
	
	
	public Boolean getTestCaseRun() {
		return testCaseRun;
	}



	public void setTestCaseRun(Boolean testCaseRun) {
		this.testCaseRun = testCaseRun;
	}

}
