package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.IPUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.init.core.service.BdcCzrzService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/3/26
 * @description 操作日志服务
 */
@Service
public class BdcCzrzServiceImpl implements BdcCzrzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCzrzServiceImpl.class);

    @Autowired
    private BdcXmService bdcXmService;

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;


    @Override
    public void initBdcCzrz(String gzlslid, String czyy, Integer czlx, String currentUserName) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例ID不能为空");
        }
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXm(bdcXmDO);

        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.info("gzlslid:{},删除日志无法记录,项目信息为空",gzlslid);
            return;
        }
        if(!BdcCzrzLxEnum.CZRZ_CZLX_WWTJ.key().equals(czlx) || StringUtils.isNotBlank(bdcXmDOList.get(0).getSpxtywh())) {
            BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
            dozerUtils.initBeanDateConvert(bdcXmDOList.get(0), bdcCzrzDO);
            bdcCzrzDO.setRzid(UUIDGenerator.generate16());
            if(StringUtils.isNotBlank(currentUserName)) {
                UserDto userDto = userManagerUtils.getUserByName(currentUserName);
                if (userDto != null) {
                    bdcCzrzDO.setCzr(userDto.getAlias());
                }
            }
            bdcCzrzDO.setCzsj(new Date());
            bdcCzrzDO.setCzyy(czyy);
            bdcCzrzDO.setCzlx(czlx);
            entityMapper.insertSelective(bdcCzrzDO);
        }
    }

    @Override
    public List<BdcCzrzDO> listBdcCzrzBySpxtywh(String spxtywh,String orderBy){
        if(StringUtils.isNotBlank(spxtywh)) {
            Example example = new Example(BdcCzrzDO.class);
            example.createCriteria().andEqualTo("spxtywh", spxtywh);
            if(StringUtils.isNotBlank(orderBy)) {
                example.setOrderByClause(orderBy);
            }
            List<BdcCzrzDO> czrzDOList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(czrzDOList)){
                return czrzDOList;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void addBdcCzrz(BdcCzrzDO bdcCzrzDO) {
        if(Objects.isNull(bdcCzrzDO)){
            throw new AppException(ErrorCode.MISSING_ARG , "未获取到操作日志内容");
        }
        if(StringUtils.isBlank(bdcCzrzDO.getIp())){
            try {
                ServletRequestAttributes requestAttributes = ServletRequestAttributes.class.
                        cast(RequestContextHolder.getRequestAttributes());
                HttpServletRequest contextRequest = requestAttributes.getRequest();
                bdcCzrzDO.setIp(IPUtils.getRequestClientRealIP(contextRequest));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.isBlank(bdcCzrzDO.getRzid())){
            bdcCzrzDO.setRzid(UUIDGenerator.generate16());
        }
        if(Objects.isNull(bdcCzrzDO.getCzsj())){
            bdcCzrzDO.setCzsj(new Date());
        }
        this.entityMapper.saveOrUpdate(bdcCzrzDO, bdcCzrzDO.getRzid());
    }

    @Override
    public void modifyBdcCzrzByRzid(BdcCzrzDO bdcCzrzDO) {
        if(StringUtils.isBlank(bdcCzrzDO.getRzid())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数操作日志id信息");
        }
        this.entityMapper.updateByPrimaryKeySelective(bdcCzrzDO);
    }

    @Override
    public List<BdcCzrzDO> queryBdcCzrz(BdcCzrzDO bdcCzrzDO) {
        if(Objects.isNull(bdcCzrzDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到查询参数");
        }
        return entityMapper.selectByObj(bdcCzrzDO);
    }

    @Override
    public void addScCzrzWithOpinion(String gzlslid, String opinion) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        BdcXmDO bdcXmDO = new BdcXmDO();
        List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            //获取受理项目信息
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
                throw new AppException("未获取到项目信息");
            }
            BeanUtils.copyProperties(bdcSlXmDOList.get(0), bdcXmDO);
        } else {
            bdcXmDO = bdcXmDOList.get(0);
        }
        addScCzrzWithOpinionWithXmxx(gzlslid, opinion, bdcXmDO);
    }

    /**
     * 添加删除操作日志
     * <p>原因获取流程的审批意见内容</p>
     *
     * @param gzlslid 工作流实例ID
     * @param opinion 审核意见
     * @param bdcXmDO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    public void addScCzrzWithOpinionWithXmxx(String gzlslid, String opinion, BdcXmDO bdcXmDO) {
        if(bdcXmDO == null){
            throw new AppException("项目信息不能为空！");
        }
        // 构建操作日志实体类
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        dozerUtils.initBeanDateConvert(bdcXmDO, bdcCzrzDO);
        bdcCzrzDO.setRzid(UUIDGenerator.generate16());
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (userDto != null) {
            bdcCzrzDO.setCzr(userDto.getAlias());
        }
        bdcCzrzDO.setCzsj(new Date());
        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_SC.key());
        bdcCzrzDO.setCzzt(CommonConstantUtils.SF_S_DM);
        bdcCzrzDO.setCzyy(opinion);
        bdcCzrzDO.setSpxtywh(bdcXmDO.getSpxtywh());
        // 添加删除操作说明，说明信息为转发审批意见

        entityMapper.insertSelective(bdcCzrzDO);
    }
}
