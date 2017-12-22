package com.instrument.consumer.engine;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Month;

import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class CalculateAverageMonthEngine extends AbstractEngine {

	private long quantity;
	private BigDecimal sum;

	public CalculateAverageMonthEngine(final String instrumentName) {
		super(instrumentName);
		sum = BigDecimal.ZERO;
	}

	/**
	 * Calculates the average of a specific instrument in a given date
	 * 
	 */
	@Override
	protected BigDecimal calculate() throws UnsupportedOperationException {
		BigDecimal average = BigDecimal.ZERO;
		if(quantity > 0) average = sum.divide(new BigDecimal(quantity), MathContext.DECIMAL32);
		return average;
	}

	/**
	 * Consumes data from file to perform average for a given instrument in a
	 * specific date
	 * 
	 * @param Set<InstrumentPriceVO>
	 *            The collection of InstrumentPriceVO
	 */
	@Override
	protected void consume(final InstrumentPriceVO instrumentPriceVO) {
		if(filter(instrumentPriceVO)) {
			quantity ++;
			sum = sum.add(instrumentPriceVO.getValue());
		}
	}
	
	private boolean filter(final InstrumentPriceVO instrumentVO) {
		return getInstrumentName().equals(instrumentVO.getName())
				&& Month.NOVEMBER.getValue() == instrumentVO.getDate().getMonthValue()
				&& 2014 == instrumentVO.getDate().getYear();
	}
}
