package com.instrument.consumer.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class InstrumentPriceVO{

	private String name;
	private LocalDate date;
	private BigDecimal value;
	
	public InstrumentPriceVO(String name, LocalDate date, BigDecimal value) {
		super();
		this.name = name;
		this.date = date;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	 public InstrumentPriceVO multiply(BigDecimal multiplier) {
	        return new InstrumentPriceVO(name, date, multiplier != null ? multiplier.multiply(value) : BigDecimal.ZERO);
	    }
	
	@Override
	public String toString() {
		return "InstrumentPrice [name=" + name + ", date=" + date + ", value=" + value + "]";
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstrumentPriceVO)) return false;

        InstrumentPriceVO instrumentPriceVO = (InstrumentPriceVO) o;
        return name.equals(instrumentPriceVO.name) && date.equals(instrumentPriceVO.date);

    }

	@Override
    public int hashCode() {
        int result = name.hashCode();
        result = 53 * result + date.hashCode();
        return result;
    }
}
