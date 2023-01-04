package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLcpzDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtZrzyzkFjPzDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzySlymYwxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyYzhVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.MbConvertUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.natural.core.bo.ZrzyZscCqzhBO;
import cn.gtmap.realestate.natural.core.mapper.ZrzyConfigMapper;
import cn.gtmap.realestate.natural.core.mapper.ZrzyZsMapper;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import cn.gtmap.realestate.natural.core.service.impl.ZrzyDjcqzhServiceImpl;
import cn.gtmap.realestate.natural.core.service.pzxx.ZrzyXtLcpzService;
import cn.gtmap.realestate.natural.service.ZrzyYwxxService;
import cn.gtmap.realestate.natural.service.ZrzyZsService;
import cn.gtmap.realestate.natural.util.Constants;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/11/5 14:28
 */
@Service
public class ZrzyZsServiceImpl implements ZrzyZsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyZsServiceImpl.class);

    @Autowired
    Repository repository;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    ZrzyZsMapper zrzyZsMapper;

    @Autowired
    private ZrzyXtLcpzService zrzyXtLcpzService;

    @Autowired
    private ZrzyXmService zrzyXmService;

    @Autowired
    private ZrzyYwxxService zrzyYwxxService;

    @Autowired
    ZrzyDjcqzhServiceImpl zrzyDjcqzhService;

    @Autowired
    ZrzyConfigMapper zrzyConfigMapper;

    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * 根据条件查询证书
     *
     * @param zrzyZsQO 证书查询对象
     * @return
     */
    @Override
    public List<ZrzyZsDO> listZryzZsByZsid(ZrzyZsQO zrzyZsQO) {
        Example example = new Example(ZrzyZsDO.class);
        example.createCriteria().andEqualTo("zsid", zrzyZsQO.getZsid());
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * 根据条件查询证书
     *
     * @param zrzyZsQO 证书查询对象
     * @return
     */
    @Override
    public List<ZrzyZsDO> listZryzZs(ZrzyZsQO zrzyZsQO) {
        Example example = new Example(ZrzyXmDO.class);
        example.createCriteria().andEqualTo("gzlslid", zrzyZsQO.getGzlslid());
        List<ZrzyXmDO> zrzyXmDOList = entityMapper.selectByExampleNotNull(example);
        if (CollUtil.isNotEmpty(zrzyXmDOList)) {
            return zrzyZsMapper.selectByXmid(zrzyXmDOList.get(0).getXmid());
        }
        return null;
    }

    /**
     * @param gzlslid
     * @param zsyl    是否证书预览
     * @param zssl    证书数量
     * @param plybz   是否是批量一本证逻辑，按项目处理的要传,gzlslid的会自己判断
     * @param sjbl    数据补录 （不生成权利其他状况和附记）
     * @return
     * @throws Exception
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ZrzyZsDO initZrzyqzs(String gzlslid, boolean zsyl, boolean zssl, Boolean plybz, boolean sjbl) throws Exception {
        List<ZrzyXtLcpzDO> zrzyXtLcpzDOS = zrzyXtLcpzService.listZrzlcshPz(gzlslid);
        ZrzyZsDO zrzyZsDO = new ZrzyZsDO();
        if (CollectionUtils.isNotEmpty(zrzyXtLcpzDOS) && zrzyXtLcpzDOS.get(0).getSfsczs().equals(Constants.SFSCZS_S)) {
            ZrzyXmDO zrzyXmDO = zrzyXmService.queryZrzyXmByGzlslid(gzlslid);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
            if (!zsyl) {
                LOGGER.info("生成证书开始:{} {}", simpleDateFormat.format(new Date()), gzlslid);
            }

            //判断当前是否已有证书
            List<ZrzyZsDO> zrzyZsDOList = queryZrzyZsByXmid(zrzyXmDO.getXmid());
            if (CollectionUtils.isNotEmpty(zrzyZsDOList) && !zsyl && !zssl) {
                //删除已有的数据
                deleteZryzZs(zrzyZsDOList);
            }
            //初始化证书数据
            initZs(gzlslid, zrzyZsDO);
            //非预览的走数据库服务
            if (!zsyl) {
                //插入证书相关数据
                entityMapper.insertSelective(zrzyZsDO);
                LOGGER.info("生成证书结束:{} ,gzlslid:{},zsid:{}", simpleDateFormat.format(new Date()), gzlslid, JSONObject.toJSONString(zrzyZsDO));
                zrzyDjcqzhService.generateCqzhOfPro(gzlslid);
            }
        } else {
            throw new EntityNotFoundException("未查询到项目的初始化实例信息!");
        }
        return zrzyZsDO;
    }

    /**
     * 初始化证书数据
     *
     * @param gzlslid
     * @param zrzyZsDO
     */
    @Override
    public void initZs(String gzlslid, ZrzyZsDO zrzyZsDO) {
        ZrzySlymYwxxDTO zrzySlymYwxxDTO = zrzyYwxxService.queryZrzySlymYwxxDTO(gzlslid);
        if (Objects.nonNull(zrzySlymYwxxDTO)) {
            zrzyZsDO.setZsid(UUIDGenerator.generate16());
            zrzyZsDO.setXmid(zrzySlymYwxxDTO.getZrzyXm().getXmid());
            zrzyZsDO.setZrzydjdyh(zrzySlymYwxxDTO.getZrzyXm().getZrzydjdyh());
            zrzyZsDO.setDjdymc(zrzySlymYwxxDTO.getZrzyDjdy().getDjdymc());
            zrzyZsDO.setDjdylx(zrzySlymYwxxDTO.getZrzyDjdy().getDjdylx());
            zrzyZsDO.setSyqzt(zrzySlymYwxxDTO.getZrzyDjdy().getSyqzt());
            zrzyZsDO.setDbxszt(zrzySlymYwxxDTO.getZrzyDjdy().getDbxszt());
            zrzyZsDO.setQlxsfs(zrzySlymYwxxDTO.getZrzyDjdy().getQlxsfs());
            zrzyZsDO.setDlxszt(zrzySlymYwxxDTO.getZrzyDjdy().getDlxszt());
            zrzyZsDO.setXsnr(zrzySlymYwxxDTO.getZrzyDjdy().getXsnr());
            zrzyZsDO.setMj(zrzySlymYwxxDTO.getZrzyDjdy().getDjdyzmj());
            zrzyZsDO.setGymj(zrzySlymYwxxDTO.getZrzyDjdy().getGymj());
            zrzyZsDO.setJtmj(zrzySlymYwxxDTO.getZrzyDjdy().getJtmj());
            zrzyZsDO.setZyqmj(zrzySlymYwxxDTO.getZrzyDjdy().getZyqmj());
            zrzyZsDO.setQxdm(zrzySlymYwxxDTO.getZrzyXm().getQxdm());
            zrzyZsDO.setKjfw(zrzySlymYwxxDTO.getZrzyDjdy().getDyszd() + Constants.KJFW_SEP
                    + zrzySlymYwxxDTO.getZrzyDjdy().getDyszx() + Constants.KJFW_SEP
                    + zrzySlymYwxxDTO.getZrzyDjdy().getDyszn() + Constants.KJFW_SEP
                    + zrzySlymYwxxDTO.getZrzyDjdy().getDyszb() + Constants.KJFW_SEP
            );
            zrzyZsDO.setFzsj(new Date());
            zrzyZsDO.setSzsj(new Date());
            zrzyZsDO.setSzr(userManagerUtils.getCurrentUserName());
            //更新自然资源证书 自然资源状况 附记
            generateAndUpdateZrzyZsFj(zrzyZsDO, false);
        }
    }

    /**
     * @param zrzyYzhVO 证书印制号模板
     * @return
     */
    @Override
    public int generateBdcZsyzh(ZrzyYzhVO zrzyYzhVO) {
//        // 判断模板必填属性值，避免前端验证无效或者直接请求接口生成不走前端
//        if(StringToolUtils.existItemNullOrEmpty(zrzyYzhVO.getNf(), zrzyYzhVO.getSqdm(), zrzyYzhVO.getQxdm())
//                || StringToolUtils.existIntegerItemNullOrEmpty(zrzyYzhVO.getZslx(), zrzyYzhVO.getBhws(), zrzyYzhVO.getQsbh(), zrzyYzhVO.getJsbh())){
//            throw new NullPointerException("证书印制号模板存在必填项为空问题！");
//        }
//        // 判断编号
//        if(zrzyYzhVO.getQsbh() > zrzyYzhVO.getJsbh()){
//            throw new AppException("错误：证书印制号模板起始编号大于结束编号！");
//        }
//        // 判断证书印制号是否已经存在
//        int num = bdcXtZsyzhMapper.countYzh(zrzyYzhVO);
//        if(num > 0){
//            throw new EntityExistsException("指定区间范围的部分证书印制号已经存在！");
//        }
//
//        // 获取用户信息（只调用一次服务）
//        UserDto userDTO = userManagerUtils.getCurrentUser();
//        String userName = null;
//        String userId = null;
//        if(null != userDTO){
//            userName = userDTO.getAlias();
//            userId = userDTO.getId();
//        }
//
//        // 生成印制号
//        int size = zrzyYzhVO.getJsbh() - zrzyYzhVO.getQsbh() + 1;
//        List<BdcYzhDO> bdcYzhDOList = new ArrayList<>(size);
//        for(int qsbh = zrzyYzhVO.getQsbh(); qsbh <= zrzyYzhVO.getJsbh(); qsbh++){
//            BdcYzhDO bdcYzhDO = new BdcYzhDO();
//            bdcYzhDO.setYzhid(UUIDGenerator.generate());
//            bdcYzhDO.setNf(zrzyYzhVO.getNf());
//            bdcYzhDO.setQxdm(zrzyYzhVO.getQxdm());
//            bdcYzhDO.setZslx(zrzyYzhVO.getZslx());
//            bdcYzhDO.setQzysxlh(this.getQzysxlh(zrzyYzhVO, qsbh));
//            // 初始状态为已领用
//            bdcYzhDO.setSyqk(BdcZssyqkEnum.YLY.getCode());
//            bdcYzhDO.setCjr(userName);
//            bdcYzhDO.setCjrid(userId);
//            bdcYzhDO.setCjsj(new Date());
//            bdcYzhDO.setLqbm(zrzyYzhVO.getLqbm());
//            bdcYzhDO.setLqbmid(zrzyYzhVO.getLqbmid());
//            bdcYzhDO.setLqr(zrzyYzhVO.getLqr());
//            bdcYzhDO.setLqrid(zrzyYzhVO.getLqrid());
//
//            bdcYzhDOList.add(bdcYzhDO);
//            /// 为了防止要生成的印制号数量太大，采用分批量保存，避免全部一次保存严重耗时
//            if(bdcYzhDOList.size() >= 1000){
//                entityMapper.insertBatchSelective(bdcYzhDOList);
//                bdcYzhDOList.clear();
//            }
//        }
//        if(CollectionUtils.isNotEmpty(bdcYzhDOList)){
//            entityMapper.insertBatchSelective(bdcYzhDOList);
//        }
//
//        return size;
        return 0;
    }

    /**
     * @param zrzyZscCqzhBO@return {Integer} 库中当前最大顺序号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取数据库中当前最大顺序号
     */
    @Override
    public int queryMaxSxh(ZrzyZscCqzhBO zrzyZscCqzhBO) {
        if (null == zrzyZscCqzhBO) {
            throw new MissingArgumentException("缺失顺序号查询参数");
        }
        return zrzyZsMapper.queryMaxSxh(zrzyZscCqzhBO);
    }

    /**
     * @param xmid 项目id
     * @return List<ZrzyZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    @Override
    public List<ZrzyZsDO> queryZrzyZsByXmid(String xmid) {
        Example example = new Example(ZrzyZsDO.class);
        example.createCriteria().andEqualTo("xmid", xmid);
        return entityMapper.selectByExampleNotNull(example);
    }

    @Override
    public int updateZrzyZs(ZrzyZsDO zrzyZsDO) {
        if (StringUtils.isBlank(zrzyZsDO.getZsid())) {
            throw new NullPointerException("更新时实体对象BdcZsDO主键zsid不能为空");
        }
        return entityMapper.updateByPrimaryKeySelective(zrzyZsDO);
    }

    /**
     * @param zrzyZscCqzhBO 证号查询实体
     * @return {Integer}  证号数量
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询指定初始顺序号到最大顺序号之间顺序号
     */
    @Override
    public LinkedHashSet<Integer> querySxh(ZrzyZscCqzhBO zrzyZscCqzhBO) {
        if (null == zrzyZscCqzhBO) {
            throw new MissingArgumentException("缺失顺序号查询参数");
        }

        return zrzyZsMapper.querySxh(zrzyZscCqzhBO);
    }

    /**
     * 删除证书
     *
     * @param deleteList
     */
    @Override
    public void deleteZryzZs(List<ZrzyZsDO> deleteList) {
        deleteList.forEach(zrzyZsDO -> {
            Example example = new Example(ZrzyZsDO.class);
            example.createCriteria().andEqualTo("xmid", zrzyZsDO.getXmid());
            entityMapper.selectByExampleNotNull(example);
        });
    }


    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcZxQl]
     * @description 更新自然资源证书 自然资源状况 附记
     */
    public ZrzyZsDO generateAndUpdateZrzyZsFj(ZrzyZsDO zrzyZsDO, Boolean isUpdate) {
        if (zrzyZsDO != null && StringUtils.isNotBlank(zrzyZsDO.getXmid())) {
            // 查询原权利对应的注销附记配置
            ZrzyXtZrzyzkFjPzDO zrzyXtZrzyzkFjPzDO = getBdcXtQlqtzkFjPz(zrzyZsDO.getXmid());
            if (zrzyXtZrzyzkFjPzDO == null) {
                LOGGER.error("未查询到自然资源状况与附记配置，项目id：{}", zrzyZsDO.getXmid());
                throw new AppException("未查询到自然资源状况与附记配置！");
            }
            if (StringUtils.isNotBlank(zrzyXtZrzyzkFjPzDO.getZrzyzkmb())) {
                // 模板转为小写
                String mb = zrzyXtZrzyzkFjPzDO.getZrzyzkmb().toLowerCase();
                if (StringUtils.isNotBlank(zrzyXtZrzyzkFjPzDO.getZrzyzksjy())) {
                    mb = replaceMbWithSjy(mb, zrzyXtZrzyzkFjPzDO.getZrzyzksjy(), zrzyZsDO.getXmid());
                }

                if (StringUtils.isNotBlank(zrzyZsDO.getZrzyzk())) {
                    String zk = zrzyZsDO.getZrzyzk() + "\n" + mb;
                    zrzyZsDO.setZrzyzk(zk);
                } else {
                    zrzyZsDO.setZrzyzk(mb);
                }
            }


            if (StringUtils.isNotBlank(zrzyXtZrzyzkFjPzDO.getFjmb())) {
                // 模板转为小写
                String mb = zrzyXtZrzyzkFjPzDO.getFjmb().toLowerCase();
                if (StringUtils.isNotBlank(zrzyXtZrzyzkFjPzDO.getFjsjy())) {
                    mb = replaceMbWithSjy(mb, zrzyXtZrzyzkFjPzDO.getFjsjy(), zrzyZsDO.getXmid());
                }

                if (StringUtils.isNotBlank(zrzyZsDO.getFj())) {
                    String fj = zrzyZsDO.getFj() + "\n" + mb;
                    zrzyZsDO.setFj(fj);
                } else {
                    zrzyZsDO.setFj(mb);
                }
            }
            if (isUpdate) {
                entityMapper.updateByPrimaryKeySelective(zrzyZsDO);
            }
        }
        return zrzyZsDO;
    }


    /**
     * @return cn.gtmap.realestate.common.core.domain.BdcXtQlqtzkFjPzDO
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmid]
     * @description 查询权利其他状况附记配置
     */
    private ZrzyXtZrzyzkFjPzDO getBdcXtQlqtzkFjPz(String xmid) {
        ZrzyXtZrzyzkFjPzDO zrzyXtZrzyzkFjPzDO = null;
        List<ZrzyXtZrzyzkFjPzDO> zrzyXtZrzyzkFjPzDOS;
        if (StringUtils.isNotBlank(xmid)) {
            ZrzyXmDO zrzyXmDO = entityMapper.selectByPrimaryKey(ZrzyXmDO.class, xmid);
            if (zrzyXmDO != null && StringUtils.isNotBlank(zrzyXmDO.getGzldyid())) {
                Example example = new Example(ZrzyXtZrzyzkFjPzDO.class);
                Example.Criteria criteria = example
                        .createCriteria()
                        .andEqualTo("gzldyid", zrzyXmDO.getGzldyid());
                // 查询权利其他状况与附记配置
                zrzyXtZrzyzkFjPzDOS = entityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(zrzyXtZrzyzkFjPzDOS)) {
                    zrzyXtZrzyzkFjPzDO = zrzyXtZrzyzkFjPzDOS.get(0);
                }
            }
        }
        return zrzyXtZrzyzkFjPzDO;
    }


    /**
     * @return java.lang.String
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [mb, sjy, yxmid]
     * @description 根据数据源结果替换模板参数
     */
    private String replaceMbWithSjy(String mb, String sjy, String xmid) {
        String[] sqls = sjy.split("；|;");
        for (String sql : sqls) {
            Map param = new HashMap();
            param.put("sql", sql);
            param.put("xmid", xmid);
            List<Map> result = zrzyConfigMapper.executeConfigSql(param);
            // 若查询数据不为空
            if (CollectionUtils.isNotEmpty(result)) {
                for (Map map : result) {
                    // 对模板中参数进行替换
                    mb = MbConvertUtils.mbParamReplace(mb, map);
                    LOGGER.info("替换参数后模板为：{}", mb);
                }
            }
        }

        // 将模板未赋值行删除
        mb = MbConvertUtils.mbUnsetRowReplace(mb);
        // 去除最后一个换行符
        if (StringUtils.isNotBlank(mb) && StringUtils.lastIndexOf(mb, CommonConstantUtils.ZF_HH_CHAR) == StringUtils.length(mb) - 1) {
            mb = StringUtils.substring(mb, 0, StringUtils.lastIndexOf(mb, CommonConstantUtils.ZF_HH_CHAR));
        }
        LOGGER.info("根据模板+数据源生成的附记：{}", mb);
        return mb;
    }
}
