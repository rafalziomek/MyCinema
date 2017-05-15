package pl.cinema.model;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DateFormat {
	@Value("${cinema.dataformat}")
    private String dataFormat;
	
	@Bean
	public String getDateFormat() {
		return dataFormat;
	}
	
	@Bean 
	public DateTimeFormatter dateTimeFormatter() {
		return DateTimeFormatter.ofPattern(dataFormat);
	}
	
}
