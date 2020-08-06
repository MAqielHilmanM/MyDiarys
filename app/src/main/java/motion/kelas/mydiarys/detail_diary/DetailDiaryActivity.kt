package motion.kelas.mydiarys.detail_diary

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_diary.*
import motion.kelas.mydiarys.R
import motion.kelas.mydiarys.dao.DiaryDao
import motion.kelas.mydiarys.form_diary.FormDiaryActivity
import motion.kelas.mydiarys.list_diary.ListDiaryModel
import motion.kelas.mydiarys.load


class DetailDiaryActivity : AppCompatActivity() {

    lateinit var model: ListDiaryModel
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_diary)

        initToolbar()
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuEdit -> {
                val intent = Intent(this, FormDiaryActivity::class.java)
                intent.putExtra("isEdit", true)
                intent.putExtra("model", model)
                startActivity(intent)
                return true
            }
            R.id.menuDelete -> {
                val builder = AlertDialog.Builder(this@DetailDiaryActivity)

                builder.setTitle("Warning")
                builder.setMessage("Are you sure to delete this diary?")

                builder.setPositiveButton("OK") { dialog, which ->
                    val db = FirebaseFirestore.getInstance()
                    db.collection("diarys").document(id).delete().addOnSuccessListener {
                        finish()
                    }
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()

                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
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
        id = intent?.extras?.getString("id") ?: ""

        if (id.isNotEmpty()) {
            val db = FirebaseFirestore.getInstance()
            db.collection("diarys").document(id).get().addOnSuccessListener {
                val data = it.toObject(DiaryDao::class.java)

                model = ListDiaryModel(
                    data?.id ?: "",
                    data?.title ?: "",
                    data?.date?.toDate().toString(),
                    data?.url ?: "",
                    data?.story ?: ""
                )

                val title = model.title
                val date = model.date
                val url = model.url
                val story = model.story


                tvDetailDiaryDate.text = date
                tvDetailDiaryTitle.text = title
                tvDetailDiaryDescription.text = story
                ivDetailDiary.load(url!!)
            }
        }
    }
}
