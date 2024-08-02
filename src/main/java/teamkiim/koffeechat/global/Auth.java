package teamkiim.koffeechat.global;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    enum MemberRole {COMPANY_EMPLOYEE, FREELANCER, STUDENT, COMPANY_EMPLOYEE_TEMP, MANAGER, ADMIN};

    MemberRole[] role();
}
