package victor.training.spring.web.controller.dto;

import victor.training.spring.web.domain.Training;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TrainingDto {
	public Long id;
	@Size(min = 2)
	@NotNull
	public String name;
	public Long teacherId;
	public String teacherName;
	public String startDate;
	public String description;
}
