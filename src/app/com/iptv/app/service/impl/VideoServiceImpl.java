package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.springframework.stereotype.Service;

import com.iptv.app.service.CSPRequestServiceStub;
import com.iptv.app.service.CSPRequestServiceStub.CSPResult;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmd;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmdResponse;
import com.iptv.app.service.VideoService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class VideoServiceImpl extends BaseServiceImpl implements VideoService{

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

			ExecCmdResponse resp=stub.execCmd(cmd);
			CSPResult res= resp.getExecCmdReturn();

			System.out.println("调用结果："+res.getResult());
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
	public void offline(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList)map.get("Id")).size() <= 0) {
			errMsg.add("请选择要下线的视频。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("seller.deleteSeller", map);
	}

}
