package com.iptv.core.service.impl;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.iptv.core.dao.BasicDao;
import com.iptv.core.service.BaseService;
import com.iptv.core.utils.BaseUtil;


@Service
public class BaseServiceImpl implements BaseService {

	@Override
	public SqlSessionTemplate getDao() {
		BasicDao basicDao = (BasicDao)BaseUtil.getService("basicDao");
		SqlSessionTemplate sqlSession = basicDao.getSqlSessionTemplate();

		return sqlSession;
	}

	public Logger log = Logger.getLogger(this.getClass());
}
