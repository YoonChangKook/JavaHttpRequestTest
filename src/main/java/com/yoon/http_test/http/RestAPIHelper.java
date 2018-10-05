package com.yoon.http_test.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.yoon.http_test.exception.HttpRequestException;

/**
 * @author 국윤창
 * HTTP 요청을 보내고 응답을 받아오는 메서드를 제공해주는 클래스.
 */
public class RestAPIHelper {
	private HttpMethod httpMethod;
	private String requestUrl;
	private String params;
	
	public RestAPIHelper(HttpMethod httpMethod, String requestUrl) {
		this.httpMethod = httpMethod;
		this.requestUrl = requestUrl;
	}

	public RestAPIHelper(HttpMethod httpMethod, String requestUrl, String params) {
		this.httpMethod = httpMethod;
		this.requestUrl = requestUrl;
		this.params = params;
	}

	/**
	 * @return 요청 결과로 나온 문자열
	 * @throws HttpRequestException 요청을 보내는 도중 에러 발생 시
	 * @throws IllegalStateException 요청을 보냈지만 에러코드를 받았을 때
	 */
	public String sendRequest() throws HttpRequestException, IllegalStateException {
		try {
			URL urlObj = makeUrl();
			HttpURLConnection connection = makeUrlConnection(urlObj);
			return connectAndGetResponse(connection);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new HttpRequestException(ex);
		}

	}

	private URL makeUrl() throws MalformedURLException {
		if (params != null && params.length() > 0 && this.httpMethod == HttpMethod.GET) {
			return new URL(this.requestUrl + this.params);
		} else {
			return new URL(this.requestUrl);
		}
	}

	private HttpURLConnection makeUrlConnection(URL urlObj) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
		connection.setRequestMethod(this.httpMethod.name());
		connection.setRequestProperty("charset", "utf-8");

		if (params != null && params.length() > 0 && this.httpMethod != HttpMethod.GET) {
			connection.setDoOutput(true);
			try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
				writer.write(params);
				writer.flush();
			}
		}

		return connection;
	}

	private String connectAndGetResponse(HttpURLConnection connection) throws IOException {
		connection.connect();
		int responseCode = connection.getResponseCode();
		if (responseCode != 200) {
			throw new IllegalStateException(Integer.toString(responseCode));
		}

		try (InputStreamReader isr = new InputStreamReader(connection.getInputStream())) {
			return IOUtils.toString(isr);
		}
	}
}
