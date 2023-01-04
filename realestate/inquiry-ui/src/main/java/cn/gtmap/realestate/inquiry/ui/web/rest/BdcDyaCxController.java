package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDyaDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.qo.inquiry.count.DyaTjQO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcYdslPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDyafeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcDyaTjVO;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.ui.util.IpUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/9/3 13:51
 * @description
 */
@RestController
@RequestMapping(value = "/rest/v1.0")
public class BdcDyaCxController extends BaseController {

    @Autowired
    BdcDyafeignService dyafeignService;
    @Autowired
    BdcYdslPzFeignService bdcYdslPzFeignService;
    @Autowired
    private BdcZdController bdcZdController;
    @Autowired
    UserManagerUtils userManagerUtils;

    @Value("#{'${dyatj.except.gzldyid:}'.split(',')}")
    private List<String> gzldyidList;

    /**
     * @description Excel全部导出的导出条数,默认为1000
     */
    @Value("${excel.qxdcts:1000}")
    private Integer dcts;

    @GetMapping("/dya/listDyaByPage")
    public Object listBdcDyaByPage(@LayuiPageable Pageable pageable, BdcDyaQo bdcDyaQo, HttpServletRequest request) {
        String ip = IpUtils.getIpFromRequest(request);
        bdcDyaQo.setClientIp(ip);
        Page<BdcDyaDTO> bdcDyaDTOPage = dyafeignService.listBdcDyaByPage(pageable, JSON.toJSONString(bdcDyaQo));
        return super.addLayUiCode(bdcDyaDTOPage);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前登录的操作信息
     */
    @GetMapping("/dya/dyaTjTbxx")
    public Object getDyaTjTbxx() {
        UserDto userDto = userManagerUtils.getCurrentUser();
        // 填报人
        String tbr = "";
        // 单位名称
        String dwmc = "";
        if (null != userDto) {
            tbr = userDto.getAlias();
            if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                dwmc = userDto.getOrgRecordList().get(0).getName();
            }
        }
        String tbrq = DateUtils.formateYmdZw(new Date());
        Map map = new HashMap();
        map.put("tbr", tbr);
        map.put("dwmc", dwmc);
        map.put("tbrq", tbrq);
        return map;
    }

    /**
     * @param dyaTjQOStr 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计月报信息
     */
    @PostMapping("/dya/dyaTjMonth")
    public List<BdcDyaTjVO> getDyaTjMonth(@RequestBody String dyaTjQOStr) {
        DyaTjQO dyaTjQO = JSON.parseObject(dyaTjQOStr, DyaTjQO.class);
        dyaTjQO.setGzldyidList(gzldyidList);
        return dyafeignService.getDyaTjMonth(dyaTjQO);
    }

    /**
     * @param dyaTjQOStr 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计日报信息
     */
    @PostMapping("/dya/dyaTjDay")
    public List<BdcDyaTjVO> getDyaTjDay(@RequestBody String dyaTjQOStr) {
        DyaTjQO dyaTjQO = JSON.parseObject(dyaTjQOStr, DyaTjQO.class);
        dyaTjQO.setGzldyidList(gzldyidList);
        return dyafeignService.getDyaTjDay(dyaTjQO);
    }

    @PostMapping("/dya/tjxx/redis/{dylx}")
    public String saveDyatjXxToRedis(@PathVariable(value = "dylx") String dylx, @RequestBody String listDyatjStr) {
        if(StringUtils.isNotBlank(listDyatjStr)){
            LOGGER.info("抵押日报保存数据到redis,数据长度为：{}",listDyatjStr.length());
        }
        return dyafeignService.saveDyatjXxToRedis(dylx, listDyatjStr);
    }

    /**
     * @param redisKey 保存redis的key值
     * @param dylx     抵押统计的打印类型
     * @return 打印xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计的xml信息
     */
    @GetMapping(value = "/print/dyaTj/{dylx}/xml")
    String getDyaTjXml(@RequestParam(value = "redisKey") String redisKey, @PathVariable(value = "dylx") String dylx) {
        return dyafeignService.getDyaTjXml(redisKey, dylx);
    }

    /**
     * 抵押分页查询-标准版：查询抵押权表
     *
     * @param pageable pageable
     * @param bdcDyaQO 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/dya/standard/listDyaByPage")
    public Object listStandardDyaByPage(@LayuiPageable Pageable pageable, BdcDyaQo bdcDyaQO) {
        if (dcts > 0 && "exportAll".equals(bdcDyaQO.getType())) {
            pageable = new PageRequest(0,dcts);
        }
        bdcDyaQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("dyacx","",bdcDyaQO.getModuleCode()));
        // 导出全部
        if(StringUtils.isNotBlank(bdcDyaQO.getType()) && bdcDyaQO.getType().equals("export")) {
            // 查询条件中，存在 预告证明号时，查询预告预抵押信息
            if(StringUtils.isNotBlank(bdcDyaQO.getYgzmh())){
                return dyafeignService.listStandardYgDya(bdcDyaQO);
            }else{
                return dyafeignService.listStandardDya(bdcDyaQO);
            }
        }
        // 分页查询
        Page<BdcDyaDTO> bdcDyaDTOPage;
        if(StringUtils.isNotBlank(bdcDyaQO.getYgzmh())){
            bdcDyaDTOPage = dyafeignService.listStandardYgDyaByPage(pageable, JSON.toJSONString(bdcDyaQO));
        }else{
            bdcDyaDTOPage = dyafeignService.listStandardDyaByPage(pageable, JSON.toJSONString(bdcDyaQO));
        }
        return super.addLayUiCode(bdcDyaDTOPage);
    }

    /**
     * 抵押分页查询-常州版：查询抵押权表
     *
     * @param pageable pageable
     * @param bdcDyaQO 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @GetMapping("/dya/changzhou/listDyaByPage")
    public Object listChangzhouDyaByPage(@LayuiPageable Pageable pageable, BdcDyaQo bdcDyaQO) {
        bdcDyaQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("dyacx","",bdcDyaQO.getModuleCode()));
        if(StringUtils.isNotBlank(bdcDyaQO.getType()) && bdcDyaQO.getType().equals("export")) {// 导出全部
            return dyafeignService.listChangzhouDya(bdcDyaQO);
        }
        Page<BdcDyaDTO> bdcDyaDTOPage = dyafeignService.listChangzhouDyaByPage(pageable, JSON.toJSONString(bdcDyaQO));
        return super.addLayUiCode(bdcDyaDTOPage);
    }

    /**
     * 抵押分页查询-蚌埠版（以权利为准）：查询抵押权表
     *
     * @param pageable pageable
     * @param bdcDyaQO 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("/dya/bengbu/listDyaByPage")
    public Object listBengbuDyaByPage(@LayuiPageable Pageable pageable, BdcDyaQo bdcDyaQO) {
        bdcDyaQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("dyacx","",bdcDyaQO.getModuleCode()));
        if(StringUtils.isNotBlank(bdcDyaQO.getType()) && bdcDyaQO.getType().equals("export")) {// 导出全部
            return dyafeignService.listBengbuDya(bdcDyaQO);
        }
        Page<BdcDyaDTO> bdcDyaDTOPage = dyafeignService.listBengbuDyaByPage(pageable, JSON.toJSONString(bdcDyaQO));
        return super.addLayUiCode(bdcDyaDTOPage);
    }

    /**
     * 根据不动产单元号获取产权的不动产权证号内容
     * @param bdcDyaDTO 不动产抵押DTO
     * @return 不动产权证号
     */
    @GetMapping(value = "/dya/cqbdcqzh")
    public String getCqBdcqzhByBdcdyh(BdcDyaDTO bdcDyaDTO){
        String bdcqzh = "";
        if(StringUtils.isNotBlank(bdcDyaDTO.getBdcdyh()) && Objects.nonNull(bdcDyaDTO.getQllx())){
            bdcqzh = this.dyafeignService.getCqBdcqzhByXmxx(bdcDyaDTO);
        }
        return bdcqzh;
    }

    /**
     * 查询是否是异地受理角色需要展示部分查询条件
     * @param
     * @return 查询条件的页面id
     */
    @GetMapping(value = "/dya/ydslcxtj")
    public String getYdslcxtj(String cxym){
        if(StringUtils.isBlank(cxym)){
            throw new MissingArgumentException("查询是否是异地受理角色需要展示部分查询条件,缺失请求参数cxym");
        }
        return bdcYdslPzFeignService.listcxtj(cxym);
    }

}
