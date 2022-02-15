package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

import java.util.Set;

@RequiredArgsConstructor
@Component // bean name = permissionManager
public class PermissionManager {
   private final TrainingRepo trainingRepo;
   private final UserRepo userRepo;

//   public boolean canDeleteTraining(Long trainingId) {
//
//   }
   public boolean checkCanManageTraining(Long trainingId) {

      // imagine that this applicaiton is directly talking to a browser
            // > opportunity = you can have SESSION
         // optimization idea: enhance the data from token with any extra data you might have in your own DB about those user

      Training training = trainingRepo.findById(trainingId).get();
      Long teacherId = training.getTeacher().getId();

      String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

      User user = userRepo.getForLogin(currentUsername).get();
      Set<Long> managedTeacherIds = user.getManagedTeacherIds();

      return managedTeacherIds.contains(teacherId);
   }

}
