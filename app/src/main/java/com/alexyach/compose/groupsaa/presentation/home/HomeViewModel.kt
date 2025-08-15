package com.alexyach.compose.groupsaa.presentation.home

import HomeScreenSelDateState
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.compose.groupsaa.data.repository.DataStoreManager
import com.alexyach.compose.groupsaa.data.repository.loadDailyFromAssets
import com.alexyach.compose.groupsaa.domain.model.DailyReflections
import com.alexyach.compose.groupsaa.domain.model.DateSobriety
import com.alexyach.compose.groupsaa.domain.model.Prayer
import com.alexyach.compose.groupsaa.domain.model.PrayersEnum
import com.alexyach.compose.groupsaa.domain.model.getPrayersList
import com.alexyach.compose.groupsaa.utils.formatPeriod
import com.alexyach.compose.groupsaa.utils.formatTotalDays
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

//    private val dataStoreManager = DataStoreManager(application)

    /* Daily*/
    private val _selectDateForDaily = MutableStateFlow(LocalDate.now())
    val selectDateForDaily: StateFlow<LocalDate> = _selectDateForDaily

    private val _dailyItem = MutableStateFlow<DailyReflections?>(null)
    val dailyItem: StateFlow<DailyReflections?> = _dailyItem
    private val dailyMap: Map<String, DailyReflections> = loadDailyFromAssets(context)

    /* End Daily*/


    val _selDateScreenState: MutableStateFlow<HomeScreenSelDateState> = MutableStateFlow(HomeScreenSelDateState.Initial)
    val selDateScreenState : StateFlow<HomeScreenSelDateState> = _selDateScreenState

    private val prefVisiblePrayerList : List<PrayersEnum> = enumValues<PrayersEnum>().toList()
    private val _prayersList = MutableStateFlow(getPrayersList())
    val prayersList: StateFlow<List<Prayer>> = _prayersList


    init {
        loadDailyForDate(LocalDate.now())
        loadPrefPrayerList()
        loadDateFromDataStore()
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
            saveDateToDataStore(
                listOf(
                    selectedDate.year,
                    selectedDate.monthValue,
                    selectedDate.dayOfMonth
                )
            )
        }
    }

    private fun saveDateToDataStore(date: List<Int>) {
        viewModelScope.launch {

            dataStoreManager.saveSelectedDataList(date)
            loadDateFromDataStore()
        }
    }

    private fun loadDateFromDataStore() {
        _selDateScreenState.value = HomeScreenSelDateState.Loading

        viewModelScope.launch {
            val dateLit = dataStoreManager.readSelectedDataList()

            if (dateLit.isNotEmpty()) {
                formatingDate(dateLit)
            } else {
                _selDateScreenState.value = HomeScreenSelDateState.Error

            }
        }
    }

    private fun formatingDate(values: List<Int>) {

        val selectedDate = LocalDate.of(values[0], values[1], values[2])

        /* Формат записи выбраной даты */
        val formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale("uk", "UA"))
        val dateSobriety = selectedDate.format(formatter)

        /* Current */
        val currentDate = LocalDate.now()

        /* Вычислить разницу */
        val period = Period.between(selectedDate, currentDate)
        /* Общее количество дней */
        val totalDay=
            formatTotalDays(ChronoUnit.DAYS.between(selectedDate, currentDate).toInt())

        /* Разница */
        val difference = formatPeriod(period = period)

        _selDateScreenState.value = HomeScreenSelDateState.SelectedDate(
            DateSobriety(
                selectedDateSobriety = dateSobriety,
                totalDays = totalDay,
                difference = difference
            )
        )

    }

    /* *** Preference Show Prayer Data Store ** */

    fun savePrefPrayerList(prayersEnum: PrayersEnum, value: Boolean) {
        val newList = prefVisiblePrayerList.apply {
            prayersEnum.isVisible = value
        }

//        Log.d("Logs", "HomeViewModel, savePrefPrayerList, newList: ${newList.map { it.isVisible }}")

        viewModelScope.launch {
            dataStoreManager.savePrefVisiblePrayerList(
                newList.map {
                    it.isVisible
                }
            )
        }
    }

    fun loadPrefPrayerList() {

        viewModelScope.launch {
            val listBooleanPref = dataStoreManager.readPrefVisiblePrayerList()

            if (listBooleanPref.isNotEmpty()) {
                for (i in 0 until listBooleanPref.size) {
                    prefVisiblePrayerList[i].isVisible = listBooleanPref[i]
                }
            }

            fillPrayersPrefVisible(prefVisiblePrayerList)
        }


    }

    private fun fillPrayersPrefVisible(prefVisiblePrayerList: List<PrayersEnum>){
        _prayersList.value.forEach {prayer ->

            val index : Int = prefVisiblePrayerList.indexOf(prayer.name)
            prayer.isVisible = prefVisiblePrayerList[index].isVisible
        }
    }




}


