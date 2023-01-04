package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcJjdFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 不动产信息交接单服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/8/27 18:16
 */
@RestController
@RequestMapping("/rest/v1.0/jjd")
public class BdcJjdController extends BaseController {

    @Autowired
    private RoleManagerClient roleManagerClient;

    @Autowired
    private UserManagerClient userManagerClient;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcJjdFeignService bdcJjdFeignService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 生成并保存移交单信息
     *
     * @param bdcJjdQO 移交单查询QO
     * @return BdcJjdQO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/jjdxx")
    public BdcJjdDO generateAndSaveYjdxx(@RequestBody BdcJjdQO bdcJjdQO) {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        bdcJjdQO.setZfrid(currentUser.getId());
        bdcJjdQO.setZfr(currentUser.getAlias());
        return bdcJjdFeignService.generateAndSaveJjdxx(bdcJjdQO);
    }

    /**
     * 获取项目信息
     *
     * @param pageable 分页对象
     * @param jjdQO    查询对象
     * @param smqsr    扫描枪输入
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/ajcq")
    public Object listBdcAjxx(@LayuiPageable Pageable pageable, BdcJjdQO jjdQO, String smqsr) {
        String bdcJjdQO = JSON.toJSONString(jjdQO);
        JSONObject jsonObject = JSONObject.parseObject(bdcJjdQO);
        jsonObject.put("smqsr", smqsr);
        bdcJjdQO = JSON.toJSONString(jsonObject);
        return super.addLayUiCode(bdcJjdFeignService.listBdcAjxxByPage(pageable, bdcJjdQO));
    }

    /**
     * 获取项目信息
     *
     * @param jjdQO    查询对象
     * @param smqsr    扫描枪输入
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/ajcq/all")
    public Object listBdcAjxxAll(BdcJjdQO jjdQO, String smqsr) {
        String bdcJjdQO = JSON.toJSONString(jjdQO);
        JSONObject jsonObject = JSONObject.parseObject(bdcJjdQO);
        jsonObject.put("smqsr", smqsr);
        bdcJjdQO = JSON.toJSONString(jsonObject);
        return bdcJjdFeignService.listBdcAjxx(bdcJjdQO);
    }

    /**
     * 分页查询交接单内容
     *
     * @param pageable 分页对象
     * @param bdcJjdQO 移交单查询对象字符串
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/page")
    public Object listBdcJjdxx(@LayuiPageable Pageable pageable, BdcJjdQO bdcJjdQO) {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        List<RoleDto> roleRecordList = currentUser.getRoleRecordList();
        if (CollectionUtils.isNotEmpty(roleRecordList)) {
            String jsks = roleRecordList.stream().filter(roleDto -> !roleDto.getAlias().isEmpty())
                    .map(RoleDto::getAlias).collect(Collectors.joining(","));
            bdcJjdQO.setJsks(jsks);
        }
        bdcJjdQO.setZfrid(currentUser.getId());
        bdcJjdQO.setJsrid(currentUser.getId());
        String jjdQO = JSON.toJSONString(bdcJjdQO);
        return super.addLayUiCode(bdcJjdFeignService.listBdcJjdByPage(pageable, jjdQO));
    }

    @GetMapping("/page/all")
    public Object listBdcJjdxxAll(@LayuiPageable Pageable pageable, BdcJjdQO bdcJjdQO) {
        String jjdQO = JSON.toJSONString(bdcJjdQO);
        return super.addLayUiCode(bdcJjdFeignService.listAllBdcJjdByPage(pageable, jjdQO));
    }

    /**
     * 分页查询交接单内容
     *
     * @param pageable 分页对象
     * @param jjdid    交接单号
     * @return {Page} 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/xm")
    public Object listBdcJjdxm(@LayuiPageable Pageable pageable, @RequestParam("jjdid") String jjdid) {
        return super.addLayUiCode(bdcJjdFeignService.listBdcJjdxmByPage(pageable, jjdid));
    }


    /**
     * 删除交接单
     *
     * @param jjdid 交接单
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @DeleteMapping
    public void delJjd(@RequestParam("jjdid") String jjdid) {
        if (StringUtils.isBlank(jjdid)) {
            throw new MissingArgumentException("删除交接单信息缺少交接单主键！");
        }
        bdcJjdFeignService.delJjd(jjdid);
    }

    /**
     * 判断交接单能否删除（针对于退回的交接单进行判断）
     *
     * @param jjdid 交接单
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/check/deljjd")
    public String checkJjd(@RequestParam("jjdid") String jjdid) {
        if (StringUtils.isBlank(jjdid)) {
            throw new MissingArgumentException("删除交接单信息缺少交接单主键！");
        }
        BdcJjdQO bdcJjdQO = new BdcJjdQO();
        bdcJjdQO.setJjdid(jjdid);
        List<BdcJjdDO> bdcJjdDOS = bdcJjdFeignService.listBdcJjd(bdcJjdQO);
        BdcJjdDO currentJjd = bdcJjdDOS.get(0);
        // 转发时间为 null 说明节点未转发，非首个节点
        if (CollectionUtils.isNotEmpty(bdcJjdDOS) && null != currentJjd && 3 == currentJjd.getJjdzt()
                && currentJjd.getZfsj() != null) {
            bdcJjdQO = new BdcJjdQO();
            bdcJjdQO.setJjdh(currentJjd.getJjdh());
            List<BdcJjdDO> bdcJjdDOList = bdcJjdFeignService.listBdcJjd(bdcJjdQO);
            if (CollectionUtils.isNotEmpty(bdcJjdDOList) && bdcJjdDOList.size() > 1) {
                // 仅在所有节点都为退回时，创建人才可删除交接单
                for (BdcJjdDO bdcJjdDO : bdcJjdDOList) {
                    if (bdcJjdDO.getJjdzt() != 3) {
                        return "false";
                    }
                    if (bdcJjdDO.getZfsj() != null) {
                        // 根据转发时间可判断是否是第一个节点
                        if (bdcJjdDO.getZfsj().compareTo(currentJjd.getZfsj()) < 0) {
                            return "false";
                        }
                    }
                }
                // 判断是否是当前用户
                if (currentJjd.getZfrid().equals(userManagerUtils.getCurrentUser().getId())){
                    return "true";
                }
            } else {
                return "true";
            }
        }
        return "false";
    }

    /**
     * 删除交接单项目关系
     *
     * @param gzlslids 工作流实例id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @DeleteMapping("/xm")
    public void delJjdXm(@RequestParam("gzlslids") String gzlslids, @RequestParam("jjdid") String jjdid) {
        if (StringUtils.isAnyBlank(gzlslids, jjdid)) {
            throw new MissingArgumentException("删除交接单项目关系缺少必要条件！");
        }

        String[] split = gzlslids.split(",");
        for (String gzlslid : split) {
            if (StringUtils.isNotBlank(gzlslid)){
                bdcJjdFeignService.delJjdXmGx(gzlslid);
            }
        }
        // 如果所有项目均删除，则直接删除交接单
        List<BdcXmYjdGxDO> bdcXmYjdGxDOS = bdcJjdFeignService.queryJjdGx(jjdid);
        if (CollectionUtils.isEmpty(bdcXmYjdGxDOS)) {
            bdcJjdFeignService.delJjd(jjdid);
        }
    }

    /**
     * 查询转发角色
     * 获取全部启用角色
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/forward/roles")
    public List<RoleDto> queryForwardRoles() {
        return roleManagerClient.getEnabledRoleList();
    }

    /**
     * 查询转发用户
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/forward/users")
    public List<UserDto> queryForwardUsers(@RequestParam("roleid") String roleid) {
        if (StringUtils.equals(roleid, "allRoles")) {
            return userManagerClient.allUsers(null, null, 1);
        }
        return roleManagerClient.listEnableUsersByRoleId(roleid);
    }

    /**
     * 转发交接单
     *
     * @param bdcJjdDO 交接单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/forward")
    public void forwardJjd(@RequestBody BdcJjdDO bdcJjdDO) {
        if (StringUtils.isBlank(bdcJjdDO.getJjdid()) && bdcJjdDO.getJjdzt() != null &&
                (StringUtils.isBlank(bdcJjdDO.getJsrid()) || StringUtils.isBlank(bdcJjdDO.getJsks()) ) ) {
            throw new MissingArgumentException("交接单转发参数缺失！");
        }
        bdcJjdFeignService.forwardJjd(bdcJjdDO);
    }

    /**
     * 批量转发交接单
     * @param bdcJjdDOList 交接单对象集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/forward/all")
    public void forwardJjd(@RequestBody List<BdcJjdDO> bdcJjdDOList) {
        bdcJjdFeignService.forwardJjd(bdcJjdDOList);
    }


    /**
     * 确认接收交接单
     *
     * @param jjdid 交接单 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/accept")
    public Boolean acceptJjd(@RequestParam("jjdid") String jjdid) {
        if (StringUtils.isBlank(jjdid)) {
            throw new MissingArgumentException("确认接收参数缺失！");
        }
        return bdcJjdFeignService.acceptJjd(jjdid);
    }

    /**
     * 退回交接单
     *
     * @param jjdid 交接单 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/back")
    public Boolean backJjd(@RequestParam("jjdid") String jjdid) {
        if (StringUtils.isBlank(jjdid)) {
            throw new MissingArgumentException("拒绝接收参数缺失！");
        }
        return bdcJjdFeignService.backJjd(jjdid);
    }

    /**
     * 生成当天当前用户的注销交接单信息
     * （已办结并且未移交）
     *
     * @return BdcJjdQO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/zxjjd")
    public BdcJjdDO generateZxJjd(@RequestBody BdcJjdXmQO bdcJjdXmQO) {
        if (StringUtils.isAnyBlank(bdcJjdXmQO.getBegin(), bdcJjdXmQO.getEnd())) {
            throw new MissingArgumentException("缺少开始时间和结束时间！");
        }
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if (null == currentUser){
            throw new MissingArgumentException("未获取到当前用户信息");
        }
        bdcJjdXmQO.setAjzt(CommonConstantUtils.AJZT_YB_DM);
        bdcJjdXmQO.setSlrid(currentUser.getId());
        bdcJjdXmQO.setYj(false);
        bdcJjdXmQO.setDjlx(400);
        // 设置起始和结束时间
//        Date current = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bdcJjdXmQO.setBegin(bdcJjdXmQO.getBegin());
        bdcJjdXmQO.setEnd(bdcJjdXmQO.getEnd());

        // 查询出所有项目
        List<BdcXmDO> bdcXmDOS = bdcJjdFeignService.queryJjdXm(bdcJjdXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)){
            throw new AppException("无注销交接单数据");
        }
        BdcJjdQO bdcJjdQO = new BdcJjdQO();
        bdcJjdQO.setZfrid(currentUser.getId());
        bdcJjdQO.setZfr(currentUser.getAlias());
        List<String> gzlslidList = Lists.newArrayListWithCapacity(bdcXmDOS.size());
        for (BdcXmDO bdcXmDO : bdcXmDOS) {
            gzlslidList.add(bdcXmDO.getGzlslid());
        }
        bdcJjdQO.setListGzlslid(gzlslidList);
        return bdcJjdFeignService.generateAndSaveJjdxx(bdcJjdQO);
    }

    /**
     * 查询组合流程带抵押流程中产权的权利人与义务人
     *
     * @param gzlslid    gzlslid
     * @return {BdcXmDO} 权利人与义务人
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/ddyqlr")
    public Object queryDdyQlr(@RequestParam("gzlslid") String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOS) && bdcXmDOS.size() >1) {
            Set<String> qlrSet = new HashSet<>();
            Set<String> ywrSet = new HashSet<>();
            BdcXmDO resultXm = null;
            for (BdcXmDO bdcXmDO : bdcXmDOS) {
                if (!CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                    qlrSet.add(bdcXmDO.getQlr());
                    ywrSet.add(bdcXmDO.getYwr());
                    resultXm = bdcXmDO;
                }
            }
            if (null != resultXm) {
                resultXm.setQlr(dealWithQlr(qlrSet, resultXm.getQlr()));
                resultXm.setYwr(dealWithQlr(ywrSet, resultXm.getYwr()));
                return resultXm;
            }
        }
        return null;
    }

    private String dealWithQlr(Set<String> qlrSet, String qlr){
        if (StringUtils.isBlank(qlr)) {
            // 权利人姓名为空做处理，否则前端页面显示 null
            return StringUtils.EMPTY;
        } else {
            if (qlr.contains(",")) {
                String[] qlrs = qlr.split(",");
                return qlrs[0] + "等";
            } else if (qlrSet.size() > 1) {
                return qlr + "等";
            } else {
                return qlr;
            }
        }
    }

    @GetMapping("/iscreate")
    public Integer checkIsCreat(@RequestParam("gzlslid") String gzlslid) {
        return bdcJjdFeignService.checkIsCreat(gzlslid);
    }


    @GetMapping("/add")
    public Integer addSlbhCheck(@RequestParam("slbh") String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("未查询到对应项目信息");
        }
        return bdcJjdFeignService.checkIsCreat(bdcXmDOS.get(0).getGzlslid());
    }

    /**
     * 保存批量打印交接单参数信息
     * @param bdcJjdDOList 交接单集合
     * @return {String} Redis key
     */
    @PostMapping("/dyxx")
    public String savePrintParams(@RequestBody List<BdcJjdDO> bdcJjdDOList) {
        if(CollectionUtils.isEmpty(bdcJjdDOList)) {
            throw new MissingArgumentException("未定义交接单打印数据");
        }

        return redisUtils.addStringValue(UUIDGenerator.generate16(), JSON.toJSONString(bdcJjdDOList), 120);
    }
}
