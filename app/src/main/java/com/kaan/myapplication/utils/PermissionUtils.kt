package com.kaan.myapplication.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 * Description: Permission utility class
 *
 * Features:
 * - Single and multiple permissions can be entered (in the parameter, use *[LIST_NAME].totypedarray () expansion).
 * - In BaseActivity, rewrite 'onRequestPermissionsResult', call 'PermissionUtils.onRequestPermissionsResult' when response.  Processing results are returned to the 'request.callback'
 *
 * Version History:
 * - 1.0 (2023/12/16): Initial release
 * - 1.1 (2024/02/02): Added all permissions that need to be dynamically applied at runtime so far
 *
 * Author: Kaan.cheung (Zhangja)
 * Email: Kaan.cheung@outlook.com
 */
class PermissionUtils(private val activity: FragmentActivity) {
    //Permissions that need to be dynamically applied for at runtime
    //Location
    val ACCESS_FINE_LOCATION =
        Manifest.permission.ACCESS_FINE_LOCATION //Get accurate location (e.g. GPS)
    val ACCESS_COARSE_LOCATION =
        Manifest.permission.ACCESS_COARSE_LOCATION //Get a general location (such as a base station or Wi-Fi).

    //Camera
    val CAMERA = Manifest.permission.CAMERA //Access the camera to take photos or record videos

    //Microphone
    val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO //Record audio or use a microphone

    //Contact person
    val READ_CONTACTS = Manifest.permission.READ_CONTACTS //Read user contacts
    val WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS//Modifying User Contacts
    val GET_ACCOUNTS =
        Manifest.permission.GET_ACCOUNTS//Access a list of accounts in the Account service

    //Telephone
    val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE//Access call status
    val CALL_PHONE = Manifest.permission.CALL_PHONE//Call the phone number directly
    val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG//Read call log
    val WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG//Modify call history
    val ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL//Add a voice mail to the system
    val USE_SIP = Manifest.permission.USE_SIP//Using SIP Service
    val PROCESS_OUTGOING_CALLS =
        Manifest.permission.PROCESS_OUTGOING_CALLS//Listen, modify, or terminate incoming calls

    //Calendar
    val READ_CALENDAR = Manifest.permission.READ_CALENDAR//Read the user's calendar events
    val WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR//Modify a user's calendar event

    //SMS
    val SEND_SMS = Manifest.permission.SEND_SMS//Send a text message
    val RECEIVE_SMS = Manifest.permission.RECEIVE_SMS//Receive SMS
    val READ_SMS = Manifest.permission.READ_SMS//Read SMS content
    val RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH//Receive a WAP PUSH message
    val RECEIVE_MMS = Manifest.permission.RECEIVE_MMS//Receive MMS messages

    //Store
    val READ_EXTERNAL_STORAGE =
        Manifest.permission.READ_EXTERNAL_STORAGE//Read device external storage.
    val WRITE_EXTERNAL_STORAGE =
        Manifest.permission.WRITE_EXTERNAL_STORAGE//Writes to device external storage

    private var permissionsToRequest: MutableList<String> = mutableListOf()
    private var callback: SingleCallback? = null

    interface SingleCallback {
        fun callback(
            isAllGranted: Boolean,
            granted: List<String>,
            deniedForever: List<String>,
            denied: List<String>
        )
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001

        fun with(activity: FragmentActivity): PermissionUtils = PermissionUtils(activity)
    }

    fun permission(vararg permissions: String): PermissionUtils {
        permissionsToRequest.addAll(permissions)
        return this
    }

    fun callback(callback: SingleCallback): PermissionUtils {
        this.callback = callback
        return this
    }

    fun request() {
        if (permissionsToRequest.isEmpty()) {
            callback?.callback(true, emptyList(), emptyList(), emptyList())
            return
        }

        val notGrantedPermissions = permissionsToRequest.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }

        if (notGrantedPermissions.isEmpty()) {
            callback?.callback(true, permissionsToRequest, emptyList(), emptyList())
        } else {
            ActivityCompat.requestPermissions(
                activity,
                notGrantedPermissions.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != PERMISSION_REQUEST_CODE) return

        val granted = mutableListOf<String>()
        val denied = mutableListOf<String>()
        val deniedForever = mutableListOf<String>()

        permissions.forEachIndexed { index, permission ->
            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                granted.add(permission)
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    deniedForever.add(permission)
                } else {
                    denied.add(permission)
                }
            }
        }

        callback?.callback(
            denied.isEmpty() && deniedForever.isEmpty(),
            granted,
            deniedForever,
            denied
        )
    }

    fun isPermissionGranted(vararg permissions: String): Boolean {
        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

}
