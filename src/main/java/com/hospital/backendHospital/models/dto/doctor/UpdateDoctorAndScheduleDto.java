package com.hospital.backendHospital.models.dto.doctor;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDoctorAndScheduleDto {

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters")
    private String firstName;

    @Size(min = 5, max = 30, message = "Last name must be between 5 and 30 characters")
    private String lastName;

    @Size(min = 8, message = "The password must be at least 8 characters")
    private String newPassword;

    @Positive(message = "Specialty must be valid")
    private Long specialtyId;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be valid")
    private String phoneNumber;

    private List<DoctorScheduleDto> schedules;

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
