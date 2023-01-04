package cn.gtmap.realestate.init.service.tshz.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 盐城处理抵押顺位
 *
 * @author <a href="mailto:caolu@lixin.com">caolu</a>
 * @version 1.0 2022/3/31
 */
@Service("bdcDyswServiceImpl")
public class InitBdcDyswServiceImpl implements InitBdcTsHzService {
    private static Logger logger = LoggerFactory.getLogger(InitBdcDyswServiceImpl.class);
    @Autowired
    protected EntityMapper entityMapper;
    @Autowired
    protected BdcXmLsgxService bdcXmLsgxService;
    @Value("${dyaq.dysw.xsdyacs: false}")
    private boolean dyswXsdyacs;

    /**
     * 特殊后置处理
     * 盐城需求51881 在建建筑物抵押的抵押顺位调整
     *
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        logger.info("initServiceQO数据：{}", JSON.toJSONString(initServiceQO));
        logger.info("initServiceDTO数据：{}", JSON.toJSONString(initServiceDTO));
        // 房屋不动产单元号在建工程抵押抵押顺位=该不动产单元号所在宗地现势抵押次数+该不动产单元号现势抵押次数+1

        if (initServiceDTO != null && initServiceDTO.getBdcQl() instanceof BdcDyaqDO) {
            BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) initServiceDTO.getBdcQl();
            BdcXmDO bdcXmDO = initServiceDTO.getBdcXm();
            // 处理在建建筑物
            if (bdcDyaqDO != null && CommonConstantUtils.DYBDCLX_ZJJZW.equals(bdcDyaqDO.getDybdclx())
                    && StringUtils.isNotBlank(bdcDyaqDO.getBdcdyh()) && !CommonConstantUtils.DYBDCLX_CTD.equals(bdcXmDO.getBdclx())) {
                // 查询该不动产单元号的现势抵押信息
                Example example = new Example(BdcDyaqDO.class);
                example.createCriteria().andEqualTo("bdcdyh", bdcDyaqDO.getBdcdyh()).andEqualTo("qszt", CommonConstantUtils.QSZT_VALID);
                example.setOrderByClause("dysw desc nulls last");
                List<BdcDyaqDO> list = entityMapper.selectByExample(example);
                // 该不动产单元号所在宗地现势抵押次数
                // 宗地单元号
                String zddyh = bdcDyaqDO.getBdcdyh().substring(0, 19).concat(CommonConstantUtils.SUFFIX_ZD_BDCDYH);
                Example example1 = new Example(BdcDyaqDO.class);
                example1.createCriteria().andEqualTo("bdcdyh", zddyh).andEqualTo("qszt", CommonConstantUtils.QSZT_VALID);
                List<BdcDyaqDO> zdlist = entityMapper.selectByExample(example1);
                int zddy = 0;
                if (CollectionUtils.isNotEmpty(zdlist)){
                    zddy = zdlist.size();
                }
                boolean flag = true;
                if (CollectionUtils.isNotEmpty(list)) {
                    //获取历史关系
                    List<BdcXmLsgxDO> listXmRel;
                    if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcXmLsgxList())){
                        listXmRel=initServiceDTO.getBdcXmLsgxList();
                    }else{
                        listXmRel=bdcXmLsgxService.queryBdcXmLsgxByXmid(bdcDyaqDO.getXmid(), null);
                    }
                    if (CollectionUtils.isNotEmpty(listXmRel)) {
                        //循环关系 匹配
                        xmRel:
                        for (BdcXmLsgxDO bdcXmLsgxDO : listXmRel) {
                            //注销原权利的才做匹配
                            if (CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getZxyql())) {
                                for (BdcDyaqDO bdcdyaq : list) {
                                    //查出的抵押信息和此项目的原项目是否相同，相同就继承抵押顺位
                                    if (StringUtils.equals(bdcdyaq.getXmid(), bdcXmLsgxDO.getYxmid())) {
                                        bdcDyaqDO.setDysw(bdcdyaq.getDysw());
                                        flag = false;
                                        break xmRel;
                                    }
                                }
                            }
                        }
                    }
                    //以上处理抵押顺位还是空的话赋值加1
                    if (flag) {
                        if (dyswXsdyacs) {
                            bdcDyaqDO.setDysw(list.size() + 1);
                        } else {
                            bdcDyaqDO.setDysw(zddy + (Objects.nonNull(list.get(0).getDysw()) ? list.get(0).getDysw() : CollectionUtils.size(list)) + 1);
                        }
                    }
                } else {
                    bdcDyaqDO.setDysw(zddy + 1);
                }

            }

        }
        return initServiceDTO;
    }
}
