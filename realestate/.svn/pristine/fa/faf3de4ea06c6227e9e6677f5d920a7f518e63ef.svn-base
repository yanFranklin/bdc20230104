package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlSjclPzMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlSjclPzService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclPzDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/28
 * @description 不动产受理收件材料数据服务配置信息
 */
@Service
public class BdcSlSjclPzServiceImpl implements BdcSlSjclPzService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private Repo repo;
    @Autowired
    private BdcSlSjclPzMapper bdcSlSjclPzMapper;

    /**
     * @param djxl 登记小类
     * @return 不动产受理收件材料配置信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据登记小类获取不动产受理收件材料配置信息
     */
    @Override
    public List<BdcSlSjclPzDO> listBdcSlSjclPzByDjxl(String djxl) {
        List<BdcSlSjclPzDO> bdcSlSjclPzDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(djxl)) {
            Example example = new Example(BdcSlSjclPzDO.class);
            example.createCriteria().andEqualTo("djxl", djxl);
            example.setOrderByClause("xh");
            bdcSlSjclPzDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSjclPzDOList)) {
            bdcSlSjclPzDOList = Collections.emptyList();
        }
        return bdcSlSjclPzDOList;
    }

    @Override
    public int saveBdcSlSjclPzDO(BdcSlSjclPzDO bdcSlSjclPzDO) {
        if (bdcSlSjclPzDO == null) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        if (StringUtils.isBlank(bdcSlSjclPzDO.getPzid())) {
            bdcSlSjclPzDO.setPzid(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcSlSjclPzDO, bdcSlSjclPzDO.getPzid());

    }

    @Override
    public int deleteBdcSlSjclPzDO(List<BdcSlSjclPzDO> bdcSlSjclPzDOList) {
        int count = 0;
        if(CollectionUtils.isEmpty(bdcSlSjclPzDOList)){
            return count;
        }
        for(BdcSlSjclPzDO bdcSlSjclPzDO:bdcSlSjclPzDOList) {
            count +=entityMapper.delete(bdcSlSjclPzDO);
        }
        return count;
    }

    /**
     * @param pageable
     * @param bdcSlSjclPzJson     收件材料配置
     * @return 分页
     * @author <a href="mailto:gailining@gtmap.cn">gailining</a>
     * @description 分页查询收件材料配置
     */
    @Override
    public Page<BdcSlSjclPzDO> listBdcSlSjclPzByPage(Pageable pageable, String bdcSlSjclPzJson) {
        BdcSlSjclPzDO bdcSlSjclPzDO = new BdcSlSjclPzDO();
        if(StringUtils.isNotBlank(bdcSlSjclPzJson)) {
            bdcSlSjclPzDO = JSONObject.parseObject(bdcSlSjclPzJson,BdcSlSjclPzDO.class);
        }
        return repo.selectPaging("listBdcSlSjclPzByPage", bdcSlSjclPzDO, pageable);
    }

    /**
     * @param bdcSlSjclPzDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收件材料 配置 最大序号
     */
    @Override
    public Integer querySjclPzMaxSxh(BdcSlSjclPzDO bdcSlSjclPzDO) {
        if(bdcSlSjclPzDO==null){
            throw new MissingArgumentException("获取收件材料配置 最大序号 参数不能为空！");
        }
        return bdcSlSjclPzMapper.querySjclPzMaxSxh(bdcSlSjclPzDO);
    }
}
