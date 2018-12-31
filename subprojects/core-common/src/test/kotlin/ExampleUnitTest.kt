import assertk.assert
import assertk.assertAll
import assertk.assertions.isEqualTo
import kotlin.test.Test

class ExampleUnitTest {

    @Test
    fun example() {
        assertAll {
            assert(1 + 1).isEqualTo(2)
        }
    }
}
