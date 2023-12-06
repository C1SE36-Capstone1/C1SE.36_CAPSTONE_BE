package com.example.capstone.repository.Product;
import com.example.capstone.dto.HomeDTO;
import com.example.capstone.model.Product.Category;
import com.example.capstone.model.Product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select p.* from products p where p.status = 1",nativeQuery = true)
    List<Product> findByStatusTrue();


    List<Product> findByCategory(Category category);

    @Query(value = "select p.* from products p where p.product_id = :id and p.status = 1",nativeQuery = true)
    Product findByProductIdAndStatusTrue(Integer id);

    @Query(value = "select p.product_id, p.name, p.price, c.category_name " +
            "from products p " +
            "join categories c on p.category_id = c.category_id " +
            "where p.status = 1 and p.quantity > 0 " +
            "order by p.sold DESC ", nativeQuery = true)
    List<Product> findAllByCategoryAndSoldDesc(Category category);


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
}
