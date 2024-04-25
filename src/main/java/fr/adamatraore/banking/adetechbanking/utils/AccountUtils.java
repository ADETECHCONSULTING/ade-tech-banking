package fr.adamatraore.banking.adetechbanking.utils;

public class AccountUtils {
    // Response code
    public static final String SUCCESS_CODE = "00";
    public static final String FAILED_CODE = "01";
    public static final String ACCOUNT_NOT_FOUND_CODE = "02";
    public static final String ACCOUNT_EXISTS_CODE = "03";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "007";
    public static final String ACCOUNT_TRANSFER_SUCCESS_CODE = "008";

    // Response messages
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String DORMANT = "DORMANT";
    public static final String ACCOUNT_CREATED_SUCCESSFULLY = "Account created successfully";
    public static final String ACCOUNT_CREATION_FAILED = "Account creation failed";
    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String ACCOUNT_DEACTIVATED_SUCCESSFULLY = "Account deactivated successfully";
    public static final String ACCOUNT_DEACTIVATION_FAILED = "Account deactivation failed";
    public static final String ACCOUNT_ACTIVATED_SUCCESSFULLY = "Account activated successfully";
    public static final String ACCOUNT_ACTIVATION_FAILED = "Account activation failed";
    public static final String ACCOUNT_DORMANT_SUCCESSFULLY = "Account dormant successfully";
    public static final String ACCOUNT_DORMANT_FAILED = "Account dormant failed";
    public static final String ACCOUNT_EXISTS = "Account already exists";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account was credited successfully";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account has been successfully debited";
    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";
    public static final String ACCOUNT_TRANSFER_SUCCESS = "Account transfer was successful";

    // Transaction Types
    public static final String DEBIT = "DEBIT";
    public static final String CREDIT = "CREDIT";
    public static final String TRANSFER = "TRANSFER";

    public static String generateAccountNumber() {
        return "ADT" + System.currentTimeMillis();
    }

    public static enum AccountStatus {
        ACTIVE, INACTIVE, DORMANT
    }
}
