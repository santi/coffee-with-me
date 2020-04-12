package chat.letscoffee.security.controller

import chat.letscoffee.exception.BadRequestException
import chat.letscoffee.security.model.AuthProvider
import chat.letscoffee.user.User
import chat.letscoffee.payload.LoginRequest
import chat.letscoffee.payload.SignUpRequest
import chat.letscoffee.user.UserRepository
import chat.letscoffee.security.security.TokenProvider
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(private val authenticationManager: AuthenticationManager, private val userRepository: UserRepository
, private val passwordEncoder: PasswordEncoder
,private val tokenProvider: TokenProvider ) {

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<String> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.email,
                        loginRequest.password
                )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token: String = tokenProvider.createToken(authentication)
        return ResponseEntity.ok(token)
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignUpRequest): ResponseEntity<String> {
        if (userRepository.existsByEmail(signUpRequest.email)) {
            throw BadRequestException("Email address already in use.")
        }
        // Creating user's account
        val user = User(name = signUpRequest.name,
            email = signUpRequest.email,
            provider = AuthProvider.LOCAL,
            password = passwordEncoder.encode(signUpRequest.password))

        val result: User = userRepository.save(user)
        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.id).toUri()
        return ResponseEntity.created(location)
                .body("User registered successfully")
    }
}
