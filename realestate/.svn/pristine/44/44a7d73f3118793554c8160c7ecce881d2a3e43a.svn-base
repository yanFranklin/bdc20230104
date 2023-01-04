package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.dbs.DynamicDataSourceContextHolder;
import cn.gtmap.realestate.building.core.mapper.SsjBdcdyhxsztMapper;
import cn.gtmap.realestate.building.core.resource.BdcdyhZtResource;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.DjdcbUtils;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqZxsqDO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.yancheng.BdcZqFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/13
 * @description 不动产单元号现势状态查询
 */
@Service
public class BdcdyZtServiceImpl implements BdcdyZtService {

    /**
     * 每次执行插入 最大数量
     */
    private static final int PER_BATCH_MAXNUM = 40;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcdyZtServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    protected MessageProvider messageProvider;
    /**
     * 判断是否为现势权利
     */
    private static final Integer INCREASE = 1;
    /**
     * 判断是否为注销权利
     */
    private static final Integer REDUCE = -1;
    /**
     * 判断数据库的值是否为0
     */
    private static final Integer ERRORCODE = 0;

    @Autowired
    private FwYcHsService fwYcHsService;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwXmxxService fwXmxxService;

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private SsjBdcdyhxsztMapper ssjBdcdyhxsztMapper;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    @Autowired
    BdcZqFeignService bdcZqFeignService;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否查询房屋拆迁状态
     */
    @Value("${bdcdyzt.fwcqcx:false}")
    private boolean fwcqcx;

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据幢主键查询 状态
     */
    @Override
    public List<SSjBdcdyhxsztDO> querySsjBdcdyhxsztDOByFwDcbIndex(String fwDcbIndex) {
        if(StringUtils.isNotBlank(fwDcbIndex)){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("fwDcbIndex",fwDcbIndex);
            return ssjBdcdyhxsztMapper.queryXsztByFwDcbIndex(paramMap);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcdyh
     * @param list
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 在 LIST中查找 BDCDYH的 XSZT
     */
    @Override
    public SSjBdcdyhxsztDO getSsjBdcdyXsztInList(String bdcdyh, List<SSjBdcdyhxsztDO> list) {
        if(CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(bdcdyh)){
            for(SSjBdcdyhxsztDO xszt : list){
                if(StringUtils.equals(xszt.getBdcdyh(),bdcdyh)){
                    return xszt;
                }
            }
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询 状态实体
     */
    @Override
    public SSjBdcdyhxsztDO querySsjBdcdyhxsztDOByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            return entityMapper.selectByPrimaryKey(SSjBdcdyhxsztDO.class, bdcdyh);
        }
        return null;
    }

    /**
     * @param xsztDO
     * @param updateNull
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 XSZT
     */
    @Override
    public void updateXsztDO(SSjBdcdyhxsztDO xsztDO, boolean updateNull) {
        if(xsztDO != null && StringUtils.isNotBlank(xsztDO.getBdcdyh())){
            if(updateNull){
                entityMapper.updateByPrimaryKeyNull(xsztDO);
            }else{
                entityMapper.updateByPrimaryKeySelective(xsztDO);
            }
        }
    }

    /**
     * @param xsztDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 新增XSZT
     */
    @Override
    public void insertXsztDO(SSjBdcdyhxsztDO xsztDO) {
        if(xsztDO != null && StringUtils.isNotBlank(xsztDO.getBdcdyh())){
            try {
                entityMapper.saveOrUpdate(xsztDO,xsztDO.getBdcdyh());
            } catch (Exception e){
                LOGGER.error("保存xszt报错,xszt {}",xsztDO.toString(),e);
            }
        }
    }

    /**
     * @param lsztDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 新增LSZT
     */
    @Override
    public void insertLsztDO(SSjBdcdyhlsztDO lsztDO) {
        if(lsztDO != null && StringUtils.isNotBlank(lsztDO.getBdcdyh())){
            try {
                if(StringUtils.isBlank(lsztDO.getZtid())){
                    lsztDO.setZtid(UUIDGenerator.generate());
                }
                entityMapper.insertSelective(lsztDO);
            } catch (Exception e){
                LOGGER.error("保存xszt报错,xszt {}",lsztDO.toString(),e);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @description 根据BDCDYH查询状态 XSZT没有 查 LSZT
     */
    @Override
    public SSjBdcdyhxsztDO getBdcdyztDO(String bdcdyh){
        SSjBdcdyhxsztDO sSjBdcdyhxsztDO = querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
        //跟权籍保持一致，如果现势状态没有，查历史状态
        if (sSjBdcdyhxsztDO == null) {
            SSjBdcdyhlsztDO sSjBdcdyhlsztDO=querySsjBdcdyhlsztDOByBdcdyh(bdcdyh);
            if(sSjBdcdyhlsztDO!=null){
                sSjBdcdyhxsztDO = new SSjBdcdyhxsztDO();
                BeanUtils.copyProperties(sSjBdcdyhlsztDO,sSjBdcdyhxsztDO);
            }
        }
        return sSjBdcdyhxsztDO;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据bdcdyh查询不动产单元现势状态
     */
    @Override
    public BdcdyhZtResponseDTO queryBdcdyhZtBybdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            BdcdyhZtResponseDTO dto = queryZtByBdcdyh(bdcdyh);
            if(dto != null) {
                dto.setFwcq(false);
            }
            if(!DjdcbUtils.isTdBdcdy(bdcdyh) && dto != null){
                if(fwcqcx) {
                    //南通需求,增加房屋拆迁状态
                    BdcZqZxsqDO bdcZqZxsqDO = bdcZqFeignService.queryZxsqByBdcdyh(bdcdyh);
                    if (bdcZqZxsqDO != null) {
                        dto.setFwcq(true);
                    }
                }
                String tdBdcdyh = BdcdyhToolUtils.convertFToW(bdcdyh);
                BdcdyhZtResponseDTO zdDto = queryZtByBdcdyh(tdBdcdyh);
                dto.setZdZtDTO(zdDto);
            }
            return dto;
        }
        return null;
    }

    private BdcdyhZtResponseDTO queryZtByBdcdyh(String bdcdyh){
        if (StringUtils.isNotBlank(bdcdyh)) {
            BdcdyhZtResponseDTO dto = new BdcdyhZtResponseDTO();

            // 使用资源服务 和楼盘表展现页面使用一个处理逻辑 判断状态
            SSjBdcdyhxsztDO sSjBdcdyhxsztDO = querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
            if(sSjBdcdyhxsztDO == null){
                return null;
            }
            BdcdyhZtResource bdcdyhZtResource = new BdcdyhZtResource(sSjBdcdyhxsztDO);
            bdcdyhZtResource.convertDTO();
            Map<String, Object> statusResultMap = bdcdyhZtResource.resouceDTO.getStatus();

            // 处理 结果 Map
            if (MapUtils.isNotEmpty(statusResultMap)) {
                dto = revertDTO(statusResultMap);
            }
            dto.setBdcdyh(bdcdyh);
            return dto;
        }
        return null;
    }

    private BdcdyhZtResponseDTO revertDTO(Map<String, Object> statusResultMap){
        BdcdyhZtResponseDTO dto = new BdcdyhZtResponseDTO();
        if (MapUtils.isNotEmpty(statusResultMap)) {
            Map<String, Boolean> ztMap = new HashMap<>();
            Iterator<Map.Entry<String, Object>> iterator
                    = statusResultMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                Boolean hasFlag = false;
                if (entry.getValue() != null) {
                    int val = Integer.parseInt(entry.getValue().toString());
                    if (val > 0) {
                        hasFlag = true;
                    }
                }
                ztMap.put(StringUtils.lowerCase(entry.getKey()), hasFlag);
            }
            String json = JSONObject.toJSONString(ztMap);
            dto = JSONObject.parseObject(json,BdcdyhZtResponseDTO.class);
        }
        return dto;
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhlsztDO
     * @description 根据BDCDYH 查询 历史状态
     */
    @Override
    public SSjBdcdyhlsztDO querySsjBdcdyhlsztDOByBdcdyh(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){
            Example example = new Example(SSjBdcdyhlsztDO.class);
            example.createCriteria().andEqualTo("bdcdyh",bdcdyh);
            List<SSjBdcdyhlsztDO> sSjBdcdyhlsztDOList=entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(sSjBdcdyhlsztDOList)){
                return sSjBdcdyhlsztDOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param bdcdyhList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量根据BDCDYH查询不动产单元现势状态
     */
    @Override
    public List<BdcdyhZtResponseDTO> listBdcdyhZtBybdcdyh(List<String> bdcdyhList) {
        List<BdcdyhZtResponseDTO> bdcdyhZtResponseDTOList = new ArrayList<>();
        if (bdcdyhList.size() < 520) {
            if (CollectionUtils.isNotEmpty(bdcdyhList)) {
                for (String bdcdyh : bdcdyhList) {
                    BdcdyhZtResponseDTO bdcdyhZtResponseDTO = queryBdcdyhZtBybdcdyh(bdcdyh);
                    if (bdcdyhZtResponseDTO != null) {
                        bdcdyhZtResponseDTOList.add(bdcdyhZtResponseDTO);
                    }
                }
            }
        } else {
            throw new AppException(ErrorCodeConstants.SELECT_SUM_HIGH, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.SELECT_SUM_HIGH));
        }
        return bdcdyhZtResponseDTOList;
    }

    /**
     * @param requestDTO 接收到的数据
     * @return Integer 更新到数据库的数目
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH修改不动产单元现势状态
     */
    @Override
    public Integer updateBdcdyZtByBdcdyh(BdcdyhZtRequestDTO requestDTO) {
        Integer updateResult = 0;
        if (requestDTO != null && StringUtils.isNotBlank(requestDTO.getBdcdyh())) {
            SSjBdcdyhxsztDO bdcdyhZt = querySsjBdcdyhxsztDOByBdcdyh(requestDTO.getBdcdyh());
            if (bdcdyhZt != null) {
                // 处理登记状态
                getDjzt(bdcdyhZt,requestDTO);
                // 判断查封次数是增加还是减少
                getCf(bdcdyhZt, requestDTO);
                // 预查封
                getYcf(bdcdyhZt, requestDTO);
                // 抵押
                getDya(bdcdyhZt, requestDTO);
                // 预抵押
                getYdya(bdcdyhZt, requestDTO);
                // 地役
                getDyi(bdcdyhZt, requestDTO);
                // 锁定
                getSd(bdcdyhZt, requestDTO);
                // 预告
                getYg(bdcdyhZt, requestDTO);
                // 异议次
                getYy(bdcdyhZt, requestDTO);
                // 在建工程抵押
                getZzgcdy(bdcdyhZt, requestDTO);
                updateResult = entityMapper.updateByPrimaryKeySelective(bdcdyhZt);
            }
        }
        return updateResult;
    }

    /**
     * sly 批量更新户室中的不动产单元状态
     *
     * @param fwhsList
     */
    @Override
    public void updateBdcdyZtByFwhsList(List<FwHsDO> fwhsList) {
        if (CollectionUtils.isNotEmpty(fwhsList)) {
            for (FwHsDO fwHsDO : fwhsList) {
                updateBdcdyZtByQlztAndBdcdyh(fwHsDO.getQlzt(),fwHsDO.getBdcdyh());
            }
        }
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwYchsDOList
     * @return void
     * @description 批量更新预测户室中的不动产单元状态
     */
    @Override
    public void updateBdcdyZtByFwYchsList(List<FwYchsDO> fwYchsDOList) {
        if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
            for (FwYchsDO fwYchsDO : fwYchsDOList) {
                updateBdcdyZtByQlztAndBdcdyh(fwYchsDO.getQlzt(),fwYchsDO.getBdcdyh());
            }
        }
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param qlzt
     * @return void
     * @description 根据qlzt和bdcdyh批量更新预测户室中的不动产单元状态
     */
    private void updateBdcdyZtByQlztAndBdcdyh(String qlzt,String bdcdyh){
        if (StringUtils.isNotBlank(qlzt)&&!StringUtils.equals(qlzt, Constants.FWHS_QLZT_WSQL) && StringUtils.isNotBlank(bdcdyh)) {
            BdcdyhZtRequestDTO bdcdyhZtRequestDTO = new BdcdyhZtRequestDTO();
            bdcdyhZtRequestDTO.setBdcdyh(bdcdyh);
            boolean updateFlag = false;
            SSjBdcdyhxsztDO bdcdyhZt = querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
            if(bdcdyhZt!=null){
                if (StringUtils.equals(qlzt, Constants.FWHS_QLZT_QLDY) &&bdcdyhZt.getXsdyacs()!=null && bdcdyhZt.getXsdyacs() < 1) {
                    bdcdyhZtRequestDTO.setDya(Integer.valueOf(1));
                    updateFlag = true;
                }
                if (StringUtils.equals(qlzt, Constants.FWHS_QLZT_QLCF) &&bdcdyhZt.getXsdyacs()!=null && bdcdyhZt.getXscfcs() < 1) {
                    bdcdyhZtRequestDTO.setCf(Integer.valueOf(1));
                    updateFlag = true;
                }
            }
            if(updateFlag){
                updateBdcdyZtByBdcdyh(bdcdyhZtRequestDTO);
            }
        }
    }

    /**
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除BDCDYH XSZT表数据
     */
    @Override
    public SSjBdcdyhxsztDO deleteBdcdyhXszt(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){
            // 判断BDCDYH 确实没有其他地方使用
            FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh(bdcdyh);
            if(fwYchsDO != null){
                return null;
            }
            FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
            if(fwHsDO != null){
                return null;
            }
            FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByBdcdyh(bdcdyh);
            if(fwXmxxDO != null){
                return null;
            }
            FwLjzDO fwLjzDO = fwLjzService.queryLjzByBdcdyh(bdcdyh);
            if(fwLjzDO != null){
                return null;
            }
            SSjBdcdyhxsztDO xszt = entityMapper.selectByPrimaryKey(SSjBdcdyhxsztDO.class,bdcdyh);
            if(xszt != null){
                entityMapper.delete(xszt);
            }
            return xszt;
        }
        return null;
    }


    /**
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 移除BDCDYH历史状态
     */
    @Override
    public void deleteBdcdyhLszt(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){
            Example example = new Example(SSjBdcdyhlsztDO.class);
            example.createCriteria().andEqualTo("bdcdyh",bdcdyh);
            entityMapper.deleteByExample(example);
        }
    }

    /**
     * @param bjrq
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BJRQ同步状态
     */
    @Override
    public void syncByDate(Date bjrq) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBjrq(bjrq);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXmByBjrq(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            Set<String> bdcdyhSet = new HashSet<>();
            // BDCDYH 去重
            for(BdcXmDO bdcXmDO : bdcXmDOList){
                if(StringUtils.isNotBlank(bdcXmDO.getBdcdyh())){
                    bdcdyhSet.add(bdcXmDO.getBdcdyh());
                }
            }

        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyhSet
     * @return void
     * @description 分页批量同步BDCDYH
     */
    @Override
    public void syncByBdcdyhByPage(Set<String> bdcdyhSet){
        if(CollectionUtils.isNotEmpty(bdcdyhSet)){
            List<String> bdcdyhList = new ArrayList<>();
            for(String temp : bdcdyhSet){
                bdcdyhList.add(temp);
                if(bdcdyhList.size() == PER_BATCH_MAXNUM){
                    // 处理一次同步请求
                    syncByBdcdyh(bdcdyhList);
                    bdcdyhList = new ArrayList<>();
                }
            }
            if(CollectionUtils.isNotEmpty(bdcdyhList)){
                syncByBdcdyh(bdcdyhList);
            }
        }
    }

    /**
     * @param bdcdyhList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询
     */
    @Override
    public void syncByBdcdyh(List<String> bdcdyhList) {
        if(CollectionUtils.isNotEmpty(bdcdyhList)){
            // 调取register的服务 查询状态数量
            BatchBdcdyhSyncZtRequestDTO dto = bdcdyZtFeignService.queryBdcdyZtByBdcdyh(bdcdyhList);
            if(dto != null){
                saveZtWithDTO(dto);
            }
        }
    }

    /**
     * @param batchDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存DTO中的状态数据
     */
    @Override
    public void saveZtWithDTO(BatchBdcdyhSyncZtRequestDTO batchDTO) {
        if (batchDTO != null && CollectionUtils.isNotEmpty(batchDTO.getBdcdyhZtList())) {
            for (BdcSyncZtRequestDTO dto : batchDTO.getBdcdyhZtList()) {
                if (!CheckEntityUtils.checkPkAndFk(dto)) {
                    throw new IllegalArgumentException("主键为空");
                }
                SSjBdcdyhxsztDO xszt = querySsjBdcdyhxsztDOByBdcdyh(dto.getBdcdyh());

                // 如果不存在 直接新增一条
                if(xszt == null){
                    xszt = new SSjBdcdyhxsztDO();
                    BeanUtils.copyProperties(dto,xszt);
                    xszt.setGxsj(new Date());
                    LOGGER.info("新增现势状态：{}",xszt);
                    insertXsztDO(xszt);
                }else{
                    BeanUtils.copyProperties(dto,xszt);
                    xszt.setGxsj(new Date());
                    // 存在即更新
                    LOGGER.info("更新现势状态：{}",xszt);
                    updateXsztDO(xszt,true);
                }
            }
        } else {
            throw new IllegalArgumentException("更新列表为空");
        }
    }

    @Override
    public void saveSdZtWithDTO(BatchBdcdyhSyncZtRequestDTO batchDTO) {
        LOGGER.warn("处理锁定同步数据：{},当前权籍数据库：{}", batchDTO, DynamicDataSourceContextHolder.getDataSourceKey());
        if (batchDTO != null && CollectionUtils.isNotEmpty(batchDTO.getBdcdyhZtList())) {
            for (BdcSyncZtRequestDTO dto : batchDTO.getBdcdyhZtList()) {
                if (!CheckEntityUtils.checkPkAndFk(dto)) {
                    throw new IllegalArgumentException("主键为空");
                }
                SSjBdcdyhxsztDO xszt = querySsjBdcdyhxsztDOByBdcdyh(dto.getBdcdyh());

                // 如果不存在 直接新增一条
                if(xszt == null){
                    xszt = new SSjBdcdyhxsztDO();
                    BeanUtils.copyProperties(dto, xszt);
                    xszt.setGxsj(new Date());
                    LOGGER.info("新增现势状态：{}", xszt);
                    insertXsztDO(xszt);
                }else{
                    xszt.setXssdcs(dto.getXssdcs());
                    // 存在即更新
                    LOGGER.info("更新现势状态：{}",xszt);
                    updateXsztDO(xszt,true);
                }
            }
        } else {
            throw new IllegalArgumentException("更新列表为空");
        }
    }

    /**
     * @param bdcdyh
     * @param bdcdyhList
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 批量不动产单元合并，要把这些不动产单元和限制状态求和放到新的不动产单元号里
     */
    @Override
    public Integer updateBdcdyZtByPlBdcdyh(String bdcdyh, List<String> bdcdyhList) {
        Integer updateResult = 0;
        if (CollectionUtils.isNotEmpty(bdcdyhList) && StringUtils.isNotBlank(bdcdyh)) {
            SSjBdcdyhxsztDO sSjBdcdyhxsztDO = querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
            if (sSjBdcdyhxsztDO != null) {
                for (String bdcdyTemp : bdcdyhList) {
                    if (!StringUtils.equals(bdcdyTemp, bdcdyh)) {
                        SSjBdcdyhxsztDO sSjBdcdyhxsztTemp = querySsjBdcdyhxsztDOByBdcdyh(bdcdyTemp);
                        if (sSjBdcdyhxsztTemp != null) {
                            sSjBdcdyhxsztDO.setXszjgcdycs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXszjgcdycs(), sSjBdcdyhxsztTemp.getXszjgcdycs()));
                            sSjBdcdyhxsztDO.setXsygcs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsygcs(), sSjBdcdyhxsztTemp.getXsygcs()));
                            sSjBdcdyhxsztDO.setXsydyacs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsydyacs(), sSjBdcdyhxsztTemp.getXsydyacs()));
                            sSjBdcdyhxsztDO.setXsdyacs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsdyacs(), sSjBdcdyhxsztTemp.getXsdyacs()));
                            sSjBdcdyhxsztDO.setXsycfcs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsycfcs(), sSjBdcdyhxsztTemp.getXsycfcs()));
                            sSjBdcdyhxsztDO.setXscfcs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXscfcs(), sSjBdcdyhxsztTemp.getXscfcs()));
                            sSjBdcdyhxsztDO.setXsyycs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsyycs(), sSjBdcdyhxsztTemp.getXsyycs()));
                            sSjBdcdyhxsztDO.setXsdyics(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsdyics(), sSjBdcdyhxsztTemp.getXsdyics()));
                            sSjBdcdyhxsztDO.setXssdcs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXssdcs(), sSjBdcdyhxsztTemp.getXssdcs()));
                        }
                    }
                }
                updateResult = entityMapper.updateByPrimaryKeySelective(sSjBdcdyhxsztDO);
            }
        }
        return updateResult;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @return {List} 现势状态信息
     * @description 批量查询不动产单元现势状态信息（不循环查询避免批量查询慢）
     */
    @Override
    public List<BdcdyhZtResponseDTO> listBdcdyhZtPlcx(List<String> bdcdyhList) {
        if(CollectionUtils.isEmpty(bdcdyhList)) {
            return Collections.emptyList();
        }

        List<SSjBdcdyhxsztDO> bdcdyhxsztDOList = ssjBdcdyhxsztMapper.listBdcdyXszt(bdcdyhList);
        if(CollectionUtils.isEmpty(bdcdyhxsztDOList)) {
            return Collections.emptyList();
        }

        List<BdcdyhZtResponseDTO> result = new ArrayList<>();
        for(SSjBdcdyhxsztDO xszt : bdcdyhxsztDOList) {
            // 使用资源服务 和楼盘表展现页面使用一个处理逻辑 判断状态
            if(xszt == null){
                continue;
            }

            BdcdyhZtResponseDTO dto = new BdcdyhZtResponseDTO();
            BdcdyhZtResource bdcdyhZtResource = new BdcdyhZtResource(xszt);
            bdcdyhZtResource.convertDTO();
            Map<String, Object> statusResultMap = bdcdyhZtResource.resouceDTO.getStatus();
            // 处理 结果 Map
            if (MapUtils.isNotEmpty(statusResultMap)) {
                dto = revertDTO(statusResultMap);
            }
            dto.setBdcdyh(xszt.getBdcdyh());
            dto.setXszjgcdycs(xszt.getXszjgcdycs());
            dto.setXsygcs(xszt.getXsygcs());
            dto.setXsydyacs(xszt.getXsydyacs());
            dto.setXsdyacs(xszt.getXsdyacs());
            dto.setXsycfcs(xszt.getXsycfcs());
            dto.setXscfcs(xszt.getXscfcs());
            dto.setXsyycs(xszt.getXsyycs());
            dto.setXsdyics(xszt.getXsdyics());
            dto.setXssdcs(xszt.getXssdcs());
            dto.setXsjzqcs(xszt.getXsjzqcs());
            result.add(dto);
        }

        return result;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyhZt
     * @param requestDTO
     * @return void
     * @description 处理登记状态
     */
    private void getDjzt(SSjBdcdyhxsztDO bdcdyhZt,BdcdyhZtRequestDTO requestDTO){
        if(requestDTO.getDjzt() != null){
            bdcdyhZt.setDjzt(requestDTO.getDjzt().toString());
        }
    }

    /**
     * @param sSjBdcdyhxsztDO    数据库查出来的数据
     * @param bdcdyhZtRequestDTO 接收到的数据
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 判断查封次数是增加还是减少
     */
    private void getCf(SSjBdcdyhxsztDO sSjBdcdyhxsztDO, BdcdyhZtRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO.getCf() != null) {
            // 增加 1代表现势权利,数据库增加1
            if (INCREASE.equals(bdcdyhZtRequestDTO.getCf())) {
                if (sSjBdcdyhxsztDO.getXscfcs() == null) {
                    sSjBdcdyhxsztDO.setXscfcs(INCREASE);
                } else {
                    sSjBdcdyhxsztDO.setXscfcs(sSjBdcdyhxsztDO.getXscfcs() + INCREASE);
                }
            } else if (REDUCE.equals(bdcdyhZtRequestDTO.getCf())) {
                // 减少 -1代表注销权利,数据库值减1
                if (sSjBdcdyhxsztDO.getXscfcs() == null || sSjBdcdyhxsztDO.getXscfcs().equals(ERRORCODE)) {
                    throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
                } else {
                    sSjBdcdyhxsztDO.setXscfcs(sSjBdcdyhxsztDO.getXscfcs() - INCREASE);
                }
            } else {
                // 既不是1 也不是-1抛异常
                throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
            }

        }
    }

    /**
     * @param sSjBdcdyhxsztDO    数据库查出来的数据
     * @param bdcdyhZtRequestDTO 接收到的数据
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 判断预查封次数是增加还是减少
     */
    private void getYcf(SSjBdcdyhxsztDO sSjBdcdyhxsztDO, BdcdyhZtRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO.getYcf() != null) {
            // 增加 1代表现势权利,数据库增加1
            if (INCREASE.equals(bdcdyhZtRequestDTO.getYcf())) {
                if (sSjBdcdyhxsztDO.getXsycfcs() == null) {
                    sSjBdcdyhxsztDO.setXsycfcs(INCREASE);
                } else {
                    sSjBdcdyhxsztDO.setXsycfcs(sSjBdcdyhxsztDO.getXsycfcs() + INCREASE);
                }
            } else if (REDUCE.equals(bdcdyhZtRequestDTO.getYcf())) {
                // 减少 -1代表注销权利,数据库值减1
                if (sSjBdcdyhxsztDO.getXsycfcs() == null || sSjBdcdyhxsztDO.getXsycfcs().equals(ERRORCODE)) {
                    throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
                } else {
                    sSjBdcdyhxsztDO.setXsycfcs(sSjBdcdyhxsztDO.getXsycfcs() - INCREASE);
                }
            } else {
                // 既不是1 也不是-1抛异常
                throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
            }
        }
    }

    /**
     * @param sSjBdcdyhxsztDO    数据库查出来的数据
     * @param bdcdyhZtRequestDTO 接收到的数据
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 判断抵押次数是增加还是减少
     */
    private void getDya(SSjBdcdyhxsztDO sSjBdcdyhxsztDO, BdcdyhZtRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO.getDya() != null) {
            // 增加 1代表现势权利,数据库增加1
            if (INCREASE.equals(bdcdyhZtRequestDTO.getDya())) {
                if (sSjBdcdyhxsztDO.getXsdyacs() == null) {
                    sSjBdcdyhxsztDO.setXsdyacs(INCREASE);
                } else {
                    sSjBdcdyhxsztDO.setXsdyacs(sSjBdcdyhxsztDO.getXsdyacs() + INCREASE);
                }
            } else if (REDUCE.equals(bdcdyhZtRequestDTO.getDya())) {
                //减少 -1代表注销权利,数据库值减1
                if (sSjBdcdyhxsztDO.getXsdyacs() == null || sSjBdcdyhxsztDO.getXsdyacs().equals(ERRORCODE)) {
                    throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
                } else {
                    sSjBdcdyhxsztDO.setXsdyacs(sSjBdcdyhxsztDO.getXsdyacs() - INCREASE);
                }
            } else {
                // 既不是1 也不是-1抛异常
                throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
            }
        }
    }

    /**
     * @param sSjBdcdyhxsztDO    数据库查出来的数据
     * @param bdcdyhZtRequestDTO 接收到的数据
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 判断预抵押次数是增加还是减少
     */
    private void getYdya(SSjBdcdyhxsztDO sSjBdcdyhxsztDO, BdcdyhZtRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO.getYdya() != null) {
            // 增加 1代表现势权利,数据库增加1
            if (INCREASE.equals(bdcdyhZtRequestDTO.getYdya())) {
                if (sSjBdcdyhxsztDO.getXsydyacs() == null) {
                    sSjBdcdyhxsztDO.setXsydyacs(INCREASE);
                } else {
                    sSjBdcdyhxsztDO.setXsydyacs(sSjBdcdyhxsztDO.getXsydyacs() + INCREASE);
                }
            } else if (REDUCE.equals(bdcdyhZtRequestDTO.getYdya())) {
                // 减少 -1代表注销权利,数据库值减1
                if (sSjBdcdyhxsztDO.getXsydyacs() == null || sSjBdcdyhxsztDO.getXsydyacs().equals(ERRORCODE)) {
                    throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
                } else {
                    sSjBdcdyhxsztDO.setXsydyacs(sSjBdcdyhxsztDO.getXsydyacs() - INCREASE);
                }
            } else {
                // 既不是1 也不是-1抛异常
                throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
            }
        }
    }

    /**
     * @param sSjBdcdyhxsztDO    数据库查出来的数据
     * @param bdcdyhZtRequestDTO 接收到的数据
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 判断地役次数是增加还是减少
     */
    private void getDyi(SSjBdcdyhxsztDO sSjBdcdyhxsztDO, BdcdyhZtRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO.getDyi() != null) {
            // 增加 1代表现势权利,数据库增加1
            if (INCREASE.equals(bdcdyhZtRequestDTO.getDyi())) {
                if (sSjBdcdyhxsztDO.getXsdyics() == null) {
                    sSjBdcdyhxsztDO.setXsdyics(INCREASE);
                } else {
                    sSjBdcdyhxsztDO.setXsdyics(sSjBdcdyhxsztDO.getXsdyics() + INCREASE);
                }
            } else if (REDUCE.equals(bdcdyhZtRequestDTO.getDyi())) {
                // 减少 -1代表注销权利,数据库值减1
                if (sSjBdcdyhxsztDO.getXsdyics() == null || sSjBdcdyhxsztDO.getXsdyics().equals(ERRORCODE)) {
                    throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
                } else {
                    sSjBdcdyhxsztDO.setXsdyics(sSjBdcdyhxsztDO.getXsdyics() - INCREASE);
                }
            } else {
                // 既不是1 也不是-1抛异常
                throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
            }
        }
    }

    /**
     * @param sSjBdcdyhxsztDO    数据库查出来的数据
     * @param bdcdyhZtRequestDTO 接收到的数据
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 判断锁定次数是增加还是减少
     */
    private void getSd(SSjBdcdyhxsztDO sSjBdcdyhxsztDO, BdcdyhZtRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO.getSd() != null) {
            //增加 1代表现势权利,数据库增加1
            if (INCREASE.equals(bdcdyhZtRequestDTO.getSd())) {
                if (sSjBdcdyhxsztDO.getXssdcs() == null) {
                    sSjBdcdyhxsztDO.setXssdcs(INCREASE);
                } else {
                    sSjBdcdyhxsztDO.setXssdcs(sSjBdcdyhxsztDO.getXssdcs() + INCREASE);
                }
            } else if (REDUCE.equals(bdcdyhZtRequestDTO.getSd())) {
                //减少 -1代表注销权利,数据库值减1
                if (sSjBdcdyhxsztDO.getXssdcs() == null || sSjBdcdyhxsztDO.getXssdcs().equals(ERRORCODE)) {
                    throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
                } else {
                    sSjBdcdyhxsztDO.setXssdcs(sSjBdcdyhxsztDO.getXssdcs() - INCREASE);
                }
            } else {
                // 既不是1 也不是-1抛异常
                throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
            }
        }
    }

    /**
     * @param sSjBdcdyhxsztDO    数据库查出来的数据
     * @param bdcdyhZtRequestDTO 接收到的数据
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 判断预告次数是增加还是减少
     */
    private void getYg(SSjBdcdyhxsztDO sSjBdcdyhxsztDO, BdcdyhZtRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO.getYg() != null) {
            // 增加 1代表现势权利,数据库增加1
            if (INCREASE.equals(bdcdyhZtRequestDTO.getYg())) {
                if (sSjBdcdyhxsztDO.getXsygcs() == null) {
                    sSjBdcdyhxsztDO.setXsygcs(INCREASE);
                } else {
                    sSjBdcdyhxsztDO.setXsygcs(sSjBdcdyhxsztDO.getXsygcs() + INCREASE);
                }
            } else if (REDUCE.equals(bdcdyhZtRequestDTO.getYg())) {
                // 减少 -1代表注销权利,数据库值减1
                if (sSjBdcdyhxsztDO.getXsygcs() == null || sSjBdcdyhxsztDO.getXsygcs().equals(ERRORCODE)) {
                    throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
                } else {
                    sSjBdcdyhxsztDO.setXsygcs(sSjBdcdyhxsztDO.getXsygcs() - INCREASE);
                }
            } else {
                // 既不是1 也不是-1抛异常
                throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
            }
        }
    }

    /**
     * @param sSjBdcdyhxsztDO    数据库查出来的数据
     * @param bdcdyhZtRequestDTO 接收到的数据
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 判断异议次数是增加还是减少
     */
    private void getYy(SSjBdcdyhxsztDO sSjBdcdyhxsztDO, BdcdyhZtRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO.getYy() != null) {
            // 增加 1代表现势权利,数据库增加1
            if (INCREASE.equals(bdcdyhZtRequestDTO.getYy())) {
                if (sSjBdcdyhxsztDO.getXsyycs() == null) {
                    sSjBdcdyhxsztDO.setXsyycs(INCREASE);
                } else {
                    sSjBdcdyhxsztDO.setXsyycs(sSjBdcdyhxsztDO.getXsyycs() + INCREASE);
                }
            } else if (REDUCE.equals(bdcdyhZtRequestDTO.getYy())) {
                // 减少 -1代表注销权利,数据库值减1
                if (sSjBdcdyhxsztDO.getXsyycs() == null || sSjBdcdyhxsztDO.getXsyycs().equals(ERRORCODE)) {
                    throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
                } else {
                    sSjBdcdyhxsztDO.setXsyycs(sSjBdcdyhxsztDO.getXsyycs() - INCREASE);
                }
            } else {
                // 既不是1 也不是-1抛异常
                throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
            }
        }
    }

    /**
     * @param sSjBdcdyhxsztDO    数据库查出来的数据
     * @param bdcdyhZtRequestDTO 接收到的数据
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 判断在建工程抵押次数是增加还是减少
     */
    private void getZzgcdy(SSjBdcdyhxsztDO sSjBdcdyhxsztDO, BdcdyhZtRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO.getZjgcdy() != null) {
            //增加 1代表现势权利,数据库增加1
            if (INCREASE.equals(bdcdyhZtRequestDTO.getZjgcdy())) {
                if (sSjBdcdyhxsztDO.getXszjgcdycs() == null) {
                    sSjBdcdyhxsztDO.setXszjgcdycs(INCREASE);
                } else {
                    sSjBdcdyhxsztDO.setXszjgcdycs(sSjBdcdyhxsztDO.getXszjgcdycs() + INCREASE);
                }
            } else if (REDUCE.equals(bdcdyhZtRequestDTO.getZjgcdy())) {
                //减少 -1代表注销权利,数据库值减1
                if (sSjBdcdyhxsztDO.getXszjgcdycs() == null || sSjBdcdyhxsztDO.getXszjgcdycs().equals(ERRORCODE)) {
                    throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
                } else {
                    sSjBdcdyhxsztDO.setXszjgcdycs(sSjBdcdyhxsztDO.getXszjgcdycs() - INCREASE);
                }
            } else {
                // 既不是1 也不是-1抛异常
                throw new AppException(ErrorCodeConstants.PARAM_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.PARAM_ERROR));
            }
        }
    }

}
