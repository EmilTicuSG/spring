package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final JdbcTemplate jdbc;
    private final EntityManager em;
    private final AnotherClass other;
    private TransactionTemplate txTemplate;

    //    @Transactional
    public void transactionOne() {
        repo.save(new Message("Ok"));
//        repo.flush(); // bad practice. Instead, go through JPA and it will auto-flush, as belo
//        repo.someNative();
//        em.createNativeQuery("INSERT INTO MESSAGE(ID,MESSAGE) VALUES (616, 'aaa')").executeUpdate();
        try {
            other.bigNastyLogic();
        } catch (Exception e) {
//            other.persistError(e);

            // txTemplate under the hood does:
            // wraps the execution of the lambda in a new transaction, created inside and commited at the end (unless exception occur)

            // get conncetion, start transaction, call your lamda, commit/rollback
            txTemplate.executeWithoutResult( s -> persistErrorLocal(e)  );

        }
        repo.save(new Message("will not throw even if the current tx is ZOMBIE (kille dby the exception thrown before)"));
        System.out.println("End of method. The inserts happen AFTER this line.");
    }

    @Autowired
    public void configureTransactionTemplate(PlatformTransactionManager transactionManager) {
        txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }
    private void persistErrorLocal(Exception e) {
        repo.save(new Message("ERROR: " + e));
    }


    @Transactional
    public void transactionTwo() {
        repo.save(new Message("Ok"));
        localMethod();
    }

    private void localMethod() {
        repo.save(new Message(null));
    }

}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void persistError(Exception e) {
        repo.save(new Message("ERROR: " + e));
    }


//    @TransactionAttribute(REQUIRES_NEW) // EJB
    @Transactional (rollbackFor = Exception.class) // fight the legacy FROM EJB since 20+ years ago.
    public void bigNastyLogic() throws IOException { //
        // Checked exceptions are NEVER to be used. are a mistake in the langue
        repo.save(new Message("Stuff1"));
        repo.save(new Message("Stuff2"));

        if (true) throw new IOException("BUM!");
    }
}