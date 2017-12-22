package com.instrument.consumer.engine;

import java.math.BigDecimal;

import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public abstract class AbstractEngine {
	
	private String instrument;
	
	public AbstractEngine() {}
	
	public AbstractEngine(String instrument) {
		this.instrument = instrument;
	}
	
	/**
	 * Gets the instrument name
	 * @return the instrument name
	 */
	protected String getInstrumentName() {
		return this.instrument;
	}
	
	/**
	 * Abstract method to consume data from file
	 * @param instrumentPriceVO the InstrumentPriceVO Object
	 */
	protected abstract void consume(InstrumentPriceVO instrumentPriceVO);
	
	/**
	 * Abstract method to calculate data from file
	 * @return the calculated value for the specified engine
	 */
	protected abstract BigDecimal calculate();
	
}
