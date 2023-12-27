package com.example.capstone.api.User;

import com.example.capstone.Config.JwtUtils;
import com.example.capstone.dto.UserInfo;
import com.example.capstone.dto.reponse.JwtResponse;
import com.example.capstone.dto.reponse.MessageResponse;
import com.example.capstone.dto.request.LoginRequest;
import com.example.capstone.dto.request.SignupRequest;
import com.example.capstone.model.User.Role;
import com.example.capstone.model.User.RoleName;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Cart.ICartRepository;
import com.example.capstone.repository.User.IRoleRepository;
import com.example.capstone.repository.User.IUserRepository;
import com.example.capstone.service.UserDetailsImpl;
import com.example.capstone.service.Impl.UserService;
import com.example.capstone.util.ConverterMaxCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("api/auth/")
public class UserApi {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    UserService userService;
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

    @GetMapping("/detail")
    public ResponseEntity<User> getDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        String email = authentication.getName();
        Optional<User> userOptional = userService.findUserDetailByEmail(email);

        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User userDetail = userOptional.get();

        return ResponseEntity.ok(userDetail);
    }

    /**
     * API: http://localhost:8080/api/auth/user/signin
    {
        "email":"user15@example.com",
        "password": "123"
    }
    */
    @PostMapping("user/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getName(),
                userDetails.getEmail(), userDetails.getPassword(), userDetails.getPhone(), userDetails.getAddress(),
                userDetails.getGender(), userDetails.getStatus(), userDetails.getImage(),LocalDate.now(),
                roles));

    }

    /**
     * API: http://localhost:8080/api/auth/user/create
     * Đăng kí tài khoản cho User
    {
        "name": "user15",
        "email": "user15@example.com",
        "password": "123",
        "phone": "1234567890"
    }
    */
    @PostMapping("user/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                    error -> {
                        String fieldName = error.getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                    });
            return ResponseEntity.badRequest().body(errors);
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is alreadv in use!"));
        }

        User userLimit = userRepository.limitUser();
        signupRequest.setCode(ConverterMaxCode.generateNextId(userLimit.getCode()));

        User user = new User(signupRequest.getName(),
                signupRequest.getCode(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getPhone(),
                true,
                jwtUtils.doGenerateToken(signupRequest.getEmail()));

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, RoleName.USER));

        UserInfo userInfo = new UserInfo(user.getUserId(), user.getCode(),user.getName(),
                user.getEmail(),user.getPhone(),user.getPassword(), user.getAddress(), user.getBirthdate(), user.getGender(),
                user.getImage(),user.getStatus(),user.getToken(),user.getCart(),roles);

        userService.saveUser(userInfo);
        return  ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));
    }


    @PostMapping("admin/create")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                    error -> {
                        String fieldName = error.getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                    });
            return ResponseEntity.badRequest().body(errors);
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is alreadv in use!"));
        }

        User userLimit = userRepository.limitUser();
        signupRequest.setCode(ConverterMaxCode.generateNextId(userLimit.getCode()));

        User user = new User(signupRequest.getName(),
                signupRequest.getCode(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getPhone(),
                true,
                jwtUtils.doGenerateToken(signupRequest.getEmail()));

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, RoleName.ADMIN));

        UserInfo userInfo = new UserInfo(user.getUserId(), user.getCode(),user.getName(),
                user.getEmail(),user.getPhone(),user.getPassword(), user.getAddress(), user.getBirthdate(), user.getGender(),
                user.getImage(),user.getStatus(),user.getToken(),user.getCart(),roles);

        userService.saveUser(userInfo);
        return  ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));
    }

    @PostMapping("admin/create/veterinarian")
    public ResponseEntity<?> create(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                    error -> {
                        String fieldName = error.getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                    });
            return ResponseEntity.badRequest().body(errors);
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is alreadv in use!"));
        }

        User userLimit = userRepository.limitUser();
        signupRequest.setCode(ConverterMaxCode.generateNextId(userLimit.getCode()));

        User user = new User(signupRequest.getName(),
                signupRequest.getCode(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getPhone(),
                true,
                jwtUtils.doGenerateToken(signupRequest.getEmail()));

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, RoleName.VETERINARIAN));

        UserInfo userInfo = new UserInfo(user.getUserId(), user.getCode(),user.getName(),
                user.getEmail(),user.getPhone(),user.getPassword(), user.getAddress(), user.getBirthdate(), user.getGender(),
                user.getImage(),user.getStatus(),user.getToken(),user.getCart(),roles);

        userService.saveUser(userInfo);
        return  ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));
    }

    /**
     * API: http://localhost:8080/api/auth/user/{id}
     * Chỉnh sửa thông tin cá nhân (Edit personal information)
     {
        "name":"Anh Quoc đẹp chai",
        "image": "https://firebasestorage.googleapis.com/v0/b/capstone-1-398205.appspot.com/o/IMG%2Fstudent.jpg?alt=media&token=4b6571a2-1eea-4c15-940e-ffa391187ae3",
        "address": "123 Main St",
        "email": "user15@example.com",
        "password": "123456",
        "phone": "0788518002",
        "gender":1,
        "birthdate":"2002-06-01"
        // "cart":{
        //     "cartId": 13,
        //     "address": "123 Main St",
        //     "phone": "1234567890",
        //     "amount": 0.0
        // }
    }

     */
    @PutMapping("user/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Integer id, @RequestBody UserInfo userUpdate, BindingResult bindingResult) {
        try{
            if (bindingResult.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                bindingResult.getFieldErrors().forEach(
                        error -> {
                            String fieldName = error.getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });
                return ResponseEntity.badRequest().body(errors);
            }else{

                userService.update(userUpdate,id);
            }
            return ResponseEntity.ok(new MessageResponse("Cập nhật thông tin người dùng thành công"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi khi cập nhật thông tin người dùng: " + e.getMessage()));
        }
    }

    /**
     * API: http://localhost:8080/api/auth/admin/delete/user/{id}
     * Xóa người dùng
    */
    @DeleteMapping("admin/delete/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
