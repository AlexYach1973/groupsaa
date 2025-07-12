package com.alexyach.compose.groupsaa.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.compose.groupsaa.data.repository.DataStoreManager
import com.alexyach.compose.groupsaa.utils.formatPeriod
import com.alexyach.compose.groupsaa.utils.formatTotalDays
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

    private val _difference = MutableStateFlow<String>("")
    val difference: StateFlow<String> = _difference

    private val _selectedDate = MutableStateFlow<String>("")
    val selectedDate: StateFlow<String> = _selectedDate

    private val _totalDays = MutableStateFlow<String>("")
    val totalDays: StateFlow<String> = _totalDays

    /* Load From Date Store Date */
    val dataStoreYear = dataStoreManager.loadYear
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), -1)

    val dataStoreMonth = dataStoreManager.loadMonth
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), -1)

    val dataStoreDay = dataStoreManager.loadDay
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), -1)

    /* Load From Date Store Preferences Show Prayer */
    val prefMorningPrayer = dataStoreManager.prefMorningPrayer
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)
    val prefEveningPrayer = dataStoreManager.prefEveningPrayer
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)
    val prefDelegationPrayer = dataStoreManager.prefDelegationPrayer
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)
    val prefPeaceOfMindPrayer = dataStoreManager.prefPeaceOfMindPrayer
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)
    val prefResentmentPrayer = dataStoreManager.prefResentmentPrayer
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)
    val prefFearPrayer = dataStoreManager.prefFearPrayer
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)
    val prefStepTenPrayer = dataStoreManager.prefStepTenPrayer
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)


    init {
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

            /* Day Of Week */
            /*
            SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY
             */
//            Log.d("Logs","HomeViewModel day: ${selectedDate.dayOfWeek}")

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
        _totalDays.value = formatTotalDays(ChronoUnit.DAYS.between(selectedDate, currentDate).toInt())
//        _totalDays.value = ChronoUnit.DAYS.between(selectedDate, currentDate).toInt()

        /* Разница */
        _difference.value = formatPeriod(period = period)

    }


    /* *** Save Preference Show Prayer ** */
    fun savePrefMorningPrayer(value: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveMorningPrayer(value)
        }
    }

    fun savePrefEveningPrayer(value: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveEveningPrayer(value)
        }
    }
    fun savePrefDelegationPrayer(value: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveDelegationPrayer(value)
        }
    }
    fun savePrefPeaceOfMindPrayer(value: Boolean) {
        viewModelScope.launch {
            dataStoreManager.savePeaceOfMindPrayer(value)
        }
    }
    fun saveResentmentPrayer(value: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveResentmentPrayer(value)
        }
    }
    fun saveFearPrayer(value: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveFearPrayer(value)
        }
    }
    fun saveStepTenPrayer(value: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveStepTenPrayer(value)
        }
    }


}


