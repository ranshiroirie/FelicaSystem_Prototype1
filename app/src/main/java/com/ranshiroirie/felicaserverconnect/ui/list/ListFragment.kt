package com.ranshiroirie.felicaserverconnect.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ranshiroirie.felicaserverconnect.*
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDevice
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDeviceRepoFactory
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ListFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var arrayDeviceList: ArrayList<MutableMap<String,Any>>
    private lateinit var arrayAdapter: SimpleAdapter
    private lateinit var listView_device_list: ListView

    @SuppressLint("CutPasteId")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mainViewModel =
                ViewModelProvider(this,MainViewModelFactory(FelicaDeviceRepoFactory.createFelicaDeviceRepository()))
                    .get(MainViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val snackbar_loading = Snackbar.make(
            view.findViewById(R.id.layout_snackbar_loading),
            R.string.text_snackbar_loading,
            Snackbar.LENGTH_INDEFINITE
        )
        mainViewModel.progressbarFrag.observe(viewLifecycleOwner,{
            if(it == true){
                snackbar_loading.show()
            }else{
                snackbar_loading.dismiss()
            }
        })
        val snackbar_error = Snackbar.make(
            view.findViewById(R.id.layout_server_error),
            R.string.text_snackbar_server_connect_error,
            Snackbar.LENGTH_SHORT
        )
        snackbar_error.view.setBackgroundColor(MyApplication.instance.getColor(R.color.colorPinkPrimary))
        snackbar_error.setTextColor(MyApplication.instance.getColor(R.color.white))
        mainViewModel.connectErrorFrag.observe(viewLifecycleOwner,{
            if(it == true){
                snackbar_error.show()
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView_device_list = view.findViewById(R.id.listView_device_list)

        mainViewModel.getFelicaDeviceList()
        mainViewModel.felicaDeviceList.observe(viewLifecycleOwner, {
            arrayDeviceList = convertList(it)
            arrayAdapter = SimpleAdapter(
                activity,
                arrayDeviceList,
                R.layout.layout_listview_felica_device,
                arrayOf("device_name","card_id","card_pmm","card_sys","reg_date"),
                intArrayOf(
                    R.id.textView_device_name,
                    R.id.textView_card_id,
                    R.id.textView_card_pmm,
                    R.id.textView_card_sys,
                    R.id.textView_card_reg_date
                )
            )
            listView_device_list.adapter = arrayAdapter
        })

        listView_device_list.setOnItemLongClickListener { adapterView, view, i, l ->
            Log.d("selected item",i.toString())
            val getFelicaId : Int = arrayDeviceList[i]["felica_id"] as Int
            mainViewModel.deleteFelicaDevice(getFelicaId)
            mainViewModel.getFelicaDeviceList()
            arrayAdapter.notifyDataSetChanged()
            arrayAdapter.notifyDataSetInvalidated()
            return@setOnItemLongClickListener true
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertList(setDeviceList: List<FelicaDevice>):ArrayList<MutableMap<String,Any>>{
        val arrayList: ArrayList<MutableMap<String,Any>> = arrayListOf()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormat.timeZone = TimeZone.getTimeZone("Japan")
        for (itr in setDeviceList.indices){
            val conMap: MutableMap<String, Any> = HashMap()
            conMap["felica_id"] = setDeviceList[itr].felica_id
            conMap["device_name"] = setDeviceList[itr].device_name
            conMap["card_id"] = setDeviceList[itr].card_id
            conMap["card_pmm"] = setDeviceList[itr].card_pmm
            conMap["card_sys"] = setDeviceList[itr].card_sys
            conMap["reg_date"] = dateFormat.format(setDeviceList[itr].registered_datetime).toString()
            Log.d("time:", setDeviceList[itr].registered_datetime.toString())
            arrayList.add(conMap)
        }

        return arrayList
    }
}