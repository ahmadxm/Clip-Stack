package com.shiftx.util

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object AESUtil {
    // NOTE: For production, use salted hashing; this is to replicate web AES minimum.
    private const val SECRET = "ShiftX_Default_Secret_ChangeMe" // Replace or load from secure source
    private const val SALT = "ShiftX_Salt"
    private val iv = IvParameterSpec(ByteArray(16))

    private fun deriveKey(password: String, salt: String): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), 65536, 256)
        val tmp = factory.generateSecret(spec)
        return SecretKeySpec(tmp.encoded, "AES")
    }

    fun encrypt(plain: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, deriveKey(SECRET, SALT), iv)
        val enc = cipher.doFinal(plain.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(enc, Base64.NO_WRAP)
    }

    fun decrypt(cipherText: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, deriveKey(SECRET, SALT), iv)
        val decoded = Base64.decode(cipherText, Base64.NO_WRAP)
        val dec = cipher.doFinal(decoded)
        return String(dec, Charsets.UTF_8)
    }
}