package com.nurseVarsity.BackEndCore.entity;

import com.nurseVarsity.BackEndCore.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "personal_info")
@Getter
@Setter
public class PersonalInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_type")
    private String userType;
    @Column(name = "educationLevel")
    private String educationLevel;
    @Column(length = 1024)
    private String institution;
    @Column(name = "graduation_year")
    private String graduationYear;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String state;
    private String country;
    @Column(name = "profile_picture", length = 1024)
    private String profilePicture;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;

    public PersonalInformation(){}
    public PersonalInformation(String userType, String educationLevel, String institution, String graduationYear, Gender gender, String state, String country, String profilePicture, Users user) {
        this.userType = userType;
        this.educationLevel = educationLevel;
        this.institution = institution;
        this.graduationYear = graduationYear;
        this.gender = gender;
        this.state = state;
        this.country = country;
        this.profilePicture = profilePicture;
        this.user = user;
    }
}
