package com.ranshiroirie.felicaserverconnect.felica.reader

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.nfc.tech.NfcF
import android.os.Message
import android.util.Log
import java.util.ArrayList

interface FelicaReaderInterface : FelicaReader.Listener {
    fun onReadFelica(data : ArrayList<String>)                        // タグ受信イベント
}
class FelicaReader(private val context: Context, private val activity : Activity) : android.os.Handler() {
    private var nfcmanager : NfcManager? = null
    private var nfcadapter : NfcAdapter? = null
    private var callback : CustomReaderCallback? = null

    private var listener: FelicaReaderInterface? = null
    interface Listener {}

    fun start(){
        callback = CustomReaderCallback()
        callback?.setHandler(this)
        nfcmanager = context.getSystemService(Context.NFC_SERVICE) as NfcManager?
        nfcadapter = nfcmanager!!.defaultAdapter
        nfcadapter!!.enableReaderMode(activity,callback
            ,NfcAdapter.FLAG_READER_NFC_F,null)

    }
    fun stop(){
        nfcadapter!!.disableReaderMode(activity)
        callback = null


    }

    override fun handleMessage(msg: Message) {                  // コールバックからのメッセージクラス         // 読み取り終了
        listener?.onReadFelica(msg.obj as ArrayList<String>)                 // 拡張用
    }

    fun setListener(listener: FelicaReader.Listener?) {         // イベント受け取り先を設定
        if (listener is FelicaReaderInterface) {
            this.listener = listener as FelicaReaderInterface
        }
    }

    private class CustomReaderCallback : NfcAdapter.ReaderCallback {
        private var handler : android.os.Handler? = null
        override fun onTagDiscovered(tag: Tag) {
            val id = byteToHex(tag.id)
            Log.d("id", id)
            val msg = Message.obtain()
            val nfc : NfcF = NfcF.get(tag) ?: return
            try {
                nfc.connect()
                val systemCode = byteToHex(nfc.systemCode)
                Log.d("systemCode", systemCode)
                val manufacturer = byteToHex(nfc.manufacturer)
                Log.d("manufacturer", manufacturer)
                nfc.close()
                msg.obj = arrayListOf(id, systemCode, manufacturer)
                if (handler != null) handler?.sendMessage(msg)
            }catch (e : Exception){
                nfc.close()
            }
        }

        fun setHandler(handler  : android.os.Handler){
            this.handler = handler
        }

        private fun byteToHex(b : ByteArray) : String{
            var s : String = ""
            for (element in b){
                s += "%02X".format(element)
            }
            return s
        }
    }
}