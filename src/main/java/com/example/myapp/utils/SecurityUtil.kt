package com.example.myapp.utils

import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

class SecurityUtil {
    companion object {
        private const val transformation: String = "RSA/ECB/PKCS1Padding"

        /**
         * Public key loader
         * @param stored key string content
         * @return public key object
         */
        fun loadPublicKey(stored: String): PublicKey {
            val data: ByteArray = Base64.getDecoder().decode(stored.toByteArray())
            val spec = X509EncodedKeySpec(data)
            val fact = KeyFactory.getInstance("RSA")
            return fact.generatePublic(spec)
        }

        /**
         * Public key encryption
         * @param input
         * @param publicKey public key
         * @return encoded string
         */
        fun encryptByPublicKey(input: String, publicKey: PublicKey): String {
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            val data = cipher.doFinal(input.toByteArray())
            return Base64.getEncoder().encodeToString(data)
        }
    }
}