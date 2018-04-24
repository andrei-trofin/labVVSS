package controller;

import org.junit.Before;
import org.junit.Test;
import repository.ClientRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientControllerTest {
    private ClientController sut;
    private ClientRepository repo;

    @Before
    public void setUp() {
        repo = mock(ClientRepository.class);
        when(repo.addClient(any())).thenReturn(true);
        sut = new ClientController(repo);
    }

    @Test
    public void test_BVA_EC_1() {
        // Given && When
        String result = sut.addClient(Integer.MAX_VALUE+1, "Ala", "Bala");

        // Then
        assertThat(result, is("Invalid client id"));
    }

    @Test
    public void test_BVA_EC_2() {
        // Given && When
        String result = sut.addClient(7, "A&b", "Bala");

        // Then
        assertThat(result, is("Invalid character: " + "&"));
    }

    @Test
    public void test_BVA_EC_3() {
        // Given && When
        String result = sut.addClient(7, "Ala", "B&y");

        // Then
        assertThat(result, is("Invalid character: " + "&"));
    }

    @Test
    public void test_BVA_EC_4() {
        // Given && When
        String result = sut.addClient(7, "Ala", "Bala");

        // Then
        assertThat(result, is("OK"));
    }

    @Test
    public void test_BVA_EC_5() {
        // Given && When
        String result = sut.addClient(7, "", "");

        // Then
        assertThat(result, is("Name or address cannot be empty!"));
    }

    @Test
    public void test_BVA_EC_6() {
        // Given && When
        String result = sut.addClient(-7, "Ala", "Bala");

        // Then
        assertThat(result, is("Invalid client id"));
    }

    @Test
    public void test_BVA_EC_7() {
        // Given && When
        //String result = sut.addClient(7.5, "Ala", "Bala");

        // Then
        //assertThat(result, is("Invalid client id"));
    }

    @Test
    public void test_BVA_EC_8() {
        // Given && When
        String result = sut.addClient(-1, "Ala", "Bala");

        // Then
        assertThat(result, is("Invalid client id"));
    }

    @Test
    public void test_BVA_EC_9() {
        // Given && When
        String result = sut.addClient(0, "Ala", "Bala");

        // Then
        assertThat(result, is("OK"));
    }

    @Test
    public void test_BVA_EC_10() {
        // Given && When
        String result = sut.addClient(1, "Ala", "Bala");

        // Then
        assertThat(result, is("OK"));
    }

    @Test
    public void test_BVA_EC_11() {
        // Given && When
        String result = sut.addClient(Integer.MAX_VALUE, "Ala", "Bala");

        // Then
        assertThat(result, is("OK"));
    }

    @Test
    public void test_BVA_EC_12() {
        // Given && When
        String result = sut.addClient(7, "", "Bala");

        // Then
        assertThat(result, is("Name or address cannot be empty!"));
    }

    @Test
    public void test_BVA_EC_13() {
        // Given && When
        String result = sut.addClient(7, "A", "Bala");

        // Then
        assertThat(result, is("OK"));
    }

    @Test
    public void test_BVA_EC_14() {
        // Given && When
        String result = sut.addClient(7, "Ala", "");

        // Then
        assertThat(result, is("Name or address cannot be empty!"));
    }

    @Test
    public void test_BVA_EC_15() {
        // Given && When
        String result = sut.addClient(7, "Ala", "B");

        // Then
        assertThat(result, is("OK"));
    }
}