package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlLzrService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlLzrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcLzrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLzrFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.hutool.json.JSONTokener;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/15.
 * @description 受理领证人服务
 */
@Service
public class BdcSlLzrServiceImpl implements BdcSlLzrService {

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    BdcLzrFeignService bdcLzrFeignService;

    @Override
    public BdcSlLzrDO queryBdcSlLzrByLzrid(String lzrid) {
        if (StringUtils.isBlank(lzrid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlLzrDO.class, lzrid);
    }

    @Override
    public List<BdcSlLzrDO> listBdcSlLzrByXmid(String xmid) {
        List<BdcSlLzrDO> bdcSlLzrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlLzrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            example.setOrderByClause("sxh");
            bdcSlLzrDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlLzrDOList)) {
            bdcSlLzrDOList = Collections.emptyList();
        }
        return bdcSlLzrDOList;
    }

    @Override
    public BdcSlLzrDO insertBdcSlLzr(BdcSlLzrDO bdcSlLzrDO) {
        if (bdcSlLzrDO != null) {
            if (StringUtils.isBlank(bdcSlLzrDO.getLzrid())) {
                bdcSlLzrDO.setLzrid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlLzrDO);
        }
        return bdcSlLzrDO;
    }

    @Override
    public Integer updateBdcSlLzr(BdcSlLzrDO bdcSlLzrDO) {
        int result;
        if (bdcSlLzrDO != null && StringUtils.isNotBlank(bdcSlLzrDO.getLzrid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlLzrDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    @Override
    public Integer deleteBdcSlLzrByLzrid(String lzrid) {
        int result = 0;
        if (StringUtils.isNotBlank(lzrid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlLzrDO.class, lzrid);
        }
        return result;
    }

    @Override
    public Integer deleteBdcSlLzrByXmid(String xmid) {
        int result = 0;
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlLzrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     * 根据项目ID删除不动产受理领证人
     *
     * @param json
     * @param processInsId
     * @param djxl
     * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @param: xmid 项目ID
     * @return: Integer 删除状态
     */
    @Override
    public Integer updatePlBdcLzr(String json, String processInsId, String djxl) throws Exception {

        Integer count = 0;
        JSONArray jsonArray = new JSONArray();
        Object jsonObject = new JSONTokener(json).nextValue();
        if (jsonObject instanceof cn.hutool.json.JSONObject) {
            JSONObject obj = JSONObject.parseObject(json);
            jsonArray.add(obj);
        } else if (jsonObject instanceof cn.hutool.json.JSONArray) {
            jsonArray = JSONObject.parseArray(json);
        }
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                //获取权利人修改前的数据,用于批量更新的where条件
                if (StringUtils.isBlank(obj.getString("lzrid"))) {
                    BdcLzrDO bdcLzr = JSONObject.parseObject(JSONObject.toJSONString(obj), BdcLzrDO.class);
                    bdcLzrFeignService.insertBatchBdcLzr(bdcLzr, processInsId, djxl);
                } else {
                    BdcLzrQO bdcLzrQO = new BdcLzrQO();
                    bdcLzrQO.setLzrid(obj.getString("lzrid"));
                    List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
                    //存在即更新，不存在则新增
                    if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                        //权利人批量更新移除xmid
                        obj.remove("xmid");
                        obj.remove("qlrid");
                        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
                        Map<String, Object> map = new HashMap<>();
                        map.put("gzlslid", processInsId);
                        map.put("lzrmc", bdcLzrDOList.get(0).getLzrmc());
                        map.put("lzrzjh", bdcLzrDOList.get(0).getLzrzjh());
                        map.put("lzrdh", bdcLzrDOList.get(0).getLzrdh());
                        map.put("djxl", djxl);
                        bdcDjxxUpdateQO.setWhereMap(map);
                        count += bdcLzrFeignService.updateBatchBdcLzr(bdcDjxxUpdateQO);
                    }
                }
            }
        }
        return count;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  生成领证人信息
     */
    @Override
    public List<BdcLzrDO> initLzrxx(BdcSlxxDTO bdcSlxxDTO, String bdcgzlslid){
        List<BdcLzrDO> bdcLzrDOList =new ArrayList<>();
        if(StringUtils.isNotBlank(bdcgzlslid) &&bdcSlxxDTO != null &&CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())){
            for(BdcSlXmDTO bdcSlXmDTO:bdcSlxxDTO.getBdcSlXmList()){
                if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlLzrDOList())) {
                    for (BdcSlLzrDO bdcSlLzrDO : bdcSlXmDTO.getBdcSlLzrDOList()) {
                        if ((StringUtils.isNotBlank(bdcSlLzrDO.getLzrmc())) &&
                                StringUtils.isNoneBlank(bdcSlLzrDO.getLzrid(), bdcSlLzrDO.getXmid())) {
                            BdcLzrDO lzrDO = new BdcLzrDO();
                            BeanUtils.copyProperties(bdcSlLzrDO, lzrDO);
                            // 领证人qlrid赋值
                            List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlXmDTO.getBdcSlSqrDOList();
                            if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
                                for (BdcSlSqrDO bdcSlSqrDo:bdcSlSqrDOList) {
                                    if (bdcSlSqrDo.getSflzr()!=null && bdcSlSqrDo.getSflzr() == 1){
                                        lzrDO.setQlrid(bdcSlSqrDo.getSqrid());
                                    }
                                }
                            }
                            bdcLzrDOList.add(lzrDO);
                        }
                    }
                }
            }

        }
        if(CollectionUtils.isNotEmpty(bdcLzrDOList)){
            bdcLzrFeignService.insertBatchBdcLzr(bdcLzrDOList);
        }
        return bdcLzrDOList;


    }


}
