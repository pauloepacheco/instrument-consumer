package com.instrument.consumer.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import com.google.common.io.Files;
import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class InstrumentPriceUtil {

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

	/**
	 * Constructs InstrumentPriceVO object for each line of the file
	 * 
	 * @param line the file line
	 * @return the InstrumentPriceVO
	 */
	public static InstrumentPriceVO parseFromFile(final String line) {
		if (!StringUtils.isEmpty(line)) {
			try {
				String lineArr[] = line.trim().split("\\s*,\\s*");
				if (lineArr.length == 3 && !lineArr[0].isEmpty() && !lineArr[1].isEmpty() && !lineArr[2].isEmpty()) {
					if (isBusinessDay(LocalDate.parse(lineArr[1], DATE_FORMATTER))) {
						return new InstrumentPriceVO(lineArr[0], LocalDate.parse(lineArr[1], DATE_FORMATTER),
							new BigDecimal(lineArr[2]));
					}
				}
			}catch (Exception e) {
				//TODO: LOG
			}
		}
		return null;
	}

	/**
	 * Initiates configuration for HSQL database
	 * 
	 * @return
	 * @throws IOException
	 */
	public static DataSource buildDBConfiguration() throws IOException {

		// there is no need to persist data, so in memory approach is used here
		final String dbURL = "jdbc:h2:mem:creditsuissetask;DB_CLOSE_DELAY=-1";
		final File sqlScriptFile = new File("src/main/resources/db/create-db.sql");
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(dbURL);
		new JdbcTemplate(dataSource).execute(Files.asCharSource(sqlScriptFile, Charset.forName("UTF-8")).read());

		return dataSource;
	}

	/**
	 * Validates whether the date is a valid business day
	 * 
	 * @param date the data to be validated
	 * @return true if it's a business day
	 */

	public static boolean isBusinessDay(LocalDate date) {
		LocalDate currentDate = LocalDate.of(2014, Month.DECEMBER, 19);
		return date.getDayOfWeek().ordinal() < 5 && !date.isAfter(currentDate);
	}
}
