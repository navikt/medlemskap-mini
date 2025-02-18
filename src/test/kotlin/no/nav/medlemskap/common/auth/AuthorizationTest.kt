package no.nav.medlemskap.common.auth

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import no.nav.medlemskap.common.ServerTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class AuthorizationTest : ServerTest() {

    @Test
    fun `Gitt kall uten Authorization-header når tjeneste kalles så gis det 401 unauthorized`() {
        given()
            .body("test")
        RestAssured.post("/")
            .then()
            .statusCode(401)
    }
}
