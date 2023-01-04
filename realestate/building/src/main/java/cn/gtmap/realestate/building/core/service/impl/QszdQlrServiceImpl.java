package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.QszdQlrService;
import cn.gtmap.realestate.building.core.service.QszdService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.common.core.domain.building.HQszdQlrDO;
import cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.QszdQlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-12
 * @description
 */
@Service
public class QszdQlrServiceImpl implements QszdQlrService {

    @Autowired
    private QszdService qszdService;

    @Autowired
    private BdcdyService bdcdyService;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.QszdQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询权属宗地权利人信息
     */
    @Override
    public List<QszdQlrDO> listQszdQlrByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            QszdDjdcbDO qszdDjdcbDO = qszdService.queryQszdDjdcbByBdcdyh(bdcdyh);
            if (qszdDjdcbDO != null && StringUtils.isNotBlank(qszdDjdcbDO.getQszdDjdcbIndex())) {
                return bdcdyService.listQlrByDjDcbIndex(qszdDjdcbDO.getQszdDjdcbIndex(), QszdQlrDO.class);
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.QszdQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据地籍号查询权属宗地权利人信息
     */
    @Override
    public List<QszdQlrDO> listQszdQlrByDjh(String djh) {
        if(StringUtils.isNotBlank(djh)){
            Example example = new Example(QszdQlrDO.class);
            example.createCriteria().andEqualTo("djh", djh);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param qszdDjdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.QszdQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJDCBINDEX 查询 权属宗地权利人
     */
    @Override
    public List<QszdQlrDO> listQszdQlrByQszdDjdcbIndex(String qszdDjdcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(qszdDjdcbIndex, QszdQlrDO.class);
    }

    /**
     * @param qszdDjdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.HQszdQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJDCBINDEX 查询 备份权属宗地权利人
     */
    @Override
    public List<HQszdQlrDO> listHQszdQlrByQszdDjdcbIndex(String qszdDjdcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(qszdDjdcbIndex, HQszdQlrDO.class);
    }
}
