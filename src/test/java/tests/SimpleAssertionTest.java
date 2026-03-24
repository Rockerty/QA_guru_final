package tests;

import org.junit.jupiter.api.*;

public class SimpleAssertionTest {

    int firstSummand;

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll work\n");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AfterAll work");
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("BeforeEach work\n");
        firstSummand = 3;
    }

    @Test
    void successfulFirstAssert() {
        Assertions.assertTrue(firstSummand>2);

    }

    @Test
    void successfulSecondAssert() {
        Assertions.assertTrue(firstSummand>2);

    }
}


