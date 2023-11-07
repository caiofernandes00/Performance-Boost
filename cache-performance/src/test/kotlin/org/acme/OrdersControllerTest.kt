package org.acme

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class OrdersControllerTest {

    @Test
    fun testHelloEndpoint() {
        given()
            .`when`().get("/q/health")
            .then()
            .statusCode(200)
            .body("status", `is`("UP"))
    }

}