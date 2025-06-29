package com.alexyach.compose.groupsaa.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

class HomeViewModel : ViewModel() {

    private val _difference = MutableStateFlow <List<Int>>(mutableListOf())
    val difference: StateFlow<List<Int>> = _difference

    private val _selectedDate = MutableStateFlow <String>("")
    val selectedDate: StateFlow<String> = _selectedDate


    fun dataPickerSelected(dataMill: Long?) {

        dataMill?.let {millis ->
            /* Преобразовать миллисекунды в LocalDate */
            val selectedDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            /* Формат записи выбраной даты */
            val formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale("uk", "UA"))
            _selectedDate.value = selectedDate.format(formatter)
            Log.d("Logs", "selectDateFormat: $_selectedDate")

            /* Current */
            val currentDate = LocalDate.now()

            /* Вычислить разницу */
            val period = Period.between(selectedDate, currentDate)
            /* Общее количество дней */
            val totalDays = ChronoUnit.DAYS.between(selectedDate, currentDate)

            /* Разница */
            _difference.value = listOf(period.years, period.months, period.days, totalDays.toInt())
//            val difYear = period.years
//            val difMonth = period.months
//            val difDay = period.days

//            Log.d("Logs", "Difference: $difYear year $difMonth month $difDay day; totalDays: $totalDays")



            // Извлечь год, месяц, день выбранной даты
//            val year = selectedDate.year
//            val month = selectedDate.monthValue
//            val day = selectedDate.dayOfMonth
//            Log.d("Logs", "selectedDate: $year year $month month $day day")


        }
    }

}