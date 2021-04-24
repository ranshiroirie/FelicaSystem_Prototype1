package com.ranshiroirie.felicaserverconnect.ui.reader.child

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ranshiroirie.felicaserverconnect.R
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDeviceRepoFactory
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDeviceRepository
import com.ranshiroirie.felicaserverconnect.felica.reader.FelicaReader
import com.ranshiroirie.felicaserverconnect.felica.reader.FelicaReaderInterface
import com.ranshiroirie.felicaserverconnect.felica.reader.ReadDevice
import com.ranshiroirie.felicaserverconnect.ui.reader.ReaderViewModel
import com.ranshiroirie.felicaserverconnect.ui.reader.ReaderViewModelFactory
import java.util.ArrayList

class ReaderMainFragment : Fragment() {

    private val viewModel : ReaderViewModel by viewModels(
        {requireParentFragment()},
        {ReaderViewModelFactory(FelicaDeviceRepoFactory.createFelicaDeviceRepository()
    )})
    lateinit var felica : FelicaReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        felica = FelicaReader(requireContext(), requireActivity())
        felica.setListener(felicaListener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reader_main, container, false)
    }

    override fun onResume() {
        super.onResume()
        felica.start()
        Log.d("FelicaReader", "Service Start")
    }

    override fun onPause() {
        super.onPause()
        felica.stop()
        Log.d("FelicaReader", "Service Stop")
    }

    private val felicaListener = object : FelicaReaderInterface {
        override fun onReadFelica(data: ArrayList<String>) {
            val getFelicaReadDevice = ReadDevice(
                card_id = data[0],
                card_sys = data[1],
                card_pmm = data[2]
            )
            viewModel.setReadDevice(getFelicaReadDevice)
            viewModel.getResultReg.observe(viewLifecycleOwner, {
                if(it == true){
                    val nextFragment : Fragment = if(viewModel.checkRegistered.value == true) ReaderGetInfoFragment() else ReaderRegisterFragment()
                    toNextFragment(nextFragment)
                }
            })
        }
    }

    fun toNextFragment(nextFragment : Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.parent_fragment_reader, nextFragment).addToBackStack("fragmentReaderMain")
        fragmentTransaction.commit()
    }

}