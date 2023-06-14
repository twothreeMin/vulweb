package smvulweb.vulweb;

import org.junit.jupiter.api.*;

public class JUnitCycleTest {
    @BeforeAll
    static void beforeAll() {
        // 전체 테스트를 시작하기 전에 처음으로 한번만 실행
        // 예를 들어 데이터베이스를 연결해야 하거나 테스트 환경을 초기화 할 때 사용
        // 이 어노테이션은 전체 테스트 실행 주기에서 한번만 호출되어야 하기 떄문에 메서드를 static으로 선언
        System.out.println("@BeforeAll");
    }

    @BeforeEach
    public void beforeEach() {
        // 테스트 케이스를 시작하기 전에 매번 실행
        // 예를 들어 테스트 메서드에서 사용 하는 객체를 초기화하거나 테스트에 필요한 값을 미리 넣을 때 사용
        System.out.println("@BeforeEach");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }
    @Test
    public void test2() {
        System.out.println("test2");
    }
    @Test
    public void test3() {
        System.out.println("test3");
    }

    @AfterAll
    static void afterAll() {
        // 전체 테스트를 마치고 종료하기전에 한번만 실행
        // 예를 들어 데이터베이스 연결을 종료할 떄나 공통적으로 사용하는 자원을 해제할때 사용
        System.out.println("@AfterAll");
    }

    @AfterEach
    public void afterEach() {
        // 각 테스트 케이스를 종료하기전 매번 실행
        // 예를 들어 테스트 이후에 특정 데이터를 삭제해야 하는 경우 사용
        System.out.println("@AfterEach");
    }
}
