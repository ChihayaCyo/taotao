package com.taotao.common.pojo;

import java.util.List;

/**
 * Created by zh on 2/22/2017.
 */
public class EasyUIDataGridResult {

    private long total;

    private List<?> rows;

    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
