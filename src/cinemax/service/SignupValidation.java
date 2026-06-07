package service;

public record SignupValidation(
        boolean usernameAvailable,
        boolean usernameValid,
        boolean passwordLongEnough,
        boolean passwordHasUppercase,
        boolean passwordHasDigit,
        boolean passwordHasSpecial,
        boolean passwordOnlyAllowedSpecial) {
    public boolean isValid() {
        return usernameAvailable && usernameValid && passwordLongEnough
                && passwordHasUppercase && passwordHasDigit
                && passwordHasSpecial && passwordOnlyAllowedSpecial;
    }
}