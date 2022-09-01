package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbc;
    private final OtherClass other;

    @Transactional
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( ?,'ALO' )", 100L);
        try {
            other.bizFlow();
        } catch (Exception e) {
            log.error("BUM");
            other.saveError(e);
        }
        log.info("End of method  +" );
        // 0 p6spy
        // 1 Cause a rollback by breaking NOT NULL, throw Runtime ROLLBACK, throw CHECKED COMMIT!!!
        // 2 Tx propagates with your calls (in your thread😱)
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    }
    @Transactional
    public void transactionTwo() {
        Message message = repo.findById(100L).orElseThrow();
        message.setMessage("Different"); // no .save() is required if you are within a @Transactional method.
        // some are scared of this!
        log.info("After in the proxy.");
    }
}
@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
//    @Transactional
    public void bizFlow() {
        //risky business flow
        repo.save(new Message("Their stuff"));
        repo.save(new Message("Their stuff"));
        throw new IllegalArgumentException("BUM");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveError(Exception e) {
        repo.save(new Message("Error: " + e.getMessage()));
    }
}