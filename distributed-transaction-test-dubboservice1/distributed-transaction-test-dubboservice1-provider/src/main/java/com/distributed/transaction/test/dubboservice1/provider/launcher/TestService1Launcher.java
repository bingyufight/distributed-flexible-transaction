package com.distributed.transaction.test.dubboservice1.provider.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.distributed.transaction.test.dubboservice1.provider.launcher.TestService1Launcher;

/**
 * 
 * @author yubing
 *
 */
public class TestService1Launcher {

	private static final Logger logger = LoggerFactory.getLogger(TestService1Launcher.class);
	public static final String SHUTDOWN_HOOK_KEY = "server.shutdown.hook";

	private static volatile boolean running = true;

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		try {
		
			logger.info("DubboWithDistributedTransactionAgentService1Launcher  init.....");
			System.setProperty("dubbo.application.logger", "slf4j");
			
			
			
			//拉起spring服务主配置文件和db-service module中的以spring-开头的mybatis配置文件:spring-dbservice-mybatis.xml
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath*:test-dubbo-service1.xml");
			
			
			logger.info("user.dir: " + System.getProperty("user.dir"));
			
			if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						try {
							context.stop();
							logger.warn("server stopped!!!");
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						} catch (Throwable t) {
							logger.error(t.getMessage());
						}
						synchronized (TestService1Launcher.class) {
							running = false;
							TestService1Launcher.class.notify();
						}
					}
				});
			}
			
			//拉起服务
			context.start();
			logger.info("DubboWithDistributedTransactionAgentService1Launcher Started!  take " + (System.currentTimeMillis() - t) + " ms");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			System.exit(1);
		}
		synchronized (TestService1Launcher.class) {
			while (running) {
				try {
					TestService1Launcher.class.wait();
				} catch (Throwable e) {
				}
			}
		}

	}

}
