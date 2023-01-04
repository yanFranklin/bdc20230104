package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDyaDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.qo.inquiry.count.DyaTjQO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcDyaTjVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/9/3 14:56
 * @description
 */
public interface BdcDyaCxService {
    /**
     * 分页查询抵押不动产单元
     *
     * @param pageable
     * @param bdcDyaQo
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcDyaDTO                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ;>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<BdcDyaDTO> listBdcDyaByPage(@RequestBody Pageable pageable, BdcDyaQo bdcDyaQo);

    /**
     * @param dyaTjQO 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计月报信息
     */
    List<BdcDyaTjVO> getDyaTjMonth(DyaTjQO dyaTjQO);

    /**
     * @param dyaTjQO 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计日报信息
     */
    List<BdcDyaTjVO> getDyaTjDay(DyaTjQO dyaTjQO);

    /**
     * @param redisKey 保存redis的key值
     * @param dylx     抵押统计的打印类型
     * @return 打印xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计的xml信息
     */
    String getDyaTjXml(String redisKey, String dylx);

    /**
     * @param dylx         打印类型
     * @param listDyatjStr 打印统计信息
     * @return 保存到redis的key值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存抵押提交信息到redis
     */
    String saveDyatjXxToRedis(String dylx, String listDyatjStr);

    /**
     * 抵押分页查询-标准版：查询抵押权表
     *
     * @param pageable pageable
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<BdcDyaDTO> listStandardDyaByPage(Pageable pageable, BdcDyaQo bdcDyaQo);

    /**
     * 标准版: 抵押分页查询：查询预告预抵押信息
     * @param pageable pageable
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Page<BdcDyaDTO> listStandardYgDyaByPage(Pageable pageable, BdcDyaQo bdcDyaQo);

    /**
     * 抵押分页查询-常州版：查询抵押权表
     *
     * @param pageable pageable
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    Page<BdcDyaDTO> listChangzhouDyaByPage(Pageable pageable, BdcDyaQo bdcDyaQo);

    /**
     * 抵押查询-标准版：查询抵押权表
     *
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    List listStandardDya(BdcDyaQo bdcDyaQo);

    /**
     * 标准版: 抵押查询：查询预告预抵押信息
     * @param  bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcDyaDTO> listStandardYgDya(BdcDyaQo bdcDyaQo);

    /**
     * 抵押查询-常州版：查询抵押权表
     *
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    List listChangzhouDya(BdcDyaQo bdcDyaQo);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询抵押
     */
    List listDyaByXmid(String xmid);

    /**
     * 抵押分页查询-蚌埠版：查询抵押权表
     *
     * @param pageable pageable
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    Page<BdcDyaDTO> listBengbuDyaByPage(Pageable pageable, BdcDyaQo bdcDyaQo);

    /**
     * 抵押查询-蚌埠版：查询抵押权表
     *
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    List listBengbuDya(BdcDyaQo bdcDyaQo);

    /**
     * 查询抵押全表 qszt！=2
     *
     * @param bdcdyh
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    List listDyaByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    List<BdcDyaqDO> listBdcDyaqByBdcdyhs(List<String> bdcdyhList);

    /**
     * 查询抵押全表
     *
     * @param bdcdyh
     * @param qszt
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    List listDyaByBdcdyhAndqszt(String bdcdyh, Integer qszt);

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @version 2022/05/20
     * @description 获取产权的不动产权证号
     */
    String getCqBdcqzhByXmxx(BdcDyaDTO bdcDyaDTO);
    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
     * @version 2022/09/21
     * @description 根据地籍号查询抵押权表现势的在建工程抵押
     */
    List<BdcDyaqDO> listDyaByDjhAndQszt(String djh, Integer qszt,Integer dybdclx);
}
