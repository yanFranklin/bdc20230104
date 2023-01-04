package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.CbzdMapper;
import cn.gtmap.realestate.building.core.service.CbzdService;
import cn.gtmap.realestate.building.core.service.NydQlrService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
@Service
public class CbzdServiceImpl implements CbzdService {


    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcdyService bdcdyService;
    @Autowired
    private NydQlrService nydQlrService;
    @Autowired
    private CbzdMapper cbzdMapper;

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.CbzdFbfDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询发包方信息
     */
    @Override
    public CbzdFbfDO getFbfByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            CbzdDcbDO cbzdDcbDO = queryCdzdDcbByBdcdyh(bdcdyh);
            if (cbzdDcbDO != null && StringUtils.isNotBlank(cbzdDcbDO.getFbfbm())) {
                return getFbfByFbfIndex(cbzdDcbDO.getFbfbm());
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param paramMap
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.CbzdResponseDTO>
     * @description 分页查询承包宗地不动产单元
     */
    @Override
    public Page<Map> listCbzdBdcdy(Pageable pageable, Map<String, String> paramMap) {
        Page<Map> result = repo.selectPaging("listCbzdByPageOrder", paramMap, pageable);
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map map : result.getContent()) {
                setQlr(map);
                BuildingUtils.convertKeyToLowerCase(map);
            }
        }
        return result;
    }

    /**
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydZdmjDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据外键 查询 分类面积
     */
    @Override
    public List<NydZdmjDO> listNydZdmjByDjdcbIndex(String djdcbIndex) {
        if(StringUtils.isNotBlank(djdcbIndex)){
            Example example = new Example(NydZdmjDO.class);
            example.createCriteria().andEqualTo("djdcbIndex",djdcbIndex);
            List<NydZdmjDO> result = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(result)){
                return result;
            }
        }
        return new ArrayList<>(0);
    }
    @Override
    public List<NydJtcyDO> listCbfJtcy(String bdcdyh){
        if(StringUtils.isNotBlank(bdcdyh)){
            return cbzdMapper.listCbfJtcy(bdcdyh);
        }
        return new ArrayList<>();
    }

    @Override
    public CbzdFbfDO getFbfByFbfIndex(String fbfbm) {
        return entityMapper.selectByPrimaryKey(CbzdFbfDO.class,fbfbm);
    }

    @Override
    public HCbzdFbfDO getHFbfByFbfIndex(String fbfbm) {
        return entityMapper.selectByPrimaryKey(HCbzdFbfDO.class,fbfbm);
    }

    @Override
    public List<HCbzdCbfDO> listHCbfByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Map<String,Object> param = new HashMap<>();
            param.put("bdcdyh",bdcdyh);
            return cbzdMapper.listHCbf(param);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param dataMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @description 保存承包宗地权利人，发包方，承包方相关权利人
     */
    private void setQlr(Map dataMap) {
        List<String> cbzdQlrList = new ArrayList<>();
        List<String> cbzdCbfList = new ArrayList<>();
        List<String> cbzdFbfList = new ArrayList<>();
        String djh = MapUtils.getString(dataMap,"DJH");
        String bdcdyh = MapUtils.getString(dataMap,"BDCDYH");
        if (StringUtils.isNotBlank(djh) && StringUtils.isNotBlank(bdcdyh)) {

            // 农用地权利人
            List<NydQlrDO> qlrList = nydQlrService.listNydQlrByDjh(djh);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (NydQlrDO qlr : qlrList) {
                    cbzdQlrList.add(qlr.getQlrmc());
                }
            }
            // 承包方
            List<CbzdCbfDO> cbfDOList = listCbfByBdcdyh(bdcdyh);
            if (CollectionUtils.isNotEmpty(cbfDOList)) {
                for (CbzdCbfDO cbf : cbfDOList) {
                    cbzdCbfList.add(cbf.getCbfmc());
                }
            }
            // 发包方
            List<CbzdFbfDO> fbfDOList = new ArrayList<>();
            fbfDOList.add(getFbfByBdcdyh(bdcdyh));
            if (CollectionUtils.isNotEmpty(fbfDOList)) {
                for (CbzdFbfDO fbf : fbfDOList) {
                    cbzdFbfList.add(fbf.getFbfmc());
                }
            }
            dataMap.put("QLR",BuildingUtils.wmQlrMcWithList(cbzdQlrList));
            dataMap.put("CBFMC",BuildingUtils.wmQlrMcWithList(cbzdCbfList));
            dataMap.put("FBFMC",BuildingUtils.wmQlrMcWithList(cbzdFbfList));
        }
    }

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.CbzdCbfDO>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH查询承包宗地承包方信息
     */
    @Override
    public List<CbzdCbfDO> listCbfByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Map<String,Object> param = new HashMap();
            param.put("bdcdyh",bdcdyh);
            return cbzdMapper.listCbf(param);
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<CbzdCbfDO> listCbfByCbzdDcbcbfrelIndex(String cbzdDcbcbfrelIndex){
        if (StringUtils.isNotBlank(cbzdDcbcbfrelIndex)) {
            Map<String,Object> param = new HashMap<>();
            param.put("cbzdDcbcbfrelIndex",cbzdDcbcbfrelIndex);
            return cbzdMapper.listCbf(param);
        }
        return new ArrayList<>(0);

    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询承包宗地
     */
    @Override
    public CbzdDcbDO queryCdzdDcbByBdcdyh(String bdcdyh) {
        return bdcdyService.queryDjxxByBdcdyh(bdcdyh, CbzdDcbDO.class);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.CbzdDcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询承包宗地
     */
    @Override
    public CbzdDcbDO queryCbzdDcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjh(djh,CbzdDcbDO.class);
    }
    /**
     * @param jtIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydJtcyDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据家庭成员外键 查询家庭成员
     */
    @Override
    public List<NydJtcyDO> listJtcy(String jtIndex) {
        if(StringUtils.isNotBlank(jtIndex)){
            Example example = new Example(NydJtcyDO.class);
            example.createCriteria().andEqualTo("jtIndex",jtIndex);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }
    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.CbzdDcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询承包宗地
     */
    @Override
    public HCbzdDcbDO queryHCbzdDcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjhWithOrder(djh,HCbzdDcbDO.class,"gxrq desc");
    }

    @Override
    public List<CbzdDcbcbfRelDO> listCbfCbzdDcbcbfRelList(String cbzdDcbcbfrelIndex) {
        if(StringUtils.isNotBlank(cbzdDcbcbfrelIndex)){
           //根据主键查询关系表
            CbzdDcbcbfRelDO cbzdDcbcbfRelDO= entityMapper.selectByPrimaryKey(CbzdDcbcbfRelDO.class,cbzdDcbcbfrelIndex);
            if(cbzdDcbcbfRelDO != null){
                //获取承包方对应的所有关系表
                CbzdDcbcbfRelDO cbzdDcbcbfRel =new CbzdDcbcbfRelDO();
                cbzdDcbcbfRel.setCbzdCbfIndex(cbzdDcbcbfRelDO.getCbzdCbfIndex());
                return entityMapper.selectByObj(cbzdDcbcbfRel);
            }
        }
        return new ArrayList<>(0);
    }

    @Override
    public CbzdDcbcbfRelDO queryCbfCbzdDcbcbfRel(String cbzdDcbcbfrelIndex){
        if(StringUtils.isNotBlank(cbzdDcbcbfrelIndex)){
            return entityMapper.selectByPrimaryKey(CbzdDcbcbfRelDO.class,cbzdDcbcbfrelIndex);
        }
        return null;
    }
}