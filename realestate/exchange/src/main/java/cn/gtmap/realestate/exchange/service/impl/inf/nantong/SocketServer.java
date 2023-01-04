package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Order(value = 1)
public class SocketServer implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);

    @Value("${data.version:}")
    private String dataversion;

    @Value("${fs.socket.serverPort:}")
    private Integer socketServerPort;
    @Value("${fs.socket.pool-keep:20}")
    private Integer poolKeep;
    @Value("${fs.socket.pool-core:10}")
    private Integer poolCore;
    @Value("${fs.socket.pool-max:30}")
    private Integer poolMax;
    @Value("${fs.socket.pool-queue-init:10}")
    private Integer poolInit;


    @Override
    public void run(String... strings) throws Exception {
        if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_NT, dataversion)) {
            LOGGER.info("如皋开始启动socket服务！");
            // 创建服务端socket
            if (null != socketServerPort) {
                ServerSocket serverSocket = new ServerSocket(socketServerPort);

                Socket socket = null;
                LOGGER.info("启动socket服务！端口:{}", socketServerPort);
                ThreadPoolExecutor pool = new ThreadPoolExecutor(
                        poolCore,
                        poolMax,
                        poolKeep,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(poolInit),
                        new ThreadPoolExecutor.DiscardOldestPolicy()
                );
                while (true) {
                    socket = serverSocket.accept();
                    pool.execute(new ServerConfig(socket));
                }
            }

        } else {
            LOGGER.info("启动个寂寞！");

        }
    }
}
