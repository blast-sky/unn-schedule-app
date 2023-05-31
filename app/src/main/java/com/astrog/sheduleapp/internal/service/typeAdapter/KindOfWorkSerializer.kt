package com.astrog.sheduleapp.internal.service.typeAdapter

import com.astrog.sheduleapp.domain.model.KindOfWork
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class KindOfWorkSerializer : JsonDeserializer<KindOfWork> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): KindOfWork? = json?.let {
        stringToKindOfWork(it.asString)
    }

    companion object {
        private val kindOfWorkToPhrase = listOf(
            { s: String -> KindOfWork.Lab(s) } to listOf(
                "лаб",
                "лаборатор",
                "ЛР",
                "labor",
                "lab",
            ),
            { s: String -> KindOfWork.Seminar(s) } to listOf(
                "семинар",
                "seminar",
            ),
            { s: String -> KindOfWork.Practice(s) } to listOf(
                "практ",
                "ПЗ",
                "pract",
                "lesson",
                "practice",
                "заняти",
                "урок",
            ),
            { s: String -> KindOfWork.Exam(s) } to listOf(
                "экзамен",
                "зачет",
                "зачёт",
                "exam",
            ),
            { s: String -> KindOfWork.Consult(s) } to listOf(
                "консульт",
            ),
            { s: String -> KindOfWork.Lecture(s) } to listOf(
                "лекц",
                "Л",
                "lect",
                "мастер",
                "master"
            ),
        )

        fun stringToKindOfWork(string: String): KindOfWork {
            val lowercaseString = string.lowercase().trim()
            kindOfWorkToPhrase.forEach { (factory, phrases) ->
                phrases.forEach { phrase ->
                    if (phrase in lowercaseString) {
                        return factory.invoke(string)
                    }
                }
            }
            return KindOfWork.Unknown(string)
        }
    }
}