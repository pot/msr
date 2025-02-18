package dev.nateweisz.msr;

import org.intellij.lang.annotations.RegExp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE) // only needed at compile time
@Target(ElementType.TYPE)
public @interface MSRHandler {
    @RegExp String predicate() default "";
}
