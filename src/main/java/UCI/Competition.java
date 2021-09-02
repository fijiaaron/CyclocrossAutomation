package UCI;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class Competition
{
	public String dataUID;
	public String date;
	public String description;
	public String link;
	public String country;
	public String competitionClass;

	public String toJson()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

	public static String toCSV(List<Competition> competitionList)
			throws Exception
	{
		Writer writer = new StringWriter();

		MappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
		mappingStrategy.setType(Competition.class);

		StatefulBeanToCsv beanWriter =  new StatefulBeanToCsvBuilder(writer).withMappingStrategy(mappingStrategy)
				.withMappingStrategy(mappingStrategy)
				.withSeparator(',')
				.withQuotechar('\'')
				.build();

		beanWriter.write(competitionList);

		return writer.toString();
	}
}
