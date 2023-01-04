package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlSjclService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收件材料数据服务
 */
@Service
public class BdcSlSjclServiceImpl implements BdcSlSjclService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlSjclServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    protected UserManagerUtils userManagerUtils;



    /**
     * @param sjclid 收件材料ID
     * @return 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收件材料ID获取不动产受理收件材料
     */
    @Override
    public BdcSlSjclDO queryBdcSlSjclBySjclid(String sjclid) {
        if(StringUtils.isBlank(sjclid)){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlSjclDO.class, sjclid);
    }

    /**
     * @param wjzxid 文件中心ID
     * @return 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据文件中心ID获取不动产受理收件材料
     */
    @Override
    public  BdcSlSjclDO queryBdcSlSjclByWjzxid(String wjzxid){
        if (StringUtils.isNotBlank(wjzxid)) {
            Example example = new Example(BdcSlSjclDO.class);
            example.createCriteria().andEqualTo("wjzxid", wjzxid);
            List<BdcSlSjclDO> bdcSlSjclDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                return bdcSlSjclDOList.get(0);
            }
        }
        return null;
    }


    @Override
    public List<BdcSlSjclDO> listBdcSlSjclByGzlslid(String gzlslid) {
        List<BdcSlSjclDO> bdcSlSjclDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlSjclDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            example.setOrderByClause("xh");
            bdcSlSjclDOList = entityMapper.selectByExample(example);
        }
        if(CollectionUtils.isEmpty(bdcSlSjclDOList)){
            bdcSlSjclDOList = Collections.emptyList();
        }
        bdcSlSjclDOList = sjclResort(bdcSlSjclDOList);
        return bdcSlSjclDOList;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcSlSjclDOList]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO>
     * @description 如果顺序号不正确或有空值则重新排序，顺序号正确则直接返回
     */
    private List<BdcSlSjclDO> sjclResort(List<BdcSlSjclDO> bdcSlSjclDOList) {
        if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
            return bdcSlSjclDOList;
        }
        int sizeNum = bdcSlSjclDOList.size();
        boolean resortFlag = false;
        //如果只有一条数据且顺序号不为空，不用重新排序
        if (CollectionUtils.size(bdcSlSjclDOList) == 1 && Objects.nonNull(bdcSlSjclDOList.get(0).getXh())) {
            return bdcSlSjclDOList;
        }
        // 检测顺序号是否正确
        for (int i = 1; i <= sizeNum; i++) {
            if (null != bdcSlSjclDOList.get(i - 1)) {
                Integer xh = bdcSlSjclDOList.get(i - 1).getXh();
                if (xh == null || !xh.equals(i)) {
                    resortFlag = true;
                    break;
                }
            }
        }

        if(resortFlag){
            //重新设置顺序号
            for(int i=0; i<bdcSlSjclDOList.size(); i++){
                bdcSlSjclDOList.get(i).setXh(i+1);
            }
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                for (BdcSlSjclDO slSjclDO : bdcSlSjclDOList) {
                    this.updateBdcSlSjcl(slSjclDO);
                }
            }

        }
        return bdcSlSjclDOList;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param clmc 材料名称
     * @return 不动产受理收件材料
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description  根据项目ID、工作流实例ID或者材料名称获取不动产受理收件材料
     */
    @Override
    public List<BdcSlSjclDO> listBdcSlSjcl(String gzlslid, String clmc) {
        List<BdcSlSjclDO> bdcSlSjclDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlSjclDO.class);
            Example.Criteria criteria= example.createCriteria();
            if(StringUtils.isNotBlank(gzlslid)){
                criteria.andEqualTo("gzlslid", gzlslid);
            }
            if(StringUtils.isNotBlank(clmc)){
                try {
                    // 由于规则验证传入中文会乱码，所以先做一层编码，这边获取到值时做一层解码
                    clmc = URLDecoder.decode(clmc, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error(e.getMessage(),e);
                }
                criteria.andEqualTo("clmc", clmc);
            }
            example.setOrderByClause("xh");
            bdcSlSjclDOList = entityMapper.selectByExample(example);
        }
        if(CollectionUtils.isEmpty(bdcSlSjclDOList)){
            bdcSlSjclDOList = Collections.emptyList();
        }
        return bdcSlSjclDOList;
    }

    /**
     * @param bdcSlSjclDO 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理收件材料
     */
    @Override
    public BdcSlSjclDO insertBdcSlSjcl(BdcSlSjclDO bdcSlSjclDO) {
        if (bdcSlSjclDO != null) {
            if (StringUtils.isBlank(bdcSlSjclDO.getSjclid())) {
                bdcSlSjclDO.setSjclid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlSjclDO);
        }
        return bdcSlSjclDO;
    }

    /**
     * @param bdcSlSjclDO 不动产受理收件材料
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理收件材料
     */
    @Override
    public Integer updateBdcSlSjcl(BdcSlSjclDO bdcSlSjclDO) {
        int result;
        if (bdcSlSjclDO != null && StringUtils.isNotBlank(bdcSlSjclDO.getSjclid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlSjclDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    /**
     * @param sjclid 收件材料ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收件材料ID删除不动产受理收件材料
     */
    @Override
    public Integer deleteBdcSlSjclBySjclid(String sjclid) {
        int result = 0;
        if (StringUtils.isNotBlank(sjclid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlSjclDO.class, sjclid);
        }
        return result;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID删除不动产受理收件材料
     */
    @Override
    public Integer deleteBdcSlSjclByGzlslid(String gzlslid) {
        int result = 0;
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlSjclDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    @Override
    public int insertBatchSjclList(List<BdcSlSjclDO> bdcSlSjclDOList) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            result = entityMapper.insertBatchSelective(bdcSlSjclDOList);
        }
        return result;

    }


    /**
     * @param bdcSlSjclQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询收件材料新接口
     * @date : 2021/3/25 14:16
     */
    @Override
    public List<BdcSlSjclDO> listBdcSlSjcl(BdcSlSjclQO bdcSlSjclQO) {
        List<BdcSlSjclDO> bdcSlSjclDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcSlSjclQO.getGzlslid())) {
            Example example = new Example(BdcSlSjclDO.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(bdcSlSjclQO.getGzlslid())) {
                criteria.andEqualTo("gzlslid", bdcSlSjclQO.getGzlslid());
            }
            if (StringUtils.isNotBlank(bdcSlSjclQO.getClmc())) {
                String clmc = "";
                try {
                    // 由于规则验证传入中文会乱码，所以先做一层编码，这边获取到值时做一层解码
                    clmc = URLDecoder.decode(bdcSlSjclQO.getClmc(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error(e.getMessage(), e);
                }
                criteria.andEqualTo("clmc", clmc);
            }
            if (StringUtils.isNotBlank(bdcSlSjclQO.getDjxl())) {
                criteria.andEqualTo("djxl", bdcSlSjclQO.getDjxl());
            }
            if(StringUtils.isNotBlank(bdcSlSjclQO.getSqbm())){
                criteria.andEqualTo("sqbm", bdcSlSjclQO.getSqbm());
            }
            example.setOrderByClause("xh");
            bdcSlSjclDOList = entityMapper.selectByExample(example);
            sjclResort(bdcSlSjclDOList);
        }
        if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
            bdcSlSjclDOList = Collections.emptyList();
        }
        return bdcSlSjclDOList;
    }
}
