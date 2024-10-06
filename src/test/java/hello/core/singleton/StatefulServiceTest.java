package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleTon(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(Testconfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);
        // ThreadA: USER A 10000 Order
        statefulService1.order("userA", 10000);
        // ThreadB: USER B 20000 Order
        statefulService2.order("userB", 20000);
        // ThreadA: USER A 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println(price);
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }
    static class Testconfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}