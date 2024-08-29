package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;


    //consultationDate: Local date
    @NotNull(message = "Consultation date should not be Empty!")
    @JsonFormat(pattern= "yyyy-MM-dd")
    @FutureOrPresent
    @Column(columnDefinition = "datetime not null")
    private LocalDate consultationDate;

    //topic: String
    @NotEmpty(message = "Topic of the consultation should not be Empty!")
    @Column(columnDefinition = "varchar(500) not null")
    private String topic;

    //status: String(scheduled,completed,canceled,pending)
    //@NotEmpty(message = "Status should not be Empty!")
    @Pattern(regexp = "^(Pending|Scheduled|Completed|Canceled)$" , message = "status has three valid inputs only,Pending, Scheduled, Completed or Canceled!")
   //@Column(columnDefinition = "varchar(20) not null check(status='Scheduled' or status='Completed' or status='Canceled')")
    private String status;

    //duration: double
    @NotNull(message = "Duration should not be null!")
    @Column(columnDefinition = "DOUBLE not null")
    private double duration;

//    //Recommendation: String
//    @NotEmpty(message = "Recommendation should not be Empty!")
//    @Column(columnDefinition = "varchar(500) not null")
//    private String Recommendation;


    @Column(columnDefinition = "DOUBLE not null")
    private double consultationFee=60;


    //created_at: Timestamp when the consultation was scheduled.
    @Column(columnDefinition = "datetime default (current_timestamp)")
    private LocalDate createdAt;

    //updated_at: Timestamp for the last update.
    @Column(columnDefinition = "datetime default (current_timestamp)")
    private LocalDate updatedAt;

    //Many consultation to one consultant
    @ManyToOne
    @JoinColumn(name= "Consultant_id" , referencedColumnName = "id")
    @JsonIgnore
    private Consultant consultant;

    private Double totalAmount;

    //Many consultations to one user
    @ManyToOne
    @JoinColumn(name="User_id" , referencedColumnName = "id")
    @JsonIgnore
    private Users users;

}
