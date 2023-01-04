package cn.gtmap.realestate.inquiry.thread;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZszmDylDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/5/18
 * @description  证书证明打印量记录处理线程任务
 */
@Component
public class BdcZszmDylThread {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZszmDylThread.class);

    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;
    @Autowired
    private EntityMapper entityMapper;

    /**
     * 开启线程同步打印量数据记录至数据库中
     */
    @Async
    public void recordBdcZszmDyl(Map map){
        if(MapUtils.isNotEmpty(map)){
            BdcZszmDylDO bdcZszmDylDO = this.convertMapToZszmDyl(map, null);
            if(StringUtils.isNotBlank(bdcZszmDylDO.getId())){
                this.entityMapper.insertSelective(bdcZszmDylDO);
            }
        }
    }

    /**
     * 初始化批量处理redis中数据，同步数据至数据库中
     */
    @Async
    public void plEecordBdcZszmDyl(List<Map> mapList, Map<String, String> orgMap){
        List<BdcZszmDylDO> bdcZszmDylDOList = new ArrayList<>(mapList.size());
        for(Map map: mapList){
            bdcZszmDylDOList.add(this.convertMapToZszmDyl(map, orgMap));
        }
        List<List> lists = ListUtils.subList(bdcZszmDylDOList, 500);
        for (List partList : lists) {
            this.entityMapper.insertBatchSelective(partList);
        }
    }

    /**
     * 将 Map 转换为 BdcZsZmDylDO 对象
     */
    private BdcZszmDylDO convertMapToZszmDyl(Map map, Map<String, String> orgMap){
        BdcZszmDylDO bdcZszmDylDO = new BdcZszmDylDO();
        if(map.containsKey("CODE")){
            bdcZszmDylDO.setId((String) map.get("CODE"));
        }else{
            bdcZszmDylDO.setId(UUIDGenerator.generate16());
        }
        if(map.containsKey("COUNT")){
            bdcZszmDylDO.setDyl((Integer) map.get("COUNT"));
        }
        if(map.containsKey("ZSLX")){
            bdcZszmDylDO.setZslx((Integer) map.get("ZSLX"));
        }
        if(map.containsKey("ORG")){
            String orgCode = (String) map.get("ORG");
            bdcZszmDylDO.setOrgcode(orgCode);
            bdcZszmDylDO.setOrgname(this.getOrgNameByOrgCode(orgCode, orgMap));
        }
        if(map.containsKey("NAME")){
            bdcZszmDylDO.setDyr((String) map.get("NAME"));
        }
        bdcZszmDylDO.setDysj(new Date());
        return bdcZszmDylDO;
    }

    /**
     * 根据orgCode查找orgName
     */
    private String getOrgNameByOrgCode(String orgCode, Map<String, String> orgMap){
        String orgName = "";
        if(StringUtils.isNotBlank(orgCode)){
            if(MapUtils.isNotEmpty(orgMap)){
                orgName = orgMap.get(orgCode);
            }else{
                List<OrganizationDto> orgDtoList = organizationManagerClient.findRootOrgs();
                if(CollectionUtils.isEmpty(orgDtoList)){
                    return null;
                }
                for(OrganizationDto org: orgDtoList){
                    if(org.getCode().equals(orgCode)){
                        orgName = org.getName();
                        break;
                    }
                }
            }
        }
        return orgName;
    }

}
