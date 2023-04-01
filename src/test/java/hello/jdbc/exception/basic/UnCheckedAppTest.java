package hello.jdbc.exception.basic;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class UnCheckedAppTest {

    @Test
    void unChecked() {
        Controller controller = new Controller();

        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    @Test
    void printEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
//            e.printStackTrace();
            log.info("ex", e);
        }

    }



    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }


    }

    static class Service {

        public void logic() {
            Repository repository = new Repository();
            NetWorkClient netWorkClient = new NetWorkClient();

            repository.call();
            netWorkClient.call();

        }

    }

    static class NetWorkClient {
        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }

    }

    static class Repository {
        public void call() {
            try {
                runSql();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }

        }

        public void runSql() throws SQLException {
            throw new SQLException();
        }

    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    // cause 는 원래 터진 예외도 함께 가져와서 처리한다.
    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}
