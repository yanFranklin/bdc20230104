package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.core.service.BdcSlXztzPzService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcZdQjgldmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXztzPzDO;
import cn.gtmap.realestate.common.core.dto.BdcJsPzDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXztzPzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcJsPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/1/30
 * @description
 */
@Service
public class BdcSlXztzPzServiceImpl implements BdcSlXztzPzService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcJsPzFeignService bdcJsPzFeignService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    /**
     * 选择台账配置权籍管理代码逻辑版本,配置new走新逻辑
     */
    @Value("${xztz.qjgldmfilter.version:}")
    private String xztzQjgldmFilterVersion;

    /**
     * 常用属性名
     */
    private static final String version = "new";

    /**
     * @param gzldyid 工作流定义ID
     * @return 不动产受理选择台账配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产受理选择台账配置
     */
    @Override
    public BdcSlXztzPzDO queryBdcSlXztzPzDOByGzldyid(String gzldyid) {
        BdcSlXztzPzDO bdcSlXztzPzDO = null;
        Example example = new Example(BdcSlXztzPzDO.class);
        example.createCriteria().andEqualTo("gzldyid", gzldyid);
        List<BdcSlXztzPzDO> bdcSlXztzPzDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isNotEmpty(bdcSlXztzPzDOList)) {
            bdcSlXztzPzDO = bdcSlXztzPzDOList.get(0);
        }
        return bdcSlXztzPzDO;
    }

    @Override
    public int saveBdcSlXztzPzDO(BdcSlXztzPzDO bdcSlXztzPzDO){
        if(bdcSlXztzPzDO == null){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        if (StringUtils.isBlank(bdcSlXztzPzDO.getPzid())) {
            bdcSlXztzPzDO.setPzid(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcSlXztzPzDO,bdcSlXztzPzDO.getPzid());
    }

    @Override
    public int deleteBdcSlXztzPzDOByGzldyid(String gzldyid){
        int result = 0;
        if (StringUtils.isNotBlank(gzldyid)) {
            Example example = new Example(BdcSlXztzPzDO.class);
            example.createCriteria().andEqualTo("gzldyid", gzldyid);
            result = entityMapper.deleteByExample(example);
        }
        return result;

    }

    @Override
    public BdcSlXztzPzDTO queryBdcSlXztzPzDTOByGzldyid(String gzldyid){
        BdcSlXztzPzDTO bdcSlXztzPzDTO =null;
        BdcSlXztzPzDO bdcSlXztzPzDO =queryBdcSlXztzPzDOByGzldyid(gzldyid);
        if(bdcSlXztzPzDO != null){
            bdcSlXztzPzDTO =new BdcSlXztzPzDTO();
            BeanUtils.copyProperties(bdcSlXztzPzDO, bdcSlXztzPzDTO);
            List<BdcZdQjgldmDO> zdqjgldmList =new ArrayList<>();
            if(StringUtils.equals(version,xztzQjgldmFilterVersion)) {
                List<String> qjgldmList = Container.getBean(BdcConfigUtils.class).qjgldmFilter("selectbdcdyh");
                List<Map> qjgldmZdMap = bdcZdFeignService.queryBdcZd("qjgldm");
                for(Map qjgldmZd:qjgldmZdMap){
                    String dm =qjgldmZd.get("DM").toString();
                    if(CollectionUtils.isEmpty(qjgldmList) ||qjgldmList.contains(dm)){
                        BdcZdQjgldmDO bdcZdQjgldmDO =new BdcZdQjgldmDO();
                        bdcZdQjgldmDO.setDm(dm);
                        bdcZdQjgldmDO.setMc(qjgldmZd.get("MC").toString());
                        zdqjgldmList.add(bdcZdQjgldmDO);
                    }
                }
                if (CollectionUtils.isNotEmpty(qjgldmList) && CollectionUtils.isEmpty(zdqjgldmList)){
                    // 配置权籍管理代码与字典不匹配
                    throw new AppException("权籍管理代码过滤与字典表不对应，请检查配置，"+ JSONObject.toJSONString(qjgldmList));
                }

            }else {
                //兼容原有逻辑
                UserDto userDto = userManagerUtils.getCurrentUser();
                if (userDto != null) {
                    BdcJsPzDTO bdcJsPzDTO = bdcJsPzFeignService.queryJsPzDTOByUserId(userDto);
                    if (bdcJsPzDTO != null) {
                        zdqjgldmList = bdcJsPzDTO.getQjgldmList();
                    }
                }
            }


            bdcSlXztzPzDTO.setQjgldmList(CollectionUtils.isNotEmpty(zdqjgldmList) ?zdqjgldmList:new ArrayList<>());

        }
        return bdcSlXztzPzDTO;

    }
}
