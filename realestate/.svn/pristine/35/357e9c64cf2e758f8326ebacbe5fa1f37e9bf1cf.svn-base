package cn.gtmap.realestate.etl.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.etl.core.mapper.td.TdMapper;
import cn.gtmap.realestate.etl.core.qo.TdsqQO;
import cn.gtmap.realestate.etl.core.service.TdDataService;
import cn.gtmap.realestate.etl.service.td.TdConvertAbstractService;
import cn.gtmap.realestate.etl.service.td.TdConvertStrategyFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Service
public class TdDataServiceImpl implements TdDataService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Qualifier("tdRepository")
    @Autowired(required = false)
    private Repo tdRepository;

    @Qualifier("bdcEntityMapper")
    @Autowired(required = false)
    EntityMapper bdcEntityMapper;

    @Autowired(required = false)
    TdMapper tdMapper;

    @Autowired
    private TdConvertStrategyFactory tdConvertStrategyFactory;

    @Override
    public Page<Map> listTdxxByPage(Pageable pageable, TdsqQO tdsqQO) {
        Page<Map> page = tdRepository.selectPaging("listTdxxByTdPageOrder", tdsqQO, pageable);
        return page;
    }

    @Override
    public void importData(TdsqQO tdsqQO) {
        if(Objects.isNull(tdsqQO.getQllx())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到权利类型");
        }
        if(StringUtils.isBlank(tdsqQO.getXmid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到项目ID");
        }
        BdcXmDO item = this.bdcEntityMapper.selectByPrimaryKey(BdcXmDO.class, tdsqQO.getXmid());
        if(Objects.nonNull(item)){
            logger.info("项目id:" + tdsqQO.getXmid() + "当前土地项目重复导入");
        }
        TdConvertAbstractService tdConvertAbstractService = this.tdConvertStrategyFactory.getInvokeService(tdsqQO.getQllx());
        if(Objects.isNull(tdConvertAbstractService)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到土地数据转换具体实现类");
        }
        tdConvertAbstractService.convertTdDataAndImoprtBdcDj(tdsqQO);
    }

    @Override
    public void modifyImportStatus(String projectid) {
        this.tdMapper.updateTblGyqdjkZt(projectid);
    }
}
