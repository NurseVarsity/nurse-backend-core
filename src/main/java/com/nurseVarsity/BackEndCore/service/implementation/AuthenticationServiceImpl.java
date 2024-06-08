package com.nurseVarsity.BackEndCore.service.implementation;

import com.nurseVarsity.BackEndCore.dto.LoginReqDto;
import com.nurseVarsity.BackEndCore.dto.LoginResponseDto;
import com.nurseVarsity.BackEndCore.dto.PersonalInfoDto;
import com.nurseVarsity.BackEndCore.dto.RegUserDto;
import com.nurseVarsity.BackEndCore.entity.PersonalInformation;
import com.nurseVarsity.BackEndCore.entity.Role;
import com.nurseVarsity.BackEndCore.entity.Users;
import com.nurseVarsity.BackEndCore.exceptions.BadRequestException;
import com.nurseVarsity.BackEndCore.exceptions.NotFoundException;
import com.nurseVarsity.BackEndCore.repository.PersonalInfoRepository;
import com.nurseVarsity.BackEndCore.repository.RoleRepository;
import com.nurseVarsity.BackEndCore.repository.UserRepository;
import com.nurseVarsity.BackEndCore.utilities.HelperMethods;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthenticationServiceImpl implements com.nurseVarsity.BackEndCore.service.AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    private final PersonalInfoRepository personalInfoRepository;
    private final static Logger logInfo = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository, TokenService tokenService, AuthenticationManager authenticationManager, PersonalInfoRepository personalInfoRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.personalInfoRepository = personalInfoRepository;
    }

    @Override
    public Users registerUser(RegUserDto regUserDto){
        Optional<Users> checkUser = this.userRepository.findByEmail(regUserDto.getEmail());
        if(checkUser.isPresent()){
            throw new BadRequestException("user already exists");
        }
        Optional<Role> roleValue = this.roleRepository.findByAuthority("USER");
        Set<Role> authorities = new HashSet<>();
        roleValue.ifPresent(authorities::add);
        PersonalInformation personalInfo = new PersonalInformation();
        String encodedPassword = "{bcrypt}" + encoder.encode(regUserDto.getPassword());
        Users user = new Users(
                regUserDto.getEmail(),
                encodedPassword,
                authorities
        );
        user.setVerificationCode(HelperMethods.generateOtpTest());
        user.setVerificationExpiryTime(LocalDateTime.now().plusMinutes(10));
        personalInfo.setFirstName(regUserDto.getFirstName());
        personalInfo.setLastName(regUserDto.getLastName());
        personalInfo.setPhoneNumber(regUserDto.getPhoneNumber());
        Users savedUser = this.userRepository.save(user);
        personalInfo.setUser(savedUser);
        this.personalInfoRepository.save(personalInfo);
        return user;
    }

    @Override
    public boolean checkUsername(String username) {
       if(this.userRepository.findByUsername(username).isPresent()){
           throw new BadRequestException("username already exists");
       }
        return true;
    }

    @Override
    public LoginResponseDto loginUser(LoginReqDto loginReqDto) {
        Users user = null;
        Optional<Users> userByUsername = this.userRepository.findByUsername(loginReqDto.getUsername());
        if(userByUsername.isPresent()){
            user = userByUsername.get();
        }
        else {
            Optional<Users> userByEmail = this.userRepository.findByEmail(loginReqDto.getUsername());
            if(userByEmail.isEmpty()){
                throw new BadRequestException("user not found");
            }
            user = userByEmail.get();
        }
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), loginReqDto.getPassword())
        );
        String token = this.tokenService.generateJwt(auth);
        return new LoginResponseDto(
                user.getEmail(), user.getUserId(), token
        );
    }

    @Override
    public boolean verifyUser(String email, String otp) {
        Users user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new BadRequestException("user not found")
        );
        boolean isExpired = user.getVerificationExpiryTime().isBefore(LocalDateTime.now());
        if(isExpired){
            throw new BadRequestException("token has expired");
        }
        if(!Objects.equals(otp, user.getVerificationCode())){
            throw new BadRequestException("incorrect otp");
        }
        user.setEnabled(true);
        this.userRepository.save(user);
        return true;
    }

    @Override
    public PersonalInformation addPersonalInfo(PersonalInfoDto personalInfoDto) {
        Users user = this.userRepository.findByEmail(personalInfoDto.getEmail()).orElseThrow(
                () -> new BadRequestException("user not found")
        );
        PersonalInformation personalInfo = new PersonalInformation(
                personalInfoDto.getUserType(),
                personalInfoDto.getEduLevel(),
                personalInfoDto.getInstitution(),
                personalInfoDto.getGraduationYear(),
                personalInfoDto.getGender(),
                personalInfoDto.getState(),
                personalInfoDto.getCountry(),
                personalInfoDto.getProfilePicture(),
                user
        );
        this.personalInfoRepository.save(personalInfo);
        user.setUsername(personalInfoDto.getUsername());
        this.userRepository.save(user);
        return personalInfo;
    }

    @Override
    public boolean sendToken(String email) {
        Users user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("user not found")
        );
        String otp = HelperMethods.generateOtpTest();
        user.setVerificationCode(otp);
        user.setVerificationExpiryTime(LocalDateTime.now().plusMinutes(10L));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean forgotPassword(LoginReqDto loginReqDto) {
        Users user = this.userRepository.findByEmail(loginReqDto.getUsername()).orElseThrow(
                () -> new NotFoundException("user not found")
        );
        return false;
    }

}
