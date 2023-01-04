package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.mapper.BdcQlMapper;
import cn.gtmap.realestate.init.core.service.BdcCfService;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/4.
 * @description
 */
@Service
public class BdcCfServiceImpl  implements BdcCfService {
    @Autowired
    BdcQllxService bdcQllxService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcQlMapper bdcQlMapper;


    /**
     * 处理查封顺序
     *
     * @param bdcCfDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public BdcCfDO dealCfsx(BdcCfDO bdcCfDO) {
        if(bdcCfDO.getCfsx()==null){
            Integer lhsx = 1;
            //轮候顺序增1处理
            Example example = new Example(BdcCfDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bdcdyh",bdcCfDO.getBdcdyh());
            criteria.andEqualTo("qszt", CommonConstantUtils.QSZT_VALID);
            criteria.andIsNotNull("cfsx");
            example.setOrderByClause("cfsx desc");
            List<BdcCfDO> bdcCfList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcCfList)){
                lhsx = bdcCfList.get(0).getCfsx() + 1;
            }
            bdcCfDO.setCfsx(lhsx);
        }
        return  bdcCfDO;
    }

    /**
     * 根据不动产单元和项目ID判定查封类型
     * @param bdcdyh
     * @param xmid
     * @param yxmid
     * @return 查封类型
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public int queryCflx(String bdcdyh, String xmid, String yxmid) {
        int cflx=CommonConstantUtils.CFLX_CF;
        if(StringUtils.isNotBlank(bdcdyh)){
            BdcCfDO bdcCfDO=new BdcCfDO();
            bdcCfDO.setBdcdyh(bdcdyh);
            bdcCfDO.setQszt(CommonConstantUtils.QSZT_VALID);
            List<BdcCfDO> listCf=entityMapper.selectByObj(bdcCfDO);
            List<BdcCfDO> list=new ArrayList<>();
            //过滤掉当前项目
            if(CollectionUtils.isNotEmpty(listCf)){
                for(BdcCfDO cfDO:listCf){
                    if(!StringUtils.equals(cfDO.getXmid(),xmid)){
                        list.add(cfDO);
                    }
                }
            }
            BdcQl yBdcQl =null;
            //原项目id不为空查看原权利
            if(StringUtils.isNotBlank(yxmid)){
                yBdcQl = bdcQllxService.queryQllxDO(yxmid);
            }
            //根据现有的查封信息和原项目信息判定查封类型
            if(CollectionUtils.isNotEmpty(list)){
                if(StringUtils.isBlank(yxmid)){
                    cflx=CommonConstantUtils.CFLX_LHYCF;
                }else{
                    //判定是否是续封
                    if(yBdcQl instanceof BdcCfDO){
                        cflx = CommonConstantUtils.CFLX_XF;
                    }else{
                        cflx=CommonConstantUtils.CFLX_LHCF;
                    }
                }
            }else{
                if(StringUtils.isBlank(yxmid) || yBdcQl instanceof BdcYgDO){
                    cflx=CommonConstantUtils.CFLX_YCF;
                }else{
                    cflx=CommonConstantUtils.CFLX_CF;
                }
            }
        }
        return cflx;
    }

    @Override
    public void updateCfbhPl(String gzlslid,String cfbh){
        if(StringUtils.isNoneBlank(gzlslid,cfbh)) {
            bdcQlMapper.updateCfbhPl(gzlslid, cfbh);
        }

    }
}
