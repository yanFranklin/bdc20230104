package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.service.BdcBhService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.concurrent.CountDownLatch;



/**
 * @program: realestate
 * @description: 并发测试
 * @author: <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @create: 2020-08-31
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BfTest {

    @Autowired
    BdcBhService bdcBhService;

    @Autowired
    BdcBhFeignService bdcBhFeignService;


    //模拟短时间内的并发请求量

    private static final int threadNum =100;

    //倒计时器，用于模拟高并发

    private CountDownLatch cdl =new CountDownLatch(threadNum);

    @Before

    public void init(){

        //执行前准备




    }


    @Test
    @Rollback
    public void testMain(){

        for (int i =0; i< threadNum; i++) {

            new Thread(new UserRequest()).start();

            //倒计时计数一次

            cdl.countDown();

        }

        try{

            //阻塞主线程，等待所有的子线程执行完毕

            Thread.currentThread().join();

        }catch (Exception e){

            e.printStackTrace();

        }

    }

    private class UserRequest implements Runnable{

        @Override

        public void run() {

            try {

                cdl.await();

            }catch (InterruptedException e) {

                e.printStackTrace();

            }

            //此处执行对应的逻辑
            Date startTime = new Date();
            String bh =bdcBhFeignService.queryBh(Constants.BH_SL, Constants.ZZSJFW_DAY);
            long time = System.currentTimeMillis() - startTime.getTime();
            System.out.println(bh +"所耗时间:"+time);

        }

    }


}
