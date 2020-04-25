package pl.brzezinski.bookt.validation.validator;

import pl.brzezinski.bookt.validation.constraint.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone constraint) {

    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext ctx) {
        if (phoneNumber == null){
            return false;
        }
        if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{3}")) return true;
        else if (phoneNumber.matches("\\d{2}[-\\.\\s]\\d{3}[-\\.\\s]\\d{2}[-\\.\\s]\\d{2}")) return true;
        else return false;
    }
}
