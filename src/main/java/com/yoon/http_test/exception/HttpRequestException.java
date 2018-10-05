package com.yoon.http_test.exception;

public class HttpRequestException extends RuntimeException {
	private Exception cause;

	public HttpRequestException(Exception cause) {
		this.cause = cause;
	}
}
