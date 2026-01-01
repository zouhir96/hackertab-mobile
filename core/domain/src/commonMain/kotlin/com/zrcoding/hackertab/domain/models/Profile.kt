package com.zrcoding.hackertab.domain.models

enum class Profile(
    val label: String,
    val analyticsTag: String,
    val category: String
) {
    MOBILE_ENGINEER(
        label = "Mobile\nEngineer",
        analyticsTag = "MobileEngineer",
        category = "mobile"
    ),
    FRONTEND_ENGINEER(
        label = "Frontend\nEngineer",
        analyticsTag = "FrontendEngineer",
        category = "frontend"
    ),
    BACKEND_ENGINEER(
        label = "Backend\nEngineer",
        analyticsTag = "BackendEngineer",
        category = "backend"
    ),
    FULL_STACK_ENGINEER(
        label = "Full Stack\nEngineer",
        analyticsTag = "FullStackEngineer",
        category = "fullstack"
    ),
    DEVOPS_ENGINEER(
        label = "Devops\nEngineer",
        analyticsTag = "DevopsEngineer",
        category = "devops"
    ),
    DATA_ENGINEER(label = "Data\nEngineer", analyticsTag = "DataEngineer", category = "data"),
    SECURITY_ENGINEER(
        label = "Security\nEngineer",
        analyticsTag = "SecurityEngineer",
        category = "security"
    ),
    ML_ENGINEER(label = "ML\nEngineer", analyticsTag = "MLEngineer", category = "ai"),
    OTHER(label = "...\nOther", analyticsTag = "Other", category = "other")
}