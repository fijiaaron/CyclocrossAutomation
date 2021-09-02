package UCI;

import org.openqa.selenium.WebDriver;

public class CyclocrossPage extends UCIPage<CyclocrossPage>
{
	public static String url = "https://www.uci.org/cyclo-cross";

	public CyclocrossPage(WebDriver driver)
	{
		super(driver, url);
	}

	public CyclocrossResultsPage clickResultsFromSubmenu()
	{
		fromSubMenu("Results").click();
		return new CyclocrossResultsPage(driver);
	}
}
