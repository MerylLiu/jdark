package com.iptv.sys.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ResponseWrapper extends HttpServletResponseWrapper {
	private ByteArrayOutputStream buffer = null;
	private ServletOutputStream out = null;
	private PrintWriter writer = null;
	private Map<String, String> headers = new HashMap<String, String>();

	public ResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);

		this.buffer = new ByteArrayOutputStream();
		this.out = new WapperedOutputStream(this.buffer);
		this.writer = new PrintWriter(new OutputStreamWriter(this.buffer, getCharacterEncoding()));
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return this.writer;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return this.out;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (this.out != null) {
			this.out.flush();
		}
		if (this.writer != null) {
			this.writer.flush();
		}
	}

	@Override
	public void reset() {
		this.buffer.reset();
	}

	public Map getContent() {
		try {
			Map res = new HashMap();
			res.put("ByteData", this.buffer.toByteArray());
			res.put("StringData", new String(this.buffer.toByteArray(), "UTF-8"));

			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private class WapperedOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream bos = null;

		public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
			this.bos = stream;
		}

		@Override
		public void write(int b) throws IOException {
			this.bos.write(b);
		}

		@Override
		public void write(byte[] b) throws IOException {
			super.write(b);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			super.write(b, off, len);
		}
	}

	@Override
	public String getHeader(String name) {
		return headers.get(name);
	}

	@Override
	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
}
