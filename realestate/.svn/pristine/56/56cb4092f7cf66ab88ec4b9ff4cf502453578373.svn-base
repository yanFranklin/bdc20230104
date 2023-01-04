package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcSlqkDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.SlqktjQO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.inquiry.service.BdcGzlTjService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/5/15
 * @description  查询子系统：工作量统计
 */
@Service
public class BdcGzlTjServiceImpl implements BdcGzlTjService {
    /**
     * ORM操作
     */
    @Autowired
    private Repo repo;
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzlTjServiceImpl.class);


    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzltjQO 查询条件
     * @return {Page} 工作量统计部门办件量统计
     * @description 工作量统计部门人员办件量统计
     */
    @Override
    public List<Map> listBdcGzltj(GzltjQO gzltjQO) {
        setParamList(gzltjQO);
        return repo.selectList("listBdcGzltj", gzltjQO);
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param slqktjQO 查询条件
     * @return {Page} 工作量统计部门办件量统计
     * @description 工作量统计部门人员办件量统计
     */
    @Override
    public List<Map> listSlqkCount(SlqktjQO slqktjQO) {
        setParamListSlqk(slqktjQO);
        return repo.selectList("listBdcSlqktj", slqktjQO);
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzltjQO 查询条件
     * @return {Page} 工作量统计部门办件量统计
     * @description 工作量统计部门人员办件量统计
     */
    @Override
    public List<Map> listBdcGzltjBmry(GzltjQO gzltjQO) {
        setParamList(gzltjQO);
        return repo.selectList("listBdcGzltjBmry", gzltjQO);
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzltjQO 查询条件
     * @return {Page} 工作量统计部门办件量统计
     * @description 工作量统计部门人员办件量统计
     */
    @Override
    public List<Map> listGzltjMxBjl(GzltjQO gzltjQO) {
        setParamList(gzltjQO);
        return repo.selectList("listBdcGzltjBmmx", gzltjQO);
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @param gzltjQO 查询条件
     * @return {Page} 收件量统计
     * @description 工作量统计收件量统计
     */
    @Override
    public List<Map> listGzltjMxSjl(GzltjQO gzltjQO) {
        setParamList(gzltjQO);
        return repo.selectList("listBdcGzltjSjlmx", gzltjQO);
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param slqktjQO 查询条件
     * @return {Page} 工作量统计部门办件量统计
     * @description 工作量统计部门人员办件量统计
     */
    @Override
    public Page<BdcSlqkDTO> listSlqkMx(SlqktjQO slqktjQO, Pageable pageable) {
        setParamListSlqk(slqktjQO);
        return repo.selectPaging("listBdcSlqktjmxByPage", slqktjQO,pageable);
    }


    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzltjQO 查询条件
     * @return {Page} 工作量统计部门办件量统计
     * @description 工作量统计部门人员办件量统计
     */
    @Override
    public List<Map> listGzltjMxBjlPrint(GzltjQO gzltjQO) {
        JSONArray ja = new JSONArray();
        try {
            ja = JSON.parseArray(URLDecoder.decode(gzltjQO.getPrintFilterJson(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("转码异常"+e.getMessage());
        }
        setParamList(gzltjQO);
        List<Map> listmap = repo.selectList("listBdcGzltjBmmx", gzltjQO);
        List<Map> resultList = new ArrayList<>();

        for(int i=0;i<listmap.size();i++ ){
            // 部门名称
            String orgName = String.valueOf(listmap.get(i).get("ORGNAME"));
            // 节点名称
            String procdefname = String.valueOf(listmap.get(i).get("PROCDEFNAME"));
            // 用户名
            String username = String.valueOf(listmap.get(i).get("USERNAME"));
            for(int j=0;j<ja.size();j++){
                String orgNameFilter = String.valueOf(((Map)ja.get(j)).get("ORGNAME"));
                String procdefnameFilter = String.valueOf(((Map)ja.get(j)).get("PROCDEFNAME"));
                String userNameFilter = String.valueOf(((Map)ja.get(j)).get("USERNAME"));
                boolean orgNameFlag = orgName.equals(orgNameFilter);
                boolean procdefnameFlag = procdefname.equals(procdefnameFilter);
                boolean userNameFlag = username.equals(userNameFilter);
                // 当三个变量相等时，添加到返回信息，并跳出内部循环，继续外部循环
                if(orgNameFlag && procdefnameFlag && userNameFlag ){
                    resultList.add(listmap.get(i));
                    break;
                }
            }
        }

        return resultList;
    }

    /**
     * 处理查询参数，转换成集合
     * @param gzltjQO
     */
    public static void setParamList (GzltjQO gzltjQO){
        if(StringUtils.isNotBlank(gzltjQO.getDjjg()) && gzltjQO.getDjjg().split(",").length > 0){
            gzltjQO.setDjjglist(Arrays.asList(gzltjQO.getDjjg().split(",")));
        }else{
            gzltjQO.setDjjglist(new ArrayList());
        }

//        if(slqktjQO.getJdmc().split(",").length > 1){
//            slqktjQO.setJdmclist(Arrays.asList(slqktjQO.getJdmc().split(",")));
//        }else{
//            slqktjQO.setJdmclist(new ArrayList());
//        }

        if(StringUtils.isNotBlank(gzltjQO.getProcessname()) && gzltjQO.getProcessname().split(",").length > 0){
            gzltjQO.setProcessnamelist(Arrays.asList(gzltjQO.getProcessname().split(",")));
        }else{
            gzltjQO.setProcessnamelist(new ArrayList());
        }

        if(StringUtils.isNotBlank(gzltjQO.getSlrmc() ) && gzltjQO.getSlrmc().split(",").length > 0){
            gzltjQO.setSlrmclist(Arrays.asList(gzltjQO.getSlrmc().split(",")));
        }else{
            gzltjQO.setSlrmclist(new ArrayList());
        }
    }

    /**
     * 处理查询参数，转换成集合
     * @param slqktjQO
     */
    public static void setParamListSlqk (SlqktjQO slqktjQO){
        if(slqktjQO.getDjjg() !=null && slqktjQO.getDjjg().split(",").length > 0
                && slqktjQO.getDjjg().length()>0){
            slqktjQO.setDjjglist(Arrays.asList(slqktjQO.getDjjg().split(",")));
        }else{
            slqktjQO.setDjjglist(new ArrayList());
        }
        if(slqktjQO.getProcessname() != null && slqktjQO.getProcessname().split(",").length > 0
                && slqktjQO.getProcessname().length() > 0){
            slqktjQO.setProcessnamelist(Arrays.asList(slqktjQO.getProcessname().split(",")));
        }else{
            slqktjQO.setProcessnamelist(new ArrayList());
        }

        if(slqktjQO.getSlrmc() != null && slqktjQO.getSlrmc().split(",").length > 0
                && slqktjQO.getSlrmc().length()>0){
            slqktjQO.setSlrmclist(Arrays.asList(slqktjQO.getSlrmc().split(",")));
        }else{
            slqktjQO.setSlrmclist(new ArrayList());
        }

        if(slqktjQO.getDjlx() != null && (slqktjQO.getDjlx()).split(",").length > 0
                && (slqktjQO.getDjlx()).length()>0){
            List list = new ArrayList();
            for(int i = 0;i<slqktjQO.getDjlx().split(",").length;i++){
                list.add(Integer.parseInt(slqktjQO.getDjlx().split(",")[i]));
            }
            slqktjQO.setDjlxlist(list);
        }else{
            slqktjQO.setDjlxlist(new ArrayList());
        }

        if(slqktjQO.getDjxl() != null && slqktjQO.getDjxl().split(",").length > 0
                && slqktjQO.getDjxl().length()>0){
            slqktjQO.setDjxllist(Arrays.asList(slqktjQO.getDjxl().split(",")));
        }else{
            slqktjQO.setDjxllist(new ArrayList());
        }
        if(slqktjQO.getDjyy() != null && slqktjQO.getDjyy().split(",").length > 0
                && slqktjQO.getDjyy().length()>0){
            slqktjQO.setDjyylist(Arrays.asList(slqktjQO.getDjyy().split(",")));
        }else{
            slqktjQO.setDjyylist(new ArrayList());
        }
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param djxl 查询条件
     * @return {Page} 登记原因list
     * @description 登记原因list
     */
    @Override
    public List<Map> djyyList(String djxl){
        Map map = new HashMap();
        if(djxl != null && djxl.split(",").length > 0
                && djxl.length()>0){
            map.put("djxl",Arrays.asList(djxl.split(",")));
        }else{
            map.put("djxl",new ArrayList());
        }
        return repo.selectList("listDjyyByDjxl", map);
    }

}
