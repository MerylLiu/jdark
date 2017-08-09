package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.RecommendVideoService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RecommendVideoServiceImpl extends BaseServiceImpl implements RecommendVideoService {
	@Override
	public KendoResult getVideosPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("recommendVideo.getVideoPaged", map);
		return data;
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null  || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择视频。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}
		
		List list = (ArrayList)map.get("Id");
		
		List arr = getDao().selectList("recommendVideo.getVideoList");
		
		for(int i = 0;i<arr.size();i++){
			if(list.contains(((HashMap)arr.get(i)).get("Id"))){
				list.remove(((HashMap)arr.get(i)).get("Id"));
			}
		}
		
		for(Object obj:list){
			getDao().insert("recommendVideo.saveVideo", obj.toString());
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的视频。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("recommendVideo.deleteVideo", map);
	}

}
