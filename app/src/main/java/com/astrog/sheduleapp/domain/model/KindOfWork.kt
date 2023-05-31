package com.astrog.sheduleapp.domain.model


sealed class KindOfWork(open val type: String) {

    data class Lecture(override val type: String) : KindOfWork(type)

    data class Seminar(override val type: String) : KindOfWork(type)

    data class Lab(override val type: String) : KindOfWork(type)

    data class Practice(override val type: String) : KindOfWork(type)

    data class Exam(override val type: String) : KindOfWork(type)

    data class Consult(override val type: String) : KindOfWork(type)

    data class Unknown(override val type: String) : KindOfWork(type)
}