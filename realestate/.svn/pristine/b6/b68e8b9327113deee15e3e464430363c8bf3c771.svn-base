package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDyaDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.qo.inquiry.count.DyaTjQO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcDyaTjVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/9/3 13:53
 * @description 抵押查询服务
 */
public interface BdcDyaCxRestService {
    /**
     * 分页查询抵押不动产单元
     *
     * @param pageable
     * @param bdcDyaQOJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcDyaDTO>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/page")
    Page<BdcDyaDTO> listBdcDyaByPage(Pageable pageable,
                                     @RequestParam(name = "bdcDyaQOJson", required = false) String bdcDyaQOJson);

    /**
     * @param dyaTjQO 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计月报信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcDya/dyaTjMonth")
    List<BdcDyaTjVO> getDyaTjMonth(@RequestBody DyaTjQO dyaTjQO);

    /**
     * @param dyaTjQO 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计日报信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcDya/dyaTjDay")
    List<BdcDyaTjVO> getDyaTjDay(@RequestBody DyaTjQO dyaTjQO);

    /**
     * @param redisKey 保存redis的key值
     * @param dylx     抵押统计的打印类型
     * @return 打印xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计的xml信息
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/print/dyaTj/{dylx}/xml")
    String getDyaTjXml(@RequestParam(value = "redisKey") String redisKey, @PathVariable(value = "dylx") String dylx);

    /**
     * @param dylx         打印类型
     * @param listDyatjStr 打印统计信息
     * @return 保存到redis的key值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存抵押提交信息到redis
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/dyaTjxx/redis/{dylx}")
    String saveDyatjXxToRedis(@PathVariable(value = "dylx") String dylx, @RequestBody String listDyatjStr);

    /**
     * 抵押分页查询-标准版：查询抵押权表
     *
     * @param bdcDyaQOJson 抵押查询QO
     * @param pageable     pageable
     * @return 抵押查询结果
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/listStandardDyaByPage")
    Page<BdcDyaDTO> listStandardDyaByPage(Pageable pageable,
                                          @RequestParam(name = "bdcDyaQOJson", required = false) String bdcDyaQOJson);

    /**
     * 标准版: 抵押分页查询：查询预告预抵押信息
     * @param bdcDyaQOJson 抵押查询QO
     * @param pageable     pageable
     * @return 抵押查询结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/listStandardYgDyaByPage")
    Page<BdcDyaDTO> listStandardYgDyaByPage(Pageable pageable,
                                            @RequestParam(name = "bdcDyaQOJson", required = false) String bdcDyaQOJson);

    /**
     * 抵押分页查询-常州版：查询抵押权表
     *
     * @param bdcDyaQOJson 抵押查询QO
     * @param pageable     pageable
     * @return 抵押查询结果
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/listChangzhouDyaByPage")
    Page<BdcDyaDTO> listChangzhouDyaByPage(Pageable pageable,
                                          @RequestParam(name = "bdcDyaQOJson", required = false) String bdcDyaQOJson);

    /**
     * 抵押分页查询-标准版：查询抵押权表
     *
     * @param  bdcDyaQO 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/listStandardDya")
    List listStandardDya(@RequestBody BdcDyaQo bdcDyaQO);

    /**
     * 标准版: 抵押查询：查询预告预抵押信息
     *
     * @param  bdcDyaQO 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/listStandardYgDya")
    List listStandardYgDya(@RequestBody BdcDyaQo bdcDyaQO);

    /**
     * 抵押分页查询-标准版：查询抵押权表
     *
     * @param  bdcDyaQO 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/listChangzhouDya")
    List listChangzhouDya(@RequestBody BdcDyaQo bdcDyaQO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询抵押
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/search/{xmid}")
    List listDyaByXmid(@PathVariable(value = "xmid") String xmid);


    /**
     * 抵押分页查询-蚌埠版：查询抵押权表
     *
     * @param bdcDyaQOJson 抵押查询QO
     * @param pageable     pageable
     * @return 抵押查询结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/listBengbuDyaByPage")
    Page<BdcDyaDTO> listBengbuDyaByPage(Pageable pageable,
                                          @RequestParam(name = "bdcDyaQOJson", required = false) String bdcDyaQOJson);

    /**
     * 抵押分页查询-标准版：查询抵押权表
     *
     * @param bdcDyaQO 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/listBengbuDya")
    List listBengbuDya(@RequestBody BdcDyaQo bdcDyaQO);

    /**
     * 查询抵押全表 qszt！=2
     *
     * @param bdcdyh
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/dyaByBdcdy")
    List listDyaByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh);

    /**
     * 查询抵押全表
     *
     * @param bdcdyh
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/dyaByBdcdyAndqszt")
    List listDyaByBdcdyhAndqszt(@RequestParam(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qszt") Integer qszt);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcDyaq/bdcdyhList/search")
    List<BdcDyaqDO> listBdcDyaqByBdcdyhs(@RequestBody List<String> bdcdyhList);

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @version 2022/05/20
     * @description 获取产权的不动产权证号
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdya/cqbdcqzh")
    String getCqBdcqzhByXmxx(@RequestBody BdcDyaDTO bdcDyaDTO);

    /**
     * 根据地籍号查询抵押权表现势的在建工程抵押
     *
     * @param djh 地籍号
     * @param qszt 权属状态
     * @return 抵押查询结果
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/dyaByDjhAndQszt")
    List<BdcDyaqDO> listDyaByDjhAndQszt(@RequestParam(name = "djh") String djh, @RequestParam(name = "qszt") Integer qszt, @RequestParam(name = "dybdclx") Integer dybdclx);

}
