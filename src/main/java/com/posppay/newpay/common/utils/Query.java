package com.posppay.newpay.common.utils;

import com.baomidou.mybatisplus.plugins.Page;
import com.posppay.newpay.common.filter.SqlFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author wwa
 */
public class Query<T> extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

    private static final String PAGE = "page";
    private static final String LIMIT = "limit";

    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int currPage = 1;
    /**
     * 每页条数
     */
    private int limit = 10;

    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        if(params.get(PAGE) != null){
            currPage = Integer.parseInt((String)params.get(PAGE));
        }
        if(params.get(LIMIT) != null){
            limit = Integer.parseInt((String)params.get(LIMIT));
        }

        this.put("offset", (currPage - 1) * limit);
        this.put("page", currPage);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = SqlFilter.sqlInject((String)params.get("sidx"));
        String order = SqlFilter.sqlInject((String)params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        //mybatis-plus分页
        this.page = new Page<>(currPage, limit);

        //排序
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
            this.page.setOrderByField(sidx);
            this.page.setAsc("ASC".equalsIgnoreCase(order));
        }

    }

    public Page<T> getPage() {
        return page;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getLimit() {
        return limit;
    }
}
