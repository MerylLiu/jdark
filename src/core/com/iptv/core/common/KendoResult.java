package com.iptv.core.common;

import java.util.List;

@SuppressWarnings("rawtypes")
public class KendoResult {

	private List data;
	private Integer total;

	public KendoResult(List data){
		this.data = data;
	}
	
	public KendoResult(List data,Integer total){
		this.data = data;
		this.total = total;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
