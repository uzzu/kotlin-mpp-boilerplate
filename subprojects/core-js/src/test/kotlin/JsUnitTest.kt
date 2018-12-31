
import assertk.assertAll
import assertk.assertions.isEqualTo
import kotlin.test.Test

class JsUnitTest {
    @Test
    fun example() {
        assertAll {
            assertk.assert(1 + 1).isEqualTo(2)
        }
    }
}