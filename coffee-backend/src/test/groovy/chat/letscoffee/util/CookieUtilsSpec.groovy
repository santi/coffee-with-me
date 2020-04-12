package chat.letscoffee.util

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import javax.servlet.http.Cookie

class CookieUtilsSpec extends Specification {

    MockHttpServletRequest request
    MockHttpServletResponse response

    Cookie cookie1
    Cookie cookie2

    def setup() {
        request = new MockHttpServletRequest()
        response = new MockHttpServletResponse()

        cookie1 = new Cookie("cookie1", "value1")
        cookie2 = new Cookie("cookie2", "value2")
    }


    def "getting a cookie from a request should return the correct cookie"() {
        given: "a request with two cookies"
        request.cookies =  [
            cookie1,
            cookie2
        ]

        when: "getting a cookie by name"
        def returnedCookie = CookieUtils.@Companion.getCookie(request, "cookie1")

        then: "it should return the correct cookie"
        returnedCookie == cookie1
    }

    def "getting a cookie from a request with no cookies should return null"() {
        given: "a request with no cookies"
        request.cookies = []

        when: "getting a cookie"
        def cookie = CookieUtils.@Companion.getCookie(request, "cookie")

        then: "it should return null"
        cookie == null
    }

    def "getting a cookie that does not exist should return null"() {
        given: "a request with a cookie"
        request.cookies =  [
            cookie1,
        ]

        when: "getting a cookie with another name"
        def cookie2 = CookieUtils.@Companion.getCookie(request, "cookie2")

        then: "the returned cookie should be null"
        cookie2 == null
    }

    def "adding a cookie should make it available on the response object"() {
        when: "adding a cookie to a response"
        CookieUtils.@Companion.addCookie(response, "cookie", "value", 3600)

        then: "the cookie should be available on the response object"
        def cookie = response.cookies.first()
        cookie.name == "cookie"
        cookie.value == "value"
        cookie.maxAge == 3600
    }

    def "deleting a cookie should remove it from the request and set an empty cookie in the response"() {

        given: "a request with a cookie"
        cookie1.maxAge = 123
        request.cookies = [
            cookie1
        ]

        when: "deleting the cookie"
        CookieUtils.@Companion.deleteCookie(request, response, "cookie1")

        then: "the cookie on the request should be reset"
        def requestCookie = request.cookies.first()
        requestCookie.name == "cookie1"
        requestCookie.value == null
        requestCookie.maxAge == 0

        and: "a reset cookie should be set on the response"
        def responseCookie = response.cookies.first()
        responseCookie.name == "cookie1"
        responseCookie.value == null
        responseCookie.maxAge == 0
    }
}
