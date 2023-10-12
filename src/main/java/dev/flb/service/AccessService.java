package dev.flb.service;

import dev.flb.domain.FloorAccessRestrictedException;
import dev.flb.domain.authorization.*;
import dev.flb.domain.building.Floor;
import dev.flb.domain.people.Customer;
import dev.flb.domain.people.NextGenEmployee;
import dev.flb.domain.people.Employee;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@ApplicationScoped
public class AccessService {

    /*
    JAVA 21 Style Record pattern with switch
     */
    boolean performAccess21(Employee employee, Floor floor) {
        return switch (employee) {
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
    boolean performAccess17(Employee employee, Floor floor) {

        if (employee instanceof Customer customer) {
            throw new FloorAccessRestrictedException(employee, floor);
        } else if (employee instanceof NextGenEmployee nextGenEmployee) {
            if (nextGenEmployee.getAuthorization() instanceof StandardAuthorization standA) {
                return checkStandardAuthorization(standA.level(), nextGenEmployee);
            } else if (nextGenEmployee.getAuthorization() instanceof SensibleAuthorization sensA) {
                return checkSensibleAuthorization(sensA.level(), nextGenEmployee);
            } else if (nextGenEmployee.getAuthorization() instanceof FloorAuthorization floorA) {
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


    private boolean checkSensibleAuthorization(Level sensibleLevel, NextGenEmployee nextGenEmployee) {
        return false;
    }

    private boolean checkStandardAuthorization(Level level, NextGenEmployee nextGenEmployee) {
        return true;
    }

}

