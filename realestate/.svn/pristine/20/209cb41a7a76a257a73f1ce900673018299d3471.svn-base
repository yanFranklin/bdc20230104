package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxxMapper;
import cn.gtmap.realestate.accept.core.mapper.BdcYjdhSfxxGxMapper;
import cn.gtmap.realestate.accept.core.service.BdcYjSfDdxxService;
import cn.gtmap.realestate.accept.core.service.BdcYjdhSfxxGxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcYjSfDdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcYjdhSfxxGxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYjSfxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcYjSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/10
 * @description 不动产银行月结单号与受理编号关系服务
 */
@Service
public class BdcYjdhSfxxGxServiceImpl implements BdcYjdhSfxxGxService {

    @Autowired
    private BdcYjdhSfxxGxMapper bdcYjdhSfxxGxMapper;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcSlSfxxMapper bdcSlSfxxMapper;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Autowired
    BdcYjSfDdxxService bdcYjSfDdxxService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;


    @Override
    public List<String> queryYjSfxxidList(String yjdh){
        if(StringUtils.isNotBlank(yjdh)) {
            return bdcYjdhSfxxGxMapper.queryYjSfxxidList(yjdh);
        }
        return null;

    }

    @Override
    public List<BdcYjdhSfxxGxDO> queryBdcYjdhSfxxGxByYjdh(String yjdh) {
        if(StringUtils.isBlank(yjdh)){
            throw new MissingArgumentException("缺失参数月结单号。");
        }
        Example example = new Example(BdcYjdhSfxxGxDO.class);
        example.createCriteria().andEqualTo("yjdh", yjdh);
        return entityMapper.selectByExample(example);
    }

    @Override
    public List<BdcYjdhSfxxGxDO> generateYjSfxxGx(List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDOList) {
        if(CollectionUtils.isNotEmpty(bdcYjdhSfxxGxDOList)){
            // 生成月结单号关联关系数据
            String yjdh = bdcBhFeignService.queryCommonBh(Constants.BH_YWLX_YJDH, Constants.ZZSJFW_DAY, 4, Constants.BH_YJDH_PRE);
            for(BdcYjdhSfxxGxDO bdcYjdhSfxxGxDO:bdcYjdhSfxxGxDOList){
                bdcYjdhSfxxGxDO.setYjdh(yjdh);
                bdcYjdhSfxxGxDO.setGxid(UUIDGenerator.generate16());
            }
            this.entityMapper.insertBatchSelective(bdcYjdhSfxxGxDOList);

            // 生成月结收费订单数据
            BdcYjSfDdxxDO bdcYjSfDdxxDO = new BdcYjSfDdxxDO();
            bdcYjSfDdxxDO.setYjdh(yjdh);
            bdcYjSfDdxxDO.setDdscsj(new Date());
            bdcYjSfDdxxDO.setCzrxm(userManagerUtils.getCurrentUserName());
            this.bdcYjSfDdxxService.insertYjSfDdxx(bdcYjSfDdxxDO);

        }
        return bdcYjdhSfxxGxDOList;
    }

    @Override
    public List<BdcYjSfxxDTO> listBdcYjSfxx(BdcYjSfxxQO bdcYjSfxxQO) {
        // 根据 登簿时间开始时间、结束时间  银行名称 查询 SFXX表中有月结标识的数据
        List<BdcYjSfxxDTO> bdcYjSfxxDTOList = this.bdcSlSfxxMapper.queryYjSfxx(bdcYjSfxxQO);
        if(CollectionUtils.isNotEmpty(bdcYjSfxxDTOList)){
            if(bdcYjSfxxDTOList.size() > 500){
                List<List> subList = ListUtils.subList(bdcYjSfxxDTOList, 500);
                List<BdcYjSfxxDTO> resultList = new ArrayList<>(bdcYjSfxxDTOList.size());
                for (List data : subList) {
                    List copyList = new ArrayList(data);
                    this.addBdcXmxx(copyList, bdcYjSfxxQO);
                    if(CollectionUtils.isNotEmpty(copyList)){
                        resultList.addAll(copyList);
                    }
                }
                return resultList;
            }else{
                this.addBdcXmxx(bdcYjSfxxDTOList, bdcYjSfxxQO);
            }
        }
        return bdcYjSfxxDTOList;
    }

    /**
     * 根据xmid查询项目数据，并添加至月结缴费信息中
     */
    private void addBdcXmxx(List<BdcYjSfxxDTO> bdcYjSfxxDTOList,BdcYjSfxxQO bdcYjSfxxQO){
        if(CollectionUtils.isNotEmpty(bdcYjSfxxDTOList)){
            List<String> xmids = bdcYjSfxxDTOList.stream().filter(t -> StringUtils.isNotBlank(t.getXmid()))
                    .map(t -> t.getXmid()).distinct().collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(xmids)){
                BdcXmQO bdcXmQO = this.addBdcXmQueryParam(xmids, bdcYjSfxxQO);
                Map<String, BdcXmDO> xmxxMap = this.bdcXmFeignService.listBdcXm(bdcXmQO)
                        .stream().collect(Collectors.toMap(BdcXmDO::getXmid, BdcXmDO->BdcXmDO));
                if(MapUtils.isNotEmpty(xmxxMap)){
                    Iterator<BdcYjSfxxDTO> iterator = bdcYjSfxxDTOList.listIterator();
                    while(iterator.hasNext()){
                        BdcYjSfxxDTO bdcYjSfxxDTO = iterator.next();
                        if(xmxxMap.containsKey(bdcYjSfxxDTO.getXmid())){
                            BdcXmDO bdcXmDO = xmxxMap.get(bdcYjSfxxDTO.getXmid());
                            BeanUtils.copyProperties(bdcXmDO, bdcYjSfxxDTO);
                            bdcYjSfxxDTO.setYt(String.valueOf(bdcXmDO.getDzwyt()));
                        }else{
                            iterator.remove();
                        }
                    }
                }else{
                    bdcYjSfxxDTOList.clear();
                }
            }
        }
    }

    /**
     * 处理不动产项目的查询参数
     */
    private BdcXmQO addBdcXmQueryParam(List<String> xmids, BdcYjSfxxQO bdcYjSfxxQO){
        BdcXmQO bdcXmQO = new BdcXmQO();
        BeanUtils.copyProperties(bdcYjSfxxQO, bdcXmQO);
        bdcXmQO.setXmidList(xmids);
        bdcXmQO.setGzldymc(bdcYjSfxxQO.getLcmc());
        List<Integer> qsztList = new ArrayList<>(2);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        qsztList.add(CommonConstantUtils.QSZT_HISTORY);
        bdcXmQO.setQszts(qsztList);
        return bdcXmQO;
    }

    @Override
    public Map<String, Set<String>> checkSfxxSfxd(List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDOList) {
        if(CollectionUtils.isNotEmpty(bdcYjdhSfxxGxDOList)){
            List<String> sfxxidList = bdcYjdhSfxxGxDOList.stream().map(BdcYjdhSfxxGxDO::getSfxxid).collect(Collectors.toList());
            List<BdcYjdhSfxxGxDO> yjdhSfxxGxDOList = new ArrayList<>(bdcYjdhSfxxGxDOList.size());
            if(sfxxidList.size()>500){
                List<List> subList = ListUtils.subList(sfxxidList, 500);
                for (List data : subList) {
                    List copyList = new ArrayList(data);
                    yjdhSfxxGxDOList.addAll(this.queryBdcYjdhSfxxBySfxxids(copyList));
                }
            }else{
                yjdhSfxxGxDOList = this.queryBdcYjdhSfxxBySfxxids(sfxxidList);
            }
            if(CollectionUtils.isNotEmpty(yjdhSfxxGxDOList)){
                Map<String, Set<String>> resultMap = new HashMap(8);
                for(BdcYjdhSfxxGxDO bdcYjdhSfxxGxDO:yjdhSfxxGxDOList){
                    String slbh = bdcYjdhSfxxGxDO.getSlbh();
                    if(StringUtils.isNotBlank(slbh)){
                        String yjdh = bdcYjdhSfxxGxDO.getYjdh();
                        if(resultMap.containsKey(yjdh)){
                            Set<String> value = resultMap.get(yjdh);
                            value.add(slbh);
                            resultMap.put(yjdh,value);
                        }else{
                            Set<String> value = new HashSet<>();
                            value.add(slbh);
                            resultMap.put(yjdh, value);
                        }
                    }
                }
                return resultMap;
            }
        }
        return null;
    }

    private List<BdcYjdhSfxxGxDO> queryBdcYjdhSfxxBySfxxids(List<String> sfxxidList){
        return this.bdcYjdhSfxxGxMapper.queryYjSfxxGxBySfxxidList(sfxxidList);
    }

    @Override
    public List<String> queryYjdhxxBySfxxid(String sfxxid) {
        if(StringUtils.isBlank(sfxxid)){
            throw new MissingArgumentException("缺失参数收费信息ID。");
        }
        return this.bdcYjdhSfxxGxMapper.queryYjdhxxBySfxxid(sfxxid);
    }

    @Override
    public String queryYjfYjdh(String sfxxid) {
        if (StringUtils.isBlank(sfxxid)) {
            throw new MissingArgumentException("缺失参数收费信息ID。");
        }
        return this.bdcYjdhSfxxGxMapper.queryYjfYjdh(sfxxid);
    }

    @Override
    public BdcYjSfxxDTO queryYjdh(BdcYjSfxxQO bdcYjSfxxQO) {
        if(Objects.isNull(bdcYjSfxxQO) || CheckParameter.checkPartElementsNotAllExist(bdcYjSfxxQO, Arrays.asList(new Object[]{"xmidList"}))){
            throw new AppException(ErrorCode.MISSING_ARG, "请求参数为空");
        }

        // 查询参数中存在 受理编号时，先根据受理编号获取xmidList，在根据其他条件过滤
        if(StringUtils.isNotBlank(bdcYjSfxxQO.getSlbh())){
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxBySlbh(bdcYjSfxxQO.getSlbh());
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                bdcYjSfxxQO.setXmidList(bdcXmDTOList.stream().map(BdcXmDTO::getXmid).collect(Collectors.toList()));
            }
        }

        // 查询月结收费信息
        List<BdcYjSfxxDTO> yjSfxxDTOList = this.listBdcYjSfxx(bdcYjSfxxQO);

        if(CollectionUtils.isEmpty(yjSfxxDTOList)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到月结收费信息，无法生成月结单号。");
        }

        // 验证是否已生成月结单号
        List<BdcYjSfxxDTO> hasYjdhList = yjSfxxDTOList.stream().filter(t-> StringUtils.isNotBlank(t.getYjdh())).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(hasYjdhList)){
            String slbhList = hasYjdhList.stream().map(BdcYjSfxxDTO::getSlbh).distinct().collect(Collectors.joining(","));
            throw new AppException(ErrorCode.CUSTOM, "已生成月结单号：" + hasYjdhList.get(0).getYjdh() + "，不允许再次生成。受理编号为：" + slbhList);
        }

        // 生成月结单号
        List<BdcYjdhSfxxGxDO> yjsfxxGxList = new ArrayList<>(CollectionUtils.size(yjSfxxDTOList));
        for (BdcYjSfxxDTO bdcYjSfxxDTO : yjSfxxDTOList) {
            BdcYjdhSfxxGxDO bdcYjdhSfxxGxDO = new BdcYjdhSfxxGxDO();
            BeanUtils.copyProperties(bdcYjSfxxDTO, bdcYjdhSfxxGxDO);
            yjsfxxGxList.add(bdcYjdhSfxxGxDO);
        }
        yjsfxxGxList = generateYjSfxxGx(yjsfxxGxList);

        // 组装返回值
        BdcYjSfxxDTO bdcYjSfxxDTO = new BdcYjSfxxDTO();
        bdcYjSfxxDTO.setYjdh(yjsfxxGxList.get(0).getYjdh());
        bdcYjSfxxDTO.setYhmc(yjSfxxDTOList.get(0).getYhmc());
        // 添加银行证件号信息
        String qlrzjh = this.getQlrZjh(yjSfxxDTOList.get(0).getXmid(), yjSfxxDTOList.get(0).getYhmc());
        if(StringUtils.isNotBlank(qlrzjh)){
            bdcYjSfxxDTO.setQlrzjh(qlrzjh);
        }
        Double zje =  NumberUtil.formatDigit(yjSfxxDTOList.stream().filter(t-> Objects.nonNull(t.getSfxmhj())).mapToDouble(BdcYjSfxxDTO::getSfxmhj).sum(), 4);
        bdcYjSfxxDTO.setSfxmhj(zje);
        int sfxmsl = yjSfxxDTOList.stream().filter(t-> Objects.nonNull(t.getSfxmzsl())).mapToInt(BdcYjSfxxDTO::getSfxmzsl).sum();
        bdcYjSfxxDTO.setSfxmzsl(sfxmsl);
        return bdcYjSfxxDTO;
    }

    /**
     * 获取权利人证件号信息
     */
    private String getQlrZjh(String xmid, String yhmc){
        if(StringUtils.isNotBlank(xmid)){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrQO.setQlrmc(yhmc);
            List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
                return bdcQlrDOList.get(0).getZjh();
            }
        }
        return null;
    }
}
