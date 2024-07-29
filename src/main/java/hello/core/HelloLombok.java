package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {
//    Lombok 을 잘 사용하는 것 중요하다.
    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("mino");

        String name1 = helloLombok.getName();
        System.out.println("name1 = " + name1);

        System.out.println("toString = "+ helloLombok.toString());
    }
}
