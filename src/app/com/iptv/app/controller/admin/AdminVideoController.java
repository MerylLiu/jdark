package com.iptv.app.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.AxisFault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.app.service.CSPRequestServiceStub;
import com.iptv.app.service.CSPRequestServiceStub.CSPResult;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmd;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmdResponse;
import com.iptv.app.service.CategoryService;
import com.iptv.app.service.RegionService;
import com.iptv.app.service.SellerService;
import com.iptv.app.service.VideoService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.SysParamService;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.JsonUtil;

@Controller
@RequestMapping("/admin/video")
@SuppressWarnings({"rawtypes","unchecked"})
public class AdminVideoController extends AdminBaseController {
	@Resource
	private SysParamService sysParamService;
	@Resource
	private RegionService regionService;
	@Resource
	private SellerService sellerService;
	@Resource
	private VideoService videoService;
	@Resource
	private CategoryService categoryService;

	
	@RequestMapping("/index")
	public ModelAndView index(){
		Map res = new HashMap();

		List province = regionService.getRegions();
		res.put("province", JsonUtil.getJson(province));

		KendoResult sellers = sellerService.getAllSellers();
		res.put("sellers", JsonUtil.getJson(sellers.getData()));

		List category = categoryService.getAllCategories();
		res.put("category", JsonUtil.getJson(category));
		
		List status = BaseUtil.getSysParam("VideoStatus");
		res.put("status", JsonUtil.getJson(status));

		List cost = BaseUtil.getSysParam("VideoCost");
		res.put("cost", JsonUtil.getJson(cost));

		List costFZ = BaseUtil.getSysParam("VideoCostFZ");
		res.put("costFZ", JsonUtil.getJson(costFZ));

		List videoStyle = BaseUtil.getSysParam("VideoStyle");
		res.put("videoStyle", JsonUtil.getJson(videoStyle));

		return view("/admin/video/index",res);
	}
	
	@RequestMapping(value="/sellerList",method = RequestMethod.GET)
	public @ResponseBody KendoResult sellerList(HttpServletRequest request,HttpServletResponse response){
		KendoResult data = sellerService.getAllSellers();
		return data;
	}

	@RequestMapping(value="/videoList",method = RequestMethod.GET)
	public @ResponseBody KendoResult videoList(HttpServletRequest request,HttpServletResponse response){
		Map param = BaseUtil.getParameterMap(request);
		KendoResult data = videoService.getVideosPaged(param);

		return data;
	}

	@RequestMapping(value="/getVideo",method = RequestMethod.GET)
	public @ResponseBody Map getVideo(HttpServletRequest request,HttpServletResponse response){
		Map param = BaseUtil.getParameterMap(request);
		Map data = videoService.getVideo(Integer.valueOf(param.get("Id").toString()));

		return data;
	}
		
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public @ResponseBody Map save(@RequestBody Map map){
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			videoService.save(map);
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("错误信息：" + ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		}else{
			res.put("result", true);
			res.put("message", "保存成功。");
		}

		log.info("添加或者修改商家");
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Map delete(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			videoService.delete(map);
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("错误信息：" + ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		}else{
			res.put("result", true);
			res.put("message", "删除成功。");
		}

		log.info("删除商家");
		return res;
	}

	@RequestMapping(value = "/online", method = RequestMethod.POST)
	public @ResponseBody Map online(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			videoService.online(map);
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("错误信息：" + ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		}else{
			res.put("result", true);
			res.put("message", "上线成功。");
		}

		log.info("视频上线");
		return res;
	}

	@RequestMapping(value = "/offline", method = RequestMethod.POST)
	public @ResponseBody Map offline(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			videoService.offline(map);
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("错误信息：" + ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		}else{
			res.put("result", true);
			res.put("message", "下线成功。");
		}

		log.info("视频下线");
		return res;
	}
	
	

	/*
	 * ------------------------------------------------------------------------------
	 * */
	@RequestMapping("/video")
	public ModelAndView callApi(HttpServletRequest req) {
		log.info("访问Controller:admin/video");
		sysParamService.saveLog("访问Controller:admin/video");
		return view("admin/video");
	}

	@RequestMapping(value = "/pubVideo", method = RequestMethod.POST)
	public @ResponseBody CSPResult pubVideo(@RequestBody Map map) throws AxisFault {
		CSPResult res = new CSPResult();
		CSPRequestServiceStub stub = new CSPRequestServiceStub();

		try {
			ExecCmd cmd = new ExecCmd();
			cmd.setCSPID("20001041");
			cmd.setLSPID("all");
			cmd.setCorrelateID(map.get("cid").toString());
			cmd.setCmdFileURL("ftp://ftpuser:111111@115.28.77.76:22000/"+map.get("xmlurl"));

			ExecCmdResponse resp = stub.execCmd(cmd);
			res = resp.getExecCmdReturn();

			String logInfo = "访问JSONController:admin/video；调用结果编码：" + res.getResult() + "；结果描述："
					+ res.getErrorDescription();
			sysParamService.saveLog(logInfo);
			log.info(logInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
}
