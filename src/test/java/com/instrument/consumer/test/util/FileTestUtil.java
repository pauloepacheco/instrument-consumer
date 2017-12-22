package com.instrument.consumer.test.util;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import com.instrument.consumer.util.InstrumentPriceUtil;
import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class FileTestUtil {

	private List<InstrumentPriceVO> instrumentPriceVOs = null;
	
	private static final String INSTRUMENT1 ="INSTRUMENT1";
	private static final String INSTRUMENT2 ="INSTRUMENT2";
	private static final String INSTRUMENT3 ="INSTRUMENT3";

	public FileTestUtil() {
		instrumentPriceVOs = new ArrayList<InstrumentPriceVO>();
		instrumentPriceVOs.addAll(getListOfInstrument1());
		instrumentPriceVOs.addAll(getListOfInstrument2());
		instrumentPriceVOs.addAll(getListOfInstrument3());
	}

	public final void generateFile() {
		try {
			createTestFile(instrumentPriceVOs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final BigDecimal getAverageForInstrument1FromFile() {
		BigDecimal sum = getListOfInstrument1().stream().
				filter(f -> InstrumentPriceUtil.isBusinessDay(f.getDate())).
				map(InstrumentPriceVO::getValue).reduce(BigDecimal::add).get();
		
		long count = getListOfInstrument1().stream().filter(f -> InstrumentPriceUtil.isBusinessDay(f.getDate())).count();
		return sum.divide(new BigDecimal(count), MathContext.DECIMAL32);
	}

	public final BigDecimal getAverageForInstrument2InNovember2014FromFile() {

		Predicate<InstrumentPriceVO> dateFilterPredicate = new Predicate<InstrumentPriceVO>() {
			@Override
			public boolean test(InstrumentPriceVO vo) {
				return InstrumentPriceUtil.isBusinessDay(vo.getDate()) && 2014 == vo.getDate().getYear()
						&& Month.NOVEMBER.getValue() == vo.getDate().getMonthValue();
			}
		};

		BigDecimal sum = getListOfInstrument2().stream().
				filter(dateFilterPredicate).map(InstrumentPriceVO::getValue).
				reduce(BigDecimal::add).get();
		
		long count = getListOfInstrument2().stream().filter(dateFilterPredicate).count();
		return sum.divide(new BigDecimal(count), MathContext.DECIMAL32);
	}
	
	public BigDecimal getMaxPriceForInstrument3FromFile() {
		InstrumentPriceVO max = getListOfInstrument3().stream().
				max(Comparator.comparing(InstrumentPriceVO::getValue)).get();
		
		return max.getValue();
	}
	
	private void createTestFile(List<InstrumentPriceVO> instrumentPriceVOs) throws IOException {
		final FileWriter fw = new FileWriter("src/test/resources/fileForTesting.txt");
		try {
			instrumentPriceVOs.stream().forEach(vo -> writeToFile(fw, vo));
		} finally {
			fw.close();
		}
	}

	private void writeToFile(FileWriter fw, InstrumentPriceVO instrumentPriceVO) {
		try {
			fw.write(String.format("%s,%s,%s\n", instrumentPriceVO.getName(),
					instrumentPriceVO.getDate().format(InstrumentPriceUtil.DATE_FORMATTER),
					instrumentPriceVO.getValue()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private List<InstrumentPriceVO> getListOfInstrument1() {
		List<InstrumentPriceVO> instrumentPriceVO1List = new ArrayList<InstrumentPriceVO>();
		instrumentPriceVO1List.add(new InstrumentPriceVO(INSTRUMENT1, LocalDate.of(1996, Month.JANUARY, 1), new BigDecimal(2.12)));
		instrumentPriceVO1List.add(new InstrumentPriceVO(INSTRUMENT1, LocalDate.of(1996, Month.JANUARY, 2), new BigDecimal(1.15)));
		instrumentPriceVO1List.add(new InstrumentPriceVO(INSTRUMENT1, LocalDate.of(1996, Month.JANUARY, 3), new BigDecimal(1.33)));
		instrumentPriceVO1List.add(new InstrumentPriceVO(INSTRUMENT1, LocalDate.of(1996, Month.JANUARY, 4), new BigDecimal(1.48)));
		instrumentPriceVO1List.add(new InstrumentPriceVO(INSTRUMENT1, LocalDate.of(1996, Month.JANUARY, 5), new BigDecimal(2.90)));
		instrumentPriceVO1List.add(new InstrumentPriceVO(INSTRUMENT1, LocalDate.of(1996, Month.JANUARY, 6), new BigDecimal(100)));
		instrumentPriceVO1List.add(new InstrumentPriceVO(INSTRUMENT1, LocalDate.of(1996, Month.JANUARY, 7), new BigDecimal(200)));
		return instrumentPriceVO1List;
	}

	private List<InstrumentPriceVO> getListOfInstrument2() {
		List<InstrumentPriceVO> instrumentPriceVO2List = new ArrayList<InstrumentPriceVO>();
		instrumentPriceVO2List.add(new InstrumentPriceVO(INSTRUMENT2, LocalDate.of(2014, Month.NOVEMBER, 3), new BigDecimal(1.05)));
		instrumentPriceVO2List.add(new InstrumentPriceVO(INSTRUMENT2, LocalDate.of(2014, Month.NOVEMBER, 4), new BigDecimal(1.06)));
		instrumentPriceVO2List.add(new InstrumentPriceVO(INSTRUMENT2, LocalDate.of(2014, Month.NOVEMBER, 5), new BigDecimal(1.33)));
		instrumentPriceVO2List.add(new InstrumentPriceVO(INSTRUMENT2, LocalDate.of(2014, Month.NOVEMBER, 6), new BigDecimal(2.14)));
		instrumentPriceVO2List.add(new InstrumentPriceVO(INSTRUMENT2, LocalDate.of(2014, Month.NOVEMBER, 7), new BigDecimal(2.10)));
		instrumentPriceVO2List.add(new InstrumentPriceVO(INSTRUMENT2, LocalDate.of(2014, Month.NOVEMBER, 8), new BigDecimal(100.10)));
		instrumentPriceVO2List.add(new InstrumentPriceVO(INSTRUMENT2, LocalDate.of(2014, Month.NOVEMBER, 9), new BigDecimal(200.10)));
		return instrumentPriceVO2List;
	}

	private List<InstrumentPriceVO> getListOfInstrument3() {
		List<InstrumentPriceVO> instrumentPriceVO3List = new ArrayList<InstrumentPriceVO>();
		instrumentPriceVO3List.add(new InstrumentPriceVO(INSTRUMENT3, LocalDate.of(2010, Month.JANUARY, 1), new BigDecimal(1.10)));
		instrumentPriceVO3List.add(new InstrumentPriceVO(INSTRUMENT3, LocalDate.of(2010, Month.JANUARY, 2), new BigDecimal(2.15)));
		instrumentPriceVO3List.add(new InstrumentPriceVO(INSTRUMENT3, LocalDate.of(2010, Month.JANUARY, 3), new BigDecimal(3.15)));
		instrumentPriceVO3List.add(new InstrumentPriceVO(INSTRUMENT3, LocalDate.of(2010, Month.JANUARY, 4), new BigDecimal(4.15)));
		instrumentPriceVO3List.add(new InstrumentPriceVO(INSTRUMENT3, LocalDate.of(2010, Month.JANUARY, 5), new BigDecimal(5.15)));
		return instrumentPriceVO3List;
	}
}
