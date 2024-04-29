package fr.adamatraore.banking.adetechbanking.mapper;

import java.math.BigDecimal;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.adamatraore.banking.adetechbanking.dto.UserRequestDto;
import fr.adamatraore.banking.adetechbanking.entity.User;
import fr.adamatraore.banking.adetechbanking.utils.AccountUtils;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface IUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountBalance", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "authUserId", ignore = true)
    User toCreateUser(UserRequestDto userRequestDto);

    @AfterMapping
    default void afterMapping(@MappingTarget User.UserBuilder user) {
        user.accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .status(AccountUtils.AccountStatus.ACTIVE.toString()).build();
    }
}
