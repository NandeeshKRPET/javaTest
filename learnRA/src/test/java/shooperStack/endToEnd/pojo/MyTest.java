package shooperStack.endToEnd.pojo;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class MyTest
{
	WebDriver driver;
	@Test
	public void testMe()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
	}

}
