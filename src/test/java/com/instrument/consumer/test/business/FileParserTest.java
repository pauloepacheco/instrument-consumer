package com.instrument.consumer.test.business;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import com.instrument.consumer.util.InstrumentPriceUtil;
import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class FileParserTest{
	
	@Test
	public final void returnsNullForBadDataFormat() {
		assertTrue(InstrumentPriceUtil.parseFromFile("INSTRUMENT1,10-Jan-2012,1.786") != null);
		assertEquals(null, InstrumentPriceUtil.parseFromFile(",10-Jan-2012,1.786"));
		assertEquals(null, InstrumentPriceUtil.parseFromFile("INSTRUMENT1,,1.786"));
		assertEquals(null, InstrumentPriceUtil.parseFromFile("INSTRUMENT1,10-Jan-2012,"));
		assertEquals(null, InstrumentPriceUtil.parseFromFile("INVALID,01-Jan-1996,lsakdj"));
		assertEquals(null, InstrumentPriceUtil.parseFromFile("INVALID,jjkkj,lsakdj"));
	}
	
	@Test 
	public final void onlyBusinessDateAllowed() {
		
		LocalDate limitDate = LocalDate.of(2014, Month.DECEMBER, 19);
		InstrumentPriceVO instPriceVO1LimitDate = new InstrumentPriceVO("INSTRUMENT1", limitDate, BigDecimal.ONE);
		assertTrue(InstrumentPriceUtil.isBusinessDay(instPriceVO1LimitDate.getDate()));
		
		LocalDate afterLimitDate = LocalDate.of(2014, Month.DECEMBER, 20);
		InstrumentPriceVO instPriceVO1AterDate = new InstrumentPriceVO("INSTRUMENT1", afterLimitDate, BigDecimal.ONE);
		assertFalse(InstrumentPriceUtil.isBusinessDay(instPriceVO1AterDate.getDate()));
		
		LocalDate weekendDate = LocalDate.of(2017, Month.FEBRUARY, 19);
		InstrumentPriceVO instPriceVO1WeekendDate = new InstrumentPriceVO("INSTRUMENT1", weekendDate, BigDecimal.ONE);
		assertFalse(InstrumentPriceUtil.isBusinessDay(instPriceVO1WeekendDate.getDate()));
	}
}
