package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        // 1. 조회: 호출할 때 마다 객체를 생성성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회: 호출할 때 마다 객체를 생성성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2 확인
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);

        // => 요청을 할때마다 객체를 새로 생성함
        // => 고객 트래픽이 초당 100이 나오면 초당 100개의 객체가 생성되고 소멸된다.
        // => 메모리 낭비가 너무 심하다
        // 해결방안 : 해당 객체가 딱 1개만 생성되고, 공유하도록 설계 ( 싱글톤 패턴 )

    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        // 참조값 확인
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // => 자바가 뜰때 SingletonService에서 생성되는 객체 하나만 가져와서 쓰는 방법

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
        // isSameAs 은 == 비교
        // equal 은 자바의 equal

    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
        //AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class); // 스프링으로 생성

        // 1. 조회: 호출할 때 마다 객체를 생성하는게 아니라, 이미 만들어진 객체를 공유
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);

        // 2. 조회: 호출할 때 마다 객체를 생성하는게 아니라, 이미 만들어진 객체를 공유
        MemberService memberService2 =  ac.getBean("memberService", MemberService.class);

        // 참조값이 같은 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 == memberService2 확인
        Assertions.assertThat(memberService1).isSameAs(memberService2);



    }
}
