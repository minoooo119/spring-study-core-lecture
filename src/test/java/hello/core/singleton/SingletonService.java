package hello.core.singleton;

import lombok.Getter;

public class SingletonService {
    //    싱글톤 패턴
//    클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴이다. 그래서 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야 한다.
//    private 생성자를 사용해서 외부에서 임의로 new 키워드를 사용하지 못하도록 막아야 한다.
    /**
     * static => 모든 객체에서 공유되는 클래스 변수, 클래스 메소드에 사용이 된다.
     * final => 변수 메소드 클래스에 사용이 가능하며 선언된 대상의 변경을 금지한다.
     **/

    //1. static 영역에 객체를 딱 1개만 생성해둔다.
    @Getter
    private static final SingletonService instance = new SingletonService();

    //2. public 으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한 다.
//    public static SingletonService getInstance() {
//        // 이것만을 통해 객체를 꺼낼 수 있고 아래와 같이 private 으로 생성자를 막음으로써 새롭게 생성할 수 없다.
//        // static 이므로 같은 객체만 반환을 할 수 있게 된다.
//        return instance;
//    }

    //3. 생성자를 private 으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService(){
        // private 생성자를 생성하는 것은 new 로 새롭게 만드는 것을 금지 시킨다.
        //이렇게 해서 새로 생성하는 것을 막을 수 있다.
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
