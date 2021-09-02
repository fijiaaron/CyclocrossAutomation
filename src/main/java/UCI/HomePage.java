package UCI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends UCIPage<HomePage>
{
	public static String path = "/";

	public HomePage(WebDriver driver)
	{
		super(driver, UCIPage.url + HomePage.path);
	}

	public CyclocrossPage clickCyclocrossFromMainMenu()
	{
		fromMainMenu("CYCLO-CROSS").click();
		return new CyclocrossPage(driver);
	}

}
