package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@SpringBootApplication
public class DemoApplication {
   public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
   }
}


@RestController
@RequestMapping("prefix")
class HelloController {
   private static final Logger log = LoggerFactory.getLogger(HelloController.class);
   private final MyService myService;

   HelloController(MyService myService) {
      this.myService = myService;
   }

   //OPTIONS --> CORS
   @GetMapping("hello/{name}")
   public String helloSpring(@PathVariable String name) {
      return "Hello SpringX : " + name;
   }

   @GetMapping("bye")
   public String bySpring(@RequestParam String name) {
      myService.met();
      return "Bye SpringX : " + name;
   }

   @ExceptionHandler
   @ResponseStatus
   public String handle(Exception e) {
      String id = UUID.randomUUID().toString();
      log.error("BUBA " + id, e);
      return "Shit happens. Check the logs. ref=" + id;
   }


   @PostMapping
   public MyDto createStuff(@RequestBody MyDto dto) {
      dto.id = 13L; // generat din Sequence
      dto.creationDate = LocalDateTime.now();
      return dto;
   }

}

class MyDto {
   public long id;
   public String name;
   public LocalDateTime creationDate;
}
