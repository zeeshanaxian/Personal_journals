package com.bbox.personaljournal.recyclerviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bbox.personaljournal.R
import com.bbox.personaljournal.models.AllNotes
import com.bbox.personaljournal.utils.enums.UserMood
import com.bbox.personaljournal.models.NoteEntry


class AllNotesRecyclerViewAdapter(private val AllNotesData: AllNotes?) :
    RecyclerView.Adapter<AllNotesRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_all_notes_item, parent, false)
        return MyViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = AllNotesData?.monthList!![position]
        var goodMood = 0
        var normalMood = 0
        var badMood = 0
        holder.tvMonthName.text = item.monthName
        val entities = holder.llDays.context.getString(R.string.entities)
        holder.tvEntriesNum.text = "${item.dayDateList.size} $entities"

        for (i in 0 until item.dayDateList.size) {
//            for adding both layouts
            val addUpLayout = LinearLayout(holder.itemView.context)
            addUpLayout.orientation = LinearLayout.VERTICAL
            addUpLayout.setPadding(16, 16, 16, 16)
            addUpLayout.addView(
                getDateContainer(
                    holder.llDays.context, item.dayDateList[i].NoteList, item.dayDateList[i].dayName
                )
            )

            holder.llDays.addView(addUpLayout)
            for (j in 0 until item.dayDateList[i].NoteList.size) {
                when (item.dayDateList[i].NoteList[j].mood) {
                    UserMood.GOOD -> {
                        goodMood += 1
                    }
                    UserMood.NORMAL -> {
                        normalMood += 1
                    }
                    UserMood.BAD -> {
                        badMood += 1
                    }
                }
                holder.llDays.addView(
                    getDescriptionContainer(
                        holder.llDays.context, item.dayDateList[i].NoteList[j]
                    )
                )
            }
        }
        when (maxOf(goodMood, normalMood, badMood)) {
            goodMood -> {
                holder.clMain.setBackgroundColor(
                    setMoodColor(UserMood.GOOD, holder.llDays.context)
                )
            }
            normalMood -> {
                holder.clMain.setBackgroundColor(
                    setMoodColor(UserMood.NORMAL, holder.llDays.context)
                )
            }
            badMood -> {
                holder.clMain.setBackgroundColor(
                    setMoodColor(UserMood.BAD, holder.llDays.context)
                )
            }
        }


    }

    override fun getItemCount(): Int {
        return AllNotesData?.monthList?.size ?: 0
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llDays: LinearLayout = itemView.findViewById(R.id.ll_days)
        val clMain: ConstraintLayout = itemView.findViewById(R.id.cl_month)
        val tvMonthName: TextView = itemView.findViewById(R.id.tv_month)
        val tvEntriesNum: TextView = itemView.findViewById(R.id.tv_month_entries)
    }

    private fun getDescriptionContainer(context: Context, data: NoteEntry): View {

        val timeLayout = LinearLayout(context)
        timeLayout.orientation = LinearLayout.VERTICAL
        timeLayout.setPadding(16, 0, 16, 0)
        val descriptionText = TextView(context)
        descriptionText.text = data.description
        descriptionText.setPadding(0, 10, 0, 0)
        descriptionText.setTextColor(
            ContextCompat.getColor(
                context, R.color.black
            )
        )


        val moodParams = LinearLayout.LayoutParams(
            80, 10
        )
        val moodView = View(context)
        moodParams.gravity = Gravity.END
        moodView.layoutParams = moodParams
        moodView.setBackgroundColor(
            setMoodColor(data.mood, context)
        )


        val timeText = TextView(context)
        timeText.setTypeface(null, Typeface.BOLD)
        timeText.text = data.time
        timeText.gravity = Gravity.END
        timeText.setTextColor(
            ContextCompat.getColor(
                context, R.color.black
            )
        )

        val divider = View(context)
        val dividerParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 1
        )
        divider.layoutParams = dividerParams
        dividerParams.topMargin = 50
        divider.setBackgroundColor(
            ContextCompat.getColor(
                context, R.color.black
            )
        )


        timeLayout.addView(timeText)
        timeLayout.addView(moodView)
        timeLayout.addView(descriptionText)
        timeLayout.addView(divider)
        return timeLayout
    }

    @SuppressLint("SetTextI18n")
    private fun getDateContainer(
        context: Context,
        notesData: ArrayList<NoteEntry>,
        dayName: String
    ): View {

        var goodMood = 0
        var normalMood = 0
        var badMood = 0
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        for (i in 0 until notesData.size) {
            when (notesData[i].mood) {
                UserMood.GOOD -> {
                    goodMood += 1
                }
                UserMood.NORMAL -> {
                    normalMood += 1
                }
                UserMood.BAD -> {
                    badMood += 1
                }
            }
        }
        val daysLayout = LinearLayout(context)
        layoutParams.weight = 1.0f
        val dateTExt = TextView(context)
        dateTExt.text = dayName
        dateTExt.setTextColor(
            ContextCompat.getColor(
                context, R.color.black
            )
        )
        dateTExt.layoutParams = layoutParams
        val entriesCountText = TextView(context)
        val entities = context.getString(R.string.entities)
        entriesCountText.text = "${notesData.size} $entities"
        entriesCountText.layoutParams = layoutParams
        entriesCountText.gravity = Gravity.END
        entriesCountText.setTextColor(
            ContextCompat.getColor(
                context, R.color.black
            )
        )
        daysLayout.orientation = LinearLayout.HORIZONTAL
        daysLayout.setPadding(16, 16, 16, 16)
        daysLayout.weightSum = 2f
        when (maxOf(goodMood, normalMood, badMood)) {
            goodMood -> {
                daysLayout.setBackgroundColor(
                    setMoodColor(UserMood.GOOD, context)
                )
            }
            normalMood -> {
                daysLayout.setBackgroundColor(
                    setMoodColor(UserMood.NORMAL, context)
                )
            }
            badMood -> {
                daysLayout.setBackgroundColor(
                    setMoodColor(UserMood.BAD, context)
                )
            }
        }

        daysLayout.addView(dateTExt)
        daysLayout.addView(entriesCountText)

        return daysLayout
    }

    private fun setMoodColor(mood: UserMood, context: Context): Int {
        when (mood) {
            UserMood.NORMAL -> {
                return ContextCompat.getColor(
                    context, R.color.normal
                )
            }
            UserMood.BAD -> {
                return ContextCompat.getColor(
                    context, R.color.bad
                )
            }
            UserMood.GOOD -> {
                return ContextCompat.getColor(
                    context, R.color.good
                )
            }
            else -> {
                return ContextCompat.getColor(
                    context, R.color.normal
                )
            }
        }
    }
}