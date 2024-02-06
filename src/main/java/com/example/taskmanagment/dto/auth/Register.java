package com.example.taskmanagment.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Register {
    @NotEmpty(message = "Поле не может быть пустым")
    @Email
    private String email;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 8, max = 16, message = "Пароль не может быть меньше 8 и больше 16 символов")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Register register = (Register) o;
        return Objects.equals(email, register.email) && Objects.equals(password, register.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
