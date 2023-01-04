package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDldmSyqxGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmZsDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.register.core.qo.BdcXmGxQO;
import cn.gtmap.realestate.register.core.qo.BdcXmfbZsxtQO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.realestate.register.core.service.BdcQlService;
import cn.gtmap.realestate.register.service.BdcDbxxService;
import cn.gtmap.realestate.register.service.BdcDjbxxService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 项目信息业务实现类
 */
@Service
public class BdcXmxxServiceImpl implements BdcXmxxService {

    /**
     * 撤销登记
     */
    @Value("#{'${cxdj.gzldyid:}'.split(',')}")
    private List<String> cxdjDyids;
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXmxxServiceImpl.class);

    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = BdcXmxxServiceImpl.class.getName();

    @Autowired
    Set<BdcQlService> bdcQlServiceSet;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcXmMapper bdcXmMapper;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    BdcDjbxxService bdcDjbxxService;
    @Autowired
    BdcDbxxService bdcDbxxService;


    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新登簿信息
     */
    @Override
    public void updateBdcXmDbxx(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getXmid())) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        // 更新项目登簿信息
        bdcXmMapper.updateBdcXmDbxxByXmid(dbxxQO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目的登簿信息和权属状态
     */
    @Override
    public void updateBdcXmDbxxAndQsztByGzlslid(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("dbxxQO缺失工作流实例ID参数");
        }
        bdcXmMapper.updateBdcXmDbxxAndQsztByGzlslid(dbxxQO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @param qllx   权利类型
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据不同的权利更新项目的登簿信息和权属状态
     */
    @Override
    public int updateBdcQlXmDbxxAndQsztPl(DbxxQO dbxxQO, String qllx) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失gzlslid！");
        }
        if (StringUtils.isBlank(qllx)) {
            LOGGER.error("{}未查询到权利信息！", CLASS_NAME);
            return -1;
        }
        dbxxQO.setQllx(Integer.parseInt(qllx));
        int result = bdcXmMapper.updateBdcQlXmDbxxAndQsztPl(dbxxQO);
        LOGGER.info("更新当前权利类型为{}的项目,共{}条！", qllx, result);
        return result;
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新预抵押项目的登簿信息和权属状态
     */
    @Override
    public int updateYdyaXmDbxxAndQsztPl(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID参数");
        }
        return bdcXmMapper.updateYdyaXmDbxxAndQsztPl(dbxxQO);
    }

    /**
     * @param dbxxQO 登簿信息查询QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原项目的权属状态
     */
    @Override
    public int updateYxmQsztByGzlslid(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID参数");
        }
        int result = bdcXmMapper.updateYxmQsztByGzlslid(dbxxQO);
        LOGGER.info("注销{}项目{}条！", dbxxQO.getGzlslid(), result);
        return result;
    }


    /**
     * @param bdcRyzdDTO 冗余字段信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新项目表证号
     */
    @Override
    public int updateBdcXmBdcqzh(BdcRyzdDTO bdcRyzdDTO) {
        if (null == bdcRyzdDTO || StringUtils.isBlank(bdcRyzdDTO.getXmid())) {
            throw new MissingArgumentException("更新项目证号异常：冗余字段信息或项目ID为空！");
        }

        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(bdcRyzdDTO.getXmid());
        bdcXmDO.setBdcqzh(bdcRyzdDTO.getBdcqzhs());
        return entityMapper.updateByPrimaryKeySelective(bdcXmDO);
    }

    /**
     * @param xmid 项目
     * @return List<BdcXmLsgxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据xmid查询List<BdcXmLsgxDO>
     */
    @Override
    public List<BdcXmLsgxDO> getBdcXmLsgxByXmid(String xmid) {
        Example example = new Example(BdcXmLsgxDO.class);
        example.createCriteria().andEqualTo("xmid", xmid);
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param xmid  项目ID
     * @param zxyql 注销原权利（1:是，0：否）
     * @return List<BdcXmLsgxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据xmid查询List<BdcXmLsgxDO>
     */
    @Override
    public List<BdcXmLsgxDO> getBdcXmLsgxByXmidAndZxyql(String xmid, Integer zxyql) {
        if (StringUtils.isNotBlank(xmid) && null != zxyql) {
            Example example = new Example(BdcXmLsgxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid).andEqualTo("zxyql", zxyql);
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList();
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<BdcXmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据工作流实例ID获取项目
     */
    @Override
    public List<BdcXmDO> getListBdcXmByGzlslid(String gzlslid) {
        Example example = new Example(BdcXmDO.class);
        example.setOrderByClause("xmid ASC");
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param xmid 项目ID
     * @param qszt 权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目的权属状态
     */
    @Override
    public int updateBdcXmQszt(String xmid, Integer qszt) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        // 更新项目的权属状态
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(xmid);
        bdcXmDO.setQszt(qszt);
        return bdcXmMapper.updateBdcXmQsztByXmid(bdcXmDO);
    }

    /**
     * @param xmid 项目ID
     * @return BdcDldmSyqxGxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前项目土地的使用期限
     */
    @Override
    public BdcDldmSyqxGxDO queryDldmSyqxGx(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失项目ID");
        }
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        if (null != bdcXmDO && StringUtils.isNotBlank(bdcXmDO.getZdzhyt())) {
            Example example = new Example(BdcDldmSyqxGxDO.class);
            example.createCriteria().andEqualTo("dldm", bdcXmDO.getZdzhyt());
            List<BdcDldmSyqxGxDO> bdcDldmSyqxGxDOList = entityMapper.selectByExampleNotNull(example);
            if (CollectionUtils.isNotEmpty(bdcDldmSyqxGxDOList)) {
                return bdcDldmSyqxGxDOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param bdcRyzdDTO 冗余字段
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新项目表冗余字段
     */
    @Override
    public void updateBdcXmQlrxx(BdcRyzdDTO bdcRyzdDTO) {
        if (StringUtils.isBlank(bdcRyzdDTO.getXmid())) {
            throw new MissingArgumentException("缺失项目ID！");
        }
        bdcXmMapper.updateBdcXmQlrxx(bdcRyzdDTO);
    }

    /**
     * @param bdcRyzdDTO 冗余字段对象DTO
     * @param gzlslid
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新项目表中的权利人信息（权利人相同）
     */
    @Override
    public int updateBdcXmQlrxxPl(BdcRyzdDTO bdcRyzdDTO, String gzlslid) {
        if (StringUtils.isBlank(gzlslid) && CollectionUtils.isEmpty(bdcRyzdDTO.getXmidList())) {
            throw new MissingArgumentException("缺失工作流实例ID！或缺失需要更新的项目ID信息！");
        }
        if (StringUtils.isNotBlank(gzlslid)) {
            // 根据gzlslid 一次性更新
            bdcRyzdDTO.setGzlslid(gzlslid);
            return bdcXmMapper.updateBdcXmQlrxxPl(bdcRyzdDTO);
        } else if (CollectionUtils.isNotEmpty(bdcRyzdDTO.getXmidList())) {
            bdcRyzdDTO.setXmid("");
            // 更新部分项目信息，批量更新
            List<List> xmLists = ListUtils.subList(bdcRyzdDTO.getXmidList(), 800);
            Integer count = 0;
            for (List xmList : xmLists) {
                bdcRyzdDTO.setXmidList(xmList);
                count += bdcXmMapper.updateBdcXmQlrxx(bdcRyzdDTO);
            }
            return count;
        }
        return -1;
    }

    /**
     * @param fwBdcXmDO 房屋不动产项目
     * @param tdqllx    需要查询的土地的权利类型
     * @return BdcXmDO  查询到的土地的项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 已知单元号的房屋产权信息，查询同单元号的土地项目信息
     */
    @Override
    public BdcXmDO queryFwTdXm(BdcXmDO fwBdcXmDO, Integer tdqllx) {
        if (null == fwBdcXmDO || tdqllx == null || StringUtils.isBlank(fwBdcXmDO.getQlr()) || StringUtils.isBlank(fwBdcXmDO.getQlrzjh())) {
            return null;
        }
        String[] qlrArr = StringUtils.split(fwBdcXmDO.getQlr(), CommonConstantUtils.ZF_YW_DH);
        String[] qlrzjhArr = StringUtils.split(fwBdcXmDO.getQlrzjh(), CommonConstantUtils.ZF_YW_DH);
        if (qlrArr.length != qlrzjhArr.length) {
            return null;
        }
        int index = 0;
        Set<String> tdXmDOSet = new HashSet();
        for (String qlr : qlrArr) {
            String qlrzjh = qlrzjhArr[index];
            // 此处是为了利用listXmByPage 分页查询的方法，所以采用map，其他情况请使用实体对象传参
            HashMap bdcXmParam = new HashMap(5);
            bdcXmParam.put("qszt", fwBdcXmDO.getQszt());
            bdcXmParam.put("qlr", qlr);
            bdcXmParam.put("qlrzjh", qlrzjh);
            bdcXmParam.put("bdcdyh", fwBdcXmDO.getBdcdyh());
            bdcXmParam.put("qllx", tdqllx);
            List<BdcXmDO> bdcXmDOList = bdcXmMapper.listXmByPage(bdcXmParam);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                return null;
            }
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (null != bdcXmDO && StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                    tdXmDOSet.add(JSON.toJSONString(bdcXmDO));
                }
            }
            index++;
        }
        // 获取最终结果
        Iterator<String> it = tdXmDOSet.iterator();
        if (it.hasNext()) {
            return JSON.parseObject(it.next(), BdcXmDO.class);
        }
        return null;
    }

    /**
     * @param dbxxQO   登簿信息
     * @param qllxList 权利信息
     * @param isDb
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿，更新项目和权利信息（因为同类中调用其它事务方法会失效，所以讲该方法写在该接口中）
     * 之前事务加在updateBdcDbxxAndQszt方法，
     * 导致aop拦截updateBdcDbxxAndQszt此方法 同步权籍数据的时候 数据不对
     * （由于在同一事务，权利状态尚未提交到数据库，同步方法中就获取，导致数据不一致）
     * 所以将更新方法独立出来加事务
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBdcXmAndQlXx(DbxxQO dbxxQO, List<String> qllxList, boolean isDb) {
        // 更新当前项目和当前权利的权属状态和登簿信息
        bdcDbxxService.updateBdcXmDbxx(dbxxQO, qllxList);
        // 更新原项目和原权利的权属状态和注销登簿信息
        bdcDbxxService.updateYxmDbxx(dbxxQO);

        if (isDb) {
            //更新当前流程生成的历史权利的登薄时间 减少60秒
            bdcDbxxService.updateBdcLsQlXmDjsj(dbxxQO, qllxList);
            if(StringUtils.isNotBlank(dbxxQO.getGzlslid())) {
                BdcXmXmfbDTO bdcXmXmfbDTO = getOnlyOneXm(dbxxQO.getGzlslid(), "");
                boolean isCxdj = CollectionUtils.isNotEmpty(cxdjDyids) && bdcXmXmfbDTO != null && StringUtils.isNotBlank(bdcXmXmfbDTO.getGzldyid()) && cxdjDyids.contains(bdcXmXmfbDTO.getGzldyid());
                if (isCxdj) {
                    //撤销登记,还原上一手产权项目对应外联历史关系中注销的原限制权利的状态
                    bdcDbxxService.updateYxmWlzxqlDbxxAndQsztForCxdj(dbxxQO.getGzlslid());
                }
            }

        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String> 登记小类List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程的登记小类
     */
    @Override
    public String[] getListDjxlByGzlslid(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失参数gzlslid！");
        }
        Set<String> djxlSet = new HashSet();
        List<BdcXmDO> bdcXmDOList = this.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("未查询到项目信息！");
        }
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                djxlSet.add(bdcXmDO.getDjxl());
            }
        }
        return djxlSet.toArray(new String[djxlSet.size()]);
    }

    /**
     * @param xmid 项目主键
     * @return BdcXmDO 项目对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键项目ID获取项目信息
     */
    @Override
    public BdcXmDO getBdcXmByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失查询参数xmid！");
        }
        return entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
    }

    /**
     * 通过上一手的证书信息查询现不动产项目信息
     * <p>不动产项目表，不动产历史关系表，不动产项目证书关系表三表关联查询</p>
     *
     * @param gzlslid
     * @param zsid
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [gzlslid, zsid, qllx] 工作流实例ID， 证书ID（上一手项目的证书ID）， 权利类型
     * @return: List<BdcXmDO> 不动产项目集合信息
     */
    @Override
    public List<BdcXmDO> getBdcXmByGzlslidAndZsid(String gzlslid, String zsid, Integer qllx) {
        HashMap paramMap = new HashMap<>(4);
        paramMap.put("gzlslid", gzlslid);
        paramMap.put("zsid", zsid);
        paramMap.put("qllx", qllx);
        return this.bdcXmMapper.queryXmByGzlslidAndZsid(paramMap);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param qszt    项目的权属状态
     * @param ajzt    项目的案件状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目的权属状态和案件状态
     */
    @Override
    public int updateBdcXmQsztAndAjztPl(String gzlslid, Integer qszt, Integer ajzt) {
        Map paramMap = new HashMap(3);
        paramMap.put("gzlslid", gzlslid);
        paramMap.put("qszt", qszt);
        paramMap.put("ajzt", ajzt);
        return bdcXmMapper.updateBdcXmQsztAndAjztPl(paramMap);
    }

    /**
     * @param bdcXmGxQO 查询QO
     * @return List<BdcXmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询原项目信息
     */
    @Override
    public List<BdcXmDO> getListYbdcXm(BdcXmGxQO bdcXmGxQO) {
        return bdcXmMapper.listYbdcXm(bdcXmGxQO);
    }

    @Override
    public BdcXmXmfbDTO getOnlyOneXm(String gzlslid,String xmid){
        if(StringUtils.isBlank(gzlslid) && StringUtils.isBlank(xmid)){
            throw new AppException("缺失工作流实例ID或项目ID");
        }
        return bdcXmMapper.getOnlyOneXmBfxx(gzlslid,xmid);

    }

    /**
     * @param xmidList
     * @param zsxt
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 更新项目附表证书形态
     */
    @Override
    public void updateBdcXmfbZsxt(List<String> xmidList, Integer zsxt) {
        if (CollectionUtils.isEmpty(xmidList) || null == zsxt) {
            return;
        }

        BdcXmfbZsxtQO bdcXmfbZsxtQO = new BdcXmfbZsxtQO();
        bdcXmfbZsxtQO.setZsxt(zsxt);
        List<List> partList = ListUtils.subList(xmidList, 1000);
        for (List data : partList) {
            bdcXmfbZsxtQO.setXmidList(data);
            bdcXmMapper.updateBdcXmfbZsxt(bdcXmfbZsxtQO);
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 根据工作流实例id更新案件状态为完结
     */
    @Override
    public void updateAjztWjByGzlslid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcXmMapper.updateAjztWjByGzlslid(gzlslid, new Date(System.currentTimeMillis() + 60000L));
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 查询证书
     */
    @Override
    public List<BdcXmZsDTO> queryZsByGzlslid(String gzlslid) {
        return bdcXmMapper.queryZsByGzlslid(gzlslid);
    }

    @Override
    public void updateBdcXmxxDbxxPl(DbxxQO dbxxQO){
        int result =0;
        if(StringUtils.isNotBlank(dbxxQO.getGzlslid()) &&StringUtils.isNotBlank(dbxxQO.getDbr())) {
            result= bdcXmMapper.updateBdcXmxxDbxxPl(dbxxQO);
        }
        LOGGER.info("gzlslid:{}更新项目表登簿信息：{}，共{}条", dbxxQO.getGzlslid(), dbxxQO, result);
    }


}
