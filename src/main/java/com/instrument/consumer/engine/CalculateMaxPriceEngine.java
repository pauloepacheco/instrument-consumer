package com.instrument.consumer.engine;

import java.math.BigDecimal;

import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class CalculateMaxPriceEngine extends AbstractEngine {
	
	BigDecimal maxPrice;

	public CalculateMaxPriceEngine(String instrumentName) {
		super(instrumentName);
		maxPrice = BigDecimal.ZERO;
	}
	
	/**
	 * Consumes data from file and flush it to disk
	 * 
	 * @param Set<InstrumentPriceVO> The collection of InstrumentPriceVO
	 */
	@Override
	protected void consume(InstrumentPriceVO instrumentPriceVO) {
//		OptionalDouble maxValue = instrumentPrices.stream().filter(p -> super.getInstrumentName().equals(p.getName()))
//				.mapToDouble(InstrumentPriceVO::getValue).max();
//		
//		if(maxValue.isPresent()) {
//			if(maxValue.getAsDouble() > maxPrice)
//				maxPrice = maxValue.getAsDouble();
//		}
		if(getInstrumentName().equals(instrumentPriceVO.getName())){
			BigDecimal value = instrumentPriceVO.getValue() != null ? instrumentPriceVO.getValue() : BigDecimal.ZERO;
			if(value.compareTo(maxPrice) == 1)
				maxPrice = instrumentPriceVO.getValue();
		}
	}

	/**
	 * Calculates the highest price found in the input file
	 * 
	 */
	@Override
	protected BigDecimal calculate(){
		return maxPrice;
	}
}
