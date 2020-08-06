package motion.kelas.mydiarys

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_diary.view.*

fun ImageView.load(url : String){
    Picasso.get().load(url).into(this)
}