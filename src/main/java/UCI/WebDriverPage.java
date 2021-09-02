package UCI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebDriverPage<T extends WebDriverPage>
{
	WebDriver driver;
	WebDriverWait wait;
	public String url;

	public WebDriverPage(WebDriver driver, String url)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, 30);
		this.url = url;

		PageFactory.initElements(driver, this);
	}

	public T open()
	{
		driver.get(url);
		return (T) this;
	}
}
