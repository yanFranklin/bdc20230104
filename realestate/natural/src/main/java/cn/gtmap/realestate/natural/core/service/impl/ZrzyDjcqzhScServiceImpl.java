package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyCqzhDTO;
import cn.gtmap.realestate.common.core.enums.BdcZslxEnum;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.natural.core.bo.ZrzyZscCqzhBO;
import cn.gtmap.realestate.natural.core.service.ZrzyZsDjcqzhService;
import cn.gtmap.realestate.natural.service.ZrzyZsService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/10
 * @description 生成不动产权证号综合逻辑处理
 */
@Service
public class ZrzyDjcqzhScServiceImpl {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyDjcqzhScServiceImpl.class);
    /**
     * 证书逻辑
     */
    @Autowired
    ZrzyZsService bdcZsService;
    /**
     * 证号公共逻辑处理
     */
    @Autowired
    private ZrzyDjcqzhGgServiceImpl zrzyDjcqzhGgService;
    /**
     * 默认处理方式
     */
    @Resource(name = "zrzyDjcqzhMrServiceImpl")
    private ZrzyZsDjcqzhService zrzyZsDjcqzhService;

    /**
     * 版本
     */
    @Value("${certificate.version:null}")
    private String certificateVersion;

    /**
     * 生成证号是否采用锁方案，默认true采用
     */
    @Value("${bdcqzh.needlock:true}")
    private Boolean bdcqzhNeedLock;


    /**
     * @param zrzyZscCqzhBO 信息实体
     * @return {List} 不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （换证场景）生成不动产项目证书（明）号
     * <p>
     * 1、加锁，120s内尝试获取锁，加锁后60s内若未手动释放锁则自动释放锁
     * 2、为保证同一个项目对应的证号相对连续及取号避免冲突，加锁粒度对整个证书集合加
     * </p>
     * @see cn.gtmap.realestate.common.util.redisson.RedissonLockService
     */
    @Transactional(rollbackFor = Exception.class)
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_BDCQZH, description = "获取不动产证书（明）号")
    public List<ZrzyCqzhDTO> generateZrzycqzh(ZrzyZscCqzhBO zrzyZscCqzhBO) {
        return zrzycqzh(zrzyZscCqzhBO);
    }

    /**
     * @param zrzyZscCqzhBO 信息实体
     * @return {List} 不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （换证场景）生成不动产项目证书（明）号 (不采用加锁方案)
     */
    @Transactional(rollbackFor = Exception.class)
    public List<ZrzyCqzhDTO> generateZrzycqzhWithNoLock(ZrzyZscCqzhBO zrzyZscCqzhBO) {
        return zrzycqzh(zrzyZscCqzhBO);
    }

    /**
     * @param zrzyZscCqzhBO 信息实体
     * @return {List} 不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成证号
     */
    private List<ZrzyCqzhDTO> zrzycqzh(ZrzyZscCqzhBO zrzyZscCqzhBO) {
        // 获取证书（如果加锁方案一定需要放到同步块中，否则同一项目并发会导致问题）
        List<ZrzyZsDO> zrzyZsDOList = zrzyDjcqzhGgService.getZrzyZs(zrzyZscCqzhBO.getXmid());
        List<ZrzyCqzhDTO> bdcBdcqzhDTOList = new ArrayList<>(zrzyZsDOList.size());

        for (ZrzyZsDO zrzyZsDO : zrzyZsDOList) {
            if (null == zrzyZsDO) {
                continue;
            }

            ZrzyCqzhDTO zrzyCqzhDTO;
            if (StringUtils.isBlank(zrzyZsDO.getZrzycqzh())) {

                // 获取证号
                zrzyZscCqzhBO.setZsid(zrzyZsDO.getZsid());
                zrzyZscCqzhBO.setZslx(ZrzyDjcqzhGgServiceImpl.ZSLX);
                zrzyCqzhDTO = resolveZrzycqzh(zrzyZscCqzhBO);
                // 更新保存证号
                zrzyDjcqzhGgService.saveZrzycqzh(zrzyCqzhDTO);
            } else {
                // 证号不为空，则直接返回
                zrzyCqzhDTO = zrzyDjcqzhGgService.generateDqBdcBdcqzh(zrzyZsDO);
                LOGGER.info("证号不为空，无需生成：{}", JSON.toJSONString(zrzyCqzhDTO));
            }
            bdcBdcqzhDTOList.add(zrzyCqzhDTO);
        }
        return bdcBdcqzhDTOList;
    }


    /**
     * @param zrzyZscCqzhBO 证号业务BO
     * @return {List}  不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成指定证书的证号
     * 1、需要依次判断从预留证号、废号、默认方式中取号
     * 2、如果上一种方式已经取到就返回，取不到就继续下一个方式
     */
    private ZrzyCqzhDTO resolveZrzycqzh(ZrzyZscCqzhBO zrzyZscCqzhBO) {
        // 默认取号
        return zrzyZsDjcqzhService.resolveZrzycqzh(zrzyZscCqzhBO);
    }

    /**
     * @param bdcBdcqzhBO 证号业务BO
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取不动产权证号前缀
     */
    private String getZrzycqzhPrefix(ZrzyZscCqzhBO bdcBdcqzhBO) {
        StringBuilder builder = new StringBuilder();
        builder.append(bdcBdcqzhBO.getSqsjc());
        builder.append(CommonConstantUtils.BDCQ_BH_LEFT_BRACKET);
        builder.append(bdcBdcqzhBO.getNf());
        builder.append(CommonConstantUtils.BDCQ_BH_RIGHT_BRACKET);
        builder.append(bdcBdcqzhBO.getSzsxqc());
        builder.append(BdcZslxEnum.getZhlx(bdcBdcqzhBO.getZslx(), certificateVersion));
        builder.append("第");
        return builder.toString();
    }

    /**
     * 同种类型的证书类型生成证号内容
     *
     * @param zhxxList
     */
    private List<ZrzyCqzhDTO> scZhxx(List<ZrzyCqzhDTO> zhxxList) {
        if (CollectionUtils.isEmpty(zhxxList)) {
            return zhxxList;
        }

        // 证书ID 对应的 记录索引
        Map<String, List<Integer>> zsidMap = new HashMap<>(zhxxList.size());
        for (int i = 0; i < zhxxList.size(); i++) {
            String zsid = zhxxList.get(i).getZsid();

            if (zsidMap.containsKey(zsid)) {
                zsidMap.get(zsid).add(new Integer(i));
            } else {
                List<Integer> list = new ArrayList<>(5);
                list.add(new Integer(i));
                zsidMap.put(zsid, list);
            }
        }

        // 根据第一个项目获取证号模板，批量的都一样
        ZrzyZscCqzhBO bdcBdcqzhBO = zrzyDjcqzhGgService.getBdcqzhBO(zhxxList.get(0).getXmid());
        bdcBdcqzhBO.setZsid(zhxxList.get(0).getZsid());
        bdcBdcqzhBO.setZslx(zhxxList.get(0).getZslx());

        Set set = new HashSet();
        for (ZrzyCqzhDTO zrzyCqzhDTO : zhxxList) {
            set.add(zrzyCqzhDTO.getZsid());
        }

        // 获取数据库当前最大顺序号
        int startMaxSxh;
        if (bdcqzhNeedLock) {
            // 从数据库取
            startMaxSxh = bdcZsService.queryMaxSxh(bdcBdcqzhBO);

            if (null != bdcBdcqzhBO.getCssxh() && bdcBdcqzhBO.getCssxh().intValue() > startMaxSxh) {
                startMaxSxh = bdcBdcqzhBO.getCssxh().intValue();
            }
        } else {
            // 采用错误重试方案，从Redis取值，批量获取当前件数量的号
            int redisValue = zrzyDjcqzhGgService.getNextSxhFromRedis(bdcBdcqzhBO, Integer.valueOf(set.size()).longValue());
            // 例如当前redis最大号 10 ，当前件5个件，则incr操作后，redis值为 15，则当前可用号从 15-5+1=11 开始，11、12、13、14、15 共5个号
            startMaxSxh = redisValue - set.size();
            LOGGER.info("批量件获取号从{}到{}", startMaxSxh, redisValue);
        }

        String prefix = this.getZrzycqzhPrefix(bdcBdcqzhBO);

        for (ZrzyCqzhDTO zsxx : zhxxList) {
            if (StringUtils.isNotBlank(zsxx.getZrzycqzh())) {
                continue;
            }
            String zhlsh = String.valueOf(++startMaxSxh);
            if (zhlsh.length() < bdcBdcqzhBO.getZrzycqzhws()) {
                do {
                    zhlsh = StringUtils.join("0", zhlsh);
                } while (zhlsh.length() < bdcBdcqzhBO.getZrzycqzhws());
            }
            /// 拼接所在区号或特定编码
            if (1 == bdcBdcqzhBO.getSqdmkg().intValue()) {
                zhlsh = bdcBdcqzhBO.getSqdm() + zhlsh.substring(bdcBdcqzhBO.getSqdm().length());
            }

            // 所有共用同一本证书的记录都设置相同的证号信息
            List<Integer> indexList = zsidMap.get(zsxx.getZsid());
            for (Integer index : indexList) {
                zhxxList.get(index.intValue()).setZrzycqzh(prefix + zhlsh + "号");
                zhxxList.get(index.intValue()).setNf(bdcBdcqzhBO.getNf());
                zhxxList.get(index.intValue()).setZhlsh(zhlsh);
                zhxxList.get(index.intValue()).setSqsjc(bdcBdcqzhBO.getSqsjc());
                zhxxList.get(index.intValue()).setSzsxqc(bdcBdcqzhBO.getSzsxqc());
                zhxxList.get(index.intValue()).setZslx(bdcBdcqzhBO.getZslx());
                zhxxList.get(index.intValue()).setZhxlh(startMaxSxh);
            }

            // 更新保存证号
            zrzyDjcqzhGgService.saveZrzycqzh(zsxx);
        }
        return zhxxList;
    }

    /**
     * 将要生成证号信息的集合根据证书类型分组
     *
     * @param zhxxList
     * @return
     */
    private Map<String, List<ZrzyCqzhDTO>> qfZslx(List<ZrzyCqzhDTO> zhxxList) {
        Map<String, List<ZrzyCqzhDTO>> map = new HashMap<>();
        List<ZrzyCqzhDTO> listzs = new ArrayList<>();
        List<ZrzyCqzhDTO> listzm = new ArrayList<>();
        List<ZrzyCqzhDTO> listzmd = new ArrayList<>();

        for (ZrzyCqzhDTO bdcBdcqzhDTO : zhxxList) {
            if (bdcBdcqzhDTO.getZslx().equals(CommonConstantUtils.ZSLX_ZS)) {
                listzs.add(bdcBdcqzhDTO);
            } else if (bdcBdcqzhDTO.getZslx().equals(CommonConstantUtils.ZSLX_ZM)) {
                listzm.add(bdcBdcqzhDTO);
            } else if (bdcBdcqzhDTO.getZslx().equals(CommonConstantUtils.ZSLX_ZMD)) {
                listzmd.add(bdcBdcqzhDTO);
            }
        }
        map.put(CommonConstantUtils.ZSLX_ZS + "", listzs);
        map.put(CommonConstantUtils.ZSLX_ZM + "", listzm);
        map.put(CommonConstantUtils.ZSLX_ZMD + "", listzmd);

        return map;
    }
}