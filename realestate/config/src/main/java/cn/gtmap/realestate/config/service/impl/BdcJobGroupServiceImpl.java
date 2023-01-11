package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobRegistryDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.job.enums.RegistryConfig;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.config.core.mapper.BdcJobGroupMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobInfoMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobRegistryMapper;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import cn.gtmap.realestate.config.service.BdcJobGroupService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobGroup实现类
 */
@Slf4j
@Service
public class BdcJobGroupServiceImpl implements BdcJobGroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcJobGroupService.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private Repo repo;
    @Autowired
    private BdcJobGroupMapper bdcJobGroupMapper;
    @Resource
    public BdcJobInfoMapper bdcJobInfoMapper;
    @Resource
    private BdcJobRegistryMapper bdcJobRegistryMapper;



    /**
     * 分页获取执行器数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param bdcJobGroupDO jobGroup
     * @return JobGroup
     */
    @Override
    public Page<BdcJobGroupDO> listBdcJobGroupPage(Pageable pageable, BdcJobGroupDO bdcJobGroupDO) {
        return repo.selectPaging("listBdcJobGroupByPage", bdcJobGroupDO,pageable);
    }

    /**
     * 保存执行器信息 没有记录则新增
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return ReturnT
     */
    @Override
    public ReturnT<String> saveJobGroup(BdcJobGroupDO bdcJobGroupDO) {

        if (!CheckParameter.checkAnyParameter(bdcJobGroupDO)){
            throw new AppException("传入参数为空！");
        }

        // valid
        if (bdcJobGroupDO.getAppname()==null || bdcJobGroupDO.getAppname().trim().length()==0) {
            return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"AppName") );
        }
        if (bdcJobGroupDO.getAppname().length()<4 || bdcJobGroupDO.getAppname().length()>64) {
            return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appname_length") );
        }
        if (bdcJobGroupDO.getAppname().contains(">") || bdcJobGroupDO.getAppname().contains("<")) {
            return new ReturnT<String>(500, "AppName"+I18nUtil.getString("system_unvalid") );
        }
        if (bdcJobGroupDO.getTitle()==null || bdcJobGroupDO.getTitle().trim().length()==0) {
            return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")) );
        }
        if (bdcJobGroupDO.getTitle().contains(">") || bdcJobGroupDO.getTitle().contains("<")) {
            return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_title")+I18nUtil.getString("system_unvalid") );
        }
        if (bdcJobGroupDO.getAddresstype()!=0) {
            if (bdcJobGroupDO.getAddresslist()==null || bdcJobGroupDO.getAddresslist().trim().length()==0) {
                return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addresstype_limit") );
            }
            if (bdcJobGroupDO.getAddresslist().contains(">") || bdcJobGroupDO.getAddresslist().contains("<")) {
                return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList")+I18nUtil.getString("system_unvalid") );
            }

            String[] addresss = bdcJobGroupDO.getAddresslist().split(",");
            for (String item: addresss) {
                if (item==null || item.trim().length()==0) {
                    return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid") );
                }
            }
        }

        // process
        bdcJobGroupDO.setUpdatetime(new Date());
        int listCount = bdcJobGroupMapper.pageListCount(0, 0, null, null);
        int ret = -1;
        if (!Objects.nonNull(bdcJobGroupDO.getId())){
            bdcJobGroupDO.setId(listCount+1);
            ret = entityMapper.insertSelective(bdcJobGroupDO);
        } else {
            Example example= new Example(BdcJobGroupDO.class,false);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id", bdcJobGroupDO.getId());
            ret = entityMapper.updateByExampleSelectiveNotNull(bdcJobGroupDO, example);
        }

        return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
    }

    /**
     * 更新执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return ReturnT
     */
    @Override
    public ReturnT<String> updateJobGroup(BdcJobGroupDO bdcJobGroupDO) {
        // valid
        if (bdcJobGroupDO.getAppname()==null || bdcJobGroupDO.getAppname().trim().length()==0) {
            return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"AppName") );
        }
        if (bdcJobGroupDO.getAppname().length()<4 || bdcJobGroupDO.getAppname().length()>64) {
            return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appname_length") );
        }
        if (bdcJobGroupDO.getTitle()==null || bdcJobGroupDO.getTitle().trim().length()==0) {
            return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")) );
        }
        if (bdcJobGroupDO.getAddresstype() == 0) {
            // 0=自动注册
            List<String> registryList = findRegistryByAppName(bdcJobGroupDO.getAppname());
            String addresslistStr = null;
            if (registryList!=null && !registryList.isEmpty()) {
                Collections.sort(registryList);
                addresslistStr = "";
                for (String item:registryList) {
                    addresslistStr += item + ",";
                }
                addresslistStr = addresslistStr.substring(0, addresslistStr.length()-1);
            }
            bdcJobGroupDO.setAddresslist(addresslistStr);
        } else {
            // 1=手动录入
            if (bdcJobGroupDO.getAddresslist()==null || bdcJobGroupDO.getAddresslist().trim().length()==0) {
                return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addresstype_limit") );
            }
            String[] addresss = bdcJobGroupDO.getAddresslist().split(",");
            for (String item: addresss) {
                if (item==null || item.trim().length()==0) {
                    return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid") );
                }
            }
        }

        // process
        bdcJobGroupDO.setUpdatetime(new Date());

        int ret = bdcJobGroupMapper.update(bdcJobGroupDO);
        return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
    }
    /**
     * 删除执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @Override
    public ReturnT<String> removeJobGroup(Integer id) {
        // valid 查看执行器任务表，查看改该执行器是否在执行任务 （不管该任务的调度状态是运行还是停止）
        int count = bdcJobInfoMapper.pageListCount(0, 10, id, -1,  null, null, null);
        if (count > 0) {
            //拒绝删除，该执行器使用中 在XxlJobInfo表中有改执行器在执行任务
            return new ReturnT<String>(500, I18nUtil.getString("jobgroup_del_limit_0") );
        }

        List<BdcJobGroupDTO> allList = bdcJobGroupMapper.findAll();
        if (allList.size() == 1) {
            //拒绝删除, 系统至少保留一个执行器
            return new ReturnT<String>(500, I18nUtil.getString("jobgroup_del_limit_1") );
        }

        int ret = bdcJobGroupMapper.remove(id);
        return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
    }

    @Override
    public ReturnT<BdcJobGroupDTO> loadJobGroupById(Integer id) {
        BdcJobGroupDTO bdcJobGroupDTO = bdcJobGroupMapper.load(id);
        return bdcJobGroupDTO !=null?new ReturnT<BdcJobGroupDTO>(bdcJobGroupDTO):new ReturnT<BdcJobGroupDTO>(ReturnT.FAIL_CODE, null);
    }

    /**
     * 存在疑问
     * 根据执行器的名字，查找已经注册的执行器IP地址, 将注册的执行器加入到xxl-job-group分组中
     * @param appnameParam
     * @return
     */
    private List<String> findRegistryByAppName(String appnameParam){
        HashMap<String, List<String>> appAddressMap = new HashMap<String, List<String>>();
        //查找所有没有死亡的注册器（注册更新时间超过60s算作死亡的执行器）
        List<BdcJobRegistryDO> list = bdcJobRegistryMapper.findAll(RegistryConfig.DEAD_TIMEOUT, new Date());
        if (list != null) {
            for (BdcJobRegistryDO item: list) {
                if (RegistryConfig.RegistType.EXECUTOR.name().equals(item.getRegistrygroup())) {
                    String appname = item.getRegistrykey();
                    List<String> registryList = appAddressMap.get(appname);
                    if (registryList == null) {
                        registryList = new ArrayList<String>();
                    }

                    if (!registryList.contains(item.getRegistryvalue())) {
                        registryList.add(item.getRegistryvalue());
                    }
                    appAddressMap.put(appname, registryList);
                }
            }
        }
        return appAddressMap.get(appnameParam);
    }
}
