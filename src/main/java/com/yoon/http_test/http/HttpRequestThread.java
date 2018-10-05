package com.yoon.http_test.http;

import java.util.Queue;

public class HttpRequestThread extends Thread {
	public Queue<RequestInfo> requestQueue;

	public HttpRequestThread(Queue<RequestInfo> requestQueue) {
		this.requestQueue = requestQueue;
	}

	@Override
	public void run() {
		RequestInfo requestInfo;
		while (true) {
			synchronized (requestQueue) {
				requestInfo = requestQueue.poll();
			}

			if (requestInfo == null) {
				break;
			}

			RestAPIHelper restApiHelper = new RestAPIHelper(HttpMethod.GET, "https://httpbin.org/get");
			String responseText = restApiHelper.sendRequest();
			//System.out.println(responseText);
		}
	}
}
