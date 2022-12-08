package hello.itemservice.web.validation

import hello.itemservice.domain.item.Item
import hello.itemservice.domain.item.ItemRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.validation.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/validation/v3/items")
class ValidationItemControllerV3(
    private val itemRepository: ItemRepository,
    private val itemValidator: ItemValidator
) {
    @InitBinder
    fun init(dataBinder: DataBinder) {
        dataBinder.addValidators(itemValidator)
    }

    @GetMapping
    fun items(model: Model): String {
        val items = itemRepository.findAll()
        model.addAttribute("items", items)
        return "validation/v3/items"
    }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId)
        model.addAttribute("item", item)
        return "validation/v3/item"
    }

    @GetMapping("/add")
    fun addForm(model: Model): String {
        model.addAttribute("item", Item())
        return "validation/v3/addForm"
    }

    //    @PostMapping("/add")
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
            return "validation/v3/addForm"
        }

        // 성공 로직

        val savedItem = itemRepository.save(item)
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v3/items/{itemId}"
    }

    //    @PostMapping("/add")
    fun addItemV2(
        @ModelAttribute item: Item,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {

        // 검증 로직
        if (StringUtils.hasText(item.itemName).not()) {
            bindingResult.addError(
                FieldError(
                    "item",
                    "itemName",
                    item.itemName,
                    false,
                    null,
                    null,
                    "상품 이름은 필수입니다."
                )
            )
        }
        if (item.price == null || item.price!! < 1000 || item.price!! > 1_000_000) {
            bindingResult.addError(
                FieldError(
                    "item",
                    "price",
                    item.price,
                    false,
                    null,
                    null,
                    "가격은 1,000 ~ 1,000,000 까지 허용합니다."
                )
            )

        }
        if (item.quantity == null || item.quantity!! >= 9999) {
            bindingResult.addError(
                FieldError(
                    "item",
                    "quantity",
                    item.quantity,
                    false,
                    null,
                    null,
                    "수량은 최대 9,999 까지 허용합니다."
                )
            )
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price!! * item.quantity!!
            if (resultPrice < 10000) {
                bindingResult.addError(
                    ObjectError(
                        "item",
                        null,
                        null,
                        "가격 * 수량은 10,000 이상이어야 합니다. 현재 값 = $resultPrice"
                    )
                )
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "validation/v3/addForm"
        }

        // 성공 로직

        val savedItem = itemRepository.save(item)
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v3/items/{itemId}"
    }

    //@PostMapping("/add")
    fun addItemV3(
        @ModelAttribute item: Item,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {

        // 검증 로직
        if (StringUtils.hasText(item.itemName).not()) {
            bindingResult.addError(
                FieldError(
                    "item",
                    "itemName",
                    item.itemName,
                    false,
                    arrayOf("required.item.itemName"),
                    null,
                    null
                )
            )
        }
        if (item.price == null || item.price!! < 1000 || item.price!! > 1_000_000) {
            bindingResult.addError(
                FieldError(
                    "item",
                    "price",
                    item.price,
                    false,
                    arrayOf("range.item.price"),
                    arrayOf(1000, 1000000),
                    null
                )
            )

        }
        if (item.quantity == null || item.quantity!! >= 9999) {
            bindingResult.addError(
                FieldError(
                    "item",
                    "quantity",
                    item.quantity,
                    false,
                    arrayOf("max.item.quantity"),
                    arrayOf(9999),
                    null
                )
            )
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price!! * item.quantity!!
            if (resultPrice < 10000) {
                bindingResult.addError(
                    ObjectError(
                        "item",
                        arrayOf("totalPriceMin"),
                        arrayOf(10000, resultPrice),
                        null
                    )
                )
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "validation/v3/addForm"
        }

        // 성공 로직

        val savedItem = itemRepository.save(item)
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v3/items/{itemId}"
    }


    //    @PostMapping("/add")
    fun addItemV4(
        @ModelAttribute item: Item,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {

        // 검증 로직
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required")
//        if (StringUtils.hasText(item.itemName).not()) {
//            bindingResult.rejectValue("itemName", "required")
//        }

        if (item.price == null || item.price!! < 1000 || item.price!! > 1_000_000) {
            bindingResult.rejectValue("price", "range", arrayOf(1000, 1000000), null)
        }
        if (item.quantity == null || item.quantity!! >= 9999) {
            bindingResult.rejectValue("quantity", "max", arrayOf(9999), null)
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.price != null && item.quantity != null) {
            val resultPrice = item.price!! * item.quantity!!
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", arrayOf(10000, resultPrice), null)
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "validation/v3/addForm"
        }

        // 성공 로직

        val savedItem = itemRepository.save(item)
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v3/items/{itemId}"
    }

    //    @PostMapping("/add")
    fun addItemV5(
        @ModelAttribute item: Item,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {

        itemValidator.validate(item, bindingResult)

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "validation/v3/addForm"
        }

        // 성공 로직

        val savedItem = itemRepository.save(item)
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v3/items/{itemId}"
    }


    @PostMapping("/add")
    fun addItemV6(
        @Validated @ModelAttribute item: Item,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "validation/v3/addForm"
        }

        // 성공 로직

        val savedItem = itemRepository.save(item)
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/validation/v3/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String {
        val item = itemRepository.findById(itemId)
        model.addAttribute("item", item)
        return "validation/v3/editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(@PathVariable itemId: Long, @ModelAttribute item: Item): String {
        itemRepository.update(itemId, item)
        return "redirect:/validation/v3/items/{itemId}"
    }
}
