package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCfDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJfxxQO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcCfxxMapper;
import cn.gtmap.realestate.inquiry.service.BdcCfxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 查封信息查询服务
 */
@Service
public class BdcCfxxServiceImpl implements BdcCfxxService {

    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private BdcCfxxMapper bdcCfxxMapper;

    private static final Logger logger = LoggerFactory.getLogger(BdcCfxxServiceImpl.class);


    /**
     * 存储递归后的xmids集合
     */

    private  LinkedHashSet<String>  finalXmids = new LinkedHashSet();

    /**
     * @param pageable 分页参数
     * @param bdcCfxxQO 查询Qo
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.BdcCfxxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询已登记不动产单元信息
     */
    @Override
    public Page<BdcCfxxDTO> listBdcCfxxByPage(Pageable pageable, BdcCfxxQO bdcCfxxQO) {
        return repo.selectPaging("listBdcInquiryCfxxByPageOrder", bdcCfxxQO, pageable);
    }

    /**
     * @param bdcXfxxQO 查询Qo
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.BdcCfxxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 续封信息查询
     */
    @Override
    public List<Map> listBdcXfxx(BdcCfxxQO bdcXfxxQO) {
        return repo.selectList("listBdcInquiryXfxx", bdcXfxxQO);
    }

    /**
     * @param bdcCfxxQO 查询Qo
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.BdcCfxxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查封到期接口
     */
    @Override
    public Object bdcCfDqList(Pageable pageable, BdcCfxxQO bdcCfxxQO) {
        if(bdcCfxxQO.getQueryType() == 0){//不分页
            return  repo.selectList("listBdcXfDqxx", bdcCfxxQO);
        }else{//分页
            return repo.selectPaging("listBdcXfDqxxByPage", bdcCfxxQO, pageable);
        }
    }

    /**
     * @param bdcJfxxQO 解封Qo
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 解封数据
     */
    @Override
    public void jfBdcCf(BdcJfxxQO bdcJfxxQO){

        BdcZxQO bdcZxQO = new BdcZxQO();
        bdcZxQO.setXmidList(Arrays.asList(bdcJfxxQO.getXmid().split(",")));

        if(bdcJfxxQO.getXmid().indexOf(",")!=-1){
            Set<String> resultXmidsSet = getCurrentAllCfxmids(Arrays.asList(bdcJfxxQO.getXmid().split(",")));
            finalXmids =  new LinkedHashSet();
            List<String> resultXmids = new ArrayList<>(resultXmidsSet);
            if(CollectionUtils.isNotEmpty(resultXmids)){
                bdcZxQO.setXmidList(resultXmids);
            }else{
                bdcZxQO.setXmidList(Arrays.asList(bdcJfxxQO.getXmid().split(",")));
            }
        }else{
            Set<String> resultXmidsSet = new HashSet<>();
            resultXmidsSet.add(bdcJfxxQO.getXmid());
            resultXmidsSet = getCurrentAllCfxmids(Arrays.asList(bdcJfxxQO.getXmid().split(",")));
            finalXmids =  new LinkedHashSet();
            List<String> resultXmids = new ArrayList<>(resultXmidsSet);
            if(CollectionUtils.isNotEmpty(resultXmids)){
                bdcZxQO.setXmidList(resultXmids);
            }
        }

        // 注销权利的操作统一调用接口进行注销
        bdcZxQO.setQszt(CommonConstantUtils.QSZT_HISTORY);
        bdcZxQO.setZxyy(bdcJfxxQO.getJfyy());
        logger.info("注销权利：{}",bdcZxQO.toString());
        bdcDbxxFeignService.zxQl(bdcZxQO,userManagerUtils.getCurrentUserName());
        finalXmids =  new LinkedHashSet();
    }

    /**
     * @param bdcJfxxQO 更新Qo
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新解封数据
     */
    @Override
    public void editBdcCf(BdcJfxxQO bdcJfxxQO){

        if(StringUtils.isNotBlank(bdcJfxxQO.getXmid())){
            repo.update("updateCfxx",bdcJfxxQO);
        }
    }

    @Override
    public int queryWdqXfByBdcyhs(Map map){
        if(map.containsKey("xmid")){
            List<String> list = (List<String>)map.get("xmid");
            map.put("xmid",getCurrentAllCfxmids(list));
            finalXmids = new LinkedHashSet<>();
        }
        logger.info("查询当前查封进程是否有未到期的：{}",map);
        return repo.selectOne("queryWdqXfByBdcyhs",map);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询查封
     */
    @Override
    public List listBdcCfByXmid(String xmid) {
        return bdcCfxxMapper.listBdcCfByXmid(xmid);
    }

    /**
     * @param bdcdyh
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询查封信息
     */
    @Override
    public List listBdcCfByBdcdyh(String bdcdyh) {
        return bdcCfxxMapper.listBdcCfByBdcdyh(bdcdyh);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询查封信息
     */
    @Override
    public List<BdcCfDO> listBdcCfByBdcdyhs(List<String> bdcdyhList) {
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            return Lists.newArrayList();
        }
        return bdcCfxxMapper.listBdcCfByBdcdyhs(bdcdyhList);
    }

    @Override
    public List<BdcCfDTO> listBdcCfxx(BdcCfxxQO bdcCfQO) {
        return bdcCfxxMapper.listBdcCfxx(bdcCfQO);
    }

    @Override
    public List<BdcCfDTO> getBdcCfxxAndYcfbh(BdcCfxxQO bdcCfQO) {
        return bdcCfxxMapper.listBdcXfxx(bdcCfQO);
    }

    @Override
    public List<BdcCfDO> listYcfxxByGzlslid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            return bdcCfxxMapper.listYcfxxByGzlslid(gzlslid);
        }
        return new ArrayList<>();

    }

    /**
     * @param bdcCfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取查封信息根据查封顺序和时间排序
     * @date : 2021/11/1 11:13
     */
    @Override
    public List<BdcCfDTO> listBdcCfxxByCfsx(BdcCfxxQO bdcCfxxQO) {
        return bdcCfxxMapper.listBdcCfxxByCfsx(bdcCfxxQO);
    }


    /**
     * 根据xmid 查询当前这个查封进程所有的xmid
     *
     * @param xmids
     * @return
     */
    private Set<String> getCurrentAllCfxmids(List<String> xmidList) {
        Set<String> resultXmids = new LinkedHashSet();
        try {
            if (CollectionUtils.isNotEmpty(xmidList)) {
                getLastCfOrXfXmids(xmidList, "queryLastXmids");
                getLastCfOrXfXmids(xmidList, "queryNextXmids");
                return finalXmids;
            }
        } catch (Exception e) {
            return resultXmids;
        }
        return resultXmids;

    }

    /**
     * 向上 找查封和续封
     * 当前的xmid 是为lsgx的xmid
     * lsgx的yxmid 是为目的xmid
     * @param xmids
     * @return
     */
    private void getLastCfOrXfXmids(List<String> xmids,String queryName){
        if(CollectionUtils.isNotEmpty(xmids)){
            finalXmids.addAll(xmids);
            Map map = new HashMap();
            map.put("xmid",xmids);
            List<Map<String,String>> listmap = repo.selectList(queryName,map);
            if(CollectionUtils.isNotEmpty(listmap)){
                logger.info("根据项目id集合：{}，查询上下手项目：{}",map,listmap);
                List xmidsTemp = new ArrayList();
                for(int i=0;i<listmap.size();i++){
                    xmidsTemp.add(listmap.get(i).get("XMID"));
                }
                getLastCfOrXfXmids(xmidsTemp,queryName);
            }
        }

    }
}
