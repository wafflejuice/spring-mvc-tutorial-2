package hello.itemservice.domain.item

data class Item(
    var id: Long? = null,
    var itemName: String? = null,
    var price: Int? = null,
    var quantity: Int? = null
)
