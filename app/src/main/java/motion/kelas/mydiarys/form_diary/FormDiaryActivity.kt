package motion.kelas.mydiarys.form_diary

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_form_diary.*
import motion.kelas.mydiarys.R
import motion.kelas.mydiarys.dao.DiaryDao
import motion.kelas.mydiarys.detail_diary.DetailDiaryActivity
import motion.kelas.mydiarys.list_diary.ListDiaryActivity
import motion.kelas.mydiarys.list_diary.ListDiaryModel
import motion.kelas.mydiarys.load


class FormDiaryActivity : AppCompatActivity() {

    var isEdit = false
    lateinit var model : ListDiaryModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_diary)

        initToolbar()
        loadData()
        initListener()
    }

    private fun initListener() {
        btnDiaryNotes.setOnClickListener {
            val data = DiaryDao(
                model.id,
                Timestamp.now(),
                etCreateDiaryDescription.text.toString(),
                etCreateDiaryTitle.text.toString(),
                "https://i.ibb.co/y8XHwYS/island.png"
            )

            val db = FirebaseFirestore.getInstance()
            if (isEdit) {
                db.collection("diarys").document(model.id).set(data).addOnSuccessListener {
                    finish()
                }
            }else {
                db.collection("diarys").add(data).addOnSuccessListener {
                    finish()
                }
            }
        }
    }

    fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish() // menghancurkan aktivitas
        }
        supportActionBar?.title = ""

    }

    fun loadData() {
        isEdit = intent?.extras?.getBoolean("isEdit", false) ?: false

        if (isEdit!!) {
            toolbarTitle.text = "Edit Diary"
            llCreateDiaryCover.visibility = View.GONE
            rlCreateDiaryCover.visibility = View.VISIBLE

            model = intent.extras?.getSerializable("model") as ListDiaryModel
            etCreateDiaryTitle.setText(model.title)
            etCreateDiaryDescription.setText(model.story)
            ivCreateDiaryEdit.load(model.url)
            btnDiaryNotes.text = "Update"
        } else {
            toolbarTitle.text = "Create Diary"
            llCreateDiaryCover.visibility = View.VISIBLE
            rlCreateDiaryCover.visibility = View.GONE
            btnDiaryNotes.text = "Create"
        }
    }
}
