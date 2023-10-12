package dev.flb.service;

import dev.flb.domain.FloorAccessRestrictedException;
import dev.flb.domain.authorization.*;
import dev.flb.domain.building.Floor;
import dev.flb.domain.people.Customer;
import dev.flb.domain.people.NextGenEmployee;
import dev.flb.domain.people.Employee;
import dev.flb.domain.people.OldEmployee;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@ApplicationScoped
public class AccessService {

    /*
       Java 21 Without Switch but Record pattern
        */
    boolean performAccess21WithoutSwitch(Person person, Floor floor) {

        if (person instanceof Customer customer) {
            throw new FloorAccessRestrictedException(person, floor);
        } else if (person instanceof NextGenEmployee nextGenEmployee) {
            if (nextGenEmployee.getAuthorization() instanceof StandardAuthorization(_, Level level)) {
                return checkStandardAuthorization(level, nextGenEmployee);
            } else if (nextGenEmployee.getAuthorization() instanceof SensibleAuthorization(Level sensibleLevel)) {
                return checkSensibleAuthorization(sensibleLevel, nextGenEmployee);
            } else if (nextGenEmployee.getAuthorization() instanceof FloorAuthorization(_, DepartmentName name)) {
                return name.compareTo(floor.departmentName()) == 0;
            }
        }
        return false;
    }
    /*
    JAVA 21 Style Record pattern with switch
    JEP 440
     */

    boolean performAccess21(Person person, Floor floor) {
        return switch (person) {
            case Customer customer -> throw new FloorAccessRestrictedException(customer, floor);
            case NextGenEmployee nextGenEmployee -> switch (nextGenEmployee.getAuthorization()) {
                case StandardAuthorization(_, Level level) -> checkStandardAuthorization(level, nextGenEmployee);
                case SensibleAuthorization(Level sensibleLevel) ->
                        checkSensibleAuthorization(sensibleLevel, nextGenEmployee);
                case FloorAuthorization(_, DepartmentName name) -> name.compareTo(floor.departmentName()) == 0;
                /*
                Les champs inutile pour notre cas peuvent etre ign orés avec _
                L'ordre des champs doit etre respecté
                 */
                case null, default -> false;
            };
            default -> false;
        };
    }


    /*
    JAVA 17 Pattern matching with if only anbd no Record Pattern
     */
    boolean performAccess17(Person person, Floor floor) {

        if (person instanceof Customer customer) {
            throw new FloorAccessRestrictedException(customer, floor);
        } else if (person instanceof Employee employee) {
            if (employee.getAuthorization() instanceof StandardAuthorization standA) {
                return checkStandardAuthorization(standA.level(), employee);
            } else if (employee.getAuthorization() instanceof SensibleAuthorization sensA) {
                return checkSensibleAuthorization(sensA.level(), employee);
            } else if (employee.getAuthorization() instanceof FloorAuthorization floorA) {
                return floorA.name().compareTo(floor.departmentName()) == 0;
            }
        }
        return false;
    }


    public boolean checkIfAuthorized() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(1));
        return true;

    }

    public boolean checkIfAuthorizedMulti() throws InterruptedException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });

        }
        return true;
    }


    private boolean checkSensibleAuthorization(Level sensibleLevel, Employee nextGenEmployee) {
        return false;
    }


    public boolean checkStandardAuthorization(Level level, Employee nextGenEmployee) {
        return switch (nextGenEmployee) {
            //Not authorize when is available only with pattern matching
            // case DIAMOND, RUBY  when nextGenEmployee.getDepartmentName() == DepartmentName.ADMIN -> true;
            case NextGenEmployee newEmployee
                    when newEmployee.getDepartmentName() == DepartmentName.LEGAL ||
                         newEmployee.getDepartmentName() == DepartmentName.SALES ||
                         newEmployee.getDepartmentName() == DepartmentName.CUSTOMER_SERVICE ->
                    newEmployee.getAuthorization() instanceof StandardAuthorization sta && sta.level() == level;

            case OldEmployee oldEmployee when (long) oldEmployee.getId() > 2000 ->
                    oldEmployee.getAuthorization() instanceof StandardAuthorization sta && sta.level() == level;

            default -> throw new IllegalArgumentException("Unexpected value: " + nextGenEmployee);
        };
    }


}

