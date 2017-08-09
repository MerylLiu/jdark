package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.HotspotService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HotspotServiceImpl extends BaseServiceImpl implements HotspotService {

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("VersionId") == null) {
			errMsg.add("请输入版本号。");
		}
		if (map.get("HotspotType") == null) {
			errMsg.add("请输入热点类型。");
		}
		if (map.get("Title") == null) {
			errMsg.add("请输入标题。");
		}
		if (map.get("Province") == null) {
			errMsg.add("请选择省。");
		}
		if (map.get("City") == null) {
			errMsg.add("请选择市。");
		}
		if (map.get("Area") == null) {
			errMsg.add("请选择区／县。");
		}
		
		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}
		
		if(map.get("ActivityImages")!=null && map.get("ActivityImages") instanceof java.util.List){
			List list = (List) map.get("ActivityImages");
			String str = "";
			for(int i = 0;i<list.size();i++){
				str += list.get(i).toString();
				if(i != (list.size()-1)){
					str += '|';
				}
			}
			map.put("ActivityImages", str);
		}
		
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			map.put("CreateDate", DateUtil.getNow());
			getDao().insert("hotspot.saveHotspot",map);
		} else {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().update("hotspot.updateHotspot", map);
		}
		
		
		
		int Id = Integer.parseInt(map.get("Id").toString());
		Map b = new HashMap();
		b.put("HotspotId", Id);
		
		getDao().delete("hotspot.deleteHotspotVideo",b);
		
		if(map.get("VideoId")!=null){
			List list = (List)map.get("VideoId");
			for(Object obj:list){
				b.put("VideoId", obj.toString());
				getDao().insert("hotspot.saveHotspotVideo",b);
			}
		}
	}

	@Override
	public KendoResult getHotspotPaged(Map map) {
		KendoResult kr = QueryUtil.getRecordsPaged("hotspot.getHotspotPaged", map);
		return kr;
	}

	@Override
	public Map getHotspot(Map map) {
		Map data = getDao().selectOne("hotspot.getHotspot",map);
		return data;
	}

	@Override
	public List getVideoIdByHotspotId(Map map) {
		List list = getDao().selectList("hotspot.getVideoIdByHotspotId", map);
		return list;
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的热点话题。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}
		
		getDao().delete("hotspot.deleteHotspotVideos", map);
		getDao().delete("hotspot.deleteHotspot", map);
		
	}

	@Override
	public Map getHomeHotspot() {
		List recommend = getDao().selectList("hotspot.getHomeRecommend");
		List topic = getDao().selectList("hotspot.getHomeTopic");
		List special = getDao().selectList("hotspot.getHomeSpecial");
		
		Map res = new HashMap();
		res.put("recommend", recommend);
		res.put("topic", topic);
		res.put("special", special);

		Map map = new HashMap();
		map.put("page", 1);
		map.put("pageSize", 5);
		Integer pageNum = getDao().selectOne("hotspot.getTopicPageNum",map);
		res.put("pageNum",pageNum);
		
		
		Integer offset = (2 - 2) * 5 + 1;
		Integer rows = 1;
		Map param = new HashMap();
		param.put("offset", offset);
		param.put("rows", rows);
		Map nextTopic = getDao().selectOne("hotspot.getTopicByPage", param);
		
		res.put("nextTopic", nextTopic);
		
		return res;
	}

	@Override
	public Map getTopicByPage(Map map) {
		KendoResult kendoResult = new KendoResult();
		
		Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
		Integer offset = (Integer.valueOf(map.get("page").toString()) - 2) * pageSize + 1;
		
		Map param = new HashMap();
		param.put("offset", offset);
		param.put("rows", pageSize);

		List list = getDao().selectList("hotspot.getTopicByPage", param);
		kendoResult.setData(list);

		Integer pageNum = getDao().selectOne("hotspot.getTopicPageNum", param);
		kendoResult.setPageNum(pageNum);
	
		Map res = new HashMap();
		res.put("CurrentPage", kendoResult);

		Integer offsetNext = (Integer.valueOf(map.get("page").toString()) - 1) * pageSize + 1;
		param.put("offset", offsetNext);
		List nextPagelist = getDao().selectList("hotspot.getTopicByPage", param);
		if(nextPagelist !=null && nextPagelist.size() >0){
			res.put("NextPagePreview", nextPagelist.get(0));
		}

		return res;
	}

	@Override
	public List getHotspotVideos(Map map) {
		List data = getDao().selectList("hotspot.getHotspotVideos",map);
		return data;
	}
}
