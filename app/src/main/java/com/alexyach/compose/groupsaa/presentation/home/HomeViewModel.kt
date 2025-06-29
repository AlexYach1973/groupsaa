package com.alexyach.compose.groupsaa.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.compose.groupsaa.data.repository.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

class HomeViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _difference = MutableStateFlow<List<Int>>(mutableListOf())
    val difference: StateFlow<List<Int>> = _difference

    private val _selectedDate = MutableStateFlow<String>("")
    val selectedDate: StateFlow<String> = _selectedDate

    private val _totalDays = MutableStateFlow<Int>(0)
    val totalDays: StateFlow<Int> = _totalDays

    /* Load From Date Store */
    val dataStoreYear = dataStoreManager.loadYear
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), -1)

    val dataStoreMonth = dataStoreManager.loadMonth
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), -1)

    val dataStoreDay = dataStoreManager.loadDay
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), -1)

    val testDay = MutableStateFlow<Int>(0)
    val testYear = MutableStateFlow<Int>(0)

    init {
        loadTest()
    }

    /* TEST */
    private fun loadTest() {
        viewModelScope.launch {
            dataStoreManager.loadDay.collect { day ->
                Log.d("Logs", "init $day")
                testDay.value = day
            }
        }
        viewModelScope.launch {
            dataStoreManager.loadYear.collect { year ->
                Log.d("Logs", "init $year")
                testYear.value = year
            }
        }

    }

    fun dataPickerSelected(dataMill: Long?) {

        dataMill?.let { millis ->
            /* Преобразовать миллисекунды в LocalDate */
            val selectedDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            /* Извлечь год, месяц, день выбранной даты */
//            val year = selectedDate.year
//            val month = selectedDate.monthValue
//            val day = selectedDate.dayOfMonth

            /* Save to DataStore*/
            saveToDataStore(
                listOf(
                    selectedDate.year,
                    selectedDate.monthValue,
                    selectedDate.dayOfMonth
                )
            )
        }
    }

    private fun saveToDataStore(date: List<Int>) {
        viewModelScope.launch {
            dataStoreManager.saveSelectedData(date)
        }
    }


    fun formatingDate(values: List<Int>) {

        val selectedDate = LocalDate.of(values[0], values[1], values[2])

        /* Формат записи выбраной даты */
        val formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale("uk", "UA"))
        _selectedDate.value = selectedDate.format(formatter)
//        Log.d("Logs", "selectDateFormat: ${_selectedDate.value}")


        /* Current */
        val currentDate = LocalDate.now()

        /* Вычислить разницу */
        val period = Period.between(selectedDate, currentDate)
        /* Общее количество дней */
        _totalDays.value = ChronoUnit.DAYS.between(selectedDate, currentDate).toInt()

        /* Разница */
        _difference.value = listOf(period.years, period.months, period.days)


    }


}


