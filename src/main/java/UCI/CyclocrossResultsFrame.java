package UCI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.util.List;
import java.util.Optional;

public class CyclocrossResultsFrame extends WebDriverPage<CyclocrossResultsFrame>
{
	public static String url = "https://dataride.uci.org/iframe/results/3";

	By raceTypesDropdown = By.cssSelector("#raceTypes");

	By raceTypeOptions = By.cssSelector("#raceTypes_listbox li");

	public CyclocrossResultsFrame(WebDriver driver)
	{
		super(driver, url);
	}

	public void setRaceType(String raceType)
	{
		WebElement dropdown = wait.until(elementToBeClickable(raceTypesDropdown));
		dropdown.click();

		List<WebElement> elements = wait.until(visibilityOfAllElementsLocatedBy(raceTypeOptions));
		Optional<WebElement> selected = elements.stream()
				.filter(option -> option.getText().equalsIgnoreCase(raceType))
				.findFirst();

		if (selected.isPresent())
		{
			selected.get().click();
		}
		else
		{
			throw new RuntimeException("Can't find raceType option: " + raceType);
		}
	}
}
