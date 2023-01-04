package cn.gtmap.realestate.etl.core.service.impl;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.core.domian.wwsq.*;
import cn.gtmap.realestate.etl.core.dto.GxWwBlztDTO;
import cn.gtmap.realestate.etl.core.mapper.hlw.HlwYwxxMapper;
import cn.gtmap.realestate.etl.core.service.HlwYwxxDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/12
 * @description 互联网业务信息查询数据层级方法
 */
@Service
public class HlwYwxxDataServiceImpl implements HlwYwxxDataService {

    @Resource(name = "entityMapper")
    private EntityMapper hlwEntityMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private HlwYwxxMapper hlwYwxxMapper;

    @Override
    public GxWwSqxmDo gxWwsqxmByHlwXmid(String hlwxmid) {
        if (StringUtils.isBlank(hlwxmid)) {
            throw new MissingArgumentException("互联网主键项目id不能为空");
        }
        return hlwEntityMapper.selectByPrimaryKey(GxWwSqxmDo.class, hlwxmid);
    }

    @Override
    public List<GxWwSqxxDo> listGxWwSqxxByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("互联网主键项目id不能为空");
        }
        Example exampleSqxx = new Example(GxWwSqxxDo.class);
        exampleSqxx.setOrderByClause("dyfs desc");
        exampleSqxx.createCriteria().andEqualTo("xmid", xmid);
        return hlwEntityMapper.selectByExample(exampleSqxx);
    }

    @Override
    public List<GxWwSqxxQlrDo> listGxWwSqxxQlrBySqxxid(String sqxxid) {
        if (StringUtils.isNotBlank(sqxxid)) {
            Example exampleQlr = new Example(GxWwSqxxQlrDo.class);
            exampleQlr.setOrderByClause("qlrlx asc,sxh asc");
            exampleQlr.createCriteria().andEqualTo("sqxxid", sqxxid);

            return hlwEntityMapper.selectByExample(exampleQlr);
        }
        return new ArrayList<>();
    }

    @Override
    public GxWwSqxxQlrDo queryGxWwSqxxQlrByQlrid(String qlrid){
        if(StringUtils.isNotBlank(qlrid)){
            return hlwEntityMapper.selectByPrimaryKey(GxWwSqxxQlrDo.class,qlrid);
        }
        return null;

    }

    @Override
    public List<GxWwSqxxClxxDo> listGxWwSqxxClxxBySqxxid(String sqxxId) {
        if (StringUtils.isNotBlank(sqxxId)) {
            Example exampleClxx = new Example(GxWwSqxxClxxDo.class);
            exampleClxx.createCriteria().andEqualTo("sqxxid", sqxxId);
            exampleClxx.setOrderByClause("xh");
            List<GxWwSqxxClxxDo> wwSqxxClxxDoList = hlwEntityMapper.selectByExample(exampleClxx);
            if (CollectionUtils.isNotEmpty(wwSqxxClxxDoList)) {
                for (GxWwSqxxClxxDo gxWwSqxxClxxDo : wwSqxxClxxDoList) {
                    gxWwSqxxClxxDo.setFjxx(listGxWwSqxxFjxxByClid(gxWwSqxxClxxDo.getClid()));
                }
            }
            return wwSqxxClxxDoList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<GxWwSqxxFjxxDo> listGxWwSqxxFjxxByClid(String clxxid) {
        if (StringUtils.isNotBlank(clxxid)) {
            Example exampleFjxx = new Example(GxWwSqxxFjxxDo.class);
            exampleFjxx.createCriteria().andEqualTo("clid", clxxid);
            return hlwEntityMapper.selectByExample(exampleFjxx);
        }
        return new ArrayList<>();
    }

    @Override
    public GxWwSqxxFjxxDo queryGxWwSqxxFjxxByFjid(String fjid) {
        if (StringUtils.isNotBlank(fjid)) {
            Example exampleFjxx = new Example(GxWwSqxxFjxxDo.class);
            exampleFjxx.createCriteria().andEqualTo("fjid", fjid);
            List<GxWwSqxxFjxxDo> gxWwSqxxFjxxDoList = hlwEntityMapper.selectByExample(exampleFjxx);
            if(CollectionUtils.isNotEmpty(gxWwSqxxFjxxDoList)){
                return gxWwSqxxFjxxDoList.get(0);
            }
        }
        return null;
    }

    @Override
    public List<GxWwSwxxDo> listGxWwSwxxBySqxxid(String sqxxid) {
        if (StringUtils.isNotBlank(sqxxid)) {
            Example exampleSwxx = new Example(GxWwSwxxDo.class);
            exampleSwxx.createCriteria().andEqualTo("sqid", sqxxid);
            List<GxWwSwxxDo> gxwwSwxxDoList = hlwEntityMapper.selectByExample(exampleSwxx);
            if (CollectionUtils.isNotEmpty(gxwwSwxxDoList)) {
                for (GxWwSwxxDo gxWwSwxxDo : gxwwSwxxDoList) {
                    gxWwSwxxDo.setSwmx(listGxWwSwmxBySwid(gxWwSwxxDo.getSwid()));
                }
            }
            return gxwwSwxxDoList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<GxWwSwmxDo> listGxWwSwmxBySwid(String swid) {
        if (StringUtils.isNotBlank(swid)) {
            Example exampleSwmx = new Example(GxWwSwmxDo.class);
            exampleSwmx.createCriteria().andEqualTo("swid", swid);
            return hlwEntityMapper.selectByExample(exampleSwmx);
        }
        return new ArrayList<>();
    }

    @Override
    public List<GxWwSqxxClxxDo> listGxWwSqxxClxxByXmid(String hlwxmid){
        List<GxWwSqxxClxxDo> gxWwSqxxClxxDoList =new ArrayList<>();
        if(StringUtils.isNotBlank(hlwxmid)){
            List<GxWwSqxxDo> gxWwSqxxDoList =listGxWwSqxxByXmid(hlwxmid);
            if(CollectionUtils.isNotEmpty(gxWwSqxxDoList)){
                for(GxWwSqxxDo gxWwSqxxDo:gxWwSqxxDoList){
                    List<GxWwSqxxClxxDo> clxxDoList=listGxWwSqxxClxxBySqxxid(gxWwSqxxDo.getSqxxid());
                    if(CollectionUtils.isNotEmpty(clxxDoList)){
                        gxWwSqxxClxxDoList.addAll(clxxDoList);
                    }

                }
            }
        }
        return gxWwSqxxClxxDoList;

    }

    @Override
    public List<GxWwShxxDO> listGxWwShxxByWwslbh(String wwslbh){
        if (StringUtils.isNotBlank(wwslbh)) {
            Example example = new Example(GxWwShxxDO.class);
            example.createCriteria().andEqualTo("wwslbh", wwslbh);
            return hlwEntityMapper.selectByExample(example);
        }
        return new ArrayList<>();
    }

    @Override
    public List<GxWwSqxmDo> listWcjYhWwsqXmidList(){
        return hlwYwxxMapper.listWcjYhWwsqXmidList();

    }

    @Override
    public void insertCjjl(String wwxmid){
        if(StringUtils.isNotBlank(wwxmid)){
            BdcWwsqCjjlDO bdcWwsqCjjlDO =new BdcWwsqCjjlDO();
            bdcWwsqCjjlDO.setCjjlid(UUIDGenerator.generate16());
            bdcWwsqCjjlDO.setWwxmid(wwxmid);
            bdcWwsqCjjlDO.setCjsj(new Date());
            hlwEntityMapper.insertSelective(bdcWwsqCjjlDO);
        }

    }



    @Override
    public void updateCjjl(String wwxmid,String wwslbh,String gzlslid,String sbyy){
        if(StringUtils.isNotBlank(wwxmid)){
            BdcWwsqCjjlDO bdcWwsqCjjlDO =queryWwsqCjjlByXmid(wwxmid);
            if(bdcWwsqCjjlDO != null) {
                bdcWwsqCjjlDO.setGxsj(new Date());
                bdcWwsqCjjlDO.setGzlslid(gzlslid);
                bdcWwsqCjjlDO.setSbyy(sbyy);
                bdcWwsqCjjlDO.setWwslbh(wwslbh);
                hlwEntityMapper.updateByPrimaryKeyNull(bdcWwsqCjjlDO);
            }
        }

    }

    @Override
    public void deleteSbCjjl(String wwxmid){
        if(StringUtils.isNotBlank(wwxmid)){
            Example example =new Example(BdcWwsqCjjlDO.class);
            example.createCriteria().andEqualTo("wwxmid",wwxmid).andIsNull("gzlslid");
            hlwEntityMapper.deleteByExampleNotNull(example);
        }
    }

    @Override
    public List<GxWwBlztDTO> listGxWwBlztDTOByBdcdyh(String bdcdyh){
        if(StringUtils.isNotBlank(bdcdyh)) {
            return hlwYwxxMapper.listGxWwBlztDTOByBdcdyh(bdcdyh);
        }else{
            return null;
        }

    }

    /**
     * @param sqslbh@return
     * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
     * @description 外网受理编号查询
     */
    @Override
    public GxWwSqxmDo getGxWwSqxmBySqslbh(String sqslbh) {
        Example example = new Example(GxWwSqxmDo.class);
        example.createCriteria().andEqualTo("sqslbh", sqslbh);
        List<GxWwSqxmDo> gxWwSqxmDoList = entityMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(gxWwSqxmDoList) ? gxWwSqxmDoList.get(0) : null;
    }

    @Override
    public List<GxWwSqxxWlxxDO> listGxWwSqxxWlxxBySqxxid(String sqxxId){
        if (StringUtils.isNotBlank(sqxxId)) {
            Example example = new Example(GxWwSqxxWlxxDO.class);
            example.createCriteria().andEqualTo("sqxxid", sqxxId);
            List<GxWwSqxxWlxxDO> gxWwSqxxWlxxDOList = hlwEntityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(gxWwSqxxWlxxDOList)) {
                return gxWwSqxxWlxxDOList;
            }
        }
        return new ArrayList<>();

    }

    @Override
    public List<GxWwSqxxSfxxDO> listGxWwSqxxSfxxBySqxxid(String sqxxId){
        if (StringUtils.isNotBlank(sqxxId)) {
            Example example = new Example(GxWwSqxxSfxxDO.class);
            example.createCriteria().andEqualTo("sqxxid", sqxxId);
            List<GxWwSqxxSfxxDO> gxWwSqxxSfxxDOList = hlwEntityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(gxWwSqxxSfxxDOList)) {
                return gxWwSqxxSfxxDOList;
            }
        }
        return new ArrayList<>();
    }


    private BdcWwsqCjjlDO queryWwsqCjjlByXmid(String wwxmid){
        if(StringUtils.isNotBlank(wwxmid)){
            Example example = new Example(BdcWwsqCjjlDO.class);
            example.createCriteria().andEqualTo("wwxmid", wwxmid);
            List<BdcWwsqCjjlDO> bdcWwsqCjjlDOList = hlwEntityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcWwsqCjjlDOList)){
                if(bdcWwsqCjjlDOList.size() >1) {
                    throw new AppException("同一xmid创建记录存在多条,请检查");
                }
                return bdcWwsqCjjlDOList.get(0);
            }

        }
        return null;

    }

    /**
     * 查询时间范围内创建失败的xmid
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<String> listFailedWwsqXmidList(Date startTime,Date endTime){
        return hlwYwxxMapper.listFailedWwsqXmidList(startTime,endTime);
    }

    /**
     * 查询时间范围内创建成功的xmid
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<String> listSuccessWwsqXmidList(Date startTime,Date endTime){
        return hlwYwxxMapper.listSuccessWwsqXmidList(startTime,endTime);
    }

    /**
     * @param xmids
     * @return
     */
    @Override
    public List<GxWwSqxmDo> listWwsqListByXmids(List<String> xmids) {
        return hlwYwxxMapper.listWwsqListByXmids(xmids);
    }

}
