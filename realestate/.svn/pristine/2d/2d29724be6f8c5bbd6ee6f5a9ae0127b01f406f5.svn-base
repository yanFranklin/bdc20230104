package cn.gtmap.realestate.init.service.tshz.changzhou;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcFctdPpgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 常州房产证土地证业务读取字段取值逻辑
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-12-27 13:44
 **/
@Service("bdcTdzxxServiceImpl_changzhou")
public class InitBdcFczTdzxxServiceImpl implements InitBdcTsHzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcFczTdzxxServiceImpl.class);

    @Autowired
    private BdcFctdPpgxService bdcFctdPpgxService;
    @Autowired
    private InitServiceQoService initServiceQoService;
    @Autowired
    BdcXmService bdcXmService;

    /**
     * 特殊后置处理
     *
     * @param initServiceQO
     * @param initServiceDTO *@return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //属于房地产权或者抵押的
        if (initServiceDTO != null) {
            //部分对照的重新取数据
            if (initServiceQO.isSfdzbflpbsj()) {
                initServiceQO.setBdcdyResponseDTO(null);
                initServiceQO.setDjxxResponseDTO(null);
            }
            if (initServiceDTO.getBdcQl() instanceof BdcFdcqDO || initServiceDTO.getBdcQl() instanceof BdcDyaqDO) {
                //存在历史关系数据 过滤外联数据 要存有土地证的才处理
                if (CollectionUtils.isNotEmpty(initServiceDTO.getBdcXmLsgxList())) {
                    //产权的原项目
                    BdcXmDO yxmDO = null;
                    if (initServiceDTO.getBdcQl() instanceof BdcFdcqDO && StringUtils.isNotBlank(initServiceQO.getYxmid())) {
                        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) initServiceDTO.getBdcQl();
                        yxmDO = initServiceQoService.queryYbdcxm(initServiceQO.getYxmid(), initServiceQO);
                        //匹配土地证
                        if (yxmDO != null && CommonConstantUtils.SF_S_DM.equals(yxmDO.getClsjppzt())) {
                            //如果有空的则获取匹配的值
                            if (initServiceDTO.getBdcXm().getZdzhmj() == null || bdcFdcqDO.getFttdmj() == null || bdcFdcqDO.getDytdmj() == null || StringUtils.isBlank(initServiceDTO.getBdcXm().getQlxz())
                                    || bdcFdcqDO.getTdsyqmj() == null || StringUtils.isAnyBlank(initServiceDTO.getBdcXm().getZdzhyt(), initServiceDTO.getBdcXm().getZdzhyt2(), initServiceDTO.getBdcXm().getZdzhyt3())
                                    || Objects.isNull(bdcFdcqDO.getTdsyqssj()) || Objects.isNull(bdcFdcqDO.getTdsyjssj()) || Objects.isNull(bdcFdcqDO.getTdsyqssj2()) ||
                                    Objects.isNull(bdcFdcqDO.getTdsyjssj2()) || Objects.isNull(bdcFdcqDO.getTdsyqssj3()) || Objects.isNull(bdcFdcqDO.getTdsyjssj3())) {
                                LOGGER.warn("当前项目{}房地产权信息{}字段值存在空，读取匹配的土地证信息", bdcFdcqDO.getSlbh(), JSON.toJSONString(bdcFdcqDO, SerializerFeature.WriteNullStringAsEmpty));
                                List<BdcFctdPpgxDO> bdcFctdPpgxList = bdcFctdPpgxService.queryBdcFctdPpgx(initServiceQO.getYxmid());
                                if (CollectionUtils.isNotEmpty(bdcFctdPpgxList) && bdcFctdPpgxList.get(0) != null && StringUtils.isNotBlank(bdcFctdPpgxList.get(0).getTdcqxmid())) {
                                    BdcQl ytdql = initServiceQoService.queryYql(bdcFctdPpgxList.get(0).getTdcqxmid(), initServiceQO);
                                    if (ytdql instanceof BdcJsydsyqDO) {
                                        //查询土地项目信息
                                        BdcXmDO tdXm = bdcXmService.queryBdcXmByPrimaryKey(ytdql.getXmid());
                                        if (Objects.nonNull(tdXm)) {
                                            if (Objects.isNull(initServiceDTO.getBdcXm().getZdzhmj())) {
                                                initServiceDTO.getBdcXm().setZdzhmj(tdXm.getZdzhmj());
                                            }
                                            if (StringUtils.isBlank(initServiceDTO.getBdcXm().getZdzhyt())) {
                                                initServiceDTO.getBdcXm().setZdzhyt(tdXm.getZdzhyt());
                                            }
                                            if (StringUtils.isBlank(initServiceDTO.getBdcXm().getZdzhyt2())) {
                                                initServiceDTO.getBdcXm().setZdzhyt2(tdXm.getZdzhyt2());
                                            }
                                            if (StringUtils.isBlank(initServiceDTO.getBdcXm().getZdzhyt3())) {
                                                initServiceDTO.getBdcXm().setZdzhyt3(tdXm.getZdzhyt3());
                                            }
                                        } else {
                                            LOGGER.error("未查询到土地项目信息,xmid={}", ytdql.getXmid());
                                        }
                                        if (bdcFdcqDO.getFttdmj() == null) {
                                            bdcFdcqDO.setFttdmj(((BdcJsydsyqDO) ytdql).getFttdmj());
                                        }
                                        if (bdcFdcqDO.getDytdmj() == null) {
                                            bdcFdcqDO.setDytdmj(((BdcJsydsyqDO) ytdql).getDytdmj());
                                        }
                                        if (bdcFdcqDO.getTdsyqmj() == null) {
                                            bdcFdcqDO.setTdsyqmj(((BdcJsydsyqDO) ytdql).getSyqmj());
                                        }
                                        if (bdcFdcqDO.getTdsyqssj() == null) {
                                            bdcFdcqDO.setTdsyqssj(((BdcJsydsyqDO) ytdql).getSyqqssj());
                                        }
                                        if (bdcFdcqDO.getTdsyjssj() == null) {
                                            bdcFdcqDO.setTdsyjssj(((BdcJsydsyqDO) ytdql).getSyqjssj());
                                        }
                                        if (bdcFdcqDO.getTdsyqssj2() == null) {
                                            bdcFdcqDO.setTdsyqssj2(((BdcJsydsyqDO) ytdql).getSyqqssj2());
                                        }
                                        if (bdcFdcqDO.getTdsyjssj2() == null) {
                                            bdcFdcqDO.setTdsyjssj2(((BdcJsydsyqDO) ytdql).getSyqjssj2());
                                        }
                                        if (bdcFdcqDO.getTdsyqssj3() == null) {
                                            bdcFdcqDO.setTdsyqssj3(((BdcJsydsyqDO) ytdql).getSyqqssj3());
                                        }
                                        if (bdcFdcqDO.getTdsyjssj3() == null) {
                                            bdcFdcqDO.setTdsyjssj3(((BdcJsydsyqDO) ytdql).getSyqjssj3());
                                        }
                                        if (StringUtils.isBlank(initServiceDTO.getBdcXm().getQlxz()) && ((BdcJsydsyqDO) ytdql).getQlxz() != null) {
                                            initServiceDTO.getBdcXm().setQlxz(((BdcJsydsyqDO) ytdql).getQlxz().toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return initServiceDTO;
    }
}
