package victor.training.spring.transactions;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private String message;

    protected Message() {
    }

    public Message(String message) {
        this.message = message;
    }

}
