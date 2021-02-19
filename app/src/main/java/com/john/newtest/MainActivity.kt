package com.john.newtest

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.text.Editable
import android.text.InputType.*
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import com.john.common.BaseActivity
import com.john.jrouter.JRouter
import com.john.jrouter.RouteCallback
import com.john.jrouter.RoutePath
import com.john.jrouter.annotation.Route
import com.john.newtest.activities.WebActivity
import com.john.newtest.aidl.SocketToggle
import com.john.newtest.databinding.ActivityMainBinding
import com.john.newtest.kotlin.logDebug
import com.john.newtest.service.RemoteService
import com.john.newtest.socketest.Callback
import com.john.newtest.socketest.MarketSubscriber
import com.john.newtest.socketest.Req

@Route("/app/main")
class MainActivity : BaseActivity() {

    companion object {
        val  INPUTTYPE_PASSWORD = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
        val  INPUTTYPE_PASSWORD_VISIBLE = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    }

    lateinit var mBinding: ActivityMainBinding
    var connection: ServiceConnection? = null
    var socketToggle: SocketToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        val intent = Intent(this, RemoteService::class.java)
        bindService(intent, conn, BIND_AUTO_CREATE)

        mBinding.stop.setOnClickListener { MarketSubscriber.get().close() }
        mBinding.sub.setOnClickListener { MarketSubscriber.get().subscribe("Ag12") }
        mBinding.unsub.setOnClickListener { MarketSubscriber.get().unsubscribe("Ag12") }

        MarketSubscriber.get().setCallback(object : Callback<Req?>() {
            override fun onCallback(data: Req?) {
                mBinding.text.text = data?.data
            }
        })

        mBinding.edit.inputType = INPUTTYPE_PASSWORD
        mBinding.edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                logDebug("onTextChanged: ")
            }

            override fun afterTextChanged(s: Editable) {
                logDebug("afterTextChanged: ")
            }
        })

        mBinding.testButton.setOnClickListener {
            var inputType = mBinding.edit.inputType
            if (inputType == INPUTTYPE_PASSWORD) {
                inputType = INPUTTYPE_PASSWORD_VISIBLE
            } else{
                inputType = INPUTTYPE_PASSWORD
            }
            mBinding.edit.inputType = inputType
        }

        mBinding.openWeb.setOnClickListener {
            val pdfUrl = "https://www.mcdonalds.co.jp/media_library/6523/file.pdf"
            //val pdfUrl = "https://mindorks.s3.ap-south-1.amazonaws.com/courses/MindOrks_Android_Online_Professional_Course-Syllabus.pdf"

            val intent1 = Intent(activity, WebActivity::class.java)
            intent1.putExtra("url", pdfUrl)
            startActivity(intent1)
        }
    }

    private val conn: ServiceConnection
        private get() {
            connection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName, service: IBinder) {
                    socketToggle = SocketToggle.Stub.asInterface(service)
                }

                override fun onServiceDisconnected(name: ComponentName) {}
            }
            return connection as ServiceConnection
        }

    override fun onDestroy() {
        super.onDestroy()
        try {
            socketToggle!!.quit()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        unbindService(connection)
    }
}