package org.example.gym.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;


public enum UserDTO {;
        private interface Username { String getUsername(); }
        private interface Password { String getPassword(); }
        private interface NewPassword { String getNewPassword(); }

        public enum Request{;
                @Value
                public static class Login implements Username, Password {
                        @NotBlank(message = "Username is required")
                        String username;
                        @NotBlank(message = "Password is required")
                        String password;
                }
                @Value
                public static class ChangeLogin implements Username, Password, NewPassword {
                        @NotBlank(message = "Username is required")
                        String username;
                        @NotBlank(message = "Old password is required")
                        String password;
                        @NotBlank (message = "New password is required")
                        String newPassword;
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
