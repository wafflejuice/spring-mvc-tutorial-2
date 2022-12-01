package hello.itemservice.web.validation

import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.ItemRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/validation/v2/items")
class ValidationItemControllerV2(
    private val itemRepository: ItemRepository
) {
    @GetMapping
    fun items(model: Model): String {
        val items = itemRepository.findAll()
        model.addAttribute("items", items)
        return "validation/v2/items"
    }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId)
        model.addAttribute("item", item)
        return "validation/v2/item"
    }

    @GetMapping("/add")
    fun addForm(model: Model): String {
        model.addAttribute("item", Item())
        return "validation/v2/addForm"
    }

    @PostMapping("/add")
    fun addItemV1(
        @ModelAttribute item: Item,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {

        // 검증 로직
        if (StringUtils.hasText(item.itemName).not()) {
            bindingResult.addError(FieldError("item", "itemName", "상품 이름은 필수입니다."))
        }
        if (item.price == null || item.price!! < 1000 || item.price!! > 1_000_000) {
            bindingResult.addError(FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."))

        }
        if (item.quantity == null || item.quantity!! >= 9999) {
            bindingResult.addError(FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."))
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price!! * item.quantity!!
            if (resultPrice < 10000) {
                bindingResult.addError(ObjectError("item", "가격 * 수량은 10,000 이상이어야 합니다. 현재 값 = $resultPrice"))
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "validation/v2/addForm"
        }

        // 성공 로직

        val savedItem = itemRepository.save(item)
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v2/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId)
        model.addAttribute("item", item)
        return "validation/v2/editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(@PathVariable itemId: Long, @ModelAttribute item: Item): String {
        itemRepository.update(itemId, item)
        return "redirect:/validation/v2/items/{itemId}"
    }
}