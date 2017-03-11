package com.iptv.core.dao;

import org.mybatis.spring.SqlSessionTemplate;

public class BasicDaoImpl implements BasicDao {
	public SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSession) {
		this.sqlSessionTemplate = sqlSession;
	}

	@Override
	public SqlSessionTemplate getSqlSessionTemplate() {
		return this.sqlSessionTemplate;
	}
}
