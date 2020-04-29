package victor.training.spring.events.order;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@EnableBinding({Queues.class})
@SpringBootApplication
public class PlaceOrderApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PlaceOrderApp.class, args);
    }

//	@Bean
//    public ApplicationEventMulticaster applicationEventMulticaster() {
//        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
//        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//        return eventMulticaster;
//    }


    // TODO [1] Decouple using @EventListener and ApplicationEventPublisher
    // TODO [2] also generate invoice
    // TODO [2] control the @Order
    // TODO [3] event chaining
    // TODO [4] handle asynchronously: exceptions, Tx, Thread Scope, debate
    // TODO [5] queues: retries, chaining, topics
    // TODO [opt] Transaction-scoped events


    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    public void run(String... args) {
        placeOrder();
    }

    private void placeOrder() {
        log.debug(">> PERSIST new Order");
        long orderId = 13L;
        eventPublisher.publishEvent(new OrderPlacedEvent(orderId));
        invoiceService.sendInvoice(orderId);
    }
}

@Value
class OrderPlacedEvent {
    long orderId;
}

@Slf4j
@Service
class StockManagementService {
    private int stock = 3; // silly implem :D

	@EventListener
    public void process(OrderPlacedEvent event) {
        log.info("Checking stock for products in order " + event.getOrderId());
        if (stock == 0) {
            throw new IllegalStateException("Out of stock");
        }
        stock--;
        log.info(">> PERSIST new STOCK!!");
    }
}

@Slf4j
@Service
class InvoiceService {
    public void sendInvoice(long orderId) {
        log.info("Generating invoice for order " + orderId);
        // TODO what if (random() < .3) throw new RuntimeException("Invoice Generation Failed");
        log.info(">> PERSIST Invoice!!");
    }
}