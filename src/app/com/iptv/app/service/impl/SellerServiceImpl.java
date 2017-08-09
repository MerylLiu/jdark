package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.SellerService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SellerServiceImpl extends BaseServiceImpl implements SellerService {

	@Override
	public KendoResult getSellersPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("seller.getSellersPaged", map);
		return data;
	}

	@Override
	public Map getSeller(Integer id) {
		Map data = getDao().selectOne("seller.getSellerById", id);
		return data;
	}

	@Override
	public String save(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Code") == null) {
			errMsg.add("请输入商家编号。");
		}
		if (map.get("Name") == null) {
			errMsg.add("请输入商家名称。");
		}
		if (map.get("Tel") == null) {
			errMsg.add("请输入商家电话。");
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
		if (map.get("Address") == null) {
			errMsg.add("请输入商家地址。");
		}
		if (map.get("SetUpDate") == null) {
			errMsg.add("请选择商家开店时间。");
		}

		Integer existCount = getDao().selectOne("seller.getExistCount", map);
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			if (existCount > 0) {
				errMsg.add("您输入的商家编号已存在。");
			}

			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}

			map.put("CreateDate", DateUtil.getNow());
			getDao().insert("seller.saveSeller", map);

			return "add";
		} else {
			Map curr = getDao().selectOne("seller.getSellerById", map.get("Id"));

			if (!map.get("Code").equals(curr.get("Code")) && existCount > 0) {
				errMsg.add("您输入的商家编号已存在。");
			}

			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}

			getDao().update("seller.updateSeller", map);

			return "update";
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList)map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的商家。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("seller.deleteSeller", map);
	}


	@Override
	public KendoResult getAllSellers() {
		List data = getDao().selectList("seller.getAllSellers");
		return new KendoResult(data);
	}

	@Override
	public KendoResult getSellersOptions(Map map) {
		KendoResult data = QueryUtil.getSelectOptions("seller.getSellersOptions", map);
		return data;
	}
}
