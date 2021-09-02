package UCI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class UCIPage<T extends UCIPage> extends WebDriverPage<UCIPage>
{
	public static String url = "https://www.uci.org";

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(css="#uci-main-menu")
	WebElement mainMenu;

	@FindBy(css=".uci-submenu-wrapper")
	WebElement subMenu;

	public UCIPage(WebDriver driver, String path)
	{
		super(driver, url + path);
	}

	public T open()
	{
		return (T) super.open();
	}

	public WebElement fromMainMenu(String menuItem)
	{
		return mainMenu.findElement(By.linkText(menuItem));
	}

	public WebElement fromSubMenu(String menuItem)
	{
		return subMenu.findElement(By.linkText(menuItem));
	}

}
