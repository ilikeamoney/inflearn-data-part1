package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class CheckedTest {

    @Test
    void exceptionTest() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void throwsException() {
        Service service = new Service();
        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }


    static class Service {
        Repository repository = new Repository();

        /**
         * 호출한 메서드에서 예외가 던져지면
         * try catch final 문 으로 던져진 예외를 잡아서 처리해 줘야한다.
         */

        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("message = {}", e.getMessage(), e);
            }
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 체크 예외를 잡지 않고 던지려면 throws 예외를 메서드에 필수로 선언해야 한다.
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }

    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }

    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

}
