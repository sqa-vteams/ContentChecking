package DriverFactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverManager extends DriverManager{

	@Override
	protected void createWebDriver() {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options=new ChromeOptions();
		this.driver=new ChromeDriver(options);
	}
	

}
