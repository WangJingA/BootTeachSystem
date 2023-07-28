package com.boot.teach.service.formlock;

import org.aspectj.lang.ProceedingJoinPoint;

public interface RequestKeyGenerator {
    String getLockKey(ProceedingJoinPoint proceedingJoinPoint);
}
