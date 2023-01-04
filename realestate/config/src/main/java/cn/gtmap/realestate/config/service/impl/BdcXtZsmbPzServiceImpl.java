package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXtZsmbPzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityExistsException;
import cn.gtmap.realestate.common.core.qo.config.BdcXtZsmbPzQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.config.core.mapper.BdcXtZsmbPzMapper;
import cn.gtmap.realestate.config.service.BdcXtZsmbPzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/17
 * @description 业务配置系统：证书模板配置处理Service处理
 */
@Service
public class BdcXtZsmbPzServiceImpl implements BdcXtZsmbPzService {
    /**
     * ORM操作
     */
    @Autowired
    private Repo repo;

    /**
     * MyBatis ORM操作
     */
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcXtZsmbPzMapper bdcXtZsmbPzMapper;


    /**
     * @param pageable      分页对象
     * @param bdcXtZsmbPzQO 查询条件
     * @return {Page} 证书模板配置分页数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询证书模板配置数据列表
     */
    @Override
    public Page<BdcXtZsmbPzDO> listBdcXtZsmbPz(Pageable pageable, BdcXtZsmbPzQO bdcXtZsmbPzQO) {
        return repo.selectPaging("listBdcXtZsmbPzByPageOrder", bdcXtZsmbPzQO, pageable);
    }

    /**
     * @param mbsql 证书模板配置SQL
     * @return {Boolean} 正确：true，不正确：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 校验证书模板配置SQL是否正确
     */
    @Override
    public Boolean checkBdcXtZsmbPzSql(String mbsql) {
        // 避免SQL注入（理论上一定程度阻止恶意SQL，但是对查询不做限制，可能会导致撞库问题）
        if (StringUtils.isBlank(mbsql) || StringToolUtils.containsTargetStr(mbsql,
                "insert", "INSERT", "update", "UPDATE", "delete", "DELETE")) {
            return false;
        }

        try {
            String[] array = mbsql.trim().split(";");
            for (String sql : array) {
                // SQL中的动态参数默认都在条件位置，替换成常量执行
                sql = sql.replaceAll("#\\{[A-Za-z0-9_]*\\}", "'1'");
                bdcXtZsmbPzMapper.checkSql(sql);
            }
            // 执行SQL不报异常即认为SQL正确
            return true;
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }

    /**
     *
     * @param sql 证书模板配置SQL
     * @param csmc 参数名称
     * @param csz 参数值
     * @return sql注入参数后的执行结果
     */
    @Override
    public List<String> checkBdcXtZsmbPzSqlcs(String sql, String csmc,String csz) {
        List<String> res = new ArrayList<>();
        try {
            String[] array = sql.trim().split(";");
            String[] csmcstr=csmc.trim().split(",");
            String[] cszstr=csz.trim().split(",");
            for(int n=0;n<array.length;n++){
                String sqlstr=array[n];
                //用来控制是否执行sql,当sql未设置参数或有参数但参数值不存在时不执行
                int flag=0;
                if(!sqlstr.contains("#")){
                    flag=1;
                    res.add("第"+ String.valueOf(n+1)+"条sql未设置参数，为避免全表扫描，不执行sql！");
                } else  {
                    String[] sqlarr = sqlstr.split("#");
                    for (int i = 1; i < sqlarr.length; i++) {
                        String nowCsmc = sqlarr[i].substring(1, sqlarr[i].indexOf("}"));
                        if (Arrays.asList(csmcstr).contains(nowCsmc)) {
                            String nowCsz = cszstr[Arrays.asList(csmcstr).indexOf(nowCsmc)];
                            if (!Objects.equals(nowCsz, "-1")) {
                                sqlstr = sqlstr.replaceAll("#\\{" + nowCsmc + "\\}", "'" + nowCsz + "'");
                            }
                            else{
                                flag=1;
                                res.add("第"+String.valueOf(n+1)+"条sql缺失参数值！");
                                break;
                            }
                        }
                    }
                }
                try{
                    if(flag==0){
                        // 避免SQL注入（理论上一定程度阻止恶意SQL，但是对查询不做限制，可能会导致撞库问题）
                        if (StringUtils.isBlank(sqlstr) || StringToolUtils.containsTargetStr(sqlstr,
                                "insert", "INSERT", "update", "UPDATE", "delete", "DELETE")
                                || !StringToolUtils.containsTargetStr(sqlstr,"select","SELECT")) {
                            res.add("第"+String.valueOf(n+1)+"条sql填写有错误，请重新输入!" );
                            return res;
                        }else {
                            res.add(bdcXtZsmbPzMapper.checkSql(sqlstr).toString());
                        }
                    }
                }catch (Exception e){
                    res.add(e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
        return res;
    }

    /**
     * @param bdcXtZsmbPzDO 证书模板配置实体
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存证书模板配置
     */
    @Override
    public int saveBdcXtZsmbPz(BdcXtZsmbPzDO bdcXtZsmbPzDO) {
        if (null == bdcXtZsmbPzDO || null == bdcXtZsmbPzDO.getQllx()) {
            throw new NullPointerException("保存证书模板配置：请求参数‘证书模板配置或权利类型’为空！");
        }

        // 根据当前权利类型去查询对应的证书模板配置
        Example example = new Example(BdcXtZsmbPzDO.class);
        example.createCriteria().andEqualTo("qllx", bdcXtZsmbPzDO.getQllx());
        List<BdcXtZsmbPzDO> bdcXtZsmbPzDOList = entityMapper.selectByExample(example);

        // 判断数据重复
        if (CollectionUtils.isNotEmpty(bdcXtZsmbPzDOList) &&
                !StringUtils.equals(bdcXtZsmbPzDO.getZsmbid(), bdcXtZsmbPzDOList.get(0).getZsmbid())) {
            throw new EntityExistsException("配置系统-保存证书模板配置：指定权利类型证书模板配置已经存在！");
        }

        // 查询不到且提交的ID为空，说明是新增数据
        if (StringUtils.isBlank(bdcXtZsmbPzDO.getZsmbid())) {
            bdcXtZsmbPzDO.setZsmbid(UUIDGenerator.generate());
        }

        return entityMapper.saveOrUpdate(bdcXtZsmbPzDO, bdcXtZsmbPzDO.getZsmbid());
    }

    /**
     * @param bdcXtZsmbPzDOList 证书模板配置实体集合
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 删除证书模板配置（这里加事务，要么批量成功、要么批量失败）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBdcXtZsmbPz(List<BdcXtZsmbPzDO> bdcXtZsmbPzDOList) {
        if (CollectionUtils.isEmpty(bdcXtZsmbPzDOList)) {
            return 0;
        }

        int count = 0;
        for (BdcXtZsmbPzDO bdcXtZsmbPzDO : bdcXtZsmbPzDOList) {
            count += entityMapper.delete(bdcXtZsmbPzDO);
        }
        return count;
    }

    /**
     * 根据权利类型查询,主要用于导入时判断是否存在
     *
     * @param qllx
     * @return BdcXtZsmbPzDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:22 2020/8/7
     */
    @Override
    public BdcXtZsmbPzDO queryBdcXtZsmbPzByQllx(Integer qllx) {
        return bdcXtZsmbPzMapper.queryXtZsmbPzByQllx(qllx);
    }

    /**
     * 根据zsmbid查询，主要用于导出时，循环查询
     *
     * @param zsmbid
     * @return queryBdcXtZsmbPz
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:51 2020/8/7
     */
    @Override
    public BdcXtZsmbPzDO queryBdcXtZsmbPzByZsmbid(String zsmbid) {
        if (StringUtils.isBlank(zsmbid)) {
            throw new AppException("zsmbid查询参数不能为空！");
        }
        return entityMapper.selectByPrimaryKey(BdcXtZsmbPzDO.class, zsmbid);
    }
}