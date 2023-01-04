package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.etl.HtbaMsrVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.core.mapper.HtbaQlrMapper;
import cn.gtmap.realestate.etl.service.HtbaQlrService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: realestate
 * @description: 合同备案权利人服务实现方法
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-15 19:16
 **/
@Service
public class HtbaQlrServiceImpl implements HtbaQlrService {

    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    HtbaQlrMapper htbaQlrMapper;
    /**
     * @param json 合同备案权利人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新合同备案权利人信息
     * @date : 2020/12/15 17:25
     */
    @Override
    public int saveOrUpdatHtbaQlr(String json) {
        Integer count = 0;
        JSONArray jsonArray = JSONObject.parseArray(json);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                HtbaQlrDO htbaQlrDO = JSONObject.parseObject(JSONObject.toJSONString(jsonArray.get(i)),HtbaQlrDO.class);
                if(StringUtils.isBlank(htbaQlrDO.getQlrid())) {
                    htbaQlrDO.setQlrid(UUIDGenerator.generate16());
                    count += entityMapper.insertSelective(htbaQlrDO);
                } else {
                    count += bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(obj), HtbaQlrDO.class.getName());
                }
            }
        }
        return count;
    }

    /**
     * @param qlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人id删除备案权利人
     * @date : 2020/12/15 19:19
     */
    @Override
    public int deleteHtbaQlr(String qlrid) {
        return entityMapper.deleteByPrimaryKey(HtbaQlrDO.class,qlrid);
    }

    /**
     * @param bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋户室xindex 查询买受人
     * @date : 2020/12/21 17:51
     */
    @Override
    public List<HtbaQlrDO> listHtbaQlr(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            return new ArrayList<>();
        }
        Map map = new HashMap();
        map.put("bdcdyh", bdcdyh);
        return htbaQlrMapper.listHtbaQlrByParamMap(map);
    }

    /**
     * @param bdcdyh
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据bdcdyh 查询买受人
     * @date : 2021/01/18
     */
    @Override
    public FcjyBaxxDTO spfzyMsr(String bdcdyh,String qlrlb) {
        if(StringUtils.isBlank(bdcdyh) && StringUtils.isBlank(qlrlb)){
            return null;
        }
        FcjyBaxxDTO fcjyBaxxDTO = new FcjyBaxxDTO();
        Map map = new HashMap();
        if(StringUtils.isNotBlank(bdcdyh)){
            map.put("bdcdyh",bdcdyh);
        }
        if(StringUtils.isNotBlank(qlrlb)) {
            map.put("qlrlb", qlrlb);
        }
        List<HtbaQlrDO> listMsr = htbaQlrMapper.listHtbaQlrByParamMap(map);
        if(CollectionUtils.isNotEmpty(listMsr)){
            List<BdcSlSqrDO> listSqr = new ArrayList<>();
            for(HtbaQlrDO htbaQlrDO : listMsr){
                BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                bdcSlSqrDO.setDh(htbaQlrDO.getLxdh());
                bdcSlSqrDO.setSqrmc(htbaQlrDO.getQlrmc());
                bdcSlSqrDO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                bdcSlSqrDO.setZjh(htbaQlrDO.getZjh());
                bdcSlSqrDO.setSqrid(null);
                bdcSlSqrDO.setZjzl(CommonConstantUtils.ZJZL_QT);
                listSqr.add(bdcSlSqrDO);
            }
            fcjyBaxxDTO.setBdcSlSqr(listSqr);
        }
        return fcjyBaxxDTO;
    }

    /**
     * @param bdcdyhList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量查询买受人信息
     * @date : 2021/3/3 17:11
     */
    @Override
    public List<HtbaMsrVO> listMsr(List<String> bdcdyhList,String bazt) {
        Map map = new HashMap(bdcdyhList.size());
        List<HtbaMsrVO> resultList = new ArrayList<>(bdcdyhList.size());
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            List<List> partList = ListUtils.subList(bdcdyhList, 100);
            if (CollectionUtils.isNotEmpty(partList)) {
                for (List dyhlist : partList) {
                    map.put("bdcdyhs", dyhlist);
                    //查询权利人
                    map.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
                    map.put("bazt", bazt);
                    List<HtbaMsrVO> htbaMsrVOList = htbaQlrMapper.listMsr(map);
                    resultList.addAll(htbaMsrVOList);
                }
            }
            return resultList;
        }
        return Collections.emptyList();
    }

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据备案id 查询备案权利人
     * @date : 2021/4/14 14:01
     */
    @Override
    public List<HtbaQlrDO> listHtbaQlrByBaid(String baid) {
        if (StringUtils.isBlank(baid)) {
            return new ArrayList<>(1);
        }
        Example example = new Example(HtbaQlrDO.class);
        example.createCriteria().andEqualTo("baid", baid);
        return entityMapper.selectByExample(example);
    }

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据备案id删除权利人
     * @date : 2021/4/14 14:02
     */
    @Override
    public void deleteHtbaQlrByBaid(String baid) {
        if (StringUtils.isNotBlank(baid)) {
            Example example = new Example(HtbaQlrDO.class);
            example.createCriteria().andEqualTo("baid", baid);
            entityMapper.deleteByExample(example);
        }
    }
}
