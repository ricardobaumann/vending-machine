package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateUserCommand;
import com.github.ricbau.vendingmachine.domain.entities.Password;
import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateUserException;
import com.github.ricbau.vendingmachine.domain.exceptions.DeleteUserException;
import com.github.ricbau.vendingmachine.domain.mappers.UserCommandMapper;
import com.github.ricbau.vendingmachine.domain.ports.UserCrudPort;
import com.github.ricbau.vendingmachine.persistence.entities.UserRole;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserCrudPort mockUserCrudPort;
    @Mock
    private UserCommandMapper mockUserCommandMapper;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockUserCrudPort, mockUserCommandMapper);
    }

    @Test
    void testCreate() {
        // Setup
        final CreateUserCommand createUserCommand = new CreateUserCommand("username", "password", Set.of(
                UserRole.USER));

        // Configure UserCommandMapper.toUser(...).
        final User user = new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER));
        when(mockUserCommandMapper.toUser(
                new CreateUserCommand("username", "password", Set.of(UserRole.USER)))).thenReturn(user);

        when(mockUserCrudPort.existsByUsername("username")).thenReturn(false);

        // Configure UserCrudPort.persist(...).
        final Try<User> users = Try.success(
                new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER)));
        User expectedUser = new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER));
        when(mockUserCrudPort.persist(
                expectedUser)).thenReturn(users);

        // Run the test
        final Either<CreateUserException, User> result = userServiceUnderTest.create(createUserCommand);

        // Verify the results
        assertThat(result.get()).usingRecursiveComparison()
                .isEqualTo(expectedUser);
    }

    @Test
    void testCreate_UserCrudPortPersistReturnsFailure() {
        // Setup
        final CreateUserCommand createUserCommand = new CreateUserCommand("username", "password", Set.of(
                UserRole.USER));

        // Configure UserCommandMapper.toUser(...).
        final User user = new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER));
        when(mockUserCommandMapper.toUser(
                new CreateUserCommand("username", "password", Set.of(UserRole.USER)))).thenReturn(user);

        when(mockUserCrudPort.existsByUsername("username")).thenReturn(false);

        // Configure UserCrudPort.persist(...).
        final Try<User> users = Try.failure(new Exception("message"));
        when(mockUserCrudPort.persist(
                new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER)))).thenReturn(users);

        // Run the test
        final Either<CreateUserException, User> result = userServiceUnderTest.create(createUserCommand);

        // Verify the results
        assertThat(result.getLeft()).isInstanceOf(CreateUserException.class);
    }

    @Test
    void testDelete() {
        // Setup
        when(mockUserCrudPort.delete("id")).thenReturn(Try.success(null));

        // Run the test
        final Either<DeleteUserException, Void> result = userServiceUnderTest.delete("id");

        // Verify the results
        assertThat(result.get()).isNull();
    }

    @Test
    void testDelete_UserCrudPortReturnsNoItems() {
        // Setup
        when(mockUserCrudPort.delete("id")).thenReturn(Try.success(null));

        // Run the test
        final Either<DeleteUserException, Void> result = userServiceUnderTest.delete("id");

        // Verify the results
        assertThat(result.get()).isNull();
    }

    @Test
    void testDelete_UserCrudPortReturnsFailure() {
        // Setup
        when(mockUserCrudPort.delete("id")).thenReturn(Try.failure(new Exception("message")));

        // Run the test
        final Either<DeleteUserException, Void> result = userServiceUnderTest.delete("id");

        // Verify the results
        assertThat(result.getLeft()).isInstanceOf(DeleteUserException.class)
                .hasCauseInstanceOf(Exception.class)
                .hasRootCauseMessage("message");
    }

    @Test
    void testAddToBalanceOf() {
        // Setup
        final User user = new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER));
        final Try<User> expectedResult = Try.success(new User("id", "username", new Password("password"), 0, Set.of(
                UserRole.USER)));

        // Configure UserCrudPort.persist(...).
        final Try<User> users = Try.success(
                new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER)));
        when(mockUserCrudPort.persist(
                new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER)))).thenReturn(users);

        // Run the test
        final Try<User> result = userServiceUnderTest.addToBalanceOf(user, 0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testAddToBalanceOf_UserCrudPortReturnsFailure() {
        // Setup
        final User user = new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER));

        // Configure UserCrudPort.persist(...).
        final Try<User> users = Try.failure(new Exception("message"));
        when(mockUserCrudPort.persist(
                new User("id", "username", new Password("password"), 0, Set.of(UserRole.USER)))).thenReturn(users);

        // Run the test
        final Try<User> result = userServiceUnderTest.addToBalanceOf(user, 0);

        // Verify the results
        assertThatThrownBy(result::get)
                .isInstanceOf(Exception.class);
    }
}
