package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.realestate.accept.core.mapper.BdcSlShxxMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlShxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlShxxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXtMryjFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import cn.gtmap.realestate.common.util.*;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * *
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>"
 * @version 1.0, 2021/2/23
 * @description 审核信息服务实现类
 */
@Service
public class BdcSlShxxServiceImpl implements BdcSlShxxService {
    protected final Logger LOGGER = LoggerFactory.getLogger(BdcSlShxxServiceImpl.class);
    /**
     * 审核意见操作结果-同意
     */
    private static final Integer SHYJCZJG_TY = 1;
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcXtMryjFeignService bdcXtMryjFeignService;
    @Autowired
    BdcSlShxxMapper bdcSlShxxMapper;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;


    /**
     * @param gzlslid 工作流实例ID
     * @return Integer sxh
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取当前流程审核信息的顺序号
     */
    private Integer getSxhByGzlslid(String gzlslid) {
        Example example = new Example(BdcSlShxxDO.class);
        example.createCriteria().andEqualTo(CommonConstantUtils.GZLSLID, gzlslid);
        List<BdcSlShxxDO> BdcSlShxxDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isEmpty(BdcSlShxxDOList)) {
            return 1;
        } else {
            return BdcSlShxxDOList.size() + 1;
        }
    }

    /**
     * @param bdcShxx BdcSlShxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新指定节点的审核信息
     */
    @Override
    public int updateBdcShxx(BdcSlShxxDO bdcShxx) {
        if (StringUtils.isBlank(bdcShxx.getShxxid())) {
            throw new MissingArgumentException("缺失主键shxxid！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcShxx);
    }



    /**
     * @param bdcSlShxxQO BdcSlShxxDO
     * @return List<BdcSlShxxDO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据条件查询审核信息，（暂时只实现根据shxxid/gzlslid/xmid精确查询）
     */
    @Override
    public List<BdcSlShxxDO> queryBdcShxx(BdcSlShxxQO bdcSlShxxQO) {
        if (null != bdcSlShxxQO) {
            Example example = new Example(BdcSlShxxDO.class);
            example.setOrderByClause("sxh DESC");
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(bdcSlShxxQO.getShxxid())) {
                criteria.andEqualTo("shxxid", bdcSlShxxQO.getShxxid());
            }
            if (StringUtils.isNotBlank(bdcSlShxxQO.getJdmc())) {
                criteria.andEqualTo("jdmc", bdcSlShxxQO.getJdmc());
            }
            if (StringUtils.isNotBlank(bdcSlShxxQO.getGzlslid())) {
                criteria.andEqualTo(CommonConstantUtils.GZLSLID, bdcSlShxxQO.getGzlslid());
            }
            if (StringUtils.isNotBlank(bdcSlShxxQO.getXmid())) {
                criteria.andEqualTo("xmid", bdcSlShxxQO.getXmid());
            }
            if (StringUtils.isNotBlank(bdcSlShxxQO.getShryxm())) {
                criteria.andEqualTo("shryxm", bdcSlShxxQO.getShryxm());
            }
            if (StringUtils.isNotBlank(bdcSlShxxQO.getXzxxid())) {
                criteria.andEqualTo("xzxxid", bdcSlShxxQO.getXzxxid());
            }
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList<>();
    }

    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 依据主键更新多条审核信息数据
     */
    @Override
    public int updateShxxList(List<BdcSlShxxDO> paramList) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(paramList)) {
            for (BdcSlShxxDO BdcSlShxxDO : paramList) {
                result += updateBdcShxx(BdcSlShxxDO);
            }
        }
        return result;
    }

    /**
     * @param shxxid
     * @return int
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    @Override
    public int deleteShxx(String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失主键审核信息ID");
        }
        Example example = new Example(BdcSlShxxDO.class);
        example.createCriteria().andEqualTo("shxxid",shxxid);
        return entityMapper.deleteByExample(example);

    }

    /**
     * @param shxxidList
     * @return int
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量删除审核意见和签名信息
     */
    @Override
    public int deleteShxxSign(List<String> shxxidList) {
        if (CollectionUtils.isEmpty(shxxidList)) {
            throw new MissingArgumentException("缺失主键审核信息ID值，shxxid");
        }
        int result = 0;
        List<List> lists = ListUtils.subList(shxxidList, 500);
        for (List list : lists) {
            result += bdcSlShxxMapper.batchDeleteShxxSign(list);
        }
        return result;
    }

    /**
     * @param shxxid 审核信息ID
     * @return BdcSlShxxDO 审核信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据主键查询审核信息
     */
    @Override
    public BdcSlShxxDO queryBdcShxxById(String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失shxxid");
        }
        return entityMapper.selectByPrimaryKey(BdcSlShxxDO.class, shxxid);
    }

    /**
     * @param BdcSlShxxDO 审核信息对象
     * @return int 操作数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @Override
    public int saveOrUpdateBdcShxx(BdcSlShxxDO BdcSlShxxDO) {
        if (StringUtils.isBlank(BdcSlShxxDO.getShxxid())) {
            BdcSlShxxDO.setShxxid(UUIDGenerator.generate16());
            insertBdcShxx(BdcSlShxxDO);
        }else{
            return updateBdcShxx(BdcSlShxxDO);
        }

        return 1;
    }

    /**
     * @param shxxid 审核信息id
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    @Override
    public int deleteSignAndSaveShjssj(String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失主键审核信息ID");
        }
        BdcSlShxxDO BdcSlShxxDO = entityMapper.selectByPrimaryKey(BdcSlShxxDO.class, shxxid);
        if (null != BdcSlShxxDO) {
            BdcSlShxxDO.setQmid("");
            BdcSlShxxDO.setShyj("");
            BdcSlShxxDO.setQmsj(null);
            BdcSlShxxDO.setShjssj(new Date());
            return entityMapper.updateByPrimaryKeyNull(BdcSlShxxDO);
        }
        return -1;
    }

    /**
     * @param shxxid 审核信息id
     * @return int 操作的数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    @Override
    public int updateShjssj(String shxxid) {
        LOGGER.info("开始更新审核结束时间，shxxid:{}",shxxid);
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失主键审核信息ID");
        }
        BdcSlShxxDO BdcSlShxxDO = new BdcSlShxxDO();
        BdcSlShxxDO.setShxxid(shxxid);
        BdcSlShxxDO.setShjssj(new Date());
        return entityMapper.updateByPrimaryKeySelective(BdcSlShxxDO);
    }



    /**
     * @param bdcSlShxxQOParam 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @Override
    public List<BdcShxxVO> queryShJdxx(BdcSlShxxQO bdcSlShxxQOParam) {
        String gzlslid = bdcSlShxxQOParam.getGzlslid();
        String taskId = bdcSlShxxQOParam.getShxxid();
        String currentJdmc = bdcSlShxxQOParam.getJdmc();
        Boolean onlyCurrentJd = bdcSlShxxQOParam.getOnlyCurrentJd();
        if (StringUtils.isEmpty(gzlslid) || StringUtils.isBlank(taskId) || StringUtils.isBlank(currentJdmc)) {
            throw new MissingArgumentException("缺失gzlslid或taskId或当前jdmc！");
        }
        BdcSlShxxQO bdcSlShxxQO = new BdcSlShxxQO();
        // 调用平台接口，获取当前流程的节点，只获取formProperties 中sign 为true的节点
        List<UserTaskDto> userTaskDataListTemp = workFlowUtils.listShjdxx(gzlslid);
        List<UserTaskDto> userTaskDataList = new ArrayList(userTaskDataListTemp.size());

        //只返回当前一个签名节点的信息
        if (onlyCurrentJd && CollectionUtils.isNotEmpty(userTaskDataListTemp)) {
            for (UserTaskDto userTaskDto : userTaskDataListTemp) {
                if (StringUtils.equals(currentJdmc, userTaskDto.getName())) {
                    userTaskDataList.add(userTaskDto);
                    break;
                }
            }
        } else {
            userTaskDataList = userTaskDataListTemp;
        }

        List<BdcShxxVO> shxxVoList = Lists.newArrayListWithCapacity(userTaskDataList.size());
        userTaskDataList.forEach(taskData -> {
            bdcSlShxxQO.setGzlslid(gzlslid);
            bdcSlShxxQO.setJdmc(taskData.getName());
            // 如果是当前节点，则增加主键查询条件
            if (StringUtils.equals(currentJdmc, taskData.getName())) {
                bdcSlShxxQO.setShxxid(taskId);
            }

            List<BdcSlShxxDO> BdcSlShxxDOList = this.queryBdcShxx(bdcSlShxxQO);

            BdcShxxVO bdcShxxVO = new BdcShxxVO();
            if (CollectionUtils.isNotEmpty(BdcSlShxxDOList)) {
                BdcSlShxxDOList.sort(Comparator.comparing(BdcSlShxxDO::getSxh));
                // 要获取审核sxh最大的那条数据
                Collections.reverse(BdcSlShxxDOList);
                BdcSlShxxDO BdcSlShxxDO = BdcSlShxxDOList.get(0);
                bdcShxxVO.setShxxid(BdcSlShxxDO.getShxxid());
                bdcShxxVO.setShyj(BdcSlShxxDO.getShyj());
                if (BdcSlShxxDO.getQmsj() != null) {
                    bdcShxxVO.setQmsj(DateFormatUtils.format(BdcSlShxxDO.getQmsj(), "yyyy年MM月dd日"));
                }
                bdcShxxVO.setQmid(BdcSlShxxDO.getQmid());
                // 拼接签名ID
                if (StringUtils.isNotEmpty(BdcSlShxxDO.getQmid())) {
                    bdcShxxVO.setQmtpdz(bdcSlShxxQOParam.getSignImageUrl() + BdcSlShxxDO.getQmid());
                }
            } else if (StringUtils.equals(currentJdmc, taskData.getName())) {
                // 数据库中还未保存审核信息，则初始化当前节点的审核信息
                bdcShxxVO.setShxxid(taskId);
            }
            bdcShxxVO.setJdid(taskData.getId());
            bdcShxxVO.setJdmc(taskData.getName());
            shxxVoList.add(bdcShxxVO);
        });
        return shxxVoList;
    }


    /**
     * @param BdcSlShxxDO 审核信息DO
     * @return 签名信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取当前人的签名信息
     */
    @Override
    public BdcShxxVO getShxxSign(BdcSlShxxDO BdcSlShxxDO) {

        UserDto userDto = userManagerUtils.getCurrentUser();

        LOGGER.info("userManagerUtils.getCurrentUser,审核信息签名获取到登录信息：{}", userDto);
        if(userDto ==null){
            throw new AppException("用户信息获取失败");
        }
        String signId = storageClient.userSign(userDto.getUsername());
        if (StringUtils.isEmpty(signId)) {
            throw new AppException("获取签名id失败");
        }
        // 签名时保存审核信息，且shxxid为当前taskId，由初始化审核页面的时候控制
        BdcSlShxxDO.setShryxm(userDto.getUsername());
        BdcSlShxxDO.setShryid(userDto.getId());
        BdcSlShxxDO.setQmid(signId);
        BdcSlShxxDO.setQmsj(new Date());
        this.saveOrUpdateBdcShxx(BdcSlShxxDO);

        BdcShxxVO bdcShxxVO = new BdcShxxVO();
        bdcShxxVO.setQmid(signId);
        bdcShxxVO.setQmsj(DateFormatUtils.format(BdcSlShxxDO.getQmsj(), "yyyy年MM月dd日"));
        return bdcShxxVO;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除审核信息
     * @date : 2021/9/22 21:25
     */
    @Override
    public int deleteShxxByGzlslid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlShxxDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.deleteByExample(example);
        }
        return 0;
    }

    public BdcSlShxxDO insertBdcShxx(BdcSlShxxDO bdcShxx) {
        Integer sxh = getSxhByGzlslid(bdcShxx.getGzlslid());

        Date shkssj = new Date();
        if (null != bdcShxx.getQmsj()) {
            shkssj = bdcShxx.getQmsj();
        }
        bdcShxx.setShkssj(shkssj);
        bdcShxx.setCzjg(SHYJCZJG_TY);
        bdcShxx.setSxh(sxh);
        entityMapper.insertSelective(bdcShxx);
        return bdcShxx;
    }

}
