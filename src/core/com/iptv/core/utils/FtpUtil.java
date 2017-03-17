package com.iptv.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpUtil {
	private static FTPClient ftpClient;
	private static String _ip = "115.28.77.76";
	private static int _port = 22000;
	private static String _userName = "ftpuser";
	private static String _password = "111111";

	private static void setParam(String ip, int port, String uName, String uPwd) {
		_ip = ip;
		_port = port;
		_userName = uName;
		_password = uPwd;
	}

	private static FTPClient getFTPClient() {
		if (ftpClient == null)
			ftpClient = new FTPClient();
		boolean result = true;

		try {
			ftpClient.connect(_ip, _port);
			if (ftpClient.isConnected()) {
				boolean flag = ftpClient.login(_userName, _password);
				if (flag) {
					ftpClient.setControlEncoding("UTF-8");
					// binary file
					ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
					ftpClient.enterLocalPassiveMode();
				} else {
					result = false;
				}
			} else {
				result = false;
			}
			if (result) {
				return ftpClient;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void close(InputStream in, OutputStream out, FTPClient ftpClient) {
		if (null != in) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Input stream close error!");
			}
		}
		if (null != out) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Onput stream close error!");
			}
		}
		if (null != ftpClient) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Ftp client stream close error!");
			}
		}
	}

	public static boolean upload(String ip, int port, String uName, String uPwd, String fileName, String localPath,
			String remotePath) {
		boolean result = true;
		FileInputStream in = null;

		setParam(ip, port, uName, uPwd);

		FTPClient ftpClient = getFTPClient();

		try {
			File file = new File(localPath + fileName);
			in = new FileInputStream(file);

			result = ftpClient.changeWorkingDirectory(remotePath);
			result = ftpClient.storeFile(fileName, in);

			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(in, null, ftpClient);
		}
	}

	public static boolean upload(String fileName, String localPath, String remotePath) {
		boolean result = true;
		FileInputStream in = null;
		FTPClient ftpClient = getFTPClient();

		try {
			File file = new File(localPath + fileName);
			in = new FileInputStream(file);

			result = ftpClient.changeWorkingDirectory(remotePath);
			result = ftpClient.storeFile(fileName, in);

			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(in, null, ftpClient);
		}
	}

	public static boolean upload(InputStream in, String fileName, String remotePath) {
		boolean result = true;
		FTPClient ftpClient = getFTPClient();

		try {
			result = ftpClient.changeWorkingDirectory(remotePath);
			result = ftpClient.storeFile(fileName, in);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(in, null, ftpClient);
		}
	}

	public static boolean download(String ip, int port, String uName, String uPwd, String fileName, String localPath,
			String remotePath) {
		boolean result = true;
		FileOutputStream out = null;

		setParam(ip, port, uName, uPwd);

		FTPClient ftpClient = getFTPClient();

		try {
			File file = new File(localPath + fileName);
			out = new FileOutputStream(file);

			ftpClient.changeWorkingDirectory(remotePath);
			ftpClient.retrieveFile(fileName, out);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(null, out, ftpClient);
		}
	}

	public static boolean download(String fileName, String localPath, String remotePath) {
		boolean result = true;
		FileOutputStream out = null;
		FTPClient ftpClient = getFTPClient();

		try {
			File file = new File(localPath + fileName);
			out = new FileOutputStream(file);

			ftpClient.changeWorkingDirectory(remotePath);
			ftpClient.retrieveFile(fileName, out);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(null, out, ftpClient);
		}
	}

	public static String readFile(String fileName, String remotePath) {
		InputStream ins = null;
		StringBuilder builder = null;
		FTPClient ftpClient = getFTPClient();

		try {
			ftpClient.changeWorkingDirectory(remotePath);
			ins = ftpClient.retrieveFileStream(fileName);

			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String line;
			builder = new StringBuilder(150);
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			reader.close();
			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			close(ins, null, ftpClient);
		}

		return builder.toString(); 
	}

	public static String readFile(String ip, int port, String uName, String uPwd, String fileName, String remotePath) {
		InputStream ins = null;
		StringBuilder builder = null;
		FTPClient ftpClient = getFTPClient();

		try {
			ftpClient.changeWorkingDirectory(remotePath);
			ins = ftpClient.retrieveFileStream(fileName);

			setParam(ip, port, uName, uPwd);

			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String line;
			builder = new StringBuilder(150);
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			reader.close();
			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(ins, null, ftpClient);
		}

		return builder.toString(); 
	}

	public static InputStream readFileStream(String filePath) {
		InputStream ins = null;
		FTPClient ftpClient = getFTPClient();
		
		String dir = filePath.replaceAll("((/\\w+)*.*)/(.*)", "$1"); 
		String fileName = filePath.replaceAll("((/\\w+)*.*)/(.*)", "$3"); 
		
		//System.out.println(dir);
		//System.out.println(fileName);

		try {
			ftpClient.changeWorkingDirectory(dir);
			ins = ftpClient.retrieveFileStream(fileName);
			ftpClient.getReply();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(null, null, ftpClient);
		}

		return ins;
	}
}
