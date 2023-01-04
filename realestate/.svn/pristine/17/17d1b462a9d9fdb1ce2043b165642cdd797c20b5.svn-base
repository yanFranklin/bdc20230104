package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxmMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxCzrzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmPlxgDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxmQO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收费项目数据服务
 */
@Service
public class BdcSlSfxmServiceImpl implements BdcSlSfxmService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    BdcSlSfxmMapper bdcSlSfxmMapper;
    @Autowired
    UserManagerUtils userManagerUtils;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  初始化收费项目,数量或者应收金额配置为0,是否清空
      */
    @Value("${sfxm.ysje.sl.zeroclean:true}")
    private Boolean zeroclean;

    /**
     * @param sfxmid 收费项目ID
     * @return 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费项目ID获取不动产受理收费项目
     */
    @Override
    public BdcSlSfxmDO queryBdcSlSfxmBySfxmid(String sfxmid) {
        if (StringUtils.isBlank(sfxmid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlSfxmDO.class, sfxmid);
    }

    /**
     * @param sfxxid 收费信息ID
     * @return 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID获取不动产受理收费项目
     */
    @Override
    public List<BdcSlSfxmDO> listBdcSlSfxmBySfxxid(String sfxxid) {
        List<BdcSlSfxmDO> bdcSlSfxmDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(sfxxid)) {
            Example example = new Example(BdcSlSfxmDO.class);
            example.createCriteria().andEqualTo("sfxxid", sfxxid);
            example.setOrderByClause("xh");
            bdcSlSfxmDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxmDOList)) {
            bdcSlSfxmDOList = Collections.emptyList();
        }
        return bdcSlSfxmDOList;
    }

    @Override
    public List<BdcSlSfxmDO> listCshBdcSlSfxm(List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList,String sfxxid) {
        List<BdcSlSfxmDO> bdcSlSfxmDOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcSlSfxmPzDOList) && StringUtils.isNotBlank(sfxxid)){
            for (BdcSlSfxmPzDO bdcSlSfxmPzDO:bdcSlSfxmPzDOList) {
                BdcSlSfxmDO bdcSlSfxmDO = new BdcSlSfxmDO();
                BeanUtils.copyProperties(bdcSlSfxmPzDO, bdcSlSfxmDO);
                //配置不为空且不为0 才赋值
                if (bdcSlSfxmPzDO.getSl() != null) {
                    bdcSlSfxmDO.setSl((double) bdcSlSfxmPzDO.getSl());
                }
                if (Objects.nonNull(bdcSlSfxmDO.getSl()) && (zeroclean &&Objects.equals((double) 0, bdcSlSfxmDO.getSl()))) {
                    bdcSlSfxmDO.setSl(null);
                }
                if (Objects.nonNull(bdcSlSfxmDO.getYsje()) && (zeroclean &&Objects.equals((double) 0, bdcSlSfxmDO.getYsje()))) {
                    bdcSlSfxmDO.setYsje(null);
                }
                bdcSlSfxmDO.setSfxxid(sfxxid);
                bdcSlSfxmDO = this.insertBdcSlSfxm(bdcSlSfxmDO);
                bdcSlSfxmDOList.add(bdcSlSfxmDO);
            }
        }
        return bdcSlSfxmDOList;
    }

    /**
     * @param bdcSlSfxmDO 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理收费项目
     */
    @Override
    public BdcSlSfxmDO insertBdcSlSfxm(BdcSlSfxmDO bdcSlSfxmDO) {
        if (bdcSlSfxmDO != null) {
            if (StringUtils.isBlank(bdcSlSfxmDO.getSfxmid())) {
                bdcSlSfxmDO.setSfxmid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlSfxmDO);
        }
        return bdcSlSfxmDO;
    }

    /**
     * @param bdcSlSfxmDO 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理收费项目
     */
    @Override
    public Integer updateBdcSlSfxm(BdcSlSfxmDO bdcSlSfxmDO) {
        int result;
        if (bdcSlSfxmDO != null && StringUtils.isNotBlank(bdcSlSfxmDO.getSfxmid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlSfxmDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    @Override
    public Integer saveOrUpdateBdcSlSfxm(BdcSlSfxmDO bdcSlSfxmDO) {
        int result;
        if (bdcSlSfxmDO != null) {
            if (StringUtils.isBlank(bdcSlSfxmDO.getSfxmid())) {
                bdcSlSfxmDO.setSfxmid(UUIDGenerator.generate16());
                return  entityMapper.insertSelective(bdcSlSfxmDO);
            }
            result = entityMapper.saveOrUpdate(bdcSlSfxmDO, bdcSlSfxmDO.getSfxmid());

        }else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    @Override
    public void batchUpdateBdcSlSfxm(List<BdcSlSfxmDO> bdcSlSfxmDOList) {
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            this.entityMapper.batchSaveSelective(bdcSlSfxmDOList);
        }
    }

    /**
     * @param sfxmid 收费项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费项目ID删除不动产受理收费项目
     */
    @Override
    public Integer deleteBdcSlSfxmBySfxmid(String sfxmid) {
        int result = 0;
        if (StringUtils.isNotBlank(sfxmid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlSfxmDO.class, sfxmid);
        }
        return result;
    }

    /**
     * @param sfxxid 收费信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID删除不动产受理收费项目
     */
    @Override
    public Integer deleteBdcSlSfxmBySfxxid(String sfxxid) {
        int result = 0;
        if (StringUtils.isNotBlank(sfxxid)) {
            Example example = new Example(BdcSlSfxmDO.class);
            example.createCriteria().andEqualTo("sfxxid", sfxxid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     * @param bdcSlSfxmQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询获取收费项目
     * @date : 2020/11/30 11:33
     */
    @Override
    public List<BdcSlSfxmDO> listSfxmBySfxmdm(BdcSlSfxmQO bdcSlSfxmQO) {
        return bdcSlSfxmMapper.listSfxmBySfxmdm(bdcSlSfxmQO);
    }

    @Override
    public void plxgSfxm(BdcSlSfxmPlxgDTO bdcSlSfxmPlxgDTO) {
        if(CollectionUtils.isEmpty(bdcSlSfxmPlxgDTO.getGzlslidList()) || CollectionUtils.isEmpty(bdcSlSfxmPlxgDTO.getBdcSlSfxmDOList())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID或收费项目信息");
        }
        // 查询工作流实例ID 关联收费项目信息
        List<BdcSlSfxxDO> sfxxList = new ArrayList<>(bdcSlSfxmPlxgDTO.getGzlslidList().size());
        for(String gzlslid: bdcSlSfxmPlxgDTO.getGzlslidList()){
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.queryQlrSfxxByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
                sfxxList.addAll(bdcSlSfxxDOList);
            }
        }

        Double hj = bdcSlSfxmPlxgDTO.getBdcSlSfxmDOList().stream().filter(sfxm -> null != sfxm.getSsje()).map(t -> new BigDecimal(String.valueOf(t.getSsje())))
                .reduce((m,n)->m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
        // 先删除收费项目信息、在更新修改收费项目
        for(BdcSlSfxxDO sfxx: sfxxList){
            if(StringUtils.isNotBlank(sfxx.getSfxxid())){
                for(BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmPlxgDTO.getBdcSlSfxmDOList()){
                    bdcSlSfxmDO.setSfxxid(sfxx.getSfxxid());
                    bdcSlSfxmDO.setSfxmid(UUIDGenerator.generate16());
                }
                // 删除收费项目信息
                this.deleteBdcSlSfxmBySfxxid(sfxx.getSfxxid());
                // 新增收费项目
                this.entityMapper.insertBatchSelective(bdcSlSfxmPlxgDTO.getBdcSlSfxmDOList());
                // 计算合计更新收费信息 hj 总合计
                sfxx.setHj(hj);
                sfxx.setBz(bdcSlSfxmPlxgDTO.getBz());
                this.entityMapper.updateByPrimaryKey(sfxx);
                // 添加收费信息操作日志
                this.addSfxxCzrz(sfxx, bdcSlSfxmPlxgDTO.getXgjfyy());
            }
        }

    }

    /**
     * 根据登记原因、sfxxid获取不动产受理收费项目
     * @param sfxxid
     * @param djyy
     * @return 不动产受理收费项目list
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @Override
    public List<BdcSlSfxmDO> listBdcSlSfxmBySfxxidAndDjyy(String sfxxid, String djyy) {
        List<BdcSlSfxmDO> bdcSlSfxmDOList = new ArrayList<>();
        if(StringUtils.isNotBlank(djyy) && StringUtils.isNotBlank(sfxxid)){
            Example example = new Example(BdcSlSfxmDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("djyy", djyy);
            criteria.andEqualTo("sfxxid", sfxxid);
            bdcSlSfxmDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxmDOList)) {
            bdcSlSfxmDOList = Collections.emptyList();
        }
        return bdcSlSfxmDOList;
    }

    @Override
    public void plxgSfxmSfzt(List<String> sfxxidList, Integer sfzt) {
        if(CollectionUtils.isNotEmpty(sfxxidList)){
            this.bdcSlSfxmMapper.plModifySfxmSfzt(sfxxidList, sfzt);
        }
    }

    @Override
    public void plxgDjfSfxmSsje(List<String> sfxxidList, Double ssje) {
        if(CollectionUtils.isNotEmpty(sfxxidList)){
            this.bdcSlSfxmMapper.plxgDjfSfxmSsje(sfxxidList, ssje);
        }
    }

    @Override
    public List<BdcSlSfxmDO> queryYjSfxmxxGroupBySfxmbz(String xmid, String sqrlb, List<String> sfxxidList, Integer sfyj, boolean hjfk) {
        return this.bdcSlSfxmMapper.queryYjSfxmxxGroupBySfxmbz(xmid, sqrlb, sfxxidList, sfyj, hjfk);
    }

    // 查询权利人的收费信息
    private List<BdcSlSfxxDO> queryQlrSfxxByGzlslid(String gzlslid){
        Example example = new Example(BdcSlSfxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzlslid", gzlslid);
        criteria.andEqualTo("qlrlb", CommonConstantUtils.QLRLB_QLR);
        return this.entityMapper.selectByExampleNotNull(example);
    }

    // 添加修改收费项目操作日志
    private void addSfxxCzrz(BdcSlSfxxDO bdcSlSfxxDO, String xgjfyy){
        UserDto user = userManagerUtils.getCurrentUser();
        String userName  = "", userId = "";
        if(null != user){
            userName = user.getAlias();
            userId = user.getId();
        }
        // 修改缴费前台没有传输全部数据，需要重新查一下sfxx表，拿到要保存日志的数据
        BdcSlSfxxCzrzDO bdcSlSfxxCzrzDO = new BdcSlSfxxCzrzDO();
        bdcSlSfxxCzrzDO.setCzr(userName);
        bdcSlSfxxCzrzDO.setId(UUIDGenerator.generate16());
        BeanUtils.copyProperties(bdcSlSfxxDO, bdcSlSfxxCzrzDO);
        bdcSlSfxxCzrzDO.setXgjfr(userName);
        bdcSlSfxxCzrzDO.setXgjfrid(userId);
        bdcSlSfxxCzrzDO.setXgjfsj(new Date());
        bdcSlSfxxCzrzDO.setXgjfyy(xgjfyy);
        bdcSlSfxxCzrzDO.setSfsj(null);
        this.entityMapper.insertSelective(bdcSlSfxxCzrzDO);
    }

}
