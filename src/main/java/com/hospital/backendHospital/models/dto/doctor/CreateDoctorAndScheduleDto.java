package com.hospital.backendHospital.models.dto.doctor;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDoctorAndScheduleDto {

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 10, max = 30, message = "First name must be between 10 and 30 characters")
    @NotBlank(message = "Username is required")
    private String firstName;

    @Size(min = 10, max = 30, message = "Last name must be between 10 and 30 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Size(min = 8, message = "The password must be at least 8 characters")
    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Specialty is required")
    @Positive(message = "Specialty must be valid")
    private Long specialty;

    @NotNull(message = "Schedule is required")
    private Set<DoctorScheduleDto> schedules;

    @Data
    public static class DoctorScheduleDto {
        @NotNull(message = "Day of week is required")
        private DayOfWeek dayOfWeek;

        @NotNull(message = "Start time is required")
        private LocalTime startTime;

        @NotNull(message = "End time is required")
        private LocalTime endTime;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DoctorScheduleDto that)) return false;
            return dayOfWeek == that.dayOfWeek &&
                    startTime.equals(that.startTime) &&
                    endTime.equals(that.endTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dayOfWeek, startTime, endTime);
        }
    }
}
