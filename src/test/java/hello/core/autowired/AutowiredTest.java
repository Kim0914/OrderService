package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean{

        // Member 는 스프링 빈이 아님

        @Autowired(required = false) //의존관계가 없으면 호출이 안됨
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired // Null 이 존재하면 null 이 호출된다
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired // Null 이면 empty 라고 optional 로 감싸서 출력된다
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }

    }
}
