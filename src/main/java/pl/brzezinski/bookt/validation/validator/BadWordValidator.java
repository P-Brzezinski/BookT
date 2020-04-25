package pl.brzezinski.bookt.validation.validator;

import pl.brzezinski.bookt.validation.constraint.NotBadWord;
import pl.brzezinski.bookt.validation.validator.lang.Lang;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BadWordValidator implements ConstraintValidator<NotBadWord, String> {

    private Lang[] languages;

    @Override
    public void initialize(NotBadWord constraintAnnotation) {
        this.languages = constraintAnnotation.lang();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean valid = true;
        for (Lang language : languages) {
            if (language == Lang.PL)
                valid &= plFilter(value);
            if (language == Lang.ENG)
                valid &= engFilter(value);
        }
        return valid;
    }

    private boolean plFilter(String text){
        List<String> badWords = Arrays.asList("kurcze", "cholercia");
        return generalFilter(text, badWords);
    }


    private boolean engFilter(String text){
        List<String> badWords = Arrays.asList("fak", "bjath");
        return generalFilter(text, badWords);
    }

    private boolean generalFilter(String text, List<String> badWords){
        List<String> found = badWords
                .stream()
                .filter(word -> text
                    .toLowerCase()
                    .contains(word))
                .collect(Collectors.toList());
        return found.isEmpty();
    }

}
