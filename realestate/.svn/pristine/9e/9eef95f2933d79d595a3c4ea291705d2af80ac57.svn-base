package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.mapper.DjxxMapper;
import cn.gtmap.realestate.building.core.mapper.FwHsMapper;
import cn.gtmap.realestate.building.core.mapper.FwYchsMapper;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwBdcdyDTO;
import cn.gtmap.realestate.common.core.dto.building.YchsAndQlrResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.building.FwJsydzrzxxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityHelper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-07
 * @description 不动产单元相关服务
 */
@Service
public class BdcdyServiceImpl implements BdcdyService {

    private Logger LOGGER = LoggerFactory.getLogger(BdcdyServiceImpl.class);
    @Autowired
    private FwXmxxService fwXmxxService;

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwYcHsService fwYcHsService;

    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private DjxxMapper djxxMapper;

    @Autowired
    private Repo repo;

    @Autowired
    private FwZhsService fwZhsService;

    @Autowired
    private EntityZdConvertUtils entityZdConvertUtils;

    @Autowired
    private FwJsydzrzxxService fwJsydzrzxxService;

    @Autowired
    private FwJtcyService fwJtcyService;

    @Autowired
    private FwHsMapper fwHsMapper;

    @Autowired
    FwYchsMapper fwYchsMapper;

    @Autowired
    ZdDjxxServiceImpl zdDjxxService;
    /**
     * 蚌埠特殊处理：权籍库中bdcdyh既存在fw_hs表又存在fw_lzj表时候，如果是独幢类型，优先当作独幢处理
     */
    @Value("${bengbu.hs_ljz_dz:false}")
    private boolean hsLjzDz;

    /**
     * 特殊处理，预测户室读取FW_HS表
     */
    @Value("${ychs.dqfwhs:false}")
    private boolean dqfwhs;


    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询实测户室不动产单元信息
     */
    @Override
    public Page<Map> listScFwHsBdcdy(Pageable pageable
            , Map<String, Object> paramMap) {
        return listFwHsBdcdy(pageable, paramMap, false);
    }

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询实测户室不动产单元信息
     */
    @Override
    public Page<Map> listHsForWwsq(Pageable pageable, Map<String, Object> paramMap) {
        return repo.selectPaging("queryHsForWwsqByPageOrder", paramMap, pageable);
    }

    /**
     * @param pageable
     * @param paramMap
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询户室信息，实测预测
     * @date : 2020/12/16 18:20
     */
    @Override
    public Page<Map> listScYcHsByPage(Pageable pageable, Map<String, Object> paramMap) {
        return repo.selectPaging("listScYcHsByPageOrder", paramMap, pageable);
    }


    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询预测户室不动产单元信息
     */
    @Override
    public Page<Map> listYcFwHsBdcdy(Pageable pageable, Map<String, Object> paramMap) {
        return listFwHsBdcdy(pageable, paramMap, true);
    }


    /**
     * @param pageable
     * @param paramMap
     * @param isyc     是否查预测户室
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过不动产单元号查询户室列表
     */
    private Page<Map> listFwHsBdcdy(Pageable pageable
            , Map<String, Object> paramMap, boolean isyc) {
        // 区分 实测 预测 户室
        String statement = "queryScFwHsBdcdyByPageOrder";
        if (isyc) {
            statement = "queryYcFwHsBdcdyByPageOrder";
        }
        Page<Map> result = repo.selectPaging(statement, paramMap, pageable);
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map data : result.getContent()) {
                BuildingUtils.convertKeyToLowerCase(data);
                data.put("qlr",fwFcqlrService.wmConcatQlrByFwIndex(MapUtils.getString(data,"fw_hs_index")));
            }
        }
        return result;
    }


    /**
     * @param bdcdyh
     * @param bdcdyfwlx
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询不动单元基本信息
     */
    @Override
    public BdcdyResponseDTO queryBdcdy(String bdcdyh, String bdcdyfwlx) {
        // 判断 不动产单元类型
        BdcdyResponseDTO responseDTO = new BdcdyResponseDTO();
        if (StringUtils.isNotBlank(bdcdyfwlx)) {
            // 多幢
            if (StringUtils.equals(Constants.BDCDYFWLX_XMNDZ, bdcdyfwlx)) {
                initXmxxBdcdy(responseDTO, bdcdyh);
            }

            // 独幢
            if (StringUtils.equals(Constants.BDCDYFWLX_DZ, bdcdyfwlx)) {
                initDzBdcdy(responseDTO, bdcdyh);
            }

            // 户室
            if (StringUtils.equals(Constants.BDCDYFWLX_H, bdcdyfwlx)) {
                initHsBdcdy(responseDTO, bdcdyh);
            }
        } else {
            // 为空时  先查户室
            initHsBdcdy(responseDTO, bdcdyh);
            // 再查多幢
            if (StringUtils.isBlank(responseDTO.getDjid())) {
                initXmxxBdcdy(responseDTO, bdcdyh);
            }
            // 再查独幢
            if (StringUtils.isBlank(responseDTO.getDjid())) {
                initDzBdcdy(responseDTO, bdcdyh);
            }

            // bdcdyh既存在fw_hs表又存在fw_ljz表时候
            boolean isHs = StringUtils.equals(responseDTO.getBdcdyfwlx(), String.valueOf(CommonConstantUtils.BDCDYFWLX_HS));
            if(hsLjzDz && isHs) {
                // 用不动产单元号去逻辑幢再查询一次
                BdcdyResponseDTO duZhDTO = new BdcdyResponseDTO();
                initDzBdcdy(duZhDTO, bdcdyh);

                if (null != duZhDTO && !StringUtils.isBlank(duZhDTO.getDjid())) {
                    boolean isDuZh = StringUtils.equals(duZhDTO.getBdcdyfwlx(), String.valueOf(CommonConstantUtils.BDCDYFWLX_DUZH));
                    if (isDuZh) {
                        //独幢如果既有户室又有逻辑幢,fwbm取户室中fwbm
                        duZhDTO.setFwbm(responseDTO.getFwbm());
                        // 如果是独幢类型，优先当作独幢处理
                        responseDTO = duZhDTO;
                        LOGGER.info("独幢不动产单元{}在户室及逻辑幢都存在记录，当作独幢处理，不动产单元房屋类型：{}", bdcdyh, duZhDTO.getBdcdyfwlx());
                    }
                }
            }
        }

        // 初始化权利人信息
        initQlr(responseDTO);
        // 初始化权利人房屋家庭成员信息
        initFwJtcy(responseDTO);
        //设置不动产类型
        responseDTO.setBdclx(BdcdyhToolUtils.queryBdclxByBdcdyh(bdcdyh, ""));
        if (StringUtils.isNotBlank(responseDTO.getBdcdyh())) {
            return responseDTO;
        }
        return null;
    }

    /**
     * @param fwbm
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWBM查询BDCDY
     */
    @Override
    public BdcdyResponseDTO queryBdcdyByFwbm(String fwbm) {
        // 判断 不动产单元类型
        BdcdyResponseDTO responseDTO = new BdcdyResponseDTO();
        // 先查户室
        initHsBdcdyByFwbm(responseDTO, fwbm);
        // 再查多幢
        if (StringUtils.isBlank(responseDTO.getDjid())) {
            initXmxxBdcdyByXmbm(responseDTO, fwbm);
        }
        // 再查独幢
        if (StringUtils.isBlank(responseDTO.getDjid())) {
            initLjzBdcdyByLjzh(responseDTO, fwbm);
        }
        // 初始化权利人信息
        initQlr(responseDTO);
        if(StringUtils.isNotBlank(responseDTO.getBdcdyh())){
            return responseDTO;
        }
        return null;
    }

    /**
     * @param ysfwbm
     * @param hslx
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据预售房屋编码  查询户室BDCDY
     */
    @Override
    public BdcdyResponseDTO queryHsBdcdyByYsFwbm(String ysfwbm, String hslx, String zl) {
        BdcdyResponseDTO responseDTO = new BdcdyResponseDTO();
        initHsBdcdyByYsfwbm(responseDTO, ysfwbm, hslx, zl);
        if (StringUtils.isNotBlank(responseDTO.getBdcdyh())) {
            return responseDTO;
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyResponseDTO
     * @param fwHsDO
     * @return void
     * @description 像DTO中填充 HS类型 数据
     */
    private void setHsInfoToDTO(BdcdyResponseDTO bdcdyResponseDTO,FwHsDO fwHsDO){
        // 对象复制
        BeanUtils.copyProperties(fwHsDO, bdcdyResponseDTO);
        bdcdyResponseDTO.setDjid(fwHsDO.getFwHsIndex());
        bdcdyResponseDTO.setBdcdyfwlx(Constants.BDCDYFWLX_H);
        bdcdyResponseDTO.setPzjzmj(fwHsDO.getPzjzmj());
        // 查询子户室
        List<FwZhsDO> fwZhsList = fwZhsService.listFwZhsByFwHsIndex(fwHsDO.getFwHsIndex());
        if (CollectionUtils.isNotEmpty(fwZhsList)) {
            bdcdyResponseDTO.setFwZhsList(fwZhsList);
        }
        // 查询  户室外层 逻辑幢 获取 自然幢和隶属宗地信息
        if (StringUtils.isNotBlank(fwHsDO.getFwDcbIndex())) {
            FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwHsDO.getFwDcbIndex());
            if (fwLjzDO != null) {
                bdcdyResponseDTO.setLszd(fwLjzDO.getLszd());
                bdcdyResponseDTO.setZrzh(fwLjzDO.getZrzh());
                bdcdyResponseDTO.setFwcs(fwLjzDO.getFwcs());
                bdcdyResponseDTO.setJgrq(fwLjzDO.getJgrq());
                bdcdyResponseDTO.setZzdmj(fwLjzDO.getZzdmj());
                bdcdyResponseDTO.setDh(fwLjzDO.getDh());
                bdcdyResponseDTO.setFwjg(fwLjzDO.getFwjg());
                bdcdyResponseDTO.setDscs(fwLjzDO.getDscs());
                bdcdyResponseDTO.setYsxkzh(fwLjzDO.getYsxkzh());
                bdcdyResponseDTO.setGhxkzh(fwLjzDO.getGhxkzh());
                bdcdyResponseDTO.setGhyszmbh(fwLjzDO.getGhyszmbh());
                bdcdyResponseDTO.setGhysrq(fwLjzDO.getGhysrq());
                bdcdyResponseDTO.setJgysbabh(fwLjzDO.getJgysbabh());
                bdcdyResponseDTO.setJgysbasj(fwLjzDO.getJgysbasj());
                bdcdyResponseDTO.setXmmc(fwLjzDO.getXmmc());
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyResponseDTO
     * @param fwbm
     * @return void
     * @description 根据FWBM 查询 FW_HS  FW_YCHS 数据
     */
    private void initHsBdcdyByFwbm(BdcdyResponseDTO bdcdyResponseDTO,String fwbm){
        // 户室
        List<FwHsDO> fwHsDOList = fwHsService.listKyFwhsByFwbm(fwbm);
        if(CollectionUtils.isNotEmpty(fwHsDOList) && fwHsDOList.size() > 1){
            throw new AppException("根据房屋编码查询到多条户室数据");
        }
        FwHsDO fwHsDO = null;
        if(CollectionUtils.isEmpty(fwHsDOList)){
            List<FwYchsDO> fwYchsDOList = fwYcHsService.listKyFwYchsByFwbm(fwbm);
            if(CollectionUtils.isNotEmpty(fwYchsDOList) && fwYchsDOList.size() > 1 ){
                throw new AppException("根据房屋编码查询到多条预测户室数据");
            }
            if(CollectionUtils.isNotEmpty(fwYchsDOList)){
                fwHsDO = new FwHsDO();
                BeanUtils.copyProperties(fwYchsDOList.get(0),fwHsDO);
                bdcdyResponseDTO.setHslx(Constants.FW_YCHS);
            }
        }else{
            fwHsDO = fwHsDOList.get(0);
            bdcdyResponseDTO.setHslx(Constants.FW_SCHS);
        }
        if(fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getFwHsIndex())){
            setHsInfoToDTO(bdcdyResponseDTO,fwHsDO);
        }
    }

    /**
     * @param bdcdyResponseDTO
     * @param ysfwbm
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 YSFWBM 查询户室BDCDY信息
     */
    private void initHsBdcdyByYsfwbm(BdcdyResponseDTO bdcdyResponseDTO, String ysfwbm, String hslx, String zl) {
        FwHsDO fwHsDO = null;
        if (StringUtils.isBlank(hslx) || StringUtils.equals("hs", hslx)) {
            List<FwHsDO> fwHsDOList = fwHsService.listKyFwhsByYsfwbm(ysfwbm, zl);
            if (CollectionUtils.isNotEmpty(fwHsDOList) && fwHsDOList.size() > 1) {
                throw new AppException("根据预售房屋编码查询到多条户室数据");
            }
            if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                fwHsDO = fwHsDOList.get(0);
                bdcdyResponseDTO.setHslx(Constants.FW_SCHS);
            }
        }
        // 如果户室为空 并且明确要查 预测户室 或者户室类型为空时 查询预测户室
        if(fwHsDO == null && !StringUtils.equals("hs",hslx)){
            List<FwYchsDO> fwYchsDOList = fwYcHsService.listKyFwYchsByYsfwbm(ysfwbm, zl);
            if(CollectionUtils.isNotEmpty(fwYchsDOList) && fwYchsDOList.size() > 1 ){
                throw new AppException("根据预售房屋编码查询到多条预测户室数据");
            }
            if(CollectionUtils.isNotEmpty(fwYchsDOList)){
                fwHsDO = new FwHsDO();
                BeanUtils.copyProperties(fwYchsDOList.get(0),fwHsDO);
                bdcdyResponseDTO.setHslx(Constants.FW_YCHS);
            }
        }
        if(fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getFwHsIndex())){
            setHsInfoToDTO(bdcdyResponseDTO,fwHsDO);
        }
    }


    /**
     * @param bdcdyh
     * @param djxxDOClass
     * @return T
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询地籍数据
     */
    @Override
    public <T> T queryDjxxByBdcdyh(String bdcdyh, Class<T> djxxDOClass) {
        return queryDjxxByBdcdyhWithOrder(bdcdyh,djxxDOClass,"");
    }

    @Override
    public <T> T queryDjxxByZhdm(String zhdm, Class<T> djxxDOClass) {
        return queryDjxxByZhdmWithOrder(zhdm,djxxDOClass,"");
    }

    /**
     * @param bdcdyh
     * @param djxxDOClass
     * @param order
     * @return cn.gtmap.realestate.common.core.domain.building.DjxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询地籍数据（指定）
     */
    @Override
    public <T> T queryDjxxByBdcdyhWithOrder(String bdcdyh, Class<T> djxxDOClass, String order) {
        Map paramMap = new HashMap();
        paramMap.put("bdcdyh",bdcdyh);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(djxxDOClass);
        paramMap.put("tablename",entityTable.getName());
        if(StringUtils.isNotBlank(order)){
            paramMap.put("order",order);
        }
        List<Map> result = djxxMapper.queryDjdcbList(paramMap);
        if(CollectionUtils.isNotEmpty(result)){
            return (T)BuildingUtils.map2Bean(result.get(0),djxxDOClass);
        }
        return null;
    }

    @Override
    public <T> T queryDjxxByZhdmWithOrder(String zhdm, Class<T> djxxDOClass, String order) {
        Map paramMap = new HashMap();
        paramMap.put("zhdm",zhdm);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(djxxDOClass);
        paramMap.put("tablename",entityTable.getName());
        if(StringUtils.isNotBlank(order)){
            paramMap.put("order",order);
        }
        List<Map> result = djxxMapper.queryDjdcbList(paramMap);
        if(CollectionUtils.isNotEmpty(result)){
            return (T)BuildingUtils.map2Bean(result.get(0),djxxDOClass);
        }
        return null;
    }

    /**
     * @param index
     * @param tClass
     * @return java.util.List<?>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据调查簿主键查询权利人信息
     */
    @Override
    public <T> List<T> listQlrByDjDcbIndex(String index, Class<T> tClass) {
        if (StringUtils.isNotBlank(index) && tClass != null) {
            Example example = new Example(tClass);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("djdcbIndex", index);
            example.setOrderByClause("xh asc");
            List<T> resultList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(resultList)){
                return resultList;
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * @param djh
     * @param djxxDOClass
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 DJH 查询地籍信息
     */
    @Override
    public <T> T queryDjxxByDjh(String djh, Class<T> djxxDOClass) {
        return queryDjxxByDjhWithOrder(djh,djxxDOClass,"");
    }

    /**
     * @param djh
     * @param djxxDOClass
     * @param order
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 DJH 查询地籍信息的备份
     */
    @Override
    public <T> T queryDjxxByDjhWithOrder(String djh, Class<T> djxxDOClass, String order) {
        if (StringUtils.isNotBlank(djh)) {
            Map paramMap = new HashMap();
            paramMap.put("djh",djh);
            EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(djxxDOClass);
            paramMap.put("tablename",entityTable.getName());
            if(StringUtils.isNotBlank(order)){
                paramMap.put("order",order);
            }
            List<Map> result = djxxMapper.queryDjdcbList(paramMap);
            if(CollectionUtils.isNotEmpty(result)){
                LOGGER.error("map2Bean:{}", JSONObject.toJSONString(result));
                return (T)BuildingUtils.map2Bean(result.get(0),djxxDOClass);
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyResponseDTO
     * @param xmbm
     * @return void
     * @description 根据XMBM 初始化项目信息
     */
    private void initXmxxBdcdyByXmbm(BdcdyResponseDTO bdcdyResponseDTO, String xmbm){
        List<FwXmxxDO> fwXmxxDOList = fwXmxxService.listKyFwXmxxByXmbm(xmbm);
        if(CollectionUtils.isNotEmpty(fwXmxxDOList)){
            if(fwXmxxDOList.size() > 1){
                throw new AppException("根据项目编码查询到多条项目数据");
            }
            FwXmxxDO fwXmxxDO = fwXmxxDOList.get(0);
            if (fwXmxxDO != null
                    && StringUtils.isNotBlank(fwXmxxDO.getFwXmxxIndex())
                    && bdcdyResponseDTO != null) {
                setXmxxInfoToDTO(bdcdyResponseDTO,fwXmxxDO);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyResponseDTO
     * @param fwXmxxDO
     * @return void
     * @description 向DTO中填充项目信息
     */
    private void setXmxxInfoToDTO(BdcdyResponseDTO bdcdyResponseDTO,FwXmxxDO fwXmxxDO){
        // 对象复制
        BeanUtils.copyProperties(fwXmxxDO, bdcdyResponseDTO);
        bdcdyResponseDTO.setDjid(fwXmxxDO.getFwXmxxIndex());
        bdcdyResponseDTO.setBdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
        bdcdyResponseDTO.setFwbm(fwXmxxDO.getXmbm());
        bdcdyResponseDTO.setPzjzmj(fwXmxxDO.getPzjzmj());
        // 查项目内 多幢
        List<FwLjzDO> ljzDOList = fwLjzService.listLjzByFwXmxxIndex(fwXmxxDO.getFwXmxxIndex());
        if (CollectionUtils.isNotEmpty(ljzDOList)) {
            bdcdyResponseDTO.setLjzList(ljzDOList);
            if(Objects.isNull(bdcdyResponseDTO.getPzjzmj())){
                bdcdyResponseDTO.setPzjzmj(ljzDOList.stream()
                        .filter(ljzDO -> Objects.nonNull(ljzDO.getPzjzmj()))
                        .mapToDouble(FwLjzDO::getPzjzmj).sum());
            }
        }
    }


    /**
     * @param bdcdyResponseDTO
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化 项目内多幢 不动产单元信息
     */
    private void initXmxxBdcdy(BdcdyResponseDTO bdcdyResponseDTO, String bdcdyh) {
        // 初始化项目信息
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByBdcdyh(bdcdyh);
        if (fwXmxxDO != null
                && StringUtils.isNotBlank(fwXmxxDO.getFwXmxxIndex())
                && bdcdyResponseDTO != null) {
            setXmxxInfoToDTO(bdcdyResponseDTO,fwXmxxDO);
        }
    }

    /**
     * @param bdcdyResponseDTO
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化 独幢 不动产单元信息
     */
    private void initDzBdcdy(BdcdyResponseDTO bdcdyResponseDTO, String bdcdyh) {
        // 独幢
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByBdcdyh(bdcdyh);
        if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())) {
            setFwLjzInfoToDTO(bdcdyResponseDTO,fwLjzDO);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyResponseDTO
     * @param fwLjzDO
     * @return
     * @description 像DTO中填充逻辑幢信息
     */
    private void setFwLjzInfoToDTO(BdcdyResponseDTO bdcdyResponseDTO,FwLjzDO fwLjzDO){
        // 对象复制
        BeanUtils.copyProperties(fwLjzDO, bdcdyResponseDTO);
        bdcdyResponseDTO.setDjid(fwLjzDO.getFwDcbIndex());
        bdcdyResponseDTO.setBdcdyfwlx(Constants.BDCDYFWLX_DZ);
        bdcdyResponseDTO.setGhyt(fwLjzDO.getFwyt());
        bdcdyResponseDTO.setGhyt2(fwLjzDO.getFwyt2());
        bdcdyResponseDTO.setGhyt3(fwLjzDO.getFwyt3());
        bdcdyResponseDTO.setHxjg(fwLjzDO.getFwjg());
        bdcdyResponseDTO.setZl(fwLjzDO.getZldz());
        bdcdyResponseDTO.setXmmc(fwLjzDO.getXmmc());
        //ljzh会出现重复,fwbm不取ljzh
        //bdcdyResponseDTO.setFwbm(fwLjzDO.getLjzh());
        bdcdyResponseDTO.setPzjzmj(fwLjzDO.getPzjzmj());
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyResponseDTO
     * @param ljzh
     * @return void
     * @description 根据Ljzh查询幢数据
     */
    private void initLjzBdcdyByLjzh(BdcdyResponseDTO bdcdyResponseDTO, String ljzh){
        // 独幢
        List<FwLjzDO> fwLjzDOList = fwLjzService.listKyFwljzByLjzh(ljzh);
        if(CollectionUtils.isNotEmpty(fwLjzDOList)){
            List<FwLjzDO> dzList = new ArrayList<>();
            for(FwLjzDO temp:fwLjzDOList){
                // 过滤独幢数据
                if(StringUtils.equals(temp.getBdcdyfwlx(),Constants.BDCDYFWLX_DZ)){
                    dzList.add(temp);
                }
            }
            if(CollectionUtils.isNotEmpty(dzList)){
                if(dzList.size() > 1){
                    throw new AppException("根据逻辑幢号查询到多条独幢数据");
                }
                FwLjzDO fwLjzDO = dzList.get(0);
                if (StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())) {
                    setFwLjzInfoToDTO(bdcdyResponseDTO,fwLjzDO);
                }
            }
        }
    }

    /**
     * @param bdcdyResponseDTO
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化 户室 不动产单元信息
     */
    private void initHsBdcdy(BdcdyResponseDTO bdcdyResponseDTO, String bdcdyh) {
        // 户室
        FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
        if(fwHsDO == null){
            FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh(bdcdyh);
            if(fwYchsDO != null){
                fwHsDO = new FwHsDO();
                BeanUtils.copyProperties(fwYchsDO,fwHsDO);
                bdcdyResponseDTO.setHslx(Constants.FW_YCHS);
            }
        }else{
            if(dqfwhs){
                if(fwHsDO.getScjzmj() != null &&fwHsDO.getScjzmj() >0){
                    //scjzmj不为空且大于0,为实测户室
                    bdcdyResponseDTO.setHslx(Constants.FW_SCHS);
                }else if(fwHsDO.getYcjzmj() != null &&fwHsDO.getYcjzmj() >0){
                    bdcdyResponseDTO.setHslx(Constants.FW_YCHS);
                }
            }
            if(StringUtils.isBlank(bdcdyResponseDTO.getHslx())) {
                bdcdyResponseDTO.setHslx(Constants.FW_SCHS);
            }
        }
        if (fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getFwHsIndex())) {
            setHsInfoToDTO(bdcdyResponseDTO,fwHsDO);
        }
    }

    /**
     * @param bdcdyResponseDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化 权利人 信息
     */
    private void initQlr(BdcdyResponseDTO bdcdyResponseDTO) {
        if (StringUtils.isNotBlank(bdcdyResponseDTO.getDjid())) {
            List<FwFcqlrDO> qlrList = fwFcqlrService.listFwFcQlrByFwIndex(bdcdyResponseDTO.getDjid());
            if (CollectionUtils.isNotEmpty(qlrList)) {
                bdcdyResponseDTO.setQlrList(qlrList);
            }
        }
    }

    /**
     * @param bdcdyResponseDTO
     * @return void
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 初始化 权利人家庭成员信息
     */
    private void initFwJtcy(BdcdyResponseDTO bdcdyResponseDTO){
        List<FwFcqlrDO> fwFcqlrDOList = bdcdyResponseDTO.getQlrList();
        if(CollectionUtils.isNotEmpty(fwFcqlrDOList)){
            List<FwJtcyDO> fwJtcyDOList = new ArrayList<>();
            for(FwFcqlrDO fwFcqlrDO : fwFcqlrDOList){
                List<FwJtcyDO> fwJtcyDOS = fwJtcyService.listFwJtcy(fwFcqlrDO.getFwFcqlrIndex());
                fwJtcyDOList.addAll(fwJtcyDOS);
            }
            bdcdyResponseDTO.setFwJtcyDOList(fwJtcyDOList);
        }
    }
    /**
     * @param bdcdyResponseDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  初始化建设用地量化信息
     */
    @Override
    public BdcdyResponseDTO initJsydLhxx(BdcdyResponseDTO bdcdyResponseDTO){
        if(StringUtils.isNotBlank(bdcdyResponseDTO.getBdcdyh()) &&BdcdyhToolUtils.checkXnLhbdcdyh(bdcdyResponseDTO.getBdcdyh())){
            FwJsydzrzxxQO fwJsydzrzxxQO =new FwJsydzrzxxQO();
            fwJsydzrzxxQO.setLszd(bdcdyResponseDTO.getBdcdyh().substring(0,19));
            List<FwJsydzrzxxDO> fwJsydzrzxxList =fwJsydzrzxxService.listFwJsydzrzxx(fwJsydzrzxxQO);
            if(CollectionUtils.isNotEmpty(fwJsydzrzxxList)){
                bdcdyResponseDTO.setFwJsydzrzxxList(fwJsydzrzxxList);
            }
        }
        return bdcdyResponseDTO;
    }

    @Override
    public String queryBdcdyfwlx(String bdcdyh){
        if(StringUtils.isNotBlank(bdcdyh)){
            FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByBdcdyh(bdcdyh);
            if(fwXmxxDO != null && StringUtils.isNotBlank(fwXmxxDO.getFwXmxxIndex())){
                return Constants.BDCDYFWLX_XMNDZ;
            }
            FwLjzDO fwLjzDO =fwLjzService.queryLjzByBdcdyh(bdcdyh);
            if(fwLjzDO != null &&StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())){
                return Constants.BDCDYFWLX_DZ;
            }
            FwHsDO fwHsDO =fwHsService.queryFwhsByBdcdyh(bdcdyh);
            if(fwHsDO != null &&StringUtils.isNotBlank(fwHsDO.getFwHsIndex())) {
                return Constants.BDCDYFWLX_H;
            } else {
                FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh(bdcdyh);
                if (fwYchsDO != null && StringUtils.isNotBlank(fwYchsDO.getFwHsIndex())) {
                    return Constants.BDCDYFWLX_H;
                }
            }
        }
        return "";
    }

    /**
     * @param houseId
     * @param bdcdyfwlx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据house_id 先查户室信息再查其他相关数据--蚌埠特殊需求，特殊字段
     * @date : 2022/5/16 15:09
     */
    @Override
    public BdcdyResponseDTO queryBdcdyByHouseId(String houseId, String bdcdyh, String bdcdyfwlx) {
        BdcdyResponseDTO bdcdyResponseDTO = new BdcdyResponseDTO();
        if (StringUtils.isNotBlank(bdcdyh)) {
            bdcdyResponseDTO = queryBdcdy(bdcdyh, bdcdyfwlx);
            if (Objects.isNull(bdcdyResponseDTO)) {
                return new BdcdyResponseDTO();
            }
            return bdcdyResponseDTO;
        }
        if (StringUtils.isNotBlank(houseId)) {
            List<Map> hsList =listFwScYcHsByHouseId(houseId);
            if (CollectionUtils.isNotEmpty(hsList)) {
                bdcdyh = MapUtils.getString(hsList.get(0), "BDCDYH", "");
                bdcdyResponseDTO = queryBdcdy(bdcdyh, bdcdyfwlx);
                if (Objects.isNull(bdcdyResponseDTO)) {
                    return new BdcdyResponseDTO();
                }
                return bdcdyResponseDTO;
            }
        }
        return bdcdyResponseDTO;
    }

    /**
     * @param houseId
     * @return 户室信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据houseId获取户室信息
     */
    @Override
    public List<Map> listFwScYcHsByHouseId(String houseId){
        List<Map> hsList =new ArrayList<>();
        if(StringUtils.isNotBlank(houseId)) {
            //先查实测户室
            hsList = fwHsService.queryFwhsByHouseId(houseId);
            if (CollectionUtils.isEmpty(hsList)) {
                Map map = new HashMap(1);
                map.put("houseid", houseId);
                hsList = fwYchsMapper.listFwYchsByHouseId(map);
            }
        }
        LOGGER.info("根据houseid={}查询户室信息{}", houseId, JSON.toJSONString(hsList));
        return hsList;
    }

    @Override
    public List<BdcdyResponseDTO> queryBdcdy(String houseId, String bdcdyh, String zl) {
        List<BdcdyResponseDTO> bdcdyResponseDTOS = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh) || StringUtils.isNotBlank(houseId)) {
            bdcdyResponseDTOS.add(this.queryBdcdyByHouseId(houseId,bdcdyh,null));
        }
        if (StringUtils.isNotBlank(zl)){
            Map map = new HashMap(1);
            map.put("zl", zl);
            //先查实测户室
            List<Map> hsList = fwHsMapper.listFwhsByZl(map);
            if (CollectionUtils.isEmpty(hsList)){
                hsList = fwYchsMapper.listFwYchsByZl(map);
            }
            LOGGER.warn("根据zl={}查询户室信息{}", houseId, JSON.toJSONString(hsList));
            if (CollectionUtils.isNotEmpty(hsList)) {
                for (Map hsMap:hsList){
                    bdcdyh = MapUtils.getString(hsMap, "BDCDYH", "");
                    bdcdyResponseDTOS.add(queryBdcdy(bdcdyh, null));
                }
                //根据bdcdyh进行去重
                bdcdyResponseDTOS = bdcdyResponseDTOS.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(()
                                -> new TreeSet<>(Comparator.comparing(BdcdyResponseDTO::getBdcdyh))), ArrayList::new));
                return bdcdyResponseDTOS;
            }
        }
        return bdcdyResponseDTOS;
    }

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询 权利人列表
     */
    @Override
    public List<FwFcqlrDO> listFwFcQlrByBdcdyh(String bdcdyh) {
        List<FwFcqlrDO> list = new ArrayList<>();
        BdcdyResponseDTO bdcdyResponseDTO = queryBdcdy(bdcdyh,null);
        if(bdcdyResponseDTO != null && CollectionUtils.isNotEmpty(bdcdyResponseDTO.getQlrList())){
            list = bdcdyResponseDTO.getQlrList();
        }else{
            YchsAndQlrResponseDTO ychs = fwYcHsService.queryYchsAndQlrByBdcdyh(bdcdyh);
            if(ychs != null){
                list= ychs.getFwFcqlrDOList();
            }
        }
        if(CollectionUtils.isEmpty(list)){
            return new ArrayList<>(0);
        }else{
            return list;
        }
    }

    /**
     * @param bdcdyh
     * @param hslx
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询HS BDCDY
     */
    @Override
    public FwHsDO queryHsBdcdy(String bdcdyh, String hslx) {
        // 如果是 HS 类型 或者 为空
        FwHsDO fwHsDO = null;
        if(StringUtils.isBlank(hslx) ||  StringUtils.equals("hs",hslx)){
            fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
            // 转成名称
            if(fwHsDO != null){
                entityZdConvertUtils.convertEntityToMc(fwHsDO);
            }
        }

        if(fwHsDO == null || StringUtils.equals("ychs",hslx)){
            FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh(bdcdyh);
            if(fwYchsDO != null){
                entityZdConvertUtils.convertEntityToMc(fwYchsDO);
                fwHsDO = new FwHsDO();
                BeanUtils.copyProperties(fwYchsDO,fwHsDO);
            }
        }
        return fwHsDO;
    }

    /**
     * @param fwIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FW主键 查询 BDCDYH
     */
    @Override
    public String queryBdcdyhByFwIndex(String fwIndex) {
        if(StringUtils.isNotBlank(fwIndex)){
            // 1. 先查户室
            FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(fwIndex);
            if(fwHsDO != null){
                return fwHsDO.getBdcdyh();
            }

            // 2. 查预测户室
            FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByFwHsIndex(fwIndex);
            if(fwYchsDO != null){
                return fwYchsDO.getBdcdyh();
            }

            // 3. 查 独幢
            FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwIndex);
            if(fwLjzDO != null){
                return fwLjzDO.getBdcdyh();
            }

            FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwIndex);
            if(fwXmxxDO != null){
                return fwXmxxDO.getBdcdyh();
            }
        }
        return "";
    }

    /**
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.FwBdcdyDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWBM查询户室类型的不动产单元
     */
    @Override
    public List<FwBdcdyDTO> queryFwBdcdyByFwbm(String fwbm) {
        List<FwBdcdyDTO> fwBdcdyDTOList = new ArrayList<>();
        if(StringUtils.isNotBlank(fwbm)){
            // 查询户室表
            List<FwHsDO> fwHsDOList = fwHsService.listKyFwhsByFwbm(fwbm);
            if(CollectionUtils.isNotEmpty(fwHsDOList)){
                for(FwHsDO fwHsDO : fwHsDOList){
                    FwBdcdyDTO fwBdcdyDTO = new FwBdcdyDTO();
                    fwBdcdyDTO.setFwIndex(fwHsDO.getFwHsIndex());
                    fwBdcdyDTO.setBdcdyh(fwHsDO.getBdcdyh());
                    fwBdcdyDTO.setFwbm(fwHsDO.getFwbm());
                    fwBdcdyDTO.setTablename(Constants.FW_HS_TABLE);
                    fwBdcdyDTOList.add(fwBdcdyDTO);
                }
            }
            // 查询预测户室表
            List<FwYchsDO> fwYchsDOList = fwYcHsService.listKyFwYchsByFwbm(fwbm);
            if(CollectionUtils.isNotEmpty(fwYchsDOList)){
                for(FwYchsDO fwYchsDO : fwYchsDOList){
                    FwBdcdyDTO fwBdcdyDTO = new FwBdcdyDTO();
                    fwBdcdyDTO.setFwIndex(fwYchsDO.getFwHsIndex());
                    fwBdcdyDTO.setBdcdyh(fwYchsDO.getBdcdyh());
                    fwBdcdyDTO.setFwbm(fwYchsDO.getFwbm());
                    fwBdcdyDTO.setTablename(Constants.FW_YCHS_TABLE);
                    fwBdcdyDTOList.add(fwBdcdyDTO);
                }
            }
        }
        return fwBdcdyDTOList;
    }

    /**
     * @param ybdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据ybdcdyh 查询户室 和 LJZ 的 BDCDY
     */
    @Override
    public BdcdyResponseDTO queryHsLjzBdcdyByYbdcdyh(String ybdcdyh) {
        BdcdyResponseDTO responseDTO = new BdcdyResponseDTO();
        // 先查户室
        // 户室
        List<FwHsDO> fwHsDOList = fwHsService.listKyFwhsByYbdcdyh(ybdcdyh);
        FwHsDO fwHsDO = null;
        if(CollectionUtils.isNotEmpty(fwHsDOList) && fwHsDOList.size() > 1){
            throw new AppException("根据原BDCDYH查询到多条户室数据");
        } else if(CollectionUtils.isNotEmpty(fwHsDOList)){
            fwHsDO = fwHsDOList.get(0);
            responseDTO.setHslx(Constants.FW_SCHS);
        }
        if(fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getFwHsIndex())){
            setHsInfoToDTO(responseDTO,fwHsDO);
        }else{
            // 再查独幢

            List<FwLjzDO> fwLjzDOList = fwLjzService.listKyFwLjzByYbdcdyh(ybdcdyh);
            if(CollectionUtils.isNotEmpty(fwLjzDOList)){
                if(fwLjzDOList.size() > 1){
                    throw new AppException("根据原BDCDYH查询到多条独幢数据");
                }
                FwLjzDO fwLjzDO = fwLjzDOList.get(0);
                if (StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())) {
                    setFwLjzInfoToDTO(responseDTO,fwLjzDO);
                }
            }
        }
        return responseDTO;
    }
}
