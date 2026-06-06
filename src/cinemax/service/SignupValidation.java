package service;

public record SignupValidation(
        boolean usernameAvailable,
        boolean passwordLongEnough,
        boolean passwordHasUppercase,
        boolean passwordHasDigit,
        boolean passwordHasSpecial) {
    public boolean isValid() {
        return usernameAvailable && passwordLongEnough
                && passwordHasUppercase && passwordHasDigit
                && passwordHasSpecial;
    }
}