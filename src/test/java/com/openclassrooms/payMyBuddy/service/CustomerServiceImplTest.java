package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.exception.AddContactException;
import com.openclassrooms.payMyBuddy.exception.VerifyPasswordException;
import com.openclassrooms.payMyBuddy.mapper.BankTransactionMapper;
import com.openclassrooms.payMyBuddy.mapper.TransactionCustomerMapper;
import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.model.Transaction;
import com.openclassrooms.payMyBuddy.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
    @InjectMocks
    private CustomerServiceImpl customerserviceImpl;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BankTransactionMapper bankTransactionMapper;

    @Mock
    private TransactionCustomerMapper transactionCustomerMapper;

    @Test
    public void getTransactionToCustomersThrowsExceptionTest() {
        String email = "adj@gmail.com";
        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,()-> customerserviceImpl.getTransactionToCustomers(email));
    }


    @Test
    public void getTransactionToCustomersTest(){
        //GIVEN
        String email= "adj@gmail.com";
        Customer byEmail = new Customer();

        Customer friend = new Customer();
        friend.setLastName("Hemrick");

        Transaction transaction = new Transaction(null, "for fun", 100.0, 5.0, null, null, friend);

        byEmail.setToTransactions(Arrays.asList(transaction));

        TransactionCustomerDto transactionToDto = new TransactionCustomerDto();
        transactionToDto.setFriend(friend.getLastName());
        transactionToDto.setAmount(100.0);
        transactionToDto.setDescription("for fun");

        List<TransactionCustomerDto> expected= new ArrayList<>();
        expected.add(transactionToDto);

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(byEmail));
        when(transactionCustomerMapper.toDto(transaction)).thenReturn(transactionToDto);

        //WHEN
        List<TransactionCustomerDto> actual = customerserviceImpl.getTransactionToCustomers(email);

        //THEN
        assertEquals(expected.size(), actual.size());

    }

    @Test
    public void saveContactTest() throws AddContactException {
        //GIVEN
        String myEmail= "adj@gmail.com";
        String emailFriend= "luna@gmail.com";

        Customer byEmail = new Customer();

        Customer newContact = new Customer();

        when(customerRepository.findByEmail(myEmail)).thenReturn(Optional.of(byEmail));
        when(customerRepository.findByEmail(emailFriend)).thenReturn(Optional.of(newContact));


        //WHEN
        customerserviceImpl.saveContact(myEmail, emailFriend);

        //THEN
        verify(customerRepository, Mockito.times(2)).findByEmail(any());
        verify(customerRepository, Mockito.times(1)).saveAll(any());
    }

    //@Test
    public void saveContactThrowsExceptionTest() throws AddContactException {
        //GIVEN
        String myEmail = "adj@gmail.com";
        String emailFriend = "luna@gmail.com";

        Customer newContact = new Customer();

        Customer byEmail = new Customer();
        byEmail.getFriends().add(newContact);

        when(customerRepository.findByEmail(myEmail)).thenReturn(Optional.of(byEmail));
        when(customerRepository.findByEmail(emailFriend)).thenReturn(Optional.of(newContact));


        //WHEN
        assertThrows(AddContactException.class,()->customerserviceImpl.saveContact(myEmail, emailFriend));

        //THEN
        verify(customerRepository, Mockito.times(2)).findByEmail(any());
    }

    @Test
    public void getFriendsTest(){
        //GIVEN
        String email = "adj@gmail.com";
        Customer byEmail = new Customer();
        Customer friend1 = new Customer();
        Customer friend2= new Customer();

        byEmail.getFriends().add(friend1);
        byEmail.getFriends().add(friend2);

        List<Customer> expected = new ArrayList<>();
        expected.add(friend1);
        expected.add(friend2);
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(byEmail));

        //WHEN
        List<Customer> actual = customerserviceImpl.getFriends(email);

        //THEN
        assertEquals(expected.size(), actual.size());
        actual.contains(friend1);
    }

    @Test
    public void customerRegistrationTest() throws VerifyPasswordException {
        //GIVEN
        Customer customer = new Customer();
        customer.setFirstName("Adj");
        customer.setLastName("Mel");
        customer.setEmail("adj@gmail.com");
        customer.setBalance(0.0);

        String password= new String();
        String hashedPWD= new String();
        customer.setPassword(hashedPWD);

        when(passwordEncoder.encode(password)).thenReturn(hashedPWD);
        when(customerRepository.save(any())).thenReturn(customer);

        Customer expected= new Customer(null, "Adj", "Mel", "adj@gmail.com", hashedPWD, null, 0.0, null, null, null, null );

        //WHEN
        Customer actual = customerserviceImpl.customerRegistration("Adj", "Mel", "adj@gmail.com", password, password);

        //THEN
        verify(customerRepository, Mockito.times(1)).save(any());
        verify(passwordEncoder, Mockito.times(1)).encode(any());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getBalance(), actual.getBalance());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getPassword(), actual.getPassword());
    }
}
