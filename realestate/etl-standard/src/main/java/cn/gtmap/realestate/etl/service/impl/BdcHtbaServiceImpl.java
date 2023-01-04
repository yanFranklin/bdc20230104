package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.etl.HtbaspfQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.etl.core.mapper.exchange.BdcHtbaMapper;
import cn.gtmap.realestate.etl.service.BdcHtbaService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0  2022-11-22
 * @description 合同备案相关服务
 */

@Service
public class BdcHtbaServiceImpl implements BdcHtbaService {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcHtbaServiceImpl.class);

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Qualifier("bdcRepository")
    @Autowired(required = false)
    private Repo repo;

    @Autowired
    private BdcHtbaMapper bdcHtbaMapper;
    /**
     * ORM操作
     */
    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param pageable
     * @param paramJson
     * @return 分页
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 分页查询收费项目配置
     */
    @Override
    public Page<HtbaspfQO> listHtbaByPageJson(Pageable pageable, String paramJson) {
        Map map = new HashMap();
        if (StringUtils.isNotBlank(paramJson)) {
            map = JSONObject.parseObject(paramJson, HashMap.class);
        }
        Page<HtbaspfQO> page = repo.selectPaging("listHtbaByPage", map, pageable);
        return page;
    }


    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 注销合同，更新数据
     */
    @Override
    public Integer updateHtzt(String baid, String tfyy) {

        List<HtbaSpfDO> htbaSpfDOS = new ArrayList<>();
        HtbaSpfDO htbaSpfDO = new HtbaSpfDO();
        Example example = new Example(HtbaSpfDO.class);
        Example.Criteria criteria = example.createCriteria();

        //获取当前时间
        Date date = new Date();

        //获取系统当前登录人
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (Objects.nonNull(userDto) && StringUtils.isNotBlank(userDto.getAlias())) {
            if (StringUtils.isNotBlank(baid)) {
                criteria.andEqualTo("baid", baid);
                htbaSpfDOS = entityMapper.selectByExampleNotNull(example);
            }
            if (CollectionUtils.isNotEmpty(htbaSpfDOS) && StringUtils.isNotBlank(tfyy)) {
                htbaSpfDO = htbaSpfDOS.get(0);
                htbaSpfDO.setBazt(2);
                htbaSpfDO.setZxyy(tfyy);
                htbaSpfDO.setZxshr(userDto.getAlias());
                htbaSpfDO.setZxsj(date);
                entityMapper.updateByPrimaryKey(htbaSpfDO);
                return htbaSpfDO.getBazt();
            }
        } else {
            throw new AppException("当前系统未登录");
        }
        return 0;
    }
}
