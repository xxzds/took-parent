package com.tooklili.service.biz.impl.admin.took;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tooklili.dao.db.intf.admin.TookItemSelectDao;
import com.tooklili.model.tooklili.TookItemSelect;
import com.tooklili.service.biz.intf.admin.took.ItemSelectService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.DateUtil;
import com.tooklili.util.result.PlainResult;

/**
 * 人工选择的商品服务
 * @author ding.shuai
 * @date 2018年6月24日下午4:35:04
 */
@Service
public class ItemSelectServiceImpl implements ItemSelectService{
	
	@Resource
	private TookItemSelectDao tookItemSelectDao;

	@Override
	public PlainResult<String> insertOrUpdate(List<TookItemSelect> tookItemSelects) {
		PlainResult<String> result = new PlainResult<String>();
		
		Integer insertCount = 0;
		Integer notOperateCount = 0;
		
		for(TookItemSelect tookItemSelect:tookItemSelects) {
			Long numIid = tookItemSelect.getNumIid();
			TookItemSelect tookItemSelect2 = tookItemSelectDao.queryItemByNumiid(numIid);
			
			if(tookItemSelect2 == null) {
				//添加时间
				tookItemSelect.setAddTime(DateUtil.formatDate(new Date()));
				//开始时间
				tookItemSelect.setCouponStartTime(DateUtil.formatDate(new Date()));
				//结束时间
				tookItemSelect.setCouponEndTime(DateUtil.formatDay(DateUtil.after(15,TimeUnit.DAYS)));
				tookItemSelectDao.insertSelective(tookItemSelect);
				insertCount++;
			}else {
				notOperateCount++;
			}
		}
		result.setData("采集商品成功,录入"+insertCount+"个，已入库"+notOperateCount+"个");		
		return result;
	}

	@Override
	public PlainResult<Integer> delExpiredItems() {
		PlainResult<Integer> result = new PlainResult<Integer>();
		int count = tookItemSelectDao.delExpiredItems();
		if(count<0) {
			throw new BusinessException("删除定向采集的商品失败");
		}
		result.setData(count);
		return result;
	}

}
