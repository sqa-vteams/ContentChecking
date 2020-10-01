package MainTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import Utility.BaseClass;

public class Research extends BaseClass {
  @Test
  public void f() throws IOException {
	  String url = "https://nextbridge.com/services/software-development-outsourcing/";
      driver.get(url);
	  String filePath="E:\\temp1.txt";
	  File FC=new File(filePath);
	  FC.createNewFile();
	  
	  FileWriter FW=new FileWriter(filePath);
	  BufferedWriter BW=new BufferedWriter(FW);
	  List<WebElement> txtfields = driver.findElements(By.xpath("//span"));
	  String yourtext="";
	  int counterarray=0;
	  
	  for(int a=0; a<txtfields.size();a++){   
		  yourtext=txtfields.get(a).getAttribute("innerHTML"); 
		  if(yourtext==" " || yourtext == "" || yourtext == null) {  
		  }
		  else {
			  counterarray++;
			  BW.write(yourtext);
			  BW.newLine();
		  }
		  }
	  
	  BW.close();
	  fileComparison();
	 /* String innerHtmlarray[]=new String[counterarray];
	  for(int a=0; a<txtfields.size();a++){   
		  yourtext=txtfields.get(a).getAttribute("innerHTML"); 
		  if(yourtext==" " || yourtext == "" || yourtext == null) {  
		  }
		  else {
			  innerHtmlarray[a]=yourtext;
		  }
		  }
	  String content="";
	  FileReader FR=new FileReader(filePath);
	  BufferedReader BR= new BufferedReader(FR);
	  int counterTemp=0;
	 
			  while((content=BR.readLine()) != null) {
				  
				  if(content.equals(innerHtmlarray[counterTemp])) {
					  
				  }
				  else {
					  System.out.println("******"+content+"*****"+yourtext);
				  }
				  counterTemp++;
			  }*/

  }
  public void fileComparison() throws IOException
  {   
      BufferedReader reader1 = new BufferedReader(new FileReader("E:\\text.txt"));
       
      BufferedReader reader2 = new BufferedReader(new FileReader("E:\\temp1.txt"));
       
      String line1 = reader1.readLine();
       
      String line2 = reader2.readLine();
       
      boolean areEqual = true;
      int counter=1;
       int percentage=0;
      int lineNum = 1;
       
      while (line1 != null || line2 != null)
      {
          if(line1 == null || line2 == null)
          {
              areEqual = false;
              System.out.println("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
             // break;
          }
          else if(! line1.equalsIgnoreCase(line2))
          {
              areEqual = false;
              System.out.println("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
            //  break;
          }
          else {
              
              counter++;
                      	  
          }
          line1 = reader1.readLine();
          
          line2 = reader2.readLine();
          lineNum++;
      }
      percentage=(counter*100)/lineNum;
      if(areEqual)
      {
          System.out.println("Two files have same content. "+ percentage);
      }
      else
      {
        //  System.out.println("Two files have different content. They differ at line "+lineNum);
           
          System.out.println(percentage);
      }
       
      reader1.close();
       
      reader2.close();
  }
}
