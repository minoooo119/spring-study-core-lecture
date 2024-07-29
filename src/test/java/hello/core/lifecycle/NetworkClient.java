package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;



//public class NetworkClient implements InitializingBean, DisposableBean {
public class NetworkClient{
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
//        connect();
//        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call = " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close = " + url);
    }
    /**
     * 빈 생명 주기 콜백 방법
     * 빈 등록 초기화, 소멸 메서드 지정
     * 결론은 애노테시션으로 하는 방법을 사용하자
     *
     * @PostConstruct, @PreDestory 이거 사용하자
     */

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient Init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        // 마지막에 호출되는 함수
        System.out.println("NetworkClient Destroy");
        disconnect();
    }

    /**
     * 빈 생명 주기 콜백 방법
     * 빈 등록 초기화, 소멸 메서드 지정
     *
     * 메서드 이름을 자유롭게 줄 수 있다.
     * 스프링 빈이 스프링 코드에 의존하지 않는다.
     * 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
     */
//    public void init() {
//        System.out.println("NetworkClient Init");
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    public void close() {
//        // 마지막에 호출되는 함수
//        System.out.println("NetworkClient Destroy");
//        disconnect();
//    }

    /**
     * 빈 생명 주기 콜백 방법
     * 인터페이스 InitializingBean, DisposableBean
     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //의존 관계 주입이 끝나면 호출되는 함수이다.
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        // 마지막에 호출되는 함수
//        System.out.println("NetworkClient Destroy");
//        disconnect();
//    }
}
