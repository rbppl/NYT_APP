// CategoryAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rbppl.nyt_app.Category
import com.rbppl.nyt_app.R

class CategoryAdapter(private val bookLists: List<Category>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listNameTextView: TextView = itemView.findViewById(R.id.listNameTextView)
        val newestPublishedDateTextView: TextView = itemView.findViewById(R.id.newestPublishedDateTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }
    fun getItem(position: Int): Category {
        return bookLists[position]
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookList = bookLists[position]
        holder.listNameTextView.text = bookList.list_name
        holder.newestPublishedDateTextView.text = bookList.newest_published_date
    }

    override fun getItemCount(): Int {
        return bookLists.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
