package Reports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentLogging {
	public void LogStep(ExtentTest parentTest,String log) {
		String result=log.substring(0,log.indexOf(","));
		String logtemp=log.substring(log.indexOf(",")+1, log.length());		    
		if(result.equals("true")) {
			parentTest.log(Status.PASS, MarkupHelper.createLabel(logtemp, ExtentColor.GREEN));
		}
		else {
			parentTest.log(Status.FAIL, MarkupHelper.createLabel(logtemp, ExtentColor.RED));
		}
	}
}
