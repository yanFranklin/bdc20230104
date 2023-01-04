package cn.gtmap.realestate.init.service.jtcy.impl;
/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/5.
 * @description 从原权利人家庭成员获取义务人家庭成员
 */

import cn.gtmap.gtc.workflow.utils.BeanUtil;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrHkbGxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrHkbGxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrHkbGxQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.service.jtcy.InitBdcYwrJtcyAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class InitYqlrJtcyToBdcYwrJtcyServiceImpl  extends InitBdcYwrJtcyAbstractService {
    @Autowired
    private BdcQlrService bdcQlrService;
    @Override
    public String getVal() {
        return Constants.QLRSJLY_YQLR.toString();
    }
    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(initServiceQO != null && StringUtils.isNotBlank(initServiceQO.getYxmid())){
            // 当前业务义务人数据
            List<BdcQlrDO> ywrList = initServiceQO.getBdcYwrList();
            if(CollectionUtils.isNotEmpty(ywrList)){
                // 获取上手的所有权利人与户口簿关系
                List<BdcQlrHkbGxDTO> bdcQlrHkbGxDTOList = getYqlrHkbgx(initServiceQO.getYxmid());
                // 生成当前业务义务人与户口簿关系数据
                if(CollectionUtils.isNotEmpty(bdcQlrHkbGxDTOList)){
                    List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList = new ArrayList<>();
                    for(BdcQlrDO ywr:ywrList){
                        if(ywr!=null && StringUtils.isNotBlank(ywr.getZjh())){
                            // 在上手权利人户口簿关系数据集合中找出和当前义务人证件号相同的数据
                            List<BdcQlrHkbGxDTO> qlrHkbGxDTOList = bdcQlrHkbGxDTOList.stream().filter(s->s.getZjh().equals(ywr.getZjh())).collect(Collectors.toList());
                            if(CollectionUtils.isNotEmpty(qlrHkbGxDTOList)){
                                bdcQlrHkbGxDOList.add(generateHkbGx(ywr.getQlrid(),qlrHkbGxDTOList.get(0)));
                            }
                        }
                    }
                    initServiceDTO.setBdcQlrHkbGxDOList(bdcQlrHkbGxDOList);
                }
            }
        }
        return initServiceDTO;
    }

    /**
     * 获取上手权利人户口簿关系数据
     * @param yxmid
     * @return
     */
    private List<BdcQlrHkbGxDTO> getYqlrHkbgx(String yxmid){
        BdcQlrHkbGxQO bdcQlrHkbGxQO = new BdcQlrHkbGxQO(yxmid,CommonConstantUtils.QLRLB_QLR);
        return bdcJtcyService.queryBdcQlrHkbGx(bdcQlrHkbGxQO);
    }

    /**
     * 根据上手权利人关系和当前义务人ID生成关系数据
     * @param ywrid
     * @param qlrHkbGxDTO
     * @return
     */
    private BdcQlrHkbGxDO generateHkbGx(String ywrid,BdcQlrHkbGxDTO qlrHkbGxDTO){
        BdcQlrHkbGxDO bdcQlrHkbGxDO = new BdcQlrHkbGxDO();
        BeanUtil.copyBean(qlrHkbGxDTO,bdcQlrHkbGxDO);
        // 生成新的关系
        bdcQlrHkbGxDO.setQlrid(ywrid);
        bdcQlrHkbGxDO.setGxid(UUIDGenerator.generate16());
        return bdcQlrHkbGxDO;
    }
}
