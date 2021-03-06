package chat.letscoffee.security.security

import chat.letscoffee.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class UserPrincipal(
    val id: Long,
    val email: String,
    private val password: String?,
    private val authorities: Collection<GrantedAuthority>
) : OAuth2User, UserDetails {

    private var attributes: Map<String, Any> = emptyMap()

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getAttributes(): Map<String, Any> {
        return attributes
    }

    fun setAttributes(attributes: Map<String, Any>) {
        this.attributes = attributes
    }

    override fun getName(): String {
        return id.toString()
    }

    companion object {
        fun create(user: User): UserPrincipal {
            val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_USER"))
            val userId = user.id
                ?: throw IllegalStateException("Tried to create UserPrincipal for non-persisted user. Users must have an account to be given access rights")
            return UserPrincipal(
                userId,
                user.email,
                user.password,
                authorities
            )
        }

        fun create(user: User, attributes: Map<String, Any>): UserPrincipal {
            val userPrincipal = create(user)
            userPrincipal.setAttributes(attributes)
            return userPrincipal
        }
    }

}
