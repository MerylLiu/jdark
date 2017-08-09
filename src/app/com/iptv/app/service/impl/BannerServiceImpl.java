package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.BannerService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BannerServiceImpl extends BaseServiceImpl implements BannerService {

	@Override
	public KendoResult getBannerPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("banner.getBannerPaged", map);
		return data;
	}

	@Override
	public Map getBanner(Integer id) {
		Map data = getDao().selectOne("banner.getBannerById", id);
		return data;
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();
		
		if (map.get("Title") == null||map.get("Title").toString().trim().isEmpty()) {
			errMsg.add("请输入广告图标题。");
		}
		if (map.get("ImageUrl") == null){
			errMsg.add("请选择图片路径。");
		}
		if (map.get("Province") == null) {
			errMsg.add("请输入所在的省。");
		}
		if (map.get("City") == null) {
			errMsg.add("请输入所在的市。");
		}
		if (map.get("Area") == null) {
			errMsg.add("请选择所在的区／县。");
		}
		
		if (map.get("CategoryId") == null) {
			errMsg.add("请选择分类。");
		}
		
		if (map.get("Status") == null) {
			errMsg.add("请选择广告图状态。");
		}
		
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().insert("banner.saveBanner", map);
		} else {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().update("banner.updateBanner", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的广告图。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("banner.deleteBanner", map);
	}

	@Override
	public List getBannerByArea(Map map) {
		List data = getDao().selectList("banner.getBannerListByArea",map);
		return data;
	}

}
