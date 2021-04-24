package com.ranshiroirie.felicaserverconnect.ui.reader.child

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.ranshiroirie.felicaserverconnect.R
import com.ranshiroirie.felicaserverconnect.databinding.FragmentReaderRegisterBinding
import com.ranshiroirie.felicaserverconnect.felica.device.FelicaDeviceRepoFactory
import com.ranshiroirie.felicaserverconnect.ui.reader.ReaderViewModel
import com.ranshiroirie.felicaserverconnect.ui.reader.ReaderViewModelFactory

class ReaderRegisterFragment : Fragment() {
    private val viewModel : ReaderViewModel by viewModels(
        {requireParentFragment()},
        {
            ReaderViewModelFactory(
                FelicaDeviceRepoFactory.createFelicaDeviceRepository()
            )
        })
    private lateinit var binding: FragmentReaderRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reader_register, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.readerViewModel = viewModel
        binding.fragmentReaderReqister = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.inputtedDeviceName.observe(viewLifecycleOwner,{
            view.findViewById<Button>(R.id.button_next_register).isClickable = it.toString().isNotEmpty()
        })
    }

    fun backToReaderMainFragment(view: View){
        val fragmentManager = parentFragmentManager
        fragmentManager.popBackStack(
            "fragmentReaderMain",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    fun showDialogConfirmRegister(view: View){
        val dialogBuilder = AlertDialog.Builder(
            ContextThemeWrapper(activity,
            R.style.Theme_AppCompat_DayNight_Dialog
        )
        )
            .setMessage("登録しますか？")
            .setPositiveButton("OK") { _, _ ->
                nextToRegister(view)
            }
            .setNegativeButton("キャンセル"){ _, _ -> }
        dialogBuilder.show()
    }

    private fun nextToRegister(view: View){
        viewModel.registerFelicaDevice()
        viewModel.checkRegistered.observe(viewLifecycleOwner, {
            if (it == true){
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.parent_fragment_reader, ReaderGetInfoFragment()).addToBackStack(null)
                fragmentTransaction.commit()
            }
        })
    }
}