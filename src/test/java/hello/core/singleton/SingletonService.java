package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService(); // 자기 자신을 클래스 내부에 static final로 선언

    public static SingletonService getInstance(){
        return instance;
    }

    private SingletonService(){
        // new를 이용해서 객체를 생성하는 것을 막는다
    }

    public void logic(){
        System.out.println("싱글톤 객체 호출");
    }

}
