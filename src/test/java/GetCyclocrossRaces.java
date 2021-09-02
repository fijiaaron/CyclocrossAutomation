import UCI.CyclocrossResultsFrame;
import UCI.CyclocrossResultsPage;
import UCI.UCIDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetCyclocrossRaces
{
	WebDriver driver;
	WebDriverWait wait;
	UCIDriver uci;

	@BeforeEach
	public void setup()
	{
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 15);
		uci = new UCIDriver(driver);
	}

	@AfterEach
	public void cleanup()
	{
		driver.quit();
	}

	@Test
	public void testNavigation()
	{
		UCI.HomePage homepage = uci.openHomePage();
		uci.pause(1);
		UCI.CyclocrossPage cyclocrossPage = homepage.clickCyclocrossFromMainMenu();
		uci.pause(1);
		UCI.CyclocrossResultsPage cyclocrossResultsPage = cyclocrossPage.clickResultsFromSubmenu();
		uci.pause(5);
		System.out.println(driver.getTitle());
	}

	@Test public void testOpenResultsPage()
	{
		var page = new CyclocrossResultsPage(driver);
		page.open();

	}
	@Test
	public void testOnlyResultsFrame()
	{
		var results = new CyclocrossResultsFrame(driver).open();
		results.setRaceType("Individual");
	}
}
