package com.zgcns.gravity.lms.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zgcns.gravity.authmanager.entity.Role;
import com.zgcns.gravity.authmanager.repository.RoleRepository;
// import com.zgcns.gravity.authmanager.dto.UserDTO;
import com.zgcns.gravity.lms.converter.UserDTOConverter;
import com.zgcns.gravity.lms.dto.UserDTO;
import com.zgcns.gravity.lms.exception.UserNotFoundException;
import com.zgcns.gravity.lms.model.Employee;
import com.zgcns.gravity.lms.model.LeaveData;
import com.zgcns.gravity.lms.model.User;
import com.zgcns.gravity.lms.repositories.EmployeeRepository;
import com.zgcns.gravity.lms.repositories.LeaveDataRepository;
import com.zgcns.gravity.lms.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LeaveDataRepository leaveDataRepository;
    private final RoleRepository roleRepository;
    private final UserDTOConverter userDTOConverter;

    @Autowired
    public UserService(UserRepository userRepository, EmployeeRepository employeeRepository, LeaveDataRepository leaveDataRepository, RoleRepository roleRepository, UserDTOConverter userDTOConverter) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.leaveDataRepository = leaveDataRepository;
        this.roleRepository = roleRepository;
        this.userDTOConverter = userDTOConverter;
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        User user = userDTOConverter.convertUserDTOtoUser(userDTO);
        
        // Set up Employee if not present
        Employee employee = user.getEmployee();
        if (employee == null) {
            employee = new Employee();
            user.setEmployee(employee);
            employee.setUser(user);
        }

        employee.setFirstName(user.getFirstName());
        employee.setLastName(user.getLastName());
        employee.setEmail(user.getEmail());

        // Create and associate LeaveData with the user
        LeaveData leaveData = new LeaveData();
        leaveData.setCasualLeaves(7.0);
        leaveData.setMedicalLeaves(7.0);
        leaveData.setPrivilegedLeaves(20.0);
        leaveData.setUnpaidLeaves(0.0);
        leaveData.setUser(user);
        user.setLeaveData(leaveData);

        // Set default user details
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);
        user.setVerified(true);

        // Set roles
        Set<Role> roles = new HashSet<>();
        for (Role roleDTO : user.getRoles()) {
            Role role = roleRepository.findByName(roleDTO.getName())
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(roleDTO.getName());
                        return roleRepository.save(newRole);
                    });
            roles.add(role);
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return userDTOConverter.convertUsertoUserDTO(savedUser);
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> authenticateUser(UserDTO userDTO) throws UserNotFoundException {
        User user = userDTOConverter.convertUserDTOtoUser(userDTO);
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("No user found for this email!");
        }

        User databaseUser = userOptional.get();

        if (!bCryptPasswordEncoder.matches(user.getPassword(), databaseUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error", "Invalid password")
            );
        }

        List<String> roles = databaseUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
            "firstName", databaseUser.getFirstName(),
            "email", databaseUser.getEmail(),
            "lastName", databaseUser.getLastName(),
            "role", roles,
            "userId", databaseUser.getUserId().toString(),
            "username", databaseUser.getUsername()
        ));
    }

    @Transactional
    public String addUser(UserDTO userDTO) {
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        UserDTO savedUserDTO = saveUser(userDTO);
        return savedUserDTO.getEmail() + " added to database successfully";
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException("No user found with userId: " + userId)
        );

        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        User updatedUser = userRepository.save(existingUser);
        return userDTOConverter.convertUsertoUserDTO(updatedUser);
    }

    public UserDTO getUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException("No user found with email: " + email)
        );
        return userDTOConverter.convertUsertoUserDTO(user);
    }

    public UserDTO getUserByUserId(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException("No user found with userId: " + userId)
        );
        return userDTOConverter.convertUsertoUserDTO(user);
    }

    public UserDTO getUserByFirstName(String firstName) {
        User user = userRepository.findByFirstName(firstName);
        return userDTOConverter.convertUsertoUserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userDTOConverter::convertUsertoUserDTO)
                .collect(Collectors.toList());
    }
}
