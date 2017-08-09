package com.iptv.app.common;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.iptv.core.common.Configuration;

@SuppressWarnings("rawtypes")
public class TimedTask {
	private Logger log = Logger.getLogger(this.getClass());

	private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	private static long delay = 0;
	private static long interval = Integer.valueOf(Configuration.webCfg.getProperty("timer.interval"));

	private static Future future;
	private static boolean isRunning = false;

	public static void start() {
		System.out.println("*********************************定时任务开起*********************************。");
		future = service.scheduleWithFixedDelay(runnable, delay, interval, TimeUnit.HOURS);
	}

	public static void stop() {
		System.out.println("*********************************定时任务结束*********************************。");
		if (future != null && !future.isCancelled())
			future.cancel(true);
	}

	private static Runnable runnable = new Runnable() {

		@Override
		@SuppressWarnings("deprecation")
		public void run() {
			// task to run goes here

			if (!isRunning) {
				isRunning = true;

				Date date = new Date();
				Integer hour = date.getHours();

				if (hour == Integer.valueOf(Configuration.webCfg.getProperty("timer.trigger"))) {
					// System.out.println("Hello !!" + hour);
				}

				isRunning = false;
			}
		}
	};
}
