package com.yoon.http_test;

import com.yoon.http_test.http.HttpMethod;
import com.yoon.http_test.http.HttpRequestThread;
import com.yoon.http_test.http.RequestInfo;
import com.yoon.http_test.http.RestAPIHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.json.*;

public class Main {
	public static void main(String[] args) {
//		RestAPIHelper restApiHelper = new RestAPIHelper(HttpMethod.GET, "https://httpbin.org/stream/10");
//		String responseText = restApiHelper.sendRequest();
//		System.out.println(responseText);
//		JSONObject jsonObj = new JSONObject(responseText);
//		System.out.println(jsonObj.get("url"));
//
//		restApiHelper = new RestAPIHelper(HttpMethod.DELETE, "https://httpbin.org/delete");
//		responseText = restApiHelper.sendRequest();
//		System.out.println(responseText);
//
//		restApiHelper = new RestAPIHelper(HttpMethod.PUT, "https://httpbin.org/get");
//		responseText = restApiHelper.sendRequest();
//		System.out.println(responseText);

		/*
		 * 스레드 시간 측정
		 */
		Queue<RequestInfo> requestQueue = new LinkedList<>();
		for (int i = 0; i < 100; i++) {
			requestQueue.add(new RequestInfo(HttpMethod.GET, "https://httpbin.org/get"));
		}

		long start = System.currentTimeMillis();
		ArrayList<HttpRequestThread> threads = new ArrayList<>();
		for (int i = 0; i < 38; i++) {
			HttpRequestThread requestThread = new HttpRequestThread(requestQueue);
			requestThread.start();
			threads.add(requestThread);
		}

		for (int i = 0; i < threads.size(); i++) {
			HttpRequestThread requestThread = threads.get(i);
			try {
				requestThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("스레드 실행 시간: " + (end - start) / 1000.0 + "초");

		/*
		 * 일반 시간 측정
		 */
		for (int i = 0; i < 100; i++) {
			requestQueue.add(new RequestInfo(HttpMethod.GET, "https://httpbin.org/get"));
		}

		start = System.currentTimeMillis();
		RequestInfo requestInfo;
		while (true) {
			requestInfo = requestQueue.poll();

			if (requestInfo == null) {
				break;
			}

			RestAPIHelper restApiHelper = new RestAPIHelper(HttpMethod.GET, "https://httpbin.org/get");
			String responseText = restApiHelper.sendRequest();
			// System.out.println(responseText);
		}
		end = System.currentTimeMillis();
		System.out.println("일반 실행 시간: " + (end - start) / 1000.0 + "초");
	}
}