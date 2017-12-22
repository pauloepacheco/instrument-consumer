package com.instrument.consumer.main;

import static com.instrument.consumer.util.InstrumentPriceUtil.buildDBConfiguration;

import java.io.File;
import java.io.IOException;

import com.instrument.consumer.engine.CalculateAverageEngine;
import com.instrument.consumer.engine.CalculateAverageMonthEngine;
import com.instrument.consumer.engine.CalculateMaxPriceEngine;
import com.instrument.consumer.engine.CalculationModule;
import com.instrument.consumer.engine.MultiplierEngine;
import com.instrument.consumer.util.GuavaCache;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class MainApp {

	public static void main(String[] args) throws IOException {
		//File file = new File("/Users/Eduardo/hugeFileForTesting.txt");
		File file = new File("src/main/resources/files/example_input.txt");
		String filePath = file.getAbsolutePath();
		new MainApp().run(filePath);
		
	}
	
	private void run(String filePath) throws IOException {

		MultiplierEngine multiplierEngine = null;
		try {
			CalculationModule calculationModule = new CalculationModule(filePath);
			multiplierEngine = new MultiplierEngine(new GuavaCache(buildDBConfiguration()));
	
			calculationModule.addModule(new CalculateAverageEngine("INSTRUMENT1"))
					.addModule(new CalculateAverageMonthEngine("INSTRUMENT2"))
					.addModule(new CalculateMaxPriceEngine("INSTRUMENT3"))
					.addModule(multiplierEngine).run();
	
			calculationModule.printStatistics();
			
		} finally {
			if(multiplierEngine != null)
				multiplierEngine.close();
		}
	}
}
