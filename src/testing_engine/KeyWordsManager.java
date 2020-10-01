package testing_engine;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class KeyWordsManager {

	//Flags
	public boolean passou = true;	//Flag if the test as passed
	public String result = "";		//Result of the test
	
	/**
	 * Performers a click
	 * @param driver driver
	 * @param xPath object xpath
	 */
	public void Click(WebDriver driverTemp, String xPathTemp) {

		//Tries to click the element with the xPath 
		//If it fails it catches the Exception and changes the flags 
		try {
			Actions actions = new Actions(driverTemp);
			actions.moveToElement(driverTemp.findElement(By.xpath(xPathTemp))).click().build().perform();
			
			result = "Click successful on element: "+ xPathTemp;							//Successful message
		}catch(InvalidSelectorException e) {
			result = "Click unsuccessful: Element " + xPathTemp + " not found";							
			passou = false;											//Changes the flag~
		}catch(ElementClickInterceptedException e) {
			result = "Click unsuccessful: Element " + xPathTemp + " not accessible" + e.getMessage();							
			passou = false;											//Changes the flag
		}catch(NoSuchElementException e) {
			result = "Click unsuccessful: Element " + xPathTemp + " not found";							
			passou = false;											//Changes the flag
		}catch(IllegalArgumentException e) {
			result = "Click unsuccessful: No xPath";							
			passou = false;											//Changes the flag
		}catch(Exception e) {
			result = "Click unsuccessful: Unexpected error";							
			passou = false;											//Changes the flag
		}

	}
	
	/**
	 * Writes a log in the final report
	 * @param extentTest
	 * @param statusTemp
	 * @param text
	 */
	public void WriteLog(ExtentTest extentTest, String statusTemp, String text) {
	       
        Status status = Status.INFO;
       
        //checks the status
        switch(statusTemp) {
            case "info":
                status = Status.INFO;
                break;
            case "warning":
                status = Status.WARNING;
                break;
            case "error":
                status = Status.ERROR;
                break;
            case "pass":
                status = Status.PASS;
                break;
            case "fail":
                status = Status.FAIL;
                break;
        }
        
        //writes the log
        extentTest.log(status, text);
    }
	
	/**
	 * Scrolls to a location
	 * @param driverTemp
	 * @param xTemp
	 * @param yTemp
	 */
	public void ScrollXY(WebDriver driverTemp, int xTemp, int yTemp) {
		
		try {
			
			//checks is x and y are valid
			if(xTemp >= 0 && yTemp >= 0) {
				JavascriptExecutor jse = (JavascriptExecutor)driverTemp;
				jse.executeScript("window.scrollBy("+ String.valueOf(xTemp)+ ", " + String.valueOf(xTemp) + ")");
				result = "Scroll sucessfull";
			}else {
				result = "Invalid position.";							
				passou = false;											//Changes the flag
			}
		}catch(Exception e) {
			result = "Scroll unsuccessful: Unexpected error";							
			passou = false;											//Changes the flag
		}
		
	}
	
	/**
	 * Selects an item in the select input
	 * @param driver driver
	 * @param xPath object xpath
	 * @param optionText option text
	 */
	public void Select(WebDriver driverTemp, String xPathTemp, String optionTextTemp) {
		
		//Tries to select the element with the xPath 
		//If it fails it catches the Exception and changes the flags 
		try {
			Select select = new Select(driverTemp.findElement(By.xpath(xPathTemp))); 	//Selector
			select.selectByVisibleText(optionTextTemp);									//Select
			result = "Select successful on element: "+ xPathTemp + " with text: " + optionTextTemp;							//Successful message
		}catch(InvalidSelectorException e) {
			result = "Select unsuccessful: element "+ xPathTemp + " not found";							//Successful message
			passou = false;																//Changes the flag
		}catch(NoSuchElementException e) {
			result = "Select unsuccessful: on element: "+ xPathTemp + " option "+ optionTextTemp + " not found";							//Successful message
			passou = false;																//Changes the flag
		}catch(IllegalArgumentException e) {
			result = "Select unsuccessful: No xPath";							
			passou = false;											//Changes the flag
		}catch(Exception e) {
			result = "Select unsuccessful: Unexpected error";							
			passou = false;																//Changes the flag
		}
	}
	
    /**
     * Waits the seconds on the value
     * @param attributeValueTemp
     * @throws InterruptedException
     * @throws NumberFormatException
     */
    public void Wait(String attributeValueTemp) {
        //Tries to wait if it fails give feedback
        try {
        	
        	//waits x seconds before continuing
            String stringCutted = attributeValueTemp.substring(0, attributeValueTemp.indexOf('.'));
            stringCutted.trim();
            Thread.sleep(Long.parseLong(stringCutted.trim())*1000);
            result = "Wait of "+ attributeValueTemp + " successful";							//Successful message
        }catch(NumberFormatException e) {
            result = "Wait unsuccessful: Invalid time " + attributeValueTemp;							//Successful message
            passou = false;                        //Changes the flag
        }catch(StringIndexOutOfBoundsException e) {
            try {
                String stringCutted = attributeValueTemp;
                stringCutted.trim();
                Thread.sleep(Long.parseLong(stringCutted.trim())*1000);                    //Changes the flag
                result = "Wait of "+ attributeValueTemp + " successful";							//Successful message
            }catch(Exception f) {
            	result = "Wait unsuccessful: Unexpected error";							
                passou = false;                        //Changes the flag
            }
        }catch(Exception e) {
        	result = "Wait unsuccessful: Unexpected error";							
            passou = false;                        //Changes the flag
        }
       
    }
	
    /**
     * Waits for the element to load
     * @param driverTemp
     * @param xPath
     */
    public void WaitForElement(WebDriver driverTemp, String xPathTemp) {
    	try {
    		//waits for the element to load
    		WebDriverWait wait = new WebDriverWait(driverTemp, 10);
        	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathTemp)));
    		result = "WaitForElement with xPath " + xPathTemp+ " successfull.";		
    	}catch(TimeoutException e) {
    		passou = false;
    		result = "WaitForElement with xPath " + xPathTemp+ " unsuccessfull: Timeout after 10 seconds.";		
    	}catch(Exception e) {
    		passou = false;
    		result = "WaitForElement unsuccessfull: Generic Exception.";		
    	}
    }
    
    /**
     * Closes Tab
     * @param driverTemp
     * @param attributeNameTemp
     * @param attributeValueTemp
     */
    public void CloseTab(WebDriver driverTemp, String attributeValueTemp) {
    	try {
        	ArrayList<String> tabs2 = new ArrayList<String> (driverTemp.getWindowHandles());

        	//checks if value is null and closes tab
    		if(attributeValueTemp == null) {
    			driverTemp.close();
        	}else {
            	driverTemp.switchTo().window(tabs2.get(Integer.valueOf(attributeValueTemp)));
        		driverTemp.close();
        	}
    		
    		//switches to current main tab
        	driverTemp.switchTo().window(tabs2.get(0));
    		//driverTemp.switchTo().defaultContent();
    		result = "CloseTab sucessfull";		
    	}catch(IndexOutOfBoundsException e) {
    		result = "CloseTab unsuccessful: Invalid index.";		
			passou = false;						//Changes the flag
    	}catch(Exception e) {
			result = "CloseTab unsuccessful: Unexpected error.";		
			passou = false;						//Changes the flag
		}
    }
    
	/**
     * Changes Tab
     * @param driverTemp
     * @param attributeValueTemp
    */
    public void ChangeTab(WebDriver driverTemp, String attributeValueTemp) {
    	
    	try {
    		
    		//changes to the selected tab
        	ArrayList<String> tabs2 = new ArrayList<String> (driverTemp.getWindowHandles());
        	driverTemp.switchTo().window(tabs2.get(Integer.valueOf(attributeValueTemp)));
        	result = "ChangeTab successfull to tab index " + attributeValueTemp;
    	}catch(IndexOutOfBoundsException e) {
    		result = "ChangeTab unsuccessful: Invalid index.";		
			passou = false;						//Changes the flag
    	}catch(Exception e) {
			result = "ChangeTab unsuccessful: Unexpected error.";		
			passou = false;						//Changes the flag
		}
    }
    
	/**
	 * Selects an item in the select input
	 * @param driver driver
	 * @param xPath object xpath
	 * @param attributeName attribute name
	 * @param attributeValue attribute value
	 */
	public void Check(WebDriver driverTemp, String xPathTemp, String attributeNameTemp, String attributeValueTemp) {
		
		try {
			//Variables
			String objAttValue = "";											//Value of the att
			String fieldValue = "";												//Value of the field

			//Decides witch action to do
			switch (attributeNameTemp.toLowerCase()) {
			
	            //Verifies if the element is available
		        case "disponivel":
					WebElement element1 = driverTemp.findElement(By.xpath(xPathTemp));	//Element

		        	if(!attributeValueTemp.equals("Sim") &&  !attributeValueTemp.equals("Nao")) {
		        		result = "Check unsuccessful: On element: " + xPathTemp + ". Checking " + attributeNameTemp + ". Invalid value: " + attributeValueTemp;							
		    			passou = false;						//Changes the flag
		    			return;
		            }
		           
		        	//If the element is available changes the tag to "Sim"
		        	//else changes the tag to "Nao"
		            if  (element1.isEnabled())
		                objAttValue = "Sim";	//Changes the tag
		            else
		                objAttValue = "Nao";	//Changes the flag
		           
		            //Verifies is not the result was the wanted then changes the tag
		            if (!objAttValue.equals(attributeValueTemp)) {
		                passou = false;		//Changes the flag
		                result = "Check unsuccessful: On element  " + xPathTemp + ". Checking " + attributeNameTemp + ". Result doesn't match with value " + attributeValueTemp;							
		                return;
		            }
		            result = result + " " + attributeNameTemp + " = " + objAttValue + "|";	//Changes the flag result
		            
		            result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " with value: "+ attributeValueTemp + " successful";							
		           
		            break;   
		            
	            //Verifies if the element is visible
		        case "visivel":
					WebElement element2 = driverTemp.findElement(By.xpath(xPathTemp));	//Element

		        	if(!attributeValueTemp.equals("Sim") &&  !attributeValueTemp.equals("Nao")) {
		        		result = "Check unsuccessful: On element: " + xPathTemp + ". Checking " + attributeNameTemp + ". Invalid value: " + attributeValueTemp;							
		    			passou = false;						//Changes the flag
		    			return;
		            }
		           
		        	//If the element is visible changes the tag to "Sim"
		        	//else changes the tag to "Nao"
		            if  (element2.isDisplayed())
		                objAttValue = "Sim";	//Changes the tag
		            else
		                objAttValue = "Nao";	//Changes the tag
		           
		            //Verifies is not the result was the wanted then changes the tag
		            if (!objAttValue.equals(attributeValueTemp)) {
		                passou = false;		//Changes the flag
		                result = "Check unsuccessful: On element " + xPathTemp + ". Checking " + attributeNameTemp + ". Result doesn't match with value " + attributeValueTemp;
                        return;
		            }
		            result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " with value: "+ attributeValueTemp + " successful";							
		           
		            break;
		            
		            //Checks if the element text is equal to the value received
		        case "texto":
					WebElement element3 = driverTemp.findElement(By.xpath(xPathTemp));	//Element

		        	fieldValue = element3.getText();	//Passes the text to a variable
		               
	                //Verifies if the text is the same
	                if (!fieldValue.toLowerCase().equals(attributeValueTemp.toLowerCase())){
	                	
                		if(Settings.isAttribtuePresent(element3, "value")) {
                			if(!element3.getAttribute("value").toLowerCase().equals(attributeValueTemp.toLowerCase())){
    	                		passou = false;		//Changes the tag
    	                		result = "Check unsuccessful: On element " + xPathTemp + ". Checking " + attributeNameTemp + ". Result doesn't match with value " + attributeValueTemp;							
    	                	}else {
    	                		passou = true;   		//Changes the tag
     	                    	result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " with value: "+ attributeValueTemp + " successful";							
    	                	}
                		}
                		else {
                			passou = false;		//Changes the tag
	                		result = "Check unsuccessful: On element " + xPathTemp + ". Checking " + attributeNameTemp + ". Result doesn't match with value " + attributeValueTemp;							
                		}
	                }
	                else {
	                    passou = true;   		//Changes the tag
	                    result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " with value: "+ attributeValueTemp + " successful";							
	                }
		        	break;
		        	
		        case "class":
                    WebElement element5 = driverTemp.findElement(By.xpath(xPathTemp));    //Element

                    fieldValue = element5.getText();    //Passes the text to a variable
                       
                    //Verifies if the text is the same
                    if (!fieldValue.toLowerCase().equals(attributeValueTemp.toLowerCase())){
                        
                        if(Settings.isAttribtuePresent(element5, "class")) {
                            if(!element5.getAttribute("class").toLowerCase().contains(attributeValueTemp.toLowerCase())){
                                passou = false;        //Changes the tag
                                result = "Check unsuccessful: On element " + xPathTemp + ". Checking " + attributeNameTemp + ". Result doesn't match with class " + attributeValueTemp;                            
                            }else {
                                passou = true;           //Changes the tag
                                 result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " with class: "+ attributeValueTemp + " successful";                            
                            }
                        }
                        else {
                            passou = false;        //Changes the tag
                            result = "Check unsuccessful: On element " + xPathTemp + ". Checking " + attributeNameTemp + ". Result doesn't match with class " + attributeValueTemp;                            
                        }
                    }
                    else {
                        passou = true;           //Changes the tag
                        result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " with class: "+ attributeValueTemp + " successful";                            
                    }
                    break;
                    
                    
                //Checks if the element text is equal to the value received
               case "contem":
                   WebElement element6 = driverTemp.findElement(By.xpath(xPathTemp));    //Element


                   fieldValue = element6.getText();    //Passes the text to a variable
                      
                   //Verifies if the text is the same
                   if (!fieldValue.toLowerCase().contains(attributeValueTemp.toLowerCase())){
                       
                       if(Settings.isAttribtuePresent(element6, "value")) {
                           if(!element6.getAttribute("value").toLowerCase().contains(attributeValueTemp.toLowerCase())){
                               passou = false;        //Changes the tag
                               result = "Check unsuccessful: On element " + xPathTemp + ". Checking " + attributeNameTemp + ". Result doesn't contains the value " + attributeValueTemp;                            
                           }else {
                               passou = true;           //Changes the tag
                                result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " contains value: "+ attributeValueTemp + " successful";                            
                           }
                       }
                       else {
                           passou = false;        //Changes the tag
                           result = "Check unsuccessful: On element " + xPathTemp + ". Checking " + attributeNameTemp + ". Result doesn't contains the value " + attributeValueTemp;                            
                       }
                   }
                   else {
                       passou = true;           //Changes the tag
                       result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " contains value: "+ attributeValueTemp + " successful";                            
                   }
                   break;
		        	
	        	case "url":
		        	
	        		if(attributeValueTemp.toLowerCase().equals(driverTemp.getCurrentUrl().toLowerCase())) {
	        			passou = true;
	        			result = "Check URL " + attributeValueTemp + " sucessfull. The input url matches with current url.";
	        		}else {
	        			passou = false;
	        			result = "Check URL " + attributeValueTemp + " unsucessfull. The input url doesn't matches with current url.";
	        		}
	        		
		        	break;
		        	
        		case "cor":
		        	WebElement elementTemp = driverTemp.findElement(By.xpath(xPathTemp));
		        	
		        	 try {
		        		//  RGB to HEX   
			        	 String color_hex[];  
			        	 color_hex = elementTemp.getCssValue("color").trim().replace("rgba(", "").split(",");       
			        	 String actual_hex = String.format("#%02x%02x%02x", Integer.parseInt(color_hex[0].trim()), Integer.parseInt(color_hex[1].trim()), Integer.parseInt(color_hex[2].trim()));
			        	
		        		if(actual_hex.equals(attributeValueTemp)) {
		                    result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " color: "+ attributeValueTemp + " successful";                            
		        		}else {
		        			passou = false;
		                    result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " color: "+ attributeValueTemp + " unsuccessful";                            
		        		}
					} catch (Exception e) {
						passou = false;
	                    result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " color: "+ attributeValueTemp + " unsuccessful. Unexpected ERROR.";                            
	        		}
	        		
		        	break;
		        	
	        	case "existe":

                    if(!attributeValueTemp.equals("Sim") &&  !attributeValueTemp.equals("Nao")) {
                        result = "Check unsuccessful: On element: " + xPathTemp + ". Checking " + attributeNameTemp + ". Invalid value: " + attributeValueTemp;                            
                        passou = false;                        //Changes the flag
                        return;
                    }
                    
                   
                    //If the element is visible changes the tag to "Sim"
                    //else changes the tag to "Nao"
                    if  (driverTemp.findElements(By.xpath(xPathTemp)).size() > 0)
                        objAttValue = "Sim";    //Changes the tag
                    else
                        objAttValue = "Nao";    //Changes the tag
                   
                    //Verifies is not the result was the wanted then changes the tag
                    if (!objAttValue.equals(attributeValueTemp)) {
                        passou = false;        //Changes the flag
                        result = "Check unsuccessful: On element " + xPathTemp + ". Checking " + attributeNameTemp + ". Result doesn't match with value " + attributeValueTemp;
                        return;
                    }
                    result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " with value: "+ attributeValueTemp + " successful";                            
                   
                    break;
		           
	            //If not anything above throws exception
		        default:
		        	result = "AttributeName Invalid";		
		        	result = "Check on element " + xPathTemp + ". Checking " + attributeNameTemp + " with value: "+ attributeValueTemp + " unsuccessful: Invalid: AttributeName: "+ attributeNameTemp;							
					
		        	passou = false;							//Changes the flag
					return;
	                
			}
		}catch(InvalidSelectorException e) {
			result = "Check unsuccessful: element "+ xPathTemp + " not found";							//Successful message
			passou = false;						//Changes the flag
		}catch(ElementClickInterceptedException e) {
			result = "Check unsuccessful: element "+ xPathTemp + " not accessible";							//Successful message
			passou = false;											//Changes the flag
		}catch(IllegalArgumentException e) {
			result = "Check unsuccessful: No xPath";							
			passou = false;											//Changes the flag
		}catch(Exception e) {
			result = "Check unsuccessful: Unexpected error";							
			passou = false;						//Changes the flag
		}
		
		
	}

	/**
	 * Selects windows when inside frame
	 * @param driver Webdriver
	 */
	public void SelectWindow(WebDriver driverTemp) {
		//Tries to switch back to the default content
		//If it false gives out exception
		try {
			driverTemp.switchTo().defaultContent();	//Changes to defaultContent
			result = "SelectWindow successful.";			//Changes the flag
		}catch(Exception e) {
			result = "SelectWindow unsuccessful: Unexpected error.";		
			passou = false;						//Changes the flag
		}
		
	}
	
	/**
	 * Pastes value from clipboard
	 * @param driver webdriver
	 * @param xPath object xpath
	 * @param attributeValue attribute value
	 * @throws Exception
	 */
	public void PasteValue(WebDriver driverTemp, String xPathTemp, String attributeValueTemp) {
		
		//Tries to put the value received in the clipboard then pastes it in the element 
		try {
			
			if(attributeValueTemp == null) attributeValueTemp = "";
			
			// Sets clipboard data with attribute value
	    	StringSelection strClipboard = new StringSelection(attributeValueTemp);				
	    	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strClipboard, null);	
	    	String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
	    	
	    	// pastes the value
	    	driverTemp.findElement(By.xpath(xPathTemp)).sendKeys(driverTemp.findElement(By.xpath(xPathTemp)).getText() + data);
	    	
	    	result = "PasteValue "+attributeValueTemp+" successful on element:" +xPathTemp+".";	//Successful message
		}catch(InvalidSelectorException e) {
			result = "PasteValue unsuccessful: Element " +xPathTemp + " not found.";		
			passou = false;						//Changes the flag
		}catch(IllegalArgumentException e) {
			result = "PasteValue unsuccessful: No xPath.";							
			passou = false;											//Changes the flag
		}catch(Exception e) {
			result = "PasteValue unsuccessful: Unexpected error.";		
			passou = false;						//Changes the flag
		}
		
	}
	
	/**
	 * Move mouse to object
	 * @param driver webdriver
	 * @param xPath object xpath
	 */
	public void MoveMouseObject(WebDriver driverTemp,String xPathTemp) {
		// moves the mouse to object
		try {
			// moves the mouse to object
			Actions action = new Actions(driverTemp);
			WebElement myElement = driverTemp.findElement(By.xpath(xPathTemp));
			action.moveToElement(myElement).build().perform();
			result = "MoveMouseObject successful to element: " +xPathTemp+".";
		}catch(InvalidSelectorException e) {
			result = "MoveMouseObject unsuccessful: Element " +xPathTemp + " not found.";		
			passou = false;						//Changes the flag
		}catch(IllegalArgumentException e) {
			result = "MoveMouseObject unsuccessful: No xPath.";							
			passou = false;											//Changes the flag
		}catch(Exception e) {
			result = "MoveMouseObject unsuccessful: Unexpected error.";							
			passou = false;						//Changes the flag
		}
	}
	
	/**
	 * Move mouse to XY
	 * @param driver webDriver
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void MoveMouseXY(WebDriver driverTemp, int xTemp, int  yTemp) {
		//Tries to move mouse to a certain position in the screen
		try {
			if(xTemp < 0 || yTemp < 0) throw new MoveTargetOutOfBoundsException("");
			new Actions(driverTemp).moveByOffset(xTemp, yTemp).build().perform();	//Moves the mouse
			result = "MoveMouseXY successful on x:" + xTemp + " y:"+yTemp+".";	//Successful message
		}catch(MoveTargetOutOfBoundsException e) {
			result = "MoveMouseXY unsuccessful: Invalid position x:" + xTemp + " y:"+yTemp+".";		
			passou = false;						//Changes the flag
		}catch(Exception e) {
			result = "MoveMouseXY unsuccessful: Unexpected error.";		
			passou = false;						//Changes the flag
		}
	}

	/**
	 * Clicks in XY
	 * @param driver webDriver
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void ClickXY(WebDriver driverTemp, int xTemp, int  yTemp) {
		//Tries to click the screen in a certain position
		try {
			if(xTemp < 0 || yTemp < 0) throw new MoveTargetOutOfBoundsException("");
			new Actions(driverTemp).moveByOffset(xTemp, yTemp).click().build().perform();	//Clicks a certain point in the screen
			result = "ClickXY successful on x:" + xTemp + " y:"+yTemp+".";	//Successful message
		}catch(MoveTargetOutOfBoundsException e) {
			result = "ClickXY unsuccessful: Invalid position x:" + xTemp + " y:"+yTemp+".";		
			passou = false;						//Changes the flag
		}catch(Exception e) {
			result = "ClickXY unsuccessful: Unexpected error.";		
			passou = false;						//Changes the flag
		}
	}
	
	/**
	 * Types something on the keyboard
	 * If special characters come along it will detected them and execute those commands
	 * @param driver webDriver
	 * @param arrayKeys keys list
	 */
	public void Type(WebDriver driverTemp, String xPathTemp, String attributeValueTemp) {
		
		//Tries to type the commands
		try {
			
			//If it doesn't have a "," it has 2 words if not only has 1
			if(!attributeValueTemp.contains(",")) {
				
				//Sees what key to type first then types
				switch(attributeValueTemp) {
					case Constants.constControlKey:
						driverTemp.findElement(By.xpath(xPathTemp)).sendKeys(Keys.CONTROL);
						break;
						
					case Constants.constAltKey:
						driverTemp.findElement(By.xpath(xPathTemp)).sendKeys(Keys.ALT);
						break;
						
					case Constants.constShiftKey:
						driverTemp.findElement(By.xpath(xPathTemp)).sendKeys(Keys.SHIFT);
						break;
						
					default:
						driverTemp.findElement(By.xpath(xPathTemp)).sendKeys(attributeValueTemp);
						break;
				}
			}else {
				String[] parts = attributeValueTemp.split(",");
				String key1 = parts[0]; 
				String key2 = parts[1]; 

				//Sees what key to type first then types
				switch(key1) {
					case Constants.constControlKey:
						driverTemp.findElement(By.xpath(xPathTemp)).sendKeys(Keys.CONTROL, key2);
						break;
						
					case Constants.constAltKey:
						driverTemp.findElement(By.xpath(xPathTemp)).sendKeys(Keys.ALT, key2);
						break;
						
					case Constants.constShiftKey:
						driverTemp.findElement(By.xpath(xPathTemp)).sendKeys(Keys.SHIFT, key2);
						break;
						
					default:
						driverTemp.findElement(By.xpath(xPathTemp)).sendKeys(key1, key2);
						break;
				}
			}
			
			result = "Typed successful on element: "+xPathTemp;		//Successful message
				
		}catch (InvalidSelectorException e) {
			result = "Typed unsuccessful element: " +xPathTemp + " not found.";		
			passou = false;						//Changes the flag
		}catch (NullPointerException e) {
			result = "Typed unsuccessful: word not found";			
			passou = false;						//Changes the flag
		}catch(IllegalArgumentException e) {
			result = "Typed unsuccessful: xPath: " +xPathTemp + " not found.";							
			passou = false;											//Changes the flag
		}catch (Exception e) {
			result = "Typed unsuccessful: Unexpected error.";		
			passou = false;						//Changes the flag
		}
	}
	
    public void CountChilds(WebDriver driverTemp, String xPathTemp, int expectedElements) {
        List<WebElement> forms = driverTemp.findElements(By.xpath(xPathTemp));
        int count = forms.size();
       
        if(count != expectedElements) {
            passou = false;
            result = "CountChilds unsuccessfull: Count("+count+") dont match with expected:" +expectedElements;
        }else {
            result = "CountChilds successfull. Count(" + expectedElements + ") matches with expected:" +expectedElements;
        }
    }

	
    /*
	 * Opens a browser on the received website and maximizes the window
	 * @param url
	 * @throws InterruptedException
	 */
	public void Navigate(String url, WebDriver driverTemp) {
		try {
			//Initializes Website
	        driverTemp.get(url);	//Starts website
	        driverTemp.manage().window().maximize();	//Maximize Window
	        result = "Navigate to "+url+" successful.";			//Successful message
		}catch(InvalidArgumentException e){
			result = "Navigate unsuccessful: URL '"+url+"' not found.";					
			passou = false;								//Changes the flag
		}catch(Exception e){
			result = "Navigate unsuccessful: Unexpected error.";				
			passou = false;								//Changes the flag
		}
	}
	
	/**
	 * Writes something in a input
	 * @param xPath
	 * @throws InterruptedException
	 */
	public void Set(String xPathTemp, String valueTemp, WebDriver driverTemp) {
		
		try {
			WebElement element = driverTemp.findElement(By.xpath(xPathTemp));	//Gets element by received xpath
			element.clear();
			element.sendKeys(valueTemp);
			result = "Setted value successful on element: "+xPathTemp+" with text: " + valueTemp;		//Successful message
		} catch (InvalidSelectorException e) {
			result = "Setted value unsuccessful: Element " +xPathTemp + " not found.";				
			passou = false;								//Changes the flag
		}catch(IllegalArgumentException e) {
			result = "Setted value unsuccessful: No xPath defined.";							
			passou = false;											//Changes the flag
		}catch (NoSuchElementException e) {
			result = "Setted value unsuccessful: No such element with xpath: " + xPathTemp;				
			passou = false;								//Changes the flag
		}catch (Exception e) {
			result = "Setted value unsuccessful: Unexpected error." + e.getMessage();				
			passou = false;								//Changes the flag
		}
	}
	
	/**
	 * Changes focus to the frame of the index received
	 * @param url
	 * @throws InterruptedException
	 */
	public void SwitchTo(int frameIndexTemp, WebDriver driverTemp) {
		try {		
			//Changes to the Iframe with the index of frameIndexTemp
			driverTemp.switchTo().frame(frameIndexTemp);
			result = "Switched to frame index "+frameIndexTemp+" sucessfully.";	//Successful message
		} catch (InvalidArgumentException e) {
			result = "Switched to Frame unsuccessful: Index " + frameIndexTemp + " not found.";			
			passou = false;								//Changes the flag
		}
		catch (NoSuchElementException e) {
			result = "Switched to Frame unsuccessful: Element not found.";				
			passou = false;								//Changes the flag
		}catch (Exception e) {
			result = "Switched to Frame unsuccessful: Unexpected error.";				
			passou = false;								//Changes the flag
		}
	}
	
	/**
     * Serializes a received hashmap to a file
     * @param url
     * @throws IOException 
     * @throws InterruptedException
     */
    public void WriteDynamicValue(String dynamicValue) {
        
        if(dynamicValue != null) {
            try {
                FileOutputStream fos = new FileOutputStream("dynamicValue.bin");    //Creates file
                ObjectOutputStream oos = new ObjectOutputStream(fos);    //Creates stream of output
                oos.writeObject(dynamicValue);    //Writes Object in file
                oos.close();    //Closes file
                result = "WriteDynamicValue successfully written. Data: " + dynamicValue;    //Successful message
            } catch (FileNotFoundException e) {
                result = "WriteDynamicValue unsuccessfully written: File not found";                
                passou = false;                            //Changes the flag
            }catch (IOException e) {
                result = "WriteDynamicValue unsuccessfully written: Insuficient permissions.";    
                passou = false;                            //Changes the flag
            } catch (Exception e) {
                result = "WriteDynamicValue unsuccessfully written: Unexpected error.";            
                passou = false;                            //Changes the flag
            }
        }else {
            result = "Null Value.";                                    
            passou = false;        
        }
    }
    
    
    /**
     * Reads a dynamic value then changes it and saves 
     * @param driverTemp
     * @param xPathTemp
     * @param attributeNameTemp
     * @param attributeValueTemp
     */
    public void InteractDynamicValue(WebDriver driverTemp, String attributeNameTemp, String attributeValueTemp) {
    	
    	try {
            FileInputStream fis = new FileInputStream("dynamicValue.bin");    //Finds file
            ObjectInputStream ois = new ObjectInputStream(fis);        //Creates file Input Stream
            String dynamicValue = "";    //Creates object to receive items from file
            
            //Mandatory try catch for the reading of the file
            try {
                dynamicValue = (String) ois.readObject();    //Reads the file and puts the content in the variable
                
                ois.close();                // Closes file
                
                switch(attributeNameTemp) {
	                
                	case "add":
                		int finalvalue = Integer.valueOf(dynamicValue) + Integer.valueOf(attributeValueTemp);
                		
                		FileOutputStream fos = new FileOutputStream("dynamicValue.bin");    //Creates file
                        ObjectOutputStream oos = new ObjectOutputStream(fos);    //Creates stream of output
                        oos.writeObject(finalvalue);    //Writes Object in file
                        oos.close();    //Closes file
	                	
                        result = "Added " + attributeValueTemp + " to value in file sucessfully new data in file: " + finalvalue;    //Successful message
	                	
	                	break;
	                	
                	case "equal":
                		
                		//if equal good result else bad result
                		if(attributeValueTemp.equals(dynamicValue)) {
                			result = "Value "+ attributeValueTemp + " equal to the value on file";    //Successful message
                		}else {
                			result = "Value "+ attributeValueTemp + " not equal to the value on file";    //Successful message
                			passou = false;
                		}
	                	break;
                }
                
                
            } catch (ClassNotFoundException e) {
                result = "ReadDynamicValue unsuccessful read: Class not found.";             
                passou = false;                            //Changes the flag    
            } catch (FileNotFoundException e) {
                result = "ReadDynamicValue unsuccessful read: File not found.";                
                passou = false;                            //Changes the flag
            }catch (IOException e) {
                result = "ReadDynamicValue unsuccessful read: Insuficient permissions.";    
                passou = false;                            //Changes the flag
            } catch (Exception e) {
                result = "ReadDynamicValue unsuccessful read: Unexpected error.";            
                passou = false;                            //Changes the flag
            }
            
            

        }catch(Exception e) {
            result = "Generic Exception.";            
            passou = false;                            //Changes the flag
        }
    }
	
    /**
     * Serializes a rececived hashmap to a file
     * @param url
     */
    public String ReadDynamicValue()  {
        try {
            FileInputStream fis = new FileInputStream("dynamicValue.bin");    //Finds file
            ObjectInputStream ois = new ObjectInputStream(fis);        //Creates file Input Stream
            String dynamicValue = "";    //Creates object to receive items from file
            
            //Mandatory try catch for the reading of the file
            try {
                dynamicValue = (String) ois.readObject();    //Reads the file and puts the content in the variable
                result = "ReadDynamicValue successfully read. Data: " + dynamicValue;    //Successful message
            } catch (ClassNotFoundException e) {
                result = "ReadDynamicValue unsuccessful read: Class not found.";             
                passou = false;                            //Changes the flag    
            } catch (FileNotFoundException e) {
                result = "ReadDynamicValue unsuccessful read: File not found.";                
                passou = false;                            //Changes the flag
            }catch (IOException e) {
                result = "ReadDynamicValue unsuccessful read: Insuficient permissions.";    
                passou = false;                            //Changes the flag
            } catch (Exception e) {
                result = "ReadDynamicValue unsuccessful read: Unexpected error.";            
                passou = false;                            //Changes the flag
            }
            
            ois.close();                // Closes file
            return dynamicValue;        //Returns values

        }catch(Exception e) {
            result = "Generic Exception.";            
            passou = false;                            //Changes the flag
            return null; 
        }
    }
	
	/**
     * Serializes a received hashmap to a file
     * @param url
     * @throws IOException 
     * @throws InterruptedException
     */
    public void WriteDynamicLine(Action actionTemp) {
        
        if(actionTemp != null) {
            // save the object to file
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                fos = new FileOutputStream("dynamicLine.bin");    //Open file stream
                out = new ObjectOutputStream(fos);                //Open Object input stream
                out.writeObject(actionTemp);                    //Writes object in file

                out.close();                                    //Closes file
                
                result = "WriteDynamicLine successfully written. Data: " + actionTemp;    //Successful message
            } catch (FileNotFoundException e) {
                result = "WriteDynamicLine unsuccessfully written: File not found";                
                passou = false;                            //Changes the flag
            }catch (IOException e) {
                result = "WriteDynamicLine unsuccessfully written: Insuficient permissions.";    
                passou = false;                            //Changes the flag
            } catch (Exception e) {
                result = "WriteDynamicLine unsuccessfully written: Unexpected error.";            
                passou = false;                            //Changes the flag
            }
        }else {
            result = "WriteDynamicLine unsuccessfully written: Null line.";            
            passou = false;                            //Changes the flag
        }
    }
	
    /**
     * Serializes a received hashmap to a file
     * @param url
     */
    public Action ReadDynamicLine() {
        
        try {
            FileInputStream fis = new FileInputStream("dynamicLine.bin");    //Finds file
            ObjectInputStream ois = new ObjectInputStream(fis);        //Creates file Input Stream
            Action dynamicLine = new Action("");    //Creates object to receive items from file
            
            //Mandatory try catch for the reading of the file
            try {
                dynamicLine = (Action) ois.readObject();    //Reads the file and puts the content in the variable
                
                result = "ReadDynamicLine successful read. Data: " + dynamicLine;    //Successful message
                passou = true;
            } catch (ClassNotFoundException e) {
                result = "ReadDynamicLine unsuccessful read: Class not found.";    
                passou = false;                    //Changes the flag
            } catch (FileNotFoundException e) {
                result = "ReadDynamicLine unsuccessful read: File not found.";        
                passou = false;                    //Changes the flag
            }catch (IOException e) {
                result = "ReadDynamicLine unsuccessful read: Insuficient permissions.";    
                passou = false;                            //Changes the flag
            } catch (Exception e) {
                result = "ReadDynamicLine unsuccessful read: Unexpected error.";            
                passou = false;                            //Changes the flag
            }
            
            ois.close();            // Closes file
            return dynamicLine;        //Returns values
        }catch(Exception e) {
            result = "Generic Exception.";            
            passou = false;                            //Changes the flag
            return null;        //Returns values
        }
    }
    
    
    /**
     * Makes a login with Hilario's credentials on everis knowler
     * @param driverTemp
     */
    public void LoginKnowler(WebDriver driverTemp) {
    	Navigate("https://knowler.everis.com/", driverTemp);
    	Set("//*[@id=\"userNameInput\"]", "alexandre.lopes.hilario.st@everis.com", driverTemp);
    	Set("//*[@id=\"passwordInput\"]", "sY5C4Y4U1m", driverTemp);
    	Click(driverTemp, "//*[@id=\"submitButton\"]");
    	WaitForElement(driverTemp, "//*[@id=\"differentVerificationOption\"]");
    	Click(driverTemp, "//*[@id=\"differentVerificationOption\"]");
    	WaitForElement(driverTemp, "//*[@id=\"verificationOption2\"]");
    	Click(driverTemp, "//*[@id=\"verificationOption2\"]");
    	
    	Boolean isNoNoQuestion = false;
    	
    	do {
    		isNoNoQuestion = false;
    		
    		switch(driverTemp.findElement(By.xpath("//*[@id=\"question1\"]")).getText()) {
	    	case "What is your favorite pet's name?":
	    		Set("//*[@id=\"answer1Input\"]", "Becas", driverTemp);
	    		break;
	    		
	    	case "What is your favorite meal?":
	    		Set("//*[@id=\"answer1Input\"]", "Francesinha", driverTemp);
	    		break;
	    		
	    	case "What was the name of your school?":
	    		Set("//*[@id=\"answer1Input\"]", "ESFD", driverTemp);
	    		break;
	    		
	    	case "What is your favorite sports team?":
	    		isNoNoQuestion = true;
	    		break;
	    	}
	    	
	    	switch(driverTemp.findElement(By.xpath("//*[@id=\"question2\"]")).getText()) {
	    	case "What is your favorite pet's name?":
	    		Set("//*[@id=\"answer2Input\"]", "Becas", driverTemp);
	    		break;
	    		
	    	case "What is your favorite meal?":
	    		Set("//*[@id=\"answer2Input\"]", "Francesinha", driverTemp);
	    		break;
	    		
	    	case "What was the name of your school?":
	    		Set("//*[@id=\"answer2Input\"]", "ESFD", driverTemp);
	    		break;
	    		
	    	case "What is your favorite sports team?":
	    		isNoNoQuestion = true;
	    		break;
	    	}
	    	
	    	
	    	if(isNoNoQuestion) {
	    		driverTemp.navigate().refresh();
	    	}
    	}while(isNoNoQuestion);
    	
    	Click(driverTemp, "//*[@id=\"authenticateButton\"]");
    	
		 Check(driverTemp, null, "url", "https://knowler.everis.com/onboarding");
	     
	     if(passou) {
	         Click(driverTemp, "/html/body/main/skmo-app-root/div/div/div/skmo-onboarding-noknowler/div/div/div[2]/div/div/div[1]/div[3]/div[1]/label");
	         WebElement element = driverTemp.findElement(By.id("my-id"));
	         Actions actions = new Actions(driverTemp);
	         actions.moveToElement(element);
	         actions.perform();
	         Click(driverTemp, "/html/body/main/skmo-app-root/div/div/div/skmo-onboarding-noknowler/div/div/div[3]/div[3]/button");
	         Click(driverTemp, "/html/body/main/skmo-app-root/div/div/div/skmo-onboarding-noknowler/div/div/div[2]/div/div/div[1]/div[3]/div[2]/label");
	         Click(driverTemp, "/html/body/main/skmo-app-root/div/div/div/skmo-onboarding-noknowler/evc-modal/div/evc-draggable/div/div/div/button/span");
	         Click(driverTemp, "/html/body/main/skmo-app-root/div/div/div/skmo-onboarding-noknowler/div/div/div[2]/div/div/div[3]/button");
	     }
    	
    	
    	result = "Login Sucessfull";
    	passou = true;
    	
    }
    
    
    
    
  
  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
