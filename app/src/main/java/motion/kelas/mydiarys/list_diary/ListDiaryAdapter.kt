package motion.kelas.mydiarys.list_diary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_diary.view.*
import motion.kelas.mydiarys.R
import motion.kelas.mydiarys.detail_diary.DetailDiaryActivity

class ListDiaryAdapter(val list: List<ListDiaryModel>) : RecyclerView.Adapter<ListDiaryAdapter.ListDiaryViewHolder>() {

    inner class ListDiaryViewHolder(itemView : View, val context: Context) : RecyclerView.ViewHolder(itemView){
        init {
            // TODO : isi constructor
        }

        fun initData(data : ListDiaryModel){
            itemView.tvItemListDiaryTitle.text = data.title
            itemView.tvItemListDiaryDate.text = data.date

            //TODO :  gambar menggunakan picasso
            Picasso.get().load(data.url).into(itemView.ivItemListDiary)

            itemView.setOnClickListener {
                // TODO : ketika item view di klik
                val intent = Intent(context,DetailDiaryActivity::class.java)
                intent.putExtra("id",data.id)
                context.startActivity(intent)
            }

            // duplicate : ctrl + d
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDiaryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_diary,parent,false)
        return ListDiaryViewHolder(v,parent.context)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListDiaryViewHolder, position: Int) {
        holder.initData(list[position])
    }
}