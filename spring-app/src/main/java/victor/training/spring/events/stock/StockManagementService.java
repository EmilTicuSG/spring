package victor.training.spring.events.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.events.events.OrderPlacedEvent;

@Slf4j
@Service
public class StockManagementService {
   private int stock = 4; // silly implem :D

   @Order(1)
   @EventListener
   public void onOPE(OrderPlacedEvent event) {
      process(event.getOrderId());
   }
   
   public void process(long orderId) {
      log.info("Checking stock for products in order " + orderId);
      if (stock == 0) {
         throw new IllegalStateException("Out of stock");
      }
      stock--;
      log.info(">> PERSIST new STOCK!!");
   }
}