package com.mycompany.myapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=RuleValidator.class)
public @interface ValidRule {
    String message() default "Syntax error";

    Class<? extends Payload>[] payload() default { };

    Class<?>[] groups() default { };
}
