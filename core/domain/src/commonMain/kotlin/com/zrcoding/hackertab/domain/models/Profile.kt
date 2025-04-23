package com.zrcoding.hackertab.domain.models

enum class Profile(val label: String) {
    MOBILE_ENGINEER("Mobile\nEngineer"),
    FRONTEND_ENGINEER("Frontend\nEngineer"),
    BACKEND_ENGINEER("Backend\nEngineer"),
    FULL_STACK_ENGINEER("Full Stack\nEngineer"),
    DEVOPS_ENGINEER("Devops\nEngineer"),
    DATA_ENGINEER("Data\nEngineer"),
    SECURITY_ENGINEER("Security\nEngineer"),
    ML_ENGINEER("ML\nEngineer"),
    OTHER("...\nOther")
}