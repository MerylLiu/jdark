package com.iptv.core.common;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class BizException extends Exception {
	private final List<String> errorMessage = new ArrayList<String>();

	public BizException(String message) {
		super(message);

		errorMessage.add(message);
	}

	public BizException(List<String> messages) {
		errorMessage.addAll(messages);
	}

	public List<String> getMessages() {
		return errorMessage;
	}
}
