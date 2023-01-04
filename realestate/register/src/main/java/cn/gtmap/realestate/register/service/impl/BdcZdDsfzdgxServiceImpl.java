package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdSsDO;
import cn.gtmap.realestate.common.core.domain.BdcZdZgSqxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcZdDsfzdgxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.BdcZdGlService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.accept.ui.TreeNodeVO;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.register.service.BdcZdDsfzdgxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/6/17 16:13
 * @description
 */
@Service
public class BdcZdDsfzdgxServiceImpl implements BdcZdDsfzdgxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZdDsfzdgxServiceImpl.class);

    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcZdGlService bdcZdGlService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;


    /**
     * 查询第三方系统与登记系统字典项值对照关系
     * 先从内存缓存中获取字典项内容，未获取到时去数据库中获取
     *
     * @param bdcZdDsfzdgxDO bdcZdDsfzdgxDO
     * @return BdcZdDsfzdgxDO BdcZdDsfzdgxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public BdcZdDsfzdgxDO queryZdgx(BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        if (StringUtils.isBlank(bdcZdDsfzdgxDO.getZdbs()) || StringUtils.isBlank(bdcZdDsfzdgxDO.getDsfxtbs())) {
            throw new MissingArgumentException("缺少参数：字典表名称,第三方系统标识");
        }
        if(StringUtils.isBlank(bdcZdDsfzdgxDO.getBdczdz()) && StringUtils.isBlank(bdcZdDsfzdgxDO.getDsfzdz())){
            return null;
        }

        // 采用第三的 zbbs,dsfxtbs 作为key，设置内存缓存内容
        String zdbs = bdcZdDsfzdgxDO.getZdbs() + CommonConstantUtils.ZF_YW_DH + bdcZdDsfzdgxDO.getDsfxtbs();
        List<Map> zdxMapList = this.bdcZdFeignService.queryBdcZd(zdbs);
        if(CollectionUtils.isEmpty(zdxMapList)) {
            // 未获取到缓存内容时，调用数据库查询返回第三方字典项。
            return this.getDatabaseVal(bdcZdDsfzdgxDO);
        }

        final String bdcZdzKey = "BDCZDZ";
        final String dsfZdzKey = "DSFZDZ";
        //  是否获取到缓存中的值，true：获取到，fasle:未获取到。
        boolean getCacheVal = false;

        if(StringUtils.isNotBlank(bdcZdDsfzdgxDO.getDsfzdz())){
            // 1、传第三方字典值，获取不动产字典值
            String val = this.getZdxValue(bdcZdDsfzdgxDO.getDsfzdz(), dsfZdzKey, bdcZdzKey, zdxMapList);
            if(StringUtils.isNotBlank(val)){
                bdcZdDsfzdgxDO.setBdczdz(val);
                getCacheVal = true;
            }
        }else if(StringUtils.isNotBlank(bdcZdDsfzdgxDO.getBdczdz())){
            // 2、传不动产字典值，获取第三方字典值
            String val = this.getZdxValue(bdcZdDsfzdgxDO.getBdczdz(), bdcZdzKey, dsfZdzKey, zdxMapList);
            if(StringUtils.isNotBlank(val)){
                bdcZdDsfzdgxDO.setDsfzdz(val);
                getCacheVal = true;
            }
        }
        return getCacheVal ?  bdcZdDsfzdgxDO : this.getDatabaseVal(bdcZdDsfzdgxDO);
    }

    /**
     * 获取数据库中第三方字典项的值
     */
    private BdcZdDsfzdgxDO getDatabaseVal(BdcZdDsfzdgxDO bdcZdDsfzdgxDO){
        Example example = new Example(BdcZdDsfzdgxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("zdbs", StringUtils.upperCase(bdcZdDsfzdgxDO.getZdbs()));
        if (StringUtils.isNotBlank(bdcZdDsfzdgxDO.getDsfzdz())) {
            criteria.andEqualTo("dsfzdz", StringUtils.trim(bdcZdDsfzdgxDO.getDsfzdz()));
        } else {
            criteria.andEqualTo("bdczdz", StringUtils.trim(bdcZdDsfzdgxDO.getBdczdz()));
        }
        criteria.andEqualTo("dsfxtbs", StringUtils.trim(bdcZdDsfzdgxDO.getDsfxtbs()));
        List<BdcZdDsfzdgxDO> bdcZsList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isEmpty(bdcZsList)) {
            return null;
        }
        return bdcZsList.get(0);
    }

    /**
     *  获取内存缓存中字典项的值
     *  使用字典值比对方式。使用compareValue与compareKey在内存中的值进行比对，相等时返回valueKey的值。
     */
    private String getZdxValue(String compareValue, String compareKey, String valueKey, List<Map> zdxMapList){
        for(Map map : zdxMapList){
            if(compareValue.equals(MapUtils.getString(map, compareKey))){
                return MapUtils.getString(map, valueKey);
            }
        }
        return "";
    }

    /**
     * 查询数据字典的中国省市区县的树形结构数据
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [] 无参数
     * @return: List<TreeNodeVO> 树形结构的List<TreeNode>
     */
    @Override
    public List<TreeNodeVO> queryZdZgSsqx() {
        List<HashMap> provinceMap = this.bdcZdGlService.getZdTableData(BdcZdSsDO.class);
        List<HashMap> cityAndDistrictMap = this.bdcZdGlService.getZdTableData(BdcZdZgSqxDO.class); //市
        List<TreeNodeVO> treeNodeVOList = new ArrayList<>(34);
        List<TreeNodeVO> cityAndDistrictList = convertMapToTreeNode(cityAndDistrictMap);
        for(Map map : provinceMap){
            TreeNodeVO treeNode = new TreeNodeVO(MapUtils.getInteger(map, "DM").toString(),
                    MapUtils.getString(map,"MC"));
            treeNodeVOList.add(findChildrenNode(treeNode,cityAndDistrictList));
        }
        return treeNodeVOList;
    }

    /**
     * 递归封装子节点信息
     */
    private TreeNodeVO findChildrenNode(TreeNodeVO parentNode,List<TreeNodeVO> treeNodeVOList){
        for(TreeNodeVO treeNode : treeNodeVOList){
            if(treeNode.getpId().equals(parentNode.getId())){
                if(null == parentNode.getChildren()){
                    parentNode.setChildren(new ArrayList());
                }
                parentNode.getChildren().add(findChildrenNode(treeNode,treeNodeVOList));
            }
        }
        return parentNode;
    }
    /**
     * 将字典项Map结构数据转换为TreeNodeVo对象集合
     */
    private List<TreeNodeVO> convertMapToTreeNode(List<HashMap> mapList){
        List<TreeNodeVO> treeNodeVOList = new ArrayList<>(mapList.size());
        for(Map map : mapList){
            if(map.containsKey("FJDDM")){
                treeNodeVOList.add(new TreeNodeVO(MapUtils.getInteger(map, "DM").toString(),
                        MapUtils.getString(map,"MC"),
                        MapUtils.getString(map, "FJDDM")));
            }else{
                treeNodeVOList.add(new TreeNodeVO(MapUtils.getInteger(map, "DM").toString(),
                        MapUtils.getString(map,"MC")));
            }
        }
        return treeNodeVOList;
    }

    /**
     * 获取所有第三方字典项数据
     * @return 字典项数据
     */
    @Override
    public Map<String, List<Map>> queryAllDsfZdgx() {
        Example example = new Example(BdcZdDsfzdgxDO.class);
        List<BdcZdDsfzdgxDO> bdcDsfZdList = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bdcDsfZdList)) {
            return new HashedMap();
        }
        return convertDsfZdxToMap(bdcDsfZdList);
    }

    private  Map<String, List<Map>> convertDsfZdxToMap(List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOList){
        Map<String, List<Map>> dsfZdgxMap = new HashMap<>(16);
        for(BdcZdDsfzdgxDO bdcZdDsfzdgxDO : bdcZdDsfzdgxDOList){
            String zdbs = bdcZdDsfzdgxDO.getZdbs()+ CommonConstantUtils.ZF_YW_DH + bdcZdDsfzdgxDO.getDsfxtbs() ;
            if(dsfZdgxMap.containsKey(zdbs)){
                Map map = this.getFieldMap(bdcZdDsfzdgxDO);
                if(null != map){
                    dsfZdgxMap.get(zdbs).add(map);
                }
            }else{
                Map map = this.getFieldMap(bdcZdDsfzdgxDO);
                if(null!= map){
                    dsfZdgxMap.put(zdbs, Stream.of(map).collect(Collectors.toList()));
                }
            }
        }
        return dsfZdgxMap;
    }

    @Override
    public Map<String, List<Map>> queryDsfZdgxBybs(String zdbs, String dsfxtbs) {
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfxtbs);
        List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOList = this.queryDsfZdxBybs(bdcZdDsfzdgxDO);

        if(CollectionUtils.isEmpty(bdcZdDsfzdgxDOList)){
            return new HashedMap();
        }
        return convertDsfZdxToMap(bdcZdDsfzdgxDOList);
    }

    /**
     * 分页查询第三方字典项数据
     */
    @Override
    public Page<BdcZdDsfzdgxDO> listDsfZdxByPage(Pageable pageable, BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        return repo.selectPaging("listDsfZdxByPage", bdcZdDsfzdgxDO, pageable);
    }

    /**
     * 通过主键删除第三方配置项内容
     * @param id  配置项主键
     */
    @Override
    public void deleteDsfZdxById(String id) {
        if(StringUtils.isNotBlank(id)){
            entityMapper.deleteByPrimaryKey(BdcZdDsfzdgxDO.class, id);
        }
    }

    /**
     * 根据字典标识与第三方字典标识删除第三方字典项数据
     * @param bdcZdDsfzdgxDO 第三方字典项DO
     */
    @Override
    public void deleteDsfZdxBybs(BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        if(StringUtils.isAnyBlank(bdcZdDsfzdgxDO.getDsfxtbs(),bdcZdDsfzdgxDO.getZdbs())){
            throw new MissingArgumentException("缺失第三方系统标识或字典标识。");
        }
        Example example = new Example(BdcZdDsfzdgxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dsfxtbs", bdcZdDsfzdgxDO.getDsfxtbs());
        criteria.andEqualTo("zdbs", StringUtils.upperCase(bdcZdDsfzdgxDO.getZdbs()));
        entityMapper.deleteByExampleNotNull(example);
    }

    /**
     * 新增第三方字典项内容
     * @param bdcZdDsfzdgxDO 字典项配置内容
     * @return 字典项配置
     */
    @Override
    public BdcZdDsfzdgxDO insertDsfZdx(BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        if(null == bdcZdDsfzdgxDO || CheckParameter.checkPartElementsNotAllExist(bdcZdDsfzdgxDO,
                Arrays.asList(new Object[]{"id"}))){
            throw new MissingArgumentException("配置内容缺失。");
        }
        if(StringUtils.isBlank(bdcZdDsfzdgxDO.getId())){
            bdcZdDsfzdgxDO.setId(UUIDGenerator.generate16());
        }
        entityMapper.insertSelective(bdcZdDsfzdgxDO);
        return bdcZdDsfzdgxDO;
    }

    @Override
    public int updateDsfZdx(BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        if(null==bdcZdDsfzdgxDO || StringUtils.isBlank(bdcZdDsfzdgxDO.getId())){
            throw new MissingArgumentException("未获取到第三方字典项对照关系");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcZdDsfzdgxDO);
    }

    /**
     * 据字典标识与第三方字典标识获取第三方字典项数据
     * @param bdcZdDsfzdgxDO
     * @return 字典项数据集合
     */
    @Override
    public List<BdcZdDsfzdgxDO> queryDsfZdxBybs(BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        if (StringUtils.isBlank(bdcZdDsfzdgxDO.getZdbs()) || StringUtils.isBlank(bdcZdDsfzdgxDO.getDsfxtbs())) {
            throw new MissingArgumentException("缺少参数：字典表名称,第三方系统标识");
        }
        Example example = new Example(BdcZdDsfzdgxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("zdbs", bdcZdDsfzdgxDO.getZdbs());
        criteria.andEqualTo("dsfxtbs", StringUtils.trim(bdcZdDsfzdgxDO.getDsfxtbs()));
        return entityMapper.selectByExample(example);
    }

    @Override
    public List<BdcZdDsfzdgxDO> saveBdcZdDsfzdgx(BdcZdDsfzdgxDTO bdcZdDsfzdgxDTO) {
        if(CollectionUtils.isNotEmpty(bdcZdDsfzdgxDTO.getBdcZdDsfzdgxDOList())) {
            // 通过字典标识查询存在的对照数据
            BdcZdDsfzdgxDO queryParam = new BdcZdDsfzdgxDO();
            queryParam.setZdbs(bdcZdDsfzdgxDTO.getZdbs());
            queryParam.setDsfxtbs(bdcZdDsfzdgxDTO.getDsfxtbs());
            List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOS = this.queryDsfZdxBybs(queryParam);
            // 当有数据时，先删除原有对照数据，在进行新增
            if (CollectionUtils.isNotEmpty(bdcZdDsfzdgxDOS)) {
                this.deleteDsfZdxBybs(queryParam);
            }
            for(BdcZdDsfzdgxDO bdcZdDsfzdgxDO : bdcZdDsfzdgxDTO.getBdcZdDsfzdgxDOList()){
                bdcZdDsfzdgxDO.setZdbs(bdcZdDsfzdgxDTO.getZdbs());
                bdcZdDsfzdgxDO.setDsfxtbs(bdcZdDsfzdgxDTO.getDsfxtbs());
                if(StringUtils.isBlank(bdcZdDsfzdgxDO.getId())){
                    bdcZdDsfzdgxDO.setId(UUIDGenerator.generate16());
                }
                this.insertDsfZdx(bdcZdDsfzdgxDO);
            }
        }
        return bdcZdDsfzdgxDTO.getBdcZdDsfzdgxDOList();
    }

    @Override
    public List<Map> queryDsfZd(String zdbs,String dsfxtbs){
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfxtbs);
        List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOList = this.queryDsfZdxBybs(bdcZdDsfzdgxDO);

        if(CollectionUtils.isEmpty(bdcZdDsfzdgxDOList)){
            return new ArrayList<>();
        }
        List<Map> mapList =new ArrayList<>();
        for(BdcZdDsfzdgxDO zdDsfzdgxDO : bdcZdDsfzdgxDOList){
            Map map =new HashMap();
            map.put("DSFZDZ",zdDsfzdgxDO.getDsfzdz());
            map.put("BDCZDZ",zdDsfzdgxDO.getBdczdz());
            mapList.add(map);

        }
        return mapList;

    }

    private Map getFieldMap(BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        try{
            Field[] fields = BdcZdDsfzdgxDO.class.getDeclaredFields();
            Map map = new HashMap(fields.length);
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(StringUtils.upperCase(field.getName()), field.get(bdcZdDsfzdgxDO));
            }
            return map;
        }catch(IllegalAccessException ex){
            LOGGER.error(ex.getMessage(), ex);
        }
       return null;
    }

}
