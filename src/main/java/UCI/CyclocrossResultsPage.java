package UCI;

import org.openqa.selenium.WebDriver;

public class CyclocrossResultsPage extends UCIPage<CyclocrossResultsPage>
{
	public static String url = "https://www.uci.org/cyclo-cross/results";

	public CyclocrossResultsPage(WebDriver driver)
	{
		super(driver, url);
	}
}
