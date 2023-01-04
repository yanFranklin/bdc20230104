package cn.gtmap.realestate.accept.core.service.impl;


import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.config.FdjywConfig;
import cn.gtmap.realestate.accept.core.mapper.BdcSlZjjgMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlFdjywService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmLsgxService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.core.service.BdcSlZjjgService;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.accept.YthZjjgDto;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.zjjg.ZjjgResponseDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlZjjgQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/22
 * @description 不动产资金监管
 */
@Service
@Slf4j
public class BdcSlZjjgServiceImpl implements BdcSlZjjgService,BdcSlFdjywService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlZjjgServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcSlZjjgMapper bdcSlZjjgMapper;

    @Autowired
    FdjywConfig fdjywConfig;

    @Autowired
    private DozerBeanMapper acceptDozerMapper;

    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;

    @Autowired
    BdcSlXmService bdcSlXmService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        List<String> zjjgGzldyidList =fdjywConfig.getFdjywlcDyidList("zjjg");
        if(CollectionUtils.isNotEmpty(zjjgGzldyidList)) {
            set.addAll(zjjgGzldyidList);
        }else{
            set.add("zjjg");
        }
        return set;
    }

    @Override
    public List<BdcSlZjjgDO> listBdcSlZjjg(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlZjjgDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            List<BdcSlZjjgDO> bdcSlZjjgDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcSlZjjgDOList)) {
                return bdcSlZjjgDOList;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<BdcSlZjjgDO> listBdcSlZjjgByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlZjjgDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            List<BdcSlZjjgDO> bdcSlZjjgDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcSlZjjgDOList)) {
                return bdcSlZjjgDOList;
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcSlZjjgQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询资金监管
     * @date : 2021/7/22 15:58
     */
    @Override
    public List<BdcSlZjjgDO> listBdcSlZjjg(BdcSlZjjgQO bdcSlZjjgQO) {
        if (CheckParameter.checkAnyParameter(bdcSlZjjgQO)) {
            return bdcSlZjjgMapper.listBdcSlZjjg(bdcSlZjjgQO);
        }
        return Collections.emptyList();
    }

    @Override
    public void initFdjywxx(BdcSlCshDTO bdcSlCshDTO){
        List<BdcSlZjjgDO> bdcSlZjjgDOS =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcSlCshDTO.getBdcSlXmDOList())) {
            for(BdcSlXmDO bdcSlXmDO:bdcSlCshDTO.getBdcSlXmDOList()) {
                BdcSlZjjgDO bdcSlZjjgDO =new BdcSlZjjgDO();
                bdcSlZjjgDO.setGzlslid(bdcSlCshDTO.getGzlslid());
                acceptDozerMapper.map(bdcSlXmDO, bdcSlZjjgDO);
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), "", null);
                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                    bdcSlZjjgDO.setCqxmid(bdcSlXmLsgxDOList.get(0).getYxmid());
                }
                bdcSlZjjgDO.setZjjgid(UUIDGenerator.generate16());
                bdcSlZjjgDOS.add(bdcSlZjjgDO);
            }
        }
        if(CollectionUtils.isEmpty(bdcSlZjjgDOS)){
            throw new AppException("未生成资金监管数据");
        }
        entityMapper.insertBatchSelective(bdcSlZjjgDOS);
    }

    @Override
    public List<String> getFdjywlcDyidList(String ywlx){
        return fdjywConfig.getFdjywlcDyidList(ywlx);

    }

    @Override
    public void updateZjjgZtYcx(String gzlslid) {
        // 获取资金监管流程xmid
        String zjjgLcXmid = getZjjgLcXmid(gzlslid);
        if(StringUtils.isNotBlank(zjjgLcXmid)){
            BdcSlZjjgDO bdcSlZjjgDO = new BdcSlZjjgDO();
            bdcSlZjjgDO.setZt(CommonConstantUtils.ZJJG_ZT_YCX);
            Example example= new Example(BdcSlZjjgDO.class,false);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", zjjgLcXmid);
            entityMapper.updateByExampleSelectiveNotNull(bdcSlZjjgDO, example);
        }
    }

    /**
     * 资金监管撤销流程获取原有资金监管项目ID
     */
    private String getZjjgLcXmid(String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)){
            // 获取资金监管撤销流程的项目id
            List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
                // 查询资金监管撤销流程上一手资金监管流程信息
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = this.bdcSlXmLsgxService.listBdcSlXmLsgxByXmid(bdcSlXmDOList.get(0).getXmid());
                if(CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)){
                    return bdcSlXmLsgxDOList.get(0).getYxmid();
                }
            }
        }
        return "";
    }

    @Override
    public void updateZjjgXmfbSfzjjg(String gzlslid) {
        BdcSlZjjgQO bdcSlZjjgQO = new BdcSlZjjgQO();
        bdcSlZjjgQO.setGzlslid(gzlslid);
        List<BdcSlZjjgDO> bdcSlZjjgDOList = this.listBdcSlZjjg(bdcSlZjjgQO);
        if(CollectionUtils.isNotEmpty(bdcSlZjjgDOList)){
            this.modifyXmFbSfZjjg(bdcSlZjjgDOList.get(0).getCqxmid(), CommonConstantUtils.SF_S_DM);
        }
    }

    @Override
    public void updateZjjgCxXmfbSfzjjg(String gzlslid) {
        // 获取资金监管流程xmid
        String zjjgXmid = this.getZjjgLcXmid(gzlslid);
        if(StringUtils.isNotBlank(zjjgXmid)){
            BdcSlZjjgQO bdcSlZjjgQO = new BdcSlZjjgQO();
            bdcSlZjjgQO.setXmid(zjjgXmid);
            List<BdcSlZjjgDO> bdcSlZjjgDOList = this.listBdcSlZjjg(bdcSlZjjgQO);
            if(CollectionUtils.isNotEmpty(bdcSlZjjgDOList)){
                this.modifyXmFbSfZjjg(bdcSlZjjgDOList.get(0).getCqxmid(), CommonConstantUtils.SF_F_DM);
            }
        }
    }

    /**
     * 异步保存资金监管一体化平台的返回值（额外请求文件下载接口上传到大云）
     *
     * @param zjjgResponseDto
     */
    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void saveYthptZjjg(ZjjgResponseDto zjjgResponseDto) {
        if(Objects.nonNull(zjjgResponseDto)
                && Objects.nonNull(zjjgResponseDto.getData())
                && Objects.nonNull(zjjgResponseDto.getData().getJgxybh())
        ) {
            BdcSlZjjgxyDO bdcSlZjjgxyDO = new BdcSlZjjgxyDO();
            BeanUtils.copyProperties(zjjgResponseDto.getData(),bdcSlZjjgxyDO);
            //删除原始数据
            Example example = new Example(BdcSlZjjgxyDO.class);
            example.createCriteria()
                    .andEqualTo("jgxybh",bdcSlZjjgxyDO.getJgxybh());
            entityMapper.deleteByExample(example);

            //删除原始数据
            example = new Example(BdcSlZjjgxyjcDO.class);
            example.createCriteria()
                    .andEqualTo("jgxybh",bdcSlZjjgxyDO.getJgxybh());
            entityMapper.deleteByExample(example);

            entityMapper.insertSelective(bdcSlZjjgxyDO);
            if(CollectionUtils.isNotEmpty(zjjgResponseDto.getData().getJymx())){
                for (BdcSlZjjgxyjcDO bdcSlZjjgxyjcDO : zjjgResponseDto.getData().getJymx()) {
                    bdcSlZjjgxyjcDO.setJcmxid(UUIDGenerator.generate16());
                    bdcSlZjjgxyjcDO.setJgxybh(bdcSlZjjgxyDO.getJgxybh());
                    entityMapper.insertSelective(bdcSlZjjgxyjcDO);
                }
            }

            //文件上传
            String dzbzpz = zjjgResponseDto.getData().getDzbzpz();
            if(StringUtils.isNotBlank(dzbzpz)){
                try {
                    HashMap paramMap = new HashMap();
                    paramMap.put("fileId",dzbzpz);
                    paramMap.put("jgxybh",zjjgResponseDto.getData().getJgxybh());
                    Object zjj_fjxz_ob = exchangeInterfaceFeignService.sjptRequestInterface("zjj_fjxz", paramMap);
                    log.info("资金监管文件下载接口返回值：{}",JSON.toJSONString(zjj_fjxz_ob));
                } catch (Exception e){
                    LOGGER.error("下载资金监管文件信息出错, {}", e.getMessage());
                }
            }
        }
    }

    /**
     * 查询平台资金监管
     *
     * @param htbh
     * @return
     */
    @Override
    public YthZjjgDto listYthZjjg(String htbh) {
        if(StringUtils.isBlank(htbh)){
            throw new AppException("参数错误");
        }
        YthZjjgDto zjjgResponseDto = new YthZjjgDto();
        Example example = new Example(BdcSlZjjgxyDO.class);
        example.createCriteria()
                .andEqualTo("htbh",htbh);
        List<BdcSlZjjgxyDO> bdcSlZjjgxyDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcSlZjjgxyDOList)){
            BdcSlZjjgxyDO bdcSlZjjgxyDO = bdcSlZjjgxyDOList.get(0);
            zjjgResponseDto.setBdcSlZjjgxyDO(bdcSlZjjgxyDO);
            example = new Example(BdcSlZjjgxyjcDO.class);
            example.createCriteria()
                    .andEqualTo("jgxybh",bdcSlZjjgxyDO.getJgxybh());
            List<BdcSlZjjgxyjcDO> bdcSlZjjgxyjcDOList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcSlZjjgxyjcDOList)){
                zjjgResponseDto.setBdcSlZjjgxyjcDOList(bdcSlZjjgxyjcDOList);
            }
        }
        return zjjgResponseDto;
    }

    /**
     * 查询平台资金监管附件
     *
     * @param htbh
     * @return
     */
    @Override
    public StorageDto getFileStorageUrl(String htbh) {
        if(StringUtils.isBlank(htbh)){
            throw new AppException("参数错误");
        }
        YthZjjgDto zjjgResponseDto = new YthZjjgDto();
        Example example = new Example(BdcSlZjjgxyDO.class);
        example.createCriteria()
                .andEqualTo("htbh",htbh);
        List<BdcSlZjjgxyDO> bdcSlZjjgxyDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcSlZjjgxyDOList)){
            BdcSlZjjgxyDO bdcSlZjjgxyDO = bdcSlZjjgxyDOList.get(0);
            List<StorageDto> storageDtoList = storageClient.queryMenus(CommonConstantUtils.WJZX_CLIENTID,
                    bdcSlZjjgxyDO.getJgxybh(), "", null, 0, null, null, null);
            if (CollectionUtils.isNotEmpty(storageDtoList) &&
                    CollectionUtils.isNotEmpty(storageDtoList.get(0).getChildren())){
                StorageDto newestStorageDto = null;
                List<StorageDto> children = storageDtoList.get(0).getChildren();
                for (StorageDto child : children) {
                    if(Objects.isNull(newestStorageDto) ||
                            newestStorageDto.getCreateAt().before(child.getCreateAt())
                    ){
                        newestStorageDto = child;
                    }
                }
                if (Objects.isNull(newestStorageDto)) {
                    return storageDtoList.get(0).getChildren().get(0);
                }else {
                    return newestStorageDto;
                }
            }
        }
        return null;
    }

    /**
     * 更新项目附表，是否资金监管信息
     */
    private void modifyXmFbSfZjjg(String xmid, Integer sfzjjg){
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        Map whereMap = new HashMap<>();
        whereMap.put("xmids", Arrays.asList(xmid));
        bdcDjxxUpdateQO.setWhereMap(whereMap);

        BdcXmFbDO bdcXmFbDO = new BdcXmFbDO();
        bdcXmFbDO.setSfzjjg(sfzjjg);
        bdcDjxxUpdateQO.setJsonStr(JSON.toJSONString(bdcXmFbDO));
        try {
            bdcXmfbFeignService.updateBatchBdcxmFb(bdcDjxxUpdateQO);
        } catch (Exception e) {
            LOGGER.error("更新项目附表是否资金监管信息出错, {}", e.getMessage());
        }
    }
}
