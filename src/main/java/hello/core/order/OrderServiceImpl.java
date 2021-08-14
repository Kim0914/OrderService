package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    //private final MemberRepository memberRepository = new MemoryMemberRepository(); // 우선 메모리 사용
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 우선 고정 할인 정책
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 비율 할인 정책
    // 위의 두 할인 정책은 DIP를 위반한다. Why? OrderService이 Discount interface에만 의존 해야하는데, 그것의 구현체인 Fix, Rate 에도 의존하기 때문
    // 그래서 다음과 같이 DIP 원칙을 해결 할 수 있다

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /*            // 자동으로 의존 관계 대입 (setter 주입)
    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    @Autowired // 자동으로 의존 관계 대입 (생성자 주입)
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 주문이 들어오면, 먼저 회원정보를 검색하고 할인정책을 거친 후 주문 생성
        Member member = memberRepository.findById(memberId); // OrderService의 입장에서는, 야 db! 너는 니 알아서 멤버 찾아온나 나는 주문만 관여한다는 마인드
        int discountPrice = discountPolicy.discount(member, itemPrice); // OrderService의 입장에서는, 야 할인! 너는 니 알아서 해라 나는 주문만 관여한다는 마인드
        // 이렇게 되면 할인을 고치고 싶으면 할인만 건들면 된다.

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
