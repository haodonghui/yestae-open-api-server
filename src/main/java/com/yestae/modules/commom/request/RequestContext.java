package com.yestae.modules.commom.request;

public class RequestContext {
	private String appid;
	private String mchid;

	private static ThreadLocal<RequestContext> threadLocal = new ThreadLocal<RequestContext>() {
		@Override
		protected RequestContext initialValue() {
			return new RequestContext();
		}
	};

	public static RequestContext get() {
		return threadLocal.get();
	}
	
	public static void remove() {
		threadLocal.remove();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}


}
