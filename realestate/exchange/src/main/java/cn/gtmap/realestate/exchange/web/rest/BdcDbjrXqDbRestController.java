package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.exchange.core.dto.DbJrDbMxDTO;
import cn.gtmap.realestate.exchange.core.dto.DbJrXqSjlDTO;
import cn.gtmap.realestate.exchange.core.qo.BdcDbJrXqQO;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 登簿接入详情数据对比
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-06 09:42
 **/
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/dbjrxqdb")
@Api(tags = "不动产登簿接入详情数据对比相关服务")
public class BdcDbjrXqDbRestController {

    @Autowired
    AccessLogService accessLogService;
    @Autowired
    Repo repository;


    @GetMapping("")
    public Object listDbJrXq(@LayuiPageable Pageable pageable, BdcDbJrXqQO bdcDbJrXqQO) {
        if (Objects.nonNull(bdcDbJrXqQO) && (StringUtils.isNotBlank(bdcDbJrXqQO.getKssj()) && StringUtils.isNotBlank(bdcDbJrXqQO.getJssj()))) {
            //获取每一天的数据
            //1. 分页查询天数，分页后的天数再查询每一天的数据量
            Page<Date> datePage = repository.selectPaging("findDatesByPage", bdcDbJrXqQO, pageable);
            List<DbJrXqSjlDTO> dbJrXqSjlDTOList = new ArrayList<>(1);
            if (Objects.nonNull(datePage) && CollectionUtils.isNotEmpty(datePage.getContent())) {
                for (Date date : datePage.getContent()) {
                    bdcDbJrXqQO = new BdcDbJrXqQO();
                    bdcDbJrXqQO.setDjsj(DateUtils.formateTime(date, DateUtils.DATE_FORMAT_2));
                    DbJrXqSjlDTO dbJrXqSjlDTO = new DbJrXqSjlDTO();
                    //查询时间内的登簿数据
                    dbJrXqSjlDTO.setDbsjl(accessLogService.queryDbSjl(bdcDbJrXqQO));
                    //查询时间内的接入数据
                    dbJrXqSjlDTO.setJrsjl(accessLogService.queryJrSjl(bdcDbJrXqQO));
                    //查询时间内的登簿日志详情数据量
                    dbJrXqSjlDTO.setDbrzxqsjl(accessLogService.queryDbrzXqSjl(bdcDbJrXqQO));
                    dbJrXqSjlDTO.setDbsj(bdcDbJrXqQO.getDjsj());
                    //判断是否异常，异常数据页面增加底色
                    if (dbJrXqSjlDTO.getDbsjl() < dbJrXqSjlDTO.getJrsjl()) {
                        dbJrXqSjlDTO.setTsxx("当天登簿量小于接入量");
                    } else if (dbJrXqSjlDTO.getDbsjl() > dbJrXqSjlDTO.getJrsjl()) {
                        dbJrXqSjlDTO.setTsxx("当天登簿量大于接入量");
                    }
                    dbJrXqSjlDTOList.add(dbJrXqSjlDTO);
                }
            }
            Pageable pageable1 = new PageRequest(datePage.getTotalPages() - 1, pageable.getPageSize());
            return new PageImpl(dbJrXqSjlDTOList, pageable1, datePage.getTotalElements());
        }
        return PageUtils.listToPage(new ArrayList(), new PageRequest(0, 10));
    }

    /*
     * 上报时间线
     * */
    @GetMapping("/sbsjx")
    public Object querySbsjx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            return accessLogService.queryJrCzrz(xmid);
        }
        return "";
    }

    /**
     * @param bdcDbJrXqQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 登簿量接入量对比明细
     * @date : 2022/7/28 9:47
     */
    @GetMapping("/mx")
    public Object listDbJrDbmx(@LayuiPageable Pageable pageable, BdcDbJrXqQO bdcDbJrXqQO) {
        List<DbJrDbMxDTO> dbJrDbMxDTOList = accessLogService.listDbJrDbMxDTO(bdcDbJrXqQO);
        return new PageImpl(dbJrDbMxDTOList, pageable, CollectionUtils.size(dbJrDbMxDTOList));
    }

}
