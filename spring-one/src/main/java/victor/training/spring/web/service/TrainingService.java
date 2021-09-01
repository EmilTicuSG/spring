package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.GenericException;
import victor.training.spring.web.controller.GenericException.ErrorCode;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TrainingService {
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private EmailSender emailSender;

    public List<TrainingDto> getAllTrainings() {
        List<TrainingDto> dtos = new ArrayList<>();
        for (Training training : trainingRepo.findAll()) {
            dtos.add(mapToDto(training));
        }
        return dtos;
    }

    public TrainingDto getTrainingById(Long id) {
        return mapToDto(trainingRepo.findById(id).get());
    }

    // TODO Test this!
    public void updateTraining(Long id, TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.name) != null &&  !trainingRepo.getByName(dto.name).getId().equals(id)) {
            throw new GenericException(ErrorCode.DUPLICATED_TRAINING);
        }
        Training training = trainingRepo.findById(id).get();
        training.setName(dto.name);
        training.setDescription(dto.description);
        // TODO implement date not in the past. i18n
        Date newDate = parseStartDate(dto);
        if (!newDate.equals(training.getStartDate())) {
            emailSender.sendScheduleChangedEmail(training.getTeacher(), training.getName(), newDate);
        }
        training.setStartDate(newDate);
        training.setTeacher(teacherRepo.getOne(dto.teacherId));
    }

    private Date parseStartDate(TrainingDto dto) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.parse(dto.startDate);
    }

    public void deleteById(Long id) {
        trainingRepo.deleteById(id);
    }

    public void createTraining(TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.name) != null) {
            throw new GenericException(ErrorCode.DUPLICATED_TRAINING);
        }
        trainingRepo.save(mapToEntity(dto));
    }



    private TrainingDto mapToDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.id = training.getId();
        dto.name = training.getName();
        dto.description = training.getDescription();
        dto.startDate = new SimpleDateFormat("dd-MM-yyyy").format(training.getStartDate());
        dto.teacherId = training.getTeacher().getId();
        dto.teacherName = training.getTeacher().getName();
        return dto ;
    }

    private Training mapToEntity(TrainingDto dto) throws ParseException {
        Training newEntity = new Training();
        newEntity.setName(dto.name);
        newEntity.setDescription(dto.description);
        newEntity.setStartDate(parseStartDate(dto));
        newEntity.setTeacher(teacherRepo.getOne(dto.teacherId));
        return newEntity;
    }

    public List<TrainingDto> search(TrainingSearchCriteria criteria) {
        log.debug("Search by " + criteria);
        return Collections.emptyList(); // TODO
    }
}

