package motion.kelas.mydiarys.list_diary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_form_diary.toolbar
import kotlinx.android.synthetic.main.activity_list_diary.*
import motion.kelas.mydiarys.R
import motion.kelas.mydiarys.dao.DiaryDao
import motion.kelas.mydiarys.form_diary.FormDiaryActivity


class ListDiaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_diary)

        initToolbar()
        initListener()
        initRecycler()
        loadData()
    }

    fun initToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
    }

    fun initListener(){
        fab.setOnClickListener {
            val intent = Intent(this@ListDiaryActivity, FormDiaryActivity::class.java)
            intent.putExtra("isEdit", false)
            startActivity(intent)
        }
    }


    val lists = arrayListOf<ListDiaryModel>()
    fun initRecycler(){
        rvListDiary.adapter = ListDiaryAdapter(lists)
        rvListDiary.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
    }

    fun loadData(){
        val db = FirebaseFirestore.getInstance()
        db.collection("diarys").addSnapshotListener { snapshot, error ->
            if(snapshot == null){
                return@addSnapshotListener
            }

            if(snapshot != null && !snapshot.isEmpty){
                lists.clear()
                for(document in snapshot.documents){
                    val data = document.toObject(DiaryDao::class.java)
                    lists.add(
                        ListDiaryModel(
                            document.id,
                            data?.title?:"",
                            data?.date?.toDate().toString(),
                            data?.url?:"",
                            data?.story?:""
                        )
                    )
                }
                rvListDiary.adapter?.notifyDataSetChanged()
            }
        }
    }
}
