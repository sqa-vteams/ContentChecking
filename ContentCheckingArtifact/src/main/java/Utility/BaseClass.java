package Utility;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import DriverFactory.DriverManager;
import DriverFactory.DriverManagerFactory;
import Email.emaiSending;
import Reports.ExtentTestManager;

public class BaseClass {
	DriverManager driverManager;
	public ExtentTest parentTest;
	public static WebDriver driver;
	public WebDriver getDriver() {
        return driver;
    }
@BeforeSuite
public void beforeSuite() throws IOException {
	parentTest=ExtentTestManager.startTest("Driver Configuration");
	try {
		  driverManager=DriverManagerFactory.getDriverManager("FIREFOX");
		  driver=driverManager.getWebDriver();
		  driver.manage().window().maximize();	
		  parentTest.log(Status.PASS, MarkupHelper.createLabel("Driver Configurations Successfully Done.", ExtentColor.GREEN));
		  ExtentTestManager.endTest();
	}
	catch(Exception e) {
		System.out.println("Cause is :"+e.getCause());
		System.out.println("Message is :"+ e.getMessage());
		parentTest.log(Status.FAIL, MarkupHelper.createLabel(e.getMessage(), ExtentColor.RED));
		ExtentTestManager.endTest();
	}
}

@AfterSuite
public void afterSuite() {
	emaiSending e1=new emaiSending();
	e1.sendPDFReportByGMail("gulraiz.smartcommerce@gmail.com", "ZXvYw9Ch", "gulraiz.shabbir@nxb.com.pk", "Report", "");
		  driverManager.quitWebDriver();
	  }
}
