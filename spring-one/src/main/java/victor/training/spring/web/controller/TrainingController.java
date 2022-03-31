package victor.training.spring.web.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.controller.util.TrainingId;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.service.TrainingService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.text.ParseException;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

//	@GetMapping("newId")
//	public Long method() {
//		return
//	}


	@GetMapping
//	public Flux<TrainingDto> getAllTrainings() { webflux
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

//	@ApiOperation(param)
	@GetMapping(value = "{id}")
//	public Mono<TrainingDto> getTrainingById(@PathVariable TrainingId id) {
	public TrainingDto getTrainingById(@PathVariable TrainingId id) {
		return trainingService.getTrainingById(id);
	}


	@GetMapping(value = "{id}", produces = "application/pdf")
	public TrainingDto exportTrainingAsPdf(@PathVariable TrainingId id) {
		return trainingService.getTrainingById(id);
	}

	// TODO @Valid
	@PostMapping // not idempotent - should not be retries
	public/* ResponseEntity<Void>*/
	void createTraining(@Validated @RequestBody TrainingDto dto) throws ParseException {
//		try {
			trainingService.createTraining(dto);
//		} catch (ExToReportAs400 e) {
//			return ResponseEntity.status(400).build();
//		}
	}

	@PutMapping("{id}") // aka OVERWRITE = idempotent = repeating a call does no harm
//	public Mono<Void> updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
//		isCurrentUserAllowedToChangeTraining(id);
		// critical approach
		if (!id.equals(dto.id)) {
			throw new IllegalArgumentException();
		}
		// lenient approach
		dto.id = id;
		/*return (on webflux) */trainingService.updateTraining(id, dto);
	}


	// subpath

	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of thw
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")

//	@Secured("ROLE_ADMIN") //equivalent
	@PreAuthorize("hasAnyRole('ADMIN', 'POWER','LOCAL_ADMIN') and @userPermissions.editTraining(#id)")
//	@PreAuthorize("hasAuthority('training.delete')")
	public void deleteTrainingById(@PathVariable Long id) {
//		isCurrentUserAllowedToChangeTraining(id);

		trainingService.deleteById(id);
	}



	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TrainingRepo trainingRepo;

	// TODO GET or POST ?
	@PostMapping("search")// not really that REST anymore. POST should be used to create, says the dogma.
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {


	//api/training?name=stuff&teacherId=1
//	@GetMapping
//	public List<TrainingDto> search(@RequestParam(required = false) String name,
//											  @RequestParam(required = false) Long teacherId) {
//		TrainingSearchCriteria criteria = new TrainingSearchCriteria();
//		criteria.name = name;
//		criteria.teacherId = teacherId;
		return trainingService.search(criteria);
	}
}

@Component
@Aspect
class ValidateEveryMethArgOfAController {
	@Autowired
	private SmartValidator validator;

	@Around("@within(org.springframework.web.bind.annotation.RestController)")
	public Object validateArgs(ProceedingJoinPoint pjp) throws Throwable {

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		for (Object arg : pjp.getArgs()) {

			Set<ConstraintViolation<Object>> violations = validator.validate( arg );

			if(!violations.isEmpty()) {
				throw new ConstraintViolationException(violations);
			}
		}

		return pjp.proceed();

	}
}

@Component // "userPermissions" bean name
class UserPermissions {
	private final TrainingRepo trainingRepo;
	private final UserRepo userRepo;

	UserPermissions(TrainingRepo trainingRepo, UserRepo userRepo) {
		this.trainingRepo = trainingRepo;
		this.userRepo = userRepo;
	}

	public boolean editTraining(Long id) {
		Training training = trainingRepo.findById(id).get();
		// check that the current logged in user is managing the teacher of the training he is about to delete.
		User user = userRepo.getForLogin(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		return user.getManagedTeacherIds().contains(training.getTeacher().getId());
	}
}