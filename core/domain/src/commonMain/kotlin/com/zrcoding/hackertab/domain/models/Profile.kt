package com.zrcoding.hackertab.domain.models

enum class Profile(val label: String, val analyticsTag: String) {
    MOBILE_ENGINEER("Mobile\nEngineer", analyticsTag = "MobileEngineer"),
    FRONTEND_ENGINEER("Frontend\nEngineer", analyticsTag = "FrontendEngineer"),
    BACKEND_ENGINEER("Backend\nEngineer", analyticsTag = "BackendEngineer"),
    FULL_STACK_ENGINEER("Full Stack\nEngineer", analyticsTag = "Full StackEngineer"),
    DEVOPS_ENGINEER("Devops\nEngineer", analyticsTag = "DevopsEngineer"),
    DATA_ENGINEER("Data\nEngineer", analyticsTag = "DataEngineer"),
    SECURITY_ENGINEER("Security\nEngineer", analyticsTag = "SecurityEngineer"),
    ML_ENGINEER("ML\nEngineer", analyticsTag = "MLEngineer"),
    OTHER("...\nOther",  analyticsTag = "Other")
}