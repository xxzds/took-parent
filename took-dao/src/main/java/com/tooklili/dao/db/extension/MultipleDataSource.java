package com.tooklili.dao.db.extension;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源切换类
 * @author shuai.ding
 * @date 2017年9月11日上午10:21:50
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

	public static void setDataSourceKey(String dataSource) {
		dataSourceKey.set(dataSource);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceKey.get();
	}

}
