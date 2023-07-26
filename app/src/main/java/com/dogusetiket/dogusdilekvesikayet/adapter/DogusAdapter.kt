package com.dogusetiket.dogusdilekvesikayet.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dogusetiket.dogusdilekvesikayet.R
import com.dogusetiket.dogusdilekvesikayet.room.DilekveSikayet
import com.dogusetiket.dogusdilekvesikayet.room.DilekveSikayetDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DogusAdapter() : RecyclerView.Adapter<DogusAdapter.DogusViewHolder>() {
    private var dilekveSikayetList: ArrayList<DilekveSikayet> = ArrayList()
    private var onClickItem: ((DilekveSikayet) -> Unit)? = null
    // Room Database değer atama
    private lateinit var db: DilekveSikayetDatabase

    fun addItems(items: ArrayList<DilekveSikayet>) {
        this.dilekveSikayetList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: ((DilekveSikayet) -> Unit)) {
        this.onClickItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DogusViewHolder(

        LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_recyclerview_card, parent, false)
    )

    override fun onBindViewHolder(holder: DogusViewHolder, position: Int) {
        val dilekveSikayet = dilekveSikayetList[position]
        holder.txtGroup.text = dilekveSikayet.case_group
        holder.txtCaseName.text = dilekveSikayet.case_name
        holder.txtPersonName.text = dilekveSikayet.person_name
        holder.txtTcNo.text = dilekveSikayet.tc_no
        holder.txtCaseBody.text = dilekveSikayet.case_body
        holder.txtDate.text = dilekveSikayet.date
        holder.txtStatus.text = dilekveSikayet.isChecked


        val isVisible: Boolean = dilekveSikayet.isExpandable

        holder.expandedLayout.visibility = if (isVisible) View.VISIBLE else View.GONE

        val clickListener = View.OnClickListener {
            dilekveSikayet.isExpandable = !dilekveSikayet.isExpandable
            notifyItemChanged(position)
        }

        holder.btnAccept.setOnClickListener {
            holder.txtStatus.setTextColor(Color.GREEN)
            holder.txtStatus.setText("Kabul Edildi.")
        }

        holder.btnDecline.setOnClickListener {
            holder.txtStatus.setTextColor(Color.RED)
            holder.txtStatus.setText("Reddedildi.")
        }

        holder.downArrow.setOnClickListener(clickListener)
        holder.txtGroup.setOnClickListener(clickListener)
        holder.txtCaseName.setOnClickListener(clickListener)
        holder.txtPersonName.setOnClickListener(clickListener)
        holder.txtDate.setOnClickListener(clickListener)
        holder.txtTcNo.setOnClickListener(clickListener)

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(dilekveSikayet)
        }
    }

    override fun getItemCount(): Int {
        return dilekveSikayetList.size
    }

    class DogusViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtGroup = view.findViewById<TextView>(R.id.txtGroup)
        var txtCaseName = view.findViewById<TextView>(R.id.txtCaseName)
        var txtPersonName = view.findViewById<TextView>(R.id.txtPersonName)
        var txtTcNo = view.findViewById<TextView>(R.id.txtTcNo)
        var txtCaseBody = view.findViewById<TextView>(R.id.txtCaseBody)
        var txtDate = view.findViewById<TextView>(R.id.txtDate)
        var txtStatus = view.findViewById<TextView>(R.id.txtStatus)
        var expandedLayout = view.findViewById<ConstraintLayout>(R.id.expandedLayout)
        var downArrow = view.findViewById<ImageView>(R.id.downArrow)
        var btnAccept = view.findViewById<Button>(R.id.btnAccept)
        var btnDecline = view.findViewById<Button>(R.id.btnDecline)
        var mainLayout = view.findViewById<ConstraintLayout>(R.id.mainLayout)
    }

}