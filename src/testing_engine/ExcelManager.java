package testing_engine;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings("serial")
public class ExcelManager {
	
	/**
	 * Gets testCase Action List
	 * @param path file path
	 * @return 
	 */
	public static ArrayList<ArrayList<Action>> getTestCasesData(String path) {
		
		//Sorts by testCaseID
		ArrayList<Action> actionListTestCases = new ArrayList<Action>();
		Comparator<Action> compareById = (Action o1, Action o2) -> String.valueOf(o1.getTestCaseID()).compareTo(String.valueOf(o2.getTestCaseID()));

		try {
			//gets action data and sorts it by caseId
			actionListTestCases = ExcelManager.getActionData(path);
			actionListTestCases.sort(compareById);
			return getCasesList(actionListTestCases);
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return null;
		}catch (IOException e) {
			System.out.println("IOException.");
			return null;
		}catch (Exception e) {
			System.out.println("Generic Exception.");
			return null;
		}
	}
	
	/**
	 * Groups list and Sorts it
	 * @param actionListTestCases action list
	 * @return actionlistList
	 */
	public static ArrayList<ArrayList<Action>> getCasesList(ArrayList<Action> actionListTestCases){
		ArrayList<ArrayList<Action>> list = new ArrayList<ArrayList<Action>>();
		
		// Groups all lists by textCaseID
		Map<Object, List<Action>> studlistGrouped = actionListTestCases.stream().collect(Collectors.groupingBy(w -> w.getTestCaseID()));

		// Sorts list by action order and returns
		for(Entry<Object, List<Action>> entry: studlistGrouped.entrySet()) {
			ArrayList<Action> arrlistofOptions = new ArrayList<Action>(entry.getValue());
			Comparator<Action> compareById = (Action o1, Action o2) -> Integer.valueOf(o1.getOrder()).compareTo(Integer.valueOf(o2.getOrder()));
			arrlistofOptions.sort(compareById);
			list.add(arrlistofOptions);
	    }
		
		if(list.size() == 0) list = null;
		
		return list;
	}
	
	/**
	 * Gets all test data
	 * @param path excel file path
	 * @return test data
	 */
 	public static HashMap<String, String> getTestData(String path) {

		// test data hash
		HashMap<String,String> hash = new HashMap<String,String>();
		
		try  
		{  
			File file = new File(path);   						//creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   	//obtaining bytes from the file  
			
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(1);     			//creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    			//iterating over excel file  
			itr.next();
			
			// while there's a next cell
			while (itr.hasNext()){  
				
				//gets row
				ArrayList<HashMap<String,String>> listTemp = getCellsData(itr, sheet);
				hash = new HashMap<String, String>() {{ 
					for(int i = 0; i < listTemp.size();i++) {
						HashMap<String,String> tempHash = listTemp.get(i);
						HashMap.Entry<String,String> entry = tempHash.entrySet().iterator().next();
						put(entry.getKey(), entry.getValue());
					}
				}};

				//closes file
				wb.close();
				fis.close();
			}  
		}  
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}catch(Exception e)  {  
			System.out.println("Generic Exception.");
		} 
		
		if(hash.size() == 0) hash = null;
		return hash;	//returns hash
	}
 	
 	/**
	 * Gets all actions data
	 * @param path excel file path
	 * @return test data
	 */
	public static ArrayList<HashMap<String,String>> getEmailData(String path) throws IOException {
		
		//actions list
		ArrayList<HashMap<String,String>> actionList = new ArrayList<HashMap<String,String>>();

		try  
		{  
			File file = new File(path);   						//creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   	//obtaining bytes from the file  
			
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(2);     			//creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    			//iterating over excel file  
			itr.next();
			
			// while there's a next cell
			while (itr.hasNext()){  
				
				//gets row
				ArrayList<HashMap<String,String>> listTemp = getCellsData(itr, sheet);
				actionList.add(new HashMap<String, String>() {{ 
					for(int i = 0; i < listTemp.size();i++) {
						HashMap<String,String> tempHash = listTemp.get(i);
						HashMap.Entry<String,String> entry = tempHash.entrySet().iterator().next();
						put(entry.getKey(), entry.getValue());
					}
				}});
				
				// closes file
				wb.close();
				fis.close();
		 	}  
			
			try {
				String line0 = Files.readAllLines(Paths.get("email.txt")).get(0); 
			    
		       System.out.println(line0);
		       actionList.add(new HashMap<String, String>(){{
		    	   put("email", line0);
		       }});
			    
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		    
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch(IOException e)  {  
			System.out.println("IOException.");
		} catch(Exception e)  {  
			System.out.println("Generic Exception. " + e.getMessage()); 
		} 
		
		for (HashMap<String, String> hashMap : actionList) {
			System.out.println(hashMap);
		}
		
		return actionList;	//returns action list
	}

	/**
	 * Gets all actions data
	 * @param path excel file path
	 * @return test data
	 */
	public static ArrayList<Action> getActionData(String path) throws IOException {
		
		//actions list
		ArrayList<Action> actionList = new ArrayList<Action>();

		try  
		{  
			File file = new File(path);   						//creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   	//obtaining bytes from the file  
			
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     			//creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    			//iterating over excel file  
			itr.next();
			
			// while there's a next cell
			while (itr.hasNext()){  
				
				//gets row
				ArrayList<HashMap<String,String>> listTemp = getCellsData(itr, sheet);
				actionList.add(new Action(new HashMap<String, String>() {{ 
					for(int i = 0; i < listTemp.size();i++) {
						HashMap<String,String> tempHash = listTemp.get(i);
						HashMap.Entry<String,String> entry = tempHash.entrySet().iterator().next();
						put(entry.getKey(), entry.getValue());
					}
				}}));
				
				// closes file
				wb.close();
				fis.close();
		 	}  
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch(IOException e)  {  
			System.out.println("IOException.");
		} catch(Exception e)  {  
			System.out.println("Generic Exception."); 
		} 
		
		return actionList;	//returns action list
	}
	
	/**
	 * gets value from all cells in the row
	 * @param itr iterator
	 * @param sheet sheet
	 * @return returns row
	 */
	public static ArrayList<HashMap<String,String>> getCellsData(Iterator<Row> itr,XSSFSheet sheet) {
		ArrayList<HashMap<String,String>> listTemp = new ArrayList<HashMap<String,String>>();

		// gets the new row
		Row row = itr.next();  
		Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column 
		
		// while there's another cell
		while (cellIterator.hasNext()){
			
			// checks if null
			Cell cell = cellIterator.next();  
			if(!cell.equals(null)) {
				
				// gets col address, key name, value
				String colAddress = cell.getAddress().toString().substring(0,1);
				String key = sheet.getRow(0).getCell(CellReference.convertColStringToIndex(colAddress)).getStringCellValue();
				String valueTemp = "";
				
				//checks cell type
				switch (cell.getCellType()){  
					case Cell.CELL_TYPE_STRING: 
						valueTemp = cell.getStringCellValue();
						break;  
					case Cell.CELL_TYPE_NUMERIC:
						NumberFormat nf = NumberFormat.getNumberInstance();
						nf.setMaximumFractionDigits(0);
						valueTemp = nf.format(cell.getNumericCellValue());
						break;  
					default:  
				}

				//if string not empty
				if(valueTemp != "") {
					String value = valueTemp;
					listTemp.add(new HashMap<String, String>() {{ put(key, value); }});
				}
			}
		}
		
		return listTemp;
	}
}
