package MainTests;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.xmlgraphics.image.loader.spi.ImageConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import FrameWorkFunctions.FrameWorkFunctions;
import Utility.BaseClass;

public class AutomatedSteps extends BaseClass {
	public static FrameWorkFunctions frameFunction;
	 @BeforeClass
	 public void initialize() throws Throwable {
		 frameFunction=new FrameWorkFunctions(driver);

	 }
  @Test
  public void contentVerificationAutomated() throws Throwable {

	 /* String url = "https://nextbridge.com/services/software-development-outsourcing/";
      driver.get(url);
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
                       String actual=imageName.substring(50, imageName.length());
                       System.out.println(actual);
                       
                       
                       imageURL = new URL(imageName);
                       BufferedImage saveImage = ImageIO.read(imageURL);

                       //download image to the workspace where the project is, save picture as picture.png (can be changed)
                       ImageIO.write(saveImage, "jpg", new File(actual));
                }    
            } */
      /* BufferedImage imgA = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\davinci.png")); 
       BufferedImage imgB = ImageIO.read(new File(System.getProperty("user.dir")+"\\davinci.png"));  
       System.out.println(bufferedImagesEqual(imgA,imgB));
       File orignal=new File(System.getProperty("user.dir")+"\\src\\main\\resources\\davinci.png");
       File o1=new File(System.getProperty("user.dir")+"\\davinci.png");
       int io=getImagePercentage(orignal,o1);
       System.out.println(io);
          //Wait for 5 Sec
          Thread.sleep(5);*/

       
//	  Screen screen = new Screen();
	//set a timeout for waiting for the image
//	screen.setAutoWaitTimeout(120); //default is 10 seconds
	//wait for an image to get displayed on the screen and then click on it
//	if(screen.wait(new Pattern("C:\\Users\\gulraiz.shabbir\\eclipse-workspace\\ContentChecikingArtifact\\src\\main\\resources\\about.png")).checkMatch() != null) {
//		System.out.print("Hello");
//	}
//	screen.wait(new Pattern("C:\\Users\\gulraiz.shabbir\\eclipse-workspace\\ContentChecikingArtifact\\src\\main\\resources\\about.png")).click();
	//wait for an image with exact match
//	screen.wait(new Pattern("C:\\Users\\gulraiz.shabbir\\eclipse-workspace\\ContentChecikingArtifact\\src\\main\\resources\\about.png").exact()).click();
	  //driver.get("https://nextbridge.com/");
	 // driver.findElement(By.xpath("//*[text()='Services']"));
	 // if(driver.findElement(By.xpath("//*[text()='Services']")) != null) {
	//	  System.out.print("Hello");
	 // }
	
	  frameFunction.autoStepFunction();
  }
  public static boolean convertFormat(String inputImagePath,
          String outputImagePath, String formatName) throws IOException {
      FileInputStream inputStream = new FileInputStream(inputImagePath);
      FileOutputStream outputStream = new FileOutputStream(outputImagePath);
       
      // reads input image from file
      BufferedImage inputImage = ImageIO.read(inputStream);
       
      // writes to the output image in specified format
      boolean result = ImageIO.write(inputImage, formatName, outputStream);
       
      // needs to close the streams
      outputStream.close();
      inputStream.close();
       
      return result;
  }
  boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
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
  private static void subtractImages(BufferedImage image1, BufferedImage image2) throws IOException {
  BufferedImage image3 = new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());
  int color;
  for(int x = 0; x < image1.getWidth(); x++)
      for(int y = 0; y < image1.getHeight(); y++) {
          color = Math.abs(image2.getRGB(x, y) - image1.getRGB(x, y));                
          image3.setRGB(x, y, color);
      }
  ImageIO.write(image3, "bmp",  new File(System.getProperty("user.dir")+"\\src\\main\\resources\\"+"image.bmp"));
}
 public int getImagePercentage(File fileA, File fileB) throws IOException { // start of the function getImagePercentage

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
}
