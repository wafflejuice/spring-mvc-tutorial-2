package hello.login.web.item

import hello.login.domain.item.Item
import hello.login.domain.item.ItemRepository
import hello.login.web.item.form.ItemSaveForm
import hello.login.web.item.form.ItemUpdateForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/items")
class ItemController(
    private val itemRepository: ItemRepository
) {
    @GetMapping
    fun items(model: Model): String {
        val items = itemRepository.findAll()
        model.addAttribute("items", items)
        return "items/items"
    }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId)
        model.addAttribute("item", item)
        return "items/item"
    }

    @GetMapping("/add")
    fun addForm(model: Model): String {
        model.addAttribute("item", Item())
        return "items/addForm"
    }

    @PostMapping("/add")
    fun addItem(
        @Validated @ModelAttribute("item") form: ItemSaveForm,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {

        //특정 필드 예외가 아닌 전체 예외
        if (form.price != null && form.quantity != null) {
            val resultPrice: Int = form.price * form.quantity
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", arrayOf<Any>(10000, resultPrice), null)
            }
        }
        if (bindingResult.hasErrors()) {
            return "items/addForm"
        }

        //성공 로직
        val item = Item(itemName = form.itemName, price = form.price, quantity = form.quantity)
        val savedItem = itemRepository.save(item)
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId)
        model.addAttribute("item", item)
        return "items/editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(
        @PathVariable itemId: Long,
        @Validated @ModelAttribute("item") form: ItemUpdateForm,
        bindingResult: BindingResult
    ): String {

        //특정 필드 예외가 아닌 전체 예외
        if (form.price != null && form.quantity != null) {
            val resultPrice: Int = form.price * form.quantity
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", arrayOf<Any>(10000, resultPrice), null)
            }
        }
        if (bindingResult.hasErrors()) {
            return "items/editForm"
        }
        val itemParam = Item(itemName = form.itemName, price = form.price, quantity = form.quantity)

        itemRepository.update(itemId, itemParam)
        return "redirect:/items/{itemId}"
    }
}
