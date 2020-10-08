package victor.training.spring.ms.bootservice;

import lombok.Data;

@Data // pute a JSON
public class ReservationDto {
   public Long id;
   public String name;
   public ReservationDto() {} // for Jackson
   public ReservationDto(Reservation entity) {
      id = entity.getId();
      name = entity.getName();
   }
}
