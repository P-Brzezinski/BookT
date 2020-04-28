package pl.brzezinski.bookt.validation.constraint.groupSequences;

import javax.validation.GroupSequence;

@GroupSequence({FirstValidation.class, SecondValidation.class})
public interface CompleteValidation {
}
