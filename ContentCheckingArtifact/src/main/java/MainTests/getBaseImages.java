package MainTests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import FrameWorkFunctions.FrameWorkFunctions;
import Utility.BaseClass;

public class getBaseImages extends BaseClass {
	public static FrameWorkFunctions frameFunction;
	 @BeforeClass
	 public void initialize() throws Throwable {
		 frameFunction=new FrameWorkFunctions(driver);

	 }
  @Test
  public void getBaseImagesTest() throws Throwable {
	  frameFunction.autoStepFunctionBaseImages();
  }
}
