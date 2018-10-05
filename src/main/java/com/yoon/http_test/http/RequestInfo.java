package com.yoon.http_test.http;

public class RequestInfo {
	private HttpMethod method;
	private String requestUrl;
	private String params;

	public RequestInfo() {
	}

	public RequestInfo(HttpMethod method, String requestUrl) {
		this.method = method;
		this.requestUrl = requestUrl;
	}

	public RequestInfo(HttpMethod method, String requestUrl, String params) {
		this.method = method;
		this.requestUrl = requestUrl;
		this.params = params;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
}
