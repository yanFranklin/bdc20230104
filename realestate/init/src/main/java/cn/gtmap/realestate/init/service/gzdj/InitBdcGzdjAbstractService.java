package cn.gtmap.realestate.init.service.gzdj;

import cn.gtmap.realestate.common.core.domain.BdcGzdjDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcGzdjService;
import cn.gtmap.realestate.init.service.InitService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/13
 * @description 更正登记
 */
public abstract class InitBdcGzdjAbstractService implements InitService {

    @Autowired
    protected EntityMapper entityMapper;

    @Autowired
    BdcGzdjService bdcGzdjService;

    @Override
    public String getCode() {
        return "sfgzdj";
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        // 参数空值返回(同步权籍不做此处理)
        if(initServiceQO == null || initServiceQO.isSfdzbflpbsj() || initServiceQO.isSfzqlpbsj()){
            return initServiceDTO;
        }
        if (initServiceDTO == null) {
            initServiceDTO = new InitServiceDTO();
        }
        if(initServiceDTO.getBdcXm() != null) {
            initServiceDTO.setBdcGzdj(initBdcGzdj(initServiceQO));
        }
        return initServiceDTO;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean plDel) throws Exception {
        List<Object> deleteList=new ArrayList<>();
        //抓取楼盘表数据不做此处理
        if(CollectionUtils.isNotEmpty(list) &&!sfzqlpbsj &&!sfdzbflpbsj){
            if(Boolean.TRUE.equals(plDel)){
                List<String> xmidList = list.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                bdcGzdjService.deleteBdcGzdjPl(xmidList);
            }else{
                for(BdcXmDO bdcXmDO:list){
                    List<BdcGzdjDO> bdcGzdjDOList=bdcGzdjService.listBdcGzdjByXmid(bdcXmDO.getXmid());
                    if(CollectionUtils.isNotEmpty(bdcGzdjDOList)){
                        deleteList.addAll(bdcGzdjDOList);
                    }
                }
            }
        }
        return deleteList;

    }

    @Override
    public InitServiceDTO query(BdcXmDO bdcXmDO, InitServiceDTO initServiceDTO) throws Exception{
        if(initServiceDTO == null){
            initServiceDTO = new InitServiceDTO();
        }
        if(bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getXmid())){
            BdcGzdjDO bdcGzdjDO=entityMapper.selectByPrimaryKey(BdcGzdjDO.class,bdcXmDO.getXmid());
            if(bdcGzdjDO!=null){
                initServiceDTO.setBdcGzdj(bdcGzdjDO);
            }
        }
        return initServiceDTO;
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 初始化更正信息
      */
    public abstract BdcGzdjDO initBdcGzdj(InitServiceQO initServiceQO);


}
