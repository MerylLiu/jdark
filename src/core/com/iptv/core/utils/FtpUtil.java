package com.iptv.core.utils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.iptv.core.common.Configuration;
import com.sun.tools.doclets.formats.html.resources.standard;

public class FtpUtil {
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
			System.out.println("FTP信息：" + _ip + "|" + _port);

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

	private static FTPClient getFTPClient(String ip, int port, String uName, String pwd) {
		FTPClient fc = new FTPClient();
		boolean result = true;

		try {
			fc.connect(ip, port);
			if (fc.isConnected()) {
				boolean flag = fc.login(uName, pwd);
				if (flag) {
					fc.setControlEncoding("UTF-8");
					// binary file
					fc.setFileType(FTPClient.BINARY_FILE_TYPE);
					fc.enterLocalPassiveMode();
				} else {
					result = false;
				}
			} else {
				result = false;
			}
			if (result) {
				return fc;
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

		FTPClient ftpClient = getFTPClient(ip, port, uName, uPwd);

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
		ftpClient = getFTPClient();

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
		ftpClient = getFTPClient();

		try {
			result = ftpClient.changeWorkingDirectory(remotePath);
			if (result)
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

		FTPClient ftpClient = getFTPClient(ip, port, uName, uPwd);

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
		ftpClient = getFTPClient();

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
		ftpClient = getFTPClient();

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
		} finally {
			close(ins, null, ftpClient);
		}

		return builder.toString();
	}

	public static String readFile(String ip, int port, String uName, String uPwd, String fileName, String remotePath) {
		InputStream ins = null;
		StringBuilder builder = null;

		FTPClient ftpClient = getFTPClient(ip, port, uName, uPwd);

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
			// 主动调用一次getReply()把接下来的226销毁掉. 这样做是可以解决这个返回null问题
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
		ftpClient = getFTPClient();

		String dir = filePath.replaceAll("((/\\w+)*.*)/(.*)", "$1");
		String fileName = filePath.replaceAll("((/\\w+)*.*)/(.*)", "$3");

		// System.out.println(dir);
		// System.out.println(fileName);

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

	public static boolean dirExist(String dirName) {
		ftpClient = getFTPClient();

		boolean flag = false;

		try {
			flag = ftpClient.changeWorkingDirectory(dirName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, null, ftpClient);
		}

		return flag;
	}

	public static boolean fileExist(String path) throws IOException {
		boolean flag = false;
		ftpClient = getFTPClient();

		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 创建多级目录
	 * 
	 * @param multiDir
	 *            多级文件路径 格式"/a/b/c/"
	 * @return
	 * @throws IOException
	 */
	public static boolean mkdir(String multiDir) {
		ftpClient = getFTPClient();
		boolean res = false;
		multiDir = multiDir.endsWith("/") ? multiDir : multiDir + "/";
		String[] dirs = multiDir.split("/");

		try {
			ftpClient.changeWorkingDirectory("/");

			for (int i = 1; dirs != null && i < dirs.length; i++) {
				if (!ftpClient.changeWorkingDirectory(dirs[i])) {
					if (ftpClient.makeDirectory(dirs[i])) {
						if (!ftpClient.changeWorkingDirectory(dirs[i])) {
							return false;
						}
					}
				}
				res = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(null, null, ftpClient);
		}

		return res;
	}

	/**
	 * 创建目录
	 * 
	 * @param dirName
	 *            目录名
	 * @param parentPath
	 *            父目录路径
	 * @return
	 */
	public static boolean mkdir(String dirName, String parentPath) {
		ftpClient = getFTPClient();

		boolean flag = false;
		boolean res = false;

		try {
			flag = ftpClient.changeWorkingDirectory(parentPath);
			if (flag) {
				res = ftpClient.makeDirectory(dirName);
			} else {
				System.out.println("上级目录不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, null, ftpClient);
		}

		return res;
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @param path
	 *            待删除文件的绝对路径
	 * @return boolean
	 */
	public static boolean delDir(String path) {
		ftpClient = getFTPClient();

		try {
			// 如果是文件直接删除
			ftpClient.deleteFile(path);

			if (ftpClient.changeWorkingDirectory(path)) {
				// 是文件夹
				FTPFile[] ftpFiles = ftpClient.listFiles();
				if (ftpFiles == null || ftpFiles.length <= 0) {
					// 文件夹文空直接删除
					ftpClient.removeDirectory(path);
				}
				for (FTPFile ftpFile : ftpFiles) {
					// 文件夹不为空，先删文件再删文件夹
					ftpClient.deleteFile(path + "/" + ftpFile.getName());
					ftpClient.removeDirectory(path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		// getFTPClient("110.190.90.195", 21, "ftpitv", "ylkj2017");
		boolean res = delDir("/test");
		System.out.println("res:" + res);
	}
}
