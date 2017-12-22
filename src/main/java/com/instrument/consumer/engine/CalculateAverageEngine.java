package com.instrument.consumer.engine;

import java.math.BigDecimal;
import java.math.MathContext;

import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class CalculateAverageEngine extends AbstractEngine {

	private long quantity;
	private BigDecimal sum;

	public CalculateAverageEngine(String instrumentName) {
		super(instrumentName);
		sum = BigDecimal.ZERO;
	}
	
	
//	@Override
//	protected void consume(Set<InstrumentPriceVO> instrumentPrices) {
//		double sum = instrumentPrices.stream().filter(i -> getInstrumentName().equals(i.getName()))
//				.mapToDouble(InstrumentPriceVO::getValue).sum();
//
//	}

	/**
	 * Calculates the average for a given instrument
	 * 
	 */
	@Override
	protected BigDecimal calculate() {
		BigDecimal average = BigDecimal.ZERO;
		if(quantity > 0) average = sum.divide(new BigDecimal(quantity), MathContext.DECIMAL32);
		return average;
	}
	
	/**
	 * Consumes data from file to perform average for a given instrument
	 * 
	 * @param InstrumentPriceVO The Instrument Price VO
	 */
	@Override
	protected void consume(InstrumentPriceVO instrumentPriceVO) {
		if(getInstrumentName().equals(instrumentPriceVO.getName())) {
			quantity++;
			sum = sum.add(instrumentPriceVO.getValue());
		}
	}
}
