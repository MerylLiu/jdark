package com.iptv.app.Utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.iptv.core.common.Configuration;

public class LocFtpUtil {
	private static FTPClient ftpClient;
	private static String _ip = Configuration.webCfg.getProperty("ftp.ip");
	private static int _port = Integer.valueOf(Configuration.webCfg.getProperty("ftp.port"));
	private static String _userName = Configuration.webCfg.getProperty("ftp.user");
	private static String _password = Configuration.webCfg.getProperty("ftp.pwd");

	private static FTPClient getFTPClient() {
		if (ftpClient == null)
			ftpClient = new FTPClient();
		boolean result = true;

		try {
			ftpClient.connect(_ip, _port);
			// System.out.println("FTP信息：" + _ip + "|" + _port);

			if (ftpClient.isConnected()) {
				boolean flag = ftpClient.login(_userName, _password);
				if (flag) {
					ftpClient.setControlEncoding("UTF-8");
					// binary file
					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
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

	public static FTPFile[] readFileList(String remotePath) {
		FTPFile[] files = null;
		ftpClient = getFTPClient();

		try {
			ftpClient.changeWorkingDirectory(remotePath);
			files = ftpClient.listFiles();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(ftpClient);
		}

		return files;
	}

	public static boolean deleteFile(String remotePath) {
		boolean flag = false;
		ftpClient = getFTPClient();

		try {
			flag = ftpClient.deleteFile(remotePath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(ftpClient);
		}

		return flag;
	}

	public static synchronized boolean remoteUpload(InputStream in, String fileName, String remotePath,Long size) {
		boolean result = true;
		ftpClient = getFTPClient();
		OutputStream os = null;
		
		try {
			if(size > 0){
				ftpClient.setRestartOffset(size);
			}
			
			ftpClient.storeFile(remotePath + fileName, in);
			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			if (ftpClient != null)
				try {
					ftpClient.logout();
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private static void close(FTPClient ftpClient) {
		if (null != ftpClient) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	 public static synchronized  boolean mkdir(String path) { 
		ftpClient = getFTPClient();

	 	boolean res = false;
	 	
		try {
			res = ftpClient.makeDirectory(path);
		} catch (IOException e) {
			close(ftpClient);
			e.printStackTrace();
		}finally{
			close(ftpClient);
		}

	    return res;
	  }
}
