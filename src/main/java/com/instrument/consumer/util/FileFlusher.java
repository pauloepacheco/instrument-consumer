package com.instrument.consumer.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import com.instrument.consumer.vo.InstrumentPriceVO;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class FileFlusher {

	private Writer writer;

	public FileFlusher(OutputStream outputStream) {
		writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
	}

	/**
	 * Close the output stream
	 * 
	 */
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Flush data to disk
	 * 
	 * @param instrumentVO
	 *            the InstrumentPriceVO object
	 */
	public void write(InstrumentPriceVO instrumentVO) {
		try {
			String row = String.format("%s,%s,%s\n", instrumentVO.getName(), instrumentVO.getDate(),
					instrumentVO.getValue());
			writer.append(row);
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
	}
}
