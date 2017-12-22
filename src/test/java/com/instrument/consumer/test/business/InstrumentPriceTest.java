package com.instrument.consumer.test.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class InstrumentPriceTest {

	@Test
    public void newInstrumentPriceVOInstanceWhenUsingMultiply() {
		
        InstrumentPriceVO instPriceVO1 = new InstrumentPriceVO("INSTRUMENT1", LocalDate.now(), BigDecimal.ONE);
        InstrumentPriceVO instPriceVO2 = instPriceVO1.multiply(BigDecimal.ONE);
        assertThat(instPriceVO2).isNotSameAs(instPriceVO1).isEqualToComparingFieldByField(instPriceVO1);
        assertThat(instPriceVO2).isNotSameAs(instPriceVO1);

        InstrumentPriceVO instPriceVO3 = instPriceVO1.multiply(BigDecimal.TEN);
        assertThat(instPriceVO3.getName()).isEqualTo(instPriceVO1.getName());
        assertThat(instPriceVO3.getDate()).isEqualTo(instPriceVO1.getDate());
        assertThat(instPriceVO3.getValue()).isEqualTo(BigDecimal.ONE.multiply(BigDecimal.TEN));
    }
}