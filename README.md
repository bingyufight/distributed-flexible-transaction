这是一个在dubbo rpc服务框架上实现的分布式事务补偿框架，该分布式事务框架没有采用传统的2pc(两阶段分布式事务提交的协议)，
因为两阶段分布式事务提交协议有比较大的阻塞性能问题。所以distributed-flexible-transaction 分布式事务框架采用的
是Best effort 1PC(一阶段最大努力提交协议) + 分布式回滚补偿的方式来实现分布式事务，这样可以大大提高性能，同时能
保证不同分布式服务之间的数据一致性。应用场景如下: 
比如现在有 分布式服务A,B,C,D,E,F。
A->B,B->C,C->D,D->E,E->F,在distributed-flexible-transaction分布式事务框架中一次分布式服务的调用链就是一个transaction,
每个单独的分布式服务是一个participant。一个transaction由多个participant组成。当D->E出现超时异常或是业务异常时,
分布式事务框架将会执行A->B,B->C,C->D 的各个participant(分布式服务调用)的回滚服务进行事务补偿。同时，在分布式事务的整个执行过程中，用WAL(Write ahead log)机制,将分
布式服务执行的commit和rollback之前保留重要执行信息。

这个分布式框架目前只是一个初级的版本，后续会继续迭代。框架中有很多地方需要改进和优化，如果有宝贵的改进和优化建议，请把建议发送到 我的邮箱 : yubingopensource@163.com
		 
<h1>distributed-flexible-transaction分布式事务基本组成模块</h1>

	distributed-transaction-api 分布式事务框架API层
	distributed-transaction-agent 分布式事务框架代理探测层	
    distributed-transaction-common 分布式事务框架通用工具类层
    distributed-transaction-core 分布式事务框架核心逻辑层
	distributed-transaction-service 分布式事务框架事务管理服务层
	distributed-transaction-test-dubboservice1 分布式事务框架测试dubbo服务1
	distributed-transaction-test-dubboservice2 分布式事务框架测试dubbo服务2

 
 
<h1> distributed-transaction-api 分布式事务框架API层核心接口</h1>

	/**
	 * 
	 * @author yubing
	 * 改注解表示 分布式事务的具体执行方法是否需要回滚，以及回滚的具体方法
	 */

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD })
	public @interface Transactionable {
		String rollbackMethod();
	}


	/**
	 * 
	 * @author yubing
	 
	 * 事务的状态
	 *
	 */
	public enum TransactionState {
		COMMIT(1),                                                                 
		ROLLBACK_SUCCESS(2),
		ROLLBACK_FAIL(3);
		
		private TransactionState(int value){
			this.value = value;
		}
		
		private final int value;
		
		public int getValue(){
			return value;
		}

	}
	
	/**
	 * 
	 * @author yubing
	 *
	 *各个参与者participant的执行状态
	 */
	public enum ParticipantState {
		PARTICIPANT_EXECUTED(1),
		PARTICIPANT_ROLLBACK_SUCCESS(4),
		PARTICIPANT_ROLLBACK_FAIL(5);
		
		private ParticipantState(int value){
			this.value = value;
		}
		
		private final int value;
		
		public int getValue(){
			return value;
		}
	}
	
	
	/**
	 * 
	 * @author yubing
	 *
	 * 各个参与者participant的commit 和 rollback调用实体
	 */
	public interface Invocation {
		
		Class<?> getTargetClassType();
		
		String 	 getMethodName();
		
		Object[] getArgumentValues();
		
		Class<?>[] getArgumentTypes();
		
		Map<String,Object> getExtraAttachMap();
		
		Object getExtraAttachInfoByKey(String key,Object defaultValue);
		
		void putExtraAttachItem(String key,Object value);
		
	}
	
	
	/**
	 * 
	 * @author yubing
	 * 分布式事务的参与者实体(participant)的核心结构
	 *
	 */
	public class Participant implements Serializable{

		private static final long serialVersionUID = -4512371127490746819L;
		
		private  String transactionUUID;
		
		private  ParticipantId participantId;
		
		private  String participantIpHost;
		
		private   int participantPort;
		
		private  String participantServiceName;
		
		private String participantMethodName;
		
		private  TransactionInvocation commitTransactionInvcoation;
		
		private  TransactionInvocation rollbackTransactionInvocation;
		
		private ParticipantState participantState;
		
		public Participant(){
			
		}
		......
	 }
	 
 
	 /**
	 * 
	 * @author yubing
	 * transaction核心载体
	 *
	 */
	public class Transaction implements Serializable {

		private static final long serialVersionUID = 6648691752838557325L;
		
		private final TransactionGlobalId transactionGlobalId;
		
		private TransactionState transactionState;
		
		private final List<Participant> participantList;
		
		private long createTime;
		
		private AtomicInteger retryRollbackCount ; 
		
		.....
	}
 
 

<h1>distributed-transaction-agent 分布式事务框架代理探测层</h1>
distributed-transaction-agent 本质上是一个dubbo的filter,利用dubbo的spi机制，可以自定义拦截dubbo服务间的调用。distributed-transaction-agent将会编译成一个jar包和各个dubbo服务一起部署，
没有任何代码侵入性。只有在你的dubbo 服务的provider的pom文件中加入如下:
     <dependency>
		  <groupId>com.base.ice</groupId>
		  <artifactId>distributed-transaction-agent</artifactId>
		  <version>${project.version}</version>
	</dependency>

	/**
	 * 
	 * @author yubing
	 * dubbo filter
	 *
	 */

	@Activate(group = { Constants.PROVIDER, Constants.CONSUMER })
	public class TransactionFilter implements Filter {

		private static Logger logger = LoggerFactory.getLogger(TransactionFilter.class);
		
		
		private TransactionableDubboServiceCenter transactionableDubboServiceCenter;
		
		private ParticipantTracer participantTracer;
		
		@Override
		public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
			
			String serviceName = invoker.getInterface().getName();
			Class serviceType = invoker.getInterface();
	//		String serviceName = invoker.getInterface().getName();
			String transactionableRollbackMethod = "";
			Participant participant = null ;
            .......
	}
	
 <h1> distributed-transaction-core 分布式事务框架核心逻辑层</h1>
 distributed-transaction-core 主要是进行transaction,participant的管理，并提供wal(Write-Ahead-Log)的具体实现机制以及定时任务回滚执行失败的事务，提供Spring管理MongoDB。
 
	 /**
	 * 
	 * @author yubing
	 *
	 */
	public interface IWriteAheadLogger<T> {

		 boolean  writeLogger(T data);
		 
		 List<T> readDataFromLogger(String transactionUUID);
		 
		 void setWriteAheadLoggerName(String loggerName);
		 
		 String getWriteAheadLoggerName();
		
	}
	
	 /**
	 * 
	 * @author yubing
	 *
	 */
	public abstract class AbstractWriteAheadLogger<T> implements IWriteAheadLogger<T> {
		
		
		protected String loggerStorePlace;
		
		protected String writeAheadLoggerName;
		
		public abstract void initConfig();
		
		public abstract void setLoggerStorePlace();

		public String getLoggerStorePlace() {
			return loggerStorePlace;
		}
		
	}
	
	
   <h1> distributed-transaction-service 分布式事务框架事务管理服务层</h1>
   
      distributed-transaction-service 负责开启一个事务，将调用链中的分布式服务注册进分布式事务等等
	 /**
	 * 
	 * @author yubing
	 *
	 */
	public interface TransactionManagerService {
		
		public Transaction beginTransaction();
		
		public void rollback(Transaction transaction);
		
		public void enrollParticipant(Transaction transaction,Participant participant);
		
		public Transaction getTransactionByTransactionUUID(String transactionUUID);

	}
	
	<h1>distributed-transaction-test-dubboservice1 分布式事务框架测试dubbo服务1</h1>
	
	/**
	 * 
	 * @author yubing
	 *
	 */
	public interface DubboWithDistributedTransactionAgentService1 {
		
		@Transactionable(rollbackMethod = "service1rollback")
		CommonResponse service1();
		
		CommonResponse service1rollback();

	}

	

