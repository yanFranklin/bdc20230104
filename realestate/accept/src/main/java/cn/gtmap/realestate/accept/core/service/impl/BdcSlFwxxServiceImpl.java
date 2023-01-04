package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlFwxxMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlFwxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlQlxxService;
import cn.gtmap.realestate.accept.service.BdcCommonSlService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.SqlUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 受理房屋信息
 */
@Service
public class BdcSlFwxxServiceImpl implements BdcSlFwxxService,BdcSlQlxxService,BdcCommonSlService {

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcSlFwxxMapper bdcSlFwxxMapper;

    @Resource(name = "dozerSameNullFMapper")
    private DozerBeanMapper dozerMapperF;

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        set.add(BdcSlFwxxDO.class.getSimpleName());
        set.add("4");
        set.add("6");
        set.add("8");
        return set;
    }

    @Override
    public BdcSlQl queryBdcSlQl(String xmid){
        BdcSlFwxxDO bdcSlFwxxDO =new BdcSlFwxxDO();
        List<BdcSlFwxxDO> bdcSlFwxxDOList =listBdcSlFwxxByXmid(xmid);
        if(CollectionUtils.isNotEmpty(bdcSlFwxxDOList)){
            bdcSlFwxxDO =bdcSlFwxxDOList.get(0);

        }
        return bdcSlFwxxDO;
    }



    @Override
    public List<BdcSlFwxxDO> listBdcSlFwxxByXmid(String xmid) {
        List<BdcSlFwxxDO> bdcSlFwxxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlFwxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            bdcSlFwxxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlFwxxDOList)) {
            bdcSlFwxxDOList = Collections.emptyList();
        }
        return bdcSlFwxxDOList;
    }

    /**
     * @param xmidList 项目ID集合
     * @return 不动产受理项目
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 根据项目ID集合获取不动产受理房屋信息
     */
    @Override
    public List<BdcSlFwxxDO> listBdcSlFwxxByXmids(List<String> xmidList) {
        if(CollectionUtils.isNotEmpty(xmidList)){
            return bdcSlFwxxMapper.listBdcSlFwxxByXmids(xmidList);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 批量更新受理房屋信息
     */
    @Override
    public Integer updateBatchBdcSlFwxx(BdcDjxxUpdateQO bdcDjxxUpdateQO) {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
        }
        String jsonStr = bdcDjxxUpdateQO.getJsonStr();
        //获取更新json对象
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        Map map = new HashMap();
        //获取批量更新语句
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcSlFwxxDO.class.getName());
        if (!statement.contains("SET")) {
            return 0;
        }

        map.put("statement", statement);
        //where 条件
        map.putAll(bdcDjxxUpdateQO.getWhereMap());
        //获取实体对象
        BdcSlFwxxDO bdcSlFwxxDO = JSON.parseObject(jsonStr, BdcSlFwxxDO.class);
        map.put("record", bdcSlFwxxDO);
        map.remove("gzlslid");
        return bdcSlFwxxMapper.batchUpdateBdcSlFwxx(map);
    }

    /**
     * @param fwxxid 房屋信息id
     * @return 不动产受理房屋信息息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋信息id获取不动产受理房屋信息
     */
    @Override
    public BdcSlFwxxDO queryBdcSlFwxxByFwxxid(String fwxxid) {
        if (StringUtils.isBlank(fwxxid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlFwxxDO.class, fwxxid);
    }

    /**
     * @param bdcSlFwxxDO 房屋信息
     * @return 不动产受理房屋信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理房屋信息
     */
    @Override
    public BdcSlFwxxDO insertBdcSlFwxx(BdcSlFwxxDO bdcSlFwxxDO) {
        if (bdcSlFwxxDO != null) {
            if (StringUtils.isBlank(bdcSlFwxxDO.getFwxxid())) {
                bdcSlFwxxDO.setFwxxid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlFwxxDO);
        }
        return bdcSlFwxxDO;
    }

    /**
     * @param fwxxid 房屋信息id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理房屋信息
     */
    @Override
    public Integer deleteBdcSlFwxxByFwxxid(String fwxxid) {
        int result = 0;
        if (StringUtils.isNotBlank(fwxxid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlFwxxDO.class, fwxxid);
        }
        return result;
    }

    /**
     * @param xmid 项目id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理房屋信息
     */
    @Override
    public Integer deleteBdcSlFwxxByXmid(String xmid) {
        int result = 0;
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlFwxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @return 保存数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存受理房屋信息
     */
    @Override
    public Integer updateBdcSlFwxx(BdcSlFwxxDO bdcSlFwxxDO) {
        int count;
        if (StringUtils.isNotBlank(bdcSlFwxxDO.getFwxxid())) {
            count = entityMapper.updateByPrimaryKeySelective(bdcSlFwxxDO);
        } else {
            bdcSlFwxxDO.setFwxxid(UUIDGenerator.generate16());
            count = entityMapper.insertSelective(bdcSlFwxxDO);
        }
        return count;
    }

    /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @return 保存数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据项目ID更新不动产受理房屋信息
     */
    @Override
    public Integer updateBdcSlFwxxByXmid(BdcSlFwxxDO bdcSlFwxxDO) {
        List<BdcSlFwxxDO> fwxxList = this.listBdcSlFwxxByXmid(bdcSlFwxxDO.getXmid());
        if(fwxxList.isEmpty()){
            this.insertBdcSlFwxx(bdcSlFwxxDO);
            return 1;
        }else{
            // 由于第三方接口返回的fwxx中fwid为空字符串，此处将空字符串转为null处理，避免update导致fwxxid为空更新报错
            if(StringUtils.isBlank(bdcSlFwxxDO.getFwxxid())){
                bdcSlFwxxDO.setFwxxid(null);
            }
            Example example= new Example(BdcSlFwxxDO.class,false);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid",bdcSlFwxxDO.getXmid());
            return entityMapper.updateByExampleSelectiveNotNull(bdcSlFwxxDO,example);
        }
    }

    @Override
    public void deleteBdcSlFwxx(BdcSlDeleteCsDTO bdcSlDeleteCsDTO){
        if (!CheckParameter.checkAnyParameter(bdcSlDeleteCsDTO)) {
            throw new AppException("删除参数为空" + JSONObject.toJSONString(bdcSlDeleteCsDTO));
        }
        bdcSlFwxxMapper.batchDeleteBdcSlFwxx(bdcSlDeleteCsDTO);

    }

    @Override
    public void saveBdcSlFwxx(BdcSlFwxxDO bdcSlFwxxDO) {
        if (null != bdcSlFwxxDO && StringUtils.isNotBlank(bdcSlFwxxDO.getXmid())) {
            // 获取原有的房屋信息
            List<BdcSlFwxxDO> bdcSlFwxxDOList = this.listBdcSlFwxxByXmid(bdcSlFwxxDO.getXmid());
            if(CollectionUtils.isNotEmpty(bdcSlFwxxDOList)){
                BdcSlFwxxDO ybdcSlFwxxDO = bdcSlFwxxDOList.get(0);
                dozerMapperF.map(bdcSlFwxxDO,ybdcSlFwxxDO);
                this.updateBdcSlFwxx(ybdcSlFwxxDO);
            }else {
                this.insertBdcSlFwxx(bdcSlFwxxDO);
            }
        }
    }

    @Override
    public void batchDelete(BdcSlDeleteCsDTO bdcSlDeleteCsDTO){
        deleteBdcSlFwxx(bdcSlDeleteCsDTO);
    }
}
