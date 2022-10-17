package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbc;
    private final OtherClass other;

//    @Transactional // face ca tot sau nimic ce e in metoda se va persista = ATOMIC
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        repo.save(new Message("jpa")); // nici un insert in log.
        System.out.println("saveul de mai sus NU AJUNGE IN DB decat DUPA FINALUL metodei");


        f();
        // 0 p6spy
        // 1 Cause a rollback by breaking NOT NULL, throw Runtime, throw CHECKED
        // 2 Tx propagates with your calls (in your thread😱)
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    }

    @Autowired
    private PlatformTransactionManager transactionManager;

    private void f() {
        System.out.println(" E activa? " + TransactionSynchronizationManager.isActualTransactionActive());
    }

    //    @Transactional


    @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
}