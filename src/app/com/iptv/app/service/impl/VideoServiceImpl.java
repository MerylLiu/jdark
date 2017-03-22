package com.iptv.app.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.iptv.app.Utils.LocXmlUtil;
import com.iptv.app.service.CSPRequestServiceStub;
import com.iptv.app.service.CSPRequestServiceStub.CSPResult;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmd;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmdResponse;
import com.iptv.app.service.VideoService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.Configuration;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.FtpUtil;
import com.iptv.core.utils.JsonUtil;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class VideoServiceImpl extends BaseServiceImpl implements VideoService {

	@Override
	public void UploadVedio() throws AxisFault {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) throws Exception {
		CSPRequestServiceStub stub = new CSPRequestServiceStub();

		try {
			ExecCmd cmd = new ExecCmd();
			cmd.setCSPID("20001041");
			cmd.setLSPID("all");
			cmd.setCorrelateID("001");
			cmd.setCmdFileURL("ftp://ftpuser:111111@139.129.129.184/test.xml");

			ExecCmdResponse resp = stub.execCmd(cmd);
			CSPResult res = resp.getExecCmdReturn();

			System.out.println("调用结果：" + res.getResult());
			System.out.println(res.getErrorDescription());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public KendoResult getVideosPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("video.getVideoPaged", "video.getVideoCount", null, map);
		return data;
	}

	@Override
	public Map getVideo(Integer id) {
		Map data = getDao().selectOne("video.getVideoById", id);
		return data;
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("SellerKeyId") == null) {
			errMsg.add("请选择所属商家。");
		}
		if (map.get("Code") == null) {
			errMsg.add("请输入视频编号。");
		}
		if (map.get("Name") == null) {
			errMsg.add("请输入视频名称。");
		}
		if (map.get("Jianpin") == null) {
			errMsg.add("请输入视频名称简拼。");
		}
		if (map.get("Quanpin") == null) {
			errMsg.add("请输入视频名称全拼。");
		}
		if (map.get("Cost") == null) {
			errMsg.add("请选择视频制作费。");
		}
		if (map.get("CostFZ") == null) {
			errMsg.add("请选择视频放置费。");
		}
		if (map.get("Style") == null) {
			errMsg.add("请选择视频风格。");
		}
		if (map.get("CategoryId") == null) {
			errMsg.add("请选择视频分类。");
		}
		if (map.get("VideoUrl") == null) {
			errMsg.add("请输入视频链接。");
		}
		if (map.get("StartDate") == null) {
			errMsg.add("请选择开始时间。");
		}
		if (map.get("EndDate") == null) {
			errMsg.add("请选择结束时间。");
		}
		if (map.get("Description") == null) {
			errMsg.add("请输入视频介绍。");
		}
		if (map.get("ImageUrl") == null) {
			errMsg.add("请输入静态图地址。");
		}
		if (map.get("GifUrl") == null) {
			errMsg.add("请输入动态图地址。");
		}
		if (map.get("QrCode") == null) {
			errMsg.add("请输入二维码地址。");
		}
		if (map.get("Province") == null) {
			errMsg.add("请选择商家所在的省。");
		}
		if (map.get("City") == null) {
			errMsg.add("请选择商家所在的市。");
		}
		if (map.get("Area") == null) {
			errMsg.add("请选择商家所在的区／县。");
		}
		if (map.get("Provider") == null) {
			errMsg.add("请输入提供商家。");
		}
		if (map.get("Maker") == null) {
			errMsg.add("请输入视频制作商。");
		}

		Integer existCount = getDao().selectOne("video.getExistCount", map);
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			if (existCount > 0) {
				errMsg.add("您输入的视频编号已存在。");
			}

			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}

			map.put("CreateDate", DateUtil.getNow());
			map.put("Status", 1);
			getDao().insert("video.saveVideo", map);
		} else {
			Map curr = getDao().selectOne("video.getVideoById", map.get("Id"));

			if (!map.get("Code").equals(curr.get("Code")) && existCount > 0) {
				errMsg.add("您输入的视频编号已存在。");
			}

			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}

			getDao().update("video.updateVideo", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的视频。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().update("video.deleteVideo", map);
	}

	@Override
	public void online(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要上线的视频。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().update("video.videoOnline", map);
	}

	@Override
	public void offline(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要下线的视频。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().update("video.videoOffline", map);
	}

	@Override
	public void doSubmit(Map map) throws BizException {
		List errMsg = new ArrayList();

		ArrayList ids = ((ArrayList) map.get("Id"));
		if (map.get("Id") == null || ids.size() <= 0) {
			errMsg.add("请选择要提交到电信进行审核的视频。");
		}

		ArrayList status = (ArrayList) map.get("Status");
		if (status.contains(0) || status.contains(2) || status.contains(2)) {
			errMsg.add("您选择的视频包含“已下线”、“已提交” 或 “已发布” 的视频，请重新选择。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		// 生成XML
		boolean res = false;
		for (Object item : ids) {
			Integer id = Integer.valueOf(item.toString());
			Map data = this.getVideo(id);

			String xml = LocXmlUtil.BuildXml(data);
			// System.out.println(xml);

			InputStream is = new ByteArrayInputStream(xml.getBytes());
			String xmlDir = Configuration.webCfg.getProperty("cfg.ftp.xmlPath");
			String code = data.get("Code").toString();
			String xmlFileName = code + ".xml";
			res = FtpUtil.upload(is, xmlFileName, xmlDir);

			if (res) {
				// Invoke WebService
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
					String correlateID = format.format(new Date());
					CSPResult cspResult = commitVideo(correlateID, xmlDir + "/" + xmlFileName);

					if (cspResult.getResult() == 0) {
						Map videoMap = new HashMap();
						videoMap.put("Id", id);
						videoMap.put("DxCode", "P"+data.get("Code"));
						videoMap.put("CorrelateID", correlateID);
						videoMap.put("CommitDate", DateUtil.getNow());

						log.info("提交工单到电信审核视频 CorrelateID:" + correlateID);
						BaseUtil.saveLog(8, "提交工单到电信审核视频", "CorrelateID:" + correlateID);
						getDao().update("video.videoSubmit", videoMap);
					} else {
						BaseUtil.saveLog(0, "提交工单到电信审核视频",
								"CorrelateID:" + correlateID + "|" + cspResult.getErrorDescription());
						throw new BizException(cspResult.getErrorDescription());
					}
				} catch (AxisFault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private CSPResult commitVideo(String correlateID, String xmlPath) throws AxisFault {
		CSPResult res = new CSPResult();
		CSPRequestServiceStub stub = new CSPRequestServiceStub();

		String cspid = Configuration.webCfg.getProperty("cfg.CSPID");
		String lspid = Configuration.webCfg.getProperty("cfg.LSPID");
		String ftpUrl = "ftp://" + Configuration.webCfg.getProperty("ftp.user") + ":"
				+ Configuration.webCfg.getProperty("ftp.pwd") + "@" + Configuration.webCfg.getProperty("ftp.ip") + ":"
				+ Configuration.webCfg.getProperty("ftp.port");

		try {
			ExecCmd cmd = new ExecCmd();
			cmd.setCSPID(cspid);
			cmd.setLSPID(lspid);
			cmd.setCorrelateID(correlateID);
			cmd.setCmdFileURL(ftpUrl + xmlPath);

			ExecCmdResponse resp = stub.execCmd(cmd);
			res = resp.getExecCmdReturn();

			log.info("工单详情：" + JsonUtil.getJson(cmd)+",返回结果：" + JsonUtil.getJson(res));
			BaseUtil.saveLog(8, "工单详情", "工单详情：" + JsonUtil.getJson(cmd)+",返回结果：" + JsonUtil.getJson(res));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public KendoResult getHomeVideoPaged(Map map) {
		int page = Integer.valueOf(map.get("page").toString());
		int pageSize = Integer.valueOf(map.get("pageSize").toString());

		Map param = new HashMap();

		if (Integer.valueOf(map.get("categoryId").toString()) == 0) {
			if (page == 1) {
				param.put("offset", 0);
				param.put("rows", 9);
			} else {
				param.put("offset", (page - 1) * pageSize - 6);
				param.put("rows", pageSize);
			}
		} else {
			param.put("offset", (page - 1) * pageSize);
			param.put("rows", pageSize);
		}
		param.putAll(map);

		KendoResult res = new KendoResult();
		res.setPage(page);
		res.setPageSize(pageSize);

		List data = getDao().selectList("video.getHomeVideoPaged", param);
		res.setData(data);

		if (Integer.valueOf(map.get("categoryId").toString()) == 0) {
			Integer pageNum = getDao().selectOne("video.getHomeVideoPageNum", param);
			res.setPageNum(pageNum);
		} else {
			Integer pageNum = getDao().selectOne("video.getVideoPageNum", param);
			res.setPageNum(pageNum);
		}

		Integer count = getDao().selectOne("video.getHomeVideoPagedCount", param);
		res.setTotal(count);

		return res;
	}

	@Override
	@Cacheable
	public List getHomeVideoByCategory(Integer categoryId) {
		List data = getDao().selectList("video.getVideoByCategory");
		return data;
	}

	@Override
	@Cacheable
	public List getHomeVideoForPreview(Integer categoryId) {
		Map map = new HashMap();
		map.put("categoryId", categoryId);

		List data = getDao().selectList("video.getHomeVideoForPreview", map);
		return data;
	}

	@Override
	public Map getDetail(Integer videoId) {
		Map map = new HashMap();
		map.put("id", videoId);

		Map data = getDao().selectOne("video.getVideoDetail", map);
		return data;
	}

	@Override
	public KendoResult getSearched(String name) {
		KendoResult res = new KendoResult();

		Map map = new HashMap();
		map.put("name", name);

		if (name.length() > 0) {
			List data = getDao().selectList("video.getSearched", map);
			Integer count = getDao().selectOne("video.getSearchedCount", map);

			res.setData(data);
			res.setTotal(count);
		}

		return res;
	}

	@Override
	public KendoResult getMoreVideoPaged(Map map) {
		int page = Integer.valueOf(map.get("page").toString());
		int pageSize = Integer.valueOf(map.get("pageSize").toString());

		Map param = new HashMap();
		param.put("offset", (page - 1) * pageSize);
		param.put("rows", pageSize);
		param.putAll(map);

		if (map.get("cid") != null) {
			param.put("categoryId", map.get("cid").toString());
		}
		if (map.get("key") != null) {
			param.put("name", map.get("key").toString());
		}

		KendoResult res = new KendoResult();
		res.setPage(page);
		res.setPageSize(pageSize);

		List data = getDao().selectList("video.getSearchPaged", param);
		res.setData(data);

		Integer pageNum = getDao().selectOne("video.getSearchPageNum", param);
		res.setPageNum(pageNum);

		Integer count = getDao().selectOne("video.getSearchPagedCount", param);
		res.setTotal(count);

		return res;
	}
}
