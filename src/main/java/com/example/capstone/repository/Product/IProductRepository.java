package com.example.capstone.repository.Product;
import com.example.capstone.model.Product.Category;
import com.example.capstone.model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByStatusTrue();

    List<Product> findByCategory(Category category);

    Product findByProductIdAndStatusTrue(Integer id);

    //@Query(value = "", nativeQuery = true)
    //List<Product> findProductRated();


    @Query(value = "select p.product_id, p.name, p.price, c.category_name " +
            "from products p " +
            "join categories c on p.category_id = c.category_id " +
            "where p.status = 1 and p.quantity > 0 " +
            "order by p.sold DESC ", nativeQuery = true)
    List<Product> findAllByCategoryAndSoldDesc(Category category);

}
