package com.iptv.app.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;
@SuppressWarnings("rawtypes")
public interface MaterialService extends BaseService{

	public KendoResult getMaterialListPaged(Map param);

	public Map getMaterialById(Map param);

	public void saveMaterial(Map map) throws BizException;

	public void doDeleteMaterial(Map map) throws BizException;

}
