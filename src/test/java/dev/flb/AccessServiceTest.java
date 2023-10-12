package dev.flb;

import dev.flb.domain.authorization.DepartmentName;
import dev.flb.domain.authorization.Level;
import dev.flb.domain.authorization.StandardAuthorization;
import dev.flb.domain.people.Employee;
import dev.flb.domain.people.NextGenEmployee;
import dev.flb.domain.people.OldEmployee;
import dev.flb.service.AccessService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static dev.flb.domain.authorization.DepartmentName.ADMIN;
import static org.assertj.core.api.Assertions.*;
import static org.instancio.Select.field;

public class AccessServiceTest {

    private AccessService accessService;

    public static Stream<Arguments> userWithLevelAskForStandardAuthorization() {
        return
                Stream.of(
                        Arguments.arguments(getDefaultNextGenEmployeeWithDepartmentAndStandardAuthorizationAtLevel(ADMIN, Level.A), Level.A, true),
                        Arguments.arguments(getDefaultNextGenEmployeeWithDepartmentAndStandardAuthorizationAtLevel(ADMIN, Level.B), Level.A, false),
                        Arguments.arguments(getDefaultOldGenEmployeeWithIdAndStandardAuthorizationAtLevel(2001, Level.A), Level.A, true),
                        Arguments.arguments(getDefaultOldGenEmployeeWithIdAndStandardAuthorizationAtLevel(2001, Level.B), Level.A, true),
                        Arguments.arguments(getDefaultOldGenEmployeeWithIdAndStandardAuthorizationAtLevel(-5, Level.B), Level.A, IllegalArgumentException.class)
                );

    }

    @BeforeEach
    void setUp() {
        accessService = new AccessService();
    }

    @ParameterizedTest
    @MethodSource("userWithLevelAskForStandardAuthorization")
    public void checkStandardAuthorization_when_called_with_Employee_should_return_expected_result(
            Employee employee,
            Level level,
            Object expectedResult
    ) {
        //ARRANGE
        //ACT
        AtomicBoolean isAuthorized = new AtomicBoolean(false);
        Throwable throwable = catchThrowable(() ->
        {
            isAuthorized.set(accessService.checkStandardAuthorization(level, employee));
        });
        //ASSERT
        switch (expectedResult) {
            case Boolean resultOk -> assertThat(resultOk).isEqualTo(expectedResult);
            case IllegalStateException ex -> assertThat(throwable).isInstanceOf(ex.getClass());
            default -> fail(STR. "The result type \{ expectedResult.getClass() } is not managed by this test" );
        }


    }

    private static NextGenEmployee getDefaultNextGenEmployeeWithDepartmentAndStandardAuthorizationAtLevel(DepartmentName departmentName, Level level) {
        return Instancio.of(NextGenEmployee.class)
                .set(field(NextGenEmployee::getId), UUID.randomUUID())
                .set(field(NextGenEmployee::getDepartmentName), departmentName)
                .set(field(NextGenEmployee::getAuthorization), Instancio.of(StandardAuthorization.class).set(field(StandardAuthorization::level), level))
                .create();
    }

    private static OldEmployee getDefaultOldGenEmployeeWithIdAndStandardAuthorizationAtLevel(long id, Level level) {
        return Instancio.of(OldEmployee.class)
                .set(field(OldEmployee::getId), id)
                .set(field(OldEmployee::getAuthorization), Instancio.of(StandardAuthorization.class).set(field(StandardAuthorization::level), level))
                .create();
    }


}
