package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.mapper.BdcZsMapper;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.certificate.*;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsXmAndQlrQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZssdQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHstFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsqdVO;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/2
 * @description 不动产证书业务实现类
 */
@Service
public class BdcZsServiceImpl implements BdcZsService {
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcZsMapper bdcZsMapper;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    ZdFeignService zdFeignService;

    @Autowired
    FwHstFeignService fwHstFeignService;

    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;

    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    BdcZsFeignService bdcZsFeignService;


    @Value("${fzjlListQszt:0,1,2}")
    private String fzjlListQszt;

    /**
     * 共用统一流水号的区划设置
     */
    @Value("${zhgylsh:}")
    private String zhgylsh;

    private static final String BCFJ = "撤销登记生成新的证书记录，证号不变，注销之前旧的证号";

    private static final Logger logger = LoggerFactory.getLogger(BdcZsServiceImpl.class);

    /**
     * @param zsid 证书id
     * @return BdcZsDO 不动产权证
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书
     */
    @Override
    public BdcZsDO queryBdcZs(String zsid) {
        if (StringUtils.isNotBlank(zsid)) {
            Example example = new Example(BdcZsDO.class);
            example.createCriteria().andEqualTo("zsid", zsid);
            List<BdcZsDO> bdcZsList = entityMapper.selectByExampleNotNull(example);
            if (CollectionUtils.isNotEmpty(bdcZsList)) {
                return bdcZsList.get(0);
            }
        }
        return null;
    }

    @Override
    public List<BdcZsDO> queryBdcZss(String zsids) {
        if (StringUtils.isNotBlank(zsids)) {
            Example example = new Example(BdcZsDO.class);
            example.createCriteria().andIn("zsid", Arrays.asList(zsids.split(CommonConstantUtils.ZF_YW_DH)));
            List<BdcZsDO> bdcZsList = entityMapper.selectByExampleNotNull(example);
            if (CollectionUtils.isNotEmpty(bdcZsList)) {
                return bdcZsList;
            }
        }
        return null;
    }

    /**
     * @param xmid 项目id
     * @return List<BdcZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    @Override
    public List<BdcZsDO> queryBdcZsByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Map map = new HashMap();
            map.put("xmid", xmid);
            return bdcZsMapper.listBdcZs(map);
        }
        return new ArrayList();
    }

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    @Override
    public List<BdcZsDO> listBdcZs(BdcZsQO bdcZsQO) {
        if (!CheckParameter.checkAnyParameter(bdcZsQO,"xmid","zsid","gzlslid","bdcdyh","bdcqzh","bdcqzhmh","zl","zljq","zzbs","qlr","qlrzjh","bdcqzhjc")) {
            throw new MissingArgumentException("查询证书缺失必要查询条件" + JSONObject.toJSONString(bdcZsQO));
        }
        Map map = Object2MapUtils.object2MapExceptNull(bdcZsQO);
        return bdcZsMapper.listBdcZs(map);
    }

    /**
     * @param bdcZsDO
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 修改证书信息
     */
    @Override
    public int updateBdcZs(BdcZsDO bdcZsDO) {
        if (StringUtils.isBlank(bdcZsDO.getZsid())) {
            throw new NullPointerException("更新时实体对象BdcZsDO主键zsid不能为空");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcZsDO);
    }

    /**
     * @param bdcFzjlZsDTO 证书DTO对象
     * @return 更新结果
     */
    @Override
    public int updateLzr(BdcFzjlZsDTO bdcFzjlZsDTO) {
        if (CollectionUtils.isNotEmpty(bdcFzjlZsDTO.getZsidList())) {
            Date lzsj = new Date();
            int result = 0;
            for (String zsid : bdcFzjlZsDTO.getZsidList()) {
                BdcZsDO bdcZsDO = new BdcZsDO();
                bdcZsDO.setZsid(zsid);
                bdcZsDO.setLzr(bdcFzjlZsDTO.getLzr());
                bdcZsDO.setLzrzjh(bdcFzjlZsDTO.getLzrzjh());
                bdcZsDO.setLzrdh(bdcFzjlZsDTO.getLzrdh());
                bdcZsDO.setLzrzjzl(bdcFzjlZsDTO.getLzrzjzl());
                bdcZsDO.setLzsj(lzsj);
                result += updateBdcZs(bdcZsDO);
            }
            return result;
        }
        return 0;
    }

    /**
     * @param zsidList     证书IDList
     * @param fzr          发证人信息
     * @param isNullUpdate 只有当发证人为空的时候更新（true则做发证人是否为空的判断，否则直接更新发证信息）
     * @return int 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新发证人，发证时间
     */
    @Override
    public int updateFzr(List<String> zsidList, UserDto fzr, Boolean isNullUpdate) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(zsidList) && Objects.nonNull(fzr)) {
            for (String zsid : zsidList) {
                if (isNullUpdate) {
                    BdcZsDO bdcZsDO = this.queryBdcZs(zsid);
                    if (null != bdcZsDO && StringUtils.isBlank(bdcZsDO.getFzr())) {
                        result = updateZsFzrByPrimaryKeySelective(result, zsid, fzr);
                    }
                } else {
                    result = updateZsFzrByPrimaryKeySelective(result, zsid, fzr);
                }
            }
        }
        return result;
    }

    /**
     * @param result 执行的结果数
     * @param zsid   证书ID
     * @param fzr    发证人信息
     * @return int 执行的结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新发证人信息
     */
    private int updateZsFzrByPrimaryKeySelective(int result, String zsid, UserDto fzr) {
        if (StringUtils.isNotBlank(zsid)) {
            Date fzsj = new Date();
            String fzrName = fzr.getAlias();
            String fzrId = fzr.getId();

            BdcZsDO bdcZsDO = new BdcZsDO();
            bdcZsDO.setZsid(zsid);
            bdcZsDO.setFzrid(fzrId);
            bdcZsDO.setFzr(fzrName);
            bdcZsDO.setFzsj(fzsj);
            result += entityMapper.updateByPrimaryKeySelective(bdcZsDO);
        }
        return result;
    }

    /**
     * @param zsidList 证书idList
     * @param szr      缮证人信息
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新缮证人信息（更新证书表中缮证人为空的数据）
     */
    @Override
    public BdcSzxxVO updateSzr(List<String> zsidList, UserDto szr) {
        if(Objects.isNull(szr)){
            throw new AppException(ErrorCode.MISSING_ARG, "更新缮证人信息失败，未获取到当前缮证人信息。");
        }
        BdcSzxxVO bdcSzxxVO = new BdcSzxxVO();
        if (CollectionUtils.isEmpty(zsidList)) {
            bdcSzxxVO.setExcuteNum(-1);
            return bdcSzxxVO;
        }

        Date szsj = new Date();
        String szrName = szr.getAlias();
        String szrId = szr.getId();
        if(StringUtils.isBlank(szrName) || StringUtils.isBlank(szrId)){
            throw new AppException(ErrorCode.MISSING_ARG, "更新缮证人信息失败，未获取到当前缮证人信息。");
        }

        int result = 0;
        if(CollectionUtils.size(zsidList) > 500) {
            List<List> lists = ListUtils.subList(zsidList, 500);
            for(List subList : lists) {
                // 查询需要更新的数据
                List<String> zsids = bdcZsMapper.queryUpdateZsid(subList);
                if (CollectionUtils.isNotEmpty(zsids)){
                    result = bdcZsMapper.batchUpdateSzr(zsids, szrName, szrId, szsj);
                }
            }
        } else {
            // 查询需要更新的数据
            List<String> zsids = bdcZsMapper.queryUpdateZsid(zsidList);
            if (CollectionUtils.isNotEmpty(zsids)){
                result = bdcZsMapper.batchUpdateSzr(zsids, szrName, szrId, szsj);
            }
        }

        bdcSzxxVO.setSzr(szrName);
        bdcSzxxVO.setSzrq(szsj);
        bdcSzxxVO.setExcuteNum(result);
        return bdcSzxxVO;
    }

    /**
     * @param zsidList 证书idList
     * @param szr      缮证人信息
     * @return int 更新数据量
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 更新缮证人信息为当前用户(不管证书表中是否存在缮证人信息都更新)
     */
    @Override
    public BdcSzxxVO updateSzrByGzlslid(List<String> zsidList, UserDto szr) {
        BdcSzxxVO bdcSzxxVO = new BdcSzxxVO();
        if (CollectionUtils.isEmpty(zsidList)) {
            bdcSzxxVO.setExcuteNum(-1);
            return bdcSzxxVO;
        }
        int result = 0;
        Date szsj = new Date();
        String szrName = szr.getAlias();
        String szrId = szr.getId();

        if(CollectionUtils.size(zsidList) > 500) {
            List<List> lists = ListUtils.subList(zsidList, 500);
            for(List subList : lists) {
                result = bdcZsMapper.batchUpdateSzr(subList,szrName,szrId,szsj);
            }
        } else {
            result = bdcZsMapper.batchUpdateSzr(zsidList,szrName,szrId,szsj);
        }
        bdcSzxxVO.setSzr(szrName);
        bdcSzxxVO.setSzrq(szsj);
        bdcSzxxVO.setExcuteNum(result);
        return bdcSzxxVO;
    }

    /**
     * @param bdcZsQO 证书查询对象
     * @return int 证书数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 条件查询的证书数量
     */
    @Override
    public int countBdcZs(BdcZsQO bdcZsQO) {
        if (null == bdcZsQO) {
            throw new MissingArgumentException("缺失证书查询参数");
        }
        return bdcZsMapper.countBdcZs(bdcZsQO);
    }

    /**
     * @param zsid 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前证书相关的项目信息
     */
    @Override
    public List<BdcXmDO> queryZsXmByZsid(String zsid) {
        return bdcZsMapper.queryZsXmByZsid(zsid);
    }

    /**
     * @param bdcZsQO 查询QO
     * @return List<String> 证书ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据条件查询zsid
     */
    @Override
    public List<String> queryZsid(BdcZsQO bdcZsQO) {
        if (!CheckParameter.checkAnyParameter(bdcZsQO, "xmid", "gzlslid", "zsidList", "gzlslidList")) {
            throw new MissingArgumentException("查询zsid缺失必要查询条件" + JSONObject.toJSONString(bdcZsQO));
        }
        return bdcZsMapper.queryZsid(bdcZsQO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String> 证书ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程所有的证书ID
     */
    @Override
    public List<String> queryGzlZsid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(gzlslid);
            return bdcZsMapper.queryZsid(bdcZsQO);
        }
        return new ArrayList();
    }

    /**
     * @param xmid 项目ID
     * @return List<String> 证书ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前项目的证书ID
     */
    @Override
    public List<String> queryXmZsid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setXmid(xmid);
            return bdcZsMapper.queryZsid(bdcZsQO);
        }
        return new ArrayList();
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid
     * @return List<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程所有的证书类型
     */
    @Override
    public List<String> queryGzlZslx(String gzlslid, String xmid) {
        if (StringUtils.isNotBlank(gzlslid) || StringUtils.isNotBlank(xmid)) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(gzlslid);
            bdcZsQO.setXmid(xmid);
            return bdcZsMapper.queryZslx(bdcZsQO);
        }
        return new ArrayList();
    }

    /**
     * @param bdcZsQO 证书查询QO
     * @return BdcZsQsztDTO 证书状态DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的项目权属状态
     */
    @Override
    public List<BdcZsQsztDTO> queryBdcZsQszt(BdcZsQO bdcZsQO) {
        if (StringUtils.isBlank(bdcZsQO.getGzlslid()) && StringUtils.isBlank(bdcZsQO.getXmid()) && CollectionUtils.isEmpty(bdcZsQO.getZsidList())) {
            throw new MissingArgumentException("缺失工作流实例ID，项目ID，证书IDList！");
        }
        return bdcZsMapper.queryBdcZsQszt(bdcZsQO);
    }

    /**
     * @param bdcZsQO 证书查询对象QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据配置设置要查询的证书权属状态
     */
    @Override
    public void renderZsQszt(BdcZsQO bdcZsQO) {
        // 发证记录页面，需要根据不同的配置，过滤不同状态的证书信息
        if (StringUtils.equals(bdcZsQO.getResourceName(), Constants.RESOURCE_NAME_FZJL_LIST)) {
            List<Integer> integerList = new ArrayList();
            if (StringUtils.isNotBlank(fzjlListQszt)) {
                String[] qsztArr = StringUtils.split(fzjlListQszt, CommonConstantUtils.ZF_YW_DH);
                for (String qszt : qsztArr) {
                    if (StringUtils.isNotBlank(qszt)) {
                        integerList.add(Integer.parseInt(qszt));
                    }
                }
                bdcZsQO.setQsztList(integerList);
            }
        }
    }

    /**
     * @param bdcBdcqzhBO 证号查询实体
     * @return {String} 库中当前最大顺序号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取数据库中当前最大顺序号
     */
    @Override
    public int queryMaxSxh(BdcBdcqzhBO bdcBdcqzhBO) {
        if (null == bdcBdcqzhBO || StringUtils.isBlank(bdcBdcqzhBO.getQxdm())) {
            throw new MissingArgumentException("缺失顺序号查询参数");
        }

        // 判断当前区划在哪个分组，用所在分组区划一起查询
        this.resolveQxdm(bdcBdcqzhBO);
        return bdcZsMapper.queryMaxSxh(bdcBdcqzhBO);
    }

    /**
     * @param bdcBdcqzhBO 证号查询实体
     * @return {Integer}  证号数量
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询指定初始顺序号到最大顺序号之间顺序号
     */
    @Override
    public LinkedHashSet<Integer> querySxh(BdcBdcqzhBO bdcBdcqzhBO) {
        if (null == bdcBdcqzhBO) {
            throw new MissingArgumentException("缺失顺序号查询参数");
        }

        // 判断当前区划在哪个分组，用所在分组区划一起查询
        this.resolveQxdm(bdcBdcqzhBO);
        return bdcZsMapper.querySxh(bdcBdcqzhBO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取当前流程关联的所有证书项目信息
     */
    @Override
    public List<BdcBdcqzhDTO> listBdcZhxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return Collections.emptyList();
        }

        return bdcZsMapper.listBdcZhxx(gzlslid);
    }

    /**
     * @param zsidList 需要更新的证书ID
     * @param dyzt     打印状态
     * @return 更新的数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书的打印状态
     */
    @Override
    public int updateBdcZsDyzt(List zsidList, Integer dyzt) {
        if (null == dyzt) {
            throw new MissingArgumentException("缺失参数：dyzt!");
        }
        if (CollectionUtils.isEmpty(zsidList)) {
            throw new MissingArgumentException("缺失参数zsidList！");
        }
        int result = 0;
        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setDyzt(dyzt);
        List<List> zsidLists = ListUtils.subList(zsidList, 1000);
        for (List zsids : zsidLists) {
            Example example = new Example(BdcZsDO.class);
            example.createCriteria().andIn(Constants.ZSID, zsids);
            result += entityMapper.updateByExampleSelectiveNotNull(bdcZsDO, example);
        }
        return result;
    }

    /**
     * @param bdcZsQO 证书查询QO
     * @return List<BdcZsDO> 证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询原项目的证书
     */
    @Override
    public List<BdcZsDO> listYxmZs(BdcZsQO bdcZsQO) {
        Map map = Object2MapUtils.object2MapExceptNull(bdcZsQO);
        if (MapUtils.isEmpty(map)) {
            return Collections.emptyList();
        }
        return bdcZsMapper.listYxmZs(map);
    }


    /**
     * 查询需要补偿制证的证书id
     *
     * @return
     */
    @Override
    public List<String> listSyncZzZsids() {
        return bdcZsMapper.listSyncZzZsids();
    }

    /**
     * 查询需要补偿制证的存量证书id
     *
     * @return
     */
    @Override
    public List<String> listSyncZzClZsids(Map<String, Object> map) {
        return bdcZsMapper.listSyncZzClZsids(map);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param qllx    权利类型
     * @return {List} 证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询原项目证书信息集合，证书需要按照下一手项目ID排序
     */
    @Override
    public List<BdcZsDO> queryYxmZsSortByXmid(String gzlslid, Integer qllx) {
        if (StringUtils.isBlank(gzlslid)) {
            return Collections.emptyList();
        }

        // 查询证书信息，按照下一手项目ID排序
        List<BdcZsXmidDTO> bdcZsXmidList = bdcZsMapper.queryYxmZs(gzlslid, qllx);
        if (CollectionUtils.isEmpty(bdcZsXmidList)) {
            return Collections.emptyList();
        }

        // 去重，避免一证多房这种查询出来证书重复
        Set<String> zsidSet = new HashSet();
        List<BdcZsDO> bdcZsDOList = new ArrayList<>(bdcZsXmidList.size());
        for (BdcZsXmidDTO bdcZsXmidDTO : bdcZsXmidList) {
            if (null != bdcZsXmidDTO && !zsidSet.contains(bdcZsXmidDTO.getZsid())) {
                zsidSet.add(bdcZsXmidDTO.getZsid());
                BdcZsDO bdcZsDO = new BdcZsDO();
                BeansResolveUtils.clonePropertiesValue(bdcZsXmidDTO, bdcZsDO);
                bdcZsDOList.add(bdcZsDO);
            }
        }
        return bdcZsDOList;
    }

    /**
     * 查询未办结的缮证人名称为指定名称的项目 </br>
     * 针对需求 42397 提供的补偿接口服务
     *
     * @param szrid 缮证人 id
     * @return {List} 证书项目相关信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcZsXmDTO> listWbjywxx(String szrid) {
        if (StringUtils.isBlank(szrid)) {
            return Collections.emptyList();
        }
        return bdcZsMapper.listWbjywxx(szrid);
    }

    /**
     * 平台办结后执行更新事件 </br>
     * 针对需求 42397 提供的补偿接口服务 </br>
     * <p>
     * 1. 更新发证时间
     *
     * @param bdcZsXmDTOS 证书项目集合
     * @return {int} 更新条数
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public int updateWbjxm(List<BdcZsXmDTO> bdcZsXmDTOS) {
        return bdcZsMapper.updateWbjxm(bdcZsXmDTOS);
    }

    @Override
    public String dealZsGyqk(BdcZsDO bdcZsDO, boolean zsyl, String xmid) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        String gyqk = "";
        if (bdcZsDO.getGyfs() != null) {
            gyqk = StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(), zdMap.get(Constants.GYFS));
        }
        Integer sffbcz = CommonConstantUtils.SF_F_DM;
        List<BdcXmDO> listxm;
        List<BdcQlrDO> listqlr = new ArrayList<>();
        if (zsyl) {
            if (StringUtils.isNotBlank(xmid)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(xmid);
                listxm = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(listxm)) {
                    sffbcz = listxm.get(0).getSqfbcz();
                    BdcQlrQO qlrQO = new BdcQlrQO();
                    qlrQO.setXmid(xmid);
                    qlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    if (CommonConstantUtils.SF_S_DM.equals(sffbcz) && StringUtils.isNotBlank(bdcZsDO.getQlr())) {
                        //分别持证,只获取当前证书对应的权利人
                        qlrQO.setQlrmc(bdcZsDO.getQlr());
                    }
                    listqlr = bdcQlrFeignService.listBdcQlr(qlrQO);
                }
            }

        } else {
            listxm = queryZsXmByZsid(bdcZsDO.getZsid());
            if (CollectionUtils.isNotEmpty(listxm)) {
                sffbcz = listxm.get(0).getSqfbcz();
            }
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setZsid(bdcZsDO.getZsid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            listqlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        }

        if (CollectionUtils.isNotEmpty(listqlr)) {
            Integer gyfs = listqlr.get(0).getGyfs();
            // 不是分别持证
            if (CommonConstantUtils.GYFS_GTGY.equals(gyfs)) {
                return "共同共有";
            } else if (CommonConstantUtils.SF_F_DM.equals(sffbcz) && CommonConstantUtils.GYFS_AFGY.equals(gyfs)) {
                StringBuilder gyfsStr = new StringBuilder("");
                for (int i = 0; i < listqlr.size(); i++) {
                    String qlbl = listqlr.get(i).getQlbl();
                    gyfsStr.append("按份共有");
                    gyfsStr.append(" ");
                    gyfsStr.append(listqlr.get(i).getQlrmc());
                    gyfsStr.append(" ");
                    if (StringUtils.isNotBlank(qlbl)) {
                        gyfsStr.append(qlbl);
                    } else {
                        throw new AppException("按份共有权利比例为空！");
                    }
                    gyfsStr.append(" ");
                }
                return gyfsStr.toString();
            } else if (CommonConstantUtils.SF_S_DM.equals(sffbcz) && CommonConstantUtils.GYFS_AFGY.equals(gyfs)) {
                StringBuilder gyfsStr = new StringBuilder("");
                for (int i = 0; i < listqlr.size(); i++) {
                    String qlrmc = listqlr.get(i).getQlrmc();
                    String zsQlrmc = bdcZsDO.getQlr();
                    if (StringUtils.isNotBlank(qlrmc) && StringUtils.isNotBlank(zsQlrmc) && zsQlrmc.equals(qlrmc)) {
                        String qlbl = listqlr.get(i).getQlbl();
                        gyfsStr.append("按份共有");
                        gyfsStr.append(" ");
                        gyfsStr.append(qlrmc);
                        gyfsStr.append(" ");
                        if (StringUtils.isNotBlank(qlbl)) {
                            gyfsStr.append(qlbl);
                        } else {
                            throw new AppException("按份共有权利比例为空！");
                        }
                        gyfsStr.append(" ");
                        break;
                    }
                }
                return gyfsStr.toString();
            }
        }
        return gyqk;
    }

    /**
     * 获取不动产单元号关联的锁定证书信息
     *
     * @param bdcZssdQO 查询实体
     * @return java.util.List<BdcZssdDO> 证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<BdcZssdDO> listBdcZssdxx(BdcZssdQO bdcZssdQO) {
        if (null == bdcZssdQO || StringUtils.isBlank(bdcZssdQO.getBdcdyh())) {
            return Collections.emptyList();
        }

        // 如果锁定状态为空，则默认查询锁定和解锁的所有的
        return bdcZsMapper.listBdcZssdxx(bdcZssdQO);
    }

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证书list 盐城自助打证机
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取不动产权证书列表
     */
    @Override
    public List<Map<String, Object>> listBdcZsForZzdzj(BdcZsQO bdcZsQO) {
        Map<String, Object> queryZsParam = new HashMap<>();
        queryZsParam.put("zslxFlag", true);
        //权利人信息
        if (StringUtils.isNotBlank(bdcZsQO.getQlr()) && StringUtils.isNotBlank(bdcZsQO.getQlrzjh())) {
            queryZsParam.put(CommonConstantUtils.QLR, bdcZsQO.getQlr());
            queryZsParam.put(CommonConstantUtils.QLRZJH, bdcZsQO.getQlrzjh());
        }
        //领证人信息，当只传证件号时查询领证人信息
        if (StringUtils.isNotBlank(bdcZsQO.getQlrzjh()) && StringUtils.isBlank(bdcZsQO.getQlr())) {
            queryZsParam.put(CommonConstantUtils.LZRZJH, bdcZsQO.getQlrzjh());
        }
        //是否办结
        if (StringUtils.isNotBlank(bdcZsQO.getQueryBj())) {
            if ("0".equals(bdcZsQO.getQueryBj())) {
                queryZsParam.put(CommonConstantUtils.AJZT_ZD, Arrays.asList(CommonConstantUtils.AJZT_ZB_DM, CommonConstantUtils.AJZT_YHCH_DM, CommonConstantUtils.AJZT_ZT_DM, CommonConstantUtils.AJZT_BYDJ_DM));
                queryZsParam.put(CommonConstantUtils.SPLY_ZD,null);
            } else if ("1".equals(bdcZsQO.getQueryBj())) {
                List splyList = new ArrayList();
                //39206 【盐城】自助机查询证书信息接口调整-针对外网受理 增加sply
                if (StringUtils.isNotBlank(bdcZsQO.getSply())){
                    String[] splyArray = StringUtils.split(bdcZsQO.getSply(), CommonConstantUtils.ZF_YW_DH);
                    for (String sply : splyArray) {
                        if (StringUtils.isNotBlank(sply)) {
                            splyList.add(Integer.parseInt(sply));
                        }
                    }
                }

                /**
                 * 如果选择了外网,将外网的详细都给加上
                 */
                if(CollectionUtils.isNotEmpty(splyList) && splyList.contains(CommonConstantUtils.SPLY_WWSQ)){
                    splyList.addAll(CommonConstantUtils.SPLY_WWSQ_DETAIL);
                }
                queryZsParam.put(CommonConstantUtils.AJZT_ZD, Arrays.asList(CommonConstantUtils.AJZT_YB_DM));
                queryZsParam.put(CommonConstantUtils.SPLY_ZD, splyList);
            }
        }
        //是否打印
        if (StringUtils.isNotBlank(bdcZsQO.getDyzt())) {
            if ("0".equals(bdcZsQO.getDyzt())) {
                queryZsParam.put("isdyFlag", false);
                queryZsParam.put(CommonConstantUtils.DYZT, Arrays.asList(CommonConstantUtils.DYZT_WX, CommonConstantUtils.DYZT_DD));
            } else if ("1".equals(bdcZsQO.getDyzt())) {
                queryZsParam.put(CommonConstantUtils.DYZT, Arrays.asList(CommonConstantUtils.DYZT_YX));
                queryZsParam.put("isdyFlag", true);
            }
        }
        //当只传qlr和qlrzjh 只查外网的件
        if (StringUtils.isNotBlank(bdcZsQO.getQlr()) && StringUtils.isNotBlank(bdcZsQO.getQlrzjh()) && StringUtils.isBlank(bdcZsQO.getQueryBj()) && StringUtils.isBlank(bdcZsQO.getDyzt()) && StringUtils.isBlank(bdcZsQO.getSlbh())) {
            queryZsParam.put("wbsplyFlag", true);
        }
        //受理编号
        if (StringUtils.isNotBlank(bdcZsQO.getSlbh())) {
            queryZsParam.put(CommonConstantUtils.SLBH, bdcZsQO.getSlbh());
        }
        if (queryZsParam.size() > 0) {
            List<Map> zslxZdMap = bdcZdFeignService.queryBdcZd("zslx");
            List<Map<String, Object>> tempList = bdcZsMapper.listBdcZsForZzdzj(queryZsParam);
            List<Map<String, Object>> result = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(tempList)){
                logger.info("查询到相关证书信息");
                Set<String> collectSet = new HashSet<>();
                logger.info(JSON.toJSONString(tempList));
                for (int i = 0; i < tempList.size(); i++) {
                    logger.info( "====" + collectSet);
                    if (!collectSet.add((String) tempList.get(i).get("BDCQZH"))){
                        continue;
                    }
                    initZsInfo(zslxZdMap, tempList, result, i);
                }
            }
            logger.info("不动产权证书list 盐城自助打证机查询结束");
            return result;
        }
        logger.info("不动产权证书list 盐城自助打证机查询结束");
        return null;
    }

    private void initZsInfo(List<Map> zslxZdMap, List<Map<String, Object>> tempList, List<Map<String, Object>> result, int i) {
        if (tempList.get(i).containsKey("BDCDYH") && tempList.get(i).get("BDCDYH") != null) {
            logger.info("开始查询相应的户室图和宗地图信息:{}", tempList.get(i).get("BDCDYH"));
            //查询宗地图和户室图
            ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh((String) tempList.get(i).get("BDCDYH"),"");
            String zdtBase64Str = zdtResponseDTO.getBase64();
            String hstBase64Str = fwHstFeignService.queryFwHstBase64ByBdcdyh((String) tempList.get(i).get("BDCDYH"),"");
            tempList.get(i).put("HST", hstBase64Str);
            tempList.get(i).put("ZDT", zdtBase64Str);
            logger.info("查询相应的户室图和宗地图信息结束");
        } else {
            tempList.get(i).put("HST", null);
            tempList.get(i).put("ZDT", null);
        }
        if (tempList.get(i).containsKey("ZSLX") && tempList.get(i).get("ZSLX") != null){
            logger.info("开始转换zslx:{}", tempList.get(i).get("ZSLX"));
            BigDecimal zslx = (BigDecimal) tempList.get(i).get("ZSLX");
            tempList.get(i).put("ZSMC", StringToolUtils.convertBeanPropertyValueOfZd(zslx.intValue(), zslxZdMap));
            logger.info("转换zslx结束");
        }
        List<Map> gyfsZdMap = bdcZdFeignService.queryBdcZd("gyfs");
        if (tempList.get(i).containsKey("GYQK") && tempList.get(i).get("GYQK") != null){
            logger.info("开始转换GYQk:{}", tempList.get(i).get("GYQK"));
            BigDecimal gyqk = (BigDecimal) tempList.get(i).get("GYQK");
            tempList.get(i).put("GYQK", StringToolUtils.convertBeanPropertyValueOfZd(gyqk.intValue(), gyfsZdMap));
            logger.info("转换GYQk结束");
        }

        if (tempList.get(i).containsKey("XMID") && tempList.get(i).get("XMID") != null) {
            logger.info("开始查询缴费信息:{}", tempList.get(i).get("XMID"));
            BdcSlSfxxDO querySfxxParam = new BdcSlSfxxDO();
            querySfxxParam.setXmid((String) tempList.get(i).get("XMID"));
            querySfxxParam.setGzlslid((String) tempList.get(i).get("GZLSLID"));
            querySfxxParam.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcSlSfxxDO> bdcSlSfxxDOS = bdcSlSfxxFeignService.queryBdcSlSfxx(querySfxxParam);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOS)) {
                tempList.get(i).put("SFZT", bdcSlSfxxDOS.get(0).getSfzt());
            } else {
                tempList.get(i).put("SFZT", null);
            }
        }
        if (tempList.get(i).get("QLR")!=null){
            tempList.get(i).put("QLRMC",tempList.get(i).remove("QLR"));
        }
        if (tempList.get(i).get("DJSJ")!=null){
            tempList.get(i).put("DBSJ",tempList.get(i).remove("DJSJ"));
        }
        result.add(tempList.get(i));
    }

    /**
     * 拆除登记回写证书附记
     *
     * @param processInsId
     */
    @Override
    public void updateZsFj(String processInsId) {
        if (StringUtils.isNotBlank(processInsId)) {
            List<BdcZsDO> listZs = queryYxmZsSortByXmid(processInsId, null);
            List<String> zsids = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(listZs)) {
                for (BdcZsDO bdcZsDO : listZs) {
                    zsids.add(bdcZsDO.getZsid());
                }
                bdcZsMapper.updateZsFj(zsids);
            }
        }
    }

    /**
     * 查询项目以及关联的证书信息
     * @param bdcXmDTOList 单元信息等
     * @return 项目以及关联的证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<BdcZsXmDTO> listBdcXmZs(List<BdcXmDTO> bdcXmDTOList) {
        if(CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new MissingArgumentException("未定义查询项目证书信息参数");
        }

        for(BdcXmDTO bdcXmDTO : bdcXmDTOList) {
            if(!CheckParameter.checkAnyParameter(bdcXmDTO)) {
                throw new MissingArgumentException("未定义查询项目证书信息参数");
            }
        }

        String bdcdyhs = StringToolUtils.joinBeanAttribute(bdcXmDTOList, "getBdcdyh", CommonConstantUtils.ZF_YW_DH);
        if(StringUtils.isBlank(bdcdyhs)) {
            throw new MissingArgumentException("未定义查询项目证书信息参数");
        }

        return bdcZsMapper.listBdcXmZs(bdcXmDTOList);
    }

    @Override
    public BdcZsVO initJyZsxx(BdcZsQO bdcZsQO) throws Exception{
        if(StringUtils.isBlank(bdcZsQO.getGzlslid())){
            throw new MissingArgumentException("未定义查询证书校验参数的必要参数");
        }
        if(StringUtils.isNotBlank(bdcZsQO.getZsid())){
            List<BdcXmDO> bdcXmDOList = queryZsXmByZsid(bdcZsQO.getZsid());
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                bdcZsQO.setXmid(StringToolUtils.resolveBeanToAppendStr(bdcXmDOList, "getXmid", CommonConstantUtils.ZF_YW_DH));
            }
        }
        Boolean scYbz =StringUtils.isBlank(bdcZsQO.getXmid());

        if(StringUtils.isBlank(bdcZsQO.getXmid())){
            List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcZsQO.getGzlslid());
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                bdcZsQO.setXmid(bdcXmDTOList.get(0).getXmid());
            }
        }
        if(StringUtils.isNotBlank(bdcZsQO.getXmid())) {
            List<String> xmidList = Arrays.asList(bdcZsQO.getXmid().split(CommonConstantUtils.ZF_YW_DH));
            bdcZsQO.setXmidList(xmidList);
            bdcZsQO.setXmid(xmidList.get(0));
        }
        BdcZsDO bdcZsDO =generateZsxx(bdcZsQO);
        if(bdcZsDO ==null){
            return new BdcZsVO();
        }
        BdcZsVO bdcZsVO =new BdcZsVO();
        generateBdcZsVO(bdcZsVO, bdcZsDO, true, bdcZsQO.getXmid());
        //获取权利人和义务人信息
        generateQlrxx(bdcZsVO, bdcZsDO, bdcZsQO,scYbz);
        return bdcZsVO;
    }

    /**
     * 添加项目和已有证书的关联关系
     * @param zsid
     * @param xmid
     */
    @Override
    public void addXmZsConnection(String zsid,String xmid) {
        Example example = new Example(BdcXmZsGxDO.class);
        example.createCriteria().andEqualTo("zsid",zsid).andEqualTo("xmid",xmid);
        List<BdcXmZsGxDO> bdcXmZsGxDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcXmZsGxDOList)){
            throw new AppException("已关联证书");
        }
        BdcXmZsGxDO bdcXmZsGxDO = new BdcXmZsGxDO();
        bdcXmZsGxDO.setGxid(UUIDGenerator.generate16());
        bdcXmZsGxDO.setXmid(xmid);
        bdcXmZsGxDO.setZsid(zsid);
        entityMapper.insertSelective(bdcXmZsGxDO);
    }
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //根据身份证号查询不动产登记信息
     * @Date 2022/5/12 16:16
     **/
    @Override
    public List<BdcZsXmAndQlrDTO> listDjxx(BdcZsXmAndQlrQO qo) {
        if (StringUtils.isEmpty(qo.getSfzh())) {
            throw new MissingArgumentException("身份证号参数不能为空");
        }
        return bdcZsMapper.listDjxx(qo.getBdcdyh(), qo.getQlrmc(), qo.getSfzh());
    }

    /**
     * 查询证明关联的产权证书信息
     * @param zsid 证书ID
     * @return {List} 证书信息
     * @Author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     **/
    @Override
    public List<BdcZsDO> listBdcZsByZmid(String zsid) {
        if(StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("缺失证书ID参数");
        }
        return bdcZsMapper.listBdcZsByZmid(zsid);
    }

    /**
     * 更新注销流程的证书信息
     *
     * @param processInsId
     */
    @Override
    public void gxzhzt(String processInsId) {
        logger.info("撤销流程办结处理原产权证号为撤销并添加当前证书证号！processInsId:{}", processInsId);
        if (StringUtils.isBlank(processInsId)) {
            return;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        //当前流程的证书
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        List<BdcZsDO> bdcZsDoList = listBdcZs(bdcZsQO);
        logger.info("撤销流程办结处理原产权证号为撤销并添加当前证书证号！processInsId:{},当前项目证书信息{}", processInsId,
                JSON.toJSONString(bdcZsDoList));
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && CollectionUtils.isNotEmpty(bdcZsDoList)) {
            List<BdcZsDO> ybdcZsDoList = new ArrayList<>();
            List<BdcXmLsgxDO> bdcXmLsgxDOList = new ArrayList<>();
                    //获取当前项目的历史关系--上一手
            List<String> xmidList = bdcXmDOList
                    .stream()
                    .map(BdcXmDO::getXmid)
                    .collect(Collectors.toList());
            List<List<String>> xmidListPartition = Lists.partition(xmidList, 500);
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            for (List<String> xmids : xmidListPartition) {
                bdcXmLsgxQO.setXmids(xmids);
                List<BdcXmLsgxDO> bdcXmLsgxs = bdcLsgxFeignService.listXmLsgxByXmids(bdcXmLsgxQO);
                if (CollectionUtils.isNotEmpty(bdcXmLsgxs)){
                    bdcXmLsgxDOList.addAll(bdcXmLsgxs);
                }
            }
            logger.info("撤销流程办结处理原产权证号为撤销并添加当前证书证号！processInsId:{},当前项目上一手信息{}", processInsId,
                    JSON.toJSONString(bdcXmLsgxDOList));
            //获取yxmid的上一手--上一手的上一手
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                List<BdcXmLsgxDO> yxmLsgxList = new ArrayList<>();
                List<String> yxmidList = bdcXmLsgxDOList
                        .stream()
                        .map(BdcXmLsgxDO::getYxmid)
                        .collect(Collectors.toList());
                List<List<String>> yxmidListPartition = Lists.partition(yxmidList, 500);
                for (List<String> yxmids : yxmidListPartition) {
                    bdcXmLsgxQO.setXmids(yxmids);
                    List<BdcXmLsgxDO> ybdcXmLsgxs = bdcLsgxFeignService.listXmLsgxByXmids(bdcXmLsgxQO);
                    if (CollectionUtils.isNotEmpty(ybdcXmLsgxs)){
                        yxmLsgxList.addAll(ybdcXmLsgxs);
                    }
                }
                logger.info("撤销流程办结处理原产权证号为撤销并添加当前证书证号！processInsId:{},当前项目上上一手信息{}", processInsId,
                        JSON.toJSONString(yxmLsgxList));
                if (CollectionUtils.isNotEmpty(yxmLsgxList)) {
                    for (BdcXmLsgxDO xmLsgxDO : yxmLsgxList) {
                        //获取原项目的证书
                        List<BdcZsDO> bdcZsDOS = queryBdcZsByXmid(xmLsgxDO.getYxmid());
                        if (CollectionUtils.isNotEmpty(bdcZsDOS)
                        ) {
                            ybdcZsDoList.addAll(bdcZsDOS);
                        }
                    }
                }
            }
            logger.error("撤销流程办结处理原产权证号为撤销并添加当前证书证号！证书信息,bdcZsDoList:{},yyYwxx:{}",
                    JSON.toJSONString(bdcZsDoList),
                    JSON.toJSONString(ybdcZsDoList));
            if (CollectionUtils.isNotEmpty(bdcZsDoList) || CollectionUtils.isNotEmpty(ybdcZsDoList)
            ) {
                try {
                    //更新原证书号为注销状态,保证不重号,同时将产权证号给当前的
                    updateZhzt(ybdcZsDoList,bdcZsDoList);
                }catch (Exception e){
                    logger.error("更新原证书号为注销状态,保证不重号,同时将产权证号给当前的错误，{}",e.getMessage());
                }
            }
        }
    }

    /**
     * @param yBdcZsList 原不动产证书记录
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新上一手证书号为注销状态,当前手移除临时标记
     */
    private void updateZhzt(List<BdcZsDO> yBdcZsList,List<BdcZsDO> bdcZsList) {
        if (CollectionUtils.isEmpty(yBdcZsList) && CollectionUtils.isEmpty(bdcZsList)) {
            return;
        }
        //添加注销标志
        List<String> bdcqzhList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcZsList)){
            bdcqzhList = bdcZsList.stream()
                    .map(bdcqzh -> StringUtils.replace(bdcqzh.getBdcqzh(), CommonConstantUtils.BDCQZH_LS,""))
                    .collect(Collectors.toList());
        }
        //只处理新证书相同证号的原证书
        if(CollectionUtils.isNotEmpty(yBdcZsList)){
            for (BdcZsDO yBdcZsDO : yBdcZsList) {
                // 之前的证号已经包含注销标识说明已经获取过
                if (!StringUtils.contains(yBdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_ZX) &&
                        bdcqzhList.contains(yBdcZsDO.getBdcqzh())
                ) {
                    BdcZsDO updateZs = new BdcZsDO();
                    updateZs.setZsid(yBdcZsDO.getZsid());
                    updateZs.setBdcqzh(StringUtils.join(yBdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_ZX));
                    //补充附记
                    updateZs.setFj(StringUtils.join(yBdcZsDO.getFj(), CommonConstantUtils.ZF_HH_CHAR, BCFJ));
                    entityMapper.updateByPrimaryKeySelective(updateZs);
                    logger.info("撤销登记生成新的证书记录，证号不变，注销之前旧的证号：{}", JSON.toJSONString(updateZs));
                }
            }
        }
        //移除临时标志
        if(CollectionUtils.isNotEmpty(bdcZsList)){
            for (BdcZsDO bdcZsDO : bdcZsList) {
                // 之前的证号已经包含注销标识说明已经获取过
                if (StringUtils.contains(bdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_LS)) {
                    BdcZsDO updateZs = new BdcZsDO();
                    updateZs.setZsid(bdcZsDO.getZsid());
                    updateZs.setBdcqzh(StringUtils.replace(bdcZsDO.getBdcqzh(), CommonConstantUtils.BDCQZH_LS,""));
                    entityMapper.updateByPrimaryKeySelective(updateZs);
                    logger.info("撤销登记生成新的证书记录，证号不变，移除临时标志：{}", JSON.toJSONString(updateZs));
                }
            }
        }
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  组织获取证书信息,包含证书预览情况
      */
    private BdcZsDO generateZsxx(BdcZsQO bdcZsQO) throws Exception{
        BdcZsDO bdcZsDO =null;
        //已经生成证书的情况
        if(StringUtils.isNotBlank(bdcZsQO.getZsid())){
            //多本证
            bdcZsDO =queryBdcZs(bdcZsQO.getZsid());
        }else if(StringUtils.isNotBlank(bdcZsQO.getXmid())){
            //一本证
            List<BdcZsDO> bdcZsDOList = queryBdcZsByXmid(bdcZsQO.getXmid());
            if(CollectionUtils.isNotEmpty(bdcZsDOList)){
                bdcZsDO =bdcZsDOList.get(0);
            }
        }
        //没有生成证书的情况
        if(bdcZsDO ==null &&StringUtils.isNotBlank(bdcZsQO.getXmid())){
            //没有生成证书
            List<BdcZsDO> bdcZsDOList = bdcZsInitFeignService.initBdcqz(bdcZsQO.getXmid(), true);
            if(CollectionUtils.isNotEmpty(bdcZsDOList)){
                if(StringUtils.isNotBlank(bdcZsQO.getQlr())){
                    for(BdcZsDO zsDO:bdcZsDOList){
                        if(StringUtils.equals(bdcZsQO.getQlr(),zsDO.getQlr())){
                            return zsDO;
                        }
                    }
                }
                bdcZsDO =bdcZsDOList.get(0);
            }
        }
        return bdcZsDO;

    }

    /**
     * @param bdcZsVO 页面传输对象
     * @param bdcZsDO 查询到的证书对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证书查询结果，生成页面证书对象
     */
    private void generateBdcZsVO(BdcZsVO bdcZsVO, BdcZsDO bdcZsDO,boolean zsyl,String xmid) {
        if (null != bdcZsDO) {
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            bdcZsDO.setBdcdyh(BdcdyhToolUtils.formatBdcdyh(bdcZsDO.getBdcdyh()));
            BeanUtils.copyProperties(bdcZsDO, bdcZsVO);
            // 转换共有方式
            bdcZsVO.setGyfsmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(), zdMap.get(Constants.GYFS)));

            // 转换权利类型
            bdcZsVO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getQllx(), zdMap.get(Constants.QLLX)));
            // 截取证流水号
            String bdcqzh = bdcZsDO.getBdcqzh();
            if (StringUtils.isNotBlank(bdcqzh)) {
                Integer start = StringUtils.indexOf(bdcqzh, CommonConstantUtils.DI);
                Integer end = StringUtils.indexOf(bdcqzh, CommonConstantUtils.HAO);
                bdcZsVO.setZhlsh(StringUtils.substring(bdcqzh, start + 1, end));
            }

            // 获取相关的项目信息
            BdcXmDO bdcXmDO = new BdcXmDO();
            List<BdcXmDO> bdcXmDOList = queryZsXmByZsid(bdcZsDO.getZsid());
            if (CollectionUtils.isNotEmpty(bdcXmDOList) && null != bdcXmDOList.get(0)) {
                bdcXmDO = bdcXmDOList.get(0);
                bdcZsVO.setDjjg(bdcXmDO.getDjjg());
                bdcZsVO.setQszt(bdcXmDO.getQszt());
                if(StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                    BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                    bdcXmFbQO.setXmid(bdcXmDO.getXmid());
                    List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
                    if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                        bdcZsVO.setQjgldm(bdcXmFbDOList.get(0).getQjgldm());
                    }
                }
            }
            bdcZsVO.setBdcXmDOList(bdcXmDOList);

            // 缮证日期，对应证书上面的颁发日期，标准版取的登簿时间，合肥取缮证时间
            Date bfrq = bdcXmDO.getDjsj();
            bdcZsVO.setSzrq(bfrq);
            bdcZsVO.setSzrqDay(bfrq);
            bdcZsVO.setSzrqMonth(bfrq);
            bdcZsVO.setSzrqYear(bfrq);
        }
    }

    private void generateQlrxx(BdcZsVO bdcZsVO, BdcZsDO bdcZsDO, BdcZsQO bdcZsQO,Boolean scYbz) {
        if(CollectionUtils.isEmpty(bdcZsQO.getXmidList()) ||StringUtils.isBlank(bdcZsQO.getXmid())){
            logger.info("查询权利人信息未获取到xmid");
            return;
        }
        //权利人批量默认相同,用xmid获取即可
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        bdcQlrQO.setXmid(bdcZsQO.getXmid());
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        List<BdcQlrDTO> bdcQlrDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            for (BdcQlrDO bdcQlr : bdcQlrDOList) {
                if(StringUtils.contains(bdcZsDO.getQlr(),bdcQlr.getQlrmc())) {
                    BdcQlrDTO bdcQlrDTO = new BdcQlrDTO();
                    bdcQlrDTO.setBdcQlrDO(bdcQlr);
                    bdcQlrDTO.setNfZhlsh((StringUtils.isNotBlank(bdcZsDO.getNf()) ? bdcZsDO.getNf() : "") + (StringUtils.isNotBlank(bdcZsDO.getZhlsh()) ? bdcZsDO.getZhlsh() : ""));
                    bdcQlrDTOList.add(bdcQlrDTO);
                }
            }
            bdcZsVO.setLxdh(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getDh", CommonConstantUtils.ZF_YW_DH));
            bdcZsVO.setBdcQlrDTOList(bdcQlrDTOList);
        } else {
            BdcQlrDTO bdcQlrDTO = new BdcQlrDTO();
            bdcQlrDTO.setBdcQlrDO(new BdcQlrDO());
            bdcQlrDTO.setNfZhlsh("");
            bdcQlrDTOList.add(bdcQlrDTO);
            bdcZsVO.setBdcQlrDTOList(bdcQlrDTOList);
        }
        List<BdcQlrDO> bdcYwrDOList;
        if(Boolean.TRUE.equals(scYbz)){
            //获取所有的义务人
            bdcYwrDOList = bdcQlrFeignService.listAllBdcQlr(bdcZsQO.getGzlslid(), CommonConstantUtils.QLRLB_YWR, "");
        }else{
            //生成多本证
            bdcYwrDOList =bdcQlrFeignService.listBdcQlrByXmidList(bdcZsQO.getXmidList(),CommonConstantUtils.QLRLB_YWR);
        }
        List<BdcQlrDTO> bdcYwrDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcYwrDOList)) {
            //义务人去重
            bdcYwrDOList =BdcQlrGroupUtils.distinctQlrxx(bdcYwrDOList);
            for (BdcQlrDO bdcYwrDO : bdcYwrDOList) {
                BdcQlrDTO bdcYwrDTO = new BdcQlrDTO();
                bdcYwrDTO.setBdcQlrDO(bdcYwrDO);
                //根据证书id找到
                //找到上一手的xmid，义务人是上一手的权利人
                BdcXmLsgxQO bdcLsgxQO = new BdcXmLsgxQO();
                bdcLsgxQO.setXmid(bdcYwrDO.getXmid());
                bdcLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
                List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcLsgxQO);
                //根据义务人的项目id获取证号年份+流水号
                if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {

                    //上一手的权利人数据
                    BdcQlrQO bdcYwrQO = new BdcQlrQO();
                    bdcYwrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    bdcYwrQO.setXmid(bdcXmLsgxDOList.get(0).getYxmid());
                    List<BdcQlrDO> bdcYwrList = bdcQlrFeignService.listBdcQlr(bdcYwrQO);
                    for (BdcQlrDO bdcYwr : bdcYwrList) {
                        if (StringUtils.equals(bdcYwrDO.getQlrmc(), bdcYwr.getQlrmc()) && StringUtils.equals(bdcYwrDO.getZjh(), bdcYwr.getZjh()) && StringUtils.isNotBlank(bdcYwr.getZsid())) {
                            BdcZsDO yBdcZs = queryBdcZs(bdcYwr.getZsid());
                            if (Objects.nonNull(yBdcZs)) {
                                bdcYwrDTO.setNfZhlsh((StringUtils.isNotBlank(yBdcZs.getNf()) ? yBdcZs.getNf() : "") + (StringUtils.isNotBlank(yBdcZs.getZhlsh()) ? yBdcZs.getZhlsh() : ""));
                            }
                        }
                    }
                }
                bdcYwrDTOList.add(bdcYwrDTO);
            }
            bdcZsVO.setBdcYwrDTOList(bdcYwrDTOList);
        } else {
            BdcQlrDTO bdcYwrDTO = new BdcQlrDTO();
            bdcYwrDTO.setBdcQlrDO(new BdcQlrDO());
            bdcYwrDTO.setNfZhlsh("");
            bdcYwrDTOList.add(bdcYwrDTO);
            bdcZsVO.setBdcYwrDTOList(bdcYwrDTOList);
        }
        //产权来源
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setXmid(bdcZsQO.getXmid());
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
            String cqly = bdcXmFbDOList.get(0).getCqly();
            if (StringUtils.isNotBlank(cqly)) {
                Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
                bdcZsVO.setCqly(StringToolUtils.convertBeanPropertyValueOfZdByString(cqly, zdMap.get("cqly")));
            }
        }
    }

    /**
     * 查询证号流水号时候，如果多个区划共用一个流水号，则将当前区划替换为所在分组区划查询
     * @param bdcBdcqzhBO 证号查询实体
     * @return {String} 库中当前最大顺序号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void resolveQxdm(BdcBdcqzhBO bdcBdcqzhBO) {
        if(StringUtils.isBlank(zhgylsh)) {
            return;
        }

        String[] array = zhgylsh.split(CommonConstantUtils.ZF_YW_FH);
        for(String qxdms : array) {
            String[] qxdmArray = qxdms.split(CommonConstantUtils.ZF_YW_DH);
            for(String qxdm : qxdmArray) {
                if(StringUtils.equals(qxdm, bdcBdcqzhBO.getQxdm())) {
                    bdcBdcqzhBO.setQxdms(qxdms);
                    logger.info("证书{}处理证号采用分组区划{}查询流水号，当前区划{}", bdcBdcqzhBO.getZsid(), qxdms, bdcBdcqzhBO.getQxdm());
                    return;
                }
            }
        }
    }

    /**
     * @param zsidList 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 按照zsid获取当前证书清单
     */
    @Override
    public List<BdcZsqdVO> queryZsQdByZsid(List<String> zsidList,String gzlslid) {
        List<BdcZsqdVO> resultList = new ArrayList<>();
        List<String> zsidlist = CollectionUtils.isNotEmpty(zsidList) ? zsidList:bdcZsFeignService.queryGzlZsid(gzlslid);
        if(CollectionUtils.isEmpty(zsidlist)){
            throw new AppException("未查询到证书清单！");
        }
        resultList = queryZsQdList(zsidlist);
        return resultList;
    }
    /**
     * @param zsidList 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 按照zsid获取当前证书清单
     */
    private List<BdcZsqdVO>  queryZsQdList(List<String> zsidList) {
        List<BdcZsqdVO> resultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(zsidList)) {
            List<List> lists = ListUtils.subList(zsidList, 500);
            if (CollectionUtils.isNotEmpty(lists)) {
                for (int j = 0;j<lists.size();j++) {
                    List<String> list = lists.get(j);
                    List<BdcZsqdVO> bdcZsqdVOList = bdcZsMapper.queryZsQdByZsid(list);
                    if (CollectionUtils.isNotEmpty(bdcZsqdVOList)) {
                        for (int i = 0; i < bdcZsqdVOList.size(); i++) {
                            BdcZsqdVO bdcZsqdVO = bdcZsqdVOList.get(i);
                            bdcZsqdVO.setXh(i + 1 + j*500);
                            resultList.add(bdcZsqdVO);
                        }
                    }
                }
            }
        }
        return resultList;
    }

}
