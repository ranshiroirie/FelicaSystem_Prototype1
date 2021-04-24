package com.ranshiroirie.felicaserverconnect.ui.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ranshiroirie.felicaserverconnect.R
import com.ranshiroirie.felicaserverconnect.ui.reader.child.ReaderMainFragment

class ReaderFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        readerViewModel = ViewModelProvider(this).get(ReaderViewModel::class.java)
        val firstFragment = ReaderMainFragment()
        val fragmentManager = childFragmentManager.beginTransaction()
        fragmentManager.add(R.id.parent_fragment_reader, firstFragment)
        fragmentManager.commit()

        return inflater.inflate(R.layout.fragment_reader, container, false)
    }
}