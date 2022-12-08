package hello.login.domain.item

import org.springframework.stereotype.Repository

@Repository
class ItemRepository {
    fun save(item: Item): Item {
        item.id = ++sequence
        store[item.id] = item
        return item
    }

    fun findById(id: Long): Item? {
        return store[id]
    }

    fun findAll(): List<Item> {
        return ArrayList(store.values)
    }

    fun update(itemId: Long, updateParam: Item) {
        val findItem = findById(itemId)
        findItem?.itemName = updateParam.itemName
        findItem?.price = updateParam.price
        findItem?.quantity = updateParam.quantity
    }

    fun clearStore() {
        store.clear()
    }

    companion object {
        private val store: MutableMap<Long, Item> = HashMap() //static
        private var sequence = 0L //static
    }
}
