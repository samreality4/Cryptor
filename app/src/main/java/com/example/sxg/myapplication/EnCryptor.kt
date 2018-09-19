package com.example.sxg.myapplication

import android.annotation.SuppressLint
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class EnCryptor(){

    var testName : String = "sam"

    var testPass : String = "12345678"

    lateinit var encryption : ByteArray

    lateinit var Iv : ByteArray

    private val TRANSFORMATION : String = "AES/GCM/NoPadding"

    private val ANDROID_KEY_STORE = "AndroidKeyStore"

    init {



    }



//Get the secret key
@RequiresApi(Build.VERSION_CODES.M)
public fun getSecretKey(userName: String): SecretKey {
    //Get the instance of KeyPairGenerator in order to use
    val kpg: KeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEY_STORE)


    //this specifies the properties for the keys we are going to generate
    //The private key can only be used for signing or verification and only with SHA-256 or SHA-512 as the message digest.
    //Message digests are secure one-way hash functions that take arbitrary-sized data and output a fixed-length hash value.
    val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(userName, KeyProperties.PURPOSE_ENCRYPT)
            .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

    kpg.init(parameterSpec)

    return kpg.generateKey()



}

///Actual Encrypting to the data format of your choice
    fun encryptText(userName: String, textToEncrypt: String): ByteArray {

        val cipher = Cipher.getInstance(TRANSFORMATION)

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(userName))


        Iv = cipher.iv

        encryption = cipher.doFinal(textToEncrypt.toByteArray(charset("UTF-8")))



        return encryption
    }



}