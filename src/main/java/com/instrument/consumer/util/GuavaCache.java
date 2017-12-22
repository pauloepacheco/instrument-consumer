package com.instrument.consumer.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 
 * @author Paulo Pacheco
 *
 */
public class GuavaCache {

	private DataSource dataSource;
	
	public GuavaCache(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Gets the multiplier value based on caching mechanism.
	 * 
	 */
	private LoadingCache<String, Optional<BigDecimal>> multiplierCache = CacheBuilder.newBuilder()
			.expireAfterWrite(5, TimeUnit.SECONDS).build(new CacheLoader<String, Optional<BigDecimal>>() {
				public Optional<BigDecimal> load(String instrument) throws Exception {
					return find(instrument);
				}
			});

	private Optional<BigDecimal> find(String instrumentName) {
		List<BigDecimal> multipliers = new JdbcTemplate(dataSource).query(
				"SELECT MULTIPLIER FROM INSTRUMENT_PRICE_MODIFIER WHERE NAME = ?", new Object[] { instrumentName },
				(resultSet, i) -> resultSet.getBigDecimal("MULTIPLIER"));
		return multipliers.isEmpty() ? Optional.empty() : Optional.of(multipliers.get(0));
	}

	public Optional<BigDecimal> getMultuplier(String instrument) {
		return multiplierCache.getUnchecked(instrument);
	}
}
