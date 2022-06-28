package victor.training.spring.life;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class A {
    private final C c;
}
@Service
@RequiredArgsConstructor
class B { // 1 instanta
    private final C c; // 1 camp, Spring injecteaza o singura data un C ===> se creeaz o SINGURA insntanta din C

    @PostConstruct
    public void laStartup() {
        System.out.println("La start");
        new Thread(() -> method("john")).start();
        new Thread(() -> method("jane")).start();
    }

    @Autowired
    private ApplicationContext applicationContext;

    @SneakyThrows
    public void method(String u) {
        C c = applicationContext.getBean(C.class); // de cate ori ii cer lui spring ? 2> deci spring instantiaza de 2 ori aici lasa C
        // pentru ca e marcata scope (prototype)

        System.out.println("Instanta primita: " + c);
        c.setCurrentUsername(u);
        Thread.sleep(100);
        c.method();
    }
}

// inchipuie-ti ca esti intr-o app web
@Service
@Scope("prototype") // ff periculos.
//@ApplicationScoped

// pentru app care genereaza HTML pe backend (JSP///_)

//    @Scope("request") // fiecare request HTTP primeste propria instanta

    // nu mai esti stateless : REST  tre sa fie stateless!!
    // exemplu: userul selecteaza contul. apoi inca o valoare.
    // iar in pagina urmatoare browserul trimite un alt req la BE si se asteapta ca BE sa tina minte selectia precedenta. = nu mai esti STATELESS
//    @Scope("session") // fiecare HTTP session (cookie, dupa logare browserul primeste o sesiune) _ pe BE sta asociata cu acea sesiune un obiect
class C {
    private String currentUsername;

    public C() {
        System.out.println("Cate? 1. by default orice @ pe clasa face 1 instanta din acea clasa ");
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void method() {
        System.out.println("Acu e logat userul " + currentUsername);
    }
}