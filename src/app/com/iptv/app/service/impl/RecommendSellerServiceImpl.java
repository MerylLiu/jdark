package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.RecommendSellerService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RecommendSellerServiceImpl extends BaseServiceImpl implements RecommendSellerService {
	@Override
	public KendoResult getSellersPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("recommendSeller.getSellerPaged", map);
		return data;
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null  || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择商家。");
		}
		
		if(map.get("RecommendCategoryId")==null||map.get("RecommendCategoryId")==""){
			errMsg.add("请选择推荐分类");
		}
		
		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}
		
		List list = (ArrayList)map.get("Id");
		
		List arr = getDao().selectList("recommendSeller.getSellerList");
		
		for(int i = 0;i<arr.size();i++){
			if(list.contains(((HashMap)arr.get(i)).get("Id"))&&
				((HashMap)arr.get(i)).get("RecommendCategoryId").toString().equals((map.get("RecommendCategoryId")).toString())){
				list.remove(((HashMap)arr.get(i)).get("Id"));
			}
		}
		
		for(Object obj:list){
			map.put("Id",obj.toString());
			getDao().insert("recommendSeller.saveSeller", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的商家。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("recommendSeller.deleteSeller", map);
	}

}
