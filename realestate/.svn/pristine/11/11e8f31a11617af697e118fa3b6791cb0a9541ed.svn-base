package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.register.*;
import cn.gtmap.realestate.common.core.enums.BdcDjbQlMlEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDjbQlMlVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.core.dto.BdcQlNumDTO;
import cn.gtmap.realestate.register.core.mapper.*;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.realestate.register.core.service.BdcQlService;
import cn.gtmap.realestate.register.core.service.BdcXzQlService;
import cn.gtmap.realestate.register.core.service.impl.BdcFwzlServiceImpl;
import cn.gtmap.realestate.register.core.thread.DjbQlMlThread;
import cn.gtmap.realestate.register.service.BdcDjbxxService;
import cn.gtmap.realestate.register.service.BdcQlxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/8
 * @description 不动产登记簿信息业务服务实现类
 */
@Service
public class BdcDjbxxServiceImpl implements BdcDjbxxService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDjbxxServiceImpl.class);

    @Autowired
    BdcBdcdjbMapper bdcBdcdjbMapper;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcQlxxService bdcQlxxService;
    @Autowired
    EntityZdConvertUtils entityZdConvertUtils;
    @Autowired
    BeansResolveUtils beansResolveUtils;
    @Autowired
    BdcZdCache bdcZdCache;
    @Autowired
    ThreadEngine threadEngine;
    @Autowired
    Set<BdcXzQlService> bdcXzQlServiceSet;
    @Autowired
    Set<BdcQlService> bdcQlServiceSet;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlPrintFeignService bdcSlPrintFeignService;
    @Autowired
    BdcXmMapper bdcXmMapper;
    @Autowired
    BdcFdcqMapper bdcFdcqMapper;
    @Autowired
    BdcDyaqMapper bdcDyaqMapper;
    @Autowired
    BdcCfMapper bdcCfMapper;
    @Autowired
    BdcJzqMapper bdcJzqMapper;
    @Autowired
    BdcQlrMapper bdcQlrMapper;

    public static final String KEY_NAME = "name";
    public static final String KEY_URL = "url";
    public static final String KEY_QLLX = "qllx";

    // 配置信息
    @Value("${djb.djbmlLoadZdBdcdyh:false}")
    Boolean djbmlLoadZdBdcdyh;


    /**
     * @param zdzhh 宗地宗海号
     * @return List<BdcQlDJMlDTO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产登记目录
     */
    @Override
    public List<BdcQlDjMlDTO> listBdcQlDjMl(String zdzhh) {
        if (StringUtils.isNotBlank(zdzhh)) {
            Map map = new HashMap(2);
            map.put("zdzhh", zdzhh);
            return bdcBdcdjbMapper.listBdcQlDjMlByPage(map);
        }
        return new ArrayList();
    }

    /**
     * @param zdzhh  宗地宗海号
     * @param bdcdyh
     * @return bdcdyh 不动产单元号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元在登记簿的权利信息，包括返回在登记簿的排序
     */
    @Override
    public BdcQlDjMlDTO indexBdcdyhQlDjMl(String zdzhh, String bdcdyh) {
        if (StringUtils.isNotBlank(zdzhh) && StringUtils.isNotBlank(bdcdyh)) {
            Map map = new HashMap(2);
            map.put("zdzhh", zdzhh);
            map.put("bdcdyh", bdcdyh);
            map.put("isxn", BdcdyhToolUtils.checkXnbdcdyh(bdcdyh));
            List<BdcQlDjMlDTO> bdcQlDjMlDTOS = bdcBdcdjbMapper.listBdcQlDjMlByPage(map);
            if (CollectionUtils.isNotEmpty(bdcQlDjMlDTOS) && null != bdcQlDjMlDTOS.get(0)) {
                return bdcQlDjMlDTOS.get(0);
            }
        }
        return new BdcQlDjMlDTO();
    }

    /**
     * @param zdzhh 宗地宗海号
     * @return BdcBdcdjbDO 不动产登记簿
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产登记簿信息
     */
    @Override
    public BdcBdcdjbDO queryBdcBdcdjb(String zdzhh) {
        if (StringUtils.isNotBlank(zdzhh)) {
            return entityMapper.selectByPrimaryKey(BdcBdcdjbDO.class, zdzhh);
        }
        return null;
    }

    /**
     * @param bdcdyh 宗地不动产单元号
     * @return BdcBdcdjbZdjbxxDO 不动产宗地基本信息
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产宗地基本信息
     */
    @Override
    public BdcBdcdjbZdjbxxDO queryBdcBdcdjbZdjbxx(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(BdcBdcdjbZdjbxxDO.class);
            example.createCriteria().andEqualTo("bdcdyh", bdcdyh);
            List<BdcBdcdjbZdjbxxDO> bdcBdcdjbZdjbxxList = entityMapper.selectByExampleNotNull(example);
            if (CollectionUtils.isNotEmpty(bdcBdcdjbZdjbxxList)) {
                return bdcBdcdjbZdjbxxList.get(0);
            }
        }
        return null;
    }

    /**
     * @param zddm 宗地代码
     * @return List<BdcBdcdjbZdjbxxZdbhqkDO> 不动产宗地变化情况列表
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 查询不动产宗地变化情况
     */
    @Override
    public List<BdcBdcdjbZdjbxxZdbhqkDO> listBdcBdcdjbZdjbxxZdbhqk(String zddm) {
        if (StringUtils.isNotBlank(zddm)) {
            Example example = new Example(BdcBdcdjbZdjbxxZdbhqkDO.class);
            example.createCriteria().andEqualTo("zddm", zddm);
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList();
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {BdcBdcdjbZhjbxxDO} 宗海基本信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息
     */
    @Override
    public BdcBdcdjbZhjbxxDO queryBdcBdcdjbZhjbxx(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            return null;
        }

        //条件查询宗海基本信息
        Example example = new Example(BdcBdcdjbZhjbxxDO.class);
        example.createCriteria().andEqualTo("bdcdyh", bdcdyh);
        List<BdcBdcdjbZhjbxxDO> zhjbxxDOList = entityMapper.selectByExampleNotNull(example);

        //一个不动产单元号只能对应一条宗海基本信息，所以直接选出唯一一条数据
        if (CollectionUtils.isEmpty(zhjbxxDOList)) {
            return null;
        }
        return (BdcBdcdjbZhjbxxDO) CollectionUtils.get(zhjbxxDOList, 0);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 宗海基本信息用海状况
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询宗海基本信息用海状况
     */
    @Override
    public List<BdcBdcdjbZhjbxxYhzkDO> listBdcBdcdjbZhjbxxYhzk(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            return Collections.emptyList();
        }

        /**
         * 不走SQL关联查询，直接两次单表查询
         */
        //1、根据不动产单元号查询宗海基本信息
        BdcBdcdjbZhjbxxDO zhjbxx = this.queryBdcBdcdjbZhjbxx(bdcdyh);
        if (null == zhjbxx || StringUtils.isBlank(zhjbxx.getZhdm())) {
            return Collections.emptyList();
        }

        //2、根据宗海代码查询用海状况
        Example example = new Example(BdcBdcdjbZhjbxxYhzkDO.class);
        example.createCriteria().andEqualTo("zhdm", zhjbxx.getZhdm());
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 宗海基本信息用海用岛坐标
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的用海用岛坐标
     */
    @Override
    public List<BdcBdcdjbZhjbxxYhydzbDO> listBdcBdcdjbZhjbxxYhydzb(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            return Collections.emptyList();
        }

        /**
         * 不走SQL关联查询，直接两次单表查询
         */
        //1、根据不动产单元号查询宗海基本信息
        BdcBdcdjbZhjbxxDO zhjbxx = this.queryBdcBdcdjbZhjbxx(bdcdyh);
        if (null == zhjbxx || StringUtils.isBlank(zhjbxx.getZhdm())) {
            return Collections.emptyList();
        }

        //2、根据宗海代码查询用海用岛坐标
        Example example = new Example(BdcBdcdjbZhjbxxYhydzbDO.class);
        example.createCriteria().andEqualTo("zhhddm", zhjbxx.getZhdm());
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 宗海基本信息宗海变化情况
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的宗海变化情况
     */
    @Override
    public List<BdcBdcdjbZhjbxxZhbhqkDO> listBdcBdcdjbZhjbxxZhbhqk(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            return Collections.emptyList();
        }

        /**
         * 不走SQL关联查询，直接两次单表查询
         */
        //1、根据不动产单元号查询宗海基本信息
        BdcBdcdjbZhjbxxDO zhjbxx = this.queryBdcBdcdjbZhjbxx(bdcdyh);
        if (null == zhjbxx || StringUtils.isBlank(zhjbxx.getZhdm())) {
            return Collections.emptyList();
        }

        //2、根据宗海代码查询宗海变化情况
        Example example = new Example(BdcBdcdjbZhjbxxZhbhqkDO.class);
        example.createCriteria().andEqualTo("zhdm", zhjbxx.getZhdm());
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param qsztList
     * @return BdcQlQtsxDTO 不动产权利及其他事项DTO对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产权利及其他事项信息
     */
    @Override
    public BdcQlQtsxDTO queryBdcQlQtSx(String bdcdyh, List<Integer> qsztList) {
        // 获取各权利的页码
        BdcQlQtsxDTO bdcQlQtsxDTO = generateBdcQlYm(bdcdyh, qsztList);
        if (StringUtils.isNotBlank(bdcdyh) && null != bdcQlQtsxDTO) {
            // 获取不动产单元的产权权利类型(同时存在非房屋产权和房屋产权时，以房屋产权为主)
            BdcDjbQlDTO bdcDjbQlDTO = new BdcDjbQlDTO();
            // 房屋产权
            bdcDjbQlDTO = bdcQlxxService.queryBdcFwCqQllx(bdcDjbQlDTO, bdcdyh, qsztList);
            if (null != bdcDjbQlDTO.getFwQllx()) {
                bdcQlQtsxDTO.setQllx(bdcDjbQlDTO.getFwQllx());
                return bdcQlQtsxDTO;
            }
            // 非房屋产权
            bdcDjbQlDTO = bdcQlxxService.queryBdcQtCqQllx(bdcDjbQlDTO, bdcdyh, qsztList);
            if (null != bdcDjbQlDTO.getQtCqQllx()) {
                bdcQlQtsxDTO.setQllx(bdcDjbQlDTO.getQtCqQllx());
                return bdcQlQtsxDTO;
            }
        }
        return bdcQlQtsxDTO;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param qsztList
     * @return BdcDjbQlDTO 登记簿权利信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的登记簿权利信息（原遗留数据转换为登记数据后，一个不动产单元号会有土地产权和房屋产权两种产权）
     */
    @Override
    public BdcDjbQlDTO queryBdcDjbQl(String bdcdyh, List<Integer> qsztList) {
        BdcDjbQlDTO bdcDjbQlDTO = new BdcDjbQlDTO();

        // 土地、林权、海域等其他产权信息
        bdcDjbQlDTO = bdcQlxxService.queryBdcQtCqQllx(bdcDjbQlDTO, bdcdyh, qsztList);
        if (null != bdcDjbQlDTO.getQtCqQllx()) {
            bdcDjbQlDTO.setQtCq("qtcq");
        }
        // 房屋产权
        bdcDjbQlDTO = bdcQlxxService.queryBdcFwCqQllx(bdcDjbQlDTO, bdcdyh, qsztList);
        if (null != bdcDjbQlDTO.getFwQllx()) {
            bdcDjbQlDTO.setFwCq("fwcq");
        }
        // 获取查封、异议等限制权利
        for (BdcXzQlService bdcXzQlService : bdcXzQlServiceSet) {
            if (!(bdcXzQlService instanceof BdcFwzlServiceImpl)) {
                bdcDjbQlDTO = bdcXzQlService.judgeDjbXzQl(bdcDjbQlDTO, bdcdyh, qsztList);
            }
        }
        return bdcDjbQlDTO;
    }

    /**
     * @param zdzhh    宗地宗海号
     * @param bdcdyh   不动产单元号
     * @param onlyQlfm 是否只生成权利封面url
     * @return List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成登记簿目录树
     */
    @Override
    public List generateDjbMls(String zdzhh, String bdcdyh, Boolean onlyQlfm) {
        if (StringUtils.isBlank(zdzhh) && StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("缺失查询参数zdzhh或者指定的bdcdyh");
        }
        List bdcDjbQlMlList = new ArrayList();

        // 指定不动产单元号，返回当前单元号的目录
        if (StringUtils.isNotBlank(bdcdyh)) {
            BdcDjbQlMlVO bdcDjbQlMlVO;

            // 获取房屋单元号所在宗地的目录
            if (djbmlLoadZdBdcdyh) {
                String zdBdcdyh = BdcdyhToolUtils.convertFToW(bdcdyh);
                if (!StringUtils.equals(bdcdyh, zdBdcdyh)) {
                    bdcDjbQlMlVO = generateDyhQlMl(zdBdcdyh, onlyQlfm);
                    bdcDjbQlMlList.add(bdcDjbQlMlVO);
                }
            }

            bdcDjbQlMlVO = generateDyhQlMl(bdcdyh, onlyQlfm);
            bdcDjbQlMlList.add(bdcDjbQlMlVO);
            return bdcDjbQlMlList;
        }

        // 查询当前宗地有单元的目录(超过50个，采用多线程处理)
        List<BdcQlDjMlDTO> bdcdyMlList = listBdcQlDjMl(zdzhh);
        if (CollectionUtils.isEmpty(bdcdyMlList)) {
            return bdcDjbQlMlList;
        }
        if (CollectionUtils.size(bdcdyMlList) < 50) {
            for (int i = 0; i < bdcdyMlList.size(); i++) {
                if (bdcdyMlList.get(i) != null) {
                    String bdcdyhTemp = bdcdyMlList.get(i).getBdcdyh();
                    BdcDjbQlMlVO bdcDjbQlMlVO = generateDyhQlMl(bdcdyhTemp, onlyQlfm);

                    bdcDjbQlMlList.add(bdcDjbQlMlVO);
                }
            }
            return bdcDjbQlMlList;
        }

        // 多线程处理
        List<DjbQlMlThread> djbQlMlThreadList = new ArrayList();
        for (BdcQlDjMlDTO bdcQlDjMlDTO : bdcdyMlList) {
            if (bdcQlDjMlDTO != null) {
                DjbQlMlThread djbQlMlThread = new DjbQlMlThread(bdcQlDjMlDTO, this, onlyQlfm);
                djbQlMlThreadList.add(djbQlMlThread);
            }

        }
        threadEngine.excuteThread(djbQlMlThreadList, true);

        for (DjbQlMlThread djbQlMlThread : djbQlMlThreadList) {
            bdcDjbQlMlList.add(djbQlMlThread.getDjbQlMl());
        }
        return bdcDjbQlMlList;
    }

    /**
     * @param bdcdyh   不动产单元号
     * @param onlyQlfm 是否只生成权利封面url
     * @return BdcDjbQlMlVO 权利树
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 每个单元号在登记簿中的权利目录树
     */
    @Override
    public BdcDjbQlMlVO generateDyhQlMl(String bdcdyh, Boolean onlyQlfm) {
        BdcDjbQlMlVO bdcDjbQlMlVO = new BdcDjbQlMlVO();
        bdcDjbQlMlVO.setBdcdyh(bdcdyh);
        bdcDjbQlMlVO.setBdcdyUrl(BdcDjbQlMlEnum.BdcDjbJbxxUrlEnum.BDC_DJB_BDCDYQLFM_URL.getName() + bdcdyh);
        if (onlyQlfm) {
            return bdcDjbQlMlVO;
        }
        /**
         * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
         * @description 获取不动产单元的权利信息并组织结果
         */
        List<Integer> qsztList = new ArrayList(2);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        qsztList.add(CommonConstantUtils.QSZT_HISTORY);
        BdcDjbQlDTO bdcDjbQlDTO = this.queryBdcDjbQl(bdcdyh, qsztList);
        List<Map<String, String>> bdcQlList = new ArrayList();
        bdcQlList = this.getBdcDjbQlVO(bdcQlList, bdcDjbQlDTO, bdcdyh);
        bdcDjbQlMlVO.setBdcQlList(bdcQlList);
        return bdcDjbQlMlVO;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 判断并组织不动产单元的权利目录对象
     */
    @Override
    public List<Map<String, String>> getBdcDjbQlVO(List<Map<String, String>> bdcQlList, BdcDjbQlDTO bdcDjbQlDTO, String bdcdyh) {
        // 预告
        if (StringUtils.equals(bdcDjbQlDTO.getYg(), BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_YG.getCode())) {
            Map<String, String> ygMap = new HashMap<>();
            ygMap.put(KEY_NAME, BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_YG.getName());
            ygMap.put(KEY_URL, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_YG_URL.getName() + bdcdyh);
            ygMap.put(KEY_QLLX, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_YG_URL.getCode());
            bdcQlList.add(ygMap);
        }
        // 土地、林权、海域等其他非房屋产权
        if (StringUtils.equals(bdcDjbQlDTO.getQtCq(), BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_QTCQ.getCode())) {
            Integer qllx = bdcDjbQlDTO.getQtCqQllx();
            Map<String, String> cqMap = new HashMap<>();
            cqMap.put(KEY_NAME, StringToolUtils.convertBeanPropertyValueOfZd(qllx, bdcZdCache.getZdTableList("BDC_ZD_QLLX", BdcZdQllxDO.class)) + "登记信息");
            cqMap.put(KEY_URL, getBdcDjbCqUrl(bdcdyh, qllx.toString(), bdcDjbQlDTO.getSffdcqdz()));
            cqMap.put(KEY_QLLX, qllx.toString());
            bdcQlList.add(cqMap);
        }
        // 房屋产权
        if (StringUtils.equals(bdcDjbQlDTO.getFwCq(), BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_FWCQ.getCode())) {
            Integer qllx = bdcDjbQlDTO.getFwQllx();
            Map<String, String> cqMap = new HashMap<>();
            cqMap.put(KEY_NAME, StringToolUtils.convertBeanPropertyValueOfZd(qllx, bdcZdCache.getZdTableList("BDC_ZD_QLLX", BdcZdQllxDO.class)) + "登记信息");
            cqMap.put(KEY_URL, getBdcDjbCqUrl(bdcdyh, qllx.toString(), bdcDjbQlDTO.getSffdcqdz()));
            cqMap.put(KEY_QLLX, qllx.toString());
            bdcQlList.add(cqMap);

            // 追加判断业主共有部分
            if (null != bdcDjbQlDTO.getYzgy() && bdcDjbQlDTO.getYzgy()) {
                String qllxTemp = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_JZWQFSYQYZGYBF_URL.getCode();
                Map<String, String> yzgyMap = new HashMap<>();
                yzgyMap.put(KEY_NAME, StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(qllxTemp), bdcZdCache.getZdTableList("BDC_ZD_QLLX", BdcZdQllxDO.class)) + "登记信息");
                yzgyMap.put(KEY_URL, getBdcDjbCqUrl(bdcdyh, qllxTemp, bdcDjbQlDTO.getSffdcqdz()));
                yzgyMap.put(KEY_QLLX, qllxTemp);
                bdcQlList.add(yzgyMap);
            }
        }
        // 居住权
        if (StringUtils.equals(bdcDjbQlDTO.getJzq(), BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_JZQ.getCode())) {
            Map<String, String> jzqMap = new HashMap<>();
            jzqMap.put(KEY_NAME, BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_JZQ.getName());
            jzqMap.put(KEY_URL, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_JZQ_URL.getName() + bdcdyh);
            jzqMap.put(KEY_QLLX, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_JZQ_URL.getCode());
            bdcQlList.add(jzqMap);
        }
        // 抵押
        if (StringUtils.equals(bdcDjbQlDTO.getDya(), BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_DYA.getCode())) {
            Map<String, String> dyaMap = new HashMap<>();
            dyaMap.put(KEY_NAME, BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_DYA.getName());
            dyaMap.put(KEY_URL, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_DYA_URL.getName() + bdcdyh);
            dyaMap.put(KEY_QLLX, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_DYA_URL.getCode());
            bdcQlList.add(dyaMap);
        }
        // 地役
        if (StringUtils.equals(bdcDjbQlDTO.getDyi(), BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_DYI.getCode())) {
            Map<String, String> dyiMap = new HashMap<>();
            dyiMap.put(KEY_NAME, BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_DYI.getName());
            dyiMap.put(KEY_URL, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_DYI_URL.getName() + bdcdyh);
            dyiMap.put(KEY_QLLX, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_DYI_URL.getCode());
            bdcQlList.add(dyiMap);
        }
        // 异议
        if (StringUtils.equals(bdcDjbQlDTO.getYy(), BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_YY.getCode())) {
            Map<String, String> yyMap = new HashMap<>();
            yyMap.put(KEY_NAME, BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_YY.getName());
            yyMap.put(KEY_URL, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_YY_URL.getName() + bdcdyh);
            yyMap.put(KEY_QLLX, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_YY_URL.getCode());
            bdcQlList.add(yyMap);
        }
        // 查封
        if (StringUtils.equals(bdcDjbQlDTO.getCf(), BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_CF.getCode())) {
            Map<String, String> cfMap = new HashMap(2);
            cfMap.put(KEY_NAME, BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_CF.getName());
            cfMap.put(KEY_URL, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_CF_URL.getName() + bdcdyh);
            cfMap.put(KEY_QLLX, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_CF_URL.getCode());
            bdcQlList.add(cfMap);
        }
        // 经营权
        if (StringUtils.equals(bdcDjbQlDTO.getJyq(), BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_JYQ.getCode())) {
            Map<String, String> jyqMap = new HashMap<>();
            jyqMap.put(KEY_NAME, BdcDjbQlMlEnum.BdcDjbQlEnum.BDC_DJB_JYQ.getName());
            jyqMap.put(KEY_URL, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_JYQ_URL.getName() + bdcdyh);
            jyqMap.put(KEY_QLLX, BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_JYQ_URL.getCode());
            bdcQlList.add(jyqMap);
        }
        return bdcQlList;
    }

    /**
     * @param dbxxQO 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description (宗地宗海等修改了单元的信息需要更新)更新登记簿信息
     */
    @Override
    public void updateDjbxx(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("更新登记簿信息,缺失了工作流实例ID！");
        }
        // 更新宗地基本信息
        int result = bdcBdcdjbMapper.updateZdDjbxx(dbxxQO);
        LOGGER.info("更新登记簿宗地基本{}条", result);

        // 判断对应宗地基本信息 中登记时间是否为空，为空则更新一下，避免字段为空上报失败  added by zhuyong 20200519
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(dbxxQO.getGzlslid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList) && null != bdcXmDOList.get(0) && BdcdyhToolUtils.isTdFwOrLd(bdcXmDOList.get(0).getBdcdyh())) {
            BdcXmDO param = new BdcXmDO();
            param.setDbr(dbxxQO.getDbr());
            param.setDjsj(dbxxQO.getDjsj());
            param.setBdcdyh(this.convertBdcdyh(bdcXmDOList.get(0).getBdcdyh()));

            int num = bdcBdcdjbMapper.updateZdJbxxWhenDjsjIsNull(param);
            LOGGER.info("如果对应宗地登记时间为空则更新, 当前更新信息{}条，对应土地、房屋或林地bdcdyh：{}", num, bdcXmDOList.get(0).getBdcdyh());
        }

        // 更新宗海基本信息
        result = bdcBdcdjbMapper.updateZhDjbxx(dbxxQO);
        LOGGER.info("更新登记簿宗海基本{}条", result);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 林地不动产单元号
     * @return {String} 对应宗地或林地不动产单元
     * @description  获取项目对应宗地信息中不动产单元号
     */
    private String convertBdcdyh(String bdcdyh){
        if(CommonConstantUtils.BHTZM_FW.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh))) {
            // 房屋不动产单元转换为纯土地的不动产单元
            return BdcdyhToolUtils.convertFToW(bdcdyh);
        }
        // 宗地、林地不动产单元号不变
        return bdcdyh;
    }

    /**
     * @param zdzhh 宗地宗海号
     * @return BdcBdcdjbZdjbxxDO 不动产宗地基本信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据宗地宗海号查询宗地宗海基本信息
     */
    @Override
    public BdcBdcdjbZdjbxxDO queryBdcBdcdjbZdjbxxByZdzhh(String zdzhh) {
        if (StringUtils.isNotBlank(zdzhh)) {
            Example example = new Example(BdcBdcdjbZdjbxxDO.class);
            example.createCriteria().andEqualTo("zddm", zdzhh);
            List<BdcBdcdjbZdjbxxDO> bdcBdcdjbZdjbxxList = entityMapper.selectByExampleNotNull(example);
            if (CollectionUtils.isNotEmpty(bdcBdcdjbZdjbxxList)) {
                return bdcBdcdjbZdjbxxList.get(0);
            }
        }
        return null;
    }


    /**
     * @param bdcBdcdjbZhjbxxDO 不动产登记簿宗海基本信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新宗海不动产登记簿宗海基本信息
     */
    @Override
    public int updateBdcBdcdjbZdjbxx(BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO) {
        Example example = new Example(BdcBdcdjbZhjbxxDO.class);
        example.createCriteria().andEqualTo("zhdm", bdcBdcdjbZhjbxxDO.getZhdm());
        return entityMapper.updateByExampleSelectiveNotNull(bdcBdcdjbZhjbxxDO,example);
    }

    /**
     * @param bdcBdcdjbZhjbxxYhzkDO 宗海基本信息用海状况
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新宗海基本信息用海状况
     */
    @Override
    public int updateBdcBdcdjbZhjbxxYhzk(BdcBdcdjbZhjbxxYhzkDO bdcBdcdjbZhjbxxYhzkDO) {
        Example example = new Example(BdcBdcdjbZhjbxxYhzkDO.class);
        example.createCriteria().andEqualTo("zhdm", bdcBdcdjbZhjbxxYhzkDO.getZhdm());
        return entityMapper.updateByExampleSelectiveNotNull(bdcBdcdjbZhjbxxYhzkDO,example);
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 针对于产权获取不同产权的url
     */
    private String getBdcDjbCqUrl(String bdcdyh, String qllx, String sffdcqdz) {
        String qlUrl = "";
        if (ArrayUtils.contains(BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_FDCQ_URL.getCode().split(","), qllx)) {
            if (StringUtils.equals(sffdcqdz, "1")) {
                qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_FDCQ_DZ_URL.getName();
            } else {
                qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_FDCQ_URL.getName();
            }
        }
        if (ArrayUtils.contains(BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_TDSYQ_URL.getCode().split(","), qllx)) {
            qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_TDSYQ_URL.getName();
        }
        if (ArrayUtils.contains(BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_JSYD_URL.getCode().split(","), qllx)) {
            qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_JSYD_URL.getName();
        }
        if (ArrayUtils.contains(BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_LQ_URL.getCode().split(","), qllx)) {
            qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_LQ_URL.getName();
        }
        if (ArrayUtils.contains(BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_NYD_URL.getCode().split(","), qllx)) {
            qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_NYD_URL.getName();
        }
        if (ArrayUtils.contains(BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_GZW_URL.getCode().split(","), qllx)) {
            qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_GZW_URL.getName();
        }
        if (ArrayUtils.contains(BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_HY_URL.getCode().split(","), qllx)) {
            qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_HY_URL.getName();
        }
        if (ArrayUtils.contains(BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_QT_URL.getCode().split(","), qllx)) {
            qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_QT_URL.getName();
        }
        if (ArrayUtils.contains(BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_JZWQFSYQYZGYBF_URL.getCode().split(","), qllx)) {
            qlUrl = BdcDjbQlMlEnum.BdcDjbQlUrlEnum.BDC_DJB_JZWQFSYQYZGYBF_URL.getName();
        }
        return qlUrl + bdcdyh;
    }

    private BdcQlQtsxDTO generateBdcQlYm(String bdcdyh, List<Integer> qsztList) {
        // 登记簿中权利及其他事项的的封面是第一页，具体的权利信息从第2页开始
        Integer ym = 2;
        if (StringUtils.isNotBlank(bdcdyh)) {
            BdcQlQtsxDTO bdcQlQtsxDTO = new BdcQlQtsxDTO();

            // 产权的总页数（不需要查询所有产权，所以将产权和限制权利区分开查询）
            Integer cqdjzys = bdcQlxxService.calculateCqYs(bdcdyh, qsztList);
            // 建筑物区分所有权业主共有部分登记信息（每个房屋不动产单元号做登记时，如果宗地上有共有部分，同时展示共有部分登记簿那一页）
            Integer yzgydjzys = bdcQlxxService.calculateYzgyYs(bdcdyh);

            // 获取限制权利各个权利的数量
            BdcQlNumDTO bdcQlNumDTO = new BdcQlNumDTO();
            for (BdcXzQlService bdcXzQlService : bdcXzQlServiceSet) {
                bdcQlNumDTO = bdcXzQlService.countBdcQl(bdcQlNumDTO, bdcdyh, qsztList);
            }
            // 预告的总页数
            Integer ygdjzys = bdcQlxxService.calculateQlYs(bdcQlNumDTO.getYgNum());
            // 抵押的总页数
            Integer dyadjzys = bdcQlxxService.calculateQlYs(bdcQlNumDTO.getDyaqNum());
            // 地役权的总页数
            Integer dyidjzys = bdcQlxxService.calculateQlYs(bdcQlNumDTO.getDyiqNum());
            // 异议登记的总页数
            Integer yydjzys = bdcQlxxService.calculateQlYs(bdcQlNumDTO.getYyNum());
            // 查封登记的总页数
            Integer cfdjzys = bdcQlxxService.calculateQlYs(bdcQlNumDTO.getCfNum());
            //经营权登记的总页数
            Integer jyqdjzys = bdcQlxxService.calculateQlYs(bdcQlNumDTO.getJyqNum());
            // 居住权的总页数
            Integer jzqzys = bdcQlxxService.calculateQlYs(bdcQlNumDTO.getJzqNum());

            // 以下计算按照自己的默认规则
            bdcQlQtsxDTO.setYgdjym(bdcQlxxService.generateYm(ygdjzys, ym));
            bdcQlQtsxDTO.setDjym(bdcQlxxService.generateYm(cqdjzys, ym + ygdjzys));
            bdcQlQtsxDTO.setYzgydjym(bdcQlxxService.generateYm(yzgydjzys, ym + ygdjzys + cqdjzys));
            bdcQlQtsxDTO.setJzqdjym(bdcQlxxService.generateYm(jzqzys, ym + ygdjzys + cqdjzys +yzgydjzys));
            bdcQlQtsxDTO.setDyadjym(bdcQlxxService.generateYm(dyadjzys, ym + ygdjzys + cqdjzys + yzgydjzys + jzqzys));
            bdcQlQtsxDTO.setDyidjym(bdcQlxxService.generateYm(dyidjzys, ym + ygdjzys + cqdjzys + yzgydjzys + jzqzys+ dyadjzys));
            bdcQlQtsxDTO.setYydjym(bdcQlxxService.generateYm(yydjzys, ym + ygdjzys + cqdjzys + yzgydjzys + jzqzys + dyadjzys + dyidjzys));
            bdcQlQtsxDTO.setCfdjym(bdcQlxxService.generateYm(cfdjzys, ym + ygdjzys + cqdjzys + yzgydjzys + jzqzys + dyadjzys + dyidjzys + yydjzys));
            bdcQlQtsxDTO.setJyqdjym(bdcQlxxService.generateYm(jyqdjzys, ym + ygdjzys + cqdjzys + yzgydjzys + jzqzys + dyadjzys + dyidjzys + yydjzys +cfdjzys));
            return bdcQlQtsxDTO;
        }
        return null;
    }
}
