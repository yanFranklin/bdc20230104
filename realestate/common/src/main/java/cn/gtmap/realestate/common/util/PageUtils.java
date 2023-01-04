package cn.gtmap.realestate.common.util;

import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.common.core.dto.exchange.PageDTO;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/7/24
 * @description 分页处理方法
 */
public class PageUtils {

    /**
     * @param pageable
     * @param map
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理排序信息，处理pageable中sort传入的参数，
     * 将排序信息和查询信息整理成一个map作为查询条件
     */

    public static Map pageableSort(Pageable pageable, Map map) {
        if (pageable.getSort() != null && !"UNSORTED".equals(String.valueOf(pageable.getSort()))) {
            String sort = String.valueOf(pageable.getSort());
            String sortParameter = sort.replace(":", "");
            map.put("sortParameter", sortParameter);
        }
        return map;
    }

    /**
     * 处理分页结构数据 添加code字段
     *
     * @param page
     * @return
     */
    public static Object addLayUiCode(Page page) {
        Map map = null;
        if (page != null) {
            map = JSONObject.parseObject(JSONObject.toJSONString(page), Map.class);
            if (map != null) {
                map.put("msg", "成功");
            }
            if (map == null) {
                map = new HashMap(1);
                map.put("msg", "无数据");
            }
            map.put("code", 0);
        }
        return map;
    }

    /**
     * 处理分页结构数据 添加code和权籍管理代码字段
     *
     * @param page
     * @return
     */
    public static Object addLayUiCodeWithQjgldm(Page<Map> page,String qjgldm) {
        Map map = null;
        if (page != null) {
            if(CollectionUtils.isNotEmpty(page.getContent()) &&StringUtils.isNotBlank(qjgldm)){
                for(Map content:page.getContent()){
                    content.put("qjgldm",qjgldm);
                }
            }
            map = JSONObject.parseObject(JSONObject.toJSONString(page), Map.class);
            if (map != null) {
                map.put("msg", "成功");
            }
            if (map == null) {
                map = new HashMap(1);
                map.put("msg", "无数据");
            }
            map.put("code", 0);
        }
        return map;
    }

    /**
     * 处理分页结构数据 添加code字段
     *
     * @param page
     * @return
     */
    public static Object initEmptyLayUiCode() {
        Map map = null;
        map = new HashMap(1);
        map.put("msg", "无数据");
        return map;
    }

    /**
     * sly 前台分页默认从1开始，后台分页默认从0开始，在这里做减1处理
     *
     * @param pageable
     * @return
     */
    public static Pageable delPageRequest(Pageable pageable) {
        if (pageable.getPageNumber() > 0) {
            return new PageRequest(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        } else {
            return pageable;
        }
    }

    /**
     * list数据集转为分页对象
     *
     * @param inList   数据集list
     * @param pageable 分页参数对象
     * @return org.springframework.data.domain.Page
     * @date 2019.03.13 16:15
     * @author hanyaning
     */
    public static Page listToPage(List inList, Pageable pageable) {
        if (CollectionUtils.isNotEmpty(inList)) {
            int pageNumber = pageable.getPageNumber();
            int size = pageable.getPageSize();
            int total = inList.size();
            if (total > (pageNumber + 1) * size) {
                inList = inList.subList(size * pageNumber, (pageNumber + 1) * size);
            } else {
                inList = inList.subList(size * pageNumber, total);
            }
            return new GTAutoConfiguration.DefaultPageImpl(inList, pageNumber, size, total);
        } else {
            return new PageImpl(new ArrayList(), pageable, 0);
        }

    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [inList, pageable]
     * @return org.springframework.data.domain.Page
     * @description
     */
    public static Page convertListToPage(List inList, Pageable pageable) {
        if (CollectionUtils.isNotEmpty(inList)) {
            int pageNumber = pageable.getPageNumber();
            int size = pageable.getPageSize();
            int total = inList.size();
            if(size * pageNumber > total){
                return new PageImpl(new ArrayList(), pageable, 0);
            }
            if (total > (pageNumber + 1) * size) {
                inList = inList.subList(size * pageNumber, (pageNumber + 1) * size);
            } else {
                inList = inList.subList(size * pageNumber, total);
            }
            return new GTAutoConfiguration.DefaultPageImpl(inList, pageNumber, size, total);
        } else {
            return new PageImpl(new ArrayList(), pageable, 0);
        }

    }

    /**
     * list数据集转为分页对象
     *
     * @param inList   数据集list
     * @param pageable 分页参数对象
     * @return org.springframework.data.domain.Page
     * @date 2019.03.13 16:15
     * @author hanyaning
     */
    public static Page listToPageWithTotal(List inList, Pageable pageable, int total) {
        if (CollectionUtils.isNotEmpty(inList)) {
            int pageNumber = pageable.getPageNumber();
            int size = pageable.getPageSize();
            return new GTAutoConfiguration.DefaultPageImpl(inList, pageNumber, size, total);
        } else {
            return new PageImpl(new ArrayList(), pageable, total);
        }

    }

    /**
     * @description 将PageDTO转化成Pageable
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/6/28 17:10
     * @param pageDTO
     * @return Pageable
     */
    public static Pageable pageDtoTOPageable(PageDTO pageDTO) {
        if (pageDTO == null) {
            throw new NullPointerException("分页信息不能为空");
        }
        Sort sort = null;
        if (StringUtils.isNotBlank(pageDTO.getSort())) {
            String sortStr = pageDTO.getSort();
            String[] arr = sortStr.split(",");
            if (sortStr.endsWith(Sort.Direction.ASC.name().toLowerCase())
                    || sortStr.endsWith(Sort.Direction.DESC.name().toLowerCase())) {
                String dir = arr[arr.length - 1];
                String propertys = sortStr.replace("," + dir, "");
                sort = new Sort(Sort.Direction.fromString(dir), propertys.split(","));
            } else {
                sort = new Sort(arr);
            }
        }
        int page = StringUtils.isNotBlank(pageDTO.getPage()) ? Integer.parseInt(pageDTO.getPage()) : 0;
        int size = StringUtils.isNotBlank(pageDTO.getSize()) ? Integer.parseInt(pageDTO.getSize()) : 10;
        PageRequest pageRequest = new PageRequest(page, size, sort);
        // page 需要减一
        return delPageRequest(pageRequest);
    }


}
