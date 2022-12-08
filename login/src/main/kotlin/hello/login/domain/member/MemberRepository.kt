package hello.login.domain.member

import org.springframework.stereotype.Repository

@Repository
class MemberRepository {
    private val store = HashMap<Long, Member>()
    private var sequence = 0L

    fun save(member: Member): Member {
        member.id = ++sequence
        println("save: member=$member")
        store[member.id] = member
        return member
    }

    fun findById(id: Long): Member? {
        return store[id]
    }

    fun findByLoginId(loginId: String): Member? {
        val all = findAll()
        return all.find { it.loginId == loginId }
    }

    fun findAll(): List<Member> {
        return store.values.toList()
    }

    fun clearStore() {
        store.clear()
    }
}
