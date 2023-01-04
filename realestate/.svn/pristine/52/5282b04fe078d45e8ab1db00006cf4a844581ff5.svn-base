package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.BdcLzrDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLzxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcLzrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlLzrFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcLzrRestService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.mapper.BdcLzrMapper;
import cn.gtmap.realestate.init.core.service.BdcLzrService;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.util.DataParseUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: realestate
 * @description: 领证人相关方法实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-17 17:13
 **/
@Service
public class BdcLzrServiceImpl implements BdcLzrService {

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    BdcXmService bdcXmService;
    @Autowired
    BdcLzrMapper bdcLzrMapper;
    @Autowired
    BdcLzrRestService bdcLzrRestService;
    @Autowired
    BdcSlLzrFeignService bdcSlLzrFeignService;
    @Autowired
    BdcQlrService bdcQlrService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;

    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;


    private static final String NOPARAMETER = "message.noparameter";

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcLzrServiceImpl.class);

    /**
     * @param bdcLzrDO 领证人信息
     * @param order
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询领证人信息
     * @date : 2020/1/17 16:48
     */
    @Override
    public List<BdcLzrDO> queryBdcLzr(BdcLzrDO bdcLzrDO, String order) {
        Example example = entityMapper.objToExample(bdcLzrDO);
        if (example != null && StringUtils.isNotBlank(order)) {
            example.setOrderByClause(order);
        }
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增领证人
     * @date : 2020/1/17 17:28
     */
    @Override
    public void insertBdcLzr(BdcLzrDO bdcLzrDO) {
        if (bdcLzrDO != null) {
            if (StringUtils.isBlank(bdcLzrDO.getLzrid())) {
                bdcLzrDO.setLzrid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcLzrDO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BdcLzrDO> insertBatchBdcLzr(BdcLzrDO bdcLzrDO, String gzlslid, String djxl) {
        List<BdcLzrDO> bdcLzrDOList = new ArrayList<>();
        if (StringUtils.isNoneBlank(gzlslid) && bdcLzrDO != null) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmQO.setDjxl(djxl);
            List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXm(bdcXmQO);
            //合肥版本根据权利人更新存在qlrid
            if (StringUtils.isNotBlank(bdcLzrDO.getQlrid()) && CollectionUtils.isNotEmpty(bdcXmDOList)) {
                List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
                BdcQlrDO bdcQlrDO = bdcQlrService.queryBdcQlrByQlrid(bdcLzrDO.getQlrid());
                if (bdcQlrDO != null) {
                    for (BdcXmDO xmDO : bdcXmDOList) {
                        BdcQlrDO bdcQlr = new BdcQlrDO();
                        bdcQlr.setQlrmc(bdcQlrDO.getQlrmc());
                        bdcQlr.setZjh(bdcQlrDO.getZjh());
                        bdcQlr.setXmid(xmDO.getXmid());
                        List<BdcQlrDO> bdcQlrDOS = bdcQlrService.queryBdcQlrByQlrxx(bdcQlr, "xmid");
                        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                            bdcQlrDOList.add(bdcQlrDOS.get(0));
                        }
                    }
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        for (BdcQlrDO qlrDO : bdcQlrDOList) {
                            //1.先根据权利人id找一下领证人是否存在，存在就先删掉原来的在插入，不存在新增
                            BdcLzrDO bdcLzr = new BdcLzrDO();
                            bdcLzr.setQlrid(qlrDO.getQlrid());
                            List<BdcLzrDO> bdcLzrDOS = this.queryBdcLzr(bdcLzr, "xmid");
                            if (CollectionUtils.isNotEmpty(bdcLzrDOS)) {
                                this.deleteBdcLzr(bdcLzrDOS.get(0).getLzrid());
                            }
                            BdcLzrDO lzrDO = new BdcLzrDO();
                            BeanUtils.copyProperties(bdcLzrDO, lzrDO);
                            lzrDO.setXmid(qlrDO.getXmid());
                            lzrDO.setQlrid(qlrDO.getQlrid());
                            lzrDO.setLzrid(UUIDGenerator.generate16());
                            bdcLzrDOList.add(lzrDO);

                        }
                    }
                }
            } else {
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    for (BdcXmDO xmDO : bdcXmDOList) {
                        //非空时过滤
                        if (StringUtils.isBlank(djxl) || StringUtils.equals(xmDO.getDjxl(), djxl)) {
                            BdcLzrDO lzrDO = new BdcLzrDO();
                            BeanUtils.copyProperties(bdcLzrDO, lzrDO);
                            lzrDO.setXmid(xmDO.getXmid());
                            lzrDO.setLzrid(UUIDGenerator.generate16());
                            bdcLzrDOList.add(lzrDO);
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                List<List> partList = ListUtils.subList(bdcLzrDOList, 500);
                for (List data : partList) {
                    entityMapper.insertBatchSelective(data);
                }
            }
        }
        return bdcLzrDOList;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcLzrDO, bdcXmDO]
     * @return java.lang.String
     * @description 根据项目查询领证人对应的qlrid 盐城
     */
    private String getLzrQlridByXm(BdcLzrDO bdcLzrDO, BdcXmDO bdcXmDO){
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setXmid(bdcXmDO.getXmid());
        bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,null);
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            for(BdcQlrDO qlr : bdcQlrDOList) {
                if(StringUtils.equals(qlr.getQlrmc(),bdcLzrDO.getLzrmc()) && StringUtils.equals(qlr.getZjh(),bdcLzrDO.getLzrzjh())){
                    return qlr.getQlrid();
                }
            }
        }
        return null;
    }

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新领证人
     * @date : 2020/1/17 17:28
     */
    @Override
    public int updateBdcLzr(BdcLzrDO bdcLzrDO) {
        int num;
        if (bdcLzrDO != null && StringUtils.isNotBlank(bdcLzrDO.getLzrid())) {
            num = entityMapper.updateByPrimaryKeySelective(bdcLzrDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(NOPARAMETER));
        }
        return num;
    }

    /**
     * @param lzrid 领证人id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除领证人
     * @date : 2020/1/17 17:28
     */
    @Override
    public void deleteBdcLzr(String lzrid) {
        if (StringUtils.isNotBlank(lzrid)) {
            entityMapper.deleteByPrimaryKey(BdcLzrDO.class, lzrid);
        }
    }

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 用xmid删除领证人
     * @date : 2020/1/17 17:28
     */
    @Override
    public void deleteBdcLzrByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcLzrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(BdcLzrDO.class, example);
        }
    }

    /**
     * @param zsid 证书ID
     * @return List<BdcLzrDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证书ID查询和证书相关的所有项目的受理领证人信息
     */
    @Override
    public List<BdcLzrDO> getAllZsXmLzrByZsid(String zsid) {
        if (StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("缺失查询参数zsid！");
        }
        return bdcLzrMapper.getAllZsXmLzrByZsid(zsid);
    }

    /**
     * 批量更新不动产权利人
     *
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新不动产权利人
     */
    @Override
    public int updateBatchBdcLzr(BdcDjxxUpdateQO bdcDjxxUpdateQO) throws ClassNotFoundException {
        if (bdcDjxxUpdateQO == null || StringUtils.isAnyBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
        }
        //获取更新json对象
        Map map = DataParseUtil.bdcDjxxUpdateQOParseSqlMap(bdcDjxxUpdateQO,BdcLzrDO.class);
        if(MapUtils.isEmpty(map)){
            return 0;
        }else{
            return bdcLzrMapper.updateBatchBdcLzr(map);
        }
    }

    /**
     * 根据领证人名称和证件号批量删除流程内权利人信息
     *
     * @param gzlslid
     * @param djxl
     * @param bdcLzrDO
     * @return
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据领证人名称和证件号批量删除流程内权利人信息
     */
    @Override
    public void deleteBdcLzrsByLzrxx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid, String djxl, BdcLzrDO bdcLzrDO) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put("gzlslid", gzlslid);
            if (bdcLzrDO != null) {
                map.put("lzrmc", bdcLzrDO.getLzrmc());
                map.put("lzrzjh", bdcLzrDO.getLzrzjh());
            }
            map.put("djxl", djxl);
            bdcLzrMapper.deleteLzr(map);
        }
    }

    /**
     * @param bdcLzrDTO 前台传入领证人更新信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/2/21 9:24
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateLzrxxToPllc(BdcLzrDTO bdcLzrDTO) throws Exception {
        if (StringUtils.isBlank(bdcLzrDTO.getXmid()) || StringUtils.isBlank(bdcLzrDTO.getJson()) || StringUtils.isBlank(bdcLzrDTO.getGzlslid())) {
            throw new AppException("更新领证人缺少必要参数信息");
        }
        //根据qlrid找到对应的领证人
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setQlrid(bdcLzrDTO.getQlrid());
        List<BdcLzrDO> bdcLzrDOList = bdcLzrRestService.listBdcLzr(bdcLzrQO);
        //根据名称证件号删除当前权利人对应的领证人信息
        if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
            String lzrzjh = "";
            String djxl = "";
            if (StringUtils.isNotBlank(bdcLzrDOList.get(0).getLzrzjh())) {
                lzrzjh = bdcLzrDOList.get(0).getLzrzjh();
            }
            if (StringUtils.isNotBlank(bdcLzrDTO.getDjxl())) {
                djxl = bdcLzrDTO.getDjxl();
            }
            bdcLzrRestService.deleteBdcLzrsByLzrxx(bdcLzrDOList.get(0).getLzrmc(), lzrzjh, bdcLzrDTO.getGzlslid(), djxl);
        }
        return bdcSlLzrFeignService.updatePlBdcLzr(bdcLzrDTO.getJson(), bdcLzrDTO.getGzlslid(), bdcLzrDTO.getDjxl());
    }

    /**
     * @param bdcLzrDTO 前台传入领证人更新信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 单个流程更新数据
     * @date : 2020/2/21 9:24
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BdcLzrDO updateLzrxxToJdlc(BdcLzrDTO bdcLzrDTO) {
        //根据权利人id找到对应的领证人
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setQlrid(bdcLzrDTO.getQlrid());
        List<BdcLzrDO> bdcLzrDOList = bdcLzrRestService.listBdcLzr(bdcLzrQO);
        //根据名称证件号删除当前权利人对应的领证人信息
        if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
            String lzrzjh = "";
            if (StringUtils.isNotBlank(bdcLzrDOList.get(0).getLzrzjh())) {
                lzrzjh = bdcLzrDOList.get(0).getLzrzjh();
            }
            bdcLzrRestService.deleteBdcLzrsByLzrxx(bdcLzrDOList.get(0).getLzrmc(), lzrzjh, bdcLzrDTO.getGzlslid(), "");
        }
        BdcLzrDO bdcLzrDO = new BdcLzrDO();
        JSONArray jsonArray = JSONObject.parseArray(bdcLzrDTO.getJson());
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                BdcLzrDO bdcLzr = JSONObject.parseObject(JSONObject.toJSONString(obj), BdcLzrDO.class);
                if (bdcLzr != null) {
                    bdcLzrDO = bdcLzrRestService.insertBdcLzr(bdcLzr);
                }
            }
        }
        return bdcLzrDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatchBdcLzr(List<BdcLzrDO> bdcLzrDOList){
        int count =0;
        if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
            List<List> partList = ListUtils.subList(bdcLzrDOList, 500);
            for (List data : partList) {
                count +=entityMapper.insertBatchSelective(data);
            }
        }
        return count;
    }

    /**
     * 更新lzfs
     * @param gzlslid
     * @param lzfs
     */
    @Override
    public void updateAllLzfsByGzlslid(String gzlslid, String lzfs) {
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(lzfs)) {
            BdcXmFbDO bdcXmFbDO =new BdcXmFbDO();
            bdcXmFbDO.setLzfs(Integer.parseInt(lzfs));
            Example example = new Example(BdcXmFbDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("gzlslid",gzlslid);
            entityMapper.updateByExampleSelectiveNotNull(bdcXmFbDO,example);
        }
    }

    /**
     * 查询lzfs
     *
     * @param gzlslid
     */
    @Override
    public Integer getLzfsByGzlslid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put("gzlslid", gzlslid);
            return bdcLzrMapper.getLzfsByGzlslid(map);
        }else{
            throw new AppException("查询领证方式服务，缺失工作流实例id");
        }
    }

    /**
     * 查询lzfs
     *
     * @param gzlslid
     */
    @Override
    public List<BdcLzxxDTO> getAllLzfsByGzlslid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            return bdcLzrMapper.getAllLzfsByGzlslid(gzlslid);
        }else{
            throw new AppException("查询领证方式服务，缺失工作流实例id");
        }
    }

    /**
     * 查询lzfs
     *
     * @param zsid
     */
    @Override
    public BdcLzxxDTO getLzfsByZsid(String zsid) {
        if (StringUtils.isNotBlank(zsid)) {
            return bdcLzrMapper.getLzfsByZsid(zsid);
        }else{
            throw new AppException("查询领证方式服务，缺失证书id");
        }
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcXmDOList]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcLzrDO>
     * @description 根据项目查询领证人
     */
    @Override
    public List<BdcLzrDO> getBdcLzrDOByXm(List<BdcXmDO> bdcXmDOList) {
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            return null;
        }
        return bdcLzrMapper.getBdcLzrDOByXm(bdcXmDOList);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcLzrDOList]
     * @return int
     * @description 批量更新领证人
     */
    @Override
    public int batchUpdateBdcLzr(List<BdcLzrDO> bdcLzrDOList) {
        if(CollectionUtils.isEmpty(bdcLzrDOList)){
            return 0;
        }
        int count = 0;
        for(BdcLzrDO bdcLzrDO : bdcLzrDOList){
            int num = this.updateBdcLzr(bdcLzrDO);
            count += num;
        }
        return count;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcLzrDOList]
     * @return void
     * @description 批量删除领证人
     */
    @Override
    public void batchDeleteBdcLzr(List<BdcLzrDO> bdcLzrDOList) {
        if(CollectionUtils.isNotEmpty(bdcLzrDOList)){
            bdcLzrDOList.forEach(bdcLzrDO -> this.deleteBdcLzr(bdcLzrDO.getLzrid()));
        }
    }

    /**
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcLzrDO>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcXmDOList]
     * @description 根据项目查询领证人
     */
    @Override
    public List<BdcLzrDO> getBdcLzrDOByGzlslid(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return null;
        }
        return bdcLzrMapper.getBdcLzrDOByGzlslid(gzlslid);
    }

    /**
     * @param gzlslid
     * @param lzrmc
     * @param lzrzjh
     * @param qlridList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除领证人信息
     * @date : 2022/3/25 13:28
     */
    @Override
    public void deleteLzrxx(String gzlslid, String lzrmc, String lzrzjh, String djxl, List<String> qlridList) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put("gzlslid", gzlslid);
            map.put("lzrmc", lzrmc);
            map.put("lzrzjh", lzrzjh);
            map.put("djxl", djxl);
            map.put("qlridList", qlridList);
            bdcLzrMapper.deleteLzr(map);
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 回写领证人信息到证书表相关字段
     * @date : 2022/8/9 9:30
     */
    @Override
    public void hxLzrxxToZs(String gzlslid) {
        //回写证书表逻辑如下：
        /*
         * 1. 先判断流程类型，单个，批量，组合，批量组合
         * 2. 单个流程，批量流程 用xmid取一个项目查领证人，组合流程，批量组合循环查询
         * 3. 判断领证人表qlrid 是否有值，有值说明和权利人关联，直接用qlr表的zsid字段更新证书
         * 4. 没有值用xmid 查询证书
         * 5. 新增修改删除时调用
         * */
        if (StringUtils.isNotBlank(gzlslid)) {
            int lclx = bdcXmService.bdcXmLx(gzlslid);
            List<BdcXmDO> bdcXmDTOList = bdcXmService.listBdcXm(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                String xmid = bdcXmDTOList.get(0).getXmid();
                switch (lclx) {
                    case 1:
                        getLzrAndZsxx(xmid, gzlslid, true, null, 1);
                        break;
                    case 3:
                        //批量流程判断生成证书数量。一本证取一条，多本证循环
                        //判断是否分别持证，分别持证循环项目
                        if (Objects.equals(CommonConstantUtils.SF_S_DM, bdcXmDTOList.get(0).getSqfbcz())) {
                            for (BdcXmDO bdcXmDTO : bdcXmDTOList) {
                                getLzrAndZsxx(bdcXmDTO.getXmid(), gzlslid, false, null, 3);
                            }
                        } else {
                            getLzrAndZsxx(xmid, gzlslid, true, null, 3);
                        }
                        break;
                    case 2:
                        for (BdcXmDO bdcXmDTO : bdcXmDTOList) {
                            if (Objects.equals(CommonConstantUtils.SF_S_DM, bdcXmDTO.getSqfbcz())) {
                                getLzrAndZsxx(bdcXmDTO.getXmid(), gzlslid, false, null, 2);
                            } else {
                                getLzrAndZsxx(bdcXmDTO.getXmid(), gzlslid, true, null, 2);
                            }
                        }
                        break;
                    case 4:
                        //根据登记小类分组
                        Map<String, List<BdcXmDO>> xmMap = bdcXmDTOList.stream().collect(Collectors.groupingBy(BdcXmDO::getDjxl));
                        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmService.listBdCshSl(gzlslid);
                        Map<String, List<BdcCshFwkgSlDO>> cshKgSlMap = bdcCshFwkgSlDOList.stream().collect(Collectors.groupingBy(BdcCshFwkgSlDO::getDjxl));
                        for (String key : xmMap.keySet()) {
                            List<BdcXmDO> bdcXmDTOS = xmMap.get(key);
                            BdcXmDO bdcXm = bdcXmService.queryBdcXmByPrimaryKey(bdcXmDTOS.get(0).getXmid());
                            if (Objects.equals(CommonConstantUtils.SF_S_DM, bdcXm.getSqfbcz())) {
                                for (BdcXmDO bdcXmDTO : bdcXmDTOS) {
                                    getLzrAndZsxx(bdcXmDTO.getXmid(), gzlslid, false, null, 4);
                                }
                            } else {
                                //判断生成证书数量是一本还是多本
                                if (MapUtils.isNotEmpty(cshKgSlMap) && cshKgSlMap.containsKey(key)) {
                                    List<BdcCshFwkgSlDO> bdcCshFwkgSlDOS = cshKgSlMap.get(key);
                                    if (CollectionUtils.isNotEmpty(bdcCshFwkgSlDOS)) {
                                        //为空生成多本证，循环处理
                                        List<BdcCshFwkgSlDO> nullZsxhList = bdcCshFwkgSlDOS.stream().filter(fwkgsl -> Objects.isNull(fwkgsl.getZsxh())).collect(Collectors.toList());
                                        //根据证书序号分组，数量=1 生成一本证，> 1 生成多本证
                                        List<BdcCshFwkgSlDO> zsxhList = bdcCshFwkgSlDOS.stream().filter(fwkgsl -> Objects.nonNull(fwkgsl.getZsxh())).collect(Collectors.toList());
                                        Map<Integer, List<BdcCshFwkgSlDO>> zsxhMapList = zsxhList.stream().collect(Collectors.groupingBy(BdcCshFwkgSlDO::getZsxh));
                                        if (CollectionUtils.isNotEmpty(nullZsxhList) || zsxhMapList.size() > 1) {
                                            for (BdcXmDO bdcXmDTO : bdcXmDTOS) {
                                                getLzrAndZsxx(bdcXmDTO.getXmid(), gzlslid, true, null, 4);
                                            }
                                        } else {
                                            getLzrAndZsxx(bdcXm.getXmid(), gzlslid, true, bdcXmDTOS, 4);
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void getLzrAndZsxx(String xmid, String gzlslid, boolean sfdbz, List<BdcXmDO> bdcXmDOList, int lclx) {
        BdcLzrDO bdcLzrQO = new BdcLzrDO();
        bdcLzrQO.setXmid(xmid);
        List<BdcLzrDO> bdcLzrDOS = this.queryBdcLzr(bdcLzrQO, "lzrid asc");
        if (CollectionUtils.isNotEmpty(bdcLzrDOS)) {
            if (StringUtils.isBlank(bdcLzrDOS.get(0).getQlrid())) {
                //不和权利人关联，直接用xmid 查找证书并更新
                if (Objects.equals(3, lclx) || Objects.equals(1, lclx)) {
                    //批量流程根据证书数量循环更新
                    BdcZsQO bdcZsQO = new BdcZsQO();
                    bdcZsQO.setGzlslid(gzlslid);
                    List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
                    if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                        for (BdcZsDO bdcZsDO : bdcZsDOList) {
                            hxZsLzr(Collections.singletonList(bdcZsDO), bdcLzrDOS);
                        }
                    }
                }
                //组合流程
                if (Objects.equals(2, lclx)) {
                    List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(xmid);
                    hxZsLzr(bdcZsDOList, bdcLzrDOS);
                }
                //批量组合
                if (Objects.equals(4, lclx)) {
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        for (BdcXmDO bdcXmDO : bdcXmDOList) {
                            List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(bdcXmDO.getXmid());
                            hxZsLzr(bdcZsDOList, bdcLzrDOS);
                        }
                    } else {
                        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(xmid);
                        hxZsLzr(bdcZsDOList, bdcLzrDOS);
                    }
                }
            } else {
                for (BdcLzrDO bdcLzrDO : bdcLzrDOS) {
                    if (StringUtils.isNotBlank(bdcLzrDO.getQlrid())) {
                        BdcQlrDO bdcQlrDO = bdcQlrService.queryBdcQlrByQlrid(bdcLzrDO.getQlrid());
                        if (Objects.nonNull(bdcQlrDO) && StringUtils.isNotBlank(bdcQlrDO.getZsid())) {
                            BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(bdcQlrDO.getZsid());
                            if (Objects.nonNull(bdcZsDO)) {
                                if (sfdbz) {
                                    hxZsLzr(Collections.singletonList(bdcZsDO), bdcLzrDOS);
                                    break;
                                } else {
                                    hxZsLzr(Collections.singletonList(bdcZsDO), Collections.singletonList(bdcLzrDO));
                                }
                            }
                        }
                    }
                }
            }
        } else {
            //没查到领证人且生成证书了，更新证书数据为空
            List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                if (CollectionUtils.size(bdcZsDOList) > 500) {
                    List<List> zsPzrtList = ListUtils.subList(bdcZsDOList, 500);
                    for (List zsList : zsPzrtList) {
                        List<BdcZsDO> zsDOList = (List<BdcZsDO>) zsList;
                        for (BdcZsDO bdcZsDO : zsDOList) {
                            bdcZsDO.setLzr("");
                            bdcZsDO.setLzrzjzl(null);
                            bdcZsDO.setLzrzjh("");
                            bdcZsDO.setLzrdh("");
                            bdcZsFeignService.updateBdcZs(bdcZsDO);
                        }
                    }
                } else {
                    for (BdcZsDO bdcZsDO : bdcZsDOList) {
                        bdcZsDO.setLzr("");
                        bdcZsDO.setLzrzjzl(null);
                        bdcZsDO.setLzrzjh("");
                        bdcZsDO.setLzrdh("");
                        bdcZsFeignService.updateBdcZs(bdcZsDO);
                    }
                }

            }
        }
    }

    private void hxZsLzr(List<BdcZsDO> bdcZsDOList, List<BdcLzrDO> bdcLzrDOS) {
        if (CollectionUtils.isNotEmpty(bdcZsDOList) && CollectionUtils.isNotEmpty(bdcLzrDOS)) {
            if (CollectionUtils.size(bdcZsDOList) > 500) {
                List<List> zsPzrtList = ListUtils.subList(bdcZsDOList, 500);
                for (List zsList : zsPzrtList) {
                    List<BdcZsDO> zsDOList = (List<BdcZsDO>) zsList;
                    for (BdcZsDO bdcZsDO : zsDOList) {
                        bdcZsDO.setLzr(StringToolUtils.resolveBeanToAppendStr(bdcLzrDOS, "getLzrmc", CommonConstantUtils.ZF_YW_DH));
                        bdcZsDO.setLzrzjzl(bdcLzrDOS.get(0).getLzrzjzl());
                        bdcZsDO.setLzrzjh(StringToolUtils.resolveBeanToAppendStr(bdcLzrDOS, "getLzrzjh", CommonConstantUtils.ZF_YW_DH));
                        bdcZsDO.setLzrdh(StringToolUtils.resolveBeanToAppendStr(bdcLzrDOS, "getLzrdh", CommonConstantUtils.ZF_YW_DH));
                        bdcZsFeignService.updateBdcZs(bdcZsDO);
                    }
                }
            } else {
                for (BdcZsDO bdcZsDO : bdcZsDOList) {
                    bdcZsDO.setLzr(StringToolUtils.resolveBeanToAppendStr(bdcLzrDOS, "getLzrmc", CommonConstantUtils.ZF_YW_DH));
                    bdcZsDO.setLzrzjzl(bdcLzrDOS.get(0).getLzrzjzl());
                    bdcZsDO.setLzrzjh(StringToolUtils.resolveBeanToAppendStr(bdcLzrDOS, "getLzrzjh", CommonConstantUtils.ZF_YW_DH));
                    bdcZsDO.setLzrdh(StringToolUtils.resolveBeanToAppendStr(bdcLzrDOS, "getLzrdh", CommonConstantUtils.ZF_YW_DH));
                    bdcZsFeignService.updateBdcZs(bdcZsDO);
                }
            }
        }
    }

}
