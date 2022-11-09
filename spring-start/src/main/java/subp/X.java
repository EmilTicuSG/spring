package subp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import victor.training.spring.di.Y;
import victor.training.spring.di.Z;

//@Configuration// nu pentru clase ce contin logica de-john mea, ci doar pentru john defini @Bean sau ajusta defaulturi de prin spring

// semantica = layere
//@Service // defineste un bean in context de tip "X"
//@Repository // nu mai e necesar daca folosesti spring  Data (extinzi din JpaRepository/ Mongo.. / CrudRepository)
    // * chichitza: orice tip de exceptie JPA/Mongo/ arunci dintr-o clasa marcata cu @Repository este automat wrapuita intr-o
    //  exceptie de Spring DATA! (o ex 'standard' spring'
    // util cand lucrezi manual cu EntityManager, Session, Mongo direct driver (interact cu persit neintermediate de spring)
            // JdbcTemplate (al spring) iti arunca direct ex springului
    // = AOP (interceptie de metode) selectiv in fct de LAYER!!
@RequiredArgsConstructor
@Slf4j
@Mapper
//@Component // = o porcarie. ceva f tehnic. un fel de util.

//@Controller // se folosea in MVC, .jsp, jsf, vaadin, velocity, freemarker => genereaza HTML pe server
//@RestController // intoarce JSON -> SinglePageApp (ng, react, vue)
public class X {
    // #1 field injection = cel mai frecvent in codebaseul azi
    private Y y;

    // #2 method (setter) injection (rarely used)
    //	private Z z;
    @Autowired
    public void orice(Y y, Z z) {
        this.y =y;
        System.out.println("si z" + z);
    }



    public int prod() {
        return 1 + y.prod();
    }
}

