package FrameWorkFunctions;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import ExcelUtility.ExcelDriver;
import Reports.ExtentLogging;
import Reports.ExtentTestManager;
import Utility.Constants;


public class FrameWorkFunctions {
	public static WebDriver driver;
	 public static ExcelDriver driverExcel;
	 public static ExtentTest parentTest;
	 public static ExtentTest childTest;
	 public static ExtentTest grandChildTest;
	 static ExtentLogging logStep;
	public FrameWorkFunctions(WebDriver driver) {
		FrameWorkFunctions.driver=driver;
	}
	public void autoStepFunction() throws Throwable {
		logStep=new ExtentLogging();
		driverExcel=new ExcelDriver();
		Sheet mainSheet=driverExcel.getExcelDriver("websitesFile.xlsx", "MainSheet");
		  driverExcel.getDataMainSheet(mainSheet);
		  boolean flag=false;
		  String pageName="";
		  for(int i=0;i<Constants.WebsiteSheetNameCount;i++) {
			  parentTest=ExtentTestManager.startTest(Constants.WebsiteSheetName[i]);
			  Sheet mainSheetAuto=driverExcel.getExcelDriver("websitesFile.xlsx", Constants.WebsiteSheetName[i]);
			  driverExcel.getDataMainSheetAuto(mainSheetAuto);
			  System.out.println(Constants.WebsiteSheetName[i]);
			  pageName=Constants.pageName[0];
			  Constants.currentFolderName=Constants.WebsiteSheetName[i];
			  childTest=ExtentTestManager.getTest().createNode(Constants.pageName[0]+" Verification");
			  DeleteADirectory("ComparisonImages");
			  DeleteADirectory("TemporayCheck");
			  for(int j=0;j<Constants.WebsiteSheetStepsCount;j++)
			  {
				  if(pageName.equals(Constants.pageName[j])) {
					  
				  }
				  else {
					  ExtentTestManager.endTest();
					  childTest=ExtentTestManager.getTest().createNode(Constants.pageName[j]+" Verification");
					  pageName=Constants.pageName[j];
				  }
				  if("VERIFYCONTENT".equals(Constants.StepAction[j])) {
					  String pageSource = driver.getPageSource();
					  performVerifyContent(Constants.Data[j],pageSource,childTest);
					  performVerifyContentXpath(Constants.Data[j],pageSource,childTest);
					  
				  }
				  else if("VERIFYIMAGE".equals(Constants.StepAction[j])) {
					  //String pageSource = driver.getPageSource();
					  //performVerifyContent(Constants.Data[j],pageSource,childTest);
					  performVerifyImageComparison(Constants.Data[j],childTest);
					  performVerifyImageXpath(Constants.Data[j],childTest);
					  File file = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\"+Constants.currentFolderName+"\\TemporayCheck\\");
				      FileUtils.deleteDirectory(file);
				  }
				  else {
				  flag=perform(Constants.StepAction[j],"","",Constants.Data[j],"10");
				  if(flag==true) {
					  logStep.LogStep(childTest, "true,"+Constants.StepAction[j]+" "+Constants.Data[j]+" is Success");
				  }
				  else {
					  logStep.LogStep(childTest, "false,"+Constants.StepAction[j]+" "+Constants.Data[j]+" Fail");
				  }
				  }
			  }
			  ExtentTestManager.endTest();
			  System.out.println("*******************************************");
		  }
	}
	public static boolean performVerifyImageComparison(String value,ExtentTest childTest) {
		logStep=new ExtentLogging();
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//WebElement Element;
		grandChildTest=childTest.createNode("Image Verification Comparison");
		String[] myArray=value.split(";");
		for(int i=0;i<myArray.length;i++) {
			
			try {
				if(getImagesFromURL(myArray[i])) {
					File orignal=checkExistenceBaseImage(myArray[i]);
					File toCheck=checkExistenceTemporayImage(myArray[i]);
					if(orignal != null) {
						if(toCheck != null) {
						int percentageImage=getImagePercentage(orignal,toCheck);
					    BufferedImage imgA = ImageIO.read(orignal); 
					    BufferedImage imgB = ImageIO.read(toCheck);
					    if(bufferedImagesEqual(imgA,imgB)) {
					    	logStep.LogStep(grandChildTest, "true,Image *"+myArray[i]+"* is "+percentageImage+" % Equal");
					    }
					    else {
					    	
					    	CreateADirectory("ComparisonImages");
					    	addImagesToFolder(imgA,imgB,myArray[i],"ComparisonImages");
					    	subtractImages(imgA,imgB,myArray[i],"ComparisonImages");
					    	logStep.LogStep(grandChildTest, "fail,Image *"+myArray[i]+"* is "+percentageImage+" % Equal");
					    }
						}
						else {
							logStep.LogStep(grandChildTest, "fail,*"+myArray[i]+"* Temporary File is not present is not present in temporary directory.");
						}
					}
					else {
				    	logStep.LogStep(grandChildTest, "fail,*"+myArray[i]+"* Orignal File is not present in orignal directory.");
					}
				}
				else {
			    	logStep.LogStep(grandChildTest, "fail,No Image Exist with this Name *"+myArray[i]);
				}
			}
			catch(Exception e) {
				logStep.LogStep(grandChildTest, "false,Try is Fail");
			}
		}
		ExtentTestManager.endTest();
		return true;
	}
	public static String getTimeAndDate() {
		DateFormat dateFormat=new SimpleDateFormat("DD_MM_YYYY_HH_MM_SS");
		Date date= new Date();
		String date1=dateFormat.format(date);
		return date1;
	} 
	public static boolean addImagesToFolder(BufferedImage image1, BufferedImage image2,String fileName,String directoryDestination) {
		boolean flag=false; 
		try {
                String actual=fileName.substring(fileName.lastIndexOf(".") +1, fileName.length());
                String fileNameOrignal=fileName.substring(0,fileName.lastIndexOf("."));
                String fileNameNew=fileName.substring(0,fileName.lastIndexOf("."));
                System.out.println(fileNameOrignal+"*******"+fileNameNew);
                fileNameOrignal=fileNameOrignal+"_Base."+actual;
                fileNameNew=fileNameNew+"_URL."+actual;
               //download image to the workspace where the project is, save picture as picture.png (can be changed)
               ImageIO.write(image1, actual, new File(System.getProperty("user.dir")+"\\src\\main\\resources\\"+Constants.currentFolderName+"\\"+directoryDestination+"\\"+fileNameOrignal));
               ImageIO.write(image2, actual, new File(System.getProperty("user.dir")+"\\src\\main\\resources\\"+Constants.currentFolderName+"\\"+directoryDestination+"\\"+fileNameNew));
     	      flag=true;
		}     
		 catch(Exception e){
			 flag=false;
		 }
		return flag;
	}
	public static boolean getImagesFromURL(String fileName) {
		boolean flag=false; 
		try {
	      List<WebElement> elementList = new ArrayList(); 
	      elementList = driver.findElements(By.tagName("img"));       
	      elementList.addAll(driver.findElements(By.tagName("img"))); 
	      List<WebElement> finalList = new ArrayList(); 
	      String imageName="";
	      URL imageURL = null;
	      for (WebElement element : elementList)
          { 

               if(element.getAttribute("src") != null) 
                { 
                       finalList.add(element); 
                       imageName=element.getAttribute("src");
                       if(imageName.contains(fileName)) {
                    	   imageURL = new URL(imageName);
                           BufferedImage saveImage = ImageIO.read(imageURL);
                           String actual=fileName.substring(fileName.lastIndexOf(".") +1, fileName.length());
                           System.out.println(actual);
                           CreateADirectory("TemporayCheck");
                           //download image to the workspace where the project is, save picture as picture.png (can be changed)
                           ImageIO.write(saveImage, actual, new File(System.getProperty("user.dir")+"\\src\\main\\resources\\"+Constants.currentFolderName+"\\TemporayCheck\\"+fileName));
                       }
                       //String actual=imageName.substring(50, imageName.length());
                      // System.out.println(actual);
                }    
            } 
	      flag=true;
		 }
		 catch(Exception e){
			 flag=false;
		 }
		return flag;
	}
	public static void DeleteADirectory(String DirectoryName) throws IOException
    {
        //project directory
        String workingDirectory = System.getProperty("user.dir")+"\\src\\main\\resources\\"+Constants.currentFolderName;
        String  dir = workingDirectory + File.separator + DirectoryName;

        //create a directory in the path

        File file = new File(dir);

        if (!file.exists()) {
            
        } else {
        	FileUtils.deleteDirectory(new File(dir));
            //System.out.println("Directory is already existed!");
        }

    }
	public static void CreateADirectory(String DirectoryName)
    {
        //project directory
        String workingDirectory = System.getProperty("user.dir")+"\\src\\main\\resources\\"+Constants.currentFolderName;
        String  dir = workingDirectory + File.separator + DirectoryName;

        System.out.println("Final file directory : " + dir);

        //create a directory in the path

        File file = new File(dir);

        if (!file.exists()) {
            file.mkdir();
            System.out.println("Directory is created!");
        } else {
            System.out.println("Directory is already existed!");
        }

    }
	public static File checkExistenceBaseImage(String fileName) {
		File orignal=new File(System.getProperty("user.dir")+"\\src\\main\\resources\\"+Constants.currentFolderName+"\\BaseImages\\"+fileName);
		if(orignal.isFile()) {
			return orignal;
		}
		else {
			orignal = null;
			return orignal;
		}
	}
	public static File checkExistenceTemporayImage(String fileName) {
		File orignal=new File(System.getProperty("user.dir")+"\\src\\main\\resources\\"+Constants.currentFolderName+"\\TemporayCheck\\"+fileName);
		if(orignal.isFile()) {
			return orignal;
		}
		else {
			orignal = null;
			return orignal;
		}
	}
	public static int getImagePercentage(File fileA, File fileB) throws IOException { // start of the function getImagePercentage

	    int percentage = 0;
	    BufferedImage biA = ImageIO.read(fileA); // reads fileA into bufferedImage
	    DataBuffer dbA = biA.getData().getDataBuffer();
	    int sizeA = dbA.getSize();
	    BufferedImage biB = ImageIO.read(fileB); // reads fileA into bufferedImage
	    DataBuffer dbB = biB.getData().getDataBuffer();
	    int sizeB = dbB.getSize();
	    int count = 0;
	    // compare data-buffer objects //
	    if (sizeA == sizeB) { // checks the size of the both the bufferedImage

	        for (int i = 0; i < sizeA; i++) {

	            if (dbA.getElem(i) == dbB.getElem(i)) { // checks bufferedImage array is same in both the image
	                count = count + 1;
	            }
	        }
	        percentage = (count * 100) / sizeA; // calculates matching percentage
	    } else {
	        System.out.println("Both the images are not of same size");
	    }
	    return percentage; // returns the matching percentage value
	}
	static boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
	    if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
	     for (int x = 0; x < img1.getWidth(); x++) {
	      for (int y = 0; y < img1.getHeight(); y++) {
	       if (img1.getRGB(x, y) != img2.getRGB(x, y))
	        return false;
	       }
	      }
	     } else {
	        return false;
	     }
	     return true;
	    }
	public static boolean performVerifyImageXpath(String value,ExtentTest childTest) {
		logStep=new ExtentLogging();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Element;
		grandChildTest=childTest.createNode("Image Verification Visiblity");
		String[] myArray=value.split(";");
		for(int i=0;i<myArray.length;i++) {
			
			try {
				Element = driver.findElement(By.xpath("//img[contains(@src,'"+myArray[i]+"')]"));
				js.executeScript("arguments[0].scrollIntoView(true);", Element);
				if(driver.findElement(By.xpath("//img[contains(@src,'"+myArray[i]+"')]")) != null) {
					//waitForDivtoScroll(driver,"//img[contains(@src,'"+myArray[i]+"')]");	
					//verifyImageFromFolder(myArray[i]);
					  JavascriptExecutor js1 = ((JavascriptExecutor)driver);
					  Boolean imgPresent =  (Boolean) (js1.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", Element));
					  
					  if(imgPresent==true){
						  logStep.LogStep(grandChildTest, "true,Image *"+myArray[i]+"* is Displayed");
						  System.out.println("Image is present");
					  }else{
						  logStep.LogStep(grandChildTest, "false,Image *"+myArray[i]+"* is Not Displayed");
						  System.out.println("Image is not present");
					  }
					  
				  }
				  else {
					  logStep.LogStep(grandChildTest, "false,Image No: "+i+" is Fail");
				  }
			}
			catch(Exception e) {
				logStep.LogStep(grandChildTest, "false,Image No: "+i+" is Fail");
			}
		}
		ExtentTestManager.endTest();
		return true;
	}
	private static void subtractImages(BufferedImage image1, BufferedImage image2,String fileName,String directoryTo) throws IOException {
		  BufferedImage image3 = new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());
		  int color;
		  for(int x = 0; x < image1.getWidth(); x++)
		      for(int y = 0; y < image1.getHeight(); y++) {
		          color = Math.abs(image2.getRGB(x, y) - image1.getRGB(x, y));                
		          image3.setRGB(x, y, color);
		      }
		  String actual=fileName.substring(fileName.lastIndexOf(".") +1, fileName.length());
          String fileNameOrignal=fileName.substring(0,fileName.lastIndexOf("."));
          fileNameOrignal=fileNameOrignal+"_Comparison."+actual;
		  ImageIO.write(image3, actual,  new File(System.getProperty("user.dir")+"\\src\\main\\resources\\"+Constants.currentFolderName+"\\"+directoryTo+"\\"+fileNameOrignal));
		}
	public static boolean verifyImageFromFolder(String imageName) throws FindFailed {
		Screen screen = new Screen();
		screen.setAutoWaitTimeout(120);
		if(screen.wait(new Pattern("C:\\Users\\gulraiz.shabbir\\eclipse-workspace\\ContentChecikingArtifact\\src\\main\\resources\\"+imageName)).checkMatch() != null) {
			System.out.println("HELLO");
		}
		
		screen.wait(new Pattern("C:\\Users\\gulraiz.shabbir\\eclipse-workspace\\ContentChecikingArtifact\\src\\main\\resources\\"+imageName)).click();
		screen.wait(new Pattern("C:\\Users\\gulraiz.shabbir\\eclipse-workspace\\ContentChecikingArtifact\\src\\main\\resources\\"+imageName).exact()).click();
		return true;
	}
	public static String waitForDivtoScroll(WebDriver driver,String xpathImage) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Element;
		String flag = "false";
		try {
			Element = driver.findElement(By.xpath(xpathImage));
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(120))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

		
		
		boolean aboutMe = wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
					System.out.println("Loading Element of is Present");
					WebElement elementParentXpath=Element.findElement(By.xpath("./.."));
					  Dimension d = elementParentXpath.getSize();
					  boolean isVisible =false;
					  while(isVisible != true) {
						  
						  d = elementParentXpath.getSize();
						  isVisible = (d.getHeight() > 0 && d.getWidth() > 0);
						  
						  if(isVisible) {
							  System.out.println(d);
							  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementParentXpath);
							  System.out.println("****************************************************");
							  return true;
						  }
						  else {
							  ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,100)");  
							  isVisible =false;
						  }		  
					  }
					return isVisible;
			}
		});
		return flag;
	}
	catch(Exception e) {
		flag="false,"+e.getMessage();
		return flag;
	}	
	}
	public static boolean performVerifyContent(String value,String pageSource,ExtentTest childTest) {
		logStep=new ExtentLogging();
		grandChildTest=childTest.createNode("Content Verification");
//		grandChildTest=ExtentTestManager.getTest().createNode("Content Verification");
		String[] myArray=value.split(";");
		boolean flag=false;
		String resultAddition;
		for(int i=0;i<myArray.length;i++) {
			flag=pageSource.contains(myArray[i]);
			try {
			resultAddition=myArray[i].substring(0,30);
			}
			catch(Exception e) {
				resultAddition=myArray[i];
			}
			if(flag==true) {
				  logStep.LogStep(grandChildTest, "true,Content :* "+resultAddition+" * is Success");
			  }
			  else {
				  logStep.LogStep(grandChildTest, "false,Content :* "+resultAddition+" * is Fail");
			  }
			System.out.println(flag);
			flag=false;
		}
		ExtentTestManager.endTest();
		return true;
	}
	public static boolean performVerifyContentXpath(String value,String pageSource,ExtentTest childTest) {
		logStep=new ExtentLogging();
		grandChildTest=childTest.createNode("Content Verification Visiblity");
//		grandChildTest=ExtentTestManager.getTest().createNode("Content Verification");
		String[] myArray=value.split(";");
		boolean flag=false;
		for(int i=0;i<myArray.length;i++) {
			String resultAddition;
			try {
				resultAddition=myArray[i].substring(0,30);
				}
				catch(Exception e) {
					resultAddition=myArray[i];
				}
			//flag=pageSource.contains(myArray[i]);
			try {
				if(driver.findElement(By.xpath("//*[text()='"+myArray[i]+"']")) != null) {
					  logStep.LogStep(grandChildTest, "true,Content :* "+resultAddition+" * is Success");
				  }
				  else {
					  logStep.LogStep(grandChildTest, "false,Content :* "+resultAddition+" * is Fail");
				  }
			}
			catch(Exception e) {
				logStep.LogStep(grandChildTest, "false,Content :* "+resultAddition+" * is Fail");
			}
		}
		ExtentTestManager.endTest();
		return true;
	}
	public static boolean perform(String operation,String objectType,String Object,String value,String waitfor) throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver,Integer.parseInt(waitfor));
		WebElement ele = null;
		boolean foundElement=false;
		switch (operation.toUpperCase()) {
		case "VERIFYCONTENT":
			
			break;
		case "CHECKURL":
			if(driver.getCurrentUrl().equals(value)) {
				Constants.currentURL=driver.getCurrentUrl();
				foundElement=true;
				System.out.println("URL Verified");
			}
			else {
				foundElement=false;
				System.out.println("URL Not Verified");
			}
			break;
		case "CHECKTITLE":
			if(driver.getTitle().equals(value)) {
				foundElement=true;
				System.out.println("Title Verified");
			}
			else {
				foundElement=false;
				System.out.println("Title Not Verified");
			}
			break;
		case "CLICK":
			try {
			ele =wait.until(ExpectedConditions.visibilityOfElementLocated(getObject(objectType,Object)));
			driver.findElement(getObject(objectType,Object)).click();
			foundElement=true;
			}
			catch(Exception e){
				foundElement=false;
			}
			break;
		case "SETTEXT":
			try {
				ele =wait.until(ExpectedConditions.visibilityOfElementLocated(getObject(objectType,Object)));
			driver.findElement(getObject(objectType,Object)).sendKeys(value);
			foundElement=true;
			}
			catch(Exception e){
				foundElement=false;
			}
			break;
		case "GOTOURL":
			try {
			driver.get(value);
			waitForPageLoad();
			foundElement=true;
			}
			catch(Exception e){
				foundElement=false;
			}
			break;
		case "GETTEXT":
			try {
				ele =wait.until(ExpectedConditions.visibilityOfElementLocated(getObject(objectType,Object)));
			String valueofElement=driver.findElement(getObject(objectType,Object)).getText();
			System.out.println("**************************"+valueofElement+"*********************************");
			foundElement=true;
			}
			catch(Exception e){
				foundElement=false;
			}
			break;
		case "ASSERTVALUE":
			try {
				ele =wait.until(ExpectedConditions.visibilityOfElementLocated(getObject(objectType,Object)));
			String actual=driver.findElement(getObject(objectType,Object)).getText();
			Assert.assertEquals(actual, value);
			foundElement=true;
				}
				catch(Exception e){
					foundElement=false;
				}
			break;
		default:
			foundElement=false;
			break;
			
		}
		return foundElement;
	}
	private static By getObject(String objectType,String Object) throws Throwable {
		if(objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(Object);
		}
		else if(objectType.equalsIgnoreCase("CLASSNAME")) {
			return By.className(Object);
		}
		else if(objectType.equalsIgnoreCase("CSS")) {
			return By.cssSelector(Object);
		}
		else if(objectType.equalsIgnoreCase("NAME")) {
			return By.name(Object);
		}
		else if(objectType.equalsIgnoreCase("ID")) {
			return By.id(Object);
		}
		else { throw new Exception("Wrong object type");}
	}
	public static void waitForPageLoad() {
	    Wait<WebDriver> wait = new WebDriverWait(driver, 60);
	    wait.until(new Function<WebDriver, Boolean>() {
	        public Boolean apply(WebDriver driver) {
	            //System.out.println("Current Window State       : "
	              //  + String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
	            return String
	                .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
	                .equals("complete");
	        }
	    });
	}
}
