package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlYcslDjywDzbService;
import cn.gtmap.realestate.accept.service.BdcSlService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJbxxQO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理基本信息数据服务
 */
@Service
public class BdcSlJbxxServiceImpl implements BdcSlJbxxService, BdcSlService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcSlYcslDjywDzbService bdcSlYcslDjywDzbService;

    /**
     * @param jbxxid 基本信息ID
     * @return 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取不动产受理基本信息
     */
    @Override
    public BdcSlJbxxDO queryBdcSlJbxxByJbxxid(String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlJbxxDO.class, jbxxid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理基本信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据工作流实例ID获取不动产受理基本信息
     */
    @Override
    public BdcSlJbxxDO queryBdcSlJbxxByGzlslid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlJbxxDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            List<BdcSlJbxxDO> bdcSlJbxxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcSlJbxxDOList)) {
                return bdcSlJbxxDOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理基本信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据工作流实例ID获取不动产受理基本信息
     */
    @Override
    public List<BdcSlJbxxDO> queryBdcSlJbxxByGzlslids(List<String> gzlslid) {
        if (CollectionUtils.isNotEmpty(gzlslid)) {
            Example example = new Example(BdcSlJbxxDO.class);
            List<Object> gzlslidList = new ArrayList<>(gzlslid);
            example.createCriteria().andIn("gzlslid", gzlslidList);
            List<BdcSlJbxxDO> bdcSlJbxxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcSlJbxxDOList)) {
                return bdcSlJbxxDOList;
            }
        }
        return null;
    }

    /**
     * @param slbh
     * @param type 1:一窗受理 2 登记,其他值或空任取一条
     * @return 不动产受理基本信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据slbh获取不动产受理基本信息
     */
    @Override
    public BdcSlJbxxDO queryBdcSlJbxxBySlbh(String slbh,String type) {
        if (StringUtils.isNotBlank(slbh)) {
            Example example = new Example(BdcSlJbxxDO.class);
            example.createCriteria().andEqualTo("slbh", slbh);
            List<BdcSlJbxxDO> bdcSlJbxxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcSlJbxxDOList)) {
                if(!StringUtils.equals(CommonConstantUtils.JBXX_TYPE_YCSL, type) &&!StringUtils.equals(CommonConstantUtils.JBXX_TYPE_DJ, type)){
                    return bdcSlJbxxDOList.get(0);
                }
                for(BdcSlJbxxDO bdcSlJbxxDO:bdcSlJbxxDOList) {
                    if ((StringUtils.equals(CommonConstantUtils.JBXX_TYPE_YCSL, type) &&bdcSlYcslDjywDzbService.checkIsYcslLc(bdcSlJbxxDO.getGzldyid())) ||(StringUtils.equals(CommonConstantUtils.JBXX_TYPE_DJ, type) &&!bdcSlYcslDjywDzbService.checkIsYcslLc(bdcSlJbxxDO.getGzldyid()))) {
                        return bdcSlJbxxDO;
                    }

                }
            }
        }
        return null;
    }

    /**
     * @param bdcSlJbxxDO 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理基本信息
     */
    @Override
    public BdcSlJbxxDO insertBdcSlJbxx(BdcSlJbxxDO bdcSlJbxxDO) {
        if (bdcSlJbxxDO != null) {
            if (StringUtils.isBlank(bdcSlJbxxDO.getJbxxid())) {
                bdcSlJbxxDO.setJbxxid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlJbxxDO);
        }
        return bdcSlJbxxDO;
    }

    /**
     * @param bdcSlJbxxDO 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理基本信息
     */
    @Override
    public Integer updateBdcSlJbxx(BdcSlJbxxDO bdcSlJbxxDO) {
        int result;
        if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getJbxxid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlJbxxDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID删除不动产受理基本信息
     */
    @Override
    public Integer deleteBdcSlJbxxByJbxxid(String jbxxid) {
        int result = 0;
        if (StringUtils.isNotBlank(jbxxid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlJbxxDO.class, jbxxid);
        }
        return result;
    }

    /**
     * @param jbxxid     基本信息ID
     * @param bdcSlxxDTO 受理信息
     * @return 受理信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取受理信息
     */
    @Override
    public BdcSlxxDTO queryBdcSlxx(String jbxxid, BdcSlxxDTO bdcSlxxDTO,Integer sfzlcsh) {
        BdcSlJbxxDO bdcSlJbxxDO = queryBdcSlJbxxByJbxxid(jbxxid);
        if (bdcSlJbxxDO != null) {
            bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        }
        return bdcSlxxDTO;
    }

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID更新受理信息
     */
    @Override
    public void updateBdcSlxx(BdcSlxxDTO bdcSlxxDTO) {
        if (bdcSlxxDTO != null && bdcSlxxDTO.getBdcSlJbxx() != null && StringUtils.isNotBlank(bdcSlxxDTO.getBdcSlJbxx().getJbxxid())) {
            updateBdcSlJbxx(bdcSlxxDTO.getBdcSlJbxx());
        }
    }

    /**
     * @param jbxxid 基本信息ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID,工作流实例ID删除受理信息(全部信息删除)
     */
    @Override
    public void deleteSlxx(String jbxxid,String gzlslid) {
        deleteBdcSlJbxxByJbxxid(jbxxid);
    }

    /**
     * @param bdcSlJbxxQO 受理基本信息
     * @return 受理基本信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description  获取不动产受理基本信息
     */
    @Override
    public List<BdcSlJbxxDO> listBdcSlJbxxByJbxxQO(BdcSlJbxxQO bdcSlJbxxQO) {
        if (!CheckParameter.checkAnyParameter(bdcSlJbxxQO, "jbxxid", "slbh", "fwuuid")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        Example example = new Example(BdcSlJbxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bdcSlJbxxQO.getJbxxid())) {
            criteria.andEqualTo("jbxxid", bdcSlJbxxQO.getJbxxid());
        }
        if (StringUtils.isNotBlank(bdcSlJbxxQO.getSlbh())) {
            criteria.andEqualTo("slbh", bdcSlJbxxQO.getSlbh());
        }
        if (StringUtils.isNotBlank(bdcSlJbxxQO.getFwuuid())) {
            criteria.andEqualTo("fwuuid", bdcSlJbxxQO.getFwuuid());
        }
        return entityMapper.selectByExample(example);
    }


}
