package hello.login

import hello.login.domain.item.Item
import hello.login.domain.item.ItemRepository
import hello.login.domain.member.Member
import hello.login.domain.member.MemberRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class TestDataInit(
    private val itemRepository: ItemRepository,
    private val memberRepository: MemberRepository
) {
    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    fun init() {
        itemRepository.save(Item(itemName = "itemA", price = 10000, quantity = 10))
        itemRepository.save(Item(itemName = "itemB", price = 20000, quantity = 20))

        memberRepository.save(Member(loginId = "test", password = "test!", name = "tester"))
    }
}
