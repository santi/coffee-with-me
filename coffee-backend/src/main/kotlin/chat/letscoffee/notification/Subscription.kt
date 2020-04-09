package chat.letscoffee.notification

import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
import org.bouncycastle.jce.spec.ECPublicKeySpec
import org.bouncycastle.math.ec.ECPoint
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.util.Base64

data class Subscription (
    var endpoint: String,
    val auth: String,
    val key: String
) {
    /**
     * Returns the base64 encoded public key string as a byte[]
     */
    private val keyAsBytes: ByteArray
        get() = Base64.getDecoder().decode(key)

    /**
     * Returns the base64 encoded public key as a PublicKey object
     */
    @get:Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class, NoSuchProviderException::class)
    val userPublicKey: PublicKey
        get() {
            // TODO: Refactor this code. Can we save repeated computation of this elliptic curve?
            // If we precompute the curve, is it still safe?
            val kf: KeyFactory = KeyFactory.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME)
            val ecSpec: ECNamedCurveParameterSpec = ECNamedCurveTable.getParameterSpec("secp256r1")
            val point: ECPoint = ecSpec.curve.decodePoint(keyAsBytes)
            val pubSpec = ECPublicKeySpec(point, ecSpec)
            return kf.generatePublic(pubSpec)
        }

}
