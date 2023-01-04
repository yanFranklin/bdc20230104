package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcTddysfDyhDO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhShDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.building.BdcTddysfxxQO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.core.service.BdcTddysfDyhService;
import cn.gtmap.realestate.register.service.BdcQlxxService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/12
 * @description 不动产项目土地抵押释放关系服务
 */
@Service
public class BdcTddysfDyhServiceImpl implements BdcTddysfDyhService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcTddysfDyhServiceImpl.class);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  生成方式：流程关联
     */
    private static final Integer SCFS_LCGL = 1;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成方式：例外关联
     */
    private static final Integer SCFS_LWGL = 2;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    FwHsFeignService fwHsFeignService;

    @Autowired
    BdcQlxxService bdcQlxxService;


    @Override
    public List<BdcTddysfDyhDO> listTddysfBdcdyhByGzlslid(String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)){
            Example example =new Example(BdcTddysfDyhDO.class);
            example.createCriteria().andEqualTo("gzlslid",gzlslid);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>();

    }

    @Override
    public void initBdcTddysfDyh(BdcTddysfDyhDTO bdcTddysfDyhDTO){
        if(bdcTddysfDyhDTO.getScfs() ==null){
            throw new AppException(ErrorCode.MISSING_ARG,"土地抵押释放生成,未定义生成方式");
        }
        if(CollectionUtils.isEmpty(bdcTddysfDyhDTO.getBdcdyhList())){
            throw new AppException(ErrorCode.MISSING_ARG,"土地抵押释放生成,没有单元号数据");
        }
        if(SCFS_LCGL.equals(bdcTddysfDyhDTO.getScfs())){
            //流程关联
            if(StringUtils.isBlank(bdcTddysfDyhDTO.getGzlslid())){
                throw new AppException(ErrorCode.MISSING_ARG,"土地抵押释放流程生成,缺失工作流实例ID");
            }
        }
        bdcdyhFilter(bdcTddysfDyhDTO);
        String czr=userManagerUtils.getUserAlias();
        List<BdcTddysfDyhDO> bdcTddysfDyhDOList =new ArrayList<>();
        for(String bdcdyh:bdcTddysfDyhDTO.getBdcdyhList()){
            BdcTddysfDyhDO bdcTddysfDyhDO =new BdcTddysfDyhDO();
            BeanUtils.copyProperties(bdcTddysfDyhDTO, bdcTddysfDyhDO);
            bdcTddysfDyhDO.setBdcdyh(bdcdyh);
            bdcTddysfDyhDO.setId(UUIDGenerator.generate16());
            bdcTddysfDyhDO.setCjr(czr);
            bdcTddysfDyhDO.setCjsj(new Date());
            bdcTddysfDyhDO.setShzt(CommonConstantUtils.GZLW_SHZT_XJ);
            bdcTddysfDyhDOList.add(bdcTddysfDyhDO);
        }
        if(CollectionUtils.isNotEmpty(bdcTddysfDyhDOList)){
            entityMapper.insertBatchSelective(bdcTddysfDyhDOList);
        }
    }

    @Override
    public void deleteBdcTddysfDyh(BdcTddysfDyhDTO bdcTddysfDyhDTO){
        if(bdcTddysfDyhDTO.getScfs() ==null){
            throw new AppException(ErrorCode.MISSING_ARG,"土地抵押释放删除,未定义生成方式");
        }
        if(CollectionUtils.isEmpty(bdcTddysfDyhDTO.getBdcdyhList())){
            throw new AppException(ErrorCode.MISSING_ARG,"土地抵押释放删除,没有单元号数据");
        }
        if(SCFS_LCGL.equals(bdcTddysfDyhDTO.getScfs())){
            //流程关联
            if(StringUtils.isBlank(bdcTddysfDyhDTO.getGzlslid())){
                throw new AppException(ErrorCode.MISSING_ARG,"土地抵押释放流程删除,缺失工作流实例ID");
            }
        }
        Example example =new Example(BdcTddysfDyhDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andInWithListString("bdcdyh",bdcTddysfDyhDTO.getBdcdyhList());
        criteria.andEqualTo("scfs",bdcTddysfDyhDTO.getScfs());
        if(StringUtils.isNotBlank(bdcTddysfDyhDTO.getGzlslid())){
            criteria.andEqualTo("gzlslid",bdcTddysfDyhDTO.getGzlslid());
        }
        entityMapper.deleteByExampleNotNull(example);
    }

    @Override
    public void updateBdcTddysfDyhShxx(BdcTddysfDyhShDTO bdcTddysfDyhShDTO){
        if(CollectionUtils.isEmpty(bdcTddysfDyhShDTO.getBdcTddysfDyhDOList())){
            throw new AppException(ErrorCode.MISSING_ARG,"土地抵押释放审核,没有单元号数据");
        }
        List<String> bdcdyhList =bdcTddysfDyhShDTO.getBdcTddysfDyhDOList().stream().map(BdcTddysfDyhDO::getBdcdyh).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(bdcdyhList)){
            throw new AppException(ErrorCode.MISSING_ARG,"土地抵押释放审核,没有单元号数据");
        }
        //更新审核状态和审核意见
        BdcTddysfDyhDO bdcTddysfDyhDO =new BdcTddysfDyhDO();
        bdcTddysfDyhDO.setShzt(bdcTddysfDyhShDTO.getShzt());
        bdcTddysfDyhDO.setShyj(bdcTddysfDyhShDTO.getShyj());
        bdcTddysfDyhDO.setShr(userManagerUtils.getUserAlias());
        bdcTddysfDyhDO.setShsj(new Date());
        Example example =new Example(BdcTddysfDyhDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andInWithListString("bdcdyh",bdcdyhList);
        criteria.andEqualTo("scfs",SCFS_LWGL);
        criteria.andEqualTo("shzt",CommonConstantUtils.GZLW_SHZT_XJ);
        entityMapper.updateByExampleSelectiveNotNull(bdcTddysfDyhDO,example);
        if(CommonConstantUtils.GZLW_SHZT_TG.equals(bdcTddysfDyhShDTO.getShzt())){
            //更新土地抵押释放信息
            updateTddysfxx(bdcTddysfDyhShDTO);
            //更新附记
            updateTddyFj(bdcTddysfDyhShDTO);
        }

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  单元号验证
     */
    private void bdcdyhFilter(BdcTddysfDyhDTO bdcTddysfDyhDTO){
        List<BdcTddysfDyhDO> bdcTddysfDyhDOList =listBdcTddysfDyh(bdcTddysfDyhDTO.getBdcdyhList(),bdcTddysfDyhDTO.getScfs(),CommonConstantUtils.GZLW_SHZT_BTG);
        if(CollectionUtils.isNotEmpty(bdcTddysfDyhDOList)){
            List<String> bdcdyhList =bdcTddysfDyhDOList.stream().map(BdcTddysfDyhDO::getBdcdyh).collect(Collectors.toList());
            throw new AppException(ErrorCode.CUSTOM,"以下单元号重复关联"+ JSONObject.toJSONString(bdcdyhList));
        }
        Set<String> zddyhSet = new HashSet();
        for(String bdcdyh:bdcTddysfDyhDTO.getBdcdyhList()){
            if(StringUtils.isNotBlank(bdcdyh) &&bdcdyh.length() ==28){
                zddyhSet.add(BdcdyhToolUtils.convertToW(bdcdyh.substring(0,19)));
            }
        }
        for(String zddyh:zddyhSet){
            //查询宗地抵押
            List<Integer> qsztList =new ArrayList<>();
            qsztList.add(CommonConstantUtils.QSZT_VALID);
            List<BdcQl> bdcQlList =bdcQlxxService.listBdcQlxx(zddyh, CommonConstantUtils.QLLX_DYAQ_DM.toString(), qsztList);
            if(CollectionUtils.isEmpty(bdcQlList)){
                throw new AppException(ErrorCode.CUSTOM,"宗地单元号"+zddyh+"不存在抵押,无需创建例外");
            }
        }


    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新土地抵押释放信息
     */
    private void updateTddysfxx(BdcTddysfDyhShDTO bdcTddysfDyhShDTO){
        //按照qjgldm分组
        Map<String,List<String>> qjgldmBdcdyhMap =new HashMap<>();
        for (BdcTddysfDyhDO tddysfDyhDO : bdcTddysfDyhShDTO.getBdcTddysfDyhDOList()) {
            if(StringUtils.isNotBlank(tddysfDyhDO.getBdcdyh())) {
                if (qjgldmBdcdyhMap.containsKey(tddysfDyhDO.getQjgldm())) {
                    List<String> dyhList = qjgldmBdcdyhMap.get(tddysfDyhDO.getQjgldm());
                    dyhList.add(tddysfDyhDO.getBdcdyh());
                    qjgldmBdcdyhMap.put(tddysfDyhDO.getQjgldm(), dyhList);
                } else {
                    List<String> dyhList = new ArrayList<>();
                    dyhList.add(tddysfDyhDO.getBdcdyh());
                    qjgldmBdcdyhMap.put(tddysfDyhDO.getQjgldm(), dyhList);
                }
            }
        }
        if(MapUtils.isNotEmpty(qjgldmBdcdyhMap)){
            for (Map.Entry<String, List<String>> entry : qjgldmBdcdyhMap.entrySet()) {
                //审核通过,更新释放信息
                BdcTddysfxxQO bdcTddysfxxQO = new BdcTddysfxxQO();
                bdcTddysfxxQO.setTddysfsf(CommonConstantUtils.SF_ZW_S);
                bdcTddysfxxQO.setTddysfczr(userManagerUtils.getUserAlias());
                bdcTddysfxxQO.setTddysfsj(new Date());
                bdcTddysfxxQO.setQjgldm(entry.getKey());
                bdcTddysfxxQO.setBdcdyhList(entry.getValue());
                fwHsFeignService.updateFwhsTddysfxx(bdcTddysfxxQO);
                LOGGER.info("例外审核更新土地抵押释放信息结束,土地抵押释放更新对象：{}",bdcTddysfxxQO);
            }
        }

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  更新土地抵押附记
     */
    private void updateTddyFj(BdcTddysfDyhShDTO bdcTddysfDyhShDTO){
        //按照djh分组
        Map<String,List<String>> djhDyhMap =new HashMap<>();
        for (BdcTddysfDyhDO tddysfDyhDO : bdcTddysfDyhShDTO.getBdcTddysfDyhDOList()) {
            if(StringUtils.isNotBlank(tddysfDyhDO.getBdcdyh())) {
                String djh =tddysfDyhDO.getBdcdyh().substring(0,19);
                if (djhDyhMap.containsKey(djh)) {
                    List<String> dyhList = djhDyhMap.get(djh);
                    dyhList.add(tddysfDyhDO.getBdcdyh());
                    djhDyhMap.put(djh, dyhList);
                } else {
                    List<String> dyhList = new ArrayList<>();
                    dyhList.add(tddysfDyhDO.getBdcdyh());
                    djhDyhMap.put(djh, dyhList);
                }
            }
        }
        if(MapUtils.isNotEmpty(djhDyhMap)){
            for (Map.Entry<String, List<String>> entry : djhDyhMap.entrySet()) {
                //查询宗地抵押
                List<Integer> qsztList =new ArrayList<>();
                qsztList.add(CommonConstantUtils.QSZT_VALID);
                List<BdcQl> bdcQlList =bdcQlxxService.listBdcQlxx(BdcdyhToolUtils.convertToW(entry.getKey()), CommonConstantUtils.QLLX_DYAQ_DM.toString(), qsztList);
                if(CollectionUtils.isNotEmpty(bdcQlList)){
                    List<BdcTddysfDyhDO> bdcTddysfDyhDOList =listBdcTddysfDyh(entry.getValue(),SCFS_LWGL,CommonConstantUtils.GZLW_SHZT_BTG);
                    List<String> lwyyList = bdcTddysfDyhDOList.stream().map(BdcTddysfDyhDO::getLwyy).distinct().collect(Collectors.toList());
                    for(BdcQl bdcQl:bdcQlList){
                        for(String lwyy:lwyyList){
                            if(StringUtils.isBlank(bdcQl.getFj())){
                                bdcQl.setFj(lwyy);
                            }else if(StringUtils.isNotBlank(lwyy) &&!bdcQl.getFj().contains(lwyy)){
                                bdcQl.setFj(bdcQl.getFj() + "\n" + lwyy);
                            }
                        }
                        LOGGER.info("例外审核更新土地抵押附记信息结束,权利信息：{}",bdcQl);
                        entityMapper.updateByPrimaryKeySelective(bdcQl);
                    }
                }
            }
        }

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询土地抵押释放单元号信息
     */
    private List<BdcTddysfDyhDO> listBdcTddysfDyh(List<String> bdcdyhList,Integer scfs,Integer pcshzt){
        if(CollectionUtils.isNotEmpty(bdcdyhList)) {
            Example example = new Example(BdcTddysfDyhDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andInWithListString("bdcdyh", bdcdyhList);
            if(scfs !=null) {
                criteria.andEqualTo("scfs", scfs);
            }
            if(pcshzt != null){
                criteria.andNotEqualTo("shzt",pcshzt);
            }
            return entityMapper.selectByExample(example);

        }
        return new ArrayList<>();
    }


}
