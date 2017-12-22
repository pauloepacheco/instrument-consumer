package com.instrument.consumer.engine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import com.instrument.consumer.util.FileFlusher;
import com.instrument.consumer.util.GuavaCache;
import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class MultiplierEngine extends AbstractEngine {

	private FileFlusher flusher;
	private GuavaCache multiplierCache;

	public MultiplierEngine(GuavaCache multiplierCache) throws FileNotFoundException {
		this.multiplierCache = multiplierCache;
		flusher = new FileFlusher(new FileOutputStream("multiplierResults.txt"));
	}

	/**
	 * Consumes data from file and flush them to disk
	 * 
	 * @param InstrumentPriceVO The Instrument Price VO
	 *            
	 */
	@Override
	protected void consume(InstrumentPriceVO instrumentPriceVO) {
		flusher.write(multiplierCache.getMultuplier(instrumentPriceVO.getName()).map(instrumentPriceVO::multiply)
				.orElse(instrumentPriceVO));
	}

	public void close() {
		flusher.close();
	}

	@Override
	protected BigDecimal calculate() {
		throw new UnsupportedOperationException("calculate not supported in MultiplierEngine");
	}
}
