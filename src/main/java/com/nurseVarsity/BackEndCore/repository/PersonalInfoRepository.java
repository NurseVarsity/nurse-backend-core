package com.nurseVarsity.BackEndCore.repository;

import com.nurseVarsity.BackEndCore.entity.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRepository extends JpaRepository<PersonalInformation, Long> {
}
