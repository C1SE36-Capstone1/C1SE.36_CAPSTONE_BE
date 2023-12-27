package com.example.capstone.api.Product;

import com.example.capstone.dto.CartWithDetail;
import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import com.example.capstone.model.Product.Favorite;
import com.example.capstone.model.Product.Product;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Product.IFavoriteRepository;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.User.IUserRepository;
import com.example.capstone.service.Impl.FavoriteService;
import com.example.capstone.service.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/favorites")
public class FavoriteApi {
    @Autowired
    IFavoriteRepository favoriteRepository;

    @Autowired
    UserService userService;

    @Autowired
    FavoriteService favoriteService;
    @Autowired
    IProductRepository productRepository;

    @GetMapping("/add/{productId}")
    public ResponseEntity<?> addProductToFavorite(@PathVariable("productId") Integer productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return new ResponseEntity<>("Người dùng chưa đăng nhập", HttpStatus.FORBIDDEN);
        }

        String email = authentication.getName();
        Optional<User> userOptional = userService.findByEmail(email);

        if (!userOptional.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy người dùng", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        if (favoriteService.checkProductExistInFavorites(productId, user.getUserId()).isPresent()) {
            return new ResponseEntity<>("Sản phẩm đã tồn tại trong danh sách yêu thích", HttpStatus.CONFLICT);
        }

        favoriteService.addProductToFavorites(productId, user.getUserId());
        return new ResponseEntity<>("Sản phẩm đã được thêm vào danh sách yêu thích", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if(!favoriteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        favoriteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
