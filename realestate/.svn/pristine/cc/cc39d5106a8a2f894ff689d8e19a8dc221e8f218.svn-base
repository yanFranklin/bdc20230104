package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化入库数据之后的服务  领证人增加
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/5.
 * @description
 */
@Service
public class BdcLzrInitServiceImpl extends InitBdcJwService {

    @Autowired
    private EntityMapper entityMapper;
    /**
     * 获取服务版本
     *
     * @return
     */
    @Override
    public List<String> getVersion() {
        List<String> versionList =new ArrayList<>();
        versionList.add(CommonConstantUtils.SYSTEM_VERSION_HF);
        versionList.add(CommonConstantUtils.SYSTEM_VERSION_BB);
        versionList.add(CommonConstantUtils.SYSTEM_VERSION_STD);
        return versionList;
    }

    /**
     * 初始化入库数据之后的服务
     *
     * @param initResultDTO 初始化后的数据
     * @throws Exception
     */
    @Override
    public void initJw(InitResultDTO initResultDTO,List<InitServiceQO> listQO) throws Exception {
        if(initResultDTO!=null && CollectionUtils.isNotEmpty(initResultDTO.getBdcQlrList())){
            if(CollectionUtils.isNotEmpty(listQO) &&listQO.get(0).getDsfSlxxDTO() != null && Boolean.TRUE.equals(listQO.get(0).getDsfSlxxDTO().getHasLzr())) {
                //已存在领证人,无需默认权利人为领证人
                return;
            }
            List<BdcLzrDO> bdcLzrDOList=new ArrayList<>();
             for(BdcQlrDO bdcQlrDO:initResultDTO.getBdcQlrList()){
                 //权利人处理
                 if(StringUtils.equals(bdcQlrDO.getQlrlb(),CommonConstantUtils.QLRLB_QLR)){
                     BdcLzrDO bdcLzrDO=new BdcLzrDO();
                     bdcLzrDO.setLzrmc(bdcQlrDO.getQlrmc());
                     bdcLzrDO.setXmid(bdcQlrDO.getXmid());
                     bdcLzrDO.setLzrzjzl(bdcQlrDO.getZjzl());
                     bdcLzrDO.setLzrdh(bdcQlrDO.getDh());
                     bdcLzrDO.setLzrid(UUIDGenerator.generate16());
                     bdcLzrDO.setLzrzjh(bdcQlrDO.getZjh());
                     bdcLzrDO.setQlrid(bdcQlrDO.getQlrid());
                     bdcLzrDOList.add(bdcLzrDO);
                 }
             }

            //批量新增
            if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                List<List> partList = ListUtils.subList(bdcLzrDOList, 500);
                for (List data : partList) {
                    entityMapper.insertBatchSelective(data);
                }
            }
        }
    }
}
