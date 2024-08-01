package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

//    private final MyLogger myLogger; //얘는 request 호출 시에 생성되어서 의존 관계가 주입 되지 않아서 오류가 난다. 수정이 필요
    //즉, 요청 시점에 Provider 로 주입해줘야한다.
    private final ObjectProvider<MyLogger> objectProvider;

    /**
     * [2389c405-f87e-4d7c-ae08-c14316eebe82] request scope bean create:hello.core.common.MyLogger@2d1196a7
     * [2389c405-f87e-4d7c-ae08-c14316eebe82][/log-demo] controller test
     * [2389c405-f87e-4d7c-ae08-c14316eebe82][/log-demo] service id = testID
     * [2389c405-f87e-4d7c-ae08-c14316eebe82] request scope bean close:hello.core.common.MyLogger@2d1196a7
     */
    public void logic(String id) {
        MyLogger myLogger = objectProvider.getObject();
        myLogger.log("service id = " + id);
    }
}
