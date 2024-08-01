package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    //    private final MyLogger myLogger; //얘는 request 호출 시에 생성되어서 의존 관계가 주입 되지 않아서 오류가 난다. 수정이 필요
    //즉, 요청 시점에 Provider 로 주입해줘야한다.
    private final ObjectProvider<MyLogger> objectProvider;


    /**
     * clientA
     * [47ecea06-a3f5-4105-b844-c822ea6f4d11] request scope bean create:hello.core.common.MyLogger@20e0e1c8
     * [47ecea06-a3f5-4105-b844-c822ea6f4d11][/log-demo] controller test
     * [47ecea06-a3f5-4105-b844-c822ea6f4d11][/log-demo] service id = testID
     * [47ecea06-a3f5-4105-b844-c822ea6f4d11] request scope bean close:hello.core.common.MyLogger@20e0e1c8
     * clientB
     * [295f35d7-0ab3-45c9-b9fd-d604215418b8] request scope bean create:hello.core.common.MyLogger@6e7a227a
     * [295f35d7-0ab3-45c9-b9fd-d604215418b8][/log-demo] controller testr
     * [295f35d7-0ab3-45c9-b9fd-d604215418b8][/log-demo] service id = testID
     * [295f35d7-0ab3-45c9-b9fd-d604215418b8] request scope bean close:hello.core.common.MyLogger@6e7a227a
     */
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURI().toString();
        System.out.println("requestURL = " + requestURL);

        MyLogger myLogger = objectProvider.getObject();

        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testID");

        return "OK";
    }
}
