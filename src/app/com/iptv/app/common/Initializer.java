package com.iptv.app.common;

import java.util.Properties;

import com.iptv.core.common.Configuration;

public class Initializer {

	public void init(){
		Properties cfg = Configuration.webCfg;
		
		if(Boolean.valueOf(cfg.getProperty("timer.auto"))){
			TimedTask.start();
		}else{
			TimedTask.stop();
		}
	}
}
