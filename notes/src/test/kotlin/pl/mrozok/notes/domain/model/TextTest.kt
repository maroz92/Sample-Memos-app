package pl.mrozok.notes.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TextTest {

    @Test
    fun `has content`() {
        assertThat(Text("Some title", "Lorem ipsum").isEmpty()).isFalse()
    }

    @Test
    fun `has no content`() {
        assertThat(Text("", "").isEmpty()).isTrue()
    }

}
