package com.iptv.app.controller.tv;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iptv.core.controller.BaseController;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.FtpUtil;

@Controller
@RequestMapping("/image")
@SuppressWarnings("rawtypes")
public class ImageController extends BaseController {

	@RequestMapping(value = { "", "/", "/index"})
	public void index(@RequestParam String p,HttpServletRequest request,HttpServletResponse response)  {
		InputStream ins = null;
		OutputStream os = null;  

        try {  
        	String path = URLDecoder.decode(p);
        	ins = FtpUtil.readFileStream(path);
        	os = response.getOutputStream();

            int count = 0;  
            byte[] buffer = new byte[1024 * 8];  
            while ((count = ins.read(buffer)) != -1) {  
                os.write(buffer, 0, count);  
                os.flush();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        try {  
            ins.close();  
            os.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}
