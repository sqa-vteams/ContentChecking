package DriverFactory;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FireFoxDriverManager extends DriverManager{
	@Override
	protected void createWebDriver() {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe"); 
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		//FirefoxOptions options=new FirefoxOptions();
		this.driver=new FirefoxDriver(options);
	}

}
