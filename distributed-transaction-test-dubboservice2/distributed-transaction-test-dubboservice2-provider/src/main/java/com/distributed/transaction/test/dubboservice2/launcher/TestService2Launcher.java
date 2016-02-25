package com.distributed.transaction.test.dubboservice2.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;




/**
 * 
 * @author yubing
 *
 */
public class TestService2Launcher {

	private static final Logger logger = LoggerFactory.getLogger(TestService2Launcher.class);
	public static final String SHUTDOWN_HOOK_KEY = "server.shutdown.hook";

	private static volatile boolean running = true;

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		try {
		
			logger.info("DubboWithDistributedTransactionAgentService2Launcher  init.....");
			System.setProperty("dubbo.application.logger", "slf4j");
			
			
			
			//拉起spring服务主配置文件和db-service module中的以spring-开头的mybatis配置文件:spring-dbservice-mybatis.xml
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath*:test-dubbo-service2.xml");
			
			
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
						synchronized (TestService2Launcher.class) {
							running = false;
							TestService2Launcher.class.notify();
						}
					}
				});
			}
			
			//拉起服务
			context.start();
			logger.info("DubboWithDistributedTransactionAgentService2Launcher Started!  take " + (System.currentTimeMillis() - t) + " ms");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			System.exit(1);
		}
		synchronized (TestService2Launcher.class) {
			while (running) {
				try {
					TestService2Launcher.class.wait();
				} catch (Throwable e) {
				}
			}
		}

	}

}
