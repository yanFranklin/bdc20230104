package cn.gtmap.realestate.config.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtGgDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtGgVO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.config.core.service.BdcXtGgService;
import cn.gtmap.realestate.config.service.BdcConfigService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/19
 * @description 不动产公告配置服务
 */
@Service
public class BdcXtGgServiceImpl implements BdcXtGgService {

    private static final Logger logger = LoggerFactory.getLogger(BdcXtGgServiceImpl.class);

    @Autowired
    private Repo repo;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcConfigService bdcConfigService;

    @Override
    public Page<BdcXtGgVO> listBdcXtGgByPage(Pageable pageable, BdcXtGgDO bdcXtGgDO){
        return repo.selectPaging("listBdcXtGgByPage", bdcXtGgDO, pageable);

    }

    @Override
    public int saveOrUpdateBdcXtGg(BdcXtGgDO bdcXtGgDO){
        if(StringUtils.isBlank(bdcXtGgDO.getXtggid())){
            bdcXtGgDO.setXtggid(UUIDGenerator.generate16());
            return entityMapper.insertSelective(bdcXtGgDO);
        }else {
            return entityMapper.updateByPrimaryKeySelective(bdcXtGgDO);
        }

    }

    @Override
    public int deleteBdcXtGg(List<String> xtggidList){
        if (CollectionUtils.isEmpty(xtggidList)) {
            throw new AppException("需要删除的公告配置不能为空！");
        }
        int count =0;
        for(String xtggid:xtggidList){
            count +=entityMapper.deleteByPrimaryKey(BdcXtGgDO.class,xtggid);
        }
        return count;

    }

    @Override
    public List<BdcXtGgDO> listBdcXtGg(BdcXtGgDO bdcXtGgDO){
        if(StringUtils.isBlank(bdcXtGgDO.getGzldyid())){
            throw new AppException("查询公告配置信息缺失工作流定义ID");
        }
        return entityMapper.selectByObj(bdcXtGgDO);
    }

    @Override
    public BdcGgDO generateXtggBySql(String gzlslid, String xmid, BdcXtGgDO bdcXtGgDO){
        if (StringUtils.isAnyBlank(gzlslid,xmid) || bdcXtGgDO == null || StringUtils.isAnyBlank(bdcXtGgDO.getGgbtpz(),bdcXtGgDO.getGgnrpz())) {
            throw new AppException("缺失工作流实例ID或项目ID或未配置公告标题或内容");
        }

        Map<String,Object> configParam = new HashMap(3);
        configParam.put("gzlslid", gzlslid);
        configParam.put("xmid", xmid);
        //获取标题
        BdcGgDO bdcGgDO =new BdcGgDO();
        bdcGgDO.setGgbt(getSqlResult(bdcXtGgDO.getGgbtpz(),configParam));
        //获取公告内容
        bdcGgDO.setGgnr(getSqlResult(bdcXtGgDO.getGgnrpz(),configParam));

        return bdcGgDO;

    }

    @Override
    public List<BdcGgDO> generateXtggBySqlPl(List<BdcXmDO> xmDOS, Integer sply, Integer gglx){
        if(CollectionUtils.isEmpty(xmDOS)){
            logger.warn("未获取到项目信息，根据配置生成公告信息中止！");
            return Collections.emptyList();
        }
        List<BdcGgDO> bdcGgDOList =new ArrayList<>();
        //获取工作流定义ID或项目ID为空的数据1
        List<BdcXmDO> errorXmDTOS = xmDOS.stream().filter(xm -> StringUtils.isBlank(xm.getGzldyid()) ||StringUtils.isBlank(xm.getXmid()) ||(sply !=null&&!sply.equals(xm.getSply()))).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(errorXmDTOS)) {
            logger.warn("存在：{}条项目信息参数不全或不符合,无法获取公告信息:{},sply:{}",errorXmDTOS.size(),xmDOS,sply);
            xmDOS.removeAll(errorXmDTOS);
        }
        if(CollectionUtils.isNotEmpty(xmDOS)){
            //根据工作流定义ID
            Map<String, List<BdcXmDO>> xmDTOMap =xmDOS.stream().collect(Collectors.groupingBy(BdcXmDO::getGzldyid));
            if(MapUtils.isNotEmpty(xmDTOMap)) {
                for (Map.Entry<String, List<BdcXmDO>> entry : xmDTOMap.entrySet()) {
                    //获取公告配置信息
                    BdcXtGgDO bdcXtGgQO =new BdcXtGgDO();
                    bdcXtGgQO.setGzldyid(entry.getKey());
                    bdcXtGgQO.setSply(sply);
                    bdcXtGgQO.setGglx(gglx);
                    List<BdcXtGgDO> bdcXtGgDOList =listBdcXtGg(bdcXtGgQO);
                    if(CollectionUtils.isNotEmpty(bdcXtGgDOList)){
                        for(BdcXmDO bdcXmDO:entry.getValue()){
                            BdcGgDO bdcGgDO =generateXtggBySql(bdcXmDO.getGzlslid(),bdcXmDO.getXmid(),bdcXtGgDOList.get(0));
                            if(bdcGgDO !=null &&StringUtils.isNoneBlank(bdcGgDO.getGgbt(),bdcGgDO.getGgnr())){
                                bdcGgDO.setXmid(bdcXmDO.getXmid());
                                bdcGgDO.setGzlslid(bdcXmDO.getGzlslid());
                                bdcGgDO.setGglx(gglx);
                                bdcGgDOList.add(bdcGgDO);
                            }
                        }
                    }
                }
            }
        }
        return bdcGgDOList;

    }

    /**
      * @param sql
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    private String getSqlResult(String sql,Map<String,Object> configParam){
        String sqrResult ="";
        configParam.put("sql", sql);
        List<Map> configList = bdcConfigService.executeConfigSql(configParam);
        if (CollectionUtils.isNotEmpty(configList) && MapUtils.isNotEmpty(configList.get(0))) {
            Map<String, String> result = configList.get(0);
            //只获取一个sql的一个值
            for (Map.Entry entry : result.entrySet()) {
                sqrResult = entry.getValue().toString();
                break;
            }
        }
        return sqrResult;


    }


}
