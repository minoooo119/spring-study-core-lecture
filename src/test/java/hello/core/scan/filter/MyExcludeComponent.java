package hello.core.scan.filter;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
    //이 annotation 이 불리면 자동으로 component 등록하는 것 못하게
}
