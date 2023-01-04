package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmZsGxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/30
 * @description 不动产项目查询实现类
 */
@Service
public class BdcXmServiceImpl implements BdcXmService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcXmMapper bdcXmMapper;

    /**
     * @param xmid 项目ID
     * @param bz   备注
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目的备注内容
     */
    @Override
    public int updateXmBz(String xmid, String bz) {
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(xmid);
        bdcXmDO.setBz(bz);
        return entityMapper.updateByPrimaryKeySelective(bdcXmDO);
    }

    /**
     * @param xmid 项目ID
     * @param fzyj 发证意见
     * @return int 更新数据量
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 更新项目附表的发证意见
     */
    @Override
    public int updateXmFbFzyj(String xmid, String fzyj) {
        BdcXmFbDO bdcXmFbDO = new BdcXmFbDO();
        bdcXmFbDO.setXmid(xmid);
        bdcXmFbDO.setFzyj(fzyj);
        return entityMapper.updateByPrimaryKeySelective(bdcXmFbDO);
    }

    /**
     * @param xmid    项目ID
     * @param gzlslid 工作流实例ID
     * @return List<BdcXmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程所有的项目信息
     */
    @Override
    public List<BdcXmDO> queryLcAllBdcXm(String xmid, String gzlslid) {
        if (StringUtils.isBlank(xmid) && StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少项目ID或工作流ID");
        }
        String gzlslidParam;
        if (StringUtils.isNotBlank(gzlslid)) {
            gzlslidParam = gzlslid;
        } else {
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            gzlslidParam = bdcXmDO.getGzlslid();
        }

        Example example = new Example(BdcXmDO.class);
        example.setOrderByClause("xmid ASC");
        example.createCriteria().andEqualTo("gzlslid", gzlslidParam);
        return entityMapper.selectByExampleNotNull(example);
    }
    /**
     * @param xmid    项目ID
     * @param gzlslid 工作流实例ID
     * @return List<BdcXmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程所有的项目信息
     */
    @Override
    public List<BdcXmFbDO> queryLcAllBdcXmFb(String xmid, String gzlslid) {
        if (StringUtils.isBlank(xmid) && StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少项目ID或工作流ID");
        }
        String gzlslidParam;
        if (StringUtils.isNotBlank(gzlslid)) {
            gzlslidParam = gzlslid;
        } else {
            BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, xmid);
            gzlslidParam = bdcXmFbDO.getGzlslid();
        }

        Example example = new Example(BdcXmFbDO.class);
        example.setOrderByClause("xmid ASC");
        example.createCriteria().andEqualTo("gzlslid", gzlslidParam);
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return 历史关系
     * @description 通过项目id去获取关系表
     */
    @Override
    public List<BdcXmLsgxDO> queryBdcXmLsgxByXmid(String xmid) {
        if(StringUtils.isBlank(xmid)){
            return Collections.emptyList();
        }

        BdcXmLsgxDO bdcXmLsgxDO=new BdcXmLsgxDO();
        bdcXmLsgxDO.setXmid(xmid);
        return entityMapper.selectByObj(bdcXmLsgxDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {BdcXmDO} 不动产项目
     * @description 根据项目ID获取对应项目
     */
    @Override
    public BdcXmDO queryBdcXm(String xmid) {
        if(StringUtils.isBlank(xmid)){
            return null;
        }

        return entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作实例ID
     * @return {List} 项目集合
     * @description 获取工作流实例关联的项目
     */
    @Override
    public List<BdcXmDO> listBdcXmByProInsID(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            return null;
        }
        return bdcXmMapper.listBdcXmByProInsID(gzlslid);
    }

    @Override
    public List<BdcXmFbDO> listBdcXmfbByProInsID(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            return null;
        }
        return bdcXmMapper.listBdcXmfbByProInsID(gzlslid);
    }

    /**
     * @author  <a href="mailto:zhangzinyu@gtmap.cn">zhangxinyu</a>
     * @param   zsid
     * @return  {List} 项目集合
     * @description  获取zsid关联的项目附表
     */
    @Override
    public List<BdcXmFbDO> listBdcXmfbByZsid(String zsid){
        if(StringUtils.isBlank(zsid)){
            return null;
        }
        return bdcXmMapper.listBdcXmfbByZsid(zsid);
    }

    @Override
    public List<BdcXmDTO> listBdcXmBfxxByProInsIDList(List<String> gzlslidList) {
        if(CollectionUtils.isEmpty(gzlslidList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        return bdcXmMapper.listBdcXmBfxxByProInsIDList(gzlslidList);
    }

    /**
     * @param zsid 证书ID
     * @return List<BdcXmZsGxDO> 项目证书关系
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据zsid查询项目证书关系
     */
    @Override
    public List<BdcXmZsGxDO> queryBdcXmZsgxByZsid(String zsid) {
        if (StringUtils.isBlank(zsid)) {
            return null;
        }
        Example example = new Example(BdcXmZsGxDO.class);
        example.setOrderByClause("xmid ASC");
        example.createCriteria().andEqualTo("zsid", zsid);
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param djsj        登记时间
     * @param datePattern 时间格式模式
     * @param qszt        权属状态
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据登记时间和权属状态查询项目信息
     */
    @Override
    public List<BdcXmDO> listBdcXmByDjsj(Date djsj, String datePattern, Integer qszt) {
        if (null == djsj || StringUtils.isBlank(datePattern) || null == qszt) {
            throw new MissingArgumentException("缺失查询参数 djsj或者 datePattern 或者 qszt！");
        }
        List<BdcXmDO> bdcXmDOList = new ArrayList();

        Example example = new Example(BdcXmDO.class);
        // 为了避免索引失效，所以分开多次查询
        Example.Criteria criteria = example.createCriteria();
        criteria.andFormatDateEqualTo("djsj", DateUtils.format(djsj, datePattern), datePattern);
        criteria.andEqualTo("qszt", qszt);

        List<BdcXmDO> bdcXmDOS = entityMapper.selectByExampleNotNull(example);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(bdcXmDOS)) {
            bdcXmDOList.addAll(bdcXmDOS);
        }

        return bdcXmDOList;
    }

    /**
     *
     * @author <a href="mailto:zedeqaing@gtmap.cn">zedq</a>
     * @description 根据受理编号获取相关的流程实例id号
     * @param slbhList
     * @return
     */
    @Override
    public List<BdcXmDO> listBdcXmFiles(List<String> slbhList) {
        //check input
        if (slbhList==null && slbhList.size()==0){
            throw new MissingArgumentException("缺失必要查询参数slbhList");
        }
        return bdcXmMapper.listBdcXmByslbhList(slbhList);
    }

    @Override
    public List<BdcXmDO> listBdcXmByZsid(String zsid) {
        if(StringUtils.isBlank(zsid)){
            throw new MissingArgumentException("未获取到证书ID参数。");
        }
        return bdcXmMapper.listBdcXmByZsid(zsid);
    }

    @Override
    public List<BdcXmZsGxDO> listBdcXmZsGxByGzlslidList(List<String> gzlslidList) {
        if(CollectionUtils.isEmpty(gzlslidList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID集合");
        }
        return bdcXmMapper.listBdcXmZsGxByGzlslidList(gzlslidList);
    }
}
