package org.example.gym.dto.user;


import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Value;

public enum UserDTO {;
        private interface Username { String getUsername(); }
        private interface Password { String getPassword(); }
        private interface NewPassword { String getNewPassword(); }
        private interface Active {boolean isActive();}

        public enum Request {;                ;
                @Data
                public static class Login implements Username, Password {
                        @NotBlank(message = "Username is required")
                        String username;
                        @Size(min = 5, max = 10, message = "Password must be between 3 and 50 characters")
                        String password;
                }
                @Data
                public static class Use implements Username {
                        @NotBlank(message = "Username is required")
                        String username;
                }
                @Data
                public static class ChangeLogin implements Username, Password, NewPassword {
                        @NotBlank(message = "Username is required")
                        String username;
                        @NotBlank(message = "Old password is required")
                        String password;
                        @NotBlank(message = "New password is required")
                        String newPassword;
                }
                @Data
                public static class ActivateOrDeactivate implements Username, Active {
                        @NotBlank(message = "Username is required")
                        String username;
                        @NotBlank(message = "Password is required")
                        String password;
                        @NotBlank(message = "Active status is required")
                        boolean active;
                }
        }
        public enum Response{;
                @Value
                public static class Login implements Username, Password {
                        String username;
                        String password;
                }

        }
}
