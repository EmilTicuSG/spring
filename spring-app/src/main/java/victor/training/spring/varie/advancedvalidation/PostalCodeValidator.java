package victor.training.spring.varie.advancedvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostalCodeValidator implements ConstraintValidator<ValidPostalCode,String> {
    @Override
    public void initialize(ValidPostalCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("Validating " + value);
        if (value == null || value.isEmpty() || value.isBlank()) {
            return false;
        }
        return value.matches("\\d+");
    }
}
