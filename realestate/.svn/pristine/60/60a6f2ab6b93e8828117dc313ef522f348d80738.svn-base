package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.gtc.workflow.utils.BeanUtil;
import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrHkbGxDO;
import cn.gtmap.realestate.common.core.domain.building.NydJtcyDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.init.BdcJtcySaveDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrHkbGxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrHkbGxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.mapper.BdcJtcyMapper;
import cn.gtmap.realestate.init.core.mapper.BdcQlrMapper;
import cn.gtmap.realestate.init.core.service.BdcJtcyService;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/4.
 * @description
 */

@Service
public class BdcJtcyServiceImpl implements BdcJtcyService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    protected DozerBeanMapper initDozerMapper;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Autowired
    DozerUtils dozerUtils;
    @Autowired
    private BdcJtcyMapper bdcJtcyMapper;
    @Autowired
    private BdcQlrMapper bdcQlrMapper;
    @Autowired
    private BdcQlrService bdcQlrService;

    @Override
    public List<BdcQlrHkbGxDTO> queryBdcQlrHkbGx(BdcQlrHkbGxQO bdcQlrHkbGxQO) {
        return bdcJtcyMapper.queryBdcQlrHkbGx(bdcQlrHkbGxQO);
    }
    @Override
    public List<BdcQlrHkbGxDO> listBdcQlrHkbGxDO(BdcQlrHkbGxQO bdcQlrHkbGxQO){
        return bdcJtcyMapper.listBdcQlrHkbGxDO(bdcQlrHkbGxQO);
    }

    @Override
    public void deleteBatchQlrHkbGx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid, String qlrlb) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put("gzlslid", gzlslid);
            map.put("qlrlb", qlrlb);
            bdcJtcyMapper.deleteQlrHkbGx(map);
        }
    }
    @Override
    public List<BdcJtcyDO> queryJtcy(BdcJtcyDO bdcJtcyDO){
        return entityMapper.selectByObj(bdcJtcyDO);
    }
    @Override
    public boolean compareJtcy(List<NydJtcyDO> nydJtcyDOList, List<BdcJtcyDO> jtcyDOList){
        boolean isEqual = false;
        if(CollectionUtils.isNotEmpty(nydJtcyDOList) && CollectionUtils.isNotEmpty(jtcyDOList) && nydJtcyDOList.size()==jtcyDOList.size()){
            for(NydJtcyDO nydJtcyDO:nydJtcyDOList){
                List<BdcJtcyDO> list = jtcyDOList.stream().filter(jtcy -> StringUtils.isNoneBlank(jtcy.getJtcyzjh(),jtcy.getJtcymc(),jtcy.getYhzgx()) &&jtcy.getJtcyzjh().equals(nydJtcyDO.getSfzhm()) &&jtcy.getJtcymc().equals(nydJtcyDO.getXm()) &&jtcy.getYhzgx().equals(nydJtcyDO.getGx())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(list)){
                    return isEqual;
                }
            }
            isEqual = true;
        }
        return isEqual;
    }

    @Override
    public List<BdcJtcyDO> listBdcJtcyByQlr(BdcQlrQO bdcQlrQO){
        if (!CheckParameter.checkAnyParameter(bdcQlrQO,"qlrid","xmid","qlrlb")) {
            throw new MissingArgumentException(JSONObject.toJSONString(bdcQlrQO));
        }
        Map<String,Object> param = new HashMap<>();
        dozerUtils.sameBeanDateConvert(bdcQlrQO, param, false);
        return bdcJtcyMapper.queryJtcy(param);
    }

    @Override
    public void deleteBdcJtcyByGzlslid(String jtcyid,String gzlslid){
        if(StringUtils.isBlank(jtcyid) ||StringUtils.isBlank(gzlslid)){
            throw new AppException("删除流程家庭成员工作流实例ID或家庭成员ID为空");
        }
        //判断是否有其他业务关系表
        BdcQlrHkbGxQO bdcQlrHkbGxQO = new BdcQlrHkbGxQO();
        bdcQlrHkbGxQO.setJtcyid(jtcyid);
        bdcQlrHkbGxQO.setLwgzlslid(gzlslid);
        List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList = listBdcQlrHkbGxDO(bdcQlrHkbGxQO);
        if(CollectionUtils.isNotEmpty(bdcQlrHkbGxDOList)){
            //存在,生成一个新的户口簿版本
            bdcQlrHkbGxQO = new BdcQlrHkbGxQO();
            bdcQlrHkbGxQO.setJtcyid(jtcyid);
            bdcQlrHkbGxQO.setGzlslid(gzlslid);
            List<BdcQlrHkbGxDO> ybdcQlrHkbGxList = listBdcQlrHkbGxDO(bdcQlrHkbGxQO);
            if(CollectionUtils.isNotEmpty(ybdcQlrHkbGxList)){
                //生成新的家庭成员信息
                BdcQlrQO bdcQlrQO =new BdcQlrQO();
                bdcQlrQO.setQlrid(ybdcQlrHkbGxList.get(0).getQlrid());
                List<BdcJtcyDO> ybdcJtcyDOList =listBdcJtcyByQlr(bdcQlrQO);
                if(CollectionUtils.isNotEmpty(ybdcJtcyDOList)){
                    List<BdcJtcyDO> jtcyDOList = ybdcJtcyDOList.stream().filter(yjtcyDO -> !StringUtils.equals(jtcyid,yjtcyDO.getJtcyid())).collect(Collectors.toList());
                    List<BdcJtcyDO> newbdcJtcyDOList =createNewBdcJtcy(jtcyDOList,ybdcJtcyDOList.get(0).getHkbbm(),ybdcJtcyDOList.get(0).getHkbbbh()+1);
                    if(CollectionUtils.isNotEmpty(newbdcJtcyDOList)) {
                        entityMapper.insertBatchSelective(newbdcJtcyDOList);
                    }
                    //生成新的权利人户口簿关系
                    entityMapper.batchSaveSelective(createNewBdcQlrHkbGx(ybdcQlrHkbGxList,ybdcJtcyDOList.get(0).getHkbbbh()+1));
                }
            }
        }else{
            //不存在直接删除
            entityMapper.deleteByPrimaryKey(BdcJtcyDO.class,jtcyid);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatchJtcyxx(BdcJtcySaveDTO bdcJtcySaveDTO){
        if(CollectionUtils.isNotEmpty(bdcJtcySaveDTO.getBdcJtcyDOList()) &&StringUtils.isNotBlank(bdcJtcySaveDTO.getQlrid())){
            boolean jtcyChange =false;
            BdcQlrDO bdcQlrDO =entityMapper.selectByPrimaryKey(BdcQlrDO.class,bdcJtcySaveDTO.getQlrid());
            if(bdcQlrDO != null) {
                //权利人ID有值,比较当前家庭成员与原家庭成员信息
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setQlrid(bdcJtcySaveDTO.getQlrid());
                List<BdcJtcyDO> yjtcyList = listBdcJtcyByQlr(bdcQlrQO);
                if (compareJtcyWithYjtcy(yjtcyList, bdcJtcySaveDTO.getBdcJtcyDOList())) {
                    //一致,更新即可
                    for (BdcJtcyDO bdcJtcyDO : bdcJtcySaveDTO.getBdcJtcyDOList()) {
                        if (StringUtils.isNotBlank(bdcJtcyDO.getJtcyid())) {
                            entityMapper.updateByPrimaryKeySelective(bdcJtcyDO);
                        }
                    }
                } else {
                    Integer hkbbbh;
                    String hkbbm;
                    //当前家庭成员是否应用于其他业务
                    boolean existQtyw =false;
                    if(CollectionUtils.isNotEmpty(yjtcyList)) {
                        existQtyw = checkExistQtyw(yjtcyList.get(0).getJtcyid(), bdcJtcySaveDTO.getGzlslid());
                    }
                    if(!existQtyw){
                        if(CollectionUtils.isNotEmpty(yjtcyList)) {
                            //当前流程原家庭成员不存在其他业务,直接删除
                            for (BdcJtcyDO bdcJtcyDO : yjtcyList) {
                                entityMapper.deleteByPrimaryKey(BdcJtcyDO.class, bdcJtcyDO.getJtcyid());
                            }
                        }
                        //再查询是否其他业务是否存在此家庭成员
                        List<BdcJtcyDO> bdcJtcyDOList = bdcJtcyMapper.queryLastBbhJtcyByHzZJH(bdcQlrDO.getZjh());
                        if (CollectionUtils.isEmpty(bdcJtcyDOList)) {
                            jtcyChange = true;
                            hkbbbh = Constants.JTCY_HKB_BBH;
                            hkbbm = CollectionUtils.isNotEmpty(yjtcyList) ?yjtcyList.get(0).getHkbbm() :bdcBhFeignService.queryBh(Constants.YWLX_HKB, CommonConstantUtils.ZZSJFW_YEAR);
                        } else {
                            hkbbm = bdcJtcyDOList.get(0).getHkbbm();
                            if (!compareJtcyWithYjtcy(bdcJtcyDOList, bdcJtcySaveDTO.getBdcJtcyDOList())) {
                                hkbbbh = bdcJtcyDOList.get(0).getHkbbbh() + 1;
                                jtcyChange = true;
                            } else {
                                hkbbbh = bdcJtcyDOList.get(0).getHkbbbh();
                            }
                        }
                    }else{
                        jtcyChange = true;
                        hkbbbh = yjtcyList.get(0).getHkbbbh() + 1;
                        hkbbm = yjtcyList.get(0).getHkbbm();
                    }

                    //生成权利人户口簿关系表
                    BdcQlrHkbGxQO bdcQlrHkbGxQO = new BdcQlrHkbGxQO();
                    bdcQlrHkbGxQO.setGzlslid(bdcJtcySaveDTO.getGzlslid());
                    bdcQlrHkbGxQO.setZjh(bdcQlrDO.getZjh());
                    List<BdcQlrHkbGxDO> ybdcQlrHkbGxList = listBdcQlrHkbGxDO(bdcQlrHkbGxQO);

                    if (CollectionUtils.isNotEmpty(ybdcQlrHkbGxList)) {
                        if(hkbbbh != null &&!hkbbbh.equals(ybdcQlrHkbGxList.get(0).getHkbbbh())) {
                            entityMapper.batchSaveSelective(createNewBdcQlrHkbGx(ybdcQlrHkbGxList, hkbbbh));
                        }
                    }
                    else {
                        ybdcQlrHkbGxList = new ArrayList<>();
                        Map paramMap = new HashMap();
                        paramMap.put("gzlslid", bdcJtcySaveDTO.getGzlslid());
                        List<BdcQlrDO> bdcQlrDOList = bdcQlrMapper.listAllBdcQlr(paramMap);
                        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                            //找出当前流程该权利人的所有数据
                            // 如果权利人证件号不为空，根据证件号，如果权利人证件号为空，根据权利人名称
                            bdcQlrDOList = bdcQlrDOList.stream().filter(qlrDO ->
                                    ((StringUtils.isNotBlank(bdcQlrDO.getZjh()) && StringUtils.equals(bdcQlrDO.getZjh(), qlrDO.getZjh()))
                                        || (StringUtils.isBlank(bdcQlrDO.getZjh()) && StringUtils.isNotBlank(bdcQlrDO.getQlrmc()) && StringUtils.equals(bdcQlrDO.getQlrmc(), qlrDO.getQlrmc())))
                                        && StringUtils.isNotBlank(bdcQlrDO.getQlrlb()) && bdcQlrDO.getQlrlb().equals(qlrDO.getQlrlb())
                            ).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                                for (BdcQlrDO qlrDO : bdcQlrDOList) {
                                    BdcQlrHkbGxDO qlrHkbGxDO = new BdcQlrHkbGxDO();
                                    qlrHkbGxDO.setQlrid(qlrDO.getQlrid());
                                    qlrHkbGxDO.setHkbbm(hkbbm);
                                    qlrHkbGxDO.setHkbbbh(hkbbbh);
                                    qlrHkbGxDO.setGxid(UUIDGenerator.generate16());
                                    ybdcQlrHkbGxList.add(qlrHkbGxDO);
                                }
                            }
                        }

                        entityMapper.batchSaveSelective(ybdcQlrHkbGxList);
                    }
                    if(jtcyChange){
                        //版本号发生改变,生成新的家庭成员
                        entityMapper.insertBatchSelective(createNewBdcJtcy(bdcJtcySaveDTO.getBdcJtcyDOList(), hkbbm, hkbbbh));
                    }
                }
            }
        }
    }

    @Override
    public boolean compareJtcyWithYjtcy(List<BdcJtcyDO> yJtcyDOList, List<BdcJtcyDO> jtcyDOList){
        boolean isEqual = false;
        if(CollectionUtils.isNotEmpty(yJtcyDOList) && CollectionUtils.isNotEmpty(jtcyDOList) && yJtcyDOList.size()==jtcyDOList.size()){
            for(BdcJtcyDO bdcJtcyDO:yJtcyDOList){
                // 根据证件号 户口簿编码 户口本版本号对比是否相同
                List<BdcJtcyDO> list = jtcyDOList.stream().filter(jtcy -> jtcy.getJtcyzjh().equals(bdcJtcyDO.getJtcyzjh())
                && (StringUtils.isBlank(jtcy.getHkbbm()) || jtcy.getHkbbm().equals(bdcJtcyDO.getHkbbm()))
                && (jtcy.getHkbbbh()==null || jtcy.getHkbbbh().equals(bdcJtcyDO.getHkbbbh()))).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(list)){
                    return isEqual;
                }
            }
            isEqual = true;
        }
        return isEqual;

    }

    @Override
    public List<BdcJtcyDO> createNewBdcJtcy(List<BdcJtcyDO> ybdcJtcyDOList,String hkbbm,Integer hkbbbh){
        List<BdcJtcyDO> newbdcJtcyDOList =new ArrayList<>();
        //生成版本号
        if(CollectionUtils.isNotEmpty(ybdcJtcyDOList)){
            for(BdcJtcyDO yjtcyDO:ybdcJtcyDOList){
                BdcJtcyDO jtcyDO = new BdcJtcyDO();
                BeanUtil.copyBean(yjtcyDO, jtcyDO);
                if(StringUtils.isBlank(jtcyDO.getHkbbm())){
                    jtcyDO.setHkbbm(hkbbm);
                }
                jtcyDO.setJtcyid(UUIDGenerator.generate16());
                jtcyDO.setHkbbbh(hkbbbh);
                newbdcJtcyDOList.add(jtcyDO);
            }
        }
        return newbdcJtcyDOList;
    }

    @Override
    public List<BdcQlrHkbGxDO> createNewBdcQlrHkbGx(List<BdcQlrHkbGxDO> ybdcQlrHkbGxList,Integer hkbbbh){
        List<BdcQlrHkbGxDO> newBdcQlrHkbGxDOList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(ybdcQlrHkbGxList)){
            for(BdcQlrHkbGxDO ybdcQlrHkbGx:ybdcQlrHkbGxList){
                BdcQlrHkbGxDO bdcQlrHkbGxDO = new BdcQlrHkbGxDO();
                BeanUtil.copyBean(ybdcQlrHkbGx,bdcQlrHkbGxDO);
                if(StringUtils.isBlank(bdcQlrHkbGxDO.getGxid())){
                    bdcQlrHkbGxDO.setGxid(UUIDGenerator.generate16());
                }
                bdcQlrHkbGxDO.setHkbbbh(hkbbbh);
                newBdcQlrHkbGxDOList.add(bdcQlrHkbGxDO);
            }
        }
        return newBdcQlrHkbGxDOList;
    }

    @Override
    public void deleteBatchJtcyByGzlslid(String qlrid, String gzlslid){
        if(StringUtils.isBlank(qlrid) ||StringUtils.isBlank(gzlslid)){
            throw new AppException("删除家庭成员失败,权利人ID或工作流实例ID为空");
        }
        //1.查询家庭成员
        BdcQlrQO bdcQlrQO =new BdcQlrQO();
        bdcQlrQO.setQlrid(qlrid);
        List<BdcJtcyDO> jtcyDOList =listBdcJtcyByQlr(bdcQlrQO);
        if(CollectionUtils.isNotEmpty(jtcyDOList)) {
            //2.任取一条家庭成员,查询家庭成员是否存在其他业务
            BdcQlrHkbGxQO bdcQlrHkbGxQO = new BdcQlrHkbGxQO();
            bdcQlrHkbGxQO.setJtcyid(jtcyDOList.get(0).getJtcyid());
            bdcQlrHkbGxQO.setLwgzlslid(gzlslid);
            List<BdcQlrHkbGxDO> bdclwQlrHkbGxDOList = listBdcQlrHkbGxDO(bdcQlrHkbGxQO);
            //3.存在,只删除权利人户口簿关系表;不存在,删除权利人户口簿关系表及家庭成员
            if (CollectionUtils.isEmpty(bdclwQlrHkbGxDOList)) {
                //4.不存在,删除家庭成员
                deleteBdcJtcyByHkbbmAndHkbbh(jtcyDOList.get(0).getHkbbm(),jtcyDOList.get(0).getHkbbbh());
            }
            //5.查询权利人,根据权利人名称，证件号，权利人类别，批量删除权利人户口簿关系
            BdcQlrDO bdcQlrDO =bdcQlrService.queryBdcQlrByQlrid(qlrid);
            if(bdcQlrDO != null &&StringUtils.isNotBlank(bdcQlrDO.getZjh())) {
                Map<String, Object> map = new HashMap<>();
                map.put("gzlslid", gzlslid);
                map.put("qlrlb", bdcQlrDO.getQlrlb());
                map.put("qlrmc", bdcQlrDO.getQlrmc());
                map.put("zjh", bdcQlrDO.getZjh());
                bdcJtcyMapper.deleteQlrHkbGx(map);
            }
        }
    }

    @Override
    public List<BdcJtcyDO> queryJtcyxxByhzZjh(String zjh) {
        if(StringUtils.isBlank(zjh)){
            throw new MissingArgumentException("未获取到户主的证件号信息。");
        }
        return this.bdcJtcyMapper.queryLastBbhJtcyByHzZJH(zjh);
    }

    /**
     * @param hkbbbh 户口簿版本号
     * @param hkbbm 户口簿版本
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据户口簿编码和户口簿版本号删除家庭成员
     */
    private void deleteBdcJtcyByHkbbmAndHkbbh(String hkbbm,Integer hkbbbh){
        if(StringUtils.isNotBlank(hkbbm) &&hkbbbh != null) {
            Example example = new Example(BdcJtcyDO.class);
            example.createCriteria().andEqualTo("hkbbm", hkbbm).andEqualTo("hkbbbh", hkbbbh);
            entityMapper.deleteByExampleNotNull(example);
        }

    }

    private boolean checkExistQtyw(String jtcyid,String gzlslid){
        BdcQlrHkbGxQO bdcQlrHkbGxQO = new BdcQlrHkbGxQO();
        bdcQlrHkbGxQO.setJtcyid(jtcyid);
        bdcQlrHkbGxQO.setLwgzlslid(gzlslid);
        List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList = listBdcQlrHkbGxDO(bdcQlrHkbGxQO);
        return CollectionUtils.isNotEmpty(bdcQlrHkbGxDOList);
    }


}
