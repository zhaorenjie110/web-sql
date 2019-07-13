package org.websql.db.service

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 提供通用的sql查询方案
 */
@RestController
@CompileStatic
@RequestMapping("/sql")
class DbService {
    @Autowired
    JdbcTemplate jdbcTemplate

    /**
     * 执行sql
     * @param sql
     * @return
     */
    @PostMapping("/execute")
    def exdcuteSql(@RequestBody String sql){
        return jdbcTemplate.update(sql)
    }

    /**
     * 请求查询列表
     * @param sql
     * @return
     */
    @GetMapping("/queryForList")
    def queryForList(@RequestBody String sql){
        return jdbcTemplate.queryForList(sql)
    }




    /**
     * 请求查询单个对象
     * @param sql
     * @return
     */
    @GetMapping("/queryForSingle")
    def queryForSingle(@RequestBody String sql){
        return jdbcTemplate.queryForMap(sql)
    }

    /**
     * 请求分页
     * @param sql
     * @param page 页数
     * @param pageSize 页码
     * @return
     */
    @GetMapping("/queryForPage")
    def queryForPage(@RequestBody String sql,Integer page,Integer pageSize){
        /**
         * 排除select在这个分页里面的干扰
         */
        sql = sql.trim();
        String sql1 = "select count(*) num from ( "+sql+" ) counts";
        Map<String,Object> map = new HashMap<String,Object>();
        Integer first=(page-1)*pageSize
        Integer max = pageSize
        List list = jdbcTemplate.queryForList(sql+" limit ${first},${max}"); // 要返回的某一页的记录列表

        BigInteger bigInteger =  jdbcTemplate.queryForMap(sql1).get('num').toString().toBigInteger();
        Long allRow = bigInteger==null?0L:bigInteger.longValue();
        int totalPage = (int) (allRow%pageSize==0?allRow/pageSize:allRow/pageSize+1); // 总页数
        int currentPage = page; // 当前页
        boolean isFirstPage = currentPage==1; // 是否为第一页
        boolean isLastPage = currentPage==totalPage; // 是否为最后一页
        boolean hasPreviousPage = currentPage>1; // 是否有前一页
        boolean hasNextPage = currentPage<totalPage; // 是否有下一页

        map.put("list", list);
        map.put("allRow", allRow);
        map.put("totalPage", totalPage);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        map.put("isFirstPage", isFirstPage);
        map.put("isLasePage", isLastPage);
        map.put("hasPreviousPage", hasPreviousPage);
        map.put("hasNextPage", hasNextPage);

        return map
    }
}
