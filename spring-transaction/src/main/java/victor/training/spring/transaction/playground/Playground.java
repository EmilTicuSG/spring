package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final JdbcTemplate jdbc;
    private final OtherClass otherClass;

    @Transactional // THE PROXY: one connection is acquired from the CONNECTION POOL (Hikari 20 max by default)
    // because there was no tx open yet on this
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        // any jooq insert/update you send to db in a @Transactional flow, you will see the INSERT going do DB,
        // but the DB will hold it just in your transaction until later, the app send a COMMIT
        // on that same JDBC connection
        System.out.println("Count = " + jdbc.queryForObject("select count(*) from message", Long.class));
        otherClass.anotherMethod();
        if (true) throw new RuntimeException("Intentional");
    }
    // here, after the method, the PROXY sends the COMMIT to DB
    // 0 p6spy
    // 1 Cause a rollback by breaking NOT NULL, throw Runtime, throw CHECKED
    // 2 Tx propagates with your calls (in your thread😱)
    // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
    // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
    // 5 Performance: connection starvation issues : debate: avoid nested transactions
    @Transactional
    public void transactionTwo() {
    }
}
@Service
@RequiredArgsConstructor
class OtherClass {
    private final JdbcTemplate jdbc;
    @Transactional // this proxy allows the existing tx on the current thread to enter the method
    public void anotherMethod() {
        jdbc.update("insert into MESSAGE(id, message) values ( 101,'ALO' )");
        jdbc.update("insert into MESSAGE(id, message) values ( 102,'ALO' )");
    }
}