package cn.gtmap.interchange.service.impl;

import cn.gtmap.interchange.core.domain.InfApply;
import cn.gtmap.interchange.core.mapper.gx.InfApplyMapper;
import cn.gtmap.interchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.interchange.service.InfApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class InfApplyServiceImpl implements InfApplyService {

    @Autowired
    private InfApplyMapper infApplyMapper;

    @Autowired
    @Qualifier("gxEntityMapper")
    private EntityMapper gxEntityMapper;

    @Override
    public List<InfApply> getInfApply(Map map) {
        return infApplyMapper.getInfApply(map);
    }

    @Override
    public List<InfApply> queryWtbInfApply(Map map) {
        return infApplyMapper.queryWtbInfApply(map);
    }

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @rerutn
     * @description 获取序列，用于一张网编号
     */
    @Override
    public Integer getYzwBhSeq() {
        return infApplyMapper.getYzwBhSeq();
    }

    @Override
    public void modifyInfApply(InfApply infApply) {
        if(null != infApply && StringUtils.isNoneBlank(infApply.getNo())){
            gxEntityMapper.updateByPrimaryKey(infApply);
        }
    }
}
