package com.hiwoo.utils;

import java.util.List;

public class PageResult<T> {
    /*
     * 总数量
     * */
    private long total;
    /*
     * 当前页
     * */
    private int pageNumber;
    /*
     * 每页条数
     * */
    private int pageSize;
    /*
     * 结果数组
     * */
    private List<T> results;

    public List<T> getResults() {
        return results;
    }
    public void setResults(List<T> results) {
        this.results = results;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
