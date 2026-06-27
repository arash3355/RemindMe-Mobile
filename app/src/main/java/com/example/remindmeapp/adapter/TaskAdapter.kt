package com.example.remindmeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindmeapp.R
import com.example.remindmeapp.model.Task
import com.example.remindmeapp.utils.CategoryColor

class TaskAdapter(

    private val taskList: MutableList<Task>,

    private val onTaskChanged: (Task) -> Unit,

    private val onEdit: (Int) -> Unit

) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val card: CardView =
            itemView.findViewById(R.id.cardTask)

        val check: CheckBox =
            itemView.findViewById(R.id.checkDone)

        val title: TextView =
            itemView.findViewById(R.id.txtTitle)

        val description: TextView =
            itemView.findViewById(R.id.txtDescription)

        val date: TextView =
            itemView.findViewById(R.id.txtDate)

        val time: TextView =
            itemView.findViewById(R.id.txtTime)

        val category: TextView =
            itemView.findViewById(R.id.txtCategory)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_task,
                parent,
                false
            )

        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int =
        taskList.size

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {

        val task = taskList[position]

        holder.title.text = task.title
        holder.description.text = task.description
        holder.date.text = task.date
        holder.time.text = task.time
        holder.category.text = task.category

        holder.check.setOnCheckedChangeListener(null)
        holder.check.isChecked = task.isDone
        holder.check.isEnabled = true

        holder.card.setCardBackgroundColor(

            CategoryColor.get(task.category)

        )

        holder.check.setOnCheckedChangeListener { _, checked ->

            task.isDone = checked

            onTaskChanged(task)

        }

        holder.card.setOnClickListener {

            onEdit(position)

        }
    }
}

class CalendarTaskAdapter(

    private val taskList: MutableList<Task>

) : RecyclerView.Adapter<CalendarTaskAdapter.CalendarViewHolder>() {

    inner class CalendarViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val card: CardView =
            itemView.findViewById(R.id.cardTask)

        val check: CheckBox =
            itemView.findViewById(R.id.checkDone)

        val title: TextView =
            itemView.findViewById(R.id.txtTitle)

        val description: TextView =
            itemView.findViewById(R.id.txtDescription)

        val date: TextView =
            itemView.findViewById(R.id.txtDate)

        val time: TextView =
            itemView.findViewById(R.id.txtTime)

        val category: TextView =
            itemView.findViewById(R.id.txtCategory)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_task,
                parent,
                false
            )

        return CalendarViewHolder(view)

    }

    override fun getItemCount(): Int =
        taskList.size

    override fun onBindViewHolder(
        holder: CalendarViewHolder,
        position: Int
    ) {

        val task = taskList[position]

        holder.title.text = task.title

        holder.description.text = task.description

        holder.date.text = task.date

        holder.time.text = task.time

        holder.category.text = task.category

        holder.check.setOnCheckedChangeListener(null)

        holder.check.isChecked = task.isDone

        holder.check.isEnabled = false

        holder.card.isClickable = false

        holder.card.isFocusable = false

        holder.card.isLongClickable = false

        holder.card.setCardBackgroundColor(

            CategoryColor.get(task.category)

        )

    }

}