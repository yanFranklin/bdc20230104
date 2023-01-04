package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.mapper.BdcDlrMapper;
import cn.gtmap.realestate.init.core.service.BdcDlrService;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.util.DataParseUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @program: realestate
 * @description: 代理人服务实现方法
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-21 16:21
 **/
@Service
public class BdcDlrServiceImpl implements BdcDlrService {

    @Autowired
    BdcXmService bdcXmService;
    @Autowired
    BdcQlrService bdcQlrService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcDlrMapper bdcDlrMapper;


    /**
     * @param json
     * @param gzlslid
     * @param djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量新增代理人
     * @date : 2022/3/21 16:33
     */
    @Override
    public List<BdcDlrDO> insertBatchDlr(String json, String gzlslid, String djxl) {
        return null;
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新代理人
     * @date : 2022/3/21 16:34
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BdcDlrDO> updateBatchDlr(String json, String gzlslid, String djxl) {
        Integer count = 0;
        JSONArray jsonArray = JSON.parseArray(json);
        List<BdcDlrDO> bdcDlrDOList = new ArrayList<>(1);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                //获取代理人修改前的数据,用于批量更新的where条件
                if (StringUtils.isBlank(obj.getString("dlrid"))) {
                    BdcDlrDO bdcDlrDO = JSONObject.parseObject(JSONObject.toJSONString(obj), BdcDlrDO.class);
                    bdcDlrDOList = this.insertBdcDlr(bdcDlrDO, gzlslid, djxl);
                } else {
                    BdcDlrQO bdcDlrQO = new BdcDlrQO();
                    bdcDlrQO.setDlrid(obj.getString("dlrid"));
                    bdcDlrDOList = this.listBdcDlr(bdcDlrQO);
                    //存在即更新，不存在则新增
                    if (CollectionUtils.isNotEmpty(bdcDlrDOList)) {
                        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                        //权利人批量更新移除xmid
                        String qlrid = obj.getString("qlrid");
                        BdcQlrDO bdcQlrDO = bdcQlrService.queryBdcQlrByQlrid(qlrid);
                        //判断是否是义务人，如果是义务人修改后ywr的qlrid 会重新生成，之前的代理人对应的qlrid 会不存在，所以把之前的这条数据删了，重新走新增的逻辑
                        String ywrid = obj.getString("ywrid");
                        if (StringUtils.isNotBlank(ywrid)) {
                            //删除当前权利人对应的dlr
                            deleteBatchDlr(bdcDlrDOList.get(0).getDlrmc(), bdcDlrDOList.get(0).getZjh(), gzlslid, djxl, Arrays.asList(ywrid.split(CommonConstantUtils.ZF_YW_DH)));
                            //走新增逻辑
                            BdcDlrDO bdcDlrDO = JSONObject.parseObject(JSONObject.toJSONString(obj), BdcDlrDO.class);
                            bdcDlrDOList = this.insertBdcDlr(bdcDlrDO, gzlslid, djxl);
                            continue;
                        }
                        obj.remove("xmid");
                        obj.remove("qlrid");
                        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
                        Map<String, Object> map = new HashMap<>();
                        map.put("gzlslid", gzlslid);
                        map.put("dlrmc", bdcDlrDOList.get(0).getDlrmc());
                        map.put("zjh", bdcDlrDOList.get(0).getZjh());
                        map.put("dh", bdcDlrDOList.get(0).getDh());
                        map.put("djxl", djxl);
                        List<BdcQlrDO> bdcQlrDOS = bdcQlrService.listAllBdcQlr(gzlslid, null, bdcQlrDO.getQlrlb(), djxl,null);
                        List<String> qlridList = new ArrayList<>(1);
                        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                            for (BdcQlrDO bdcQlr : bdcQlrDOS) {
                                if (StringUtils.equals(bdcQlr.getQlrmc(), bdcQlrDO.getQlrmc()) && StringUtils.equals(bdcQlr.getZjh(), bdcQlrDO.getZjh())) {
                                    qlridList.add(bdcQlr.getQlrid());
                                }
                            }
                        } else {
                            qlridList.add(qlrid);
                        }
                        map.put("qlridList", qlridList);
                        bdcDjxxUpdateQO.setWhereMap(map);
                        count += this.updateBdcDlr(bdcDjxxUpdateQO);
                    }
                }
            }
        }
        return bdcDlrDOList;
    }

    /**
     * @param bdcDlrQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询不动产代理人信息
     * @date : 2022/3/21 17:11
     */
    @Override
    public List<BdcDlrDO> listBdcDlr(BdcDlrQO bdcDlrQO) {
        if (StringToolUtils.isAllNullOrEmpty(bdcDlrQO.getDlrid(), bdcDlrQO.getXmid(), bdcDlrQO.getQlrid())) {
            throw new AppException("查询代理人信息缺失必要参数");
        }
        BdcDlrDO bdcDlrDO = new BdcDlrDO();
        BeanUtils.copyProperties(bdcDlrQO, bdcDlrDO);
        Example example = entityMapper.objToExample(bdcDlrDO);
        example.setOrderByClause("dlrid");
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param dlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据代理人id删除代理人
     * @date : 2022/3/22 9:01
     */
    @Override
    public int deleteBdcDlrByDlrid(String dlrid) {
        if (StringUtils.isBlank(dlrid)) {
            return 0;
        }
        return entityMapper.deleteByPrimaryKey(BdcDlrDO.class, dlrid);
    }

    /**
     * @param dlrmc
     * @param dlrzjh
     * @param gzlslid
     * @param djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/22 14:13
     */
    @Override
    public int deleteBatchDlr(String dlrmc, String dlrzjh, String gzlslid, String djxl, List<String> qlridList) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put("gzlslid", gzlslid);
            map.put("dlrmc", dlrmc);
            map.put("zjh", dlrzjh);
            map.put("djxl", djxl);
            map.put("qlridList", qlridList);
            return bdcDlrMapper.deleteDlr(map);
        }
        return 0;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据流程删除代理人
     * @date : 2022/3/23 14:59
     */
    @Override
    public int deleteLcDlr(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put("gzlslid", gzlslid);
            return bdcDlrMapper.deleteLcDlr(map);
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<BdcDlrDO> insertBdcDlr(BdcDlrDO bdcDlrDO, String gzlslid, String djxl) {
        List<BdcDlrDO> bdcDlrDOList = new ArrayList<>();
        if (StringUtils.isNoneBlank(gzlslid) && bdcDlrDO != null) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmQO.setDjxl(djxl);
            List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXm(bdcXmQO);
            if (StringUtils.isNotBlank(bdcDlrDO.getQlrid()) && CollectionUtils.isNotEmpty(bdcXmDOList)) {
                List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
                BdcQlrDO bdcQlrDO = bdcQlrService.queryBdcQlrByQlrid(bdcDlrDO.getQlrid());
                if (bdcQlrDO != null) {
                    for (BdcXmDO xmDO : bdcXmDOList) {
                        BdcQlrDO bdcQlr = new BdcQlrDO();
                        bdcQlr.setQlrmc(bdcQlrDO.getQlrmc());
                        bdcQlr.setZjh(bdcQlrDO.getZjh());
                        bdcQlr.setXmid(xmDO.getXmid());
                        List<BdcQlrDO> bdcQlrDOS = bdcQlrService.queryBdcQlrByQlrxx(bdcQlr, "xmid");
                        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                            bdcQlrDOList.add(bdcQlrDOS.get(0));
                        }
                    }
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        for (BdcQlrDO qlrDO : bdcQlrDOList) {
                            BdcDlrDO dlrDO = new BdcDlrDO();
                            BeanUtils.copyProperties(bdcDlrDO, dlrDO);
                            dlrDO.setXmid(qlrDO.getXmid());
                            dlrDO.setQlrid(qlrDO.getQlrid());
                            dlrDO.setDlrid(UUIDGenerator.generate16());
                            bdcDlrDOList.add(dlrDO);
                        }
                    }
                    if (CollectionUtils.isNotEmpty(bdcDlrDOList)) {
                        List<List> partList = ListUtils.subList(bdcDlrDOList, 500);
                        for (List data : partList) {
                            entityMapper.insertBatchSelective(data);
                        }
                    }
                }
            }
        }
        return bdcDlrDOList;
    }


    private int updateBdcDlr(BdcDjxxUpdateQO bdcDjxxUpdateQO) {
        if (bdcDjxxUpdateQO == null || StringUtils.isAnyBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
        }
        //获取更新json对象
        Map map = DataParseUtil.bdcDjxxUpdateQOParseSqlMap(bdcDjxxUpdateQO, BdcDlrDO.class);
        if (MapUtils.isEmpty(map)) {
            return 0;
        } else {
            return bdcDlrMapper.updateBdcDlr(map);
        }
    }
}
