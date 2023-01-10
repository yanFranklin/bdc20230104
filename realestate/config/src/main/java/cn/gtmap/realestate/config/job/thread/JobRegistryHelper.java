package cn.gtmap.realestate.config.job.thread;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobRegistryDO;
import cn.gtmap.realestate.common.job.biz.model.RegistryParam;
import cn.gtmap.realestate.common.job.biz.model.ReturnT;
import cn.gtmap.realestate.common.job.enums.RegistryConfig;
import cn.gtmap.realestate.config.job.conf.XxlJobAdminConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * job registry instance
 * @author xuxueli 2016-10-02 19:10:24
 */
public class JobRegistryHelper {
	private static Logger logger = LoggerFactory.getLogger(JobRegistryHelper.class);

	private static JobRegistryHelper instance = new JobRegistryHelper();
	public static JobRegistryHelper getInstance(){
		return instance;
	}

	private ThreadPoolExecutor registryOrRemoveThreadPool = null;
	private Thread registryMonitorThread;
	private volatile boolean toStop = false;

	public void start(){

		// for registry or remove
		registryOrRemoveThreadPool = new ThreadPoolExecutor(
				2,
				10,
				30L,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(2000),
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, "xxl-job, admin JobRegistryMonitorHelper-registryOrRemoveThreadPool-" + r.hashCode());
					}
				},
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						r.run();
						logger.warn(">>>>>>>>>>> xxl-job, registry or remove too fast, match threadpool rejected handler(run now).");
					}
				});

		// for monitor
		registryMonitorThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!toStop) {
					try {
						// auto registry group
						List<BdcJobGroupDO> groupList = XxlJobAdminConfig.getAdminConfig().getBdcJobGroupMapper().findByAddresstype(0);
						if (groupList!=null && !groupList.isEmpty()) {

							// remove dead address (admin/executor)
							List<Integer> ids = XxlJobAdminConfig.getAdminConfig().getBdcJobRegistryMapper().findDead(RegistryConfig.DEAD_TIMEOUT, new Date());
							if (ids!=null && ids.size()>0) {
								XxlJobAdminConfig.getAdminConfig().getBdcJobRegistryMapper().removeDead(ids);
							}

							// fresh online address (admin/executor)
							HashMap<String, List<String>> appAddressMap = new HashMap<String, List<String>>();
							List<BdcJobRegistryDO> list = XxlJobAdminConfig.getAdminConfig().getBdcJobRegistryMapper().findAll(RegistryConfig.DEAD_TIMEOUT, new Date());
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

							// fresh group address
							for (BdcJobGroupDO group: groupList) {
								List<String> registryList = appAddressMap.get(group.getAppname());
								String addresslistStr = null;
								if (registryList!=null && !registryList.isEmpty()) {
									Collections.sort(registryList);
									StringBuilder addresslistSB = new StringBuilder();
									for (String item:registryList) {
										addresslistSB.append(item).append(",");
									}
									addresslistStr = addresslistSB.toString();
									addresslistStr = addresslistStr.substring(0, addresslistStr.length()-1);
								}
								group.setAddresslist(addresslistStr);
								group.setUpdatetime(new Date());

								XxlJobAdminConfig.getAdminConfig().getBdcJobGroupMapper().update(group);
							}
						}
					} catch (Exception e) {
						if (!toStop) {
							logger.error(">>>>>>>>>>> xxl-job, job registry monitor thread error:{}", e);
						}
					}
					try {
						TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
					} catch (InterruptedException e) {
						if (!toStop) {
							logger.error(">>>>>>>>>>> xxl-job, job registry monitor thread error:{}", e);
						}
					}
				}
				logger.info(">>>>>>>>>>> xxl-job, job registry monitor thread stop");
			}
		});
		registryMonitorThread.setDaemon(true);
		registryMonitorThread.setName("xxl-job, admin JobRegistryMonitorHelper-registryMonitorThread");
		registryMonitorThread.start();
	}

	public void toStop(){
		toStop = true;

		// stop registryOrRemoveThreadPool
		registryOrRemoveThreadPool.shutdownNow();

		// stop monitir (interrupt and wait)
		registryMonitorThread.interrupt();
		try {
			registryMonitorThread.join();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}


	// ---------------------- helper ----------------------

	public ReturnT<String> registry(RegistryParam registryParam) {

		// valid
		if (!StringUtils.hasText(registryParam.getRegistrygroup())
				|| !StringUtils.hasText(registryParam.getRegistrykey())
				|| !StringUtils.hasText(registryParam.getRegistryvalue())) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, "Illegal Argument.");
		}

		// async execute
		registryOrRemoveThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				int ret = XxlJobAdminConfig.getAdminConfig().getBdcJobRegistryMapper().registryUpdate(registryParam.getRegistrygroup(), registryParam.getRegistrykey(), registryParam.getRegistryvalue(), new Date());
				if (ret < 1) {
					XxlJobAdminConfig.getAdminConfig().getBdcJobRegistryMapper().registrySave(registryParam.getRegistrygroup(), registryParam.getRegistrykey(), registryParam.getRegistryvalue(), new Date());

					// fresh
					freshGroupRegistryInfo(registryParam);
				}
			}
		});

		return ReturnT.SUCCESS;
	}

	public ReturnT<String> registryRemove(RegistryParam registryParam) {

		// valid
		if (!StringUtils.hasText(registryParam.getRegistrygroup())
				|| !StringUtils.hasText(registryParam.getRegistrykey())
				|| !StringUtils.hasText(registryParam.getRegistryvalue())) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, "Illegal Argument.");
		}

		// async execute
		registryOrRemoveThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				int ret = XxlJobAdminConfig.getAdminConfig().getBdcJobRegistryMapper().registryDelete(registryParam.getRegistrygroup(), registryParam.getRegistrykey(), registryParam.getRegistryvalue());
				if (ret > 0) {
					// fresh
					freshGroupRegistryInfo(registryParam);
				}
			}
		});

		return ReturnT.SUCCESS;
	}

	private void freshGroupRegistryInfo(RegistryParam registryParam){
		// Under consideration, prevent affecting core tables
	}


}
