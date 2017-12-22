package com.instrument.consumer.test.business;

import static com.instrument.consumer.util.InstrumentPriceUtil.buildDBConfiguration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.instrument.consumer.engine.CalculateAverageEngine;
import com.instrument.consumer.engine.CalculateAverageMonthEngine;
import com.instrument.consumer.engine.CalculateMaxPriceEngine;
import com.instrument.consumer.engine.CalculationModule;
import com.instrument.consumer.engine.MultiplierEngine;
import com.instrument.consumer.test.util.FileTestUtil;
import com.instrument.consumer.util.GuavaCache;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class CalculationEngineTest {
	
	private static CalculationModule calculationModule = null;
	private static FileTestUtil fileTest = null;
	
	@Before
	public final void setUp() throws IOException {
		fileTest = new FileTestUtil();
		fileTest.generateFile();
		calculationModule = new CalculationModule("src/test/resources/fileForTesting.txt");
	}

	@Test
	public final void calculateAverageForInstrument1() {
		CalculateAverageEngine averageEngine = new CalculateAverageEngine("INSTRUMENT1");
		calculationModule.addModule(averageEngine).run();
		assertEquals(fileTest.getAverageForInstrument1FromFile(), calculationModule.calculate(averageEngine));
	}
	
	@Test
	public final void calculateAverageForInstrument2InNovemberOf2014() {
		CalculateAverageMonthEngine averageMonthEngine = new CalculateAverageMonthEngine("INSTRUMENT2");
		calculationModule.addModule(averageMonthEngine).run();
		assertEquals(fileTest.getAverageForInstrument2InNovember2014FromFile(), calculationModule.calculate(averageMonthEngine));
	}
	
	@Test
	public final void calculateMaxPriceForInstrument3() {
		CalculateMaxPriceEngine maxPriceEngine = new CalculateMaxPriceEngine("INSTRUMENT3");
		calculationModule.addModule(maxPriceEngine).run();
		assertEquals(fileTest.getMaxPriceForInstrument3FromFile(), calculationModule.calculate(maxPriceEngine));
	}
	
	@Test
	public final void calculateMultiplierAndExportFile() throws IOException {
		File file = new File("multiplierResults.txt");
		file.delete();
		try {
			MultiplierEngine multiplierEngine = new MultiplierEngine(new GuavaCache(buildDBConfiguration()));
			calculationModule.addModule(multiplierEngine).run();
			multiplierEngine.close();
			assertTrue(file.exists() && file.length() > 0);
		} finally {
			file.delete();
		}
	}
}
