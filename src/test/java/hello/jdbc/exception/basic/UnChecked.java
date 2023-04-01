package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class UnChecked {

    @Test
    void catchEx() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void throwsEx() {
        Service service = new Service();
        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUnCheckedException.class);
    }


    /**
     * RuntimeException 을 상속 받은 예외는 언체크 예외가 된다.
     */
    static class MyUnCheckedException extends RuntimeException {
        public MyUnCheckedException(String message) {
            super(message);
        }
    }

    static class Repository {

        public void call() {
            throw new MyUnCheckedException("ex");
        }
    }

    /**
     * UnCheckedException
     * 예외를 잡거나 던지지 않아도 된다.
     * 예외를 잡지 않으면 자동으로 메소드 밖으로 던진다.
     */

    static class Service {

        Repository repository = new Repository();

        /**
         * 예외를 잡는 메서드
         * 언체크드 예외는 컴파일 오류가 발생하지 않지만
         * 메서드 내부에서 따로 처리해줄 수도 있다.
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUnCheckedException e) {
                log.info("message = {}", e.getMessage(), e);
            }
        }

        /**
         * 예외를 던지는 메소드
         * 언체크드 예외는 예외를 잡지 않아도 된다.
         * 하지만 잡지 않으면 자동으로 메소드 밖으로 던진다.
         */
        public void callThrow() {
            repository.call();
        }
    }
}
