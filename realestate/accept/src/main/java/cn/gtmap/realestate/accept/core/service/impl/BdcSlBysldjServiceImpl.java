package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.config.FdjywConfig;
import cn.gtmap.realestate.accept.core.service.BdcSlFdjywService;
import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.service.BdcLshService;
import cn.gtmap.realestate.common.core.domain.accept.BdcByslDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: realestate
 * @description: 不予受理/不予登记服务实现方法
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-23 13:41
 **/
@Service
public class BdcSlBysldjServiceImpl implements BdcSlFdjywService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlBysldjServiceImpl.class);

    @Autowired
    FdjywConfig fdjywConfig;
    @Autowired
    EntityMapper entityMapper;

    @Value("${bysl.gzsbhqz:}")
    private String gzsbhPz;

    @Autowired
    BdcLshService bdcLshService;

    @Autowired
    BdcSlJbxxService bdcSlJbxxService;

    /**
     * @param bdcSlCshDTO 受理初始化对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化非登记业务信息
     */
    @Override
    public void initFdjywxx(BdcSlCshDTO bdcSlCshDTO) {
        LOGGER.info("开始初始化不予受理信息{}", JSON.toJSONString(bdcSlCshDTO));
        BdcByslDO bdcByslDO = new BdcByslDO();
        bdcByslDO.setByslid(UUIDGenerator.generate16());
        bdcByslDO.setGzlslid(bdcSlCshDTO.getGzlslid());
        if (CollectionUtils.isNotEmpty(bdcSlCshDTO.getBdcSlXmDOList())) {
            //选了产权证创建项目，根据项目信息生成不予受理/不予登记信息
            List<String> byslGzldyidList = fdjywConfig.getFdjywlcDyidList("bysl");
            //不予受理类型为1 不予登记类型为2
            if (CollectionUtils.isNotEmpty(byslGzldyidList) && byslGzldyidList.contains(bdcSlCshDTO.getGzldyid())) {
                bdcByslDO.setLx("1");
            } else {
                bdcByslDO.setLx("2");
            }
            //申请人和坐落读取上一手数据拼接
            bdcByslDO.setZl(StringToolUtils.resolveBeanToAppendStr(bdcSlCshDTO.getBdcSlXmDOList(), "getZl", CommonConstantUtils.ZF_YW_DH));
            bdcByslDO.setFdsxsqr(bdcSlCshDTO.getBdcSlXmDOList().get(0).getQlr());
        }
        //根据配置生成告知书编号
        Integer lsh = bdcLshService.queryLsh(bdcSlCshDTO.getQxdm() + "GZSBH", CommonConstantUtils.ZZSJFW_YEAR);
        if (StringUtils.isNotBlank(gzsbhPz)) {
            LOGGER.warn("当前流程实例id{}获取告知书编号配置{}", bdcSlCshDTO.getGzlslid(), gzsbhPz);
            Map<String, String> gzspzMap = JSON.parseObject(gzsbhPz, Map.class);
            //根据区县获取配置
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(bdcSlCshDTO.getGzlslid());
            if (Objects.nonNull(bdcSlJbxxDO)) {
                String qz = gzspzMap.get(bdcSlJbxxDO.getQxdm());
                bdcByslDO.setGzsbh(CommonConstantUtils.ZF_YW_Z_XKH + DateUtils.getCurrYear() + CommonConstantUtils.ZF_YW_Y_XKH + qz + lsh + "号");
            }
        }
        entityMapper.insertSelective(bdcByslDO);
    }

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        List<String> byslGzldyidList = fdjywConfig.getFdjywlcDyidList("bysl");
        if (CollectionUtils.isNotEmpty(byslGzldyidList)) {
            set.addAll(byslGzldyidList);
        } else {
            set.add("bysl");
        }
        List<String> bydjGzldyidList = fdjywConfig.getFdjywlcDyidList("bydj");
        if (CollectionUtils.isNotEmpty(bydjGzldyidList)) {
            set.addAll(bydjGzldyidList);
        } else {
            set.add("bydj");
        }
        return set;
    }
}
