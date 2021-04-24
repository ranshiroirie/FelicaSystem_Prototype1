package com.ranshiroirie.felicaserverconnect.ui.reader.child

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.ranshiroirie.felicaserverconnect.R
import com.ranshiroirie.felicaserverconnect.databinding.FragmentReaderGetInfoBinding
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDeviceRepoFactory
import com.ranshiroirie.felicaserverconnect.ui.reader.ReaderViewModel
import com.ranshiroirie.felicaserverconnect.ui.reader.ReaderViewModelFactory
import kotlinx.android.synthetic.main.fragment_reader_get_info.*

class ReaderGetInfoFragment : Fragment() {
    private val viewModel : ReaderViewModel by viewModels(
        {requireParentFragment()},
        {
            ReaderViewModelFactory(
                FelicaDeviceRepoFactory.createFelicaDeviceRepository()
        )
        })
    private lateinit var binding: FragmentReaderGetInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reader_get_info, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.readerViewModel = viewModel
        binding.fragmentReaderGetInfo = this

        return binding.root
    }

    fun backToReaderMainFragment(view: View){
        val fragmentManager = parentFragmentManager
        fragmentManager.popBackStack(
            "fragmentReaderMain",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}