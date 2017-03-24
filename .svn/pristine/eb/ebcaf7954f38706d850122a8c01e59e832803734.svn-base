package com.iptv.app.controller.tv;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iptv.app.service.RegionService;
import com.iptv.core.common.KendoResult;
import com.iptv.core.controller.BaseController;

@Controller
@RequestMapping("/region")
@SuppressWarnings({"rawtypes"})
public class RegionController extends BaseController{
	@Resource
	private RegionService regionService;

	@RequestMapping(value="/getRegions",method = RequestMethod.GET)
	public @ResponseBody KendoResult getRegions(@RequestParam Map<String,Object> param){
		if(param.get("parentId") == null){
			param.put("parentId", 1);
		}

		List data = regionService.getRegionsByParentId(param);
		return new KendoResult(data);
	}
}
