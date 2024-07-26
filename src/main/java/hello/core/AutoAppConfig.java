package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

/**
 * section 6. component scan 관련
 */
@Configuration
@ComponentScan(
        // class path 를 다 뒤져서 Component 관련 클래스들을 다 빈으로 등록을 해준다 => 의존 관계는 autowired 를 통해 주입해준다.
        //Configuration 도 Component 에 속하지만 이 것도 포함하면 중복 bean 이 등록되므로 해당 부분은 걸러주게 한다
        /**
         * basePackages
         * class path 의 시작 path 를 지정해준다. 그 하위 클래스에서만 component 를 조회한다.
         * {"hello.core.member","hello.core.service"} 이렇게 여러개를 줄 수도 있다.
        */
//        basePackages = "hello.core.member",

        /**
         * basePackageClasses
         * 해당 클래스가 포함된 페키지 하위 패키지, 클래스 탐색
        */
//        basePackageClasses = AutoAppConfig.class,

        /**
         * excludeFilters
         * AppConfig, TestConfig 등 앞서 만들어두었던 설정 정보도 함께 등록되고, 실행되어 버린다.
         * 그래서 `excludeFilters` 를 이용해서 설정정보는 컴포넌트 스캔 대상에서 제외했다.
         * 보통 설정 정보를 컴포넌트 스캔 대상에서 제외하지는 않지만, 기존 예제 코드를 최대한 남기고 유지하기 위해서 이 방법을 선택했다.
        */
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)

        //base 지정안하면 default 로는 구성 정보 패키지 하위를 다 찾는다.
        //그래서 구성정보 configuration 을 최상단 패키지에 두는 것이 관례이다.
)
//알아서 스캔 해온데 이러면 => Component annotation 이 붙은 것들을 자동으로 빈으로 다 저장을 해준다.
public class AutoAppConfig {
//        @Bean(name = "memoryMemberRepository")
//        MemberRepository memberRepository() {
//                return new MemoryMemberRepository();
//        }
        /**
         * 중복이 있을 경우는 자동 말고 수동으로 등록한 빈이 우선권이 있다.
         * Overriding bean definition for bean 'memoryMemberRepository' with a different definition:
         * replacing [Generic bean: class [hello.core.member.MemoryMemberRepository];
         * defined in file [/Users/mino/Desktop/mino/core/out/production/classes/hello/core/member/MemoryMemberRepository.class]]
         * with [Root bean: class [null]; scope=;
         * defined in hello.core.AutoAppConfig]
         */

        /**
         * 그러나 spring 자체는 overriding 을 금지하고 있다.
         * The bean 'memoryMemberRepository',
         * defined in class path resource [hello/core/AutoAppConfig.class],
         * could not be registered. A bean with that name has already been
         * defined in file [/Users/mino/Desktop/mino/core/out/production/classes/hello/core/member/MemoryMemberRepository.class]
         * and overriding is disabled.
         */
}
