package com.mycompany.myapp.validation;

import com.mycompany.myapp.domain.Box;
import lombok.extern.log4j.Log4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j
public class RuleValidator implements ConstraintValidator<ValidRule, String> {
   public void initialize(ValidRule constraint) {
   }

   public boolean isValid(String script, ConstraintValidatorContext context) {
       Box box = new Box(10,10,10,10);
       ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
       engine.put("box",box);
       try {
           boolean result = (boolean) engine.eval(script);
       } catch (ScriptException | ClassCastException e) {
           log.warn("Invalid Rule: " + script);
           return false;
       }
       return true;
   }
}
