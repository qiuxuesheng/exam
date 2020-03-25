package com.qiuxs.base.page;


import com.qiuxs.base.util.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PageLimit {
    /**
     *  其中currentPage,perPageRows这两个参数是做分页查询必须具备的参数
     *  原因是：hibernate中的Criteria或则是Query这两个接口：都有setFirstResult(Integer firstResult)
     *  和setMaxResult(Integer maxResult),
     *  这里的firstResult就是每页的开始的索引数：
     *  每页开始的索引数的计算公式是：（currentPage-1）*perPageRows+1,(这是相对索引从1开始的)
     *  但是Hibernate中的firstResult的索引是从0开始的，所以在hibernate中每页开始的索引数的计算公式是：
     *  (currentPage-1)*perPageRows+1-1=(currentPge-1)*perPageRows.
     *
     *  maxResult就是每页能查询的最大记录数：也就是perPageRows.
     *
     *  Math.ceil(totalRows/perPageRows)==totalPages;//这是根据总记录数和每页的记录数算出总页数的计算公式。
     */
    private int page = 1;// 页码
    private int rows = 20;// 每页行数
    private int total = 0;// 总记录数

    private boolean pagination = true;// 默认分页

    private String url;// 上一次请求的地址
    private Map<String, String[]> parameterMap;// 上一次请求的所有参数

    /**
     * 对分页bean进行初始化
     *
     * @param request
     */
    public void init(HttpServletRequest request){
        // 公共参数
        if (Strings.isNotEmpty(request.getParameter("page"))){
            this.setPage(request.getParameter("page"));
        }

        if (Strings.isNotEmpty(request.getParameter("rows"))){
            this.setRows(request.getParameter("rows"));
        }
        // 请求地址和请求参数
        this.setUrl(request.getContextPath() + request.getServletPath());
        this.setParameterMap(request.getParameterMap());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPage(String page) {
        if (null != page && !"".equals(page.trim())) {
            this.page = Integer.parseInt(page);
        }
    }

    /**
     *  行数/页大小
     * @return
     */
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setRows(String rows) {
        if (null != rows && !"".equals(rows.trim())) {
            this.rows = Integer.parseInt(rows);
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTotal(String total) {
        this.total = Integer.parseInt(total);
    }

    public boolean isPagination() {
        return pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public void setPagination(String pagination) {
        if ("false".equals(pagination)) {
            this.pagination = false;
        }
    }

    /**
     * 下一页
     *
     * @return
     */
    public int getNextPage() {
        int nextPage = page + 1;
        if (nextPage > this.getMaxPage()) {
            nextPage = this.getMaxPage();
        }
        return nextPage;
    }

    /**
     * 上一页
     *
     * @return
     */
    public int getPreviousPage() {
        int previousPage = page - 1;
        if (previousPage < 1) {
            previousPage = 1;
        }
        return previousPage;
    }

    /**
     * 最大页码
     *
     * @return
     */
    public int getMaxPage() {
        return total % rows == 0 ? total / rows : total / rows + 1;
    }

    /**
     * 起始记录的下标
     *
     * @return
     */
    public int getStartIndex() {
        return (page - 1) * rows;
    }

    @Override
    public String toString() {
        return "PageLimit [page=" + page + ", rows=" + rows + ", total=" + total + ", pagination=" + pagination + "]";
    }

}