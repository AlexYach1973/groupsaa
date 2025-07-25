package com.alexyach.compose.groupsaa.presentation.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.compose.groupsaa.data.repository.DataStoreManager
import com.alexyach.compose.groupsaa.data.repository.loadDailyFromAssets
import com.alexyach.compose.groupsaa.domain.model.DailyReflections
import com.alexyach.compose.groupsaa.domain.model.Prayers
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

class HomeViewModel(application: Application) : ViewModel() {

    private val dataStoreManager = DataStoreManager(application)

    private val _difference = MutableStateFlow<String>("")
    val difference: StateFlow<String> = _difference

    private val _selectedDateSobriety = MutableStateFlow<String>("")
    val selectedDateSobriety: StateFlow<String> = _selectedDateSobriety

    /* Daily*/
    private val _selectDateForDaily = MutableStateFlow(LocalDate.now())
    val selectDateForDaily: StateFlow<LocalDate> = _selectDateForDaily

    private val _dailyItem =
        MutableStateFlow<DailyReflections?>(null)
    val dailyItem: StateFlow<DailyReflections?> = _dailyItem
    /* End Daily*/


    private val _totalDays = MutableStateFlow<String>("")
    val totalDays: StateFlow<String> = _totalDays

    /* Load From Store Date */
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


    private val dailyMap: Map<String, DailyReflections> = loadDailyFromAssets(application)

    init {
        loadDailyForDate(LocalDate.now())
//        Log.d("Logs", "date: ${LocalDate.now()}")

//        dataStoreManager.saveIsVisiblePrayer(Prayers.morning_prayer, true)

    }

    fun loadDailyForDate(date: LocalDate) {
        _selectDateForDaily.value = date
        val key = date.format(DateTimeFormatter.ofPattern("MM-dd"))
        _dailyItem.value = dailyMap[key]

//        Log.d("Logs", "HomeViewmodel  _dailyItem.value: ${ _dailyItem.value?.title }")
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
        _selectedDateSobriety.value = selectedDate.format(formatter)
//        Log.d("Logs", "selectDateFormat: ${_selectedDate.value}")


        /* Current */
        val currentDate = LocalDate.now()

        /* Вычислить разницу */
        val period = Period.between(selectedDate, currentDate)
        /* Общее количество дней */
        _totalDays.value =
            formatTotalDays(ChronoUnit.DAYS.between(selectedDate, currentDate).toInt())
//        _totalDays.value = ChronoUnit.DAYS.between(selectedDate, currentDate).toInt()

        /* Разница */
        _difference.value = formatPeriod(period = period)

    }


    /* *** Save Preference Show Prayer ** */

    fun savePrefPrayer(keyMap: Prayers, value: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveIsVisiblePrayer(keyMap, value)
        }
    }

}


