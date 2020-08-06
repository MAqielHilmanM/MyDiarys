package motion.kelas.mydiarys.list_diary

import java.io.Serializable

data class ListDiaryModel(
    var id: String,
    var title : String,
    var date : String,
    var url : String,
    var story : String = ""
) : Serializable