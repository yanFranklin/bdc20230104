package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwHstService;
import cn.gtmap.realestate.building.core.service.FwYcHsService;
import cn.gtmap.realestate.building.service.HstService;
import cn.gtmap.realestate.building.service.ReadHstService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FwHstRequestDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-25
 * @description 户室图服务
 */
@Service
public class HstServiceImpl implements HstService {

    @Autowired
    private FwHstService fwHstService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private FwHsService fwHsService;
    @Autowired
    private FwYcHsService fwYcHsService;

    /**
     * @param fwHstRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存户室图
     */
    @Override
    @Transactional
    public FwHstDO saveFwHst(FwHstRequestDTO fwHstRequestDTO) {
        FwHstDO fwHstDO;
        String fwHstIndex = fwHstRequestDTO.getFwHstIndex();
        if(StringUtils.isBlank(fwHstIndex)){
            // 没传主键 新增户室图
            fwHstDO = new FwHstDO();
            fwHstIndex = UUIDGenerator.generate();
        }else{
            // 传主键 查询已有户室图数据
            fwHstDO = fwHstService.queryFwHstByIndex(fwHstIndex);
            if(fwHstDO == null){
                fwHstDO = new FwHstDO();
            }else{
                // 判断 downid是否存在如果存在 删除
                if(StringUtils.isNotBlank(fwHstDO.getHstdownid())){
                    deleteStorage(fwHstDO.getHstdownid());
                }
            }
        }
        fwHstDO.setFwHstIndex(fwHstIndex);
        fwHstDO.setJlyhm(fwHstRequestDTO.getJlyhm());
        fwHstDO.setHstdownid(fwHstRequestDTO.getDownId());
        fwHstDO.setScsj(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        fwHstDO.setHstmc(fwHstRequestDTO.getHstmc());

        // 插入 或 更新 户室图
        entityMapper.saveOrUpdate(fwHstDO,fwHstIndex);

        // 更新户室存储户室图主键
        if(StringUtils.isNotBlank(fwHstRequestDTO.getFwHsIndex())){
            // 再保存房屋户室图
            if(StringUtils.equals(Constants.FW_SCHS,fwHstRequestDTO.getFwlx())){
                FwHsDO fwHsDO = new FwHsDO();
                fwHsDO.setFwHsIndex(fwHstRequestDTO.getFwHsIndex());
                fwHsDO.setFwHstIndex(fwHstIndex);
                fwHsService.updateFwHs(fwHsDO,false);
            }else if(StringUtils.equals(Constants.FW_YCHS,fwHstRequestDTO.getFwlx())){
                FwYchsDO fwYchsDO=new FwYchsDO();
                fwYchsDO.setFwHsIndex(fwHstRequestDTO.getFwHsIndex());
                fwYchsDO.setFwHstIndex(fwHstIndex);
                fwYcHsService.updateFwYcHs(fwYchsDO,false);
            }
        }
        return fwHstDO;
    }

    /**
     * @param fwHstRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存幢平面图
     */
    @Override
    public FwHstDO saveLjzPmt(FwHstRequestDTO fwHstRequestDTO) {
        String fwHstIndex = fwHstRequestDTO.getFwHstIndex();
        if(StringUtils.isNotBlank(fwHstIndex)) {
            FwHstDO fwHstDO = fwHstService.queryFwHstByIndex(fwHstIndex);
            boolean insertFlag = false;
            if (fwHstDO == null) {
                // 原不存在平面图 此操作为新增操作
                fwHstDO = new FwHstDO();
                fwHstDO.setFwHstIndex(fwHstIndex);
                insertFlag = true;
            } else if(StringUtils.isNotBlank(fwHstDO.getHstdownid())){
                // 原存在平面图
                // 删除原有平面图
                deleteStorage(fwHstDO.getHstdownid());
            }
            fwHstDO.setJlyhm(fwHstRequestDTO.getJlyhm());
            fwHstDO.setHstdownid(fwHstRequestDTO.getDownId());
            fwHstDO.setScsj(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            fwHstDO.setHstmc(fwHstRequestDTO.getHstmc());
            if(insertFlag){
                entityMapper.insertSelective(fwHstDO);
            }else{
                entityMapper.updateByPrimaryKeySelective(fwHstDO);
            }
            return fwHstDO;
        }
        return null;
    }

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除户室的户室图
     */
    @Override
    public void delFwhsHst(String fwHsIndex,String hslx) {
        if(StringUtils.isNotBlank(fwHsIndex)){
            String fwHstIndex=null;
            if(StringUtils.equals(hslx,Constants.FW_YCHS)){
                FwYchsDO fwYchsDO=fwYcHsService.queryFwYcHsByFwHsIndex(fwHsIndex);
                if(fwYchsDO !=null && StringUtils.isNotBlank(fwYchsDO.getFwHstIndex())){
                    fwHstIndex=fwYchsDO.getFwHstIndex();
                    fwYchsDO.setFwHstIndex(null);
                    entityMapper.updateByPrimaryKeyNull(fwYchsDO);
                }
            }else if(StringUtils.equals(hslx,Constants.FW_SCHS)){
                FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(fwHsIndex);
                if(fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getFwHstIndex())){
                    fwHstIndex = fwHsDO.getFwHstIndex();
                    // 清空当前户室的户室图外键
                    fwHsDO.setFwHstIndex(null);
                    entityMapper.updateByPrimaryKeyNull(fwHsDO);
                }
            }

            if(StringUtils.isNotBlank(fwHstIndex)){
                // 判断是否有其他户室  使用这个户室图
                Example example = new Example(FwHsDO.class);
                example.createCriteria().andEqualTo("fwHstIndex",fwHstIndex);
                List<FwHsDO> fwHsDOList = entityMapper.selectByExample(example);
                // 如果 为空 则 删除户室图数据
                if(CollectionUtils.isEmpty(fwHsDOList)){
                    FwHstDO fwHstDO = fwHstService.queryFwHstByIndex(fwHstIndex);
                    deleteFwHst(fwHstDO);
                }
            }

        }
    }

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除独幢的平面图
     */
    @Override
    public void delFwLjzPmt(String fwDcbIndex) {
        if(StringUtils.isNotBlank(fwDcbIndex)){
            FwHstDO fwHstDO = fwHstService.queryFwHstByIndex(fwDcbIndex);
            deleteFwHst(fwHstDO);
        }
    }

    /**
     * @param fwHstDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除房屋户室图 包含删除图片操作
     */
    @Override
    public void deleteFwHst(FwHstDO fwHstDO) {
        // todo 判断 存储类型  执行删除 目前只支持 国图大云删除
        if(fwHstDO != null && StringUtils.isNotBlank(fwHstDO.getHstdownid())){
            deleteStorage(fwHstDO.getHstdownid());
            entityMapper.delete(fwHstDO);
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param id
     * @return void
     * @description 删除国图大云存储的图片
     */
    private void deleteStorage(String id){
        List<String> ids = new ArrayList<>();
        ids.add(id);
        storageClient.deleteStorages(ids);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return cn.gtmap.realestate.building.service.ReadZdtService
     * @description 通过配置 获取  读宗地图的实现服务
     */
    @Override
    public ReadHstService getConfigReadService(){
        String beanId = EnvironmentConfig.getEnvironment().getProperty("hstService");
        if(StringUtils.isBlank(beanId)){
            // 如果没有配置 默认 走合肥实现
            beanId = "readHstFromDbBase64ServiceImpl";
        }
        Object readHstService = Container.getBean(beanId);
        return (ReadHstService)readHstService;
    }

}
