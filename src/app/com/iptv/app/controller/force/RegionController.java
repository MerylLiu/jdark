package com.iptv.app.controller.force;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iptv.app.service.RegionService;
import com.iptv.core.common.KendoResult;
import com.iptv.core.controller.BaseController;
import com.iptv.core.utils.BaseUtil;

@Controller
@RequestMapping("/region")
@SuppressWarnings({"rawtypes","unchecked"})
public class RegionController extends BaseController{
	@Resource
	private RegionService regionService;

	@RequestMapping(value="/getRegions",method = RequestMethod.GET)
	public @ResponseBody KendoResult getRegions(HttpServletRequest request,HttpServletResponse response){
		Map param = BaseUtil.getParameterMap(request);

		if(param.get("parentId") == null){
			param.put("parentId", 1);
		}

		List data = regionService.getRegionsByParentId(param);
		return new KendoResult(data);
	}
}
