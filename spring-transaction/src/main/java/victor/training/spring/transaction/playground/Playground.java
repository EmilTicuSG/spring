package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  // tx management low level:
//    DataSource ds;
//    Connection connection = ds.getConnection();
//    connection.setAutoCommit(false); // open a TX
//    // INSERT ,,, your code
//    connection.commit();

//  @Transactional //
  // acquire a JDBC Connection from the connection pool (size=10) Hikari
  // bind the connection to the CURRENT THREAD until the end of the method
  // creating a PersistenceContext on the THREAD
  public void transactionOne() {
    var dataFromRemote = "data retrieved via an API call";
    // restTemplate.getForObject / webClient.block() => JDBC Connection Pool Starvation

//    Playground myself = (Playground) AopContext.currentProxy(); // #2 also this!! OMG

    other.atomicPart(dataFromRemote);

//    transactionTemplate.executeWithoutResult(status -> { // #4 programatic no aspects
//      repo.save(new Message("JPA with " + dataFromRemote));
//      repo.save(new Message(null));
//    });
  }

  private TransactionTemplate transactionTemplate;
  @Autowired
  public void initTxTemplate(PlatformTransactionManager transactionManager) {
    transactionTemplate = new TransactionTemplate(transactionManager);
    transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    transactionTemplate.setTimeout(1);
  }
  @Autowired
//  @Lazy
//  private Playground myself; // proxy injected here ! ME #3
  private OtherClass other; // #1 move method to other class and inject it proxy injected here !
    // breaking complexity apart is generally GOOD

  @Transactional
  public void atomicPart(String dataFromRemote) {
    repo.save(new Message("JPA with " + dataFromRemote));
    repo.save(new Message(null));
  }

  public void transactionTwo() {}
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  @Transactional
  public void atomicPart(String dataFromRemote) {
    repo.save(new Message("JPA with " + dataFromRemote));
    repo.save(new Message(null));
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
