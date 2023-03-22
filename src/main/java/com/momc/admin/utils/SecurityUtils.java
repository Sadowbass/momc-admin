package com.momc.admin.utils;

import com.momc.admin.infra.config.security.auth.AdminCredential;
import com.momc.admin.infra.config.security.auth.AdminUserDetails;
import com.momc.admin.infra.config.security.auth.exception.NotLoginException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import java.util.List;
import java.util.Optional;

public class SecurityUtils {

    private static SessionRegistry sessionRegistry;

    private SecurityUtils() {
        throw new UnsupportedOperationException();
    }

    public static AdminCredential getAdminCredential() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AdminUserDetails) {
            AdminUserDetails adminUserDetails = (AdminUserDetails) principal;
            return adminUserDetails.getAdminInfo();
        }

        throw new NotLoginException("로그인이 되지 않았습니다.");
    }

    public static String getAdminName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication) {
            return "SYSTEM";
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof AdminUserDetails) {
            AdminUserDetails adminUserDetails = (AdminUserDetails) principal;
            return adminUserDetails.getAdminInfo().getAdminName();
        }

        throw new NotLoginException("로그인이 되지 않았습니다.");
    }

    public static void setSessionRegistry(SessionRegistry sessionRegistry) {
        SecurityUtils.sessionRegistry = sessionRegistry;
    }

    public static SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }

    public static void doLogoutIfLoggedIn(Integer id) {
        List<Object> allPrincipals = getSessionRegistry().getAllPrincipals();
        Optional<AdminUserDetails> principal = allPrincipals.stream()
                .filter(AdminUserDetails.class::isInstance)
                .map(AdminUserDetails.class::cast)
                .filter(detail -> detail.getAdminInfo().getId().equals(id))
                .findFirst();

        principal.ifPresent(SecurityUtils::expire);
    }

    private static void expire(AdminUserDetails principal) {
        List<SessionInformation> allSessions = getSessionRegistry().getAllSessions(principal, true);
        for (SessionInformation information : allSessions) {
            information.expireNow();
        }
    }
}
