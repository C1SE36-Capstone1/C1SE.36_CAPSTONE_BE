package com.example.capstone.api.User;

import com.example.capstone.Config.JwtUtils;
import com.example.capstone.dto.reponse.JwtResponse;
import com.example.capstone.dto.reponse.MessageResponse;
import com.example.capstone.dto.request.LoginRequest;
import com.example.capstone.dto.request.SignupRequest;
import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.User.Role;
import com.example.capstone.model.User.RoleName;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Cart.ICartRepository;
import com.example.capstone.repository.User.IRoleRepository;
import com.example.capstone.repository.User.IUserRepository;
import com.example.capstone.service.Impl.UserDetailsImpl;
import com.example.capstone.util.ConverterMaxCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("api/auth")
public class UserApi {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ICartRepository cartRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;


    @GetMapping("{id}")
    public ResponseEntity<List<User>> getAll(){
        return  ResponseEntity.ok(userRepository.findByStatusTrue());
    }

    @PostMapping("user/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getName(),
                userDetails.getEmail(), userDetails.getPassword(), userDetails.getPhone(), userDetails.getAddress(),
                userDetails.getGender(), userDetails.getStatus(), userDetails.getImage(), userDetails.getRegisterDate(),
                roles));

    }

    @PostMapping("user/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is alreadv in use!"));
        }

        // Generate the next ID for the user
        User userLimit = userRepository.limitUser();
        signupRequest.setCode(ConverterMaxCode.generateNextId(userLimit.getCode()));

        // create new user account
        User user = new User(signupRequest.getName(), signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getPhone(),
                signupRequest.getAddress(), signupRequest.getGender(), signupRequest.getStatus(),
                signupRequest.getImage(), signupRequest.getRegisterDate(), signupRequest.getCode(),
                jwtUtils.doGenerateToken(signupRequest.getEmail()));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, RoleName.USER));


        user.setRoles(roles);
        userRepository.save(user);
        Cart c = new Cart(0, 0.0, user.getAddress(), user.getPhone(), user);
        cartRepository.save(c);
        return ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));

    }

    @PostMapping("admin/signup")
    public ResponseEntity<?> registerAdmin(@Validated @RequestBody SignupRequest signupRequest) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is alreadv in use!"));
        }

        // Generate the next ID for the user
        User userLimit = userRepository.limitUser();
        signupRequest.setCode(ConverterMaxCode.generateNextId(userLimit.getCode()));

        // create new admin account
        User admin = new User(signupRequest.getName(), signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getPhone(),
                signupRequest.getAddress(), signupRequest.getGender(), signupRequest.getStatus(),
                signupRequest.getImage(), signupRequest.getRegisterDate(), signupRequest.getCode(),
                jwtUtils.doGenerateToken(signupRequest.getEmail()));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2, RoleName.ADMIN));


        admin.setRoles(roles);
        userRepository.save(admin);
        Cart c = new Cart(0, 0.0, admin.getAddress(), admin.getPhone(), admin);
        cartRepository.save(c);
        return ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));

    }

}
