package com.example.sxg.myapplication

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class Crytor{

    private val TRANSFORMATION = "AES/GCM/NoPadding"
    private val ANDROID_KEY_STORE = "AndroidKeyStore"

    lateinit var encryption : ByteArray

    var Iv : ByteArray? = null

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



    fun getSecretKey2 (userPassWord : String) : SecretKey {

        return (keyStore!!.getEntry(userPassWord, null) as KeyStore.SecretKeyEntry).secretKey



    }



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