package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.realestate.common.core.domain.natural.*;
import cn.gtmap.realestate.common.core.dto.natural.ZrzySlymYwxxDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyXmDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyYwxxDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyZrzkYwxxDTO;
import cn.gtmap.realestate.natural.config.NaturalBeanFactory;
import cn.gtmap.realestate.natural.core.service.ZrzyCommonService;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import cn.gtmap.realestate.natural.service.ZrzyYwxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源业务填报信息服务
 */
@Service
public class ZrzyYwxxServiceImpl implements ZrzyYwxxService {

    @Autowired
    private ZrzyXmService zrzyXmService;

    @Autowired
    NaturalBeanFactory naturalBeanFactory;

    @Override
    public ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //项目信息
            ZrzyXmDO zrzyXm = zrzyXmService.queryZrzyXmByGzlslid(gzlslid);
            if (zrzyXm != null) {
                ZrzySlymYwxxDTO zrzySlymYwxxDTO = new ZrzySlymYwxxDTO();
                zrzySlymYwxxDTO.setZrzyXm(zrzyXm);
                //获取其他业务填报信息
                Set<ZrzyCommonService> zrzyYwxxServiceSet = naturalBeanFactory.getZrzyYwxxServiceSet();
                if (CollectionUtils.isNotEmpty(zrzyYwxxServiceSet)) {
                    for (ZrzyCommonService zrzyCommonService : zrzyYwxxServiceSet) {
                        zrzyCommonService.queryZrzySlymYwxxDTO(zrzyXm.getXmid(), zrzySlymYwxxDTO);
                    }
                }
                return zrzySlymYwxxDTO;
            }
        }
        return null;

    }

//    @Override
//    public ZrzyYwxxDTO queryZrzywxxDTO(String xmid){
//        if(StringUtils.isNotBlank(xmid)) {
//            //项目信息
//            ZrzyXmDO zrzyXm =zrzyXmService.queryZrzyXmByXmid(xmid);
//            if(zrzyXm != null){
//                ZrzyYwxxDTO zrzyYwxxDTO =new ZrzyYwxxDTO();
//                zrzyYwxxDTO.setZrzyXm(zrzyXm);
//                //获取其他业务填报信息
//                Set<ZrzyCommonService> zrzyYwxxServiceSet =naturalBeanFactory.getZrzyYwxxInitServiceSet();
//                if(CollectionUtils.isNotEmpty(zrzyYwxxServiceSet)){
//                    for(ZrzyCommonService zrzyCommonService:zrzyYwxxServiceSet){
//                        zrzyCommonService.queryZrzySlymYwxxDTO(zrzyXm.getXmid(), zrzyYwxxDTO);
//                    }
//                }
//                return zrzyYwxxDTO;
//            }
//        }
//        return null;
//
//    }

    @Override
    public ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //项目信息
            ZrzyXmDO zrzyXm = zrzyXmService.queryZrzyXmByGzlslid(gzlslid);
            if (zrzyXm != null) {
                ZrzyZrzkYwxxDTO zrzyZrzkYwxxDTO = new ZrzyZrzkYwxxDTO();
                zrzyZrzkYwxxDTO.setZrzyXm(zrzyXm);
                //获取其他业务填报信息
                Set<ZrzyCommonService> zrzyYwxxServiceSet = naturalBeanFactory.getZrzyYwxxServiceSet();
                if (CollectionUtils.isNotEmpty(zrzyYwxxServiceSet)) {
                    for (ZrzyCommonService zrzyCommonService : zrzyYwxxServiceSet) {
                        zrzyCommonService.queryZrzyZrzkYwxxDTO(zrzyXm.getXmid(), zrzyZrzkYwxxDTO);
                    }
                }
                return zrzyZrzkYwxxDTO;
            }
        }
        return null;
    }

    @Override
    public List<ZrzyGgmbDo> queryZrzyGgmbxx(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //项目信息
            ZrzyXmDO zrzyXm = zrzyXmService.queryZrzyXmByGzlslid(gzlslid);
            if (zrzyXm != null) {
                return zrzyXmService.listGgmbByXmid(zrzyXm.getXmid());
            }
        }
        return null;
    }
}
