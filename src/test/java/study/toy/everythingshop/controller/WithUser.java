package study.toy.everythingshop.controller;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * fileName : WithUser
 * author   : pilming
 * date     : 2023-03-31
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithUserSecurityContextFactory.class)
public @interface WithUser {
    String value();
}
