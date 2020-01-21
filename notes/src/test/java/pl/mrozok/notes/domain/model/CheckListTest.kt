package pl.mrozok.notes.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CheckListTest {

    @Test
    fun `has content`() {
        val items = mutableListOf(CheckList.CheckItem("Item", false))
        assertThat(CheckList("Some title", items).isEmpty()).isFalse()
    }

    @Test
    fun `has no content`() {
        assertThat(CheckList("", mutableListOf()).isEmpty()).isTrue()
    }

}
