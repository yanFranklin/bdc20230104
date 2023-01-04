package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlXmLsgxMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlXmLsgxService;
import cn.gtmap.realestate.accept.service.BdcCommonSlService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理项目历史关系数据服务
 */
@Service
public class BdcSlXmLsgxServiceImpl implements BdcSlXmLsgxService,BdcCommonSlService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcSlXmLsgxMapper bdcSlXmLsgxMapper;

    /**
     * @param gxid 关系ID
     * @return 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据关系ID获取不动产受理项目历史关系
     */
    @Override
    public BdcSlXmLsgxDO queryBdcSlXmLsgxByGxid(String gxid) {
        if(StringUtils.isBlank(gxid)){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlXmLsgxDO.class, gxid);
    }

    /**
     * @param xmid 项目ID
     * @return 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID获取不动产受理项目历史关系
     */
    @Override
    public List<BdcSlXmLsgxDO> listBdcSlXmLsgxByXmid(String xmid) {
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlXmLsgxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            bdcSlXmLsgxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlXmLsgxDOList)) {
            bdcSlXmLsgxDOList = new ArrayList<>();
        }
        return bdcSlXmLsgxDOList;
    }


    @Override
    public List<BdcSlXmLsgxDO> listBdcSlXmLsgx(String xmid,String yxmid,Integer sfwlzs){
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid) ||StringUtils.isNotBlank(yxmid)) {
            Example example = new Example(BdcSlXmLsgxDO.class);
            Example.Criteria criteria= example.createCriteria();
            if(StringUtils.isNotBlank(xmid)) {
                criteria.andEqualTo("xmid", xmid);
            }
            if(StringUtils.isNotBlank(yxmid)) {
                criteria.andEqualTo("yxmid", yxmid);
            }
            if(sfwlzs != null){
                criteria.andEqualNvlTo("sfwlzs", sfwlzs,CommonConstantUtils.SF_F_DM);
            }
            bdcSlXmLsgxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlXmLsgxDOList)) {
            bdcSlXmLsgxDOList = Collections.emptyList();
        }
        return bdcSlXmLsgxDOList;

    }

    /**
     * @param bdcSlXmLsgxDO 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理项目历史关系
     */
    @Override
    public BdcSlXmLsgxDO insertBdcSlXmLsgx(BdcSlXmLsgxDO bdcSlXmLsgxDO) {
        if (bdcSlXmLsgxDO != null) {
            if (StringUtils.isBlank(bdcSlXmLsgxDO.getGxid())) {
                bdcSlXmLsgxDO.setGxid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlXmLsgxDO);
        }
        return bdcSlXmLsgxDO;
    }

    /**
     * @param bdcSlXmLsgxDO 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理项目历史关系
     */
    @Override
    public Integer updateBdcSlXmLsgx(BdcSlXmLsgxDO bdcSlXmLsgxDO) {
        int result;
        if (bdcSlXmLsgxDO != null && StringUtils.isNotBlank(bdcSlXmLsgxDO.getGxid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlXmLsgxDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    /**
     * @param gxid 关系ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据关系ID删除不动产受理项目历史关系
     */
    @Override
    public Integer deleteBdcSlXmLsgxByGxid(String gxid) {
        int result = 0;
        if (StringUtils.isNotBlank(gxid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlXmLsgxDO.class, gxid);
        }
        return result;
    }

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理项目历史关系
     */
    @Override
    public Integer deleteBdcSlXmLsgxByXmid(String xmid) {
        int result = 0;
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlXmLsgxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     * @param jbxxid 基本信息ID
     * @param qjidList 权籍ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询参数删除不动产受理项目历史关系
     */
    @Override
    public void deleteBdcSlXmLsgx(String jbxxid,List<String> qjidList){
        if (StringUtils.isBlank(jbxxid)){
            throw new MissingArgumentException("jbxxid");
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("jbxxid",jbxxid);
        paramMap.put("qjidList",qjidList);
        bdcSlXmLsgxMapper.deleteBdcSlXmLsgx(paramMap);
    }


    /**
     * @param gzlslid
     * @return 不动产受理项目上下手关系
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description  根据gzlslid获取不动产受理上下手关系数据
     */
    @Override
    public List<Map> sxxBdcXx(String gzlslid){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("gzlslid",gzlslid);
        return bdcSlXmLsgxMapper.sxxBdcXx(paramMap);
    }

    @Override
    public void deleteBdcSlXmLsgx(BdcSlDeleteCsDTO bdcSlDeleteCsDTO){
        if (!CheckParameter.checkAnyParameter(bdcSlDeleteCsDTO)) {
            throw new AppException("删除参数为空" + JSONObject.toJSONString(bdcSlDeleteCsDTO));
        }
        bdcSlXmLsgxMapper.batchDeleteBdcSlXmLsgx(bdcSlDeleteCsDTO);

    }

    @Override
    public void batchDelete(BdcSlDeleteCsDTO bdcSlDeleteCsDTO){
        deleteBdcSlXmLsgx(bdcSlDeleteCsDTO);
        //xmid作为yxmid
        if(CollectionUtils.isNotEmpty(bdcSlDeleteCsDTO.getXmidList())){
            deleteBdcSlXmlsgxByYxmid(bdcSlDeleteCsDTO.getXmidList());
        }


    }

    @Override
    public List<BdcSlXmLsgxDO> listSlXmLsgxPl(List<String> xmidList,Integer sfwlzs){
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(xmidList)){
            List<List> lists = ListUtils.subList(xmidList, 500);
            for (List partXmids : lists) {
                List<BdcSlXmLsgxDO> xmLsgxDOS=bdcSlXmLsgxMapper.listSlXmLsgxPl(partXmids,sfwlzs);
                if(CollectionUtils.isNotEmpty(xmLsgxDOS)){
                    bdcSlXmLsgxDOList.addAll(xmLsgxDOS);
                }
            }
        }
        return bdcSlXmLsgxDOList;

    }

    @Override
    public List<BdcSlXmLsgxDO> listSlXmLsgxPlByYxmid(List<String> yxmidList){
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(yxmidList)){
            List<List> lists = ListUtils.subList(yxmidList, 500);
            for (List partYxmids : lists) {
                Example example =new Example(BdcSlXmLsgxDO.class);
                example.createCriteria().andInWithListString("yxmid",partYxmids);
                List<BdcSlXmLsgxDO> xmLsgxDOS=entityMapper.selectByExampleNotNull(example);
                if(CollectionUtils.isNotEmpty(xmLsgxDOS)){
                    bdcSlXmLsgxDOList.addAll(xmLsgxDOS);
                }
            }
        }
        return bdcSlXmLsgxDOList;
    }

    /**
     * @param yxmidList 原项目ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据原项目ID删除项目历史关系
     */
    private void deleteBdcSlXmlsgxByYxmid(List<String> yxmidList){
        if(CollectionUtils.isNotEmpty(yxmidList)){
            bdcSlXmLsgxMapper.deleteBdcSlXmlsgxByYxmid(yxmidList);

        }

    }
}
