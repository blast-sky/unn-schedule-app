package com.astrog.sheduleapp.domain.model.lesson

import org.junit.Assert.assertEquals
import org.junit.Test

internal class KindOfWorkTest {

    @Test
    fun `valueOf Must return correct kind`() {
        val lecture = KindOfWork.Lecture
        assertEquals(lecture, KindOfWork.valueOf(lecture.name))
    }
}