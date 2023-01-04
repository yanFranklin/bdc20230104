package cn.gtmap.realestate.init.service.xmxx;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcBdcdyService;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.metadata.ClassMappingMetadata;
import org.dozer.metadata.MappingMetadata;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/10.
 * @description 项目附表加载服务
 */
@ConfigurationProperties(prefix = "init.default")
public abstract class InitBdcXmFbAbstractService implements InitService {
    private static Logger logger = LoggerFactory.getLogger(InitBdcXmFbAbstractService.class);

    /**
     * 获取默认领证方式的配置
     */
    private Map<Integer,String> mrlzfsmap;
    /**
     * 获取预告登记种类的配置
     */
    private Map<String,Integer> ygdjzl;

    @Autowired
    protected EntityMapper entityMapper;

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private BdcBdcdyService bdcBdcdyService;

    @Resource(name = "initDozerMapper")
    private DozerBeanMapper initDozerMapper;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getVal() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(initServiceQO.getBdcXm() != null){
            BdcXmFbDO bdcXmFbDO = new BdcXmFbDO();
            bdcXmFbDO.setXmid(initServiceQO.getXmid());
            bdcXmFbDO.setGzlslid(initServiceQO.getBdcXm().getGzlslid());
            //如果是重新读取权籍数据的操作
            if(initServiceQO.isSfzqlpbsj() &&initServiceQO.getBdcXmFb() != null){
                // 传入参数赋值
                dozerUtils.sameBeanDateConvert(initServiceQO.getBdcXmFb(), bdcXmFbDO, false);
            }
            String qlsjly=initServiceQO.getBdcCshFwkgSl().getQlsjly();
            //获取当前登录用户所在区
            String qx = initServiceDTO.getBdcXm().getQxdm();
            if(StringUtils.isNotBlank(qlsjly)) {
                for (String ly : qlsjly.split(",")) {
                    if (StringUtils.equals(Constants.QLSJLY_LPB, ly)) {
                        readLpbData(bdcXmFbDO,initServiceQO);
                        //将当前用户区县带入dagsd
                        bdcXmFbDO.setDagsd(qx);
                    } if(StringUtils.equals(Constants.QLSJLY_WLZS_HFTDZ,ly)){
                        readWlzsData(bdcXmFbDO,initServiceQO);
                    } else {
                        readYxmData(bdcXmFbDO,initServiceQO);
                        //若上一手dagsd无值则带入当前qxdm
                        if (StringUtils.isBlank(bdcXmFbDO.getDagsd())){
                            bdcXmFbDO.setDagsd(qx);
                        }
                    }
                }
            }

            //检测权籍中无值但是目标对象中有值的
            if(initServiceQO.isSfymtbqjsj()) {
                convertNull(initServiceQO, initServiceDTO, bdcXmFbDO);
            }

            //受理项目不为空的话做对照
            if(initServiceQO.getBdcSlXmDO()!=null){
                //有上一手，档案归属地，区县代码取上一手
                if(StringUtils.isNotBlank(initServiceQO.getYxmid())){
                    initServiceQO.getBdcSlXmDO().setDagsd(null);
                }
                dozerUtils.initBeanDateConvert(initServiceQO.getBdcSlXmDO(),bdcXmFbDO);
            }
            //第三方信息不为空的话做对照
            if(initServiceQO.getDsfSlxxDTO()!=null){
                dozerUtils.initBeanDateConvert(initServiceQO.getDsfSlxxDTO(),bdcXmFbDO);
            }
            if(!initServiceQO.isSfzqlpbsj()){
                // 默认领证方式
                if (mrlzfsmap != null && !mrlzfsmap.isEmpty() && bdcXmFbDO.getLzfs()==null) {
                    Integer qllx = initServiceQO.getBdcXm().getQllx();
                    String djxl = initServiceQO.getBdcXm().getDjxl();
                    Integer ygDjzl = null;
                    if (ygdjzl!=null && !ygdjzl.isEmpty()){
                        ygDjzl = ygdjzl.get(djxl);
                    }
                    for (Integer key : mrlzfsmap.keySet()) {
                        // 根据qllx设置领证方式
                        List<String> qllxs = Arrays.asList(mrlzfsmap.get(key).split(","));
                        if (qllxs.contains(String.valueOf(qllx))) {
                            bdcXmFbDO.setLzfs(key);
                        }
                    }
                    // 特殊，预告抵押是电子证照
                    if (CommonConstantUtils.QLLX_YG_DM.equals(qllx) && (CommonConstantUtils.YGDJZL_YSSPFDYYG.equals(ygDjzl) || CommonConstantUtils.YGDJZL_QTDYYG.equals(ygDjzl))) {
                        bdcXmFbDO.setLzfs(CommonConstantUtils.LZFS_DZZZ);
                    }
                }
            }
            initServiceDTO.setBdcXmFb(bdcXmFbDO);
        }
        return initServiceDTO;
    }

    private void convertNull(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO, BdcXmFbDO bdcXmFbDO) {
        logger.info("对比权籍为空的数据:{},{},当前数据{}", JSON.toJSONString(initServiceQO),
                JSON.toJSONString(initServiceDTO),
                JSON.toJSONString(bdcXmFbDO));
        try {
            String qlsjly = initServiceQO.getBdcCshFwkgSl().getQlsjly();
            if (StringUtils.isNotBlank(qlsjly)) {
                for (String ly : qlsjly.split(",")) {
                    if (StringUtils.equals(Constants.QLSJLY_LPB, ly) &&
                            bdcBdcdyService.judgeIsFwByBdcdyh(initServiceQO.getBdcdyh())) {
                        BdcXmFbDO bdcQjXmFbDO = new BdcXmFbDO();
                        bdcQjXmFbDO.setXmid(bdcXmFbDO.getXmid());
                        bdcQjXmFbDO.setGzlslid(bdcXmFbDO.getGzlslid());
                        //单独对照权籍
                        readLpbData(bdcQjXmFbDO, initServiceQO);
                        logger.info("对比权籍为空的数据,单权籍对照的数据{}", JSON.toJSONString(bdcQjXmFbDO));
                        MappingMetadata mappingMetadata = initDozerMapper.getMappingMetadata();
                        List<ClassMappingMetadata> metaMappingList = mappingMetadata.getClassMappingsBySource(initServiceQO.getBdcdyDTO().getClass());
                        logger.info("对比权籍为空的数据,使用权籍对照的字段{}", JSON.toJSONString(metaMappingList));
                        Set<String> fieldNameList = BeansResolveUtils.getSourceNullButTargetedNotNullFieldWithDozer(bdcQjXmFbDO, bdcXmFbDO, metaMappingList);
                        initServiceDTO.getXmFieldNameSet().addAll(fieldNameList);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("对比权籍为空的数据,使用权籍对照的字段错误{}", e.getMessage());
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean plDel) throws Exception {
        List<Object> deleteList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            if(plDel){
                String gzlslid=list.get(0).getGzlslid();
                if(StringUtils.isNotBlank(gzlslid)){
                    //删除项目
                    Example example=new Example(BdcXmFbDO.class);
                    example.createCriteria().andEqualTo("gzlslid",gzlslid);
                    entityMapper.deleteByExample(example);
                }
                logger.info("项目附表执行批量删除操作，对应gzlslid: {}", gzlslid);
            }else{
                for(BdcXmDO bdcXmDO:list){
                    BdcXmFbDO bdcXmFbDO=entityMapper.selectByPrimaryKey(BdcXmFbDO.class,bdcXmDO.getXmid());
                    if(bdcXmFbDO != null){
                        deleteList.add(bdcXmFbDO);
                    }
                }
            }
        }
        logger.info("项目附表待删除数据: {}", JSON.toJSONString(deleteList));
        return deleteList;
    }

    @Override
    public InitServiceDTO query(BdcXmDO bdcXmDO, InitServiceDTO initServiceDTO) throws Exception {
        if(initServiceDTO == null){
            initServiceDTO = new InitServiceDTO();
        }
        if(bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getXmid())){
            BdcXmFbDO bdcXmFbDO=entityMapper.selectByPrimaryKey(BdcXmFbDO.class,bdcXmDO.getXmid());
            if(bdcXmFbDO!=null){
                initServiceDTO.setBdcXmFb(bdcXmFbDO);
            }
        }
        return initServiceDTO;
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  读取权籍数据
      */
    private BdcXmFbDO readLpbData(BdcXmFbDO newBdcXmFb, InitServiceQO initServiceQO){
        if(bdcBdcdyService.judgeIsFwByBdcdyh(initServiceQO.getBdcdyh())){
            if(initServiceQO.getBdcdyDTO() != null){
                dozerUtils.initBeanDateConvert(initServiceQO.getBdcdyDTO(), newBdcXmFb);
            }
        }
        return newBdcXmFb;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  读取原项目数据
     */
    private BdcXmFbDO readYxmData(BdcXmFbDO newBdcXmFb, InitServiceQO initServiceQO){
        // 从原项目读取宗地所有类型
        if (StringUtils.isNotBlank(initServiceQO.getYxmid())) {
            BdcXmFbDO yxmfb = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, initServiceQO.getYxmid());
            if (yxmfb != null) {
                dozerUtils.initBeanDateConvert(yxmfb, newBdcXmFb);
            }
        }
        return newBdcXmFb;
    }


    /**
     * 读取信息从外联证书数据中
     * @param newBdcXmFb
     * @param initServiceQO
     * @return
     */
    private BdcXmFbDO readWlzsData(BdcXmFbDO newBdcXmFb, InitServiceQO initServiceQO){
        // 当原项目id不为空时
        List<BdcXmLsgxDO> bdcXmLsgxList = initServiceQO.getBdcXmLsgxList();
        if (CollectionUtils.isNotEmpty(bdcXmLsgxList)){
            List<BdcXmLsgxDO> wlXmLsgxList = bdcXmLsgxList
                    .stream()
                    .filter(bdcXmLsgxDO -> 1 == bdcXmLsgxDO.getWlxm()).collect(Collectors.toList());

            if(CollectionUtils.isEmpty(wlXmLsgxList)){
                return newBdcXmFb;
            }
            BdcXmLsgxDO bdcXmLsgxDO = wlXmLsgxList.get(0);
            /**
             * 尝试从外联的证书中获取数据放到当前的项目中
             */
            BdcXmFbDO yxmfb = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXmLsgxDO.getYxmid());

            if (Objects.nonNull(yxmfb)){
                newBdcXmFb.setSyqx(yxmfb.getSyqx());
            }
        }
        return newBdcXmFb;
    }

    public Map<Integer, String> getMrlzfsmap() {
        return mrlzfsmap;
    }

    public void setMrlzfsmap(Map<Integer, String> mrlzfsmap) {
        this.mrlzfsmap = mrlzfsmap;
    }

    public Map<String, Integer> getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(Map<String, Integer> ygdjzl) {
        this.ygdjzl = ygdjzl;
    }
}
