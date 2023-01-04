package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDysdQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.core.mapper.BdcSdMapper;
import cn.gtmap.realestate.register.core.service.BdcdySdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/17
 * @description 不动产单元锁定实现类
 */
@Service
public class BdcdySdServiceImpl implements BdcdySdService {
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcSdMapper bdcSdMapper;

    /**
     * @param bdcdyh 不动产单元号
     * @param sdzt   锁定状态
     * @return BdcDysdDO 查询到的锁定结果集
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元锁定结果
     */
    @Override
    public List<BdcDysdDO> queryBdcDysd(String bdcdyh, Integer sdzt, Integer bdclx) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(BdcDysdDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bdcdyh", bdcdyh);
            if (null != bdclx) {
                criteria.andEqualTo("bdclx", bdcdyh);
            }
            if (null != sdzt) {
                criteria.andEqualTo("sdzt", sdzt);
            }
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList();
    }

    /**
     * @param bdcDysdQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量查询单元锁定信息
     * @date : 2021/8/19 15:31
     */
    @Override
    public List<BdcDysdDO> listBdcDySd(BdcDysdQO bdcDysdQO) {
        if (CheckParameter.checkAnyParameter(bdcDysdQO)) {
            Example example = new Example(BdcDysdDO.class);
            Example.Criteria criteria = example.createCriteria();
            if (CollectionUtils.isNotEmpty(bdcDysdQO.getBdcdyhList())) {
                criteria.andInWithListString("bdcdyh", bdcDysdQO.getBdcdyhList());
            }
            if (null != bdcDysdQO.getSdlx()) {
                criteria.andEqualTo("sdlx", bdcDysdQO.getSdlx());
            }
            if (null != bdcDysdQO.getSdzt()) {
                criteria.andEqualTo("sdzt", bdcDysdQO.getSdzt());
            }
            if (StringUtils.isNotBlank(bdcDysdQO.getGzlslid())) {
                criteria.andEqualTo("gzlslid", bdcDysdQO.getGzlslid());
            }
            if (StringUtils.isNotBlank(bdcDysdQO.getXmid())) {
                criteria.andEqualTo("xmid", bdcDysdQO.getXmid());
            }
            if (StringUtils.isNotBlank(bdcDysdQO.getDysdid())) {
                criteria.andEqualTo("dysdid", bdcDysdQO.getDysdid());
            }
            return entityMapper.selectByExampleNotNull(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param sdzt   锁定状态
     * @param bdclx
     * @return BdcDysdDO 查询到的锁定结果集
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产证书锁定结果
     */
    @Override
    public List<BdcZssdDO> queryBdZsSd(String bdcdyh, Integer sdzt, Integer bdclx) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            //取得现势的产权
            //根据产权取得证书关联
            //取得证书
            //取得证书锁定
            return bdcSdMapper.queryBdcZssdByBdcdyh(bdcdyh,sdzt);
        }
        return new ArrayList();
    }

    /**
     * 获取不动产锁定数量
     * @param bdcdyh
     * @return
     */
    @Override
    public Integer queryBdcXssdcs(String bdcdyh) {
        List<BdcDysdDO> bdcDysdDOList = queryBdcDysd(bdcdyh, CommonConstantUtils.SDZT_SD, null);
        int bdcDysdSize = 0;
        if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
            bdcDysdSize = bdcDysdDOList.size();
        }
        List<BdcZssdDO> bdcZssdDOList = queryBdZsSd(bdcdyh, CommonConstantUtils.SDZT_SD, null);
        int bdcZssdSize = 0;
        if(CollectionUtils.isNotEmpty(bdcZssdDOList)){
            bdcZssdSize = bdcZssdDOList.size();
        }
//        List<BdcDysdDO> bdcDyjsDOList = queryBdcDysd(bdcdyh, CommonConstantUtils.SDZT_JS, null);
//        int bdcDyjsSize = 0;
//        if(CollectionUtils.isNotEmpty(bdcDyjsDOList)){
//            bdcDyjsSize = bdcDyjsDOList.size();
//        }
//        List<BdcZssdDO> bdcZsjsDOList = queryBdZsSd(bdcdyh, CommonConstantUtils.SDZT_JS, null);
//        int bdcZsjsSize = 0;
//        if(CollectionUtils.isNotEmpty(bdcZsjsDOList)){
//            bdcZsjsSize = bdcZsjsDOList.size();
//        }
        int xssdcs = bdcDysdSize + bdcZssdSize;
        return Math.max(xssdcs, 0);
    }

    /**
     * 查询证书锁定下的所有的不动产单元号
     * @param bdcdyhList
     * @param sdzt
     * @return
     */
    @Override
    public List<String> queryBdcZssdBdcdyh(List<String> bdcdyhList, Integer sdzt) {
        return bdcSdMapper.queryBdcZssdBdcdyh(bdcdyhList,sdzt);
    }
}
