package com.example.sxg.myapplication

import android.os.Build
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class DeCryptor{

    private val TRANSFORMATION = "AES/GCM/NoPadding"
    private val ANDROID_KEY_STORE = "AndroidKeyStore"

    private var keyStore: KeyStore? = null


    init {

        initKeyStore()

    }

    fun initKeyStore(){

        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore!!.load(null)
    }


    fun decryptData(userName : String, encryptedData : ByteArray, encryptionIv : ByteArray): String {
        val cipher : Cipher = Cipher.getInstance(TRANSFORMATION)

        var spec : GCMParameterSpec =
            GCMParameterSpec(128, encryptionIv)


        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(userName), spec)


        return (cipher.doFinal(encryptedData)).toString()

    }



fun getSecretKey (userPassWord : String) : SecretKey{

    return (keyStore!!.getEntry(userPassWord, null) as KeyStore.SecretKeyEntry).secretKey



    }


}