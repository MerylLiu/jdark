package com.iptv.sys.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper {
	private ByteArrayOutputStream buffer;

	private WapperedOutputStream out;
	private PrintWriter writer;

	public ResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);

		buffer = new ByteArrayOutputStream();

		out = new WapperedOutputStream(buffer);
		writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (out.size() <= 0) {
			ServletOutputStream outputStream = super.getOutputStream();
			return outputStream;
		}
		return out;
	}

	@Override

	public void flushBuffer() throws IOException {
		if (out != null) {
			out.flush();
		}

		if (writer != null) {
			writer.flush();
		}
	}

	@Override
	public void reset() {
		buffer.reset();
	}

	public String getContent() {
		try {
			return new String(buffer.toByteArray(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
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

		@SuppressWarnings("unused")
		public int size() {
			return bos.size();
		}
	}
}
