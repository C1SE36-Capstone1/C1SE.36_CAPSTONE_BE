package com.example.capstone.repository.Product;
import com.example.capstone.dto.HomeDTO;
import com.example.capstone.model.Product.Category;
import com.example.capstone.model.Product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p.* from products p where p.status = 1",nativeQuery = true)
    List<Product> findByStatusTrue();


    List<Product> findByCategory(Category category);

    @Query(value = "select p.* from products p where p.product_id = :id and p.status = 1",nativeQuery = true)
    Product findByProductIdAndStatusTrue(Long id);

    @Query(value = "select p.product_id, p.name, p.price, c.category_name " +
            "from products p " +
            "join categories c on p.category_id = c.category_id " +
            "where p.status = 1 and p.quantity > 0 " +
            "order by p.sold DESC ", nativeQuery = true)
    List<Product> findAllByCategoryAndSoldDesc(Category category);


    @Query(value = "select product_id,entered_date,expire_date,status,code,image,name,price,quantity,category_id from products where product_id = :product_id", nativeQuery = true)
    Product findByProductId(@Param("product_id") Long productId);

    @Query(value =
            "SELECT p.pet_id, p.name, NULL AS price, p.status, p.images, br.breed_name " +
                    "FROM pet p " +
                    "JOIN breed br ON br.breed_id = p.breed_id " +
                    "WHERE (LOWER(br.breed_name) LIKE LOWER(CONCAT('%', :title, '%')) " +
                    "OR LOWER(p.code) LIKE LOWER(CONCAT('%', :title, '%'))) " +
                    "AND p.status = true " +

                    "UNION " +

                    "SELECT pd.product_id, pd.name, pd.price, pd.status, pd.image, c.category_name " +
                    "FROM products pd " +
                    "JOIN categories c ON pd.category_id = c.category_id " +
                    "WHERE LOWER(pd.name) LIKE LOWER(CONCAT('%', :title, '%')) AND pd.status = true",
            countQuery = "SELECT COUNT(*) FROM (" +
                    "SELECT p.pet_id " +
                    "FROM pet p " +
                    "JOIN breed br ON br.breed_id = p.breed_id " +
                    "WHERE (LOWER(br.breed_name) LIKE LOWER(CONCAT('%', :title, '%')) " +
                    "OR LOWER(p.code) LIKE LOWER(CONCAT('%', :title, '%'))) " +
                    "AND p.status = true " +

                    "UNION " +

                    "SELECT pd.product_id " +
                    "FROM products pd " +
                    "JOIN categories c ON pd.category_id = c.category_id " +
                    "WHERE LOWER(pd.name) LIKE LOWER(CONCAT('%', :title, '%')) AND pd.status = true) AS result",
            nativeQuery = true)
    Page<HomeDTO> search(@Param("title") String title, Pageable pageable);

    @Query(value = "SELECT * FROM products " +
            "where status = 1 and quantity > 0 " +
            "ORDER BY discount DESC LIMIT 10 ", nativeQuery = true)
    List<Product> topdiscount();

    @Query(value = "SELECT * FROM products " +
            "where status = 1 " +
            "ORDER BY sold DESC " +
            "LIMIT 10;", nativeQuery = true)
    List<Product> findTop10BySoldDesc();


    @Query(value = "SELECT * FROM products " +
            "where status = 1 " +
            "ORDER BY entered_date DESC " +
            "LIMIT 10;", nativeQuery = true)
    List<Product> findTop10ByEnteredDateDesc();

    @Modifying
    @Transactional
    @Query(value = "insert into products(expire_date, status, code, image, name, price, quantity, category_id)" +
            " values(:expire_date, :status, :code, :image, :name, :price, :quantity, :category_id)",
            nativeQuery = true)
    void saveProductNative(@Param("expire_date") Date expire_date,
                           @Param("status") boolean status,
                           @Param("code") String code,
                           @Param("image") String product_img,
                           @Param("name") String product_name,
                           @Param("price") Double price,
                           @Param("quantity") Integer product_quantity,
                           @Param("category_id") String category_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET expire_date = :expire_date, status = :status,description = :description, code = :code, image = :image, name = :name, "+
            "price =  :price, quantity = :quantity, category_id = :category_id WHERE product_id = :product_id",nativeQuery = true)
    void updateProduct(@Param("expire_date") Date expire_date,
                       @Param("status") boolean status,
                       @Param("code") String code,
                       @Param("image") String image,
                       @Param("name") String name,
                       @Param("description") String description,
                       @Param("price") Double price,
                       @Param("quantity") Integer quantity,
                       @Param("category_id") Long category_id,
                       @Param("product_id") Long productId
    );

    List<Product> findByName(String name);

    @Transactional
    @Query(value = "select  name from products where name = ?1 and product_id <> ?2", nativeQuery = true)
    boolean existsProductName2(String product_name, Long product_id);

    @Transactional
    @Query(value = "SELECT * FROM product WHERE product_id = :id", nativeQuery = true)
    Optional<Product> findByIdNative(@Param("id") Long id);

    @Transactional
    @Query(value = "select  name from products where name = :name", nativeQuery = true)
    String existsProductName(@Param("name") String name);

    @Query(value = "select * from products order by code desc limit 1 ", nativeQuery = true)
    Product findMaxCodeInDatabase();
}
