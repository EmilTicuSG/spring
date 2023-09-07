package victor.training.spring.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
     (proxyBeanMethods = false) // don't do this unless in a library
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private Conversation conversation2;

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
        conversation2.start();
    }

    @Bean
    public Person john(@Value("${john.name}") String name) {
        System.out.println("NEW john : "+name);
        return new Person(name);
    }
    @Bean // 2... instances of one type, manually configured
    public Person jane() {
        return new Person("Jane");
    }
//    @Bean//I can't touch the code of Conversation.java
//    public Conversation conversation() {
//        System.out.println("One");
//        return new Conversation(john(null), jane());
//        // the only place in Spring where local method calls get intercepter
//    }
    @Bean//I can't touch the code of Conversation.java
    public Conversation conversation2(Person john, Person jane) {
        System.out.println("Two");
        return new Conversation(john, jane);
    }
    @Bean//I can't touch the code of Conversation.java
    public Conversation conversation3(Person john, Person jane) {
        System.out.println("Three");
        return new Conversation(john, jane);
    }
}

//class SpringSubclassesYourConfigs extends BeanApp {
//    @Override
//    public Person john() {
//        if (john exists in singleton map ) return ...
//        return super.john();
//    }
//}
// -- imagine you don't have acecss to the code of Conversation
@RequiredArgsConstructor
@Data
class Conversation {
    private final Person john;
    private final Person jane;

    public void start() {
        System.out.println(john.getName() + " talks with " + jane.getName());
    }
}

class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}


