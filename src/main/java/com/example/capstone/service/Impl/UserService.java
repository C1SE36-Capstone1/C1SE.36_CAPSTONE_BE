package com.example.capstone.service.Impl;

import com.example.capstone.dto.UserInfo;
import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Cart.ICartRepository;
import com.example.capstone.repository.User.IRoleRepository;
import com.example.capstone.repository.User.IUserRepository;
import com.example.capstone.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ICartRepository cartRepository;
    private final IRoleRepository roleRepository;

    @Autowired
    UserService(IUserRepository userRepository,ICartRepository cartRepository, IRoleRepository roleRepository){
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteUserId(id);
    }

    @Override
    public void update(UserInfo userInfo, Integer id) {

    }

    @Override
    public void saveUser(UserInfo userInfo) {
        cartRepository.insertCart(userInfo.getAddress(),userInfo.getPhone(),0.0);
        Cart cart = cartRepository.findLastCart().orElse(null);
        if(cart!=null){
            LocalDate currentDate = LocalDate.now();
            userRepository.insertUser( userInfo.getName(),userInfo.getEmail(),userInfo.getCode(),userInfo.getPhone(), userInfo.getAddress(), userInfo.getBirthdate(),
                   userInfo.getPassword(), currentDate, userInfo.getGender(),userInfo.getAddress(),userInfo.getStatus(),userInfo.getToken()
                    ,cart.getCartId());

            User user = userRepository.findByEmail(userInfo.getEmail()).orElse(null);
            if (user != null) {
                // Lưu thông tin vai trò
                userInfo.getRoles().forEach(role -> roleRepository.insertUserRole(user.getUserId(), role.getId()));
            }
        }
    }
}