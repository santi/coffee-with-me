package chat.letscoffee.security.oauth2.user

abstract class OAuth2UserInfo(var attributes: Map<String, String>) {

    abstract val id: String?
    abstract val name: String?
    abstract val email: String?
    abstract val imageUrl: String?

}