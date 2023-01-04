package cn.gtmap.realestate.init.service.qlxx.qlfl;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlDataAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Objects;

/**
 * 初始化抵押权信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31.
 * @description
 */
public abstract class InitBdcDyaqAbstractService extends InitBdcQlDataAbstractService {
    @Autowired
    protected BdcXmLsgxService bdcXmLsgxService;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  抵押顺位是否自动计算
     */
    @Value("${dyaq.dysw.zdjs: true}")
    private boolean dyswzdjs;

    @Value("${dyaq.dysw.xsdyacs: false}")
    private boolean dyswXsdyacs;

    /**
     * 处理抵押不动产类型
     *
     * @param bdcXmDO
     * @param bdcDyaqDO
     * @return
     */
    public BdcDyaqDO dealDybdclx(BdcXmDO bdcXmDO, BdcDyaqDO bdcDyaqDO) {
        //处理抵押不动产类型   为空时处理
        if (bdcXmDO != null && bdcXmDO.getBdclx() != null && bdcDyaqDO != null) {
            Integer dybdclx = bdcDzgxConfig.getDybdclxMap().get(bdcXmDO.getBdclx());
            bdcDyaqDO.setDybdclx(dybdclx);
        }
        return bdcDyaqDO;
    }

    /**
     * 债务人处理
     *
     * @param qlrList
     * @param bdcDyaqDO
     * @return
     */
    public BdcDyaqDO dealZwr(List<BdcQlrDO> qlrList, BdcDyaqDO bdcDyaqDO) {
        //债务人处理   为空时处理
        if (CollectionUtils.isNotEmpty(qlrList) && bdcDyaqDO != null && StringUtils.isBlank(bdcDyaqDO.getZwr())) {
            String zwr = StringToolUtils.resolveBeanToAppendStr(qlrList, "getQlrmc", " ");
            bdcDyaqDO.setZwr(zwr);
        }
        return bdcDyaqDO;
    }

    /**
     * 抵押顺位处理
     *
     * @param bdcDyaqDO
     * @return
     */
    public BdcDyaqDO dealDysw(BdcDyaqDO bdcDyaqDO,InitServiceQO initServiceQO) {
        //抵押顺位处理   为空时处理
        if (bdcDyaqDO != null && bdcDyaqDO.getDysw() == null && StringUtils.isNotBlank(bdcDyaqDO.getBdcdyh()) &&Boolean.TRUE.equals(dyswzdjs)) {
            Example example = new Example(BdcDyaqDO.class);
            example.createCriteria().andEqualTo("bdcdyh", bdcDyaqDO.getBdcdyh()).andEqualTo("qszt", CommonConstantUtils.QSZT_VALID);
            example.setOrderByClause("dysw desc nulls last");
            List<BdcDyaqDO> list = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(list)) {
                //查询项目关系表
                List<BdcXmLsgxDO> listXmRel;
                if(CollectionUtils.isNotEmpty(initServiceQO.getBdcXmLsgxList())){
                    listXmRel=initServiceQO.getBdcXmLsgxList();
                }else{
                    listXmRel=bdcXmLsgxService.queryBdcXmLsgxByXmid(bdcDyaqDO.getXmid(), null);
                }
                if (CollectionUtils.isNotEmpty(listXmRel)) {
                    //循环关系 匹配
                    xmRel:for (BdcXmLsgxDO bdcXmLsgxDO : listXmRel) {
                        //注销原权利的才做匹配
                        if (CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getZxyql())) {
                            for (BdcDyaqDO bdcdyaq : list) {
                                //查出的抵押信息和此项目的原项目是否相同，相同就继承抵押顺位
                                if (StringUtils.equals(bdcdyaq.getXmid(), bdcXmLsgxDO.getYxmid())) {
                                    bdcDyaqDO.setDysw(bdcdyaq.getDysw());
                                    return bdcDyaqDO;
                                }
                            }
                        }
                    }
                }
                //以上处理抵押顺位还是空的话赋值加1
                if (bdcDyaqDO.getDysw() == null) {
                    if (dyswXsdyacs) {
                        bdcDyaqDO.setDysw(list.size() + 1);
                    } else {
                        //计算逻辑调整，按照最大抵押顺位+1,空直排在最后
                        bdcDyaqDO.setDysw((Objects.nonNull(list.get(0).getDysw()) ? list.get(0).getDysw() : CollectionUtils.size(list)) + 1);
                    }
                }
            } else {
                bdcDyaqDO.setDysw(1);
            }
        }
        return bdcDyaqDO;
    }


    /**
     * 抵押权数据处理
     *
     * @param initServiceQO
     * @param bdcDyaqDO
     * @return
     */
    public BdcDyaqDO dealDyaq(InitServiceQO initServiceQO, BdcDyaqDO bdcDyaqDO) {
        dealDybdclx(initServiceQO.getBdcXm(), bdcDyaqDO);
        dealZwr(initServiceQO.getBdcYwrList(), bdcDyaqDO);
        dealDysw(bdcDyaqDO,initServiceQO);
        return bdcDyaqDO;
    }
}
