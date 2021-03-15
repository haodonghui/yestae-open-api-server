package com.yestae.common.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * 
 * 线程池工具类
 *
 * @Package com.yestae.common.utils 
 * @ClassName ExecutorsFactory 
 * @author wangpeng
 * @date 2019年5月14日 上午10:50:51 
 *
 */
public class ExecutorsFactory {
	private ListeningExecutorService defaultExecutorService = null;

	private static class Holder {
		final static ExecutorsFactory instance = new ExecutorsFactory();
	}

	public static ListeningExecutorService bulid() {
		return Holder.instance.getDefaultExecutorService();
	}

	private ListeningExecutorService newCachedExecutorService() {
		return MoreExecutors.listeningDecorator(new ThreadPoolExecutor(50, 500,
				60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>()));
	}

	private ListeningExecutorService getDefaultExecutorService() {
		if (defaultExecutorService == null) {
			defaultExecutorService = newCachedExecutorService();
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					defaultExecutorService.shutdown();
					try {
						if (!defaultExecutorService.awaitTermination(30, TimeUnit.SECONDS)) {
							defaultExecutorService.shutdownNow(); 
						}
					} catch (InterruptedException e) {
						defaultExecutorService.shutdownNow(); 
					}
				}
			});
		}
		return defaultExecutorService;
	}

}
