package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository(); // 우선 메모리 사용
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 우선 고정 할인 정책

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 주문이 들어오면, 먼저 회원정보를 검색하고 할인정책을 거친 후 주문 생성
        Member member = memberRepository.findById(memberId); // OrderService의 입장에서는, 야 db! 너는 니 알아서 멤버 찾아온나 나는 주문만 관여한다는 마인드
        int discountPrice = discountPolicy.discount(member, itemPrice); // OrderService의 입장에서는, 야 할인! 너는 니 알아서 해라 나는 주문만 관여한다는 마인드
        // 이렇게 되면 할인을 고치고 싶으면 할인만 건들면 된다.

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
