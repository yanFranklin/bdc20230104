package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcZdytjDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZdttjTbxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZdytjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZdytjRestService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.service.BdcZdytjConfigService;
import cn.gtmap.realestate.inquiry.service.DtcxViewService;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author: <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version: V1.0, 2022/09/08
 * @description:
 */
@RestController
@Api(tags = "自定义统计")
public class BdcZdytjRestController extends BaseController implements BdcZdytjRestService {

    @Autowired
    DtcxViewService viewService;
    @Autowired
    BdcZdytjConfigService bdcZdytjConfigService;

    @Override
    public void saveAll(@RequestBody  BdcZdytjDO bdcZdytjDO){
        String cxid = bdcZdytjDO.getCxid();
        // 如果为复制的话重新生成cxid
        if (StringUtils.isBlank(cxid)){
            cxid = UUIDGenerator.generate();
            bdcZdytjDO.setCxid(cxid);
        }
        if (StringUtils.isNotBlank(bdcZdytjDO.getCxsql())){
            String sql = bdcZdytjDO.getCxsql();
            while (sql.indexOf("    ") >= 0){
                sql = sql.replace("    "," ");
            }
            bdcZdytjDO.setCxsql(sql);
        }
        bdcZdytjConfigService.saveAllDtcxCxxx(bdcZdytjDO);
    }

    @Override
    public Page<BdcZdytjDO> listZdytjPage(@RequestBody BdcZdytjQO zdytjQO, int page, int size, Sort sort){
        Pageable pageable = new PageRequest(page,size,sort);
        return bdcZdytjConfigService.listZdytjPage(zdytjQO,pageable);
    }

    @Override
    public BdcZdytjDO getCxxxByCxdh(@PathVariable("cxdh") String cxdh){

        return bdcZdytjConfigService.getConfigsByCxdh(cxdh);
    }
    @Override
    public Boolean checkSql(@RequestBody BdcZdytjDO bdcZdytjDO){
        return bdcZdytjConfigService.checkSql(bdcZdytjDO);
    }
    @Override
    public BdcZdttjTbxxDTO getTbxxByCxdh(@PathVariable("cxdh") String cxdh){

        return bdcZdytjConfigService.getTbxxByCxdh(cxdh);
    }

    @Override
    public void delCxConfig(@PathVariable("cxid") String cxid){

        bdcZdytjConfigService.deleteDtcxCx(cxid);
    }

}