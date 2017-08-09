package com.iptv.app.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.iptv.core.common.Configuration;
import com.iptv.core.utils.FtpUtil;
import com.iptv.sys.controller.admin.AdminBaseController;

@Controller
@RequestMapping("/admin/file")
@SuppressWarnings({ "rawtypes", "unused", "unchecked"})
public class AdminFileController extends AdminBaseController{

	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public @ResponseBody Map upload(@RequestParam("Filedata")MultipartFile file){
		String fileName = file.getOriginalFilename();
		try {
			InputStream stream = file.getInputStream();
			Boolean res = FtpUtil.upload(stream, fileName, "banner");
			log.info("上传文件："+res);
			
			String path = "/banner/" + fileName;
			
			if(res){
				Map data = new HashMap();
				data.put("result", true);
				data.put("path", path);
				data.put("url", Configuration.webCfg.getProperty("static.url") + path);
				return data;
			} else{
				Map data = new HashMap();
				data.put("result", false);
				data.put("message", "上传失败");
				return data;
			}
		} catch (IOException e) {
			e.printStackTrace();

			Map data = new HashMap();
			data.put("result", false);
			data.put("message", "上传失败");
			return data;
		}
	}
	
	@RequestMapping(value="/uploadThumbnail",method=RequestMethod.POST)
	public @ResponseBody Map uploadThumbnail(@RequestParam("Filedata")MultipartFile file){
		String fileName = file.getOriginalFilename();
		try {
			InputStream stream = file.getInputStream();
			Boolean res = FtpUtil.upload(stream, fileName, "thumbnail");
			log.info("上传文件缩略图："+res);
			
			String path = "/thumbnail/" + fileName;
			
			if(res){
				Map data = new HashMap();
				data.put("result", true);
				data.put("path", path);
				data.put("url", Configuration.webCfg.getProperty("static.url") + path);
				return data;
			} else{
				Map data = new HashMap();
				data.put("result", false);
				data.put("message", "上传失败");
				return data;
			}
		} catch (IOException e) {
			e.printStackTrace();

			Map data = new HashMap();
			data.put("result", false);
			data.put("message", "上传失败");
			return data;
		}
	}
}
