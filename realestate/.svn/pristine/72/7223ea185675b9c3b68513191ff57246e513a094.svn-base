package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxFphhDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxDahQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.core.mapper.BdcGdxxFphhMapper;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxFphhQO;
import cn.gtmap.realestate.register.service.BdcGdxxFphhService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
 * @version 1.0, 2021/10/30
 * @description 档案分配盒号
 */
@Service
public class BdcGdxxFphhServiceImpl implements BdcGdxxFphhService {
    private static Logger logger = LoggerFactory.getLogger(BdcGdxxFphhServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcGdxxFphhMapper bdcGdxxFphhMapper;

    @Autowired
    private Repo repo;

    @Autowired
    private UserManagerUtils userManagerUtils;


    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return
     * @description 分配盒号，先进行入参查询
     */
    @Override
    public String fphhMethod(BdcGdxxFphhQO bdcGdxxFphhQO) {
        if(StringUtils.isBlank(bdcGdxxFphhQO.getXzdm())) {
            return "请选择乡镇！";
        }
        logger.info("{} 查询指定范围内的数据",bdcGdxxFphhQO);
       List<BdcGdxxFphhDO> listBdcGdxxFphh  = listBdcGdxx(bdcGdxxFphhQO);
        logger.info("{} 查询指定范围内的数据 {}",bdcGdxxFphhQO,listBdcGdxxFphh);
       if(CollectionUtils.isEmpty(listBdcGdxxFphh)) {
           return "指定范围档案号不存在！";
       }

        Integer sxh = bdcGdxxFphhQO.getSxh();
        if (Objects.isNull(bdcGdxxFphhQO.getSxh())) {
            // 用户没有选择盒号，则根据库中最大盒号生成下一个盒号
            this.getNextSxh(listBdcGdxxFphh.get(0));
        } else {
            listBdcGdxxFphh.get(0).setSxh(sxh);
        }
        logger.info("{} 确定顺序号 {}",bdcGdxxFphhQO,sxh);
       if (CollectionUtils.isNotEmpty(listBdcGdxxFphh)) {
           for (BdcGdxxFphhDO bdcGdxxFphhDO : listBdcGdxxFphh) {
               UserDto userDto = userManagerUtils.getCurrentUser();
               if (null != userDto) {
                   bdcGdxxFphhDO.setGdrxm(userDto.getAlias());
               }
               bdcGdxxFphhDO.setGdsj(new Date());
               bdcGdxxFphhDO.setSxh(listBdcGdxxFphh.get(0).getSxh());
           }
           logger.info("{} 拼接数据 {}",bdcGdxxFphhQO,listBdcGdxxFphh);
//           for (BdcGdxxFphhDO bdcGdxxFphhDO : listBdcGdxxFphh) {
//               entityMapper.updateByPrimaryKeySelective(bdcGdxxFphhDO);
//           }
           listBdcGdxxFphh.parallelStream().forEach(
                   bdcGdxxFphhDO -> {
                       logger.info("{} 更新 {}",bdcGdxxFphhQO,bdcGdxxFphhDO);
                       entityMapper.updateByPrimaryKeySelective(bdcGdxxFphhDO);
                   }
           );
           logger.info("{} 数据更新完毕 {}",bdcGdxxFphhQO,listBdcGdxxFphh);
       }
       return "";
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGdxxFphhDO 档案号信息
     * @return String 档案信息记录主键ID
     * @description 获取指定档案号范围对应乡镇、年份已有最大目录号（盒号） [这里先不考虑并发重号]
     */
    private void getNextSxh(BdcGdxxFphhDO bdcGdxxFphhDO) {
        if(null == bdcGdxxFphhDO || StringUtils.isBlank(bdcGdxxFphhDO.getXzdm())) {
            throw new MissingArgumentException("获取最大盒号失败，未获取到乡镇信息");
        }

        if(StringUtils.isBlank(bdcGdxxFphhDO.getNf())) {
            bdcGdxxFphhDO.setNf(DateUtils.getCurrYear());
        }

        Integer currentMlhMaxSxh = bdcGdxxFphhMapper.getCurrentMlhMaxSxh(bdcGdxxFphhDO);
        if(null == currentMlhMaxSxh) {
            currentMlhMaxSxh = 0;
        }

        bdcGdxxFphhDO.setSxh(currentMlhMaxSxh + 1);
    }

    /**
     * 查询未分配的数据
     *
     * @param bdcGdxxFphhQO
     * @return
     */
    private List<BdcGdxxFphhDO> listBdcGdxx(BdcGdxxFphhQO bdcGdxxFphhQO) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhFirstHalf())) {
            map.put("nf", StringUtils.substring(bdcGdxxFphhQO.getAjhFirstHalf(),3));
        }
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhFirstHalf()) && StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhStart())) {
            map.put("ajhStart", bdcGdxxFphhQO.getAjhFirstHalf()+"-"+bdcGdxxFphhQO.getAjhStart());
        }
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhFirstHalf()) && StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhEnd())) {
            map.put("ajhEnd", bdcGdxxFphhQO.getAjhFirstHalf()+"-"+bdcGdxxFphhQO.getAjhEnd());
        }
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getXzdm())) {
            map.put("xzdm", bdcGdxxFphhQO.getXzdm());
        }
        return bdcGdxxFphhMapper.listBdcGdxx(map);
    }

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return
     * @description 查询出已经分配的数据，用于前端提示
     */
    @Override
    public List<BdcGdxxFphhDO> sffphh(BdcGdxxFphhQO bdcGdxxFphhQO) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhFirstHalf())) {
            map.put("nf", StringUtils.substring(bdcGdxxFphhQO.getAjhFirstHalf(),3));
        }
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getXzdm())) {
            map.put("xzdm", bdcGdxxFphhQO.getXzdm());
        }
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhFirstHalf()) && StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhStart())) {
            map.put("ajhStart", bdcGdxxFphhQO.getAjhFirstHalf()+"-"+bdcGdxxFphhQO.getAjhStart());
        }
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhFirstHalf()) && StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhEnd())) {
            map.put("ajhEnd", bdcGdxxFphhQO.getAjhFirstHalf()+"-"+bdcGdxxFphhQO.getAjhEnd());
        }
        return bdcGdxxFphhMapper.listBdcGdxxFphhsffp(map);
    }

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return
     * @description 页面下拉框需要展示盒号
     */
    @Override
    public List<Integer> listhh(String xzdm,String xzmc, String nf) {
        //加载下个盒号
        BdcGdxxFphhDO bdcGdxxFphhDO = new BdcGdxxFphhDO();
        bdcGdxxFphhDO.setXzdm(xzdm);
        bdcGdxxFphhDO.setXzmc(xzmc);
        bdcGdxxFphhDO.setNf(nf);
        this.getNextSxh(bdcGdxxFphhDO);

        List<Integer> listhh = new ArrayList<>();
        listhh.add(bdcGdxxFphhDO.getSxh());
        listhh.addAll(bdcGdxxFphhMapper.listXzhh(xzdm, nf));
        return listhh;
    }

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param bdcGdxxFphhDO
     * @return
     * @description 新增归档分配盒子信息
     */
    @Override
    public int insertBdcGdxxFphh(BdcGdxxFphhDO bdcGdxxFphhDO) {
        return entityMapper.insertSelective(bdcGdxxFphhDO);
    }

    /**
     * 更新档案号信息
     * @param bdcGdxxFphhDO 档案信息
     * @return {int} 更新记录数量
     */
    @Override
    public int updateBdcGdxxFphh(BdcGdxxFphhDO bdcGdxxFphhDO) {
        if(null == bdcGdxxFphhDO || StringUtils.isBlank(bdcGdxxFphhDO.getGdxxid())) {
            throw new MissingArgumentException("未定义要更新的档案信息");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcGdxxFphhDO);
    }

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param bdcGdxxFphhDOList
     * @return
     * @description 页面批量更新
     */
    @Override
    public void batchUpdateGdxxFphh(List<BdcGdxxFphhDO> bdcGdxxFphhDOList) {
        if(CollectionUtils.isNotEmpty(bdcGdxxFphhDOList)) {
            entityMapper.batchSaveSelective(bdcGdxxFphhDOList);
        }
    }

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param gdxxid
     * @return daId
     * @description 根据xmid查询daid
     */
    @Override
    public BdcGdxxFphhDO getDaIdById(String gdxxid) {
        return entityMapper.selectByPrimaryKey(BdcGdxxFphhDO.class, gdxxid);
    }

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param bdcGdxxFphhQO
     * @return List<BdcGdxxFphhDO>
     * @description 查询归档信息集合
     */
    @Override
    public List<BdcGdxxFphhDO> listBdcGdxxFphh(BdcGdxxFphhQO bdcGdxxFphhQO) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getMlh())) {
            map.put("mlh", bdcGdxxFphhQO.getMlh());
        }
        if (Objects.nonNull(bdcGdxxFphhQO.getSxh())) {
            map.put("sxh", bdcGdxxFphhQO.getSxh());
        }
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhFirstHalf()) && StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhStart())) {
            map.put("ajhStart", bdcGdxxFphhQO.getAjhFirstHalf()+"-"+bdcGdxxFphhQO.getAjhStart());
        }
        if (StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhFirstHalf()) && StringUtils.isNotBlank(bdcGdxxFphhQO.getAjhEnd())) {
            map.put("ajhEnd", bdcGdxxFphhQO.getAjhFirstHalf()+"-"+bdcGdxxFphhQO.getAjhEnd());
        }
            return bdcGdxxFphhMapper.listBdcGdxx(map);
    }

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param gdxxid
     * @return BdcGdxxFphhDO
     * @description 根据主键查询归档信息
     */
    @Override
    public BdcGdxxFphhDO queryBdcGdxxFphh(String gdxxid) {
        if (StringUtils.isNotBlank(gdxxid)) {
            return entityMapper.selectByPrimaryKey(BdcGdxxFphhDO.class, gdxxid);
        }
        return null;
    }

    /**
     * @param pageable
     * @param bdcGdxxDahQO
     * @return BdcGdxxFphhDO
     * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 分页获取归档分盒信息 全参数
     */
    @Override
    public Page<BdcGdxxFphhDO> listGdxxTable(Pageable pageable, BdcGdxxDahQO bdcGdxxDahQO) {
        return repo.selectPaging("listBdcGdxxFphhByPageOrder", bdcGdxxDahQO, pageable);
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGdxxFphhDO 档案信息
     * @return String 档案信息记录主键ID
     * @description 保存档案号
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveDah(BdcGdxxFphhDO bdcGdxxFphhDO) {
        if(null == bdcGdxxFphhDO) {
            throw new MissingArgumentException("保存档案号异常：未定义档案号信息");
        }
        if(StringUtils.isBlank(bdcGdxxFphhDO.getSlbh()) && StringUtils.isBlank(bdcGdxxFphhDO.getXmid())) {
            throw new MissingArgumentException("保存档案号异常：未定义关联项目信息");
        }
        if(StringUtils.isNotBlank(bdcGdxxFphhDO.getXmid())){
            Example example = new Example(BdcGdxxFphhDO.class);
            example.createCriteria().andEqualTo("xmid", bdcGdxxFphhDO.getXmid());
            entityMapper.deleteByExampleNotNull(example);
        }else{
            // 有些件档案号是固定的，用户多次点击打印需要将档案号已有记录先清除
            Example example = new Example(BdcGdxxFphhDO.class);
            example.createCriteria().andEqualTo("ajh", bdcGdxxFphhDO.getAjh());
            entityMapper.deleteByExampleNotNull(example);
        }


        bdcGdxxFphhDO.setGdxxid(UUIDGenerator.generate16());
        entityMapper.insertSelective(bdcGdxxFphhDO);
        logger.info("保存档案信息：{}", JSON.toJSONString(bdcGdxxFphhDO));
        return bdcGdxxFphhDO.getGdxxid();
    }

    /**
     * @param bdcGdxxFphhQO
     * @return
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    @Override
    public List<BdcGdxxFphhDO> getBdcGdxxFphhDOList(BdcGdxxFphhQO bdcGdxxFphhQO){
        logger.info("获取档案号查询条件：{}", JSON.toJSONString(bdcGdxxFphhQO));
        return bdcGdxxFphhMapper.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
    }

    @Override
    public String getMaxLsh(BdcGdxxFphhQO bdcGdxxFphhQO){
        if(bdcGdxxFphhQO.getDahscsj() == null ){
            throw new MissingArgumentException("缺失参数： 档案号生成dahscsj为空");
        }
        return bdcGdxxFphhMapper.getMaxLsh(bdcGdxxFphhQO);
    }
}
