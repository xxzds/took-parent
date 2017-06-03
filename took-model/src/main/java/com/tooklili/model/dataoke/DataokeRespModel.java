package com.tooklili.model.dataoke;

import java.util.List;

/**
 * 大淘客响应模型
 * @author shuai.ding
 *
 * @date 2017年5月31日下午4:54:14
 */
public class DataokeRespModel {
	 
	private SummaryData data;
	
	private List<Goods> result;

	public SummaryData getData() {
		return data;
	}

	public void setData(SummaryData data) {
		this.data = data;
	}

	public List<Goods> getResult() {
		return result;
	}

	public void setResult(List<Goods> result) {
		this.result = result;
	}
}