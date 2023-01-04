package cn.gtmap.realestate.init.service.qlxx.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlxxAbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;


/**
 * 注销权利信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description
 */
@Service
public class InitBdcQlxxZxServiceImpl extends InitBdcQlxxAbstractService {

    @Autowired
    private InitServiceQoService initServiceQoService;


    @Override
    public String getVal() {
        return CommonConstantUtils.SF_F_DM.toString();
    }


    /**
     * 初始化权利信息接口
     *
     * @param initServiceQO  初始化所需数据结构
     * @param initServiceDTO
     * @return 返回所有权利相关信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO initQlxx(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //注销更新原权利信息
        if(!initServiceQO.isSfzqlpbsj() &&!initServiceQO.isSfdzbflpbsj() && initServiceDTO!=null &&initServiceQO !=null &&StringUtils.isNotBlank(initServiceQO.getYxmid())){
            BdcQl ybdcQl=initServiceQoService.queryYql(initServiceQO.getYxmid(),initServiceQO);
            if(ybdcQl instanceof BdcDyaqDO ||ybdcQl instanceof BdcCfDO){
                boolean updateYql =false;
                String qlbs = StringUtils.lowerCase(ybdcQl.getClass().getAnnotation(Table.class).name()).replace("bdc_","");
                //第三方信息不为空的话做对照
                if(initServiceQO.getDsfSlxxDTO()!=null){
                    dozerUtils.initBeanDateConvert(initServiceQO.getDsfSlxxDTO(),ybdcQl,"zxql_dsfslxx_"+qlbs);
                    updateYql =true;
                }
                //受理权利不为空的话做对照
                if(initServiceQO.getBdcSlQl()!=null){
                    dozerUtils.initBeanDateConvert(initServiceQO.getBdcSlQl(),ybdcQl,"zxql_slql_"+qlbs);
                    updateYql =true;
                }
                if(Boolean.TRUE.equals(updateYql)) {
                    entityMapper.updateByPrimaryKeySelective(ybdcQl);
                }

            }

        }
        return initServiceDTO;
    }
}
