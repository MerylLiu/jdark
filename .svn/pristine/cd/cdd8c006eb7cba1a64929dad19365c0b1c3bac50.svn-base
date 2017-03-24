package com.iptv.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.RegionService;
import com.iptv.core.service.impl.BaseServiceImpl;

@Service
@SuppressWarnings("rawtypes")
public class RegionServiceImpl extends BaseServiceImpl implements RegionService{

	@Override
	public List getRegions() {
		List data = getDao().selectList("region.getRegions");
		return data;
	}

	@Override
	public List getRegionsByParentId(Map map) {
		List data = getDao().selectList("region.getRegionsByParentId",map);
		return data;
	}
}
