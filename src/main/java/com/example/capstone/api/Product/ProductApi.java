package com.example.capstone.api.Product;

import com.example.capstone.dto.ProductCreateDTO;
import com.example.capstone.dto.ResponseToClient;
import com.example.capstone.model.Product.Category;
import com.example.capstone.model.Product.Product;
import com.example.capstone.repository.Product.ICategoryRepository;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.service.Impl.CategoryService;
import com.example.capstone.service.Impl.ProductService;
import com.example.capstone.util.ConverterMaxCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin("*")
@RestController
@RequestMapping("api/products/")
public class ProductApi {
    @Autowired
    IProductRepository productRepository;

    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productRepository.findByStatusTrue());
    }

    // Liệt kê danh sách sản phẩm theo category
    @GetMapping("category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") Long id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category c = categoryRepository.findById(id).get();
        return ResponseEntity.ok(productRepository.findByCategory(c));
    }


    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productRepository.findById(id).get());
    }

    @GetMapping("top-discount")
    public ResponseEntity<List<Product>> top10discount(){
        return ResponseEntity.ok(productRepository.topdiscount());
    }

    @GetMapping("top-sold")
    public ResponseEntity<List<Product>> top10sold(){
        return ResponseEntity.ok(productRepository.findTop10BySoldDesc());
    }

    @GetMapping("top-entered-date")
    public ResponseEntity<List<Product>> top10enteredDate(){
        return ResponseEntity.ok(productRepository.findTop10ByEnteredDateDesc());
    }

    /**
    {
        "productId":0,
        "productName": "test",
        "productQuantity": 100,
        "productPrice": 100000,
        "expireDate": "2024-06-31",
        "discount": 0,
        "productImg":"",
        "description": "",
        "category": "Food"
    }
    */
    /**
     * API:http://localhost:8080/api/products/create
     * */
    @PostMapping("create")
    public ResponseEntity<ResponseToClient> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        if (productCreateDTO.getExpireDate() != null) {
            LocalDate localExpireDate = productCreateDTO.getExpireDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localExpireDate.isBefore(LocalDate.now())) {
                return ResponseEntity.badRequest().body(new ResponseToClient("Hạn sử dụng không được bé hơn ngày hiện tại."));
            }
        } else {
            return ResponseEntity.badRequest().body(new ResponseToClient("Hạn sử dụng không được để trống."));
        }

        String productName = productCreateDTO.getName();
        if (productName != null && productService.existsProductName(productName.trim()) != null) {
            return ResponseEntity.badRequest().body(new ResponseToClient("Tên sản phẩm đã được sử dụng."));
        }

        Product productMax = productService.findMaxCodeInDatabase();
        String newCode = ConverterMaxCode.generateNextId(productMax != null ? productMax.getCode() : null);
        productCreateDTO.setCode(newCode);


        Category category = categoryService.findByCategoryName(productCreateDTO.getCategory());
        if (category == null) {
            return ResponseEntity.badRequest().body(new ResponseToClient("Danh mục sản phẩm không tồn tại."));
        }

        Product product = productService.createProduct(productCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseToClient("Sản phẩm đã được tạo thành công."));
    }


    /**
     * MAP: PATCH
     * API: http://localhost:8080/api/products/update


    {
    "productId": {product_id},
    "code": "PD-0053",
    "name": "test",
    "quantity": 100,
    "price": 100000.0,
    "discount": null,
    "image": "https://firebasestorage.googleapis.com/v0/b/capstone-1-398205.appspot.com/o/IMG%2Fpexels-mithul-varshan-2318990.jpg?alt=media&token=3f5ff189-53a7-40de-94c2-033154592b0e",
    "description": "Introducing our new Pet Paradise Playhouse! Pamper your furry friend with the ultimate pet haven. This beautifully designed playhouse is the perfect retreat for your beloved pets, whether they are cats, dogs, or even small critters. Crafted with high-quality materials, the Pet Paradise Playhouse is both durable and stylish.The spacious interior offers plenty of room for your pet to relax, play, and unwind. With cozy nooks for lounging, scratching posts for the feline friends, and engaging toys to keep them entertained, your pets will never want to leave their little piece of paradise.The Pet Paradise Playhouse is also easy to assemble, making it a hassle-free addition to your home. Its neutral color scheme and modern design will seamlessly blend into any room decor. Give your pets the luxury they deserve and create a space they'll adore with our Pet Paradise Playhouse. Order yours today and watch your furry companion enjoy their own personal sanctuary!",
    "enteredDate": null,
    "status": true,
    "sold": null,
    "expireDate": "2024-07-01",
    "user": null,
    "category": "Treats"
    }


*/
    @PatchMapping("/update")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        // Kiểm tra xem sản phẩm có tồn tại không
        Product existingProduct = productService.findById(productCreateDTO.getProductId());
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseToClient("Sản phẩm không tồn tại."));
        }

        // Kiểm tra xem tên sản phẩm đã được sử dụng bởi sản phẩm khác chưa
        if (productService.existsProductNameEdit(productCreateDTO.getName().trim(), productCreateDTO.getProductId())) {
            return ResponseEntity.badRequest().body(new ResponseToClient("Tên sản phẩm đã được sử dụng."));
        }

        // Kiểm tra và lấy danh mục từ tên danh mục
        Category category = categoryService.findByCategoryName(productCreateDTO.getCategory());
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseToClient("Danh mục sản phẩm không tồn tại."));
        }

        try {
            productService.updateProduct(productCreateDTO);
            return ResponseEntity.ok().body(new ResponseToClient("Cập nhật sản phẩm thành công."));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseToClient("Dữ liệu không hợp lệ, không thể cập nhật sản phẩm."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseToClient("Có lỗi xảy ra trong quá trình cập nhật sản phẩm: " + e.getMessage()));
        }
    }


    /**
     * MAP: DELETE
     * API: http://localhost:8080/api/products/{product_id}
     */

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        // Kiểm tra xem sản phẩm có tồn tại hay không
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Cập nhật trạng thái của sản phẩm thành false thay vì xóa hẳn
            Product product = optionalProduct.get();
            product.setStatus(false);
            productRepository.save(product);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log lỗi để dễ dàng debug và xử lý lỗi
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
