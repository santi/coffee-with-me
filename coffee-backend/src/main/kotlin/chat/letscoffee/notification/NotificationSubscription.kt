package chat.letscoffee.notification

import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
import org.bouncycastle.jce.spec.ECPublicKeySpec
import org.bouncycastle.math.ec.ECPoint
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.util.Base64

data class NotificationSubscription (
    var endpoint: String,
    val auth: String,
    val key: String
) {
    private val keyFactory = KeyFactory.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME)
    private val ecSpec: ECNamedCurveParameterSpec = ECNamedCurveTable.getParameterSpec("secp256r1")

    /**
     * Returns the base64 encoded public key string as a byte[]
     */
    private val keyAsBytes: ByteArray
        get() = Base64.getDecoder().decode(key)

    /**
     * Returns the base64 encoded public key as a PublicKey object
     */
    val userPublicKey: PublicKey
        get() {
            val point: ECPoint = ecSpec.curve.decodePoint(keyAsBytes)
            val pubSpec = ECPublicKeySpec(point, ecSpec)
            return keyFactory.generatePublic(pubSpec)
        }

}
