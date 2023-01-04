package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlrXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxPlDTO;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcQlrGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.mapper.BdcRyzdMapper;
import cn.gtmap.realestate.register.core.service.BdcQlService;
import cn.gtmap.realestate.register.core.service.BdcQlrService;
import cn.gtmap.realestate.register.core.service.impl.BdcCfServiceImpl;
import cn.gtmap.realestate.register.service.BdcQlxxService;
import cn.gtmap.realestate.register.service.BdcRyzdService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/12
 * @description 冗余字段处理逻辑类
 */
@Service
public class BdcRyzdServiceImpl implements BdcRyzdService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcRyzdServiceImpl.class);

    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = BdcRyzdServiceImpl.class.getName();

    /**
     * ORM操作
     */
    @Autowired
    private EntityMapper entityMapper;

    /**
     * 字典
     */
    @Autowired
    private BdcZdCache bdcZdCache;

    /**
     * 权利类型接口调用
     */
    @Autowired
    private BdcQllxFeignService qllxFeignService;

    /**
     * 权利人、义务人接口调用
     */
    @Autowired
    private BdcQlrFeignService qlrFeignService;

    /**
     * 证书接口调用
     */
    @Autowired
    private BdcZsFeignService zsFeignService;
    /**
     * 权利接口实现类集合
     */
    @Autowired
    private Set<BdcQlService> bdcQlServices;

    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    BdcQlrService bdcQlrService;
    @Autowired
    BdcQlxxService bdcQlxxService;
    @Autowired
    BdcCfServiceImpl bdcCfService;
    @Autowired
    BdcRyzdMapper bdcRyzdMapper;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;


    /**
     * @param xmid 不动产项目ID
     * @return {BdcRyzdDTO} 冗余字段
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取冗余字段对应的值
     */
    @Override
    public BdcRyzdDTO getRyzd(String xmid) {
        LOGGER.debug("{}：开始获取冗余字段，对应项目ID：{}", CLASS_NAME, xmid);

        // 冗余字段实体，每个信息内容传对象设值
        BdcRyzdDTO bdcRyzdDTO = new BdcRyzdDTO();

        // 获取项目信息相关的冗余字段
        this.setXmxx(bdcRyzdDTO, xmid);
        // 获取权利人相关冗余字段（包含证书信息）
        this.setQlrxx(bdcRyzdDTO, CommonConstantUtils.QLRLB_QLR);
        // 获取义务人相关冗余字段
        this.setQlrxx(bdcRyzdDTO, CommonConstantUtils.QLRLB_YWR);
        // 获取权利信息相关冗余字段
        this.setQlxx(bdcRyzdDTO);

        LOGGER.debug("{}：结束获取冗余字段，对应项目ID：{}", CLASS_NAME, xmid);
        return bdcRyzdDTO;
    }

    /**
     * @param xmid         项目ID
     * @param qlrlb
     * @param bdcQlrDOList
     * @return {BdcRyzdDTO} 冗余字段
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权利人冗余字段
     */
    @Override
    public BdcRyzdDTO getRyzdQlr(String xmid, Integer qlrlb, List<BdcQlrDO> bdcQlrDOList) {
        // 冗余字段实体，每个信息内容传对象设值
        BdcRyzdDTO bdcRyzdDTO = new BdcRyzdDTO();
        bdcRyzdDTO.setXmid(xmid);
        bdcRyzdDTO.setQlrlb(qlrlb);
        // 获取权利人义务人相关冗余字段
        this.setQlrYwrxx(bdcRyzdDTO, bdcQlrDOList);
        return bdcRyzdDTO;
    }

    /**
     * @param bdcRyzdDTO 冗余字段
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID关联处理不动产项目中存在的冗余字段
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRyzd(BdcRyzdDTO bdcRyzdDTO) {
        if (null == bdcRyzdDTO || StringUtils.isBlank(bdcRyzdDTO.getXmid())) {
            LOGGER.info("{}：处理冗余字段中止，原因：无冗余字段、参数项目ID为空！", CLASS_NAME);
            return;
        }

        LOGGER.debug("{}：开始更新冗余字段，对应项目ID：{}", CLASS_NAME, bdcRyzdDTO.getXmid());

        // 更新不动产项目表
        bdcXmxxService.updateBdcXmQlrxx(bdcRyzdDTO);
        // 更新权利人表
        bdcQlrService.updateQlrRyzd(bdcRyzdDTO);

        LOGGER.debug("{}：完成冗余字段更新，对应项目ID：{}", CLASS_NAME, bdcRyzdDTO.getXmid());
    }

    /**
     * @param xmid       项目ID
     * @param listBdcqzh 项目生成的所有证号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取需要更新的证号信息
     */
    @Override
    public BdcRyzdDTO getRyzdBdcqzh(String xmid, List<BdcBdcqzhDTO> listBdcqzh) {
        if (StringUtils.isBlank(xmid) || CollectionUtils.isEmpty(listBdcqzh)) {
            LOGGER.info("{}：获取证号冗余字段！项目ID：{},证号信息：{}", CLASS_NAME, xmid, listBdcqzh);
        }
        // 冗余字段实体，每个信息内容传对象设值
        BdcRyzdDTO bdcRyzdDTO = new BdcRyzdDTO();
        bdcRyzdDTO.setXmid(xmid);

        Example example = new Example(BdcQlrDO.class);
        example.createCriteria().andEqualTo("xmid", xmid).andEqualTo("qlrlb", CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isNotEmpty(bdcQlrList)) {
            this.getZsxx(bdcRyzdDTO, bdcQlrList, listBdcqzh);
        }

        return bdcRyzdDTO;
    }

    /**
     * @param bdcRyzd    冗余字段DTO
     * @param bdcQlrList 权利人信息
     * @param listBdcqzh 项目生成的证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 已知项目证书信息，权利人信息，生成冗余字段值
     */
    private void getZsxx(BdcRyzdDTO bdcRyzd, List<BdcQlrDO> bdcQlrList, List<BdcBdcqzhDTO> listBdcqzh) {
        // 缓存权利人ID对应不动产权证号
        Map<String, Object> bdcqzhMap = new HashMap<>(bdcQlrList.size());

        // 1、获取权利人对应证号key-value集合
        for (BdcQlrDO bdcQlrDO : bdcQlrList) {
            String zsid = bdcQlrDO.getZsid();
            for (BdcBdcqzhDTO bdcBdcqzhDTO : listBdcqzh) {
                if (StringUtils.isNotBlank(zsid) && StringUtils.equals(bdcBdcqzhDTO.getZsid(), zsid)) {
                    bdcqzhMap.put(bdcQlrDO.getQlrid(), bdcBdcqzhDTO.getBdcqzh());
                    break;
                }
            }
        }
        bdcRyzd.setBdcqzh(bdcqzhMap);

        // 2、获取拼接的不动产权证号
        StringBuilder bdcqzhBuilder = new StringBuilder();
        for (BdcBdcqzhDTO bdcqzhDTO : listBdcqzh) {
            bdcqzhBuilder.append(bdcqzhDTO.getBdcqzh()).append(",");
        }
        String bdcqzhStr = bdcqzhBuilder.toString();
        if (StringUtils.isNotBlank(bdcqzhStr)) {
            bdcRyzd.setBdcqzhs(bdcqzhStr.substring(0, bdcqzhStr.length() - 1));
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcqzhMap 项目、证号对应信息集合
     * @description 更新流程对应项目、权利人证号冗余字段
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRyzdBdcqzh(Map<String, List<BdcBdcqzhDTO>> bdcqzhMap) {
        if (MapUtils.isEmpty(bdcqzhMap)) {
            LOGGER.warn("操作参数listBdcqzhMap没有数据，处理冗余字段bdcqzh中止！");
            return;
        }

        // 更新项目表的不动产权证号
        BdcXmDO bdcXmDO = new BdcXmDO();
        for(Map.Entry<String, List<BdcBdcqzhDTO>> entry : bdcqzhMap.entrySet()){
            String xmid = entry.getKey();
            List<BdcBdcqzhDTO> zhxx = entry.getValue();
            if(StringUtils.isBlank(xmid) || CollectionUtils.isEmpty(zhxx)){
                continue;
            }

            String xmBdcqzh = zhxx.stream().map(BdcBdcqzhDTO::getBdcqzh).collect(Collectors.joining(","));
            bdcXmDO.setXmid(xmid);
            bdcXmDO.setBdcqzh(xmBdcqzh);
            entityMapper.updateByPrimaryKeySelective(bdcXmDO);
        }

        // 更新权利人表的不动产权证号
        BdcXmDO xmxx = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcXmDO.getXmid());
        if(null != xmxx && StringUtils.isNotBlank(xmxx.getGzlslid())){
            bdcRyzdMapper.updateBdcQlrBdcqzh(xmxx.getGzlslid());
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID关联处理不动产项目中存在的冗余字段
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRyzdQlrByXmid(BdcRyzdDTO bdcRyzdDTO) {
        if (null == bdcRyzdDTO || StringUtils.isBlank(bdcRyzdDTO.getXmid())) {
            LOGGER.info("{}：处理证号冗余字段中止，原因：无冗余字段、参数项目ID为空！", CLASS_NAME);
            return;
        }
        // 更新项目表
        bdcXmxxService.updateBdcXmQlrxx(bdcRyzdDTO);
        //更新权利表
        bdcQlxxService.updateBdcQlRyzdQlr(bdcRyzdDTO);

    }

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新共有情况字段信息（权利人表和各个权利表）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGyqk(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失参数xmid");
        }
        BdcGyqkDTO bdcGyqkDTO = generateGyqk(xmid, null);
        // 更新权利人的共有情况
        bdcQlrService.updateQlrGyqk(bdcGyqkDTO);

        // 更新义务人gyqk
        BdcGyqkDTO ywrbdcGyqkDTO = generateGyqkYwr(xmid, null,CommonConstantUtils.QLRLB_YWR);
        // 更新权利人的共有情况
        bdcQlrService.updateQlrGyqk(ywrbdcGyqkDTO);

        // 更新权利的共有情况
        bdcQlxxService.updateQlGyqk(xmid, bdcGyqkDTO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 首次登记，批量更新唯一权利人等值
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRyzdPl(String gzlslid, String xmid) {
        // 获取权利人义务人信息
        BdcRyzdDTO bdcRyzdDTO = getRyzdQlr(xmid, null, null);
        bdcXmxxService.updateBdcXmQlrxxPl(bdcRyzdDTO, gzlslid);
        // 获取共有情况
        BdcGyqkDTO bdcGyqkDTO = generateGyqk(xmid, null);
        bdcGyqkDTO.setXmid(xmid);
        bdcGyqkDTO.setGzlslid(gzlslid);
        bdcQlrService.updateQlrGyqkPl(bdcGyqkDTO);
        bdcQlxxService.updateQlGyqkPl(bdcGyqkDTO);
    }

    /**
     * @param bdcQlrXmDTOList 权利人项目DTO对象List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权人相关冗余字段
     */
    @Override
    public void updateRyzdQlrXm(List<BdcQlrXmDTO> bdcQlrXmDTOList) {
        if (CollectionUtils.isEmpty(bdcQlrXmDTOList)) {
            LOGGER.info("缺失权利人项目对应对象信息!");
            return;
        }
        for (BdcQlrXmDTO bdcQlrXmDTO : bdcQlrXmDTOList) {
            if (CollectionUtils.isEmpty(bdcQlrXmDTO.getXmidList()) || null == bdcQlrXmDTO.getQlrlb()) {
                LOGGER.info("缺失项目信息!或缺失权利人类别!");
                continue;
            }

            String xmid = bdcQlrXmDTO.getXmidList().get(0);
            if (StringUtils.isBlank(xmid)) {
                throw new MissingArgumentException("入参中的xmid有空值！");
            }
            Integer qlrlb = bdcQlrXmDTO.getQlrlb();

            // 权利人信息为空时，取任意一个xmid查询权利人信息
            List<BdcQlrDO> bdcQlrDOList;
            if (CollectionUtils.isEmpty(bdcQlrXmDTO.getBdcQlrDOList())) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(Integer.toString(bdcQlrXmDTO.getQlrlb()));

                bdcQlrDOList = qlrFeignService.listBdcQlr(bdcQlrQO);
            } else {
                bdcQlrDOList = bdcQlrXmDTO.getBdcQlrDOList();
            }

            // 获取权利人义务人信息
            BdcRyzdDTO bdcRyzdDTO = getRyzdQlr(xmid, qlrlb, bdcQlrDOList);
            // 设置要更新的项目ID
            bdcRyzdDTO.setXmidList(bdcQlrXmDTO.getXmidList());
            bdcXmxxService.updateBdcXmQlrxxPl(bdcRyzdDTO, null);
            bdcQlxxService.updateBdcQlRyzdQlr(bdcRyzdDTO);

            // 权利人，获取共有情况并更新
            if (CommonConstantUtils.QLRLB_QLR_DM.equals(qlrlb)) {
                BdcGyqkDTO bdcGyqkDTO = generateGyqk(xmid, bdcQlrDOList);
                // gzlslid和xmidList 二选一
                bdcGyqkDTO.setGzlslid(null);
                bdcGyqkDTO.setXmid(xmid);
                bdcGyqkDTO.setXmidList(bdcQlrXmDTO.getXmidList());
                bdcQlrService.updateQlrGyqkPl(bdcGyqkDTO);
                bdcQlxxService.updateQlGyqkPl(bdcGyqkDTO);
            }
        }
    }

    @Override
    public void updateSyqx(String xmid){
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失参数xmid");
        }
        BdcSyqxDTO bdcSyqxDTO = bdcQlxxService.generateSyqx(xmid);
        if(bdcSyqxDTO != null &&Boolean.TRUE.equals(bdcSyqxDTO.getUpdate())) {
            BdcXmFbDO bdcXmFb = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, xmid);
            if (bdcXmFb != null) {
                bdcXmFb.setSyqx(bdcSyqxDTO.getSyqx());
                entityMapper.updateByPrimaryKeySelective(bdcXmFb);
            }
        }

    }

    @Override
    public void updateSyqxPl(@RequestBody BdcSyqxPlDTO bdcSyqxPlDTO) throws Exception{
        if(bdcSyqxPlDTO.getJsonObject() != null &&(StringUtils.isNotBlank(bdcSyqxPlDTO.getGzlslid()) ||CollectionUtils.isNotEmpty(bdcSyqxPlDTO.getXmidList()))){
            BdcSyqxDTO bdcSyqxDTO = bdcQlxxService.generateSyqxPl(bdcSyqxPlDTO);
            if(bdcSyqxDTO != null &&Boolean.TRUE.equals(bdcSyqxDTO.getUpdate())) {
                BdcDjxxUpdateQO bdcDjxxUpdateQO =new BdcDjxxUpdateQO();
                JSONObject jsonObject =new JSONObject();
                jsonObject.put("syqx",bdcSyqxDTO.getSyqx());
                bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(jsonObject));
                Map whereMap = new HashMap<>();
                whereMap.put("gzlslid",bdcSyqxPlDTO.getGzlslid());
                whereMap.put("xmids",bdcSyqxPlDTO.getXmidList());
                bdcDjxxUpdateQO.setWhereMap(whereMap);
                bdcXmfbFeignService.updateBatchBdcxmFb(bdcDjxxUpdateQO);
            }
        }

    }

    /**
     * @param xmid 项目ID
     * @param bdcQlrDOList
     * @return BdcGyqkDTO 生成共有情况信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成共有情况(假设存在共同共有和按份共有同时存在的情况)
     */
    private BdcGyqkDTO generateGyqk(String xmid, List<BdcQlrDO> bdcQlrDOList) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失参数xmid");
        }
        BdcGyqkDTO bdcGyqkDTO = new BdcGyqkDTO();

        List<BdcQlrDO> bdcQlrList;
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrList = qlrFeignService.listBdcQlr(bdcQlrQO);
        } else {
            bdcQlrList = bdcQlrDOList;
        }
        if (CollectionUtils.isEmpty(bdcQlrList)) {
            LOGGER.info("{}：处理共有情况中断，原因：未查询到权利人信息！", CLASS_NAME);
            return bdcGyqkDTO;
        }

        BdcQlrGyqkDTO bdcQlrGyqkDTO = queryBdcQlrGyqk(bdcQlrList);

        if (null != bdcQlrGyqkDTO && null != bdcQlrGyqkDTO.getBdcDdsyQlr()) {
            String ddsyMsg = "单独所有";
            // 为权利人共有情况赋值
            Map map = new HashMap();
            map.put(bdcQlrGyqkDTO.getBdcDdsyQlr().getQlrid(), ddsyMsg);
            List mapList = new ArrayList();
            mapList.add(map);
            bdcGyqkDTO.setQlrGyqk(mapList);
            // 为权利共有情况赋值
            bdcGyqkDTO.setQlGyqk(ddsyMsg);
            return bdcGyqkDTO;
        }

        // 权利表需要保存的共有情况结果
        String qlGyqk = generateQlGyqk(bdcQlrGyqkDTO);
        bdcGyqkDTO.setQlGyqk(qlGyqk);

        // 权利人表需要保存的共有情况结果
        List qlrGyqk = new ArrayList();
        for (BdcQlrDO bdcQlrDO : bdcQlrList) {
            String qlrGyqkStr = "";
            // 权利人是共同共有
            if (CommonConstantUtils.GYFS_GTGY.equals(bdcQlrDO.getGyfs())) {
                qlrGyqkStr = generateGtgyQlrGyqk(bdcQlrDO, bdcQlrGyqkDTO);
            }
            // 权利人是按份共有
            if (CommonConstantUtils.GYFS_AFGY.equals(bdcQlrDO.getGyfs())) {
                qlrGyqkStr = generateAfgyQlrGyqk(bdcQlrDO, bdcQlrGyqkDTO);
            }
            // 权利人是其它共有
            if (CommonConstantUtils.GYFS_QTGY.equals(bdcQlrDO.getGyfs())) {
                qlrGyqkStr = generateQtgyQlrGyqk(bdcQlrDO, bdcQlrGyqkDTO);
            }
            Map map = new HashMap();
            map.put(bdcQlrDO.getQlrid(), qlrGyqkStr);
            qlrGyqk.add(map);
        }
        bdcGyqkDTO.setQlrGyqk(qlrGyqk);

        return bdcGyqkDTO;
    }

    /**
     * @param bdcQlrGyqkDTO 权利人共有情况
     * @return String 权利共有情况
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成权利共有情况
     */
    private String generateQlGyqk(BdcQlrGyqkDTO bdcQlrGyqkDTO) {
        String qlGyqk = "";
        List<BdcQlrDO> bdcQtgyQlrList = bdcQlrGyqkDTO.getBdcQtgyQlrList();
        List<BdcQlrDO> bdcGtgyQlrList = bdcQlrGyqkDTO.getBdcGtgyQlrList();
        List<BdcQlrDO> bdcAfgyQlrList = bdcQlrGyqkDTO.getBdcAfgyQlrList();
        qlGyqk = qlrGtgyqk(qlGyqk, null, bdcGtgyQlrList);
        qlGyqk = qlrAfgyqk(qlGyqk, null, bdcAfgyQlrList);
        qlGyqk = qlrQtgyqk(qlGyqk, null, bdcQtgyQlrList);
        return qlGyqk;
    }

    /**
     * @param bdcQlrDO      不动产权利人
     * @param bdcQlrGyqkDTO 权利人共有情况
     * @return String 权利人共有情况
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 当前权利人是其它共有方式时，生成共有情况
     */
    private String generateQtgyQlrGyqk(BdcQlrDO bdcQlrDO, BdcQlrGyqkDTO bdcQlrGyqkDTO) {
        String qlrGyqk = "";
        List<BdcQlrDO> bdcQtgyQlrList = bdcQlrGyqkDTO.getBdcQtgyQlrList();
        List<BdcQlrDO> bdcGtgyQlrList = bdcQlrGyqkDTO.getBdcGtgyQlrList();
        List<BdcQlrDO> bdcAfgyQlrList = bdcQlrGyqkDTO.getBdcAfgyQlrList();
        qlrGyqk = qlrQtgyqk(qlrGyqk, bdcQlrDO, bdcQtgyQlrList);
        qlrGyqk = qlrGtgyqk(qlrGyqk, null, bdcGtgyQlrList);
        qlrGyqk = qlrAfgyqk(qlrGyqk, null, bdcAfgyQlrList);
        return qlrGyqk;
    }

    /**
     * @param bdcQlrDO      当前权利人
     * @param bdcQlrGyqkDTO 权利人共有情况
     * @return String 权利人共有情况
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 当前权利人是按份共有时，生成共有情况
     */
    private String generateAfgyQlrGyqk(BdcQlrDO bdcQlrDO, BdcQlrGyqkDTO bdcQlrGyqkDTO) {
        String qlrGyqk = "";
        List<BdcQlrDO> bdcQtgyQlrList = bdcQlrGyqkDTO.getBdcQtgyQlrList();
        List<BdcQlrDO> bdcGtgyQlrList = bdcQlrGyqkDTO.getBdcGtgyQlrList();
        List<BdcQlrDO> bdcAfgyQlrList = bdcQlrGyqkDTO.getBdcAfgyQlrList();
        qlrGyqk = qlrAfgyqk(qlrGyqk, bdcQlrDO, bdcAfgyQlrList);
        qlrGyqk = qlrQtgyqk(qlrGyqk, null, bdcQtgyQlrList);
        qlrGyqk = qlrGtgyqk(qlrGyqk, null, bdcGtgyQlrList);
        return qlrGyqk;
    }

    /**
     * @param bdcQlrDO      当前权利人
     * @param bdcQlrGyqkDTO 权利人共有情况
     * @return String 权利人共有情况
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 当前权利人是共同共有时，生成共有情况
     */
    private String generateGtgyQlrGyqk(BdcQlrDO bdcQlrDO, BdcQlrGyqkDTO bdcQlrGyqkDTO) {
        String qlrGyqk = "";
        List<BdcQlrDO> bdcQtgyQlrList = bdcQlrGyqkDTO.getBdcQtgyQlrList();
        List<BdcQlrDO> bdcGtgyQlrList = bdcQlrGyqkDTO.getBdcGtgyQlrList();
        List<BdcQlrDO> bdcAfgyQlrList = bdcQlrGyqkDTO.getBdcAfgyQlrList();
        qlrGyqk = qlrGtgyqk(qlrGyqk, bdcQlrDO, bdcGtgyQlrList);
        qlrGyqk = qlrAfgyqk(qlrGyqk, null, bdcAfgyQlrList);
        qlrGyqk = qlrQtgyqk(qlrGyqk, null, bdcQtgyQlrList);
        return qlrGyqk;
    }

    /**
     * @param bdcQlrList 权利人信息List
     * @return BdcQlrGyqkDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前项目权利人共有情况信息
     */
    private BdcQlrGyqkDTO queryBdcQlrGyqk(List<BdcQlrDO> bdcQlrList) {
        BdcQlrGyqkDTO bdcQlrGyqkDTO = new BdcQlrGyqkDTO();
        if (CollectionUtils.isNotEmpty(bdcQlrList)) {
            // 一个权利人，单独所有的情况
            if (CollectionUtils.size(bdcQlrList) == 1 && CommonConstantUtils.GYFS_DDSY.equals(bdcQlrList.get(0).getGyfs())) {
                bdcQlrGyqkDTO.setBdcDdsyQlr(bdcQlrList.get(0));
                return bdcQlrGyqkDTO;
            }

            // 多个权利人的情况
            // 共同共有
            List<BdcQlrDO> bdcGtgyQlrList = new ArrayList();
            // 按份共有
            List<BdcQlrDO> bdcAfgyQlrList = new ArrayList();
            // 其它共有
            List<BdcQlrDO> bdcQtgyQlrList = new ArrayList();
            for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                if (CommonConstantUtils.GYFS_GTGY.equals(bdcQlrDO.getGyfs())) {
                    bdcGtgyQlrList.add(bdcQlrDO);
                }
                if (CommonConstantUtils.GYFS_AFGY.equals(bdcQlrDO.getGyfs())) {
                    bdcAfgyQlrList.add(bdcQlrDO);
                }
                if (CommonConstantUtils.GYFS_QTGY.equals(bdcQlrDO.getGyfs())) {
                    bdcQtgyQlrList.add(bdcQlrDO);
                }
            }
            bdcQlrGyqkDTO.setBdcGtgyQlrList(bdcGtgyQlrList);
            bdcQlrGyqkDTO.setBdcAfgyQlrList(bdcAfgyQlrList);
            bdcQlrGyqkDTO.setBdcQtgyQlrList(bdcQtgyQlrList);
        }
        return bdcQlrGyqkDTO;
    }

    /**
     * @param qlrGyqk        权利人共有情况内容
     * @param bdcQlrDO       当前权利人信息
     * @param bdcAfgyQlrList 按份共有的权利人信息
     * @return 生成权利人按份共有情况信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 权利人按份共有情况
     */
    private String qlrAfgyqk(String qlrGyqk, BdcQlrDO bdcQlrDO, List<BdcQlrDO> bdcAfgyQlrList) {
        if (CollectionUtils.isNotEmpty(bdcAfgyQlrList)) {
            StringBuilder qlrGyqkBuilder = new StringBuilder();
            qlrGyqkBuilder.append("按份共有：");
            for (BdcQlrDO bdcAfgyQlr : bdcAfgyQlrList) {
                if (null == bdcQlrDO || !StringUtils.equals(bdcQlrDO.getQlrid(), bdcAfgyQlr.getQlrid())) {
                    qlrGyqkBuilder.append(bdcAfgyQlr.getQlrmc()).append(':').append(bdcAfgyQlr.getQlbl()).append(',');
                }
            }
            qlrGyqk += qlrGyqkBuilder.toString().substring(0, qlrGyqkBuilder.toString().length() - 1) + '。';
        }
        return qlrGyqk;
    }

    /**
     * @param qlrGyqk        权利人共有情况信息
     * @param bdcQlrDO       当前权利人信息
     * @param bdcGtgyQlrList 共同共有的权利人信息
     * @return qlrGyqk  权利人共同共有情况
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成权利人共同共有情况信息
     */
    private String qlrGtgyqk(String qlrGyqk, BdcQlrDO bdcQlrDO, List<BdcQlrDO> bdcGtgyQlrList) {
        if (CollectionUtils.isNotEmpty(bdcGtgyQlrList)) {
            StringBuilder qlrGyqkBuilder = new StringBuilder();
            if (null != bdcQlrDO) {
                qlrGyqkBuilder.append("与");
            }
            for (BdcQlrDO bdcGtgyQlr : bdcGtgyQlrList) {
                if (null == bdcQlrDO || !StringUtils.equals(bdcQlrDO.getQlrid(), bdcGtgyQlr.getQlrid())) {
                    qlrGyqkBuilder.append(bdcGtgyQlr.getQlrmc()).append(',');
                }
            }
            qlrGyqk += qlrGyqkBuilder.toString().substring(0, qlrGyqkBuilder.toString().length() - 1) + "共同共有。";
        }
        return qlrGyqk;
    }

    /**
     * @param qlrGyqk        权利人共有情况信息
     * @param bdcQlrDO       当前权利人信息
     * @param bdcQtgyQlrList 其它共有情况权利人信息
     * @return String 权利人其它共有情况信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成权利人其它共有情况信息
     */
    private String qlrQtgyqk(String qlrGyqk, BdcQlrDO bdcQlrDO, List<BdcQlrDO> bdcQtgyQlrList) {
        if (CollectionUtils.isNotEmpty(bdcQtgyQlrList)) {
            StringBuilder qlrGyqkBuilder = new StringBuilder();
            if (null != bdcQlrDO) {
                qlrGyqkBuilder.append("与");
            }
            for (BdcQlrDO bdcQtgyQlr : bdcQtgyQlrList) {
                if (null == bdcQlrDO || !StringUtils.equals(bdcQlrDO.getQlrid(), bdcQtgyQlr.getQlrid())) {
                    qlrGyqkBuilder.append(bdcQtgyQlr.getQlrmc()).append(',');
                }
            }
            qlrGyqk += qlrGyqkBuilder.toString().substring(0, qlrGyqkBuilder.toString().length() - 1) + "其它共有。";
        }
        return qlrGyqk;
    }

    /**
     * @param bdcRyzdDTO 冗余字段
     * @param xmid       不动产项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取项目信息相关的冗余字段
     */
    private void setXmxx(BdcRyzdDTO bdcRyzdDTO, String xmid) {
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        if (null == bdcXmDO || StringUtils.isBlank(bdcXmDO.getXmid())) {
            LOGGER.warn("{}：获取{}对应的项目信息为空！", CLASS_NAME, xmid);
            // 没有对应的项目，无需后续处理
            throw new EntityNotFoundException("项目不存在，结束冗余字段处理！");
        }

        // 项目ID
        bdcRyzdDTO.setXmid(xmid);
        // 不动产单元号
        bdcRyzdDTO.setBdcdyh(bdcXmDO.getBdcdyh());
        // 坐落
        bdcRyzdDTO.setZl(bdcXmDO.getZl());
        // 权利类型
        bdcRyzdDTO.setQllx(bdcXmDO.getQllx());
        // 登记类型
        bdcRyzdDTO.setDjlx(bdcXmDO.getDjlx());
    }

    /**
     * @param bdcRyzd 冗余字段
     * @param qlrlb   权利人类别
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID获取对应项目的权利人或者义务人相关信息
     */
    private void setQlrxx(BdcRyzdDTO bdcRyzd, String qlrlb) {
        if (StringUtils.isAnyBlank(bdcRyzd.getXmid(), qlrlb)) {
            throw new MissingArgumentException("缺失参数xmid和qlrlb");
        }
        // 获取权利人或者义务人信息
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(bdcRyzd.getXmid());
        bdcQlrQO.setQlrlb(qlrlb);
        List<BdcQlrDO> bdcQlrList = qlrFeignService.listBdcQlr(bdcQlrQO);

        if (CollectionUtils.isEmpty(bdcQlrList)) {
            LOGGER.warn("{}：获取项目对应的权利人或者义务人为空，对应项目ID：{}", CLASS_NAME, bdcRyzd.getXmid());
            return;
        }

        // 获取权利人、义务人基本信息
        this.getQlrJbxx(bdcRyzd, qlrlb, bdcQlrList);

        // 如果是权利人，执行获取证书信息
        if (CommonConstantUtils.QLRLB_QLR.equals(qlrlb)) {
            this.getZsxx(bdcRyzd, bdcQlrList);
        }
    }

    /**
     * @param bdcRyzd      冗余字段
     * @param bdcQlrDOList
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID获取对应项目的权利人或者义务人相关信息
     */
    private void setQlrYwrxx(BdcRyzdDTO bdcRyzd, List<BdcQlrDO> bdcQlrDOList) {
        // 获取权利人或者义务人信息
        List<BdcQlrDO> bdcQlrList;
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            if (StringUtils.isNotBlank(bdcRyzd.getXmid())) {
                bdcQlrQO.setXmid(bdcRyzd.getXmid());
            }
            if (null != bdcRyzd.getQlrlb()) {
                bdcQlrQO.setQlrlb(Integer.toString(bdcRyzd.getQlrlb()));
            }
            bdcQlrList = qlrFeignService.listBdcQlr(bdcQlrQO);
        } else {
            bdcQlrList = bdcQlrDOList;
        }
        if (CollectionUtils.isEmpty(bdcQlrList)) {
            LOGGER.warn("{}：获取项目对应的权利人或者义务人为空，对应项目ID：{}", CLASS_NAME, bdcRyzd.getXmid());
            return;
        }

        List<BdcQlrDO> qlrList = bdcQlrList.stream().filter(
                bdcQlr -> StringUtils.equals(bdcQlr.getQlrlb(), CommonConstantUtils.QLRLB_QLR)).collect(Collectors.toList());
        List<BdcQlrDO> ywrList = bdcQlrList.stream().filter(
                bdcQlr -> StringUtils.equals(bdcQlr.getQlrlb(), CommonConstantUtils.QLRLB_YWR)).collect(Collectors.toList());

        // 获取权利人、义务人基本信息
        this.getQlrJbxx(bdcRyzd, CommonConstantUtils.QLRLB_QLR, qlrList);
        this.getQlrJbxx(bdcRyzd, CommonConstantUtils.QLRLB_YWR, ywrList);
    }

    /**
     * @param bdcRyzd    冗余字段
     * @param qlrlb      权利人类别
     * @param bdcQlrList 权利人或者义务人集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权利人或者义务人基本信息
     */
    private void getQlrJbxx(BdcRyzdDTO bdcRyzd, String qlrlb, List<BdcQlrDO> bdcQlrList) {
        // 权利人/义务人名称
        String qlr = StringToolUtils.resolveBeanToAppendStr(bdcQlrList, "getQlrmc", ",");
        // 权利人/义务人证件号ID
        String zjh = StringToolUtils.resolveBeanToAppendStr(bdcQlrList, "getZjh", ",");
        // 权利人/义务人证件种类
        String zjzl = StringToolUtils.getZjzlOfZd(bdcQlrList, bdcZdCache.getZdTableList("BDC_ZD_ZJZL", BdcZdZjzlDO.class));

        if (CommonConstantUtils.QLRLB_QLR.equals(qlrlb)) {
            bdcRyzd.setQlr(qlr);
            bdcRyzd.setQlrzjh(zjh);
            bdcRyzd.setQlrzjzl(zjzl);
            // 权利人类型
            bdcRyzd.setQlrlx(StringToolUtils.convertBeanPropertiesValueOfZd(bdcQlrList, "qlrlx",
                    bdcZdCache.getZdTableList("BDC_ZD_QLRLX", BdcZdQlrlxDO.class),""));
            //共有方式
            if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                bdcRyzd.setGyfs(bdcQlrList.get(0).getGyfs());
            } else {
                bdcRyzd.setGyfs(null);
            }
        } else {
            bdcRyzd.setYwr(qlr);
            bdcRyzd.setYwrzjh(zjh);
            bdcRyzd.setYwrzjzl(zjzl);
        }
    }

    /**
     * @param bdcRyzd    冗余字段
     * @param bdcQlrList 权利人集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权利人关联的证书不动产权证号 / 不动产权证明号 <br>
     * <p>
     * 1、根据项目ID获取对应的权利人（可能多个） <br>
     * 2、利用权利人关联的证书ID（ZSID）查询证书 <br>
     * 3、考虑到更新场景需要获取两种类型数据：权利人对应证号key-value、拼接的证号
     * </p>
     */
    private void getZsxx(BdcRyzdDTO bdcRyzd, List<BdcQlrDO> bdcQlrList) {
        // 缓存权利人ID对应不动产权证号
        Map<String, Object> bdcqzhMap = new HashMap<>(bdcQlrList.size());
        // 考虑到权利人共同持证，即证号一致时只需要显示一个证号不需要拼接，所以先用set缓存证号，回头再拼接
        Set<String> bdcqzhSet = new HashSet<>(bdcQlrList.size());

        // 1、获取权利人对应证号key-value集合
        for (BdcQlrDO bdcQlr : bdcQlrList) {
            if (null != bdcQlr && StringUtils.isNotBlank(bdcQlr.getQlrid())) {
                // 根据证书ID查询证书
                BdcZsDO bdcZs = zsFeignService.queryBdcZs(bdcQlr.getZsid());
                if (null == bdcZs || StringUtils.isBlank(bdcZs.getZsid())) {
                    LOGGER.warn("{}：获取权利人对应证书为空，对应权利人ID：{}", CLASS_NAME, bdcQlr.getQlrid());
                    continue;
                }

                bdcqzhMap.put(bdcQlr.getQlrid(), bdcZs.getBdcqzh());
                bdcqzhSet.add(bdcZs.getBdcqzh());
            }
        }
        bdcRyzd.setBdcqzh(bdcqzhMap);

        // 2、获取拼接的不动产权证号
        StringBuilder bdcqzhBuilder = new StringBuilder();
        for (String bdcqzh : bdcqzhSet) {
            bdcqzhBuilder.append(bdcqzh).append(",");
        }
        String bdcqzhStr = bdcqzhBuilder.toString();
        if (StringUtils.isNotBlank(bdcqzhStr)) {
            bdcRyzd.setBdcqzhs(bdcqzhStr.substring(0, bdcqzhStr.length() - 1));
        }
    }

    /**
     * @param bdcRyzd 冗余字段
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权利相关的冗余字段
     */
    private void setQlxx(BdcRyzdDTO bdcRyzd) {
        BdcQl bdcQl = qllxFeignService.queryQlxx(bdcRyzd.getXmid());
        if (null == bdcQl || StringUtils.isBlank(bdcQl.getQlid())) {
            LOGGER.warn("{}：获取项目{}对应的权利为空！", CLASS_NAME, bdcRyzd.getXmid());
            return;
        }

        // 登记机构
        bdcRyzd.setDjjg(bdcQl.getDjjg());
        // 登簿人
        bdcRyzd.setDbr(bdcQl.getDbr());
        // 权属状态
        bdcRyzd.setQszt(bdcQl.getQszt());
    }



    /**
     * @param xmid 项目ID
     * @param bdcQlrDOList
     * @return BdcGyqkDTO 生成共有情况信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成共有情况(假设存在共同共有和按份共有同时存在的情况)
     */
    private BdcGyqkDTO generateGyqkYwr(String xmid, List<BdcQlrDO> bdcQlrDOList,String qlrlb) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失参数xmid");
        }
        BdcGyqkDTO bdcGyqkDTO = new BdcGyqkDTO();

        List<BdcQlrDO> bdcYwrList;
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(qlrlb);
            bdcYwrList = qlrFeignService.listBdcQlr(bdcQlrQO);
        } else {
            bdcYwrList = bdcQlrDOList;
        }
        if (CollectionUtils.isEmpty(bdcYwrList)) {
            LOGGER.info("{}：处理共有情况中断，原因：未查询到义务人信息！", CLASS_NAME);
            return bdcGyqkDTO;
        }

        BdcQlrGyqkDTO bdcYwrGyqkDTO = queryBdcQlrGyqk(bdcYwrList);

        if (null != bdcYwrGyqkDTO && null != bdcYwrGyqkDTO.getBdcDdsyQlr()) {
            String ddsyMsg = "单独所有";
            // 为义务人人共有情况赋值
            Map map = new HashMap();
            map.put(bdcYwrGyqkDTO.getBdcDdsyQlr().getQlrid(), ddsyMsg);
            List mapList = new ArrayList();
            mapList.add(map);
            bdcGyqkDTO.setQlrGyqk(mapList);
            // 为权利共有情况赋值
            bdcGyqkDTO.setQlGyqk(ddsyMsg);
            return bdcGyqkDTO;
        }

        // 权利表需要保存的共有情况结果
        String qlGyqk = generateQlGyqk(bdcYwrGyqkDTO);
        bdcGyqkDTO.setQlGyqk(qlGyqk);

        // 权利人表需要保存的共有情况结果
        List qlrGyqk = new ArrayList();
        for (BdcQlrDO bdcQlrDO : bdcYwrList) {
            String qlrGyqkStr = "";
            // 义务人是共同共有
            if (CommonConstantUtils.GYFS_GTGY.equals(bdcQlrDO.getGyfs())) {
                qlrGyqkStr = generateGtgyQlrGyqk(bdcQlrDO, bdcYwrGyqkDTO);
            }
            // 义务人是按份共有
            if (CommonConstantUtils.GYFS_AFGY.equals(bdcQlrDO.getGyfs())) {
                qlrGyqkStr = generateAfgyQlrGyqk(bdcQlrDO, bdcYwrGyqkDTO);
            }
            // 义务人是其它共有
            if (CommonConstantUtils.GYFS_QTGY.equals(bdcQlrDO.getGyfs())) {
                qlrGyqkStr = generateQtgyQlrGyqk(bdcQlrDO, bdcYwrGyqkDTO);
            }
            Map map = new HashMap();
            map.put(bdcQlrDO.getQlrid(), qlrGyqkStr);
            qlrGyqk.add(map);
        }
        bdcGyqkDTO.setQlrGyqk(qlrGyqk);

        return bdcGyqkDTO;
    }


}
