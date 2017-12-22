package com.instrument.consumer.engine;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.util.StringUtils;

import com.instrument.consumer.util.InstrumentPriceUtil;
import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class CalculationModule {

	/** The variable to hold all supported engines */
	private Map<String, AbstractEngine> SUPPORTED_ENGINES = null;

	private String filePath = null;

	public CalculationModule(String filePath) {
		this.filePath = filePath;
		SUPPORTED_ENGINES = new HashMap<String, AbstractEngine>();
	}

	/**
	 * Adds the module to the SUPPORTED_ENGINES
	 * 
	 * @param engine The subclass module of AbstractEngine
	 * @return The CalculationModule
	 */
	public CalculationModule addModule(AbstractEngine engine) {
		addEngine(engine);
		return this;
	}

	/**
	 * Consumes data from file for all supported engines
	 * 
	 * @param instrumentPriceVO The Instrument Price VO
	 *            
	 */
	private void consume(InstrumentPriceVO instrumentPriceVO) {
		
		SUPPORTED_ENGINES.forEach((k, v) -> v.consume(instrumentPriceVO));
	}
	

	/**
	 * Calculates the price of a given instrument
	 * 
	 * @param engine the Engine
	 * @return the calculated value for the specified engined
	 */
	public BigDecimal calculate(AbstractEngine engine) {
		return SUPPORTED_ENGINES.get(engine.getInstrumentName()).calculate();
		
	}
	/**
	 * Initiates process
	 * 
	 */
	public void run() {
		loadFile(new File(filePath));
	}

	/**
	 * Prints statistics in the console for all supported engines
	 */
	public void printStatistics() {
		Map<String, AbstractEngine> filteredModules = SUPPORTED_ENGINES.entrySet().stream()
				.filter(p -> null != p.getKey()).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		
		filteredModules.forEach((k, v) -> System.out.println(k + ":" + v.calculate()));
	}

	/**
	 * Adds engine to the SUPPORTED_ENGINES
	 * 
	 * @param module The module to be added
	 */
	private void addEngine(AbstractEngine module) {
		SUPPORTED_ENGINES.put(module.getInstrumentName(), module);
	}
	
	// TODO: move this to a different module and call the supported engines via
	// reflection to keep high cohesion and loose coupling
	private void loadFile(File file) {
		LineIterator iterator = null;
		try {
			iterator = FileUtils.lineIterator(file, "UTF-8");
			while (iterator.hasNext()) {
				String row = iterator.nextLine();
				if (!StringUtils.isEmpty(row)) {
					InstrumentPriceVO instrumentPriceVO = InstrumentPriceUtil.parseFromFile(row);
					if (instrumentPriceVO != null) {
						consume(instrumentPriceVO);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LineIterator.closeQuietly(iterator);
		}
	}
}
