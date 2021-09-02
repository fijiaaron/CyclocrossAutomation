package UCI;

import org.openqa.selenium.WebDriver;

public class UCIDriver
{
	WebDriver driver;

	public UCIDriver(WebDriver driver)
	{
		this.driver = driver;
	}

	public HomePage openHomePage()
	{
		HomePage homepage = new HomePage(driver);
		homepage.open();
		return homepage;
	}


	public void pause(int seconds)
	{
		try {
			Thread.sleep(seconds  * 1000);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
