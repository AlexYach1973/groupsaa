import com.alexyach.compose.groupsaa.domain.model.DateSobriety


sealed class HomeScreenSelDateState {
    object Initial : HomeScreenSelDateState()
    object Error : HomeScreenSelDateState()
    object Loading : HomeScreenSelDateState()
    data class SelectedDate(val selDate: DateSobriety) : HomeScreenSelDateState()
}
