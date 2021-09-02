import UCI.Competition;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleTest
{
	WebDriver driver;
	WebDriverWait wait;

	By raceTypesDropdownArrow = By.cssSelector("[aria-owns^=raceTypes_listbox] [class*=arrow]");
	By raceTypesDropdownOptions = By.cssSelector("#raceTypes_listbox li");

	By raceCategoriesDropdownArrow = By.cssSelector("[aria-owns^=categories_listbox] [class*=arrow]");
	By raceCategoriesDropdownOptions = By.cssSelector("#categories_listbox li");

	By raceSeasonsDropdownArrow =  By.cssSelector("[aria-owns^=seasons_listbox] [class*=arrow]");
	By raceSeasonsDropdownOptions = By.cssSelector("#seasons_listbox li");

	By competitions = By.cssSelector("#competitions");

	@BeforeEach
	public void setup()
	{
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 15);
		driver.get("https://dataride.uci.org/iframe/results/3");
		pause(5);
	}

	@AfterEach
	public void cleanup()
	{
		pause(5);
		driver.quit();
	}

	public void pause(int seconds)
	{
		try {
			Thread.sleep(seconds * 1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void findRaces() throws Exception
	{
		selectRaceType("Individual");
		selectRaceCategory("Men Elite");
		selectRaceSeason("2021");
		pause(20);
		List<Competition> competitions = fetchCompetitions();
		System.out.println("got competitions: " + competitions.size());
		System.out.println(Competition.toCSV(competitions));
		printCSV(competitions);
	}

	public void selectRaceType(String raceType)
	{
		wait.until(elementToBeClickable(raceTypesDropdownArrow)).click();

		List<WebElement> raceTypeOptions =
				wait.until(visibilityOfAllElementsLocatedBy(raceTypesDropdownOptions));

		Optional<WebElement> selectedRaceType = raceTypeOptions.stream()
				.filter(option-> option.getText().equalsIgnoreCase(raceType))
				.findAny();

		if (selectedRaceType.isPresent())
		{
			System.out.println("raceType selected: " + raceType);
			selectedRaceType.get().click();
		}
		else
		{
			throw new RuntimeException("raceType not found: " + raceType);
		}
	}

	public void selectRaceCategory(String raceCategory)
	{
		wait.until(elementToBeClickable(raceCategoriesDropdownArrow)).click();

		List<WebElement> raceCategoriesOptions =
				wait.until(visibilityOfAllElementsLocatedBy(raceCategoriesDropdownOptions));

		Optional<WebElement> selectedCategory = raceCategoriesOptions.stream()
				.filter(option-> option.getText().equalsIgnoreCase(raceCategory))
				.findAny();

		if (selectedCategory.isPresent())
		{
			System.out.println("raceCategory selected: " + raceCategory);
			selectedCategory.get().click();
		}
		else
		{
			throw new RuntimeException("raceCategory not found: " + raceCategory);
		}
	}

	public void selectRaceSeason(String season)
	{
		wait.until(elementToBeClickable(raceSeasonsDropdownArrow)).click();

		List<WebElement> raceSeasonsOptions =
				wait.until(visibilityOfAllElementsLocatedBy(raceSeasonsDropdownOptions));

		Optional<WebElement> selectedCategory = raceSeasonsOptions.stream()
				.filter(option-> option.getText().equalsIgnoreCase(season))
				.findAny();

		if (selectedCategory.isPresent())
		{
			System.out.println("season selected: " + season);
			selectedCategory.get().click();
		}
		else
		{
			throw new RuntimeException("season not found: " + season);
		}
	}

	public List<Competition> fetchCompetitions()
	{
		List<Competition> competitions = new ArrayList<>();

		By competitionRowSelector= By.cssSelector("#competitions table tbody tr");
		List<WebElement> tableRows =
				wait.until(presenceOfAllElementsLocatedBy(competitionRowSelector));

		System.out.println("found competition tableRows: " + tableRows.size());

		tableRows.forEach(tableRow -> {
			competitions.add(getCompetitionFromTableRow(tableRow));
		});

		System.out.println("processed competitions: " + competitions.size());

		return competitions;
	}

	public Competition getCompetitionFromTableRow(WebElement tableRow)
	{
		Competition competition = new Competition();

		String tagName = tableRow.getTagName();
		if (! tagName.equalsIgnoreCase("tr"))
		{
			throw new RuntimeException("Expecting a tr element, but got: " + tagName);
		}

		competition.dataUID = tableRow.getAttribute("data-uid");

		List<WebElement> tableCells = tableRow.findElements(By.tagName("td"));
		competition.date = tableCells.get(0).getText();
		competition.description = tableCells.get(1).getText();
		competition.link = tableCells.get(1).findElement(By.tagName("a")).getAttribute("href");
		competition.country = tableCells.get(2).getText();
		competition.competitionClass = tableCells.get(3).getText();

		System.out.println("Competition: " + competition.toJson());
		return competition;
	}

	public void printCSV(List<Competition> competitions) throws IOException
	{
		CsvMapper mapper = new CsvMapper();
		mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
		CsvSchema schema = CsvSchema.builder().setUseHeader(true)
				.addColumn("dataUID")
				.addColumn("date")
				.addColumn("description")
				.addColumn("link")
				.addColumn("country")
				.addColumn("competitionClass")
				.build();

		ObjectWriter writer = mapper.writerFor(Competition.class).with(schema);
		System.out.println("CSV: " + writer.toString());

		File csvOutputFile = new File("competitions.csv");

		writer.writeValues(csvOutputFile).writeAll(competitions);

	}
}
