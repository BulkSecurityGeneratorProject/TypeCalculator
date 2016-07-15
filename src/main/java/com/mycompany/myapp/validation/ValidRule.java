package com.mycompany.myapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD,
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=RuleValidator.class)
public @interface ValidRule {
    String message() default "Syntax error";

    Class<? extends Payload>[] payload() default { };

    Class<?>[] groups() default { };
}
