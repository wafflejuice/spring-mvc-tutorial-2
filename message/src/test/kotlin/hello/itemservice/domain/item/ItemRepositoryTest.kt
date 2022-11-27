package hello.itemservice.domain.item

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

internal class ItemRepositoryTest {
    private val itemRepository = ItemRepository()

    @AfterEach
    fun afterEach() {
        itemRepository.clearStore()
    }

    @Test
    fun save() {
        //given
        val item = Item(
            itemName = "itemA",
            price = 10000,
            quantity = 10
        )

        //when
        val savedItem = itemRepository.save(item)

        //then
        val findItem = itemRepository.findById(item.id!!)
        Assertions.assertThat(findItem).isEqualTo(savedItem)
    }

    @Test
    fun findAll() {
        //given
        val item1 = Item(
            itemName = "item1",
            price = 10000,
            quantity = 10
        )
        val item2 = Item(
            itemName = "item2",
            price = 20000,
            quantity = 20
        )
        itemRepository.save(item1)
        itemRepository.save(item2)

        //when
        val result = itemRepository.findAll()

        //then
        Assertions.assertThat(result.size).isEqualTo(2)
        Assertions.assertThat(result).contains(item1, item2)
    }

    @Test
    fun updateItem() {
        //given
        val item = Item(
            itemName = "item1",
            price = 10000,
            quantity = 10
        )
        val (itemId) = itemRepository.save(item)

        //when
        val updateParam = Item(
            itemName = "item2",
            price = 20000,
            quantity = 30
        )
        itemRepository.update(itemId!!, updateParam)
        val findItem = itemRepository.findById(itemId)

        //then
        Assertions.assertThat(findItem!!.itemName).isEqualTo(updateParam.itemName)
        Assertions.assertThat(findItem.price).isEqualTo(updateParam.price)
        Assertions.assertThat(findItem.quantity).isEqualTo(updateParam.quantity)
    }
}
