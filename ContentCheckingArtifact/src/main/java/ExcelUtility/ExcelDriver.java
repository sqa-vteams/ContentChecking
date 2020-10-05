package ExcelUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Utility.Constants;

public class ExcelDriver {
	public Sheet getExcelDriver(String fileName,String sheetName) {
		try {
		File file=new File(System.getProperty("user.dir")+"\\src\\main\\resources\\"+fileName);
		FileInputStream inputStream=new FileInputStream(file);
		Workbook mainWorkbook=null;
		String fileExtensionName=fileName.substring(fileName.indexOf("."));
		if(fileExtensionName.equals(".xlsx")) {
			mainWorkbook=new XSSFWorkbook(inputStream);	
		}
		else if(fileExtensionName.equals(".xls")) {
			mainWorkbook=new HSSFWorkbook(inputStream);
		}
		
		Sheet mainSheet=mainWorkbook.getSheet(sheetName);
		return mainSheet;
		}
		catch (IOException ex) {
			return  null;
		}
	}
	
public void getDataMainSheet(Sheet mainSheet) {
		if(mainSheet!=null) {
			  int rowCount=mainSheet.getLastRowNum()-mainSheet.getFirstRowNum();
			  int countYesItems=0;
			  for(int i=1;i<rowCount+1;i++) {
				  Row row=mainSheet.getRow(i);
					  try {
						  //System.out.println(row.getCell(columnName("WebsiteSheetName",mainSheet)).getStringCellValue());
						if("Y".equals(row.getCell(columnName("Execution",mainSheet)).getStringCellValue())) {
							countYesItems++;
						}
					} catch (EncryptedDocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
			  }
			  Constants.WebsiteSheetNameCount=countYesItems;
			  Constants.WebsiteSheetName=new String[countYesItems];
			  int countTemp=0;
			  for(int j=1;j<rowCount+1;j++) {
				  
				  Row row=mainSheet.getRow(j);
					  try {
						if("Y".equals(row.getCell(columnName("Execution",mainSheet)).getStringCellValue())) {
						Constants.WebsiteSheetName[countTemp]=row.getCell(columnName("WebsiteSheetName",mainSheet)).getStringCellValue();
						countTemp++;
						}
						
						
					} catch (EncryptedDocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
			  }	  
			  	  
		  }
		  else {
			  
		  }
	}
public void getDataMainSheetAuto(Sheet mainSheet) {
	if(mainSheet!=null) {
		  int rowCount=mainSheet.getLastRowNum()-mainSheet.getFirstRowNum();
		  int countYesItems=0;
		  for(int i=1;i<rowCount+1;i++) {
			  Row row=mainSheet.getRow(i);
				  try {
					  //System.out.println(row.getCell(columnName("WebsiteSheetName",mainSheet)).getStringCellValue());
					if("Y".equals(row.getCell(columnName("Execution",mainSheet)).getStringCellValue())) {
						countYesItems++;
					}
				} catch (EncryptedDocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
		  }
		  Constants.WebsiteSheetStepsCount=countYesItems;
		  Constants.StepDescription=new String[countYesItems];
		  Constants.StepAction=new String[countYesItems];
		  Constants.Data=new String[countYesItems];
		  Constants.pageName=new String[countYesItems];
		  int countTemp=0;
		  for(int j=1;j<rowCount+1;j++) {
			  
			  Row row=mainSheet.getRow(j);
				  try {
					if("Y".equals(row.getCell(columnName("Execution",mainSheet)).getStringCellValue())) {
					Constants.StepDescription[countTemp]=row.getCell(columnName("StepDescription",mainSheet)).getStringCellValue();
					Constants.StepAction[countTemp]=row.getCell(columnName("StepAction",mainSheet)).getStringCellValue();
					Constants.Data[countTemp]=row.getCell(columnName("Data",mainSheet)).getStringCellValue();
					Constants.pageName[countTemp]=row.getCell(columnName("Page",mainSheet)).getStringCellValue();
					countTemp++;
					}
					
					
				} catch (EncryptedDocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
		  }	  
		  	  
	  }
	  else {
		  
	  }
}
public void getDataBaseSheetAuto(Sheet mainSheet) {
	if(mainSheet!=null) {
		  int rowCount=mainSheet.getLastRowNum()-mainSheet.getFirstRowNum();
		  int countYesItems=0;
		  for(int i=1;i<rowCount+1;i++) {
			  Row row=mainSheet.getRow(i);
				  try {
					  //System.out.println(row.getCell(columnName("WebsiteSheetName",mainSheet)).getStringCellValue());
					if("Y".equals(row.getCell(columnName("Execution",mainSheet)).getStringCellValue())) {
						countYesItems++;
					}
				} catch (EncryptedDocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
		  }
		  Constants.WebsiteSheetStepsCount=countYesItems;
		  Constants.WebsitesURLFolderName=new String[countYesItems];
		  Constants.WebsitesURL=new String[countYesItems];
		  Constants.WebsitesURLData=new String[countYesItems];
		  
		  int countTemp=0;
		  for(int j=1;j<rowCount+1;j++) {
			  
			  Row row=mainSheet.getRow(j);
				  try {
					if("Y".equals(row.getCell(columnName("Execution",mainSheet)).getStringCellValue())) {
					Constants.WebsitesURLFolderName[countTemp]=row.getCell(columnName("Folder",mainSheet)).getStringCellValue();
					Constants.WebsitesURL[countTemp]=row.getCell(columnName("URL",mainSheet)).getStringCellValue();
					Constants.WebsitesURLData[countTemp]=row.getCell(columnName("Data",mainSheet)).getStringCellValue();
					countTemp++;
					}
					
					
				} catch (EncryptedDocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
		  }	  
		  	  
	  }
	  else {
		  
	  }
}
	public String valueOFExcelCell(Cell cell) {
		String value="";
		if(cell==null)
		{
	       value="";
		}
		else {
		if(cell.getCellType()==CellType.BLANK) {
			value="";
		}
		else if(cell.getCellType()==CellType.STRING) {
			value=cell.getStringCellValue();
		}
		else if(cell.getCellType()==CellType.NUMERIC) {
			double temp=cell.getNumericCellValue();
			value=String.valueOf(temp);
		}
		else if(cell.getCellType()==CellType.BOOLEAN) {
			value=String.valueOf(cell.getBooleanCellValue());
		}
		else {
			value="";
		}
		}
		return value;
	}
	public static int columnName(String a,Sheet sh) throws EncryptedDocumentException, IOException {

	    int coefficient = 0;
	    Row row = sh.getRow(0);
	    int cellNum = row.getPhysicalNumberOfCells();
	    for (int i = 0; i < cellNum; i++) {
	        if ((row.getCell(i).toString()).equals(a)) {
	            coefficient = i;
	        }
	    }

	    return coefficient;
	}
}
