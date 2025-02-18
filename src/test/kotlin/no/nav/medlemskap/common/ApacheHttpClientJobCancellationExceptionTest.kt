package no.nav.medlemskap.common

import io.ktor.client.call.*
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@Disabled("Fikk ConnectException på GHA-bygget.. Merkelig...")
class ApacheHttpClientJobCancellationExceptionTest {

    @Test
    fun `JobCancellationException kastes naar backend service ikke naas`() {
        // Ref https://github.com/ktorio/ktor/issues/1592

        assertThrows<CancellationException> {
            runBlocking {
                apacheHttpClient.get {
                    url("http://localhost:9987/finnesIkke")
                }.body()
            }
        }
    }
}
