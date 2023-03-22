package com.example.boot3testinge2;

import com.example.boot3testinge2.model.Person;
import com.example.boot3testinge2.repository.PersonRepository;
import com.example.boot3testinge2.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonRepository repo;

    @InjectMocks
    PersonService personService;

    // Without using @ExtendWith(MockitoExtension.class)
    /*
    @BeforeEach
    void setUp() {
        repo = Mockito.mock(PersonRepository.class);
        personService = new PersonService(repo);
    }
    */

    @Test
    void loginSuccess() {
        //Arrange
        Person person = new Person(1L, "Jeetu", "jeetu@gmail.com", "jeetu123");
        when(repo.findByEmailAndPassword(eq("jeetu@gmail.com"), anyString()))
                .thenReturn(Optional.of(person));
        //Act
        String token = personService.login("jeetu@gmail.com", "jeetu123");

        //Assert
        assertThat(token).isNotNull();
    }

    @Test
    void loginFailure() {
        when(repo.findByEmailAndPassword("jeetu@gmail.com", "jeetu123"))
                .thenReturn(Optional.empty());

        String token = personService.login("jeetu@gmail.com", "jeetu123");

        assertThat(token).isNull();
    }

    @Test
    void findByEmail() {
        Person person = new Person(1L, "Jeetu", "jeetu@gmail.com", "jeetu123");
        when(repo.findByEmail("jeetu@gmail.com")).thenReturn(Optional.of(person));

        Optional<Person> optionalPerson = personService.findByEmail("jeetu@gmail.com");

        assertThat(optionalPerson).isPresent();
        assertThat(optionalPerson.get().getName()).isEqualTo("Jeetu");
        assertThat(optionalPerson.get().getEmail()).isEqualTo("jeetu@gmail.com");
    }

    @Test
    void shouldCreatePersonSuccessfully() {
        when(repo.findByEmail("jeetu@gmail.com")).thenReturn(Optional.empty());
        when(repo.create(any(Person.class))).thenAnswer(answer -> answer.getArgument(0));

        Person person = personService.create("Jeetu", "JEETU@gmail.com", "jeetu123");

        assertEquals("Jeetu", person.getName());
        assertEquals("jeetu@gmail.com", person.getEmail());

        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(repo).create(argumentCaptor.capture());
        Person value = argumentCaptor.getValue();
        assertEquals("Jeetu", value.getName());
        assertEquals("jeetu@gmail.com", value.getEmail());
    }

    @Test
    void updatePerson() {
        Person person = new Person(1L, "Jeetu", "jeetu@gmail.com", "jeetu123");

        doNothing().when(repo).update(any(Person.class));
        //doThrow(new RuntimeException("Invalid email")).when(repo).update(any(Person.class));

        personService.update(person);

        verify(repo).update(any(Person.class));
        //verify(repo, times(1)).update(any(Person.class));
        //verify(repo, atMostOnce()).update(any(Person.class));
        //verify(repo, atLeastOnce()).update(any(Person.class));
    }
}