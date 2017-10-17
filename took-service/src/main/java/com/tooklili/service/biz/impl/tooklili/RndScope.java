package com.tooklili.service.biz.impl.tooklili;

public class RndScope {
	
	/**
	 * 大于的数
	 */
	private double gteVal;
	/**
	 * 小于的数
	 */
	private double lteVal;

	/**
	 * 构造器
	 * @param length   总个数
	 * @param limit    从总个数中获取的数量
	 */
	public RndScope(long length, int limit) {
		double rndVal = Math.random() * length;
		// 根据要获取的记录的条数，对范围值进行修改
		gteVal = rndVal - limit;
		lteVal = rndVal + limit;
		if (gteVal < 0) {
			lteVal += Math.abs(gteVal);
			gteVal = 0;
		} else if (lteVal > length) {
			lteVal -= (length - lteVal);
			gteVal = length;
		}
		gteVal = gteVal / length;
		lteVal = lteVal / length;
	}

	/**
	 * @return the gteVal
	 */
	public double getGteVal() {
		return gteVal;
	}

	/**
	 * @return the lteVal
	 */
	public double getLteVal() {
		return lteVal;
	}

}
