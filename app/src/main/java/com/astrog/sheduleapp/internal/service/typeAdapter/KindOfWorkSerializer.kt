package com.astrog.sheduleapp.internal.service.typeAdapter

import com.astrog.sheduleapp.domain.model.lesson.KindOfWork

class KindOfWorkSerializer {

    companion object {
        private val kindOfWorkToPhrase = listOf(
            KindOfWork.Lab to listOf(
                "лаб",
                "лаборатор",
                "ЛР",
                "labor",
                "lab",
            ),
            KindOfWork.Seminar to listOf(
                "семинар",
                "seminar",
            ),
            KindOfWork.Practice to listOf(
                "практ",
                "ПЗ",
                "pract",
                "lesson",
                "practice",
                "заняти",
                "урок",
            ),
            KindOfWork.Exam to listOf(
                "экзамен",
                "зачет",
                "зачёт",
                "exam",
            ),
            KindOfWork.Consult to listOf(
                "консульт",
            ),
            KindOfWork.Lecture to listOf(
                "лекц",
                "Л",
                "lect",
                "мастер",
                "master"
            ),
        )

        fun stringToKindOfWork(string: String): KindOfWork {
            val lowercaseString = string.lowercase().trim()
            kindOfWorkToPhrase.forEach { (kindOfWork, phrases) ->
                phrases.forEach { phrase ->
                    if (phrase in lowercaseString) {
                        return kindOfWork
                    }
                }
            }
            return KindOfWork.Unknown
        }
    }
}