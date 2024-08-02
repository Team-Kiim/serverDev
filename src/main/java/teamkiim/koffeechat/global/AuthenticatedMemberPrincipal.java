package teamkiim.koffeechat.global;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 회원 권한 확인
 * {COMPANY_EMPLOYEE, FREELANCER, STUDENT, COMPANY_EMPLOYEE_TEMP, MANAGER, ADMIN}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Auth(role = {Auth.MemberRole.COMPANY_EMPLOYEE, Auth.MemberRole.FREELANCER, Auth.MemberRole.STUDENT,
        Auth.MemberRole.COMPANY_EMPLOYEE_TEMP, Auth.MemberRole.MANAGER, Auth.MemberRole.ADMIN})
public @interface AuthenticatedMemberPrincipal {
}
