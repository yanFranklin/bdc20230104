package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlCsjpzService;
import cn.gtmap.realestate.accept.core.service.impl.BdcSfmServiceImpl;
import cn.gtmap.realestate.accept.service.BdcSjclService;
import cn.gtmap.realestate.accept.service.BdcSlQlrService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCsjpzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQlrGroupDTO;
import cn.gtmap.realestate.common.core.dto.accept.SfmLiscenseFjDTO;
import cn.gtmap.realestate.common.core.dto.accept.SfmLiscenseInfoDTO;
import cn.gtmap.realestate.common.core.dto.accept.SjclUploadDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.csjzzxx.CsjZzxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.csjzzxx.JiangsuZzxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.csjzzxx.sfm.SfmResponse;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrIdsDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlrXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.CsjZzxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.hutool.json.JSONTokener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/13.
 * @description 不动产权利人对外接口服务实现类
 * 提取原有<code>SlymQlrController<code/>中的方法，提供对外操作权利人信息的通用接口服务
 */
@Service
public class BdcSlQlrServiceImpl implements BdcSlQlrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlQlrServiceImpl.class);

    private static final String MSG_HXFAIL = "回写信息失败";

    /**
     * 权利人类型集合： 企业、事业单位、国家
     */
    private final static int[] QLRLX_QYJGGJ = new int[]{CommonConstantUtils.QLRLX_QY, CommonConstantUtils.QLRLX_SYDW, CommonConstantUtils.QLRLX_GJJG};

    /**
     * 单个权利人时需要默认为共同共有的 登记小类
     */
    @Value("${default.gtgyDjxl:}")
    private String gtgyDjxl;

    /**
     * 验证单个权利人默认为单个共有。 配置为false时，允许单个权利人存在共同共有的情况
     */
    @Value("${default.gyfsdgyz:true}")
    private boolean gyfsdgyz;

    //长三角证照信息 sign 标签
    @Value("${csjzzxx.sign:}")
    private String sign;

    //长三角证照信息保存的文件夹名称
    @Value("${csjzzxx.wjjmc:长三角证件照}")
    private String wjjmc;

    //长三角证照上传省内证照的时候时候是否过滤，默认过滤
    @Value("${csjzzxx.glsnfj:true}")
    private boolean glsnfj;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcRyzdFeignService bdcRyzdFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSjclService bdcSjclService;
    @Autowired
    BdcSlCsjpzService bdcSlCsjpzService;
    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcSfmServiceImpl bdcSfmService;

    /**
     * 常用属性名
     */
    private static final String QLRLB = "qlrlb";


    /**
     * 修改权利人共有方式接口
     *
     * @param bdcQlrList 不动产权利人集合
     * @param gzlslid    工作流实例ID
     * @param xmid       项目ID
     * @param lclx       流程类型
     * @return List<BdcQlrDO> 不动产权利人DO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public List<BdcQlrDO> modifyBdcQlrGyfs(List<BdcQlrDO> bdcQlrList, String gzlslid, String xmid, String lclx) throws Exception {
        //判断程序是否修改共有方式，确认修改才进行更新
        Boolean isChange = false;
        //组合流程不能用批量更新的方法，分开更新
        if (CollectionUtils.isNotEmpty(bdcQlrList)) {
            //2 一个权利人时
            if (bdcQlrList.size() == 1) {
                // 2019-11-02 lyq 24806 如果只有一个权利人，并且为默认共有方式为共同共有的登记小类
                if (checkGyfsDefaultGtgy(xmid)) {
                    //配置默认共同共有,如果共有方式为空,默认共同共有（允许为其他共有方式）
                    if (bdcQlrList.get(0).getGyfs() == null) {
                        bdcQlrList.get(0).setGyfs(CommonConstantUtils.GYFS_GTGY);
                        bdcQlrList = updateBdcQlrGyfs(bdcQlrList, gzlslid, xmid, lclx);
                    }
                } else if (!CommonConstantUtils.GYFS_DDSY.equals(bdcQlrList.get(0).getGyfs()) && gyfsdgyz) {
                    // 单个权利人默认为单个共有验证配置，true开启验证，验证单个权利人必须为单个共有
                    //判断原有共有方式是否正确，正确则不更新
                    bdcQlrList.get(0).setGyfs(CommonConstantUtils.GYFS_DDSY);
                    bdcQlrList = updateBdcQlrGyfs(bdcQlrList, gzlslid, xmid, lclx);
                }
            } else if (bdcQlrList.size() == 2) {
                //两个权利人时共有方式不能存在单独所有，且两个共有方式保持一致
                if (bdcQlrList.get(0).getGyfs() != null && bdcQlrList.get(1).getGyfs() != null) {
                    if (CommonConstantUtils.GYFS_DDSY.equals(bdcQlrList.get(0).getGyfs()) && !CommonConstantUtils.GYFS_DDSY.equals(bdcQlrList.get(1).getGyfs())) {
                        bdcQlrList.get(0).setGyfs(bdcQlrList.get(1).getGyfs());
                        isChange = true;
                    } else if (!CommonConstantUtils.GYFS_DDSY.equals(bdcQlrList.get(0).getGyfs()) && CommonConstantUtils.GYFS_DDSY.equals(bdcQlrList.get(1).getGyfs())) {
                        bdcQlrList.get(1).setGyfs(bdcQlrList.get(0).getGyfs());
                        isChange = true;
                    }
                } else {
                    //共有方式为空或者为单独所有
                    if (bdcQlrList.get(0).getGyfs() == null || CommonConstantUtils.GYFS_DDSY.equals(bdcQlrList.get(0).getGyfs())) {
                        bdcQlrList.get(0).setGyfs(CommonConstantUtils.GYFS_GTGY);
                        isChange = true;
                    }
                    if (bdcQlrList.get(1).getGyfs() == null || CommonConstantUtils.GYFS_DDSY.equals(bdcQlrList.get(1).getGyfs())) {
                        bdcQlrList.get(1).setGyfs(CommonConstantUtils.GYFS_GTGY);
                        isChange = true;
                    }
                }
                if (bdcQlrList.get(0).getQlbl() != null || bdcQlrList.get(1).getQlbl() != null && (!CommonConstantUtils.GYFS_AFGY.equals(bdcQlrList.get(0).getGyfs()) || !CommonConstantUtils.GYFS_AFGY.equals(bdcQlrList.get(1).getGyfs()))) {
                    bdcQlrList.get(0).setGyfs(CommonConstantUtils.GYFS_AFGY);
                    bdcQlrList.get(1).setGyfs(CommonConstantUtils.GYFS_AFGY);
                    isChange = true;
                }
                if (isChange) {
                    bdcQlrList = updateBdcQlrGyfs(bdcQlrList, gzlslid, xmid, lclx);
                }
            } else if (bdcQlrList.size() > 2) {
                //多个权利人时共有方式不能存在单独所有,若存在默认赋值为共同共有
                List<BdcQlrDO> newQlrList = new ArrayList<>();
                for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                    if (bdcQlrDO.getGyfs() != null) {
                        if (CommonConstantUtils.GYFS_DDSY.equals(bdcQlrDO.getGyfs())) {
                            bdcQlrDO.setGyfs(CommonConstantUtils.GYFS_GTGY);
                            isChange = true;
                        }
                    } else {
                        bdcQlrDO.setGyfs(CommonConstantUtils.GYFS_GTGY);
                        isChange = true;

                    }
                    //共有比例不为空，默认按份共有
                    if (bdcQlrDO.getQlbl() != null && !CommonConstantUtils.GYFS_AFGY.equals(bdcQlrDO.getGyfs())) {
                        bdcQlrDO.setGyfs(CommonConstantUtils.GYFS_AFGY);
                        isChange = true;
                    }
                    newQlrList.add(bdcQlrDO);
                }
                if (isChange) {
                    bdcQlrList = updateBdcQlrGyfs(newQlrList, gzlslid, xmid, lclx);
                }
            }
        }
        return bdcQlrList;
    }




    /**
     * 批量修改权利人（用于批量页面）
     *
     * @param json         权利人集合json
     * @param processInsId 流程实例ID
     * @param xmid         项目ID
     * @return Integer 修改数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public Integer updatePlBdcQlr(String json, String processInsId, String xmid) throws Exception {

        Integer count = 0;
        JSONArray jsonArray = new JSONArray();
        Object jsonObject = new JSONTokener(json).nextValue();
        if (jsonObject instanceof cn.hutool.json.JSONObject) {
            JSONObject obj = JSONObject.parseObject(json);
            jsonArray.add(obj);
        } else if (jsonObject instanceof cn.hutool.json.JSONArray) {
            jsonArray = JSONObject.parseArray(json);
        }
        //权利人类别改变
        Boolean qlrlbChange = false;
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                //获取权利人修改前的数据,用于批量更新的where条件
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setQlrid(obj.getString("qlrid"));
                List<BdcQlrDO> listyBdcQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(listyBdcQlr)) {
                    BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                    //权利人批量更新移除xmid
                    obj.remove("xmid");
                    //处理权利人来源
                    dealQlrly(obj, listyBdcQlr.get(0));
                    bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
                    Map<String, Object> map = new HashMap<>();
                    map.put("gzlslid", processInsId);
                    map.put("qlrmc", listyBdcQlr.get(0).getQlrmc());
                    /**
                     *  权利人类型不是企业的时候，则用zjh作为更新条件,
                     *  当权利人类型为企业时，则不用zjh作为更新条件
                     *  原因：企业会用不同的证件种类去做多次单个抵押流程后，再做批量抵押注销
                     *  这时，页面只会带任意一条数据（其中一个证件种类）。
                     *  保存权利人如果根据zjh去更新的话，会漏更新数据，必填验证不通过
                     *
                     *  31696：当qlrlx是2、3、4时(企业、事业单位、国家机关)，不使用zjh作为更新条件
                     */
                    String qlrlx = obj.getString("qlrlx");
                    if (StringUtils.isNotBlank(qlrlx) && !ArrayUtils.contains(QLRLX_QYJGGJ, Integer.parseInt(qlrlx))) {
                        map.put("zjh", listyBdcQlr.get(0).getZjh());
                    }
                    map.put(QLRLB, listyBdcQlr.get(0).getQlrlb());

                    bdcDjxxUpdateQO.setWhereMap(map);
                    count += bdcQlrFeignService.updateBatchBdcQlr(bdcDjxxUpdateQO);
                    if (CommonConstantUtils.QLRLB_QLR.equals(listyBdcQlr.get(0).getQlrlb()) && CommonConstantUtils.QLRLB_YWR.equals(obj.getString(QLRLB))) {
                        qlrlbChange = true;

                    }
                }
            }
        }

        if (StringUtils.isNotBlank(processInsId) && StringUtils.isNotBlank(xmid)) {
            //权利人保存后更新权利人相关信息
            updateQlrxxPl(processInsId, "", "", null);
            if (qlrlbChange) {
                //如果发生权利人类型改变，需要更新义务人冗余字段和共有情况
                updateAllYwrRyzd(processInsId, "");
            }


        }

        return count;
    }

    /**
     * @param json         权利人集合JSON
     * @param processInsId 流程实例ID
     * @param xmid         项目ID
     * @param djxl         登记小类
     * @return List<BdcQlrDO> 不动产权利人DO集合
     * @description 批量修改权利人(用于批量组合页面)
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public List<BdcQlrDO> updatePlzhBdcQlr(String json, String processInsId, String xmid, String djxl) throws Exception {

        //存储需要返回的当前流程完整的权利人对象
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        //存储需要更新的完整权利人对象
        List<BdcQlrDO> bdcQlrDOS = new ArrayList<>();
        Object jsonObject = new JSONTokener(json).nextValue();
        if (jsonObject instanceof cn.hutool.json.JSONObject) {
            //权利人详细页面
            JSONObject obj = JSONObject.parseObject(json);
            if (obj != null) {
                BdcQlrDO bdcQlrDO = JSONObject.parseObject(JSON.toJSONString(obj), BdcQlrDO.class);
                bdcQlrDOS.add(bdcQlrDO);
            }
        } else if (jsonObject instanceof cn.hutool.json.JSONArray) {
            //受理页面权利人模块
            JSONArray jsonArray = JSONObject.parseArray(json);
            if (CollectionUtils.isNotEmpty(jsonArray)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    BdcQlrDO bdcQlrDO = JSONObject.parseObject(JSON.toJSONString(obj), BdcQlrDO.class);
                    bdcQlrDOS.add(bdcQlrDO);
                }

            }
        }
        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
            //项目ID集合
            List<String> xmidList = new ArrayList<>();
            if (StringUtils.isNotBlank(xmid)) {
                //如果证书序号不为空,获取相同证号序号的所有项目同步更新
                xmidList = getGroupXmidList(xmid);
            }
            for (BdcQlrDO bdcUpateQlrDO : bdcQlrDOS) {
                //批量组合需要同步更新其他流程对应的权利人或义务人
                JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(bdcUpateQlrDO));
                List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_UPDATE);
                for (JSONObject object : jsonObjectList) {
                    BdcQlrDO bdcQlrDO = JSONObject.toJavaObject(object, BdcQlrDO.class);
                    djxl = getDjxlByXmid(bdcQlrDO.getXmid());
                    if (StringUtils.isNotBlank(djxl)) {

                        //获取权利人修改前的数据,用于批量更新的where条件
                        BdcQlrQO bdcQlrQO = new BdcQlrQO();
                        bdcQlrQO.setQlrid(bdcQlrDO.getQlrid());
                        List<BdcQlrDO> listyBdcQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        if (CollectionUtils.isNotEmpty(listyBdcQlr)) {
                            dealQlrly(object, listyBdcQlr.get(0));
                            BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                            //权利人批量更新移除xmid
                            object.remove("xmid");
                            object.remove("zsid");
                            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(object));
                            Map<String, Object> map = new HashMap<>();
                            map.put("gzlslid", processInsId);
                            map.put("qlrmc", listyBdcQlr.get(0).getQlrmc());
                            map.put("zjh", listyBdcQlr.get(0).getZjh());
                            map.put(QLRLB, listyBdcQlr.get(0).getQlrlb());
                            map.put("djxl", djxl);
                            if (CollectionUtils.isNotEmpty(xmidList)) {
                                List<List> lists = ListUtils.subList(xmidList, 1000);
                                for (List partList : lists) {
                                    map.put("xmidList", partList);
                                    bdcDjxxUpdateQO.setWhereMap(map);
                                    bdcQlrFeignService.updateBatchBdcQlr(bdcDjxxUpdateQO);
                                }
                            } else {
                                bdcDjxxUpdateQO.setWhereMap(map);
                                bdcQlrFeignService.updateBatchBdcQlr(bdcDjxxUpdateQO);
                            }
                            if (StringUtils.isNotBlank(processInsId) && StringUtils.isNotBlank(xmid)) {
                                //权利人保存后更新权利人相关信息
                                updateQlrxxPl(processInsId, djxl, listyBdcQlr.get(0).getQlrlb(), xmidList);
                            }
                        }
                    }
                    //返回当前流程对象的权利人
                    if (obj.get(QLRLB) != null && StringUtils.equals(obj.get(QLRLB).toString(), bdcQlrDO.getQlrlb())) {
                        bdcQlrDOList.add(bdcQlrDO);
                    }
                }
            }
        }
        return bdcQlrDOList;
    }

    /**
     * 处理权利人来源
     *
     * @param jsonObject
     */
    private void dealQlrly(JSONObject jsonObject, BdcQlrDO yqlr) {
        try {
            if (null != jsonObject && yqlr != null) {
                // 权利人名称或权利人证件号不相等时  则更新权利人来源 为手动
                if ((jsonObject.containsKey("qlrmc") && !StringUtils.equals(yqlr.getQlrmc(), jsonObject.getString("qlrmc")))
                        || (jsonObject.containsKey("zjh") && !StringUtils.equals(yqlr.getZjh(), jsonObject.getString("zjh")))) {
                    jsonObject.put("qlrly", CommonConstantUtils.QLRLY_SD);
                }
            }
        } catch (Exception e) {
            LOGGER.error("处理权利人来源异常", e);
        }
    }

    @Override
    public BdcQlrDO insertPlzhBdcQlr(String json, String processInsId, String djxl, boolean syncTdsyqr) {
        BdcQlrDO returnQlr = null;
        JSONObject obj = JSONObject.parseObject(json);
        //批量组合需要同步更新其他流程对应的权利人或义务人
        List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_INSERT);
        if (CollectionUtils.isNotEmpty(jsonObjectList)) {
            for (JSONObject object : jsonObjectList) {
                BdcQlrDO bdcQlrDO = JSONObject.toJavaObject(object, BdcQlrDO.class);
                //批量更新
                djxl = getDjxlByXmid(bdcQlrDO.getXmid());
                //如果证书序号不为空,获取相同证号序号的所有项目同步更新
                List<String> xmidList = getGroupXmidList(bdcQlrDO.getXmid());
                //新增前先删除相同的权利人,防止有重复权利人数据
                bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(), djxl, xmidList);
                if (bdcQlrDO.getQlrly() == null) {
                    bdcQlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
                }
                List<BdcQlrDO> bdcQlrDOList;
                if (CollectionUtils.isNotEmpty(xmidList)) {
                    BdcQlrIdsDTO bdcQlrIdsDTO = new BdcQlrIdsDTO();
                    bdcQlrIdsDTO.setXmidlist(xmidList);
                    bdcQlrIdsDTO.setBdcQlrDO(bdcQlrDO);
                    bdcQlrDOList = bdcQlrFeignService.insertBatchBdcQlrByXmids(bdcQlrIdsDTO);
                } else {
                    bdcQlrDOList = bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO, processInsId, djxl);
                }

                if (StringUtils.isNotBlank(processInsId)) {
                    //权利人保存后更新权利人相关信息
                    updateQlrxxPl(processInsId, djxl, bdcQlrDO.getQlrlb(), new ArrayList<>());
                    if (syncTdsyqr) {
                        // 同步更新土地使用权人
                        this.syncTdsyqr(JSON.toJSONString(bdcQlrDO), processInsId);
                    }
                }
                //返回当前流程对象的权利人
                if (obj.get(QLRLB) != null && StringUtils.equals(obj.get(QLRLB).toString(), bdcQlrDO.getQlrlb())) {
                    bdcQlrDOList.sort(Comparator.comparing(BdcQlrDO::getXmid));
                    returnQlr = bdcQlrDOList.get(0);
                }
            }

        }
        return returnQlr;

    }

    @Override
    public void deletePlzhBdcQlr(String qlrid, String processInsId) throws Exception {
        if (StringUtils.isNotBlank(qlrid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                BdcQlrDO bdcDeleteQlrDO = bdcQlrDOList.get(0);
                //批量组合需要同步删除其他流程对应的权利人或义务人
                JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(bdcDeleteQlrDO));
                List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_DELETE);
                for (JSONObject object : jsonObjectList) {
                    BdcQlrDO bdcQlrDO = JSONObject.toJavaObject(object, BdcQlrDO.class);
                    String djxl = getDjxlByXmid(bdcQlrDO.getXmid());
                    if (StringUtils.isNotBlank(djxl)) {
                        //如果人名、工作流实例id没值则抛异常
                        if (StringUtils.isBlank(bdcQlrDO.getQlrmc()) || StringUtils.isBlank(processInsId)) {
                            throw new MissingArgumentException("删除权利人时权利人名称或工作流实例ID为空");
                        }

                        //如果证书序号不为空,获取相同证号序号的所有项目同步更新
                        List<String> xmidList = getGroupXmidList(bdcQlrDO.getXmid());
                        //批量删除
                        bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(), djxl, xmidList);
                        if (StringUtils.isNotBlank(processInsId)) {
                            //权利人保存后更新权利人相关信息
                            updateQlrxxPl(processInsId, djxl, bdcQlrDO.getQlrlb(), null);
                        }
                    }
                }


            }
        }

    }

    /**
     * @param xmid
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证是否为共有方式默认为共同共有
     */
    private boolean checkGyfsDefaultGtgy(String xmid) {
        if (StringUtils.isNotBlank(gtgyDjxl) && StringUtils.isNotBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                BdcXmDO bdcXmDO = bdcXmList.get(0);
                if (StringUtils.contains(gtgyDjxl, bdcXmDO.getDjxl())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param bdcQlrDOList,gzlslid,xmid,lclx
     * @return bdcQlrDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新操作
     */
    private List<BdcQlrDO> updateBdcQlrGyfs(List<BdcQlrDO> bdcQlrDOList, String gzlslid, String xmid, String lclx) throws Exception {
        if (StringUtils.equals(lclx, Constants.LCLX_JDLC)) {
            bdcQlrDOList = bdcQlrFeignService.updateBatchBdcQlr(bdcQlrDOList, gzlslid, CommonConstantUtils.QLRLB_QLR);
            bdcRyzdFeignService.updateGyqkWithGzlslid(gzlslid);
        } else if (StringUtils.equals(lclx, Constants.LCLX_ZHLC)) {
            //直接更新单个的，然后查出来返回
            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                bdcQlrFeignService.updateBdcQlr(bdcQlrDO);
            }
            bdcRyzdFeignService.updateGyqkWithGzlslid(gzlslid);
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        } else if (StringUtils.equals(lclx, Constants.LCLX_PLLC)) {
            updatePlBdcQlr(JSONObject.toJSONString(bdcQlrDOList), gzlslid, bdcQlrDOList.get(0).getXmid());
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        } else if (StringUtils.equals(lclx, Constants.LCLX_PLZH)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                updatePlzhBdcQlr(JSONObject.toJSONString(bdcQlrDOList), gzlslid, bdcQlrDOList.get(0).getXmid(), bdcXmDOList.get(0).getDjxl());
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            }
        }
        return bdcQlrDOList;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量流程更新权利人相关信息（包括权利人冗余字段与共有情况，权利人回写）
     */
    private void updateQlrxxPl(String gzlslid, String djxl, String qlrlb, List<String> xmidList) {
        //组织数据批量更新冗余字段和共有情况
        List<BdcQlrXmDTO> bdcQlrXmDTOList = new ArrayList<>();
        BdcQlrXmDTO bdcQlrXmDTO = new BdcQlrXmDTO();
        if (StringUtils.isNotBlank(qlrlb)) {
            bdcQlrXmDTO.setQlrlb(Integer.parseInt(qlrlb));
        } else {
            bdcQlrXmDTO.setQlrlb(CommonConstantUtils.QLRLB_QLR_DM);
        }
        if (CollectionUtils.isEmpty(xmidList)) {
            xmidList = new ArrayList<>();
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    //登记小类为空或者两者一致
                    if (StringUtils.isBlank(djxl) || StringUtils.equals(bdcXmDTO.getDjxl(), djxl)) {
                        xmidList.add(bdcXmDTO.getXmid());
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(xmidList)) {
            bdcQlrXmDTO.setXmidList(xmidList);
            bdcQlrXmDTOList.add(bdcQlrXmDTO);
            bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);
        }
        //回写信息到平台
        try {
            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
        } catch (Exception e) {
            LOGGER.error(MSG_HXFAIL, e);
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取登记小类
     */
    private String getDjxlByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            //同步其他流程，需要获取对应流程的登记小类
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                return bdcXmDOList.get(0).getDjxl();
            }
        }
        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新义务人冗余字段和共有情况
     */
    private void updateAllYwrRyzd(String gzlslid, String djxl) {
        //查出根据证件号分组的义务人对象
        List<BdcQlrGroupDTO> bdcQlrGroupDTOList = bdcQlrFeignService.groupQlrYwrByZjh(gzlslid, CommonConstantUtils.QLRLB_YWR, djxl, "");
        if (CollectionUtils.isNotEmpty(bdcQlrGroupDTOList)) {
            //更新冗余字段和共有情况
            List<BdcQlrXmDTO> bdcQlrXmDTOList = new ArrayList<>();
            for (BdcQlrGroupDTO bdcQlrGroupDTO : bdcQlrGroupDTOList) {
                if (bdcQlrGroupDTO.getBdcQlrDO() != null) {
                    BdcQlrXmDTO bdcQlrXmDTO = new BdcQlrXmDTO();
                    bdcQlrXmDTO.setQlrlb(CommonConstantUtils.QLRLB_YWR_DM);
                    List<String> xmidList = new ArrayList<>();
                    xmidList.add(bdcQlrGroupDTO.getBdcQlrDO().getXmid());
                    if (CollectionUtils.isNotEmpty(bdcQlrGroupDTO.getOtherXmidList())) {
                        xmidList.addAll(bdcQlrGroupDTO.getOtherXmidList());
                    }
                    bdcQlrXmDTO.setXmidList(xmidList);
                    bdcQlrXmDTOList.add(bdcQlrXmDTO);
                }
            }
            bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);

        }
    }

    /**
     * 权利人新增、修改、删除时，同步更新土地使用权人信息
     */
    @Override
    public void syncTdsyqr(String json, String processInsId) {
        // 1、校验权利人信息是否为“权利人”。不为权利人时，不进行同步操作
        List<BdcQlrDO> bdcQlrDOList = this.handlerQlrJson(json);
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            return;
        }
        bdcQlrDOList = bdcQlrDOList.stream().filter(
                t -> CommonConstantUtils.QLRLB_QLR.equals(t.getQlrlb())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            return;
        }
        // 2、获取权利类型为：房地产权的项目并按登记小类进行分组
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        Map<String, BdcXmDTO> bdcXmDTOMap = bdcXmDTOList.stream()
                .filter(x -> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, x.getQllx()))
                .collect(Collectors.toMap(BdcXmDTO::getDjxl, v -> v, (v1, v2) -> v1));
        if (MapUtils.isNotEmpty(bdcXmDTOMap)) {
            for (Map.Entry<String, BdcXmDTO> entry : bdcXmDTOMap.entrySet()) {
                this.modifyFdcqTdsyqr(processInsId, entry.getValue().getXmid(), entry.getKey());
            }
        }
    }

    /**
     * @param yyzzm 营业执照码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通页面输入营业执照调用接口查询权利人信息和附件信息
     * @date : 2021/5/11 10:53
     */
    @Override
    public void importYyzz(String yyzzm, String gzlslid, String djxl) {
        //1.接口查询数据
        Map<String, String> paramMap = new HashMap<>(1);
        paramMap.put("qrid", yyzzm);
        Object response = exchangeInterfaceFeignService.requestInterface("yzyyzz", paramMap);
        if (Objects.nonNull(response)) {
            //2.处理权利人数据
            Map<String, Map<String, String>> repoMap = (Map<String, Map<String, String>>) response;
            Map<String, String> contentMap = repoMap.get("message_content");
            Map<String, String> headerMap = repoMap.get("message_header");
            String errorCode = MapUtils.getString(headerMap, "errorCode", StringUtils.EMPTY);
            if (!StringUtils.equals("0", errorCode)) {
                throw new AppException(MapUtils.getString(headerMap, "errorInfo", StringUtils.EMPTY));
            }
            String qlrmc = MapUtils.getString(contentMap, "entname", StringUtils.EMPTY);
            LOGGER.info("营业执照查询代码{},企业名称{},社会信用代码{}", yyzzm, qlrmc, MapUtils.getString(contentMap, "uniscid", StringUtils.EMPTY));
            if (MapUtils.isNotEmpty(contentMap) && StringUtils.isNotBlank(qlrmc)) {
                BdcQlrDO bdcQlrDO = new BdcQlrDO();
                bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                bdcQlrDO.setQlrlx(CommonConstantUtils.QLRLX_QY);
                bdcQlrDO.setQlrmc(qlrmc);
                bdcQlrDO.setSxh(1);
                bdcQlrDO.setZjh(MapUtils.getString(contentMap, "uniscid", StringUtils.EMPTY));
                bdcQlrDO.setZjzl(CommonConstantUtils.ZJZL_SHXYDM);
                bdcQlrDO.setFrmc(MapUtils.getString(contentMap, "name", StringUtils.EMPTY));
                bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO, gzlslid, djxl);
                //更新权利人 信息后回写数据
                if (StringUtils.isNotBlank(gzlslid)) {
                    bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(gzlslid);
                    bdcRyzdFeignService.updateGyqkWithGzlslid(gzlslid);
                    //回写信息到平台
                    try {
                        bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                    } catch (Exception e) {
                        LOGGER.error(MSG_HXFAIL, e);
                    }
                }
            }
            //3.处理附件数据
            if (StringUtils.isNotBlank(MapUtils.getString(contentMap, "imageurl", StringUtils.EMPTY))) {
                try {
                    SjclUploadDTO sjclUploadDTO = new SjclUploadDTO();
                    sjclUploadDTO.setGzlslid(gzlslid);
                    sjclUploadDTO.setDjxl(djxl);
                    sjclUploadDTO.setSjclmc(CommonConstantUtils.SJCLMC_CLQD);
                    byte[] netFile = Base64Utils.getFile(MapUtils.getString(contentMap, "imageurl", StringUtils.EMPTY));
                    String fileString = new String(netFile, StandardCharsets.UTF_8);
                    JSONObject fileJson = JSONObject.parseObject(fileString);
                    String base64 = fileJson.getJSONObject("message_content").getString("licencePDF");
                    sjclUploadDTO.setSjclnr("data:application/pdf;base64," + base64);
                    bdcSjclService.createAndUploadFile(sjclUploadDTO);
                } catch (IOException e) {
                    LOGGER.error("上传营业执照附件失败{}", e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            LOGGER.info("未获取到对应的营业执照数据");
        }
    }

    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询长三角证照信息并导入附件
     * @date : 2022/5/11 10:11
     */
    @Override
    public String querCsjZzxx(CsjZzxxQO csjZzxxQO) {
        String code = "";
        if (StringUtils.isNoneBlank(csjZzxxQO.getZzlx(), csjZzxxQO.getZjh(), csjZzxxQO.getGzlslid(), csjZzxxQO.getGzldyid())) {
            String[] zzlxList = csjZzxxQO.getZzlx().split(CommonConstantUtils.ZF_YW_DH);
            LOGGER.warn("当前流程实例id{},选择的证照类型为{}", csjZzxxQO.getGzlslid(), zzlxList);
            //获取证照名称字典项数据
            List<Map> zdList = bdcSlZdFeignService.queryBdcSlzd("csjzzmc");
            //本省证照名称
            List<String> bszzmcList = new ArrayList<>(1);
            Map<String, String> bszzmcMap = new HashMap<>(1);
            //标准证照名称
            List<String> bzzzmcList = new ArrayList<>(1);
            Map<String, String> bzzzmcMap = new HashMap<>(1);
            if (CollectionUtils.isNotEmpty(zdList)) {
                for (String zzlx : zzlxList) {
                    List<Map> zzlxDzbzMap = zdList.stream().filter(zd -> StringUtils.equals(zd.get("ZZLX").toString(), zzlx)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(zzlxDzbzMap)) {
                        for (Map zzlxMcMap : zzlxDzbzMap) {
                            //证照来源为1 的表示本省的证照
                            if (StringUtils.equals("1", MapUtils.getString(zzlxMcMap, "ZZLY", ""))) {
                                bszzmcList.add(MapUtils.getString(zzlxMcMap, "MC", ""));
                                bszzmcMap.put(MapUtils.getString(zzlxMcMap, "MC", ""), zzlx);
                            } else {
                                bzzzmcList.add(MapUtils.getString(zzlxMcMap, "MC", ""));
                                bzzzmcMap.put(MapUtils.getString(zzlxMcMap, "MC", ""), zzlx);
                            }
                        }
                    }
                }
            }
            LOGGER.warn("当前流程实例id{}配置的本省证照{}标准证照{}", csjZzxxQO.getGzlslid(), bszzmcList, bzzzmcList);
            //根据勾选的证照类型查询证照名称
            //根据勾选的证照类型查询所有的证照名称
            BdcSlCsjpzDO bdcSlCsjpzQO = new BdcSlCsjpzDO();
            bdcSlCsjpzQO.setGzldyid(csjZzxxQO.getGzldyid());
            List<BdcSlCsjpzDO> bdcSlCsjpzDOList = bdcSlCsjpzService.listCsjpz(bdcSlCsjpzQO);
            if (CollectionUtils.isNotEmpty(bdcSlCsjpzDOList)) {
                LOGGER.info("当前流程实例id{}定义id{}查询到的长三角配置信息{}", csjZzxxQO.getGzlslid(), csjZzxxQO.getGzldyid(), JSON.toJSONString(bdcSlCsjpzDOList));
                Map<String, String> paramMap = new HashMap<>(4);
                paramMap.put("identity_number", csjZzxxQO.getZjh());
                paramMap.put("item_id", bdcSlCsjpzDOList.get(0).getYwbm());
                //为空表示查省内，不为空"是"表示查长三角
                paramMap.put("is_provincecert", "");
                paramMap.put("sign", sign);
                paramMap.put("license_type", StringUtils.join(bszzmcList, CommonConstantUtils.ZF_YW_DH));
                LOGGER.warn("当前流程实例id{}查询证照信息入参{}", csjZzxxQO.getGzlslid(), paramMap);
                Object response = exchangeInterfaceFeignService.requestInterface("csj_getCertData", paramMap);
                if (Objects.nonNull(response)) {
                    LOGGER.warn("当前流程实例id{}查询本省证照信息返回值{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(response));
                    JiangsuZzxxDTO jiangsuZzxxDTO = JSON.parseObject(JSON.toJSONString(response), JiangsuZzxxDTO.class);
                    //判断是否调用成功
                    if (Objects.nonNull(jiangsuZzxxDTO) && Objects.nonNull(jiangsuZzxxDTO.getStatus()) && jiangsuZzxxDTO.getStatus().getCode() == 200) {
                        //成功保存附件信息
//                        code = "200";
                        List<JiangsuZzxxDTO.CustomBean.CertfilesBean> certfilesBeanList = jiangsuZzxxDTO.getCustom().getCertfiles();
                        List<JiangsuZzxxDTO.CustomBean.LicensedatasBean> licensedatasBeanList = jiangsuZzxxDTO.getCustom().getLicensedatas();
                        if (CollectionUtils.isNotEmpty(certfilesBeanList) && CollectionUtils.isNotEmpty(licensedatasBeanList)) {
                            //接口调用成功，且获取到证照的情况，才返回200
                            code = CommonConstantUtils.RESPONSE_SUCCESS;

                            if (Boolean.TRUE.equals(glsnfj)) {
                                //当查询省内接口时，会将名下所有的证照均返回,而实际上办理不动产业务有些证照不需要。要求根据勾选的证照类型进行过滤，只将需要的证照类型下载到附件中。
                                for (JiangsuZzxxDTO.CustomBean.LicensedatasBean licensedata : licensedatasBeanList) {
                                    if (StringUtils.isNotBlank(licensedata.getData().getName()) && bszzmcList.contains(licensedata.getData().getName())) {//只上传勾选的证照类型
                                        //确认文件名
                                        String fileName = licensedata.getData().getLicense_code();
                                        //根据勾选的证照类型，去certifies中确定附件名
                                        for (JiangsuZzxxDTO.CustomBean.CertfilesBean certfiles : certfilesBeanList) {
                                            if (Objects.nonNull(certfiles.getData()) && StringUtils.isNotBlank(certfiles.getData().getFile_data()) && StringUtils.isNotBlank(certfiles.getData().getFile_name())) {
                                                //取文件名，不带文件格式
                                                String fileMc = certfiles.getData().getFile_name();
                                                fileMc = fileMc.split("\\.")[0];
                                                if (fileName.equals(fileMc)) {
                                                    try {
                                                        bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + certfiles.getData().getFile_data(), csjZzxxQO.getGzlslid(), wjjmc, certfiles.getData().getFile_name(), CommonConstantUtils.WJHZ_PDF);
                                                    } catch (IOException e) {
                                                        LOGGER.error("上传附件失败，附件名称{}", certfiles.getData().getFile_name());
                                                    }
                                                }

                                            }

                                        }

                                    }
                                }
                            } else {
                                for (JiangsuZzxxDTO.CustomBean.CertfilesBean certfiles : certfilesBeanList) {
                                    //上传附件信息
                                    if (Objects.nonNull(certfiles.getData()) && StringUtils.isNotBlank(certfiles.getData().getFile_data())) {
                                        try {
                                            bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + certfiles.getData().getFile_data(), csjZzxxQO.getGzlslid(), wjjmc, certfiles.getData().getFile_name(), CommonConstantUtils.WJHZ_PDF);
                                        } catch (IOException e) {
                                            LOGGER.error("上传附件失败，附件名称{}", certfiles.getData().getFile_name());
                                        }
                                    }
                                }
                            }

                            //没有获取到的需要调用长三角的信息
                            List<String> lastZzxxList = new ArrayList<>(1);
                            for (JiangsuZzxxDTO.CustomBean.LicensedatasBean licensedata : licensedatasBeanList) {
                                if (!bszzmcList.contains(licensedata.getData().getName())) {
                                    String zzmc = MapUtils.getString(bzzzmcMap, licensedata.getData().getName(), "");
                                    if (StringUtils.isNotBlank(zzmc)) {
                                        lastZzxxList.add(zzmc);
                                    }
                                }
                            }
                            if (CollectionUtils.isNotEmpty(lastZzxxList)) {
                                paramMap.put("is_provincecert", CommonConstantUtils.SF_ZW_S);
                                paramMap.put("license_type", StringUtils.join(lastZzxxList, CommonConstantUtils.ZF_YW_DH));
                                LOGGER.warn("当前流程实例id{}剩余部分证照类型未获取到证照信息{}，开始查询长三角接口", csjZzxxQO.getGzlslid(), JSON.toJSONString(bzzzmcList));
                                String codeCsj = exchangeCsjZzxx(paramMap, csjZzxxQO.getGzlslid());
                                if (StringUtils.equals(codeCsj, "")) {
                                    //获取到本省证照，但是调用长三角接口失败，返回"";
                                    code = "";
                                }
                            }
                        } else {
                            LOGGER.error("当前流程实例id{}查询本省证照无材料返回，开始查询长三角证照信息", csjZzxxQO.getGzlslid());
                            //调用长三角接口查询
                            paramMap.put("is_provincecert", CommonConstantUtils.SF_ZW_S);
                            paramMap.put("license_type", StringUtils.join(bzzzmcList, CommonConstantUtils.ZF_YW_DH));
                            code = exchangeCsjZzxx(paramMap, csjZzxxQO.getGzlslid());
                        }
                    } else {
                        LOGGER.error("当前流程实例id{}查询本省证照失败，返回结果code!=200，开始查询长三角证照信息", csjZzxxQO.getGzlslid());
                        //调用长三角证照接口
                        paramMap.put("is_provincecert", CommonConstantUtils.SF_ZW_S);
                        paramMap.put("license_type", StringUtils.join(bzzzmcList, CommonConstantUtils.ZF_YW_DH));
                        exchangeCsjZzxx(paramMap, csjZzxxQO.getGzlslid());
                    }
                }
            } else {
                LOGGER.error("当前流程实例id{},定义id{}未查询到长三角业务配置信息", csjZzxxQO.getGzlslid(), csjZzxxQO.getGzldyid());
            }
        } else {
            LOGGER.error("查询证照信息缺失必要参数{}", JSON.toJSONString(csjZzxxQO));
            throw new AppException("查询证照信息缺失必要参数");
        }
        return code;
    }

    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码查询长三角证照信息并带入权利人信息
     * @date : 2022/5/12 8:35
     */
    @Override
    public String queryCsjZzxxBySfm(CsjZzxxQO csjZzxxQO) {
        String code = "";
        if (Objects.nonNull(csjZzxxQO) && StringUtils.isNotBlank(csjZzxxQO.getSfmnr())) {
            //1. 获取业务代码
            BdcSlCsjpzDO bdcSlCsjpzQO = new BdcSlCsjpzDO();
            bdcSlCsjpzQO.setGzldyid(csjZzxxQO.getGzldyid());
            List<BdcSlCsjpzDO> bdcSlCsjpzDOList = bdcSlCsjpzService.listCsjpz(bdcSlCsjpzQO);
            String[] zzlxList = csjZzxxQO.getZzlx().split(CommonConstantUtils.ZF_YW_DH);
            LOGGER.warn("当前流程实例id{},苏服码查询选择的证照类型为{}", csjZzxxQO.getGzlslid(), zzlxList);
            //获取证照名称字典项数据
            List<Map> zdList = bdcSlZdFeignService.queryBdcSlzd("csjzzmc");
            //本省证照名称
            List<String> bszzmcList = new ArrayList<>(1);
            Map<String, String> bszzmcMap = new HashMap<>(1);
            //标准证照名称
            List<String> bzzzmcList = new ArrayList<>(1);
            Map<String, String> bzzzmcMap = new HashMap<>(1);
            if (CollectionUtils.isNotEmpty(zdList)) {
                for (String zzlx : zzlxList) {
                    List<Map> zzlxDzbzMap = zdList.stream().filter(zd -> StringUtils.equals(zd.get("ZZLX").toString(), zzlx)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(zzlxDzbzMap)) {
                        for (Map zzlxMcMap : zzlxDzbzMap) {
                            //证照来源为1 的表示本省的证照
                            if (StringUtils.equals("1", MapUtils.getString(zzlxMcMap, "ZZLY", ""))) {
                                bszzmcList.add(MapUtils.getString(zzlxMcMap, "MC", ""));
                                bszzmcMap.put(MapUtils.getString(zzlxMcMap, "MC", ""), zzlx);
                            } else {
                                bzzzmcList.add(MapUtils.getString(zzlxMcMap, "MC", ""));
                                bzzzmcMap.put(MapUtils.getString(zzlxMcMap, "MC", ""), zzlx);
                            }
                        }
                    }
                }
            }
            LOGGER.warn("当前流程实例id{}配置的本省证照{}标准证照{}", csjZzxxQO.getGzlslid(), bszzmcList, bzzzmcList);
            if (CollectionUtils.isNotEmpty(bdcSlCsjpzDOList)) {
                LOGGER.info("当前流程实例id{}定义id{}查询到的长三角配置信息{}", csjZzxxQO.getGzlslid(), csjZzxxQO.getGzldyid(), JSON.toJSONString(bdcSlCsjpzDOList));
                Map<String, String> paramMap = new HashMap<>(4);
                paramMap.put("item_id", bdcSlCsjpzDOList.get(0).getYwbm());
                //为空表示查省内，不为空"是"表示查长三角
                paramMap.put("sfmnumber", csjZzxxQO.getSfmnr());
                paramMap.put("sign", sign);
                paramMap.put("license_type", StringUtils.join(bszzmcList, CommonConstantUtils.ZF_YW_FH));
                LOGGER.warn("当前流程实例id{}苏服码查询信息入参{}", csjZzxxQO.getGzlslid(), paramMap);
                Object response = exchangeInterfaceFeignService.requestInterface("csj_getSFMData", paramMap);
                if (Objects.nonNull(response)) {
                    LOGGER.warn("当前流程实例id{}查询苏服码信息返回值{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(response));
                    SfmResponse sfmResponse = JSON.parseObject(JSON.toJSONString(response), SfmResponse.class);
                    if (Objects.nonNull(sfmResponse) && Objects.nonNull(sfmResponse.getStatus()) && 200 == sfmResponse.getStatus().getCode()) {
//                        code = CommonConstantUtils.RESPONSE_SUCCESS;
                        //组织权利人数据，新增权利人
                        List<SfmResponse.CustomBean.DataBean> customBeanList = sfmResponse.getCustom().getData();
                        if (CollectionUtils.isNotEmpty(customBeanList)) {
                            for (SfmResponse.CustomBean.DataBean dataBean : customBeanList) {
                                SfmResponse.CustomBean.DataBean.LiscenseInfoBean liscenseInfo = dataBean.getLiscenseInfo();
                                if (Objects.nonNull(liscenseInfo) && StringUtils.isNotBlank(liscenseInfo.getHolder_name()) && StringUtils.isNotBlank(liscenseInfo.getHolder_identity_num())) {
                                    BdcQlrDO bdcQlrDO = new BdcQlrDO();
                                    bdcQlrDO.setQlrmc(liscenseInfo.getHolder_name());
                                    bdcQlrDO.setZjh(liscenseInfo.getHolder_identity_num());
                                    bdcQlrDO.setZjzl(1);
                                    bdcQlrDO.setQlrlx(1);
                                    bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                                    bdcQlrDO.setXmid(csjZzxxQO.getXmid());
                                    insertSfmQlr(JSON.toJSONString(bdcQlrDO), csjZzxxQO.getGzlslid(), csjZzxxQO.getDjxl(), csjZzxxQO.getLclx());
                                    //获取到权利人信息后根据权利人信息查询长三角证照信息或者本省证照信息
                                    csjZzxxQO.setZjh(liscenseInfo.getHolder_identity_num());
                                    String codeCsjZzxx = querCsjZzxx(csjZzxxQO);
                                    if (CommonConstantUtils.RESPONSE_SUCCESS.equals(codeCsjZzxx)) {
                                        code = CommonConstantUtils.RESPONSE_SUCCESS;
                                    }
                                    //扫码权利人只有一个，一旦新增了就退出循环
                                    break;
                                } else {
                                    LOGGER.error("当前流程实例id{}查询苏服码信息返回值权利人信息缺失{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(liscenseInfo));
                                }
                            }
                        }

                    } else {
                        LOGGER.error("当前流程实例id{}查询苏服码信息失败，返回结果code!=200，开始查询长三角证照信息", csjZzxxQO.getGzlslid());
                    }
                }
            } else {
                LOGGER.error("当前流程实例id{},定义id{}未查询到长三角业务配置信息", csjZzxxQO.getGzlslid(), csjZzxxQO.getGzldyid());
            }

        }
        return code;
    }

    /**
     * @param csjZzxxQO
     * @author wangyinghao
     * @description 扫描苏服码
     * @date : 2022/5/12 8:35
     */
    @Override
    public List<SfmLiscenseInfoDTO> queryCsjZzxxBySfmCx(CsjZzxxQO csjZzxxQO) {
        List<SfmLiscenseInfoDTO> sfmLiscenseInfo = new ArrayList<>();
        if (Objects.nonNull(csjZzxxQO) && StringUtils.isNotBlank(csjZzxxQO.getSfmnr())) {
            return bdcSfmService.queryCsjZzxxBySfmCx(csjZzxxQO);
        }
        return sfmLiscenseInfo;
    }

    /**
     * @param csjZzxxQO
     * @author wangyinghao
     * @description 扫描苏服码带入权利人信息
     * @date : 2022/5/12 8:35
     */
    @Override
    public void queryCsjZzxxBySfmAdd(CsjZzxxQO csjZzxxQO) {
        List<SfmLiscenseInfoDTO> sfmLiscenseInfoDTOS = bdcSfmService.queryCsjZzxxBySfmCx(csjZzxxQO);
        if(CollectionUtils.isNotEmpty(sfmLiscenseInfoDTOS)){
            for (SfmLiscenseInfoDTO liscenseInfo : sfmLiscenseInfoDTOS) {
                if (Objects.nonNull(liscenseInfo)
                        && StringUtils.isNotBlank(liscenseInfo.getHolder_name())
                        && StringUtils.isNotBlank(liscenseInfo.getHolder_identity_num())) {
                    BdcQlrDO bdcQlrDO = new BdcQlrDO();
                    bdcQlrDO.setQlrmc(liscenseInfo.getHolder_name());
                    bdcQlrDO.setZjh(liscenseInfo.getHolder_identity_num());
                    bdcQlrDO.setZjzl(1);
                    bdcQlrDO.setQlrlx(1);
                    bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    bdcQlrDO.setXmid(csjZzxxQO.getXmid());
                    insertSfmQlr(JSON.toJSONString(bdcQlrDO), csjZzxxQO.getGzlslid(), csjZzxxQO.getDjxl(), csjZzxxQO.getLclx());
                    //扫码权利人只有一个，一旦新增了就退出循环
                    break;
                } else {
                    LOGGER.error("当前流程实例id{}查询苏服码信息返回值权利人信息缺失{}", csjZzxxQO.getGzlslid(), JSON.toJSONString(liscenseInfo));
                }
            }
        }
    }

    /**
     * @param csjZzxxQO
     * @author wangyinghao
     * @description 扫描苏服码带入附件
     * @date : 2022/5/12 8:35
     */
    @Override
    public void queryCsjZzxxBySfmAddFj(CsjZzxxQO csjZzxxQO) {
        List<SfmLiscenseFjDTO> sfmLiscenseFjDTOS = bdcSfmService.queryCsjZzxxBySfmFj(csjZzxxQO);
        if(CollectionUtils.isNotEmpty(sfmLiscenseFjDTOS)){
            for (SfmLiscenseFjDTO liscenseFj : sfmLiscenseFjDTOS) {
                if (Objects.nonNull(liscenseFj.getPdf())
                        && csjZzxxQO.getLicense_code().equals(liscenseFj.getLicense_code())) {
                    try {
                        bdcUploadFileUtils.uploadBase64File(
                                CommonConstantUtils.BASE64_QZ_PDF + liscenseFj.getPdf(),
                                csjZzxxQO.getGzlslid(),
                                liscenseFj.getName(),
                                liscenseFj.getHolder_name()+ "_" + liscenseFj.getName(),
                                CommonConstantUtils.WJHZ_PDF);
                    } catch (IOException e) {
                        LOGGER.error("上传附件失败，附件名称{}", liscenseFj.getName());
                    }
                }
            }
        }
    }

    private String exchangeCsjZzxx(Map paramMap, String gzlslid) {
        String code = "";
        Object response = exchangeInterfaceFeignService.requestInterface("csj_getCertData", paramMap);
        if (Objects.isNull(response)) {
            LOGGER.warn("当前流程实例id{}未查询到长三角的证照信息", gzlslid);
        }
        LOGGER.warn("当前流程实例id{}查询长三角证照信息返回值{}", gzlslid, JSON.toJSONString(response));
        CsjZzxxDTO csjZzxxDTO = JSON.parseObject(JSON.toJSONString(response), CsjZzxxDTO.class);
        if (Objects.nonNull(csjZzxxDTO) && Objects.nonNull(csjZzxxDTO.getStatus()) && Objects.equals(200, csjZzxxDTO.getStatus().getCode())) {
//            code = "2000";//接口调用成功，但是可能未获取到证照信息
            List<CsjZzxxDTO.CustomBean.CertfilesBean> certfilesBeanList = csjZzxxDTO.getCustom().getCertfiles();
            if (CollectionUtils.isNotEmpty(certfilesBeanList)) {
                code = CommonConstantUtils.RESPONSE_SUCCESS;//接口调用成功，且获取到证照信息的情况
                for (CsjZzxxDTO.CustomBean.CertfilesBean certfile : certfilesBeanList) {
                    //上传附件
                    if (StringUtils.isNotBlank(certfile.getFile_data())) {
                        try {
                            bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + certfile.getFile_data(), gzlslid, wjjmc, certfile.getFile_name(), CommonConstantUtils.WJHZ_PDF);
                        } catch (IOException e) {
                            LOGGER.error("上传附件失败，附件名称{}", certfile.getFile_name());
                        }
                    }
                }
            } else {
                LOGGER.warn("当前流程实例id{}未获取到长三角证照信息，本次查询结束", gzlslid);
            }
        }
        return code;
    }

    /**
     * 批量更新房地产权
     */
    private void modifyFdcqTdsyqr(String gzlslid, String xmid, String djxl) {
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
        if (!ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcCshFwkgSlDO.getQllx())) {
            return;
        }
        if (Objects.nonNull(djxl)) {
            BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
            bdcDjxxUpdateQO.setClassName(BdcFdcqDO.class.getName());
            // 更新参数
            JSONObject param = new JSONObject();
            param.put("tdsyqr", this.generateTdsyqr(xmid));
            // 更新条件
            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(param));
            Map<String, Object> map = new HashMap<>(2);
            map.put("djxl", djxl);
            try {
                map.put("gzlslid", gzlslid);
                bdcDjxxUpdateQO.setWhereMap(map);
                bdcQllxFeignService.updateBatchBdcQl(bdcDjxxUpdateQO);

            } catch (Exception e) {
                LOGGER.error("更新房地产权土地使用权人信息失败, djxl:{}", djxl, e);
            }
        }
    }

    /**
     * 处理权利人JSON数据，将其转换为对象
     */
    private List<BdcQlrDO> handlerQlrJson(String json) {
        String firststr = json.substring(0, 1);
        if (CommonConstantUtils.ZF_YW_Z_ZKH.equals(firststr)) {
            return JSONArray.parseArray(json, BdcQlrDO.class);
        } else if (CommonConstantUtils.ZF_YW_Z_DKH.equals(firststr)) {
            List<BdcQlrDO> bdcQlrDOList = new ArrayList<>(1);
            bdcQlrDOList.add(JSON.parseObject(json, BdcQlrDO.class));
            return bdcQlrDOList;
        }
        return Collections.emptyList();
    }

    /**
     * 生成土地使用权人信息
     */
    private String generateTdsyqr(String xmid) {
        String tdsyqr = "";
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            List<String> qlrmcList = bdcQlrDOList.stream().map(BdcQlrDO::getQlrmc).distinct().collect(Collectors.toList());
            tdsyqr = StringUtils.join(qlrmcList, CommonConstantUtils.ZF_YW_XG);
        }
        return tdsyqr;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取项目ID集合作为权利人更新过滤条件
     */
    private List<String> getGroupXmidList(String xmid) {
        List<String> xmidList = new ArrayList<>();
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
        //证书序号有值并且为抵押权,获取相同序号的所有项目
        if (bdcCshFwkgSlDO.getZsxh() != null && CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcCshFwkgSlDO.getQllx())) {
            xmidList = bdcXmFeignService.queryZsxmList(xmid);
        }
        return xmidList;

    }

    private void insertSfmQlr(String json, String gzlslid, String djxl, Integer lclx) {
        JSONObject obj = JSONObject.parseObject(json);
        if (Objects.equals(CommonConstantUtils.LCLX_ZH, lclx) || Objects.equals(CommonConstantUtils.LCLX_PT, lclx)) {
            List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_INSERT);
            if (CollectionUtils.isNotEmpty(jsonObjectList)) {
                for (JSONObject object : jsonObjectList) {
                    BdcQlrDO bdcQlrDO = JSONObject.toJavaObject(object, BdcQlrDO.class);
                    if (bdcQlrDO != null && bdcQlrDO.getQlrly() == null) {
                        bdcQlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
                    }
                    bdcQlrFeignService.insertBdcQlr(bdcQlrDO);
                }
            }
        }
        if (Objects.equals(CommonConstantUtils.LCLX_PL, lclx)) {
            BdcQlrDO bdcQlrDO = JSON.parseObject(json, BdcQlrDO.class);
            //新增前先删除相同的权利人,防止有重复权利人数据
            bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), gzlslid, bdcQlrDO.getQlrlb(), "", new ArrayList<>());
            if (bdcQlrDO.getQlrly() == null) {
                bdcQlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
            }
            bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO, gzlslid, "");
        }
        //回写冗余字段
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(gzlslid);
            bdcRyzdFeignService.updateGyqkWithGzlslid(gzlslid);
            //回写信息到平台
            try {
                bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
            } catch (Exception e) {
                LOGGER.error(MSG_HXFAIL, e);
            }
        }
        if (Objects.equals(CommonConstantUtils.LCLX_PLZH, lclx)) {
            insertPlzhBdcQlr(json, gzlslid, djxl, false);
        }
    }

}
