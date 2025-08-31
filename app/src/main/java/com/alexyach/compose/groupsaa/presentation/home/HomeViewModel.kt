package com.alexyach.compose.groupsaa.presentation.home

import HomeScreenSelDateState
import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.compose.groupsaa.data.repository.AssetsManager
import com.alexyach.compose.groupsaa.data.repository.DataStoreManager
import com.alexyach.compose.groupsaa.data.repository.UpdateManager
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
    private val assetsManager: AssetsManager,
    private val updateManager: UpdateManager,
    @ApplicationContext private val context: Context
) : ViewModel() {


    /* Daily*/
    private val _selectDateForDaily = MutableStateFlow(LocalDate.now())
    val selectDateForDaily: StateFlow<LocalDate> = _selectDateForDaily

    private val _dailyItem = MutableStateFlow<DailyReflections?>(null)
    val dailyItem: StateFlow<DailyReflections?> = _dailyItem
    private val dailyMap: Map<String, DailyReflections> = assetsManager.loadDailyFromAssets()
    /* End Daily*/

    val _selDateScreenState: MutableStateFlow<HomeScreenSelDateState> = MutableStateFlow(HomeScreenSelDateState.Initial)
    val selDateScreenState : StateFlow<HomeScreenSelDateState> = _selDateScreenState

    private val prefVisiblePrayerList : List<PrayersEnum> = enumValues<PrayersEnum>().toList()
    private val _prayersList = MutableStateFlow(getPrayersList())
    val prayersList: StateFlow<List<Prayer>> = _prayersList

    /* TTS */
    private val _isUkrVoice: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUkrVoice: StateFlow<Boolean> = _isUkrVoice
    private var tts: MutableStateFlow<TextToSpeech?> = MutableStateFlow(null)

    /* Update Status  */
    val progress = updateManager.progress
    val status = updateManager.status

    fun checkForUpdate() {
        updateManager.checkAndUpdate(owner = "AlexYach1973", repo = "groupsaa")
    }




    init {
        loadDailyForDate(LocalDate.now())
        loadPrefPrayerList()
        loadDateFromDataStore()
        textToSpeechTest()

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
        val formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.forLanguageTag("uk-UA"))
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


    /* *** **************** TTS **************** *** */
    private fun textToSpeechTest() {
        var textToSpeech: TextToSpeech?
        val locale = Locale.forLanguageTag("uk-UA")

        textToSpeech = TextToSpeech(context){ status ->

            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = locale

                /* Is Ukrainian Voice */
                tts.let {
                    val voices = tts.value?.voices
                    val ukrainianVoices =
                        voices?.filter { it.locale.language == "uk" } ?: emptyList()

                    _isUkrVoice.value = ukrainianVoices.isNotEmpty()

//                    Log.d("Logs", "HomeVM, ukrainianVoices: $ukrainianVoices")
                }
            }
        }

        tts.value = textToSpeech

    }

    fun speechUkrainianText(text: String) {
//        Log.d("Logs", "HomeVM, speechUkrainianText(text)")
        tts.value?.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }

    fun isSpeaking(): Boolean {
//        Log.d("Logs", "HomeVM, isSpeaking(): ${tts.value?.isSpeaking}")
        return tts.value?.isSpeaking ?: true
    }

    fun stopUkrainianText() {
        tts.value?.stop()
//        Log.d("Logs", "HomeVM, stopUkrainianText()")
    }
    /* END **************** TTS **************** *** */


    /* **************** Update Latest Release **************** */
   /* fun triggerUpdate() {
        updateManager.downloadApk("https://github.com/AlexYach1973/repo/groupsaa/latest/download/update.apk")
       *//* updateManager.checkAndUpdate(
            owner = "AlexYach1973",
            repo = "groupsaa"
        )*//*
    }*/





//    fun startProgressTracking(downloadId: Long) {
//        updateManager.observeDownloadProgress(downloadId) { percent ->
//            _progress.value = percent
//        }
//    }

    /* fun checkForUpdate() {
         Log.d("Logs", "HomeViewModel checking update start")

         val currentVersionName = context.packageManager.
         getPackageInfo(context.packageName, 0).versionName

         viewModelScope.launch {
             try {
                 val release = checkUpdateUseCase("AlexYach1973", "groupsaa")

                 if (release?.tagName != currentVersionName) {
                     _updateAvailable.value = release?.assets?.firstOrNull()?.downloadUrl
                 }

 //                val asset = release?.assets?.find { it.name.endsWith(".apk") }

             } catch (e: Exception) {
                 Log.e("Logs", "HomeViewModel Error checking update", e)
             }
         }
     }

     fun downloadAndInstallApk(url: String) {
         Log.d("Logs", "HomeViewModel downloadAndInstallApk start")

         val request = DownloadManager.Request(url.toUri()).apply {
             setTitle("App Update")
             setDescription("Downloading new version")
             setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "app-release.apk")
             setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
         }

         val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
         val downloadId = downloadManager.enqueue(request)

         *//* Отслеживание завершения загрузки *//*
        viewModelScope.launch {
            val query = DownloadManager.Query().setFilterById(downloadId)
            while (true) {
                val cursor = downloadManager.query(query)

                if (cursor.moveToFirst()) {
                    val status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))

                    if(status == DownloadManager.STATUS_SUCCESSFUL) {
                        installApk()
                        break
                    } else if(status == DownloadManager.STATUS_FAILED) {
                        Log.e("UpdateViewModel", "Download failed")
                        break
                    }
                }
                cursor.close()
                delay(1000) // Проверка каждую секунду
            }
        }

        Log.d("Logs", "HomeViewModel downloadAndInstallApk END")
    }

    private fun installApk() {
        Log.d("Logs", "HomeViewModel installApk start")

        val apkFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "app-release.apk"
        )

        val apkUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            apkFile
        )

        val packageInstaller = context.packageManager.packageInstaller
        val canInstall = context.packageManager.canRequestPackageInstalls()

        if (canInstall) {
            *//* Запуск установки *//*
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkUri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(intent)
        } else {
            *//* Запрос разрешения *//*
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                data = "package:${context.packageName}".toUri()
            }
            context.startActivity(intent)
        }


        Log.d("Logs", "HomeViewModel installApk END")
    }*/


    /* OLD Code */

    /* END **************** Update Latest Release **************** */

    override fun onCleared() {
        super.onCleared()
        tts.value?.shutdown()
    }


}


