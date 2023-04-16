package com.astrog.sheduleapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

@RequiresApi(Build.VERSION_CODES.O)
val dateFormatterWithDayOfWeek: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE dd.MM")