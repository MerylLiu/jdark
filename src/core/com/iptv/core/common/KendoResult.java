package com.iptv.core.common;

import java.util.List;

@SuppressWarnings("rawtypes")
public class KendoResult {

	private List data;
	private Integer total;
	private int page;
	private int pageSize;
	private int pageNum;

	public KendoResult(){
	}

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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
}

