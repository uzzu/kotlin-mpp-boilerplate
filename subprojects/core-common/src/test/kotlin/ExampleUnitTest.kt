import assertk.assertThat
import assertk.assertAll
import assertk.assertions.isEqualTo
import kotlin.test.Test

class ExampleUnitTest {

    @Test
    fun example() {
        assertAll {
            assertThat(1 + 1).isEqualTo(2)
        }
    }
}
