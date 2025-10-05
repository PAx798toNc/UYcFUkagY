// 代码生成时间: 2025-10-06 02:01:18
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.Callable;

// 交易执行引擎组件
@Component
public class TransactionExecutor {

    private final PlatformTransactionManager transactionManager;

    // 自动注入事务管理器
    @Autowired
    public TransactionExecutor(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    // 执行事务的方法，支持返回值
    @Transactional
    public <T> T executeTransaction(Callable<T> transactionCallable) throws Exception {
        return new TransactionTemplate(transactionManager).execute(transactionStatus -> {
            try {
                // 执行实际的业务逻辑并返回结果
                return transactionCallable.call();
            } catch (Exception e) {
                // 记录异常信息
                transactionStatus.setRollbackOnly();
                throw e;
            }
        });
    }

    // 执行事务的方法，不返回任何值
    @Transactional
    public void executeTransaction(Runnable transactionRunnable) throws Exception {
        executeTransaction(() -> {
            transactionRunnable.run();
            return null;
        });
    }
}
