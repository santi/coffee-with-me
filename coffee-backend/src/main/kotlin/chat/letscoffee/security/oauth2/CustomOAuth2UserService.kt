package chat.letscoffee.security.oauth2

import chat.letscoffee.exception.OAuth2AuthenticationProcessingException
import chat.letscoffee.security.model.AuthProvider
import chat.letscoffee.security.oauth2.user.OAuth2UserInfo
import chat.letscoffee.security.oauth2.user.OAuth2UserInfoFactory
import chat.letscoffee.security.security.UserPrincipal
import chat.letscoffee.user.User
import chat.letscoffee.user.UserService
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class CustomOAuth2UserService(
    private val userService: UserService
) : DefaultOAuth2UserService() {

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(oAuth2UserRequest)
        return try {
            processOAuth2User(oAuth2UserRequest, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) { // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.clientRegistration.registrationId, oAuth2User.attributes)
        if (StringUtils.isEmpty(oAuth2UserInfo.email)) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }

        val userExists = userService.existsByEmail(oAuth2UserInfo.email)
        var user: User
        if (userExists) {
            user = userService.getUserByEmail(oAuth2UserInfo.email)
            if (userHasAnotherProvider(user, oAuth2UserRequest)) {
                throw OAuth2AuthenticationProcessingException("You are already signed up with a ${user.provider} account. Use that account to login instead")
            }
            user = updateExistingUser(user, oAuth2UserInfo)
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo)
        }
        return UserPrincipal.create(user, oAuth2User.attributes)
    }

    private fun registerNewUser(oAuth2UserRequest: OAuth2UserRequest, oAuth2UserInfo: OAuth2UserInfo): User {
        val user = User(
            provider = AuthProvider.valueOf(oAuth2UserRequest.clientRegistration.registrationId.toUpperCase()),
            providerId = oAuth2UserInfo.id,
            name = oAuth2UserInfo.name,
            email = oAuth2UserInfo.email,
            imageUrl = oAuth2UserInfo.imageUrl
        )
        return userService.create(user)
    }

    private fun updateExistingUser(existingUser: User, oAuth2UserInfo: OAuth2UserInfo): User {
        existingUser.name = oAuth2UserInfo.name
        existingUser.imageUrl = oAuth2UserInfo.imageUrl
        return userService.update(existingUser)
    }

    private fun userHasAnotherProvider(user: User, oAuth2UserRequest: OAuth2UserRequest): Boolean {
        return user.provider != AuthProvider.valueOf(oAuth2UserRequest.clientRegistration.registrationId.toUpperCase())
    }
}
