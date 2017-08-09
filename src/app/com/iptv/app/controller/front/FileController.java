package com.iptv.app.controller.front;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.app.Utils.LocFtpUtil;
import com.iptv.core.common.Configuration;
import com.iptv.core.controller.BaseController;

@Controller
@RequestMapping(value = { "/file", "/front/file" })
@SuppressWarnings({ "resource", "rawtypes", "unused", "unchecked" })
public class FileController extends BaseController {

	private final static String uploadStyle = Configuration.webCfg.getProperty("cfg.upload.style");
	private final static String root = Configuration.webCfg.getProperty("cfg.ftp.upload.root");
	private final static String chunkSize = Configuration.webCfg.getProperty("cfg.ftp.upload.chunkSize");

	@RequestMapping(value = { "/", "", "/index" })
	public ModelAndView index() {

		Map map = new HashMap();
		map.put("chunkSize", chunkSize);
		return view("/front/file/index", map);

	}

	@RequestMapping("/upload")
	public void upload(@RequestParam Map map, @RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		boolean isFtp = uploadStyle.equals("local") ? false : true;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String rootPath = "/" + root + "/" + sdf.format(date) + "/";
		String name = file.getOriginalFilename();
		String remotePath = rootPath + name;

		if ((map.get("chunk") == null || map.get("chunk").toString().equals("0")) && isFtp) {
			LocFtpUtil.mkdir(rootPath);
		}

		if (isFtp) {

			Long size = (long) 0;

			if (map.get("chunk") != null && Integer.parseInt(map.get("chunk").toString()) > 0) {
				size = (long) (Integer.parseInt(map.get("chunk").toString()) * Float.parseFloat(chunkSize) * 1024
						* 1024);
			}

			LocFtpUtil.remoteUpload(file.getInputStream(), name, rootPath, size);

		} else if (file.getInputStream() instanceof FileInputStream) {
			FileChannel resultFileChannel = new FileOutputStream("D:/" + name, true).getChannel();
			FileChannel blk = ((FileInputStream) file.getInputStream()).getChannel();

			resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
			blk.close();
			resultFileChannel.close();
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			FileOutputStream fos = new FileOutputStream(new File("D:/" + name), true);
			ByteArrayInputStream bis = (ByteArrayInputStream) file.getInputStream();

			int data = 0;
			while ((data = bis.read()) != -1) {
				bos.write(data);
			}
			bos.writeTo(fos);
			bos.flush();
			fos.flush();
			bos.close();
			fos.close();
			bis.close();
		}
	}
}
