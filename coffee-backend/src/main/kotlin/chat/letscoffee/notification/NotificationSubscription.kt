package chat.letscoffee.notification

import chat.letscoffee.user.User
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
import org.bouncycastle.jce.spec.ECPublicKeySpec
import org.bouncycastle.math.ec.ECPoint
import java.security.KeyFactory
import java.security.PublicKey
import java.security.Security
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(
    name = "notification_subscription",
    indexes = [Index(columnList = "user_id", name = "notification_subscription_user_id_uix", unique = true)])
class NotificationSubscription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "notification_subscription_user_id_fkey"), nullable = false)
    val user: User,

    @Column(columnDefinition = "TEXT", nullable = false)
    val endpoint: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    // TODO: What is really auth?
    val auth: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    val publicKey: String,

    @Column(nullable = false)
    val created: Instant = Instant.now()
) {

    /**
     * Returns the base64 encoded public key string as a byte[]
     */
    private val keyAsBytes: ByteArray
        get() = Base64.getDecoder().decode(publicKey)

    /**
     * Returns the base64 encoded public key as a PublicKey object
     */
    val userPublicKey: PublicKey
        get() {
            val point: ECPoint = ecSpec.curve.decodePoint(keyAsBytes)
            val pubSpec = ECPublicKeySpec(point, ecSpec)
            return keyFactory.generatePublic(pubSpec)
        }

    companion object {
        init {
            // Add BouncyCastle as an algorithm provider
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(BouncyCastleProvider())
            }
        }

        private val keyFactory = KeyFactory.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME)
        private val ecSpec: ECNamedCurveParameterSpec = ECNamedCurveTable.getParameterSpec("secp256r1")
    }
}
