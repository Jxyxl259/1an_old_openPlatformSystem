package com.yaic.fa.dto;

import java.io.Serializable;
import java.util.List;

public class JqGridPageDto<T> implements Serializable {

    private static final long serialVersionUID = JqGridPageDto.class.hashCode();

    /** 总页数 */ 
    private int total ;
    
    /** 当前页数 */
    private int page ;
    
    /** 总记录数 */
    private long records ;
    
    /** 数据 */
    private List<T> rows;

    private String errorMsg ;
    /**
     * 返回总页数
     * @return
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置总页数
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 返回当前页数
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * 设置当前页数
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 返回总记录数
     * @return
     */
    public long getRecords() {
        return records;
    }

    /**
     * 设置总记录数
     * @param records
     */
    public void setRecords(long records) {
        this.records = records;
    }

    /**
     * 返回数据
     * @return
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置数据
     * @param rows
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
