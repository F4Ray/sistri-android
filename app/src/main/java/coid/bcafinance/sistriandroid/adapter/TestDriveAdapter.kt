package coid.bcafinance.sistriandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coid.bcafinance.sistriandroid.R
import coid.bcafinance.sistriandroid.model.TestDriveContent

class TestDriveAdapter(
    private var testDriveList: List<TestDriveContent>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TestDriveAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(testDriveId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_test_drive, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val testDrive = testDriveList[position]
        holder.bind(testDrive)
    }

    override fun getItemCount(): Int = testDriveList.size

    inner class ViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val consumerNameTextView: TextView = itemView.findViewById(R.id.consumerNameTextView)
        private val carNameTextView: TextView = itemView.findViewById(R.id.carNameTextView)
        private val antrianIDTextView: TextView = itemView.findViewById(R.id.antrianIDTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val testDrive = testDriveList[position]
                    onItemClickListener.onItemClick(testDrive.testDriveID)
                }
            }
        }

        fun bind(testDrive: TestDriveContent) {
            consumerNameTextView.text = testDrive.consumerName
            carNameTextView.text = testDrive.carID.tipe
            antrianIDTextView.text = testDrive.antrianID.toString()
        }
    }
}